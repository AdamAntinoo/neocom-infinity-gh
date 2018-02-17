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
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.dimensinfin.eveonline.neocom.conf.SpringBootConfigurationProvider;
import org.dimensinfin.eveonline.neocom.database.SDESBDBHelper;
import org.dimensinfin.eveonline.neocom.datamngmt.manager.GlobalDataManager;
import org.dimensinfin.eveonline.neocom.datamngmt.manager.MarketDataServer;
import org.dimensinfin.eveonline.neocom.enums.EMarketSide;
import org.dimensinfin.eveonline.neocom.market.MarketDataEntry;
import org.dimensinfin.eveonline.neocom.market.MarketDataSet;
import org.dimensinfin.eveonline.neocom.market.TrackEntry;
import org.dimensinfin.eveonline.neocom.model.EveItem;

// - CLASS IMPLEMENTATION ...................................................................................
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MarketDownloaderTestUnit extends MarketDataServer.MarketDataJobDownloadManager {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = LoggerFactory.getLogger("MarketDownloaderTestUnit");
	private static MarketDataServer server = null;

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
		logger.info("-- [NeoComMicroServiceApplication.main]> Starting Market Data service...");
		server = new MarketDataServer().start();
		GlobalDataManager.setMarketDataManager(server);
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
	public void test01GetModuleLink() {
		logger.info(">> [MarketDownloaderTestUnit.test01GetModuleLink]");
		final String name = getModuleLink("Tritanium", "SELL");
		Assert.assertEquals("-> Validating the Module Link Name...", name.toLowerCase(), "http://eve-marketdata.com/price_check" +
				".php?type=SELL&region_id=-1&type_name_header=Tritanium".toLowerCase());
		logger.info("<< [MarketDownloaderTestUnit.test01GetModuleLink]");
	}

	/**
	 * Some tests only have to be run depending on the configuration. For example the eve-central server is down for a long time
	 * so I have to skip those tests until it is running again and I can activate again that flag.
	 *
	 * @throws JSONException
	 */
	@Test
	public void test02ReadJsonData() throws JSONException {
		logger.info(">> [MarketDownloaderTestUnit.test02ReadJsonData]");
		if (GlobalDataManager.getResourceBoolean("R.cache.marketdata.provider.activateEC", false)) {
			logger.info(">> [MarketDownloaderTestUnit.test02ReadJsonData]> Activated");
			String jsonStr = this.readJsonData(34);
			Assert.assertNotNull("-> Validating the result is not null...", jsonStr);
			Assert.assertTrue("-> Validating the size of the result...", jsonStr.length() > 10);
			// Validate the content is a Json serialized and not any other type of content.
			JSONArray jsonObj = new JSONArray(jsonStr);
		}
		logger.info("<< [MarketDownloaderTestUnit.test02ReadJsonData]");
	}

	@Test
	public void test03ParseMarketDataEC() {
		logger.info(">> [MarketDownloaderTestUnit.test03ParseMarketDataEC]");
		if (GlobalDataManager.getResourceBoolean("R.cache.marketdata.provider.activateEC", false)) {
			logger.info(">> [MarketDownloaderTestUnit.test03ParseMarketDataEC]> Activated");
			List<TrackEntry> marketEntries = parseMarketDataEC(34, EMarketSide.SELLER);
			Assert.assertTrue("-> Validating the market entries exist.", marketEntries.size() > 0);
			marketEntries = parseMarketDataEC(34, EMarketSide.BUYER);
			Assert.assertTrue("-> Validating the market entries exist.", marketEntries.size() > 0);
		}
		logger.info("<< [MarketDownloaderTestUnit.test03ParseMarketDataEC]");
	}

	@Test
	public void test04ParseMarketDataEMD() {
		logger.info(">> [MarketDownloaderTestUnit.test04ParseMarketDataEMD]");
		final EveItem item = GlobalDataManager.searchItem4Id(34);
		List<TrackEntry> marketEntries = parseMarketDataEMD(item.getName(), EMarketSide.SELLER);
		Assert.assertTrue("-> Validating the market entries exist.", marketEntries.size() > 0);
		marketEntries = parseMarketDataEMD(item.getName(), EMarketSide.BUYER);
		Assert.assertTrue("-> Validating the market entries exist.", marketEntries.size() > 0);
		logger.info("<< [MarketDownloaderTestUnit.test04ParseMarketDataEMD]");
	}
	@Test
	public void test05GenerateMarketDataJobReference() {
		logger.info(">> [MarketDownloaderTestUnit.test05GenerateMarketDataJobReference]");
		final String name = generateMarketDataJobReference(34);
		Assert.assertEquals("-> Validating the Download Job Reference...", name, "MDJ:34:");
		logger.info("<< [MarketDownloaderTestUnit.test05GenerateMarketDataJobReference]");
	}

	@Test
	public void test06FilterStations() {
		logger.info(">> [MarketDownloaderTestUnit.test06FilterStations]");
		// Get the list of defined markets.
		final List<String> markets = getMarketHubs();
		TrackEntry entry = new TrackEntry()
				.setPrice("100.0")
				.setQty("10.0")
				.setSecurity("1.0")
				.setStationName("0.7 Kador - Romi");
		final boolean found = filterStations(entry, markets);
		Assert.assertEquals("-> Validating the found on the Station List...", true, found);
		entry = new TrackEntry()
				.setPrice("100.0")
				.setQty("10.0")
				.setSecurity("1.0")
				.setStationName("0.7 Kador-Romi");
		final boolean notfound = filterStations(entry, markets);
		Assert.assertEquals("-> Validating the not found on the Station List...", false, notfound);
		logger.info("<< [MarketDownloaderTestUnit.test06FilterStations]");
	}

	@Test
	public void test07CountRunningJobs() {
		logger.info(">> [MarketDownloaderTestUnit.test07CountRunningJobs]");
		// Clear the queue and be sure the count is valid.
		runningJobs.clear();
		Assert.assertEquals("-> Checking that the number is zero...", 0, countRunningJobs());

		// Add some requests for known modules.
		addMarketDataRequest(34);
		addMarketDataRequest(35);
		addMarketDataRequest(36);
		Assert.assertEquals("-> Checking that the number is 3...", 3, countRunningJobs());
		Assert.assertEquals("-> Validate the state of the public counter...", 3, marketJobCounter);
		logger.info("<< [MarketDownloaderTestUnit.test07CountRunningJobs]");
	}

	@Test
	public void test08LaunchDownloadJob() {
		logger.info(">> [MarketDownloaderTestUnit.test08LaunchDownloadJob]");
		// Check initial state of the cached values.
		runningJobs.clear();
		final int initialCounter = countRunningJobs();
		Future<?> job = launchDownloadJob(37);
		Assert.assertFalse("-> Checking the job is pending run...", job.isDone());

		// Wait for job to complete.
		boolean completed = false;
		final MarketDataSet singleReference = server.searchMarketData(37, EMarketSide.SELLER);
		while (!completed) {
			try {
				Thread.sleep(TimeUnit.SECONDS.toMillis(5));
			} catch (InterruptedException ie) {
			}
			logger.info(">> [MarketDownloaderTestUnit.test08LaunchDownloadJob]> Waiting job completion...");
			if (job.isDone()) {
				completed = true;
				logger.info(">> [MarketDownloaderTestUnit.test08LaunchDownloadJob]> Job completed. Performing verifications");
				// Check that the data is stored on the cache.
				Assert.assertTrue("-> Checking the job is completed...", job.isDone());
//				Assert.assertEquals("-> Checking the job is completed...", countRunningJobs(), 0);
				final List<MarketDataEntry> hubData = singleReference.getDataOnMarketHub();
				Assert.assertNotNull("-> Verifying the hub data is not null...", hubData);
				Assert.assertTrue("-> Checking the data on cache is valid...", hubData.size() > 0);
//			} else {
//				Assert.assertFalse("-> Checking the data on cache is valid...", hubData.size() > 0);
			}
		}
		logger.info("<< [MarketDownloaderTestUnit.test08LaunchDownloadJob]");
	}
}
