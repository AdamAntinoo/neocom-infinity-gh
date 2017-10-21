//	PROJECT:      NeoCom.Databases (NEOC.D)
//	AUTHORS:      Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:    (c) 2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:	Java 1.8 Library.
//	DESCRIPTION:	SQLite database access library. Isolates Neocom database access from any
//								environment limits.
package org.dimensinfin.eveonline.neocom.connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;

import org.dimensinfin.eveonline.neocom.constant.ModelWideConstants;
import org.dimensinfin.eveonline.neocom.database.NeoComBaseDatabase;
import org.dimensinfin.eveonline.neocom.database.NeocomDBHelper;
import org.dimensinfin.eveonline.neocom.industry.Job;
import org.dimensinfin.eveonline.neocom.market.NeoComMarketOrder;
import org.dimensinfin.eveonline.neocom.model.ApiKey;
import org.dimensinfin.eveonline.neocom.model.DatabaseVersion;
import org.dimensinfin.eveonline.neocom.model.EveItem;
import org.dimensinfin.eveonline.neocom.model.EveLocation;
import org.dimensinfin.eveonline.neocom.model.Login;
import org.dimensinfin.eveonline.neocom.model.NeoComAsset;
import org.dimensinfin.eveonline.neocom.model.NeoComBlueprint;
import org.dimensinfin.eveonline.neocom.model.Outpost;
import org.dimensinfin.eveonline.neocom.model.Property;
import org.dimensinfin.eveonline.neocom.model.TimeStamp;
import org.dimensinfin.eveonline.neocom.planetary.PlanetaryResource;
import org.dimensinfin.eveonline.neocom.planetary.ResourceList;
import org.dimensinfin.eveonline.neocom.planetary.Schematics;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;

// - CLASS IMPLEMENTATION ...................................................................................
//@Component
//@CacheConfig(cacheNames = "MarketData")
public class SpringDatabaseConnector extends NeoComBaseDatabase implements INeoComModelDatabase {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger												logger										= Logger.getLogger("SpringDatabaseConnector");

	// - F I E L D   I D E N T I F I E R S
	private static int													STATIONTYPEID_COLINDEX		= 1;

	// - S Q L   C O M M A N D S
	private static final String									CCPDATABASE_URL						= "jdbc:sqlite:src/main/resources/eve.db";
	//	protected static String												DATABASE_NAME							= "neocomdata.db";
	//	protected static int													DATABASE_VERSION					= 010;
	//
	private static final String									SELECT_RAW_PRODUCTRESULT	= "SELECT pstmo.typeID, pstmo.quantity, pstmo.schematicID"
			+ " FROM   planetSchematicsTypeMap pstmi, planetSchematicsTypeMap pstmo" + " WHERE  pstmi.typeID = ?"
			+ " AND    pstmo.schematicID = pstmi.schematicID" + " AND    pstmo.isInput = 0";
	private static final String									SELECT_TIER2_INPUTS				= "SELECT pstmt.TYPEid, pstmt.quantity"
			+ " FROM  planetSchematicsTypeMap pstms, planetSchematicsTypeMap pstmt" + " WHERE pstms.typeID = ?"
			+ " AND   pstms.isInput = 0" + " AND   pstmt.schematicID = pstms.schematicID" + " AND   pstmT.isInput = 1";
	private static final String									SELECT_SCHEMATICS_INFO		= "SELECT pstms.typeID, pstms.quantity, pstms.isInput"
			+ " FROM   planetSchematicsTypeMap pstmt, planetSchematicsTypeMap pstms" + " WHERE  pstmt.typeID = ?"
			+ " AND    pstmt.isInput = 0" + " AND    pstms.schematicID = pstmt.schematicID";

	//private static final String							DATABASE_URL							= "jdbc:sqlite:D:\\Development\\WorkStage\\ProjectsAngular\\NeoCom\\src\\main\\resources\\eve.db";
	//private static final String							DATABASE_URL							= "jdbc:sqlite:D:\\Development\\ProjectsAngular\\NeoCom\\src\\main\\resources\\eve.db";
	private static final String									SELECT_ITEM_BYID					= "SELECT it.typeID AS typeID, it.typeName AS typeName"
			+ " , ig.groupName AS groupName" + " , ic.categoryName AS categoryName" + " , it.basePrice AS basePrice"
			+ " , it.volume AS volume" + " , IFNULL(img.metaGroupName, " + '"' + "NOTECH" + '"' + ") AS Tech"
			+ " FROM invTypes it" + " LEFT OUTER JOIN invGroups ig ON ig.groupID = it.groupID"
			+ " LEFT OUTER JOIN invCategories ic ON ic.categoryID = ig.categoryID"
			+ " LEFT OUTER JOIN invMetaTypes imt ON imt.typeID = it.typeID"
			+ " LEFT OUTER JOIN invMetaGroups img ON img.metaGroupID = imt.metaGroupID" + " WHERE it.typeID = ?";

	private static final String									SELECT_LOCATIONBYID				= "SELECT md.itemID AS locationID, md.typeID AS typeID, md.itemName AS locationName, md.security AS security"
			+ " , IFNULL(md.solarSystemID, -1) AS systemID, ms.solarSystemName AS system"
			+ " , IFNULL(md.constellationID, -1) AS constellationID, mc.constellationName AS constellation"
			+ " , IFNULL(md.regionID, -1) AS regionID, mr.regionName AS region" + " FROM mapDenormalize md"
			+ " LEFT OUTER JOIN mapRegions mr ON mr.regionID = md.regionID"
			+ " LEFT OUTER JOIN mapConstellations mc ON mc.constellationID = md.constellationID"
			+ " LEFT OUTER JOIN mapSolarSystems ms ON ms.solarSystemID = md.solarSystemID" + " WHERE itemID = ?";
	private static final String									SELECT_LOCATIONBYSYSTEM		= "SELECT solarSystemID from mapSolarSystems WHERE solarSystemName = ?";

	private static final String									LOM4BLUEPRINT							= "SELECT iam.typeID, itb.typeName, iam.materialTypeID, it.typeName, ig.groupName, ic.categoryName, iam.quantity, iam.consume"
			+ " FROM industryActivityMaterials iam, invTypes itb, invTypes it, invGroups ig, invCategories ic"
			+ " WHERE iam.typeID = ?" + " AND iam.activityID = 1" + " AND itb.typeID = iam.typeID"
			+ " AND it.typeID = iam.materialTypeID" + " AND ig.groupID = it.groupID" + " AND ic.categoryID = ig.categoryID";

	private static final String									TECH4BLUEPRINT						= "SELECT iap.typeID, it.typeName, imt.metaGroupID, img.metaGroupName"
			+ " FROM industryActivityProducts iap, invTypes it, invMetaTypes imt, invMetaGroups img" + " WHERE it.typeID =?"
			+ " AND iap.typeID = it.typeID" + " AND imt.typeID = productTypeID" + " AND img.metaGroupID = imt.metaGroupID"
			+ " AND iap.activityID = 1";

	private static final String									REFINING_ASTEROID					= "SELECT itm.materialTypeID AS materialTypeID, itm.quantity AS qty"
			+ " , it.typeName AS materialName" + " , ito.portionSize AS portionSize"
			+ " FROM invTypeMaterials itm, invTypes it, invTypes ito" + " WHERE itm.typeID = ?"
			+ " AND it.typeID = itm.materialTypeID" + " AND ito.typeID = itm.typeID" + " ORDER BY itm.materialTypeID";

	private static final String									INDUSTRYACTIVITYMATERIALS	= "SELECT materialTypeID, quantity, consume FROM industryActivityMaterials WHERE typeID = ? AND activityID = 8";
	private static final String									STATIONTYPE								= "SELECT stationTypeID FROM staStations WHERE stationID = ?";
	private static final String									JOB_COMPLETION_TIME				= "SELECT typeID, time FROM industryActivity WHERE typeID = ? AND activityID = ?";
	private static final String									CHECK_INVENTION						= "SELECT count(*) AS counter"
			+ " FROM industryActivityProducts iap" + " WHERE iap.typeID = ?" + " AND iap.activityID = 8";
	private static final String									INVENTION_PRODUCT					= "SELECT productTypeID FROM industryActivityProducts WHERE typeID = ? AND activityID = 8";
	private static final String									CHECK_MANUFACTURABLE			= "SELECT count(*) AS counter FROM industryActivityProducts iap WHERE iap.productTypeID = ? AND iap.activityID = 1";
	private static final String									CHECK_REACTIONABLE				= "SELECT count(*) AS counter FROM industryActivityProducts iap WHERE iap.productTypeID = ? AND iap.activityID = 1";
	private static final String									CHECK_PLANETARYPRODUCED		= "SELECT count(*) AS counter FROM industryActivityProducts iap WHERE iap.productTypeID = ? AND iap.activityID = 1";
	private static final String									REACTION_COMPONENTS				= "SELECT"
			+ "   invTypeReactions.reactionTypeID" + " , invTypes.typeID, invTypes.typeName" + " , invTypeReactions.input"
			+ " , COALESCE(dgmTypeAttributes.valueInt, dgmTypeAttributes.valueFloat) * invTypeReactions.quantity AS quantity"
			+ " FROM invTypeReactions, dgmTypeAttributes, invTypes" + " WHERE"
			+ " invTypes.typeId = invTypeReactions.typeID AND" + " invTypeReactions.reactionTypeID IN ("
			+ "    SELECT reactionTypeID" + "    FROM invTypeReactions" + "    WHERE typeID = ? ) AND"
			+ " dgmTypeAttributes.typeID = invTypeReactions.typeID";

	// - F I E L D - S E C T I O N ............................................................................
	private String															databaseLink							= "jdbc:sqlite:src/main/resourcesneocomdata.db";
	private int																	dbVersion									= 10;
	private NeocomDBHelper											neocomDBHelper						= null;
	private Connection													ccpDatabase								= null;

	//	private Context														_context									= null;
	//	private SQLiteDatabase										staticDatabase						= null;
	//	private EveDroidDBHelper									appDatabaseHelper					= null;
	private final Hashtable<Integer, EveItem>		itemCache									= new Hashtable<Integer, EveItem>();
	//	private final HashMap<Integer, MarketDataSet>	buyMarketDataCache				= new HashMap<Integer, MarketDataSet>();
	//	private final HashMap<Integer, MarketDataSet>	sellMarketDataCache				= new HashMap<Integer, MarketDataSet>();
	private final Hashtable<Integer, Outpost>		outpostsCache							= new Hashtable<Integer, Outpost>();
	private final Hashtable<Long, NeoComAsset>	containerCache						= new Hashtable<Long, NeoComAsset>();;

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public SpringDatabaseConnector(final String dblocation, final String dbname, final String dbversion) {
		if ((null != dblocation) && (null != dbname)) {
			databaseLink = dblocation + dbname;
		}
		if (null != dbversion) dbVersion = Integer.valueOf(dbversion);
		neocomDBHelper = new NeocomDBHelper(databaseLink, dbVersion);
	}

	/**
	 * Get the complete list of assets that are Planetary Materials.
	 * 
	 * @return
	 */
	public ArrayList<NeoComAsset> accessAllPlanetaryAssets(final long characterID) {
		// Select assets for each one of the Planetary categories.
		ArrayList<NeoComAsset> assetList = new ArrayList<NeoComAsset>();
		assetList.addAll(this.searchAsset4Category(characterID, "Planetary Commodities"));
		assetList.addAll(this.searchAsset4Category(characterID, "Planetary Resources"));
		return assetList;
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	//[02]
	//	private String readJsonData() {
	//		//	String fileLocation = "C:\\Users\\ldediego\\UserData\\Workstage\\OrangeProjectsMars\\NeoCom\\src\\main\resources\\outposts.json";
	//		String fileLocation = "./src/main/resources/outposts.json";
	//		StringBuffer data = new StringBuffer();
	//		try {
	//			String str = "";
	//			InputStream is = new FileInputStream(new File(fileLocation));
	//			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	//			if (is != null) while ((str = reader.readLine()) != null)
	//				data.append(str);
	//			is.close();
	//		} catch (Exception ex) {
	//			ex.printStackTrace();
	//		}
	//		return data.toString();
	//	}
	//	public boolean checkInvention(final int typeID) {
	//		throw new RuntimeException(
	//				"Application connector not defined. Functionality 'checkExpiration' disabled. Call intercepted by abstract class 'AbstractDatabaseConnector'.");
	//	}
	//
	//	public boolean checkManufacturable(final int typeid) {
	//		throw new RuntimeException(
	//				"Application connector not defined. Functionality 'checkExpiration' disabled. Call intercepted by abstract class 'AbstractDatabaseConnector'.");
	//	}

	/**
	 * removes from the application database any asset and blueprint that contains the special -1 code as the
	 * owner identifier. Those records are from older downloads and have to be removed to avoid merging with the
	 * new download.
	 */
	public synchronized void clearInvalidRecords(final long pilotid) {
		try {
			ConnectionSource conn = neocomDBHelper.getConnectionSource();
			DatabaseConnection database = conn.getReadWriteConnection();
			synchronized (database) {
				int rowCount = database.delete("DELETE FROM Assets WHERE ownerID=" + (pilotid * -1), null, null);
				logger
						.info("-- [NeocomDatabaseConnector.clearInvalidAssets]> rows deleted ASSETS [OWNERID = -1] - " + rowCount);
				rowCount = database.delete("DELETE FROM Blueprints WHERE ownerID=" + (pilotid * -1), null, null);
				logger.info(
						"-- [NeocomDatabaseConnector.clearInvalidAssets]> rows deleted BLUEPRINTS [OWNERID = -1] - " + rowCount);
			}
		} catch (final SQLException ex) {
			logger.warning("W> Problem clearing invalid assets. " + ex.getMessage());
		}
	}

	//	public void closeDatabases() {
	//		throw new RuntimeException(
	//				"Application connector not defined. Functionality 'checkExpiration' disabled. Call intercepted by abstract class 'AbstractDatabaseConnector'.");
	//	}

	public Dao<ApiKey, String> getApiKeysDao() throws SQLException {
		return this.getNeocomDBHelper().getApiKeysDao();
	}

	@Deprecated
	public List<ApiKey> getApiList4Login(final String login) {
		logger.info(">> [AndroidDatabaseConnector.getApiList4Login] login=" + login);
		// Access the database to get the list of keys. From that point on we can retrieve the characters easily.
		List<ApiKey> apilist = null;
		try {
			Dao<ApiKey, String> keyDao = NeoComMSConnector.getSingleton().getDBConnector().getApiKeysDao();
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

	public Dao<NeoComAsset, String> getAssetDAO() throws SQLException {
		return this.getNeocomDBHelper().getAssetDAO();
	}

	public Dao<NeoComBlueprint, String> getBlueprintDAO() throws SQLException {
		return this.getNeocomDBHelper().getBlueprintDAO();
	}

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

	public Dao<TimeStamp, String> getTimeStampDAO() throws SQLException {
		return this.getNeocomDBHelper().getTimeStampDAO();
	}

	public Dao<DatabaseVersion, String> getVersionDao() throws SQLException {
		return this.getNeocomDBHelper().getVersionDao();
	}

	public void loadSeedData() {
		neocomDBHelper.loadSeedData();
	}

	//	@Deprecated
	//	public boolean openAppDataBase() {
	//		throw new RuntimeException(
	//				"Application connector not defined. Functionality 'openAppDataBase' disabled. Call intercepted by abstract class 'AbstractDatabaseConnector'.");
	//	}

	public boolean openCCPDataBase() {
		if (null == ccpDatabase) {
			try {
				Class.forName("org.sqlite.JDBC");
				ccpDatabase = DriverManager.getConnection(CCPDATABASE_URL);
				ccpDatabase.setAutoCommit(false);
			} catch (Exception sqle) {
				logger.warning(sqle.getClass().getName() + ": " + sqle.getMessage());
			}
			logger.info("-- [StringDatabaseConnector.openCCPDataBase]> Opened CCP database successfully.");
		}
		return true;
	}

	//	@Deprecated
	//	public boolean openDAO() {
	//		neocomDBHelper = new NeocomDBHelper(databaseLink, dbVersion);
	//		return false;
	//	}

	/**
	 * Returns the list of distinct identifiers for parentAssetId that should represent the container where an
	 * asset is located. That list can represent known Locations, Assets and unknown locations that should
	 * represent other Corporation assets like the Customs or not listed Space Structures.
	 */
	public List<NeoComAsset> queryAllAssetContainers(final long identifier) {
		// Get access to one assets with a distinct location. Discard the rest of the data and only process the Location id
		List<NeoComAsset> uniqueContainers = new Vector<NeoComAsset>();
		try {
			Dao<NeoComAsset, String> assetDao = this.getAssetDAO();
			QueryBuilder<NeoComAsset, String> queryBuilder = assetDao.queryBuilder().distinct()
					.selectColumns("parentAssetID");
			Where<NeoComAsset, String> where = queryBuilder.where();
			where.eq("ownerID", identifier);
			PreparedQuery<NeoComAsset> preparedQuery = queryBuilder.prepare();
			uniqueContainers = assetDao.query(preparedQuery);
		} catch (java.sql.SQLException sqle) {
			sqle.printStackTrace();
			logger.warning("W [SpringDatabaseConnector.queryAllLogins]> Excpetion reading all Logins" + sqle.getMessage());
		}
		return uniqueContainers;
	}

	/**
	 * Return the list of all the distinct Locations where a Character has assets. There is one location with a
	 * minus value id that is the <code>undefined</code> that contains all the items inside another elements
	 * (before assets processing) and the items on stations or space locations that do not belong to this
	 * character.<br>
	 * Once the assets are processed inside a location this list can change because it may contain other
	 * elements that are Ships or Containers. When processing the list we should connect them properly on the
	 * new hierarchy.<br>
	 * 
	 * @return
	 */
	public List<NeoComAsset> queryAllAssetLocations(final long identifier) {
		// Get access to one assets with a distinct location. Discard the rest of the data and only process the Location id
		List<NeoComAsset> uniqueLocations = new Vector<NeoComAsset>();
		try {
			Dao<NeoComAsset, String> assetDao = this.getAssetDAO();
			QueryBuilder<NeoComAsset, String> queryBuilder = assetDao.queryBuilder().distinct().selectColumns("locationID");
			Where<NeoComAsset, String> where = queryBuilder.where();
			where.eq("ownerID", identifier);
			PreparedQuery<NeoComAsset> preparedQuery = queryBuilder.prepare();
			uniqueLocations = assetDao.query(preparedQuery);
		} catch (java.sql.SQLException sqle) {
			sqle.printStackTrace();
			logger.warning("W [SpringDatabaseConnector.queryAllLogins]> Exception reading all Logins" + sqle.getMessage());
		}
		return uniqueLocations;
	}

	/**
	 * Reads all the keys stored at the database and classified them into a set of Login names. Added
	 * synchronization to avoid blocking while starting up. This call is only used when activating the Login
	 * list and this is found to be empty. While we fill it, block any other call.
	 */
	@Override
	public synchronized Hashtable<String, Login> queryAllLogins() {
		// Get access to all ApiKey registers
		List<ApiKey> keyList = new Vector<ApiKey>();
		try {
			Dao<ApiKey, String> keysDao = this.getApiKeysDao();
			QueryBuilder<ApiKey, String> queryBuilder = keysDao.queryBuilder();
			PreparedQuery<ApiKey> preparedQuery = queryBuilder.prepare();
			keyList = keysDao.query(preparedQuery);
		} catch (java.sql.SQLException sqle) {
			sqle.printStackTrace();
			logger.warning("W [SpringDatabaseConnector.queryAllLogins]> Excpetion reading all Logins" + sqle.getMessage());
		}
		// Classify the keys on they matching Logins.
		Hashtable<String, Login> loginList = new Hashtable<String, Login>();
		for (ApiKey apiKey : keyList) {
			String name = apiKey.getLogin();
			// Search for this on the list before creating a new Login.
			Login hit = loginList.get(name);
			if (null == hit) {
				Login login = new Login(name).addKey(apiKey);
				loginList.put(name, login);
			} else {
				hit.addKey(apiKey);
			}
		}
		return loginList;
	}

	//	public int queryBlueprintDependencies(final int bpitemID) {
	//		throw new RuntimeException(
	//				"Application connector not defined. Functionality 'queryBlueprintDependencies' disabled. Call intercepted by abstract class 'AbstractDatabaseConnector'.");
	//	}

	public List<NeoComAsset> queryContainerContents(final long identifier) {
		// Get access to one assets with a distinct location. Discard the rest of the data and only process the Location id
		List<NeoComAsset> contents = new Vector<NeoComAsset>();
		try {
			Dao<NeoComAsset, String> assetDao = this.getAssetDAO();
			QueryBuilder<NeoComAsset, String> queryBuilder = assetDao.queryBuilder();
			Where<NeoComAsset, String> where = queryBuilder.where();
			//			where.eq("ownerID", identifier);
			where.eq("locationID", identifier);
			PreparedQuery<NeoComAsset> preparedQuery = queryBuilder.prepare();
			contents = assetDao.query(preparedQuery);
		} catch (java.sql.SQLException sqle) {
			sqle.printStackTrace();
			logger.warning(
					"W [SpringDatabaseConnector.queryLocationContents]> Exception reading Location contents" + sqle.getMessage());
		}
		return contents;
	}

	public List<NeoComAsset> queryLocationContents(final long identifier) {
		// Get access to one assets with a distinct location. Discard the rest of the data and only process the Location id
		List<NeoComAsset> contents = new Vector<NeoComAsset>();
		try {
			Dao<NeoComAsset, String> assetDao = this.getAssetDAO();
			QueryBuilder<NeoComAsset, String> queryBuilder = assetDao.queryBuilder();
			Where<NeoComAsset, String> where = queryBuilder.where();
			//			where.eq("ownerID", identifier);
			where.eq("locationID", identifier);
			PreparedQuery<NeoComAsset> preparedQuery = queryBuilder.prepare();
			contents = assetDao.query(preparedQuery);
		} catch (java.sql.SQLException sqle) {
			sqle.printStackTrace();
			logger.warning(
					"W [SpringDatabaseConnector.queryLocationContents]> Exception reading Location contents" + sqle.getMessage());
		}
		return contents;
	}

	public List<NeoComAsset> queryLocationPlanetaryContents(final long identifier) {
		// Get access to one assets with a distinct location. Discard the rest of the data and only process the Location id
		List<NeoComAsset> contents = new Vector<NeoComAsset>();
		try {
			Dao<NeoComAsset, String> assetDao = this.getAssetDAO();
			QueryBuilder<NeoComAsset, String> queryBuilder = assetDao.queryBuilder();
			Where<NeoComAsset, String> where = queryBuilder.where();
			where.eq("locationID", identifier);
			where.and().eq("category", "Planetary Commodities");
			where.or().eq("category", "Planetary Resources");
			PreparedQuery<NeoComAsset> preparedQuery = queryBuilder.prepare();
			contents = assetDao.query(preparedQuery);
		} catch (java.sql.SQLException sqle) {
			sqle.printStackTrace();
			logger.warning(
					"W [SpringDatabaseConnector.queryLocationContents]> Exception reading Location contents" + sqle.getMessage());
		}
		return contents;
	}

	//	public ArrayList<Resource> refineOre(final int itemID) {
	//		throw new RuntimeException(
	//				"Application connector not defined. Functionality 'refineOre' disabled. Call intercepted by abstract class 'AbstractDatabaseConnector'.");
	//	}

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
				rowCount = database.update(
						"UPDATE Assets " + " SET ownerID=" + characterID + " WHERE ownerID=" + (characterID * -1), null, null);
				logger.info("-- [NeocomDatabaseConnector.replaceAssets]> rows replaced ASSETS [OWNERID = " + characterID
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

	//	public void replaceJobs(final long characterID) {
	//		throw new RuntimeException(
	//				"Application connector not defined. Functionality 'replaceJobs' disabled. Call intercepted by abstract class 'AbstractDatabaseConnector'.");
	//	}

	/**
	 * Get the list of all the Blueprints for a given Character. It can ge a Pilot or a Corporation and the
	 * differences should be none.
	 * 
	 * @param characterID
	 * @return
	 */
	public ArrayList<NeoComAsset> searchAllBlueprintAssets(final long characterID) {
		// Select assets for each one of the Planetary categories.
		ArrayList<NeoComAsset> assetList = new ArrayList<NeoComAsset>();
		assetList.addAll(this.searchAsset4Category(characterID, "Blueprint"));
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

	public ArrayList<NeoComAsset> searchAsset4Type(final long characterID, final int typeID) {
		// Select assets for the owner and with an specific type id.
		List<NeoComAsset> assetList = new ArrayList<NeoComAsset>();
		try {
			Dao<NeoComAsset, String> assetDao = this.getAssetDAO();
			QueryBuilder<NeoComAsset, String> queryBuilder = assetDao.queryBuilder();
			Where<NeoComAsset, String> where = queryBuilder.where();
			where.eq("ownerID", characterID);
			where.and();
			where.eq("typeID", typeID);
			PreparedQuery<NeoComAsset> preparedQuery = queryBuilder.prepare();
			assetList = assetDao.query(preparedQuery);
		} catch (java.sql.SQLException sqle) {
			sqle.printStackTrace();
		}
		return (ArrayList<NeoComAsset>) assetList;
	}

	public NeoComAsset searchAssetByID(final long assetID) {
		// search for the asset on the cache. Usually searching for containers.
		NeoComAsset hit = containerCache.get(assetID);
		if (null == hit) {
			// Select assets for the owner and with an specific type id.
			List<NeoComAsset> assetList = new ArrayList<NeoComAsset>();
			try {
				Dao<NeoComAsset, String> assetDao = this.getAssetDAO();
				QueryBuilder<NeoComAsset, String> queryBuilder = assetDao.queryBuilder();
				Where<NeoComAsset, String> where = queryBuilder.where();
				where.eq("assetID", assetID);
				PreparedQuery<NeoComAsset> preparedQuery = queryBuilder.prepare();
				assetList = assetDao.query(preparedQuery);
				if (assetList.size() > 0) {
					hit = assetList.get(0);
					containerCache.put(hit.getAssetID(), hit);
				}
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
		}
		return hit;
	}

	public ArrayList<NeoComAsset> searchAssetContainedAt(final long characterID, final long containerId) {
		// Select assets for the owner and with an specific type id.
		List<NeoComAsset> assetList = new ArrayList<NeoComAsset>();
		try {
			Dao<NeoComAsset, String> assetDao = this.getAssetDAO();
			QueryBuilder<NeoComAsset, String> queryBuilder = assetDao.queryBuilder();
			Where<NeoComAsset, String> where = queryBuilder.where();
			where.eq("ownerID", characterID);
			where.and();
			where.eq("parentAssetID", containerId);
			PreparedQuery<NeoComAsset> preparedQuery = queryBuilder.prepare();
			assetList = assetDao.query(preparedQuery);
		} catch (java.sql.SQLException sqle) {
			sqle.printStackTrace();
		}
		return (ArrayList<NeoComAsset>) assetList;
	}

	public int searchBlueprint4Module(final int moduleID) {
		throw new RuntimeException(
				"Application connector not defined. Functionality 'searchBlueprint4Module' disabled. Call intercepted by abstract class 'AbstractDatabaseConnector'.");
	}

	/**
	 * Gets the list of type ids for the required resources to get an output batch of the former planetary
	 * resource.
	 * 
	 * @param typeID
	 * @return
	 */
	public Vector<Integer> searchInputResources(final int typeID) {
		Vector<Integer> result = new Vector<Integer>();
		PreparedStatement prepStmt = null;
		ResultSet cursor = null;
		try {
			prepStmt = getCCPDatabase().prepareStatement(SELECT_TIER2_INPUTS);
			prepStmt.setString(1, Integer.valueOf(typeID).toString());
			cursor = prepStmt.executeQuery();
			while (cursor.next()) {
				result.add(cursor.getInt(1));
			}
		} catch (Exception ex) {
			logger.warning("W- [SpingDatabaseConnector.searchRawPlanetaryOutput]> Database exception: " + ex.getMessage());
		} finally {
			try {
				if (cursor != null) cursor.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			try {
				if (prepStmt != null) prepStmt.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	//	public ArrayList<Integer> searchInventionableBlueprints(final String resourceIDs) {
	//		throw new RuntimeException(
	//				"Application connector not defined. Functionality 'searchInventionableBlueprints' disabled. Call intercepted by abstract class 'AbstractDatabaseConnector'.");
	//	}
	//
	//	public int searchInventionProduct(final int typeID) {
	//		throw new RuntimeException(
	//				"Application connector not defined. Functionality 'searchInventionProduct' disabled. Call intercepted by abstract class 'AbstractDatabaseConnector'.");
	//	}

	/**
	 * Search on the eve.db database for the attributes that describe an Item. Items are the lowest data
	 * structure for EVE resources or modules. Everything on Eve is an Item. We detect blueprints that require a
	 * different treatment and also we check for the availability of the item at the current cache if
	 * implemented.
	 */
	public synchronized EveItem searchItembyID(final int typeID) {
		// Search the item on the cache.
		EveItem hit = itemCache.get(typeID);
		if (null == hit) {
			PreparedStatement prepStmt = null;
			ResultSet cursor = null;
			try {
				hit = new EveItem();
				//			final Cursor cursor = getCCPDatabase().rawQuery(SELECT_ITEM_BYID,
				//					new String[] { Integer.valueOf(typeID).toString() });
				//	      Statement stmt = getCCPDatabase().createStatement();
				prepStmt = getCCPDatabase().prepareStatement(SELECT_ITEM_BYID);
				prepStmt.setString(1, Integer.valueOf(typeID).toString());
				cursor = prepStmt.executeQuery();
				// The query can be run but now there are ids that do not return data.
				boolean found = false;
				while (cursor.next()) {
					found = true;
					hit.setTypeID(cursor.getInt(1));
					hit.setName(cursor.getString(2));
					hit.setGroupname(cursor.getString(3));
					hit.setCategory(cursor.getString(4));
					hit.setBasePrice(cursor.getDouble(5));
					hit.setVolume(cursor.getDouble(6));
					// Process the Tech field. The query marks blueprints
					String tech = cursor.getString(7);
					if (tech.equalsIgnoreCase("NOTECH")) {
						// Double check it is a Blueprint
						hit.setTech(ModelWideConstants.eveglobal.TechI);
						if (hit.getName().contains(" II Blueprint")) {
							hit.setBlueprint(true);
							if (hit.getName().contains(" II Blueprint")) {
								hit.setTech(ModelWideConstants.eveglobal.TechII);
							}
							if (hit.getName().contains(" III Blueprint")) {
								hit.setTech(ModelWideConstants.eveglobal.TechIII);
							}
						}
					} else {
						hit.setTech(tech);
					}
				}
				if (!found) {
					logger.warning("W> AndroidDatabaseConnector.searchItembyID -- Item <" + typeID + "> not found.");
				}
			} catch (Exception e) {
				logger.warning("W> AndroidDatabaseConnector.searchItembyID -- Item <" + typeID + "> not found.");
				return new EveItem();
			} finally {
				try {
					if (cursor != null) cursor.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					if (prepStmt != null) prepStmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			itemCache.put(new Integer(typeID), hit);
		}
		return hit;
	}

	//	public ArrayList<Job> searchJob4Class(final long characterID, final String string) {
	//		throw new RuntimeException(
	//				"Application connector not defined. Functionality 'searchJob4Class' disabled. Call intercepted by abstract class 'AbstractDatabaseConnector'.");
	//	}
	//
	//	public int searchJobExecutionTime(final int typeID, final int activityID) {
	//		throw new RuntimeException(
	//				"Application connector not defined. Functionality 'searchJobExecutionTime' disabled. Call intercepted by abstract class 'AbstractDatabaseConnector'.");
	//	}
	//
	//	public ArrayList<Resource> searchListOfDatacores(final int itemID) {
	//		throw new RuntimeException(
	//				"Application connector not defined. Functionality 'searchListOfDatacores' disabled. Call intercepted by abstract class 'AbstractDatabaseConnector'.");
	//	}
	//
	//	public ArrayList<Resource> searchListOfMaterials(final int itemID) {
	//		throw new RuntimeException(
	//				"Application connector not defined. Functionality 'searchListOfMaterials' disabled. Call intercepted by abstract class 'AbstractDatabaseConnector'.");
	//	}
	//
	//	public ArrayList<Resource> searchListOfReaction(final int itemID) {
	//		throw new RuntimeException(
	//				"Application connector not defined. Functionality 'searchListOfReaction' disabled. Call intercepted by abstract class 'AbstractDatabaseConnector'.");
	//	}
	//
	//	public int searchModule4Blueprint(final int bpitemID) {
	//		throw new RuntimeException(
	//				"Application connector not defined. Functionality 'searchModule4Blueprint' disabled. Call intercepted by abstract class 'AbstractDatabaseConnector'.");
	//	}

	//	public EveLocation searchLocationbyID(final long locationID) {
	//		EveLocation hit = new EveLocation(locationID);
	//		PreparedStatement prepStmt = null;
	//		ResultSet cursor = null;
	//		try {
	//			prepStmt = getCCPDatabase().prepareStatement(SELECT_LOCATIONBYID);
	//			prepStmt.setString(1, Long.valueOf(locationID).toString());
	//			cursor = prepStmt.executeQuery();
	//			boolean detected = false;
	//			while (cursor.next()) {
	//				//				if (cursor.moveToFirst()) {
	//				detected = true;
	//				// Check returned values when doing the assignments.
	//				long fragmentID = cursor.getLong(5);
	//				if (fragmentID > 0) {
	//					hit.setSystemID(fragmentID);
	//					hit.setSystem(cursor.getString(6));
	//				} else {
	//					hit.setSystem(cursor.getString(3));
	//				}
	//				fragmentID = cursor.getLong(7);
	//				if (fragmentID > 0) {
	//					hit.setConstellationID(fragmentID);
	//					hit.setConstellation(cursor.getString(8));
	//				}
	//				fragmentID = cursor.getLong(9);
	//				if (fragmentID > 0) {
	//					hit.setRegionID(fragmentID);
	//					hit.setRegion(cursor.getString(10));
	//				}
	//				hit.setTypeID(ELocationType.CCPLOCATION);
	//				hit.setStation(cursor.getString(3));
	//				hit.setLocationID(cursor.getLong(1));
	//				hit.setSecurity(cursor.getString(4));
	//				// Update the final ID
	//				hit.getID();
	//				detected = true;
	//			}
	//			//			if (!detected) // Search the location on the list of outposts.
	//			//				hit = searchOutpostbyID(locationID);
	//			//	}
	//		} catch (final Exception ex) {
	//			logger.warning("Location <" + locationID + "> not found.");
	//		}
	//		return hit;
	//	}

	//	public EveLocation searchLocationBySystem(final String name) {
	//		EveLocation hit = new EveLocation();
	//		PreparedStatement prepStmt = null;
	//		ResultSet cursor = null;
	//		try {
	//			prepStmt = getCCPDatabase().prepareStatement(SELECT_LOCATIONBYSYSTEM);
	//			prepStmt.setString(1, name);
	//			cursor = prepStmt.executeQuery();
	//			boolean detected = false;
	//			while (cursor.next()) {
	//				//				if (cursor.moveToFirst()) {
	//				detected = true;
	//				// Check returned values when doing the assignments.
	//				long fragmentID = cursor.getInt(1);
	//				if (fragmentID > 0) {
	//					hit.setSystemID(fragmentID);
	//					//					hit.setSystem(cursor.getString(6));
	//					//				} else {
	//					//					hit.setSystem(cursor.getString(3));
	//				}
	//				//				fragmentID = cursor.getLong(7);
	//				//				if (fragmentID > 0) {
	//				//					hit.setConstellationID(fragmentID);
	//				//					hit.setConstellation(cursor.getString(8));
	//				//				}
	//				//				fragmentID = cursor.getLong(9);
	//				//				if (fragmentID > 0) {
	//				//					hit.setRegionID(fragmentID);
	//				//					hit.setRegion(cursor.getString(10));
	//				//				}
	//				//				hit.setTypeID(cursor.getInt(2));
	//				//				hit.setStation(cursor.getString(3));
	//				//				hit.setLocationID(cursor.getLong(1));
	//				//				hit.setSecurity(cursor.getString(4));
	//				//				// Update the final ID
	//				//				hit.getID();
	//				detected = true;
	//			}
	//			//			if (!detected) // Search the location on the list of outposts.
	//			//				hit = searchOutpostbyID(locationID);
	//			//	}
	//		} catch (final Exception ex) {
	//			logger.warning("Location <" + name + "> not found.");
	//		}
	//		return searchLocationbyID(hit.getSystemID());
	//	}

	public int searchRawPlanetaryOutput(final int typeID) {
		int outputResourceId = typeID;
		PreparedStatement prepStmt = null;
		ResultSet cursor = null;
		try {
			prepStmt = getCCPDatabase().prepareStatement(SELECT_RAW_PRODUCTRESULT);
			prepStmt.setString(1, Integer.valueOf(typeID).toString());
			cursor = prepStmt.executeQuery();
			while (cursor.next()) {
				outputResourceId = cursor.getInt(1);
			}
		} catch (Exception ex) {
			logger.warning("W- [SpingDatabaseConnector.searchRawPlanetaryOutput]> Database exception: " + ex.getMessage());
		} finally {
			try {
				if (cursor != null) cursor.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			try {
				if (prepStmt != null) prepStmt.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return outputResourceId;
	}

	//	public int searchReactionOutputMultiplier(final int itemID) {
	//		// TODO Auto-generated method stub
	//		return 0;
	//	}

	public Vector<Schematics> searchSchematics4Output(final int targetId) {
		Vector<Schematics> scheList = new Vector<Schematics>();
		PreparedStatement prepStmt = null;
		ResultSet cursor = null;
		try {
			prepStmt = getCCPDatabase().prepareStatement(SELECT_SCHEMATICS_INFO);
			prepStmt.setString(1, Integer.valueOf(targetId).toString());
			cursor = prepStmt.executeQuery();
			while (cursor.next()) {
				scheList.add(new Schematics().addData(cursor.getInt(1), cursor.getInt(2), cursor.getBoolean(3)));
			}
		} catch (Exception ex) {
			logger.warning("W- [SpingDatabaseConnector.searchRawPlanetaryOutput]> Database exception: " + ex.getMessage());
		} finally {
			try {
				if (cursor != null) cursor.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			try {
				if (prepStmt != null) prepStmt.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return scheList;
	}

	/**
	 * Returns the resource identifier of the station class to locate icons or other type related resources.
	 * 
	 * @param stationID
	 * @return
	 */
	public int searchStationType(final long stationID) {
		int stationTypeID = 1529;
		NeoComMSConnector.getSingleton().startChrono();
		PreparedStatement prepStmt = null;
		ResultSet cursor = null;
		try {
			prepStmt = getCCPDatabase().prepareStatement(STATIONTYPE);
			prepStmt.setString(1, Long.valueOf(stationID).toString());
			cursor = prepStmt.executeQuery();
			while (cursor.next()) {
				stationTypeID = cursor.getInt(STATIONTYPEID_COLINDEX);
			}
		} catch (Exception ex) {
			logger.warning("W- [SpingDatabaseConnector.searchStationType]> Database exception: " + ex.getMessage());
		} finally {
			try {
				if (cursor != null) cursor.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			try {
				if (prepStmt != null) prepStmt.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		logger.info(
				"~~ Time lapse for [SELECT STATIONTYPEID " + stationID + "] " + NeoComMSConnector.getSingleton().timeLapse());
		return stationTypeID;
	}

	//	public String searchTech4Blueprint(final int blueprintID) {
	//		throw new RuntimeException(
	//				"Application connector not defined. Functionality 'searchTech4Blueprint' disabled. Call intercepted by abstract class 'AbstractDatabaseConnector'.");
	//	}

	//	public int totalLocationContentCount(final long identifier) {
	//		try {
	//			Dao<NeoComAsset, String> assetDao = this.getAssetDAO();
	//			QueryBuilder<NeoComAsset, String> queryBuilder = assetDao.queryBuilder();
	//			queryBuilder.setCountOf(true).where().eq("locationID", identifier);
	//			long totalAssets = assetDao.countOf(queryBuilder.prepare());
	//			return Long.valueOf(totalAssets).intValue();
	//		} catch (java.sql.SQLException sqle) {
	//			sqle.printStackTrace();
	//			logger.warning("W [SpringDatabaseConnector.getLocationContentCount]> Exception reading Location contents count."
	//					+ sqle.getMessage());
	//			return 0;
	//		}
	//	}

	private Connection getCCPDatabase() {
		if (null == ccpDatabase) openCCPDataBase();
		return ccpDatabase;
	}

	private NeocomDBHelper getNeocomDBHelper() {
		if (null == neocomDBHelper) {
			logger.warning(
					"W> [StringDatabaseConnector.getNeocomDBHelper]> helper not found. Creating a new one with default database.");
			neocomDBHelper = new NeocomDBHelper(databaseLink, dbVersion);
		}
		return neocomDBHelper;
	}

}

// - UNUSED CODE ............................................................................................
