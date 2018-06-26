//  PROJECT:     NeoCom.DataManagement(NEOC.DTM)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Java 1.8 Library.
//  DESCRIPTION: NeoCom project library that comes from the old Models package but that includes much more
//               functionality than the model definitions for the Eve Online NeoCom application.
//               If now defines the pure java code for all the repositories, caches and managers that do
//               not have an specific Android implementation serving as a code base for generic platform
//               development. The architecture model has also changed to a better singleton/static
//               implementation that reduces dependencies and allows separate use of the modules. Still
//               there should be some initialization/configuration code to connect the new library to the
//               runtime implementation provided by the Application.
package org.dimensinfin.eveonline.neocom.testblock.manager;

import org.dimensinfin.eveonline.neocom.database.NeoComSBDBHelper;
import org.dimensinfin.eveonline.neocom.database.SDESBDBHelper;
import org.dimensinfin.eveonline.neocom.datamngmt.GlobalDataManager;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

/**
 * @author Adam Antinoo
 */
// - CLASS IMPLEMENTATION ...................................................................................
public class AssetsManagerTestUnit {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = LoggerFactory.getLogger("AssetsManagerTestUnit");
	
	//	@BeforeClass
	public static void testOpenAndConnectDatabase() throws SQLException {
		logger.info(">> [NeoComDatabaseTestUnit.testOpenAndConnectDatabase]");
		// Connect the SDE database.
		logger.info("-- [NeoComDatabaseTestUnit.testOpenAndConnectDatabase]> Connecting SDE database...");
		GlobalDataManager.connectSDEDBConnector(new SDESBDBHelper()
				.setDatabaseSchema(GlobalDataManager.getResourceString("R.database.sdedatabase.databaseschema"))
				.setDatabasePath(GlobalDataManager.getResourceString("R.database.sdedatabase.databasepath"))
				.setDatabaseName(GlobalDataManager.getResourceString("R.database.sdedatabase.databasename"))
				.build()
		);
		// Connect the NeoCom database.
		logger.info("-- [NeoComDatabaseTestUnit.testOpenAndConnectDatabase]> Connecting NeoCom private database...");
		GlobalDataManager.connectNeoComDBConnector(new NeoComSBDBHelper()
				.setDatabaseHost(GlobalDataManager
						.getResourceString("R.database.neocom.databasehost", "jdbc:mysql://localhost:3306"))
				.setDatabaseName("neocom")
				.setDatabaseUser("NEOCOM")
				.setDatabasePassword("01.Alpha")
				.setDatabaseVersion(GlobalDataManager.getResourceInt("R.database.neocom.databaseversion"))
				.build()
		);
		// Check the connection descriptor.
		Assert.assertEquals("-> Validating the database is valid..."
				, new GlobalDataManager().getNeocomDBHelper().isDatabaseValid()
				, true);
		// Check the database is open and has a valid connection.
		Assert.assertEquals("-> Validating the database is open..."
				, new GlobalDataManager().getNeocomDBHelper().isOpen()
				, true);
		logger.info("<< [NeoComDatabaseTestUnit.testOpenAndConnectDatabase]");
	}
	
	// - F I E L D - S E C T I O N ............................................................................
	
	// - C O N S T R U C T O R - S E C T I O N ................................................................
	
	// - M E T H O D - S E C T I O N ..........................................................................
//	@Test
	public void test01GetAssets4Type() {
//		logger.info(">> [AssetsManagerTestUnit.test01GetAssets4Type]");
//		// Get the list of character assets.
//		final int credentialIdentifier = 92002067;
//		final AssetsManager assetsManager = GlobalDataManager.getAssetsManager(DataManagementModelStore.activateCredential(credentialIdentifier),
//				true);
//		final List<NeoComAsset> available = assetsManager.getAssets4Type(578);
//		logger.info("<< [AssetsManagerTestUnit.test01GetAssets4Type]");
	}
}

// - UNUSED CODE ............................................................................................
//[01]
