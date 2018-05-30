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
import java.sql.SQLException;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.dimensinfin.eveonline.neocom.database.NeoComSBDBHelper;
import org.dimensinfin.eveonline.neocom.database.SDESBDBHelper;
import org.dimensinfin.eveonline.neocom.datamngmt.ESINetworkManager;
import org.dimensinfin.eveonline.neocom.datamngmt.FileSystemSBImplementation;
import org.dimensinfin.eveonline.neocom.datamngmt.GlobalDataManager;
import org.dimensinfin.eveonline.neocom.datamngmt.GlobalSBConfigurationProvider;
import org.dimensinfin.eveonline.neocom.datamngmt.InfinityGlobalDataManager;
import org.dimensinfin.eveonline.neocom.datamngmt.MarketDataServer;
import org.dimensinfin.eveonline.neocom.model.ANeoComEntity;

/**
 * @author Adam Antinoo
 */
// - CLASS IMPLEMENTATION ...................................................................................
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FullInitializationTestBase {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = LoggerFactory.getLogger("FullInitializationTestBase");

	@BeforeClass
	public static void before01FullApplicationInitialization() throws SQLException, IOException {
		logger.info(">> [FullInitializationTestBase.before01FullApplicationInitialization]");

		// Connect the file system to be able to read the assets and other application resources stored externally.
		logger.info("-- [FullInitializationTestBase.before01FullApplicationInitialization]> Connecting the File System to Global...");
		InfinityGlobalDataManager.installFileSystem(new FileSystemSBImplementation(
				System.getenv("appname"))
		);

		// Connect the Configuration manager.
		logger.info("-- [FullInitializationTestBase.before01FullApplicationInitialization]> Connecting the Configuration Manager...");
		// TODO - Checks that the configuration manager runs with other properties than the production.
		InfinityGlobalDataManager.connectConfigurationManager(new GlobalSBConfigurationProvider("properties"));

		// Initialize the Model with the current global instance.
		logger.info("-- [FullInitializationTestBase.before01CreateApplicationEnvironment]> Connecting Global to Model...");
		ANeoComEntity.connectGlobal(new InfinityGlobalDataManager());

		// Initializing the ESI api network controller.
		ESINetworkManager.initialize();

		// Connect the SDE database.
		logger.info("-- [FullInitializationTestBase.before01FullApplicationInitialization]> Connecting SDE database...");
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
		logger.info("-- [FullInitializationTestBase.before01FullApplicationInitialization]> Starting Market Data service...");
		final MarketDataServer mdServer = new MarketDataServer().start();
		InfinityGlobalDataManager.setMarketDataManager(mdServer);

		// Connect the NeoCom database.
		logger.info("-- [FullInitializationTestBase.before01FullApplicationInitialization]> Connecting NeoCom private database...");
//		try {
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
//		} catch (SQLException sqle) {
//			sqle.printStackTrace();
//		}

		// Load the Locations cache to speed up the Citadel and Outpost search.
		logger.info("-- [NeoComMicroServiceApplication.main]> Read Locations data cache...");
		InfinityGlobalDataManager.readLocationsDataCache();

//		// Connect the Timed Upgrade scan.
//		logger.info("-- [NeoComMicroServiceApplication.main]> Connecting the background timed download scanner...");
//		timedService = new TimedUpdater();

		logger.info("<< [FullInitializationTestBase.before01FullApplicationInitialization]");
	}

	// - F I E L D - S E C T I O N ............................................................................

	// - M E T H O D - S E C T I O N ..........................................................................
}

// - UNUSED CODE ............................................................................................
//[01]
