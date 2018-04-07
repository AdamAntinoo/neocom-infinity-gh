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
package org.dimensinfin.eveonline.neocom.testblock.esinetwork;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.joda.time.Instant;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.dimensinfin.eveonline.neocom.database.NeoComSBDBHelper;
import org.dimensinfin.eveonline.neocom.database.SDESBDBHelper;
import org.dimensinfin.eveonline.neocom.datamngmt.GlobalDataManager;
import org.dimensinfin.eveonline.neocom.model.AllianceV1;

/**
 * @author Adam Antinoo
 */
// - CLASS IMPLEMENTATION ...................................................................................
	@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ESINetworkDownloadTestUnit {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = LoggerFactory.getLogger("ESINetworkDownloadTestUnit");

	@BeforeClass
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
				, GlobalDataManager.getNeocomDBHelper().isDatabaseValid()
				, true);
		// Check the database is open and has a valid connection.
		Assert.assertEquals("-> Validating the database is open..."
				, GlobalDataManager.getNeocomDBHelper().isOpen()
				, true);
		logger.info("<< [NeoComDatabaseTestUnit.testOpenAndConnectDatabase]");
	}

	// - F I E L D - S E C T I O N ............................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................

	// - M E T H O D - S E C T I O N ..........................................................................
	@Test
	public void test01UseAllianceV1() {
		logger.info(">> [ESINetworkDownloadTestUnit.test01UseAllianceV1]");
		// Get an alliance by id.
		int allianceId=117383987;
		logger.info("-- [ESINetworkDownloadTestUnit.test01UseAllianceV1]> Validating the access to alliance {}",allianceId);
		final AllianceV1 alliance = GlobalDataManager.useAllianceV1(allianceId);
		Assert.assertNotNull("-> Validating the alliance is found..."
				, alliance);
		logger.info("<< [NeoComDatabaseTestUnit.test01UseAllianceV1]");
	}
}

// - UNUSED CODE ............................................................................................
//[01]
