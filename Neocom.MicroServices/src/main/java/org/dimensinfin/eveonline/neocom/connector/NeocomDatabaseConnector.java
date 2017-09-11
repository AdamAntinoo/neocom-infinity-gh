//	PROJECT:      Neocom.Microservices (NEOC-MS)
//	AUTHORS:      Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:    (c) 2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:	SpringBoot-MS-Java 1.8.
//	DESCRIPTION:	This is the integration project for all the web server pieces. This is the launcher for
//								the SpringBoot+MicroServices+Angular unified web application.
package org.dimensinfin.eveonline.neocom.connector;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dimensinfin.eveonline.neocom.constant.R;
import org.dimensinfin.eveonline.neocom.database.NeocomDBHelper;
import org.dimensinfin.eveonline.neocom.industry.Job;
import org.dimensinfin.eveonline.neocom.market.NeoComMarketOrder;
import org.dimensinfin.eveonline.neocom.model.ApiKey;
import org.dimensinfin.eveonline.neocom.model.EveLocation;
import org.dimensinfin.eveonline.neocom.model.NeoComAsset;
import org.dimensinfin.eveonline.neocom.model.NeoComBlueprint;
import org.dimensinfin.eveonline.neocom.model.Property;
import org.dimensinfin.eveonline.neocom.planetary.PlanetaryResource;
import org.dimensinfin.eveonline.neocom.planetary.ResourceList;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;

// - CLASS IMPLEMENTATION ...................................................................................
public class NeocomDatabaseConnector extends SpringDatabaseConnector {
	// - S T A T I C - S E C T I O N ..........................................................................

	// - F I E L D - S E C T I O N ............................................................................
	private NeocomDBHelper neocomDBHelper = null;

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public NeocomDatabaseConnector() {
		DATABASE_NAME = R.getResourceString("R.string.appdatabasepath")
				+ R.getResourceString("R.string.appdatabasefilename");
		DATABASE_VERSION = new Integer(R.getResourceString("R.string.databaseversion")).intValue();
		neocomDBHelper = new NeocomDBHelper(DATABASE_NAME, DATABASE_VERSION);
	}

	/**
	 * removes from the application database any asset and blueprint that contains the special -1 code as the
	 * owner identifier. Those records are from older downloads and have to be removed to avoid merging with the
	 * new download.
	 */
	public synchronized void clearInvalidRecords() {
		//		DatabaseConnection database = null;
		try {
			ConnectionSource conn = neocomDBHelper.getConnectionSource();
			DatabaseConnection database = conn.getReadWriteConnection();
			synchronized (database) {
				int rowCount = database.delete("DELETE FROM Assets WHERE ownerID=-1", null, null);
				logger
						.info("-- [NeocomDatabaseConnector.clearInvalidAssets]> rows deleted ASSETS [OWNERID = -1] - " + rowCount);
				rowCount = database.delete("DELETE FROM Blueprints WHERE ownerID=-1", null, null);
				logger.info(
						"-- [NeocomDatabaseConnector.clearInvalidAssets]> rows deleted BLUEPRINTS [OWNERID = -1] - " + rowCount);
			}
		} catch (final SQLException ex) {
			logger.warning("W> Problem clearing invalid assets. " + ex.getMessage());
		}
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	public Dao<ApiKey, String> getApiKeysDao() throws SQLException {
		return this.getNeocomDBHelper().getApiKeysDao();
	}

	public List<ApiKey> getApiList4Login(final String login) {
		logger.info(">> [AndroidDatabaseConnector.getApiList4Login] login=" + login);
		// Access the database to get the list of keys. From that point on we can retrieve the characters easily.
		List<ApiKey> apilist = null;
		try {
			Dao<ApiKey, String> keyDao = AppConnector.getDBConnector().getApiKeysDao();
			QueryBuilder<ApiKey, String> queryBuilder = keyDao.queryBuilder();
			Where<ApiKey, String> where = queryBuilder.where();
			where.eq("login", login);
			PreparedQuery<ApiKey> preparedQuery = queryBuilder.prepare();
			apilist = keyDao.query(preparedQuery);
		} catch (java.sql.SQLException sqle) {
			sqle.printStackTrace();
		}
		logger.info("<< [AndroidDatabaseConnector.getApiList4Login]");
		return apilist;
	}

	public Dao<NeoComAsset, String> getAssetDAO() throws java.sql.SQLException {
		return this.getNeocomDBHelper().getAssetDAO();
	}

	public Dao<NeoComBlueprint, String> getBlueprintDAO() throws SQLException {
		return this.getNeocomDBHelper().getBlueprintDAO();
	}

	@Override
	public Dao<Job, String> getJobDAO() throws SQLException {
		return this.getNeocomDBHelper().getJobDAO();
	}

	public Dao<EveLocation, String> getLocationDAO() throws SQLException {
		return this.getNeocomDBHelper().getLocationDAO();
	}

	public Dao<NeoComMarketOrder, String> getMarketOrderDAO() throws SQLException {
		return this.getNeocomDBHelper().getMarketOrderDAO();
	}

	public Dao<PlanetaryResource, String> getPlanetaryResourceDao() throws SQLException {
		return this.getNeocomDBHelper().getPlanetaryResourceDao();
	}

	public Dao<Property, String> getPropertyDAO() throws SQLException {
		return this.getNeocomDBHelper().getPropertyDAO();
	}

	public Dao<ResourceList, String> getResourceListDao() throws SQLException {
		return this.getNeocomDBHelper().getResourceListDao();
	}

	public Dao<DatabaseVersion, String> getVersionDao() throws SQLException {
		return this.getNeocomDBHelper().getVersionDao();
	}

	/**
	 * Changes the owner id for all records from a new download with the id of the current character. This
	 * completes the download and the assignment of the resources to the character without interrupting the
	 * processing of data by the application.
	 */
	public synchronized void replaceAssets(final long characterID) {
		try {
			ConnectionSource conn = neocomDBHelper.getConnectionSource();
			DatabaseConnection database = conn.getReadWriteConnection();
			synchronized (database) {
				int rowCount = database.delete("DELETE FROM Assets WHERE ownerID=" + characterID, null, null);
				logger.info("-- [NeocomDatabaseConnector.replaceAssets]> rows deleted ASSETS [OWNERID = " + characterID + "] - "
						+ rowCount);
				rowCount = database.update("UPDATE FROM Assets WHERE ownerID=" + characterID + " SET ownerID=" + characterID,
						null, null);
				logger.info("-- [NeocomDatabaseConnector.replaceAssets]> rows replaces ASSETS [OWNERID = " + characterID
						+ "] - " + rowCount);
			}
		} catch (final SQLException ex) {
			logger.warning("W> Problem clearing invalid assets. " + ex.getMessage());
		}
	}

	public synchronized void replaceBlueprints(final long characterID) {
		try {
			ConnectionSource conn = neocomDBHelper.getConnectionSource();
			DatabaseConnection database = conn.getReadWriteConnection();
			synchronized (database) {
				int rowCount = database.delete("DELETE FROM Blueprints WHERE ownerID=" + characterID, null, null);
				logger.info("-- [NeocomDatabaseConnector.replaceAssets]> rows deleted BLUEPRINTS [OWNERID = " + characterID
						+ "] - " + rowCount);
				rowCount = database
						.update("UPDATE FROM Blueprints WHERE ownerID=" + characterID + " SET ownerID=" + characterID, null, null);
				logger.info("-- [NeocomDatabaseConnector.replaceAssets]> rows replaces BLUEPRINTS [OWNERID = " + characterID
						+ "] - " + rowCount);
			}
		} catch (final SQLException ex) {
			logger.warning("W> Problem clearing invalid assets. " + ex.getMessage());
		}
	}

	/**
	 * Get the complete list of assets that are Planetary Materials.
	 * 
	 * @return
	 */
	public ArrayList<NeoComAsset> searchAllPlanetaryAssets(final long characterID) {
		// Select assets for each one of the Planetary categories.
		ArrayList<NeoComAsset> assetList = new ArrayList<NeoComAsset>();
		assetList.addAll(this.searchAsset4Category(characterID, "Planetary Commodities"));
		assetList.addAll(this.searchAsset4Category(characterID, "Planetary Resources"));
		return assetList;
	}

	/**
	 * Gets the list of assets of a select Category
	 * 
	 * @param characterID
	 * @param typeID
	 * @return
	 */
	public ArrayList<NeoComAsset> searchAsset4Category(final long characterID, final String categoryName) {
		// Select assets for the owner and with an specific type id.
		List<NeoComAsset> assetList = new ArrayList<NeoComAsset>();
		try {
			Dao<NeoComAsset, String> assetDao = this.getAssetDAO();
			QueryBuilder<NeoComAsset, String> queryBuilder = assetDao.queryBuilder();
			Where<NeoComAsset, String> where = queryBuilder.where();
			where.eq("ownerID", characterID);
			where.and();
			where.eq("category", categoryName);
			PreparedQuery<NeoComAsset> preparedQuery = queryBuilder.prepare();
			assetList = assetDao.query(preparedQuery);
		} catch (java.sql.SQLException sqle) {
			sqle.printStackTrace();
		}
		return (ArrayList<NeoComAsset>) assetList;
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
