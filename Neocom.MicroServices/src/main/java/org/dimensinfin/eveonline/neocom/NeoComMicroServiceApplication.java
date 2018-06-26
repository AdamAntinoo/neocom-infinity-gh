//  PROJECT:     Neocom.Microservices (NEOC-MS)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Java 1.8 / SpringBoot-1.3.5 / Angular 5.0
//  DESCRIPTION: This is the SpringBoot MicroServices module to run the backend services to complete the web
//               application based on Angular+SB. This is the web version for the NeoCom Android native
//               application. Most of the source code is common to both platforms and this module includes
//               the source for the specific functionality for the backend services.
package org.dimensinfin.eveonline.neocom;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.dimensinfin.eveonline.neocom.conf.GlobalSBPreferencesManager;
import org.dimensinfin.eveonline.neocom.database.NeoComSBDBHelper;
import org.dimensinfin.eveonline.neocom.database.SDESBDBHelper;
import org.dimensinfin.eveonline.neocom.datamngmt.*;
import org.dimensinfin.eveonline.neocom.model.ANeoComEntity;
import org.dimensinfin.eveonline.neocom.services.MarketDataServer;
import org.dimensinfin.eveonline.neocom.services.TimedUpdater;
import org.joda.time.Instant;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

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

// - CLASS IMPLEMENTATION ...................................................................................

/**
 * This is the initial class and loader for the Spring Boot application. It will be used to integrate the
 * different modules and libraries, instantiate the adapters and serve as the base point to integrate the
 * different controllers. Most of the code will be imported and integrated from the depending libraries.
 * @author Adam Antinoo
 */
//@EnableCaching
//@EnableCircuitBreaker
@EnableScheduling
//@EnableAsync
@SpringBootApplication
public class NeoComMicroServiceApplication extends NeoComMicroServiceApplicationSerializers {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = LoggerFactory.getLogger("NeoComMicroServiceApplication");
	public static final boolean MOCK_UP = true;
	
	public static MarketDataServer mdServer = null;
	public static final TimedUpdater timedService = new TimedUpdater();
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
	
	public static boolean validatePilotIdentifierMatch(final String sessionLocator, final Integer identifier) {
		logger.info(">> [NeoComMicroServiceApplication.validatePilotIdentifierMatch]");
		try {
			// Convert the locator to an instance.
			SessionLocator locator = NeoComMicroServiceApplication.modelMapper.map(sessionLocator, SessionLocator.class);
			locator = new SessionLocator()
					.setSessionLocator("-MANUALLY-CREATED-LOCATOR-")
					.setTimeValid(Instant.now().getMillis());
			if ( null == locator ) return false;
			final String contentsSerialized = NeoComMicroServiceApplication.jsonMapper.writeValueAsString(locator);
			
			// First check the time span validity.
			final long now = Instant.now().getMillis();
//			final long nowPlus15 = Instant.now().plus(TimeUnit.MINUTES.toMillis(15)).toInstant().getMillis();
			final long timeLimit = new Instant(locator.getTimeValid()).plus(TimeUnit.MINUTES.toMillis(15)).toInstant().getMillis();
			if ( timeLimit <= now ) return false;
			
			// Then check if the identifiers match.
			final NeoComSession hit = sessionStore.get(locator.getSessionLocator());
			if ( null == hit ) return false;
			if ( hit.getPilotIdentifier() != identifier ) return false;
			
			// All was validated. We can access the data
			return true;
		} catch ( JsonProcessingException e ) {
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
	 * @param args
	 */
	public static void main(final String[] args) throws IOException {
		logger.info(">> [NeoComMicroServiceApplication.main]");
		// Connect the file system to be able to read the assets and other application resources stored externally.
		logger.info("-- [NeoComApp.onCreate]> Connecting the File System to Global...");
		InfinityGlobalDataManager.installFileSystem(new FileSystemSBImplementation(
				System.getenv("appname"))
		);
		
		// Connect the Configuration manager.
		logger.info("-- [NeoComMicroServiceApplication.main]> Connecting the Configuration Manager...");
		InfinityGlobalDataManager.connectConfigurationManager(new GlobalSBConfigurationProvider("properties"));
		
		logger.info("-- [NeoComApp.onCreate]> Connecting the Preferences Manager...");
		InfinityGlobalDataManager.connectPreferencesManager(new GlobalSBPreferencesManager());
		
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
		} catch ( SQLException sqle ) {
			sqle.printStackTrace();
		}
		ANeoComEntity.connectSDEHelper(new GlobalDataManager().getSDEDBHelper());
		
		// Connect the MarketData service.
		logger.info("-- [NeoComMicroServiceApplication.main]> Starting Market Data service...");
		mdServer = new MarketDataServer().start();
		InfinityGlobalDataManager.connectMarketDataManager(mdServer);
		
		// Connect the NeoCom database.
		logger.info("-- [NeoComMicroServiceApplication.main]> Connecting NeoCom private database...");
		try {
			InfinityGlobalDataManager.connectNeoComDBConnector(new NeoComSBDBHelper()
					.setDatabaseType(GlobalDataManager.getResourceString("R.database.neocom.databasetype"))
					.setDatabaseHost(GlobalDataManager.getResourceString("R.database.neocom.databasehost"))
					.setDatabaseName(GlobalDataManager.getResourceString("R.database.neocom.databasename"))
					.setDatabaseUser(GlobalDataManager.getResourceString("R.database.neocom.databaseuser"))
					.setDatabasePassword(GlobalDataManager.getResourceString("R.database.neocom.databasepassword"))
					.setDatabaseOptions(GlobalDataManager.getResourceString("R.database.neocom.databaseoptions"))
					.setDatabaseVersion(GlobalDataManager.getResourceInt("R.database.neocom.databaseversion"))
					.build()
			);
		} catch ( SQLException sqle ) {
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
		if ( GlobalDataManager.getResourceBoolean("R.updater.allowtimer", false) ) {
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
		
		public SessionLocator setSessionLocator(final String sessionLocator) {
			this.sessionLocator = sessionLocator;
			return this;
		}
		
		public SessionLocator setTimeValid(final long timeValid) {
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
		
		public NeoComSessionIdentifier(final NeoComSession session) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
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
}
// - UNUSED CODE ............................................................................................
