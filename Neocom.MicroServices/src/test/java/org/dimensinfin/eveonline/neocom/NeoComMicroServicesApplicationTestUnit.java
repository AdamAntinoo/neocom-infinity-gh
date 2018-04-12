//  PROJECT:     NeoCom.Microservices (NEOC.MS)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Java 1.8 / SpringBoot-1.3.5 / Angular 5.0
//  DESCRIPTION: This is the SpringBoot MicroServices module to run the backend services to complete the web
//               application based on Angular+SB. This is the web version for the NeoCom Android native
//               application. Most of the source code is common to both platforms and this module includes
//               the source for the specific functionality for the backend services.
package org.dimensinfin.eveonline.neocom;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
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

/**
 * @author Adam Antinoo
 */
// - CLASS IMPLEMENTATION ...................................................................................
	@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NeoComMicroServicesApplicationTestUnit {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = LoggerFactory.getLogger("NeoComMicroServicesApplicationTestUnit");

	// - F I E L D - S E C T I O N ............................................................................

	// - M E T H O D - S E C T I O N ..........................................................................
	@Test
	public void test01CheckESIDateConversion(){
		logger.info(">> [NeoComMicroServicesApplicationTestUnit.test01CheckESIDateConversion]");
		final long testDate = DateTime.now().getMillis();
		DateTime dt = new DateTime(testDate);
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MMMM-dd'THH:mm:ssZ");
		logger.info(">> [NeoComMicroServicesApplicationTestUnit.test01CheckESIDateConversion]");
	}
//	@Test
	public  void test02OpenAndConnectDatabase() throws SQLException {
		logger.info(">> [NeoComMicroServicesApplicationTestUnit.test02OpenAndConnectDatabase]");
		// Connect the SDE database.
		logger.info("-- [NeoComMicroServicesApplicationTestUnit.test02OpenAndConnectDatabase]> Connecting SDE database...");
		GlobalDataManager.connectSDEDBConnector(new SDESBDBHelper()
				.setDatabaseSchema(GlobalDataManager.getResourceString("R.database.sdedatabase.databaseschema"))
				.setDatabasePath(GlobalDataManager.getResourceString("R.database.sdedatabase.databasepath"))
				.setDatabaseName(GlobalDataManager.getResourceString("R.database.sdedatabase.databasename"))
				.build()
		);
		// Connect the NeoCom database.
		logger.info("-- [NeoComMicroServicesApplicationTestUnit.test02OpenAndConnectDatabase]> Connecting NeoCom private database...");
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
		logger.info("<< [NeoComMicroServicesApplicationTestUnit.test02OpenAndConnectDatabase]");
	}
}

// - UNUSED CODE ............................................................................................
//[01]
