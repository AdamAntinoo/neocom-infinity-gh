//  PROJECT:     NeoCom.Microservices (NEOC.MS)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Java 1.8 / SpringBoot-1.3.5 / Angular 5.0
//  DESCRIPTION: This is the SpringBoot MicroServices module to run the backend services to complete the web
//               application based on Angular+SB. This is the web version for the NeoCom Android native
//               application. Most of the source code is common to both platforms and this module includes
//               the source for the specific functionality for the backend services.
package org.dimensinfin.eveonline.neocom.testblock.marketdata;

import java.sql.SQLException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.dimensinfin.eveonline.neocom.conf.SpringBootConfigurationProvider;
import org.dimensinfin.eveonline.neocom.database.SDESBDBHelper;
import org.dimensinfin.eveonline.neocom.datamngmt.manager.GlobalDataManager;
import org.dimensinfin.eveonline.neocom.datamngmt.manager.MarketDataServer;
import org.dimensinfin.eveonline.neocom.enums.EMarketSide;
import org.dimensinfin.eveonline.neocom.market.TrackEntry;
import org.dimensinfin.eveonline.neocom.model.EveItem;

// - CLASS IMPLEMENTATION ...................................................................................
public class MarketDownloaderTestUnit extends MarketDataServer.MarketDataJobDownloadManager {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = LoggerFactory.getLogger("MarketDownloaderTestUnit");
	@BeforeClass
	public static void testOpenAndConnectDatabase() throws SQLException {
		logger.info(">> [MarketDownloaderTestUnit.testOpenAndConnectDatabase]");
		logger.info("-- [MarketDownloaderTestUnit.testOpenAndConnectDatabase]> Connecting the Configuration Manager...");
		GlobalDataManager.connectConfigurationManager(new SpringBootConfigurationProvider("src/test/resources/properties"));
		// Connect the NeoCom database.
//		logger.info("-- [NeoComMicroServiceApplication.main]> Connecting NeoCom private database...");
//		try {
//			GlobalDataManager.connectNeoComDBConnector(new NeoComSBDBHelper()
//					.setDatabaseHost(GlobalDataManager.getResourceString("R.database.neocom.databasehost", "jdbc:mysql://localhost:3306"))
//					.setDatabaseName(GlobalDataManager.getResourceString("R.database.neocom.databasename","neocom"))
//					.setDatabaseUser("NEOCOM")
//					.setDatabasePassword("01.Alpha")
//					.setDatabaseVersion(GlobalDataManager.getResourceInt("R.database.neocom.databaseversion"))
//					.build()
//			);
		// Connect the SDE database.
		logger.info("-- [MarketDownloaderTestUnit.testOpenAndConnectDatabase]> Connecting SDE database...");
		GlobalDataManager.connectSDEDBConnector(new SDESBDBHelper()
				.setDatabaseSchema(GlobalDataManager.getResourceString("R.database.sdedatabase.databaseschema"))
				.setDatabasePath(GlobalDataManager.getResourceString("R.database.sdedatabase.databasepath"))
				.setDatabaseName(GlobalDataManager.getResourceString("R.database.sdedatabase.databasename"))
				.build()
		);
		// Connect the MarketData service.
//			logger.info("-- [NeoComMicroServiceApplication.main]> Starting Market Data service...");
//			GlobalDataManager.setMarketDataManager(new MarketDataServer().start());
//		} catch (SQLException sqle) {
//			sqle.printStackTrace();
//		}

		// Check the initialization counting the jobs to zero.
//		Assert.assertEquals(testServer., schema + ":" + dbpath + dbname);
		// Check the database is open and has a valid connection.
//		Assert.assertTrue(sdeHelper.databaseIsValid());
		logger.info("<< [MarketDownloaderTestUnit.testOpenAndConnectDatabase]");
	}

	// - F I E L D - S E C T I O N ............................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public MarketDownloaderTestUnit() {
		super(1);
		logger.info(">< [MarketDownloaderTestUnit.<constructor>]> Initializing service with 1 thread.");
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	@Test
	public void testQueueSizeClear() {
		logger.info(">> [MarketDownloaderTestUnit.testQueueSizeClear]");
		runningJobs.clear();
		Assert.assertEquals(countRunningJobs(), 0);
		logger.info("<< [MarketDownloaderTestUnit.testQueueSizeClear]");
	}

//	@Test
	public void testReadJson() throws JSONException {
		String jsonStr = this.readJsonData(34);
		Assert.assertNotNull("-> Validating the result is not null...", jsonStr);
		Assert.assertTrue("-> Validating the size of the result...", jsonStr.length() > 10);
		// Validate the content is a Json serialized and not any other type of content.
		JSONArray jsonObj = new JSONArray(jsonStr);
	}

	@Test
	public void testEMDParsing() {
		logger.info(">> [MarketDownloaderTestUnit.testEMDParsing]");
		final EveItem item = GlobalDataManager.searchItem4Id(34);
		List<TrackEntry> marketEntries = parseMarketDataEMD(item.getName(), EMarketSide.SELLER);
		Assert.assertTrue("-> Validating the market entries exist.", marketEntries.size() > 0);
		marketEntries = parseMarketDataEMD(item.getName(), EMarketSide.BUYER);
		Assert.assertTrue("-> Validating the market entries exist.", marketEntries.size() > 0);
		logger.info("<< [MarketDownloaderTestUnit.testEMDParsing]");
	}

//	@Test
	public void testECParsing() {
		logger.info(">> [MarketDownloaderTestUnit.testECParsing]");
		List<TrackEntry> marketEntries = parseMarketDataEC(34, EMarketSide.SELLER);
		Assert.assertTrue("-> Validating the market entries exist.", marketEntries.size() > 0);
		marketEntries = parseMarketDataEC(34, EMarketSide.BUYER);
		Assert.assertTrue("-> Validating the market entries exist.", marketEntries.size() > 0);
		logger.info("<< [MarketDownloaderTestUnit.testECParsing]");
	}
}
