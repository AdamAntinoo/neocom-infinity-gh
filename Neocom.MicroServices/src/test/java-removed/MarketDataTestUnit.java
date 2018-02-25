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
import java.util.Vector;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.dimensinfin.eveonline.neocom.database.ISDEDBHelper;
import org.dimensinfin.eveonline.neocom.database.SDESBDBHelper;
import org.dimensinfin.eveonline.neocom.datamngmt.manager.GlobalDataManager;
import org.dimensinfin.eveonline.neocom.datamngmt.manager.MarketDataServer;
import org.dimensinfin.eveonline.neocom.enums.EMarketSide;
import org.dimensinfin.eveonline.neocom.market.MarketDataSet;
import org.dimensinfin.eveonline.neocom.market.TrackEntry;
import org.dimensinfin.eveonline.neocom.model.EveItem;

// - CLASS IMPLEMENTATION ...................................................................................
public class MarketDataTestUnit extends MarketDataServer {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = LoggerFactory.getLogger("MarketDataTestUnit");
//	private static  MarketDataServer testServer=null;
//
//	@BeforeClass
//	public static void testOpenAndConnectDatabase() throws SQLException {
//		// Connect the SDE database.
//		logger.info("-- [NeoComMicroServiceApplication.main]> Starting Market Data service...");
//		 testServer = new MarketDataServer().start();
//		GlobalDataManager.setMarketDataManager(testServer);
//
//		// Check the initialization counting the jobs to zero.
//		Assert.assertEquals(testServer., schema + ":" + dbpath + dbname);
//		// Check the database is open and has a valid connection.
//		Assert.assertTrue(sdeHelper.databaseIsValid());
//	}

	// - F I E L D - S E C T I O N ............................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................

	// - M E T H O D - S E C T I O N ..........................................................................
	@Test
	public void testMarketDataStart() {
		logger.info(">> [MarketDataTestUnit.testMarketDataStart]> Starting Market Data service...");
		this.start();
		// Check the initialization counting the jobs to zero.
		Assert.assertEquals(this.downloadManager.countRunningJobs(), 0);
		logger.info("<< [MarketDataTestUnit.testMarketDataStart]");
	}
//	@Test
//	public void testEMDParsing(){
//		logger.info(">> [MarketDataTestUnit.testEMDParsing]");
//		final EveItem item = GlobalDataManager.searchItem4Id(34);
//		Vector<TrackEntry> marketEntries = downloadManager.parseMarketDataEMD(item.getName(), EMarketSide.SELLER);
//		if (marketEntries.size() < 1) {
//			marketEntries = downloadManager.parseMarketDataEC(item.getTypeId(), EMarketSide.SELLER);
//		}
//		logger.info("<< [MarketDataTestUnit.testEMDParsing]");
//	}
//	@Test
//	public void testSearchNewData(){
//		logger.info(">> [MarketDataTestUnit.testSearchNewData]");
//		clear();
//		final MarketDataSet target = searchMarketData(34, EMarketSide.BUYER);
//				Assert.assertNotNull(target);
//		Assert.assertEquals(this.downloadManager.countRunningJobs(), 1);
//
//		logger.info("<< [MarketDataTestUnit.testSearchNewData]");
//	}
}
