//	PROJECT:      NeoCom.Databases (NEOC.D)
//	AUTHORS:      Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:    (c) 2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:	Java 1.8 Library.
//	DESCRIPTION:	SQLite database access library. Isolates Neocom database access from any
//								environment limits.
package org.dimensinfin.eveonline.neocom.connector;

import java.sql.SQLException;

import org.dimensinfin.eveonline.neocom.constant.R;
import org.dimensinfin.eveonline.neocom.database.NeocomDBHelper;
import org.dimensinfin.eveonline.neocom.model.ApiKey;
import org.dimensinfin.eveonline.neocom.model.Property;
import org.dimensinfin.eveonline.neocom.planetary.PlanetaryResource;
import org.dimensinfin.eveonline.neocom.planetary.ResourceList;

import com.j256.ormlite.dao.Dao;

// - CLASS IMPLEMENTATION ...................................................................................
public class NeocomDatabaseConnector extends SpringDatabaseConnector {
	// - S T A T I C - S E C T I O N ..........................................................................

	// - F I E L D - S E C T I O N ............................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public NeocomDatabaseConnector() {
		DATABASE_NAME = R.getResourceString("R.string.appdatabasepath")
				+ R.getResourceString("R.string.appdatabasefilename");
		DATABASE_VERSION = new Integer(R.getResourceString("R.string.databaseversion")).intValue();
		neocomDBHelper = new NeocomDBHelper(DATABASE_NAME, DATABASE_VERSION);
	}

	public Dao<ApiKey, String> getApiKeysDao() throws SQLException {
		return getNeocomDBHelper().getApiKeysDao();
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	public Dao<PlanetaryResource, String> getPlanetaryResourceDao() throws SQLException {
		return getNeocomDBHelper().getPlanetaryResourceDao();
	}

	@Override
	public Dao<Property, String> getPropertyDAO() throws SQLException {
		return getNeocomDBHelper().getPropertyDAO();
	}

	public Dao<ResourceList, String> getResourceListDao() throws SQLException {
		return getNeocomDBHelper().getResourceListDao();
	}

	public Dao<DatabaseVersion, String> getVersionDao() throws SQLException {
		return getNeocomDBHelper().getVersionDao();
	}

	private NeocomDBHelper getNeocomDBHelper() {
		if (null == neocomDBHelper) {
			logger.warning(
					"W> [NeocomDatabaseConnector.getNeocomDBHelper]> helper not found. Creating a new one with default database.");
			neocomDBHelper = new NeocomDBHelper(DATABASE_NAME, DATABASE_VERSION);
		}
		return neocomDBHelper;
	}
}

// - UNUSED CODE ............................................................................................
