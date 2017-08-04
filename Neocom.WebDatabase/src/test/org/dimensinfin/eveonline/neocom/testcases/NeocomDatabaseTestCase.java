//	PROJECT:      NeoCom.Databases (NEOC.D)
//	AUTHORS:      Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:    (c) 2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:	Java 1.8 Library.
//	DESCRIPTION:	SQLite database access library. Isolates Neocom database access from any
//					environment limits.
package org.dimensinfin.eveonline.neocom.testcases;

import static org.junit.Assert.fail;

import java.util.logging.Logger;

import org.dimensinfin.eveonline.neocom.database.NeocomDBHelper;
import org.junit.Test;

// - CLASS IMPLEMENTATION ...................................................................................
public class NeocomDatabaseTestCase {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = Logger.getLogger("NeocomDatabaseTestCase.java");

	// - F I E L D - S E C T I O N ............................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public NeocomDatabaseTestCase() {
	}

	@Test
	public void testDatabaseCreation() {
		NeocomDBHelper helper = new NeocomDBHelper("jdbc:sqlite:src/test/resources/neocomdata.db", 1);
		if (null == helper)
			fail("FAIL - Not able to create the NeocomDBHelper.");
		else {
			if (null == helper.getNeocomDatabase()) fail("FAIL - Creation of new database file failed.");
		}
	}

	// - M E T H O D - S E C T I O N ..........................................................................
}

// - UNUSED CODE ............................................................................................
