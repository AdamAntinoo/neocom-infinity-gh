//  PROJECT:     Neocom.Microservices (NEOC-MS)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Java 1.8 / SpringBoot-1.3.5 / Angular 5.0
//  DESCRIPTION: This is the SpringBoot MicroServices module to run the backend services to complete the web
//               application based on Angular+SB. This is the web version for the NeoCom Android native
//               application. Most of the source code is common to both platforms and this module includes
//               the source for the specific functionality for the backend services.
package org.dimensinfin.eveonline.neocom;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

import org.joda.time.DateTime;
import org.joda.time.Instant;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import org.dimensinfin.eveonline.neocom.controller.ManufactureResourcesController;
import org.dimensinfin.eveonline.neocom.database.NeoComSBDBHelper;
import org.dimensinfin.eveonline.neocom.database.SDESBDBHelper;
import org.dimensinfin.eveonline.neocom.database.entity.Property;
import org.dimensinfin.eveonline.neocom.datamngmt.ESINetworkManager;
import org.dimensinfin.eveonline.neocom.datamngmt.FileSystemSBImplementation;
import org.dimensinfin.eveonline.neocom.datamngmt.GlobalDataManager;
import org.dimensinfin.eveonline.neocom.datamngmt.GlobalSBConfigurationProvider;
import org.dimensinfin.eveonline.neocom.datamngmt.InfinityGlobalDataManager;
import org.dimensinfin.eveonline.neocom.datamngmt.MarketDataServer;
import org.dimensinfin.eveonline.neocom.esiswagger.model.GetCharactersCharacterIdAssets200Ok;
import org.dimensinfin.eveonline.neocom.industry.EveTask;
import org.dimensinfin.eveonline.neocom.industry.FacetedAssetContainer;
import org.dimensinfin.eveonline.neocom.industry.Resource;
import org.dimensinfin.eveonline.neocom.model.ANeoComEntity;
import org.dimensinfin.eveonline.neocom.model.EveItem;
import org.dimensinfin.eveonline.neocom.model.EveLocation;
import org.dimensinfin.eveonline.neocom.model.NeoComAsset;
import org.dimensinfin.eveonline.neocom.model.PilotV2;
import org.dimensinfin.eveonline.neocom.services.TimedUpdater;

// - CLASS IMPLEMENTATION ...................................................................................

/**
 * This is the initial class and loader for the Spring Boot application. It will be used to integrate the
 * different modules and libraries, instantiate the adapters and serve as the base point to integrate the
 * different controllers. Most of the code will be imported and integrated from the depending libraries.
 *
 * @author Adam Antinoo
 */
//@EnableCaching
//@EnableCircuitBreaker
@EnableScheduling
//@EnableAsync
@SpringBootApplication
public class NeoComMicroServiceApplication {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = LoggerFactory.getLogger("NeoComMicroServiceApplication");
	public static final boolean MOCK_UP = true;

	public static MarketDataServer mdServer = null;
	public static final TimedUpdater timedService = new TimedUpdater();

	public static final ObjectMapper jsonMapper = new ObjectMapper();

	static {
		jsonMapper.enable(SerializationFeature.INDENT_OUTPUT);
		jsonMapper.registerModule(new JodaModule());
		// Add our own serializers.
		SimpleModule neocomSerializerModule = new SimpleModule();
		neocomSerializerModule.addSerializer(PilotV2.class, new PilotV2Serializer());
		neocomSerializerModule.addSerializer(Property.class, new PropertySerializer());
		neocomSerializerModule.addSerializer(NeoComAsset.class, new NeoComAssetSerializer());
		neocomSerializerModule.addSerializer(EveTask.class, new ProcessingTaskSerializer());
		neocomSerializerModule.addSerializer(Exception.class, new ExceptionSerializer());
		neocomSerializerModule.addSerializer(EveLocation.class, new LocationSerializer());
		neocomSerializerModule.addSerializer(EveTask.class, new ProcessingTaskSerializer());
		neocomSerializerModule.addSerializer(Resource.class, new ResourceSerializer());
		neocomSerializerModule.addSerializer(ManufactureResourcesController.RefiningProcess.class, new RefiningProcessSerializer());
		neocomSerializerModule.addSerializer(FacetedAssetContainer.class, new FacetedAssetContainerSerializer());
		jsonMapper.registerModule(neocomSerializerModule);
	}

	/**
	 * Instance for the mapping of OK instances to the MVC compatible classes.
	 */
	public static final ModelMapper modelMapper = new ModelMapper();

	static {
		modelMapper.getConfiguration()
				.setFieldMatchingEnabled(true)
				.setMethodAccessLevel(Configuration.AccessLevel.PRIVATE);
	}

	public static Hashtable<String, NeoComSession> sessionStore = new Hashtable();

	public static boolean validatePilotIdentifierMatch( final String sessionLocator, final Integer identifier ) {
		logger.info(">> [NeoComMicroServiceApplication.validatePilotIdentifierMatch]");
		try {
			// Convert the locator to an instance.
			SessionLocator locator = NeoComMicroServiceApplication.modelMapper.map(sessionLocator, SessionLocator.class);
			locator = new SessionLocator()
					.setSessionLocator("-MANUALLY-CREATED-LOCATOR-")
					.setTimeValid(Instant.now().getMillis());
			if (null == locator) return false;
			final String contentsSerialized = NeoComMicroServiceApplication.jsonMapper.writeValueAsString(locator);

			// First check the time span validity.
			final long now = Instant.now().getMillis();
//			final long nowPlus15 = Instant.now().plus(TimeUnit.MINUTES.toMillis(15)).toInstant().getMillis();
			final long timeLimit = new Instant(locator.getTimeValid()).plus(TimeUnit.MINUTES.toMillis(15)).toInstant().getMillis();
			if (timeLimit <= now) return false;

			// Then check if the identifiers match.
			final NeoComSession hit = sessionStore.get(locator.getSessionLocator());
			if (null == hit) return false;
			if (hit.getPilotIdentifier() != identifier) return false;

			// All was validated. We can access the data
			return true;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return false;
		} finally {
			logger.info("<< [NeoComMicroServiceApplication.validatePilotIdentifierMatch]");
		}
//		return true;
	}
	// - M A I N   E N T R Y P O I N T ........................................................................

	/**
	 * Create all the platform specific connectors and then launch it to run.
	 *
	 * @param args
	 */
	public static void main( final String[] args ) throws IOException {
		logger.info(">> [NeoComMicroServiceApplication.main]");
		// Connect the file system to be able to read the assets and other application resources stored externally.
		logger.info("-- [NeoComApp.onCreate]> Connecting the File System to Global...");
		InfinityGlobalDataManager.installFileSystem(new FileSystemSBImplementation(
				System.getenv("appname"))
		);

		// Connect the Configuration manager.
		logger.info("-- [NeoComMicroServiceApplication.main]> Connecting the Configuration Manager...");
		InfinityGlobalDataManager.connectConfigurationManager(new GlobalSBConfigurationProvider("properties"));

		// Initialize the Model with the current global instance.
		logger.info("-- [NeoComMicroServiceApplication.main]> Connecting Global to Model...");
		ANeoComEntity.connectGlobal(new InfinityGlobalDataManager());

		// Initializing the ESI api network controller.
		ESINetworkManager.initialize();

		// Connect the SDE database.
		logger.info("-- [NeoComMicroServiceApplication.main]> Connecting SDE database...");
		try {
			InfinityGlobalDataManager.connectSDEDBConnector(new SDESBDBHelper()
					.setDatabaseSchema(GlobalDataManager.getResourceString("R.database.sdedatabase.databaseschema"))
					.setDatabasePath(GlobalDataManager.getResourceString("R.database.sdedatabase.databasepath"))
					.setDatabaseName(GlobalDataManager.getResourceString("R.database.sdedatabase.databasename"))
					.build()
			);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		ANeoComEntity.connectSDEHelper(new GlobalDataManager().getSDEDBHelper());

		// Connect the MarketData service.
		logger.info("-- [NeoComMicroServiceApplication.main]> Starting Market Data service...");
		mdServer = new MarketDataServer().start();
		InfinityGlobalDataManager.setMarketDataManager(mdServer);

		// Connect the NeoCom database.
		logger.info("-- [NeoComMicroServiceApplication.main]> Connecting NeoCom private database...");
		try {
			InfinityGlobalDataManager.connectNeoComDBConnector(new NeoComSBDBHelper()
					.setDatabaseHost(GlobalDataManager.getResourceString("R.database.neocom.databasehost"
							, "jdbc:mysql://localhost:3306"))
					.setDatabaseName("neocom")
					.setDatabaseUser(GlobalDataManager.getResourceString("R.database.neocom.databaseuser"
							, "NEOCOM"))
					.setDatabasePassword(GlobalDataManager.getResourceString("R.database.neocom.databasepassword"))
					.setDatabaseVersion(GlobalDataManager.getResourceInt("R.database.neocom.databaseversion"))
					.build()
			);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}

		// Load the Locations cache to speed up the Citadel and Outpost search.
		logger.info("-- [NeoComMicroServiceApplication.main]> Read Locations data cache...");
		InfinityGlobalDataManager.readLocationsDataCache();

//		// Connect the Timed Upgrade scan.
//		logger.info("-- [NeoComMicroServiceApplication.main]> Connecting the background timed download scanner...");
//		timedService = new TimedUpdater();

		logger.info("-- [NeoComMicroServiceApplication.main]> Starting application instance...");
		SpringApplication.run(NeoComMicroServiceApplication.class, args);
		logger.info("<< [NeoComMicroServiceApplication.main]");
	}

	// - F I E L D - S E C T I O N ............................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................

	// - M E T H O D - S E C T I O N ..........................................................................
	@Scheduled(initialDelay = 180000, fixedDelay = 120000)
	private void writeMarketData() {
		mdServer.writeMarketDataCacheToStorage();
	}

	@Scheduled(initialDelay = 180000, fixedDelay = 120000)
	private void writeMarketDataDownloadReport() {
		mdServer.reportMarketDataJobs();
	}
//
//	@Scheduled(initialDelay = 180000, fixedDelay = 180000)
//	private void writeLocationData() {
//		if (GlobalDataManager.getResourceBoolean("R.cache.locationscache.activestate", true))
//			GlobalDataManager.writeLocationsDatacache();
//	}

	//	@Scheduled(initialDelay = 120000, fixedDelay = 900000)
	@Scheduled(initialDelay = 120000, fixedDelay = 120000)
	private void onTime() {
		// Fire another background update scan.
		// Check if the configuration properties allow to run the updater.
		if (GlobalDataManager.getResourceBoolean("R.updater.allowtimer", false)) {
			timedService.timeTick();
		}
	}

	public static class SessionLocator {
		public String sessionLocator = null;
		public long timeValid = -1;

		public String getSessionLocator() {
			return sessionLocator;
		}

		public long getTimeValid() {
			return timeValid;
		}

		public SessionLocator setSessionLocator( final String sessionLocator ) {
			this.sessionLocator = sessionLocator;
			return this;
		}

		public SessionLocator setTimeValid( final long timeValid ) {
			this.timeValid = timeValid;
			return this;
		}
	}

	public static class NeoComSessionIdentifier {
		public String jsonClass = "NeoComSessionIdentifier";
		public String sessionIdentifier = "-NOT VALID-";
		// TODO REMOVE ONCE THE encryption works.
		public int pilotId = 0;
		public byte[] pilotIdentifier = "-EMPTY-".getBytes();

		public NeoComSessionIdentifier( final NeoComSession session ) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
			sessionIdentifier = session.getSessionId();
//		X509EncodedKeySpec publicSpec = new X509EncodedKeySpec(session.getPublicKey().getBytes());
//		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//		final PublicKey publicRemoteKey = keyFactory.generatePublic(publicSpec);
//		Cipher cipher = Cipher.getInstance("RSA");
//		cipher.init(Cipher.ENCRYPT_MODE, publicRemoteKey);
			pilotId = session.getPilotIdentifier();
//		pilotIdentifier = cipher.doFinal(Integer.valueOf(session.getPilotIdentifier()).toString().getBytes());
		}
	}

	// --- S E R I A L I Z E R   S E C T I O N
	// - CLASS IMPLEMENTATION ...................................................................................
	public static class ExceptionSerializer extends JsonSerializer<Exception> {
		// - F I E L D - S E C T I O N ............................................................................

		// - M E T H O D - S E C T I O N ..........................................................................
		@Override
		public void serialize( final Exception value, final JsonGenerator jgen, final SerializerProvider provider )
				throws IOException, JsonProcessingException {
			jgen.writeStartObject();
			jgen.writeStringField("exceptionClass", value.getClass().getSimpleName());
			jgen.writeStringField("message", value.getMessage());
			jgen.writeObjectField("stackTrace", value.getStackTrace());
			jgen.writeEndObject();
		}
	}
	// ..........................................................................................................

	// - CLASS IMPLEMENTATION ...................................................................................
	public static class PilotV2Serializer extends JsonSerializer<PilotV2> {
		// - F I E L D - S E C T I O N ............................................................................

		// - M E T H O D - S E C T I O N ..........................................................................
		@Override
		public void serialize( final PilotV2 value, final JsonGenerator jgen, final SerializerProvider provider )
				throws IOException, JsonProcessingException {
			jgen.writeStartObject();

			jgen.writeStringField("jsonClass", value.getJsonClass());
			jgen.writeNumberField("characterId", value.getCharacterId());
			jgen.writeStringField("name", value.getName());
			jgen.writeObjectField("corporation", value.getCorporation());
			jgen.writeObjectField("alliance", value.getAlliance());
			jgen.writeObjectField("race", value.getRace());
			jgen.writeObjectField("bloodline", value.getBloodline());
			jgen.writeObjectField("ancestry", value.getAncestry());

			// Transform the birthday time to a date string.
			DateTime dt = new DateTime(value.getBirthday());
			DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MMMM-dd'T'HH:mm:ssZ");
			jgen.writeStringField("birthday", fmt.print(dt));
			jgen.writeNumberField("birthdayNumber", value.getBirthday());
			jgen.writeStringField("gender", value.getGender());
			jgen.writeNumberField("securityStatus", value.getSecurityStatus());
			jgen.writeNumberField("accountBalance", value.getAccountBalance());
			jgen.writeStringField("urlforAvatar", value.getUrlforAvatar());
			jgen.writeObjectField("lastKnownLocation", value.getLastKnownLocation());
			jgen.writeObjectField("locationRoles", value.getLocationRoles());
			jgen.writeObjectField("actions4Pilot", value.getActions4Pilot());

			jgen.writeEndObject();
		}
	}

	// ..........................................................................................................

	// - CLASS IMPLEMENTATION ...................................................................................
	public static class PropertySerializer extends JsonSerializer<Property> {
		// - F I E L D - S E C T I O N ............................................................................

		// - M E T H O D - S E C T I O N ..........................................................................
		@Override
		public void serialize( final Property value, final JsonGenerator jgen, final SerializerProvider provider )
				throws IOException, JsonProcessingException {
			jgen.writeStartObject();
			jgen.writeStringField("jsonClass", "Property");
			jgen.writeNumberField("id", value.getId());
			jgen.writeStringField("propertyType", value.getPropertyType().name());
			jgen.writeStringField("stringValue", value.getStringValue());
			jgen.writeNumberField("numericValue", value.getNumericValue());
			jgen.writeNumberField("ownerId", value.getOwnerId());
			jgen.writeEndObject();
		}
	}
	// ..........................................................................................................

	// - CLASS IMPLEMENTATION ...................................................................................
	public static class NeoComAssetSerializer extends JsonSerializer<NeoComAsset> {
		// - F I E L D - S E C T I O N ............................................................................

		// - M E T H O D - S E C T I O N ..........................................................................
		@Override
		public void serialize( final NeoComAsset value, final JsonGenerator jgen, final SerializerProvider provider )
				throws IOException, JsonProcessingException {
			jgen.writeStartObject();
			jgen.writeStringField("jsonClass", value.getJsonClass());
			jgen.writeNumberField("id", value.getDAOID());
			jgen.writeNumberField("assetId", value.getAssetId());
			jgen.writeNumberField("typeId", value.getTypeId());
			jgen.writeNumberField("quantity", value.getQuantity());
			jgen.writeNumberField("locationId", value.getLocationId());
			jgen.writeObjectField("location", value.getLocation());
			jgen.writeStringField("locationType", value.getLocationType().name());
			jgen.writeStringField("locationFlag", value.getFlag().name());
			jgen.writeStringField("isPackaged", Boolean.valueOf(value.isPackaged()).toString());
			jgen.writeNumberField("parentAssetId", value.getParentContainerId());
			jgen.writeNumberField("ownerId", value.getOwnerId());
			jgen.writeStringField("name", value.getName());
			jgen.writeStringField("categoryName", value.getCategoryName());
			jgen.writeStringField("groupName", value.getGroupName());
			jgen.writeStringField("tech", value.getTech());
			jgen.writeBooleanField("blueprintFlag", value.isBlueprint());
			jgen.writeBooleanField("shipFlag", value.isShip());
			jgen.writeBooleanField("containerFlag", value.isContainer());
			jgen.writeStringField("userLabel", value.getUserLabel());
			jgen.writeNumberField("iskValue", value.getIskValue());
			jgen.writeNumberField("price", value.getItem().getPrice());
			jgen.writeNumberField("volume", value.getItem().getVolume());
			jgen.writeObjectField("item", value.getItem());
			jgen.writeEndObject();
		}
	}
	// ..........................................................................................................

	// - CLASS IMPLEMENTATION ...................................................................................
	public static class LocationSerializer extends JsonSerializer<EveLocation> {
		// - F I E L D - S E C T I O N ............................................................................

		// - M E T H O D - S E C T I O N ..........................................................................
		@Override
		public void serialize( final EveLocation value, final JsonGenerator jgen, final SerializerProvider provider )
				throws IOException, JsonProcessingException {
			jgen.writeStartObject();
//			jgen.writeNumberField("recordid", value.getRealId());
			jgen.writeNumberField("id", value.getID());
			jgen.writeNumberField("stationId", value.getStationId());
			jgen.writeStringField("station", value.getStation());
			jgen.writeNumberField("systemId", value.getSystemId());
			jgen.writeStringField("system", value.getSystem());
			jgen.writeNumberField("constellationId", value.getConstellationId());
			jgen.writeStringField("constellation", value.getConstellation());
			jgen.writeNumberField("regionId", value.getRegionId());
			jgen.writeStringField("region", value.getRegion());
			jgen.writeNumberField("security", value.getSecurityValue());
			jgen.writeStringField("typeId", value.getTypeId().name());
			jgen.writeStringField("urlLocationIcon", value.getUrlLocationIcon());
			jgen.writeEndObject();
		}
	}
	// ..........................................................................................................

	// - CLASS IMPLEMENTATION ...................................................................................
	public static class ResourceSerializer extends JsonSerializer<Resource> {
		// - F I E L D - S E C T I O N ............................................................................

		// - M E T H O D - S E C T I O N ..........................................................................
		@Override
		public void serialize( final Resource value, final JsonGenerator jgen, final SerializerProvider provider )
				throws IOException, JsonProcessingException {
			jgen.writeStartObject();
			jgen.writeStringField("jsonClass", value.getJsonClass());
			jgen.writeNumberField("typeId", value.getTypeId());
			jgen.writeStringField("name", value.getName());
			jgen.writeStringField("category", value.getCategory());
			jgen.writeStringField("groupName", value.getGroupName());
			jgen.writeNumberField("quantity", value.getQuantity());
			jgen.writeNumberField("baseQuantity", value.getBaseQuantity());
			jgen.writeNumberField("stackSize", value.getStackSize());
			jgen.writeObjectField("item", value.getItem());
			jgen.writeEndObject();
		}
	}
	// ..........................................................................................................

	// - CLASS IMPLEMENTATION ...................................................................................
	public static class FacetedAssetContainerSerializer extends JsonSerializer<FacetedAssetContainer> {
		// - F I E L D - S E C T I O N ............................................................................

		// - M E T H O D - S E C T I O N ..........................................................................
		@Override
		public void serialize( final FacetedAssetContainer value, final JsonGenerator jgen, final SerializerProvider provider )
				throws IOException, JsonProcessingException {
			jgen.writeStartObject();
			jgen.writeStringField("jsonClass", "FacetedResourceContainer");
			jgen.writeObjectField("facet", value.getFacet());
			jgen.writeObjectField("contents", value.getContents().values());
			jgen.writeEndObject();
		}
	}
	// ..........................................................................................................

	// - CLASS IMPLEMENTATION ...................................................................................
	public static class ProcessingTaskSerializer extends JsonSerializer<EveTask> {
		// - F I E L D - S E C T I O N ............................................................................

		// - M E T H O D - S E C T I O N ..........................................................................
		@Override
		public void serialize( final EveTask value, final JsonGenerator jgen, final SerializerProvider provider )
				throws IOException, JsonProcessingException {
			jgen.writeStartObject();
			jgen.writeStringField("jsonClass", value.getJsonClass());
			jgen.writeStringField("taskType", value.getTaskType().name());
			jgen.writeNumberField("typeId", value.getTypeId());
			jgen.writeStringField("itemName", value.getItemName());
			jgen.writeNumberField("price", value.getPrice());
			jgen.writeNumberField("basePrice", value.getItem().getBaseprice());
			jgen.writeNumberField("quantity", value.getQty());
			jgen.writeObjectField("item", value.getItem());
			jgen.writeObjectField("sourceLocation", value.getLocation());
			jgen.writeObjectField("destination", value.getDestination());
			jgen.writeObjectField("referencedAsset", value.getReferencedAsset());
			jgen.writeEndObject();
		}
	}

	// ..........................................................................................................
	// - CLASS IMPLEMENTATION ...................................................................................
	public static class RefiningProcessSerializer extends JsonSerializer<ManufactureResourcesController.RefiningProcess> {
		// - F I E L D - S E C T I O N ............................................................................

		// - M E T H O D - S E C T I O N ..........................................................................
		@Override
		public void serialize( final ManufactureResourcesController.RefiningProcess value, final JsonGenerator jgen, final SerializerProvider provider )
				throws IOException, JsonProcessingException {
			jgen.writeStartObject();
			jgen.writeStringField("jsonClass", "RefiningProcess");
			jgen.writeNumberField("typeId", value.getTypeId());
			jgen.writeStringField("name", value.getName());
			jgen.writeStringField("category", value.getCategory());
			jgen.writeStringField("groupName", value.getGroupName());
			jgen.writeNumberField("quantity", value.getQuantity());
			jgen.writeNumberField("baseQuantity", value.getBaseQuantity());
			jgen.writeNumberField("stackSize", value.getStackSize());
			jgen.writeObjectField("item", value.getItem());
			jgen.writeObjectField("refineResults", value.refine().values());
			jgen.writeEndObject();
		}
	}
	// ..........................................................................................................


//	// - CLASS IMPLEMENTATION ...................................................................................
//	public static class ShipSerializer extends JsonSerializer<Ship> {
//		// - F I E L D - S E C T I O N ............................................................................
//
//		// - M E T H O D - S E C T I O N ..........................................................................
//		@Override
//		public void serialize( final Ship value, final JsonGenerator jgen, final SerializerProvider provider )
//				throws IOException, JsonProcessingException {
//			jgen.writeStartObject();
//			jgen.writeStringField("jsonClass", value.getJsonClass());
//			jgen.writeNumberField("assetId", value.getAssetId());
//			jgen.writeNumberField("typeId", value.getTypeId());
//			jgen.writeNumberField("ownerId", value.getOwnerId());
//			jgen.writeStringField("name", value.getItemName());
//			jgen.writeStringField("category", value.getCategory());
//			jgen.writeStringField("groupName", value.getGroupName());
//			jgen.writeStringField("tech", value.getTech());
//			jgen.writeStringField("userLabel", value.getUserLabel());
//			jgen.writeNumberField("price", value.getItem().getPrice());
//			jgen.writeNumberField("highesBuyerPrice", value.getItem().getHighestBuyerPrice().getPrice());
//			jgen.writeNumberField("lowerSellerPrice", value.getItem().getLowestSellerPrice().getPrice());
//			jgen.writeObjectField("item", value.getItem());
//			jgen.writeEndObject();
//		}
//	}
//	// ..........................................................................................................

//	// - CLASS IMPLEMENTATION ...................................................................................
//	public static class CredentialSerializer extends JsonSerializer<Credential> {
//		// - F I E L D - S E C T I O N ............................................................................
//
//		// - M E T H O D - S E C T I O N ..........................................................................
//		@Override
//		public void serialize( final Credential value, final JsonGenerator jgen, final SerializerProvider provider )
//				throws IOException, JsonProcessingException {
//			jgen.writeStartObject();
//			jgen.writeStringField("jsonClass", value.getJsonClass());
//			jgen.writeNumberField("accountId", value.getAccountId());
//			jgen.writeStringField("accountName", value.getAccountName());
//			jgen.writeStringField("tokenType", value.getTokenType());
//			jgen.writeBooleanField("isActive", value.isActive());
//			jgen.writeBooleanField("isXML", value.isXMLCompatible());
//			jgen.writeBooleanField("isESI", value.isESICompatible());
//			jgen.writeObjectField("pilot", GlobalDataManager.getPilotV2(value.getAccountId()));
//			jgen.writeEndObject();
//		}
//	}
//	// ..........................................................................................................

//	// - CLASS IMPLEMENTATION ...................................................................................
//	public static class ActionSerializer extends JsonSerializer<Action> {
//		// - F I E L D - S E C T I O N ............................................................................
//
//		// - M E T H O D - S E C T I O N ..........................................................................
//		@Override
//		public void serialize( final Action value, final JsonGenerator jgen, final SerializerProvider provider )
//				throws IOException, JsonProcessingException {
//			jgen.writeStartObject();
//			jgen.writeStringField("jsonClass", value.getJsonClass());
//			jgen.writeNumberField("typeId", value.getTypeId());
//			jgen.writeStringField("itemName", value.getItemName());
//			jgen.writeNumberField("requestQty", value.getRequestQty());
//			jgen.writeNumberField("completedQty", value.getCompletedQty());
//			jgen.writeStringField("category", value.getCategory());
//			jgen.writeStringField("group", value.getGroupName());
//			jgen.writeStringField("itemIndustryGroup", value.getItemIndustryGroup().name());
//			jgen.writeObjectField("resource", value.getResource());
//			jgen.writeObjectField("tasks", value.getTasks());
//			jgen.writeEndObject();
//		}
//	}
//	// ..........................................................................................................


//	// - CLASS IMPLEMENTATION ...................................................................................
//	public static class NeoComAssetSerializer extends JsonSerializer<NeoComAsset> {
//		// - F I E L D - S E C T I O N ............................................................................
//
//		// - M E T H O D - S E C T I O N ..........................................................................
//		@Override
//		public void serialize( final NeoComAsset value, final JsonGenerator jgen, final SerializerProvider provider )
//				throws IOException, JsonProcessingException {
//			jgen.writeStartObject();
//			jgen.writeStringField("jsonClass", value.getJsonClass());
//			jgen.writeNumberField("assetId", value.getAssetId());
//			jgen.writeObjectField("typeId", value.getTypeId());
//			jgen.writeNumberField("quantity", value.getQuantity());
//			jgen.writeNumberField("locationId", value.getLocationId());
//			jgen.writeStringField("locationType", value.getLocationType().name());
//			jgen.writeStringField("locationFlag", value.getFlag().name());
//			jgen.writeStringField("name", value.getName());
//			jgen.writeNumberField("ownerId", value.getOwnerId());
//			jgen.writeStringField("name", value.getName());
//			jgen.writeStringField("categoryName", value.getCategory());
//			jgen.writeStringField("groupName", value.getGroupName());
//			jgen.writeStringField("tech", value.getTech());
//			jgen.writeStringField("userLabel", value.getUserLabel());
//			jgen.writeNumberField("price", value.getPrice());
//			jgen.writeNumberField("parentContainerId", value.getParentContainerId());
//			jgen.writeObjectField("item", value.getItem());
//			jgen.writeObjectField("location", value.getLocation());
//			jgen.writeEndObject();
//		}
//	}
//	// ..........................................................................................................

	// - CLASS IMPLEMENTATION ...................................................................................
//	public static class LocationSerializer extends JsonSerializer<EveLocation> {
//		// - F I E L D - S E C T I O N ............................................................................
//
//		// - M E T H O D - S E C T I O N ..........................................................................
//		@Override
//		public void serialize( final EveLocation value, final JsonGenerator jgen, final SerializerProvider provider )
//				throws IOException, JsonProcessingException {
//			jgen.writeStartObject();
//			jgen.writeStringField("jsonClass", value.getJsonClass());
//			jgen.writeNumberField("accountId", value.getAccountId());
//			jgen.writeStringField("accountName", value.getAccountName());
//			jgen.writeStringField("tokenType", value.getTokenType());
//			jgen.writeBooleanField("isActive", value.isActive());
//			jgen.writeBooleanField("isXML", value.isXMLCompatible());
//			jgen.writeBooleanField("isESI", value.isESICompatible());
//			jgen.writeObjectField("pilot", GlobalDataManager.getPilotV2(value.getAccountId()));
//			jgen.writeEndObject();
//
//
//
//			@JsonIgnore
//			@DatabaseField(id = true, index = true)
//			protected long id = -2;
//			@DatabaseField
//			protected long stationId = -1;
//			@DatabaseField
//			private String station = "SPACE";
//			@DatabaseField
//			protected long systemId = -1;
//			@DatabaseField
//			private String system = "UNKNOWN";
//			@DatabaseField
//			protected long constellationId = -1;
//			@DatabaseField
//			private String constellation = "Echo Cluster";
//			@DatabaseField
//			protected long regionId = -1;
//			@DatabaseField
//			private String region = "-DEEP SPACE-";
//			@DatabaseField
//			private String security = "0.0";
//			@DatabaseField
//			protected String typeId = ELocationType.UNKNOWN.name();
//			public String urlLocationIcon = null;
//
//
//
//
//
//		}
//	}
	// ........................................................................................................
}
// - UNUSED CODE ............................................................................................
