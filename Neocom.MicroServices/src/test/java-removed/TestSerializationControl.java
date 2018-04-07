//  PROJECT:     NeoCom.Microservices (NEOC.MS)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Java 1.8 / SpringBoot-1.3.5 / Angular 5.0
//  DESCRIPTION: This is the SpringBoot MicroServices module to run the backend services to complete the web
//               application based on Angular+SB. This is the web version for the NeoCom Android native
//               application. Most of the source code is common to both platforms and this module includes
//               the source for the specific functionalityfor the backend services.
package org.dimensinfin.eveonline.neocom.test;

import java.sql.SQLException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.dimensinfin.eveonline.neocom.conf.SpringBootConfigurationProvider;
import org.dimensinfin.eveonline.neocom.connector.ICCPDatabaseConnector;
import org.dimensinfin.eveonline.neocom.connector.ICacheConnector;
import org.dimensinfin.eveonline.neocom.connector.INeoComModelDatabase;
import org.dimensinfin.eveonline.neocom.database.NeoComSBDBHelper;
import org.dimensinfin.eveonline.neocom.database.SDESBDBHelper;
import org.dimensinfin.eveonline.neocom.datamngmt.manager.GlobalDataManager;
import org.dimensinfin.eveonline.neocom.model.ExtendedLocation;
import org.dimensinfin.eveonline.neocom.model.NeoComAsset;

import org.joda.time.Instant;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Test unit for the data serialization.
 *
 * @author Adam Antinoo
 */
// - CLASS IMPLEMENTATION ...................................................................................
public class TestSerializationControl /*implements INeoComMSConnector*/ {
	// - S T A T I C - S E C T I O N ..........................................................................

	private static Logger logger = LoggerFactory.getLogger("TestSerializationControl.java");
	public static final String APPLICATION_NAME = "NeoComMicroServiceApplication";
	//	private static NeoComMSConnector _connector = null;
	private static final int pilotid = 92223647;
	private static final long locationidnumber = 60014089;

	private static ExtendedLocation newloc = null;

	@BeforeClass
	public static void setUpBeforeClass () throws Exception {
// Connect the Configuration manager.
		GlobalDataManager.connectConfigurationManager(new SpringBootConfigurationProvider("src/test/resources/properties"));

		// Connect the NeoCom database.
		try {
			GlobalDataManager.connectNeoComDBConnector(new NeoComSBDBHelper()
					.setDatabaseHost(GlobalDataManager
							.getResourceString("R.database.neocom.databasehost", "jdbc:mysql://localhost:3306"))
					.setDatabaseName("neocom")
					.setDatabaseUser("NEOCOMTEST")
					.setDatabasePassword("01.Alpha")
					.setDatabaseVersion(1)
					.build()
			);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}

		// Connect the SDE database.
		try {
			GlobalDataManager.connectSDEDBConnector(new SDESBDBHelper()
					.setDatabaseSchema("jdbc:sqlite")
					.setDatabasePath("src/main/resources/")
					.setDatabaseName("sde.db")
					.build()
			);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
//
//
//
//
//
//		// Connect the code the the runtime librearies.
//
//		_connector = new NeoComMSConnector(new TestSerializationControl());
//		EveLocation location = NeoComMSConnector.getSingleton().getCCPDBConnector().searchLocationbyID(locationidnumber);
//		// Convert the Location to a new Extended Location with the new Contents Manager.
//		newloc = new ExtendedLocation(pilotid, location);
//		newloc.setContentManager(new DefaultAssetsContentManager(newloc));
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

//	@Override
//	public ICacheConnector getCacheConnector () {
//		if (null == cacheConnector) cacheConnector = new MicroServicesCacheConnector();
//		return cacheConnector;
//	}
//
//	@Override
//	public ICCPDatabaseConnector getCCPDBConnector () {
//		if (null == dbCCPConnector) {
//			dbCCPConnector = new CCPDatabaseConnector();
//		}
//		return dbCCPConnector;
//	}
//
//	@Override
//	public INeoComModelDatabase getDBConnector () {
//		if (null == dbNeocomConnector) {
//			String dblocation = R.getResourceString("R.string.appdatabasepath");
//			String dbname = R.getResourceString("R.string.appdatabasefilename");
//			String dbversion = R.getResourceString("R.string.databaseversion");
//			dbNeocomConnector = new SpringDatabaseConnector(dblocation, dbname, dbversion);
//			dbNeocomConnector.loadSeedData();
//		}
//		return dbNeocomConnector;
//	}
//
//	@Override
//	public INeoComModelStore getModelStore () {
//		return AppModelStore.getSingleton();
//	}

	@Test
	public void testSerializationChecks () {
		try {
			List<NeoComAsset> contents = newloc.downloadContents();

			// Use my own serialization control to return the data to generate exactly what I want.
			ObjectMapper mapper = new ObjectMapper();

			final String contentsSerialized = mapper.writeValueAsString(contents);
			logger.info(contentsSerialized);
		} catch (JsonProcessingException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
	}
}

// - UNUSED CODE ............................................................................................