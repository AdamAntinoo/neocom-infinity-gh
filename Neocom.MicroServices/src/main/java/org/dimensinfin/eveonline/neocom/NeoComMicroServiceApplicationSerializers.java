//  PROJECT:     NeoCom.Microservices (NEOC.MS)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Java 1.8 / SpringBoot-1.3.5 / Angular 5.0
//  DESCRIPTION: This is the SpringBoot MicroServices module to run the backend services to complete the web
//               application based on Angular+SB. This is the web version for the NeoCom Android native
//               application. Most of the source code is common to both platforms and this module includes
//               the source for the specific functionality for the backend services.
package org.dimensinfin.eveonline.neocom;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.joda.JodaModule;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.dimensinfin.eveonline.neocom.controller.ManufactureResourcesController;
import org.dimensinfin.eveonline.neocom.database.entity.Property;
import org.dimensinfin.eveonline.neocom.industry.EveTask;
import org.dimensinfin.eveonline.neocom.industry.FacetedAssetContainer;
import org.dimensinfin.eveonline.neocom.industry.Resource;
import org.dimensinfin.eveonline.neocom.model.EveItem;
import org.dimensinfin.eveonline.neocom.model.EveLocation;
import org.dimensinfin.eveonline.neocom.model.NeoComAsset;
import org.dimensinfin.eveonline.neocom.model.PilotV2;
import org.dimensinfin.eveonline.neocom.planetary.ProcessingActionV2;

/**
 * @author Adam Antinoo
 */
// - CLASS IMPLEMENTATION ...................................................................................
public class NeoComMicroServiceApplicationSerializers {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = LoggerFactory.getLogger("NeoComMicroServiceApplicationSerializers");

	// --- S E R I A L I Z E R S   S E C T I O N
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
		neocomSerializerModule.addSerializer(EveItem.class, new EveItemSerializer());
		neocomSerializerModule.addSerializer(ProcessingActionV2.class, new ProcessingActionSerializer());
		jsonMapper.registerModule(neocomSerializerModule);
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

	// - CLASS IMPLEMENTATION ...................................................................................
	public static class EveItemSerializer extends JsonSerializer<EveItem> {
		// - F I E L D - S E C T I O N ............................................................................

		// - M E T H O D - S E C T I O N ..........................................................................
		@Override
		public void serialize( final EveItem value, final JsonGenerator jgen, final SerializerProvider provider )
				throws IOException, JsonProcessingException {
			jgen.writeStartObject();
			jgen.writeStringField("jsonClass", value.getJsonClass());
			jgen.writeNumberField("typeId", value.getTypeId());
			jgen.writeStringField("name", value.getName());
			jgen.writeNumberField("categoryId", value.getCategoryId());
			jgen.writeStringField("categoryName", value.getCategoryName());
			jgen.writeNumberField("groupId", value.getGroupId());
			jgen.writeStringField("groupName", value.getGroupName());
			jgen.writeNumberField("baseprice", value.getBaseprice());
			jgen.writeNumberField("price", value.getPrice());
			jgen.writeStringField("tech", value.getTech());
			jgen.writeNumberField("volume", value.getVolume());
			jgen.writeStringField("industryGroup", value.getIndustryGroup().name());
			jgen.writeStringField("hullGroup", value.getHullGroup());
			try {
				jgen.writeObjectField("lowestSellerPrice", value.getLowestSellerPrice());
			} catch (ExecutionException e) {
			} catch (InterruptedException e) {
			}
			try {
				jgen.writeObjectField("highestBuyerPrice", value.getHighestBuyerPrice());
			} catch (ExecutionException e) {
			} catch (InterruptedException e) {
			}
			jgen.writeEndObject();
		}
	}
	// ..........................................................................................................

	// - CLASS IMPLEMENTATION ...................................................................................
	public static class ProcessingActionSerializer extends JsonSerializer<ProcessingActionV2> {
		// - F I E L D - S E C T I O N ............................................................................

		// - M E T H O D - S E C T I O N ..........................................................................
		@Override
		public void serialize( final ProcessingActionV2 value, final JsonGenerator jgen, final SerializerProvider provider )
				throws IOException, JsonProcessingException {
			jgen.writeStartObject();
			jgen.writeStringField("jsonClass", value.getJsonClass());
			jgen.writeNumberField("targetId", value.getTargetId());
			jgen.writeObjectField("targetItem", value.targetItem);
			jgen.writeNumberField("outputQty", value.getQuantity());
			jgen.writeObjectField("resources", value.getLimitedActionResults());
			jgen.writeObjectField("inputList", value.getInputs());
			jgen.writeObjectField("output", value.getOutput());
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


}

// - UNUSED CODE ............................................................................................
//[01]