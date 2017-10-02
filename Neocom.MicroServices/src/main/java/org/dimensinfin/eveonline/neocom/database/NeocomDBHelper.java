//	PROJECT:        NeoCom.Databases (NEOC.D)
//	AUTHORS:        Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:		Java 1.8 Library.
//	DESCRIPTION:		SQLite database access library. Isolates Neocom database access from any
//									environment limits.
package org.dimensinfin.eveonline.neocom.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import org.dimensinfin.eveonline.neocom.constant.R;
import org.dimensinfin.eveonline.neocom.industry.Job;
import org.dimensinfin.eveonline.neocom.market.NeoComMarketOrder;
import org.dimensinfin.eveonline.neocom.model.ApiKey;
import org.dimensinfin.eveonline.neocom.model.DatabaseVersion;
import org.dimensinfin.eveonline.neocom.model.EveLocation;
import org.dimensinfin.eveonline.neocom.model.NeoComAsset;
import org.dimensinfin.eveonline.neocom.model.NeoComBlueprint;
import org.dimensinfin.eveonline.neocom.model.Property;
import org.dimensinfin.eveonline.neocom.model.TimeStamp;
import org.dimensinfin.eveonline.neocom.planetary.PlanetaryResource;
import org.dimensinfin.eveonline.neocom.planetary.ResourceList;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

// - CLASS IMPLEMENTATION ...................................................................................
public class NeocomDBHelper {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger										logger									= Logger.getLogger("NeocomDBHelper");	//$NON-NLS-1$
	//	private static final String								SELECT_VERSION_NUMBER		= "SELECT versionNumber from Version";
	private static final long								MAX_CONNECTION_AGE			= 5 * 60 * 1000;
	private static final long								CHECK_CONNECTION_EVERY	= 2 * 60 * 1000;
	public static final String							RESOURCELISTID_FN				= "id";
	public static final String							RESURCELISTNAME_FN			= "name";
	public static final String							OWNERLISTID_FN					= "ownerList_id";

	// - F I E L D - S E C T I O N ............................................................................
	private String													databaseName						= "jdbc:sqlite:neocomdata.db";
	private int															databaseVersion					= 0;
	private boolean													isOpen									= false;
	private final Connection								neocomDatabase					= null;
	private JdbcConnectionSource						neocomDatasource				= null;

	private Dao<DatabaseVersion, String>		versionDao							= null;
	private Dao<TimeStamp, String>					timeStampDao						= null;
	private Dao<ApiKey, String>							apiKeysDao							= null;
	private Dao<Property, String>						propertyDao							= null;
	private Dao<ResourceList, String>				resourceList						= null;
	private Dao<PlanetaryResource, String>	planetaryResourceDao		= null;
	private Dao<NeoComAsset, String>				assetDao								= null;
	private Dao<NeoComBlueprint, String>		blueprintDao						= null;
	private Dao<Job, String>								jobDao									= null;
	private Dao<NeoComMarketOrder, String>	marketOrderDao					= null;
	private Dao<EveLocation, String>				locationDao							= null;

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public NeocomDBHelper(final String databaseName, final int databaseVersion) {
		this.databaseName = databaseName;
		this.databaseVersion = databaseVersion;
		// Open the database and check the version number.
		if (openNeocomDB()) {
			// During the current POC version force the creation of the tables and forget the version control.
			onCreate(neocomDatasource);
			//			// Read the version information from the database.
			//			int version = readDatabaseVersion();
			//			if (0 == version) {
			//				onUpgrade(neocomDatasource, version, version);
			//			} else {
			//				// Check if the version is equal to the current software version.
			//				if (version != databaseVersion) onUpgrade(neocomDatasource, version, databaseVersion);
			//			}
		}
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	/**
	 * Close the database connections and clear any cached DAOs.
	 */
	public void close() {
		try {
			neocomDatasource.close();
			neocomDatabase.close();
			//		} catch (IOException ex) {
			//			// TODO Auto-generated catch block
			//			ex.printStackTrace();
		} catch (SQLException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		versionDao = null;
	}

	public Dao<ApiKey, String> getApiKeysDao() throws SQLException {
		if (null == apiKeysDao) {
			apiKeysDao = DaoManager.createDao(this.getConnectionSource(), ApiKey.class);
		}
		return apiKeysDao;
	}

	public Dao<NeoComAsset, String> getAssetDAO() throws SQLException {
		if (null == assetDao) {
			assetDao = DaoManager.createDao(this.getConnectionSource(), NeoComAsset.class);
		}
		return assetDao;
	}

	public Dao<NeoComBlueprint, String> getBlueprintDAO() throws SQLException {
		if (null == blueprintDao) {
			blueprintDao = DaoManager.createDao(this.getConnectionSource(), NeoComBlueprint.class);
		}
		return blueprintDao;
	}

	public ConnectionSource getConnectionSource() {
		if (null == neocomDatasource) if (openNeocomDB()) return neocomDatasource;
		new RuntimeException("RT [NeocomDBHelper.neocomDatasource]> Neocom database not found and unable to be openned.");
		return neocomDatasource;
	}

	public Dao<Job, String> getJobDAO() throws SQLException {
		if (null == jobDao) {
			jobDao = DaoManager.createDao(this.getConnectionSource(), Job.class);
		}
		return jobDao;
	}

	public Dao<EveLocation, String> getLocationDAO() throws SQLException {
		if (null == locationDao) {
			locationDao = DaoManager.createDao(this.getConnectionSource(), EveLocation.class);
		}
		return locationDao;
	}

	public Dao<NeoComMarketOrder, String> getMarketOrderDAO() throws SQLException {
		if (null == marketOrderDao) {
			marketOrderDao = DaoManager.createDao(this.getConnectionSource(), NeoComMarketOrder.class);
		}
		return marketOrderDao;
	}

	public Dao<PlanetaryResource, String> getPlanetaryResourceDao() throws SQLException {
		if (null == planetaryResourceDao) {
			planetaryResourceDao = DaoManager.createDao(this.getConnectionSource(), PlanetaryResource.class);
		}
		return planetaryResourceDao;
	}

	public Dao<Property, String> getPropertyDAO() throws SQLException {
		if (null == propertyDao) {
			propertyDao = DaoManager.createDao(this.getConnectionSource(), Property.class);
		}
		return propertyDao;
	}

	public Dao<ResourceList, String> getResourceListDao() throws SQLException {
		if (null == resourceList) {
			resourceList = DaoManager.createDao(this.getConnectionSource(), ResourceList.class);
		}
		return resourceList;
	}

	public Dao<TimeStamp, String> getTimeStampDAO() throws SQLException {
		if (null == timeStampDao) {
			timeStampDao = DaoManager.createDao(this.getConnectionSource(), TimeStamp.class);
		}
		return timeStampDao;
	}

	public Dao<DatabaseVersion, String> getVersionDao() throws SQLException {
		if (null == versionDao) {
			versionDao = DaoManager.createDao(this.getConnectionSource(), DatabaseVersion.class);
		}
		return versionDao;
	}

	public void loadSeedData() {
		// Add seed data to the new database is the tables are empty.
		try {
			//---  D A T A B A S E    V E R S I O N
			Dao<DatabaseVersion, String> version = this.getVersionDao();
			QueryBuilder<DatabaseVersion, String> queryBuilder = version.queryBuilder();
			queryBuilder.setCountOf(true);
			long records = version.countOf(queryBuilder.prepare());

			// If the table is empty then insert the seeded Api Keys
			if (records < 1) {
				DatabaseVersion key = new DatabaseVersion(R.getResourceString("R.string.databaseversion"));
			}
		} catch (SQLException sqle) {
			logger.severe("E> Error creating the initial table on the app database.");
			sqle.printStackTrace();
		}
		try {
			//--- A P I   K E Y S
			Dao<ApiKey, String> apikey = getApiKeysDao();
			QueryBuilder<ApiKey, String> queryBuilder = apikey.queryBuilder();
			queryBuilder.setCountOf(true);
			long records = apikey.countOf(queryBuilder.prepare());

			// If the table is empty then insert the seeded Api Keys
			if (records < 1) {
				ApiKey key = new ApiKey("Beth Ripley").setKeynumber(2889577)
						.setValidationcode("Mb6iDKR14m9Xjh9maGTQCGTkpjRHPjOgVUkvK6E9r6fhMtOWtipaqybp0qCzxuuw");
				key = new ApiKey("Perico").setKeynumber(3106761)
						.setValidationcode("gltCmvVoZl5akrM8d6DbNKZn7Jm2SaukrmqjnSOyqKbvzz5CtNfknTEwdBe6IIFf").setActive(false);
				key = new ApiKey("CapitanHaddock09").setKeynumber(924767)
						.setValidationcode("2qBKUY6I9ozYhKxYUBPnSIix0fHFCqveD1UEAv0GbYqLenLLTIfkkIWeOBejKX5P").setActive(false);
				key = new ApiKey("CapitanHaddock29").setKeynumber(6472981)
						.setValidationcode("pj1NJKKb0pNO8LTp0qN2yJSxZoZUO0UYYq8qLtOeFXNsNBRpiz7orcqVAu7UGF7z").setActive(false);
				key = new ApiKey("CapitanHaddock").setKeynumber(924767)
						.setValidationcode("2qBKUY6I9ozYhKxYUBPnSIix0fHFCqveD1UEAv0GbYqLenLLTIfkkIWeOBejKX5P").setActive(false);
				key = new ApiKey("CapitanHaddock").setKeynumber(6472981)
						.setValidationcode("pj1NJKKb0pNO8LTp0qN2yJSxZoZUO0UYYq8qLtOeFXNsNBRpiz7orcqVAu7UGF7z").setActive(false);
			}
		} catch (SQLException sqle) {
			logger.severe("E> Error creating the initial table on the app database.");
			sqle.printStackTrace();
		}
	}

	public void onCreate(final ConnectionSource databaseConnection) {
		// Create the tables that do not exist
		try {
			TableUtils.createTableIfNotExists(databaseConnection, DatabaseVersion.class);
		} catch (SQLException sqle) {
		}
		try {
			TableUtils.createTableIfNotExists(databaseConnection, TimeStamp.class);
		} catch (SQLException sqle) {
		}
		try {
			TableUtils.createTableIfNotExists(databaseConnection, ApiKey.class);
		} catch (SQLException sqle) {
		}
		try {
			TableUtils.createTableIfNotExists(databaseConnection, Property.class);
		} catch (SQLException sqle) {
		}
		try {
			TableUtils.createTableIfNotExists(databaseConnection, ResourceList.class);
		} catch (SQLException sqle) {
		}
		try {
			TableUtils.createTableIfNotExists(databaseConnection, PlanetaryResource.class);
		} catch (SQLException sqle) {
		}
		try {
			TableUtils.createTableIfNotExists(databaseConnection, NeoComAsset.class);
		} catch (SQLException sqle) {
		}
		try {
			TableUtils.createTableIfNotExists(databaseConnection, NeoComBlueprint.class);
		} catch (SQLException sqle) {
		}
		try {
			TableUtils.createTableIfNotExists(databaseConnection, Job.class);
		} catch (SQLException sqle) {
		}
		try {
			TableUtils.createTableIfNotExists(databaseConnection, NeoComMarketOrder.class);
		} catch (SQLException sqle) {
		}
		try {
			TableUtils.createTableIfNotExists(databaseConnection, EveLocation.class);
		} catch (SQLException sqle) {
		}
	}

	public void onUpgrade(final ConnectionSource databaseConnection, final int oldVersion, final int newVersion) {
		// Execute different actions depending on the new version.
		if (oldVersion < 1) {
			try {
				// Create the version table and insert the initial new version code.
				TableUtils.createTableIfNotExists(databaseConnection, DatabaseVersion.class);
				DatabaseVersion version = new DatabaseVersion(newVersion);
				// Persist the version object to the database
				getVersionDao().create(version);
			} catch (RuntimeException rtex) {
				logger.severe("E> Error dropping table on Database new version.");
				rtex.printStackTrace();
			} catch (SQLException sqle) {
				logger.severe("E> Error dropping table on Database new version.");
				sqle.printStackTrace();
			}
		}
		// Update on the database the database version.
		try {
			List<DatabaseVersion> versionList = getVersionDao().queryForAll();
			if (versionList.size() > 0) {
				DatabaseVersion version = versionList.get(0);
				version.setVersionNumber(newVersion);
				getVersionDao().refresh(version);
			}
		} catch (SQLException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		onCreate(databaseConnection);
	}

	/**
	 * Open a new pooled JDBC datasource connection list and stores its reference for use of the whole set of
	 * services. Being a pooled connection it can create as many connections as required to do requests in
	 * parallel to the database instance.
	 * 
	 * @return
	 */
	private boolean openNeocomDB() {
		if (!isOpen) if (null == neocomDatasource) {
			// Open and configure the connection datasource for DAO queries.
			try {
				//				SQLiteDataSource slds = new SQLiteDataSource();
				//				slds.setUrl("jdbc:sqlite:src/main/resources/neocomdata.db");
				//				slds.setDatabaseName("neocomdata.db");
				//				DataSourceConnectionSource dcs = new DataSourceConnectionSource(slds, "jdbc:sqlite:src/main/resources/");
				//				new JdbcConnectionSource()
				//				neocomDatasource = dcs;
				neocomDatasource = new JdbcConnectionSource(databaseName);
				// Only keep the connections open for 5 minutes
				//				neocomDatasource.setMaxConnectionAgeMillis(MAX_CONNECTION_AGE);
				//				// Change the check-every milliseconds from 30 seconds to 60
				//				neocomDatasource.setCheckConnectionsEveryMillis(CHECK_CONNECTION_EVERY);
				//				// For extra protection, enable the testing of connections
				//				// right before they are handed to the user
				//				neocomDatasource.setTestBeforeGet(true);
				logger.info("--[NeocomDBHelper.openNeocomDB]> Opened database " + databaseName + " successfully with version "
						+ databaseVersion + ".");
				isOpen = true;
			} catch (Exception sqle) {
				logger.severe("E>[NeocomDBHelper.openNeocomDB]> " + sqle.getClass().getName() + ": " + sqle.getMessage());
			}
			//			if (null == neocomDatabase) {
			//				// Open the standard JDBC connection for direct SQL executions.
			//				try {
			//					Class.forName("org.sqlite.JDBC");
			//					neocomDatabase = DriverManager.getConnection(databaseName);
			//					neocomDatabase.setAutoCommit(false);
			//					isOpen = true;
			//				} catch (Exception sqle) {
			//					logger.severe("E>[NeocomDBHelper.openNeocomDB]> " + sqle.getClass().getName() + ": " + sqle.getMessage());
			//				}
			//			}
		}
		return isOpen;
	}

	private int readDatabaseVersion() {
		return 0;
		//		// Access the version object persistent on the database.
		//		try {
		//			DatabaseVersion version = getVersionDao().queryForFirst(null);
		//			return version.getVersionNumber();
		//		} catch (SQLException sqle) {
		//			logger.warning("W- [NeocomDBHelper.readDatabaseVersion]> Database exception: " + sqle.getMessage());
		//			return 0;
		//		}
	}

}
// - UNUSED CODE ............................................................................................
