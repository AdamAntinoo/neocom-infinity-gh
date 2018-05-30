//  PROJECT:     NeoCom.Microservices (NEOC.MS)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Java 1.8 / SpringBoot-1.3.5 / Angular 5.0
//  DESCRIPTION: This is the SpringBoot MicroServices module to run the backend services to complete the web
//               application based on Angular+SB. This is the web version for the NeoCom Android native
//               application. Most of the source code is common to both platforms and this module includes
//               the source for the specific functionality for the backend services.
package org.dimensinfin.eveonline.neocom.testblock.database;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.dimensinfin.eveonline.neocom.NeoComMicroServicesCoreTestUnit;
import org.dimensinfin.eveonline.neocom.database.ISDEDBHelper;
import org.dimensinfin.eveonline.neocom.database.SDESBDBHelper;
import org.dimensinfin.eveonline.neocom.datamngmt.GlobalDataManager;
import org.dimensinfin.eveonline.neocom.model.EveItem;

/**
 * Test unit for opening and data access to the different queries supported by the SDE Eve Online game model database. The
 * test will cover all the different queries adn will also test the cases where the parameters are not expected or the use of
 * caches.
 * @author Adam Antinoo
 */
// - CLASS IMPLEMENTATION ...................................................................................
public class SDEDatabaseTestUnit {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = LoggerFactory.getLogger("SDEDatabaseTestUnit");
	private static ISDEDBHelper sdeHelper = null;

	@BeforeClass
	public static void testOpenAndConnectDatabase() throws SQLException, IOException {

		NeoComMicroServicesCoreTestUnit.before01CreateApplicationEnvironment();

	}

	// - F I E L D - S E C T I O N ............................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................

	// - M E T H O D - S E C T I O N ..........................................................................
//	@Test
	public void testGetItem4Id() throws SQLException {
		Assert.assertNotNull(sdeHelper);
		// Check the access to an item the first time. Be sure the cache is empty.
		GlobalDataManager.cleanEveItemCache();
		// Access a know item.
		final EveItem validItem = new GlobalDataManager().searchItem4Id(34);
		// Use the specific test for a know item to check the results.
		Assert.assertEquals(validItem.getTypeId(), 34);
		Assert.assertEquals(validItem.getName(), "Tritanium");
	}
}
// - UNUSED CODE ............................................................................................
