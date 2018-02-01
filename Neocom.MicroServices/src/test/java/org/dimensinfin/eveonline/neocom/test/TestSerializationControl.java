//	PROJECT:      Neocom.Microservices (NEOC-MS)
//	AUTHORS:      Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:	SpringBoot-MS-Java 1.8.
//	DESCRIPTION:	This is the integration project for all the web server pieces. This is the launcher for
//								the SpringBoot+MicroServices+Angular unified web application.
package org.dimensinfin.eveonline.neocom.test;

import java.io.IOException;
import java.util.List;

import org.dimensinfin.eveonline.neocom.connector.AppModelStore;
import org.dimensinfin.eveonline.neocom.connector.CCPDatabaseConnector;
import org.dimensinfin.eveonline.neocom.connector.ICCPDatabaseConnector;
import org.dimensinfin.eveonline.neocom.connector.ICacheConnector;
import org.dimensinfin.eveonline.neocom.connector.INeoComMSConnector;
import org.dimensinfin.eveonline.neocom.connector.INeoComModelDatabase;
import org.dimensinfin.eveonline.neocom.connector.MicroServicesCacheConnector;
import org.dimensinfin.eveonline.neocom.connector.NeoComMSConnector;
import org.dimensinfin.eveonline.neocom.connector.SpringDatabaseConnector;
import org.dimensinfin.eveonline.neocom.constant.R;
import org.dimensinfin.eveonline.neocom.interfaces.INeoComModelStore;
import org.dimensinfin.eveonline.neocom.manager.DefaultAssetsContentManager;
import org.dimensinfin.eveonline.neocom.model.EveLocation;
import org.dimensinfin.eveonline.neocom.model.ExtendedLocation;
import org.dimensinfin.eveonline.neocom.model.NeoComAsset;
import org.dimensinfin.eveonline.neocom.model.Ship;
import org.joda.time.Instant;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

// - CLASS IMPLEMENTATION ...................................................................................
/**
 * @author Adam
 * 
 */
public class TestSerializationControl implements INeoComMSConnector {
	// - S T A T I C - S E C T I O N ..........................................................................
	public static class ShipSerializer extends JsonSerializer<Ship> {

		@Override
		public void serialize ( final Ship value, final JsonGenerator jgen, final SerializerProvider provider )
						throws IOException, JsonProcessingException {
			jgen.writeStartObject();
			jgen.writeStringField("jsonClass", value.getJsonClass());
			jgen.writeNumberField("assetId", value.getAssetID());
			jgen.writeNumberField("typeId", value.getTypeID());
			jgen.writeNumberField("ownerId", value.getOwnerID());
			jgen.writeStringField("name", value.getItemName());
			jgen.writeStringField("category", value.getCategory());
			jgen.writeStringField("groupName", value.getGroupName());
			jgen.writeStringField("tech", value.getTech());
			jgen.writeStringField("userLabel", value.getUserLabel());
			jgen.writeNumberField("price", value.getItem().getPrice());
			jgen.writeNumberField("highesBuyerPrice", value.getItem().getHighestBuyerPrice().getPrice());
			jgen.writeNumberField("lowerSellerPrice", value.getItem().getLowestSellerPrice().getPrice());
			jgen.writeObjectField("item", value.getItem());
			jgen.writeEndObject();
		}
	}

	private static Logger logger = LoggerFactory.getLogger("TestSerializationControl.java");
	public static final String APPLICATION_NAME = "NeocomMicroServiceApplication";
	private static NeoComMSConnector _connector = null;
	private static final int pilotid = 92223647;
	private static final long locationidnumber = 60014089;

	private static ExtendedLocation newloc = null;

	@BeforeClass
	public static void setUpBeforeClass () throws Exception {
		// Connect the code the the runtime librearies.
		_connector = new NeoComMSConnector(new TestSerializationControl());
		EveLocation location = NeoComMSConnector.getSingleton().getCCPDBConnector().searchLocationbyID(locationidnumber);
		// Convert the Location to a new Extended Location with the new Contents Manager.
		newloc = new ExtendedLocation(pilotid, location);
		newloc.setContentManager(new DefaultAssetsContentManager(newloc));
	}

	// - F I E L D - S E C T I O N ............................................................................
	private final Instant chrono = null;
	private INeoComModelDatabase dbNeocomConnector = null;
	private ICCPDatabaseConnector dbCCPConnector = null;

	private ICacheConnector cacheConnector = null;

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public TestSerializationControl () {
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	public String getAppName () {
		return APPLICATION_NAME;
	}

	@Override
	public ICacheConnector getCacheConnector () {
		if (null == cacheConnector) cacheConnector = new MicroServicesCacheConnector();
		return cacheConnector;
	}

	@Override
	public ICCPDatabaseConnector getCCPDBConnector () {
		if (null == dbCCPConnector) {
			dbCCPConnector = new CCPDatabaseConnector();
		}
		return dbCCPConnector;
	}

	@Override
	public INeoComModelDatabase getDBConnector () {
		if (null == dbNeocomConnector) {
			String dblocation = R.getResourceString("R.string.appdatabasepath");
			String dbname = R.getResourceString("R.string.appdatabasefilename");
			String dbversion = R.getResourceString("R.string.databaseversion");
			dbNeocomConnector = new SpringDatabaseConnector(dblocation, dbname, dbversion);
			dbNeocomConnector.loadSeedData();
		}
		return dbNeocomConnector;
	}

	@Override
	public INeoComModelStore getModelStore () {
		return AppModelStore.getSingleton();
	}

	@Test
	public void testSerializationChecks () {
		try {
			List<NeoComAsset> contents = newloc.downloadContents();

			// Use my own serialization control to return the data to generate exactly what I want.
			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			// create a custom serializer module
			SimpleModule customSerializerModule = new SimpleModule();
			// add serializer for the Compensation class
			customSerializerModule.addSerializer(Ship.class, new ShipSerializer());
			// register the serializer module
			mapper.registerModule(customSerializerModule);

			final String contentsSerialized = mapper.writeValueAsString(contents);
			logger.info(contentsSerialized);
		} catch (JsonProcessingException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
	}
}

// - UNUSED CODE ............................................................................................
