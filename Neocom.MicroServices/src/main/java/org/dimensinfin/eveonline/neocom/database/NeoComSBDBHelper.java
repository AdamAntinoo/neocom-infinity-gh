//  PROJECT:     Neocom.Microservices (NEOC-MS)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Java 1.8 / SpringBoot-1.3.5 / Angular 5.0
//  DESCRIPTION: This is the SpringBoot MicroServices module to run the backend services to complete the web
//               application based on Angular+SB. This is the web version for the NeoCom Android native
//               application. Most of the source code is common to both platforms and this module includes
//               the source for the specific functionalities for the backend services.
package org.dimensinfin.eveonline.neocom.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import org.apache.commons.lang3.StringUtils;

import org.dimensinfin.eveonline.neocom.database.entity.Colony;
import org.dimensinfin.eveonline.neocom.database.entity.ColonySerialized;
import org.dimensinfin.eveonline.neocom.database.entity.ColonyStorage;
import org.dimensinfin.eveonline.neocom.database.entity.Credential;
import org.dimensinfin.eveonline.neocom.database.entity.TimeStamp;
import org.dimensinfin.eveonline.neocom.datamngmt.manager.GlobalDataManager;
import org.dimensinfin.eveonline.neocom.model.ApiKey;
import org.dimensinfin.eveonline.neocom.database.entity.DatabaseVersion;
import org.dimensinfin.eveonline.neocom.model.NeoComAsset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.sql.Time;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * NeoCom private database connector that will have the same api as the connector to be used on Android. This
 * version already uses the mySql database JDBC implementation instead the SQLite copied from the Android
 * platform.
 * The class will encapsulate all dao and connection access.
 *
 * @author Adam Antinoo
 */

// - CLASS IMPLEMENTATION ...................................................................................
public class NeoComSBDBHelper implements INeoComDBHelper {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = LoggerFactory.getLogger(NeoComSBDBHelper.class);
	private static String DEFAULT_CONNECTION_DESCRIPTOR = "jdbc:mysql://localhost:3306/neocom?user=NEOCOMTEST&password=01.Alpha";

	// - F I E L D - S E C T I O N ............................................................................
	private String hostName = "";
	private String databaseName = "";
	private String databaseUser = "";
	private String databasePassword = "";
	private int databaseCurrentVersion = 0;
	private boolean databaseValid = false;
	private JdbcPooledConnectionSource connectionSource = null;

	private Dao<DatabaseVersion, String> versionDao = null;
	private Dao<TimeStamp, String> timeStampDao = null;
	private Dao<ApiKey, String> apiKeysDao = null;
	private Dao<Credential, String> credentialDao = null;
	private Dao<Colony, String> colonyDao = null;
	private Dao<ColonyStorage, String> colonyStorageDao = null;
	private Dao<ColonySerialized, String> colonySerializedDao = null;
	private Dao<NeoComAsset, String> assetDao = null;

	private DatabaseVersion storedVersion = null;

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public NeoComSBDBHelper () {
		super();
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	public int getDatabaseVersion () {
		return databaseCurrentVersion;
	}

	public ConnectionSource getConnectionSource () throws SQLException {
		if ( null == connectionSource ) createConnectionSource();
		return connectionSource;
	}

	public INeoComDBHelper setDatabaseHost (final String hostName) {
		this.hostName = hostName;
		return this;
	}

	public INeoComDBHelper setDatabaseName (final String instanceName) {
		this.databaseName = instanceName;
		return this;
	}

	public INeoComDBHelper setDatabaseUser (final String user) {
		this.databaseUser = user;
		return this;
	}

	public INeoComDBHelper setDatabasePassword (final String password) {
		this.databasePassword = password;
		return this;
	}

	public INeoComDBHelper setDatabaseVersion (final int newVersion) {
		this.databaseCurrentVersion = newVersion;
		return this;
	}

	public INeoComDBHelper build () throws SQLException {
		if ( StringUtils.isEmpty(hostName) )
			throw new SQLException("Cannot create connection: 'hostName' is empty.");
		if ( StringUtils.isEmpty(databaseName) )
			throw new SQLException("Cannot create connection: 'databaseName' is empty.");
		if ( StringUtils.isEmpty(databaseUser) )
			throw new SQLException("Cannot create connection: 'databaseUser' is empty.");
		if ( StringUtils.isEmpty(databasePassword) )
			throw new SQLException("Cannot create connection: 'databasePassword' is empty.");
		databaseValid = true;
		createConnectionSource();
		return this;
	}

	public int getStoredVersion () {
		if ( null == storedVersion ) {
			// Access the version object persistent on the database.
			try {
				//			Dao<DatabaseVersion, String> versionDao = this.getVersionDao();
				QueryBuilder<DatabaseVersion, String> queryBuilder = getVersionDao().queryBuilder();
				PreparedQuery<DatabaseVersion> preparedQuery = queryBuilder.prepare();
				List<DatabaseVersion> versionList = versionDao.query(preparedQuery);
				if ( versionList.size() > 0 ) {
					storedVersion = versionList.get(0);
					return storedVersion.getVersionNumber();
				} else
					return 0;
			} catch (SQLException sqle) {
				logger.warn("W- [NeocomDBHelper.getStoredVersion]> Database exception: " + sqle.getMessage());
				return 0;
			} catch (RuntimeException rtex) {
				logger.warn("W- [NeocomDBHelper.getStoredVersion]> Database exception: " + rtex.getMessage());
				return 0;
			}
		} else return storedVersion.getVersionNumber();
	}

	public void onCreate (final ConnectionSource databaseConnection) {
		logger.info(">> [NeoComSBDBHelper.onCreate]");
		// Create the tables that do not exist
		try {
			TableUtils.createTableIfNotExists(databaseConnection, DatabaseVersion.class);
		} catch (SQLException sqle) {
			logger.warn("SQL [NeoComSBDBHelper.onCreate]> SQL NeoComDatabase: {}", sqle.getMessage());
		}
		try {
			TableUtils.createTableIfNotExists(databaseConnection, TimeStamp.class);
		} catch (SQLException sqle) {
			logger.warn("SQL [NeoComSBDBHelper.onCreate]> SQL NeoComDatabase: {}", sqle.getMessage());
		}
		try {
			TableUtils.createTableIfNotExists(databaseConnection, ApiKey.class);
		} catch (SQLException sqle) {
			logger.warn("SQL [NeoComSBDBHelper.onCreate]> SQL NeoComDatabase: {}", sqle.getMessage());
		}
		try {
			TableUtils.createTableIfNotExists(databaseConnection, Credential.class);
		} catch (SQLException sqle) {
			logger.warn("SQL [NeoComSBDBHelper.onCreate]> SQL NeoComDatabase: {}", sqle.getMessage());
		}
		try {
			TableUtils.createTableIfNotExists(databaseConnection, ColonyStorage.class);
		} catch (SQLException sqle) {
			logger.warn("SQL [NeoComSBDBHelper.onCreate]> SQL NeoComDatabase: {}", sqle.getMessage());
		}
		//		try {
		//			TableUtils.createTableIfNotExists(databaseConnection, Property.class);
		//		} catch (SQLException sqle) {
		//		}
		//		try {
		//			TableUtils.createTableIfNotExists(databaseConnection, ResourceList.class);
		//		} catch (SQLException sqle) {
		//		}
		//		try {
		//			TableUtils.createTableIfNotExists(databaseConnection, PlanetaryResource.class);
		//		} catch (SQLException sqle) {
		//		}
		//		try {
		//			TableUtils.createTableIfNotExists(databaseConnection, NeoComAsset.class);
		//		} catch (SQLException sqle) {
		//		}
		//		try {
		//			TableUtils.createTableIfNotExists(databaseConnection, NeoComBlueprint.class);
		//		} catch (SQLException sqle) {
		//		}
		//		try {
		//			TableUtils.createTableIfNotExists(databaseConnection, Job.class);
		//		} catch (SQLException sqle) {
		//		}
		//		try {
		//			TableUtils.createTableIfNotExists(databaseConnection, NeoComMarketOrder.class);
		//		} catch (SQLException sqle) {
		//		}
		//		try {
		//			TableUtils.createTableIfNotExists(databaseConnection, EveLocation.class);
		//		} catch (SQLException sqle) {
		//		}
		this.loadSeedData();
		logger.info("<< [NeoComSBDBHelper.onCreate]");
	}

	public void onUpgrade (final ConnectionSource databaseConnection, final int oldVersion, final int newVersion) {
		// Execute different actions depending on the new version.
		if ( oldVersion < 109 ) {
			try {
				// Drop all the tables to force a new update from the latest SQLite version.
				TableUtils.dropTable(databaseConnection, DatabaseVersion.class, true);
				DatabaseVersion version = new DatabaseVersion(newVersion);
			} catch (RuntimeException rtex) {
				logger.error("E> Error dropping table on Database new version.");
				rtex.printStackTrace();
			} catch (SQLException sqle) {
				logger.error("E> Error dropping table on Database new version.");
				sqle.printStackTrace();
			}
			try {
				// Drop all the tables to force a new update from the latest SQLite version.
				TableUtils.dropTable(databaseConnection, TimeStamp.class, true);
			} catch (RuntimeException rtex) {
				logger.error("E> Error dropping table on Database new version.");
				rtex.printStackTrace();
			} catch (SQLException sqle) {
				logger.error("E> Error dropping table on Database new version.");
				sqle.printStackTrace();
			}
			try {
				// Drop all the tables to force a new update from the latest SQLite version.
				TableUtils.dropTable(databaseConnection, ApiKey.class, true);
			} catch (RuntimeException rtex) {
				logger.error("E> Error dropping table on Database new version.");
				rtex.printStackTrace();
			} catch (SQLException sqle) {
				logger.error("E> Error dropping table on Database new version.");
				sqle.printStackTrace();
			}
			try {
				// Drop all the tables to force a new update from the latest SQLite version.
				TableUtils.dropTable(databaseConnection, Credential.class, true);
			} catch (RuntimeException rtex) {
				logger.error("E> Error dropping table on Database new version.");
				rtex.printStackTrace();
			} catch (SQLException sqle) {
				logger.error("E> Error dropping table on Database new version.");
				sqle.printStackTrace();
			}
			try {
				// Drop all the tables to force a new update from the latest SQLite version.
				TableUtils.dropTable(databaseConnection, NeoComAsset.class, true);
			} catch (RuntimeException rtex) {
				logger.error("E> Error dropping table on Database new version.");
				rtex.printStackTrace();
			} catch (SQLException sqle) {
				logger.error("E> Error dropping table on Database new version.");
				sqle.printStackTrace();
			}
		}
	}

	/**
	 * Checks if the key tables had been cleaned and then reinserts the seed data on them.
	 */
	public void loadSeedData () {
		logger.info(">> [NeoComSBDBHelper.loadSeedData]");
		// Add seed data to the new database is the tables are empty.
		try {
			//---  D A T A B A S E    V E R S I O N
			logger.info("-- [NeoComSBDBHelper.loadSeedData]> Loading DatabaseVersion");
			Dao<DatabaseVersion, String> version = this.getVersionDao();
			QueryBuilder<DatabaseVersion, String> queryBuilder = version.queryBuilder();
			queryBuilder.setCountOf(true);
			// Check that at least one Version record exists on the database. It is a singleton.
			long records = version.countOf(queryBuilder.prepare());
			logger.info("-- [NeoComSBDBHelper.loadSeedData]> DatabaseVersion records: " + records);

			// If the table is empty then insert the seeded Api Keys
			if ( records < 1 ) {
				DatabaseVersion key = new DatabaseVersion(GlobalDataManager.getResourceInt("R.database.neocom.databaseversion"));
				logger.info("-- [NeoComSBDBHelper.loadSeedData]> Setting DatabaseVersion to: " + key.getVersionNumber());
			}
		} catch (SQLException sqle) {
			logger.error("E [NeoComSBDBHelper.loadSeedData]> Error creating the initial table on the app database.");
			sqle.printStackTrace();
		}

		//--- A P I   K E Y S
		try {
			Dao<ApiKey, String> apikey = this.getApiKeysDao();
			QueryBuilder<ApiKey, String> queryBuilder = apikey.queryBuilder();
			queryBuilder.setCountOf(true);
			long records = apikey.countOf(queryBuilder.prepare());

			// If the table is empty then insert the seeded Api Keys
			if ( records < 1 ) {
				//				ApiKey key = new ApiKey("Beth Ripley").setKeynumber(2889577)
				//				                                      .setValidationcode("Mb6iDKR14m9Xjh9maGTQCGTkpjRHPjOgVUkvK6E9r6fhMtOWtipaqybp0qCzxuuw")
				//				                                      .setActive(true);
				//				key = new ApiKey("Perico").setKeynumber(3106761)
				//				                          .setValidationcode("gltCmvVoZl5akrM8d6DbNKZn7Jm2SaukrmqjnSOyqKbvzz5CtNfknTEwdBe6IIFf").setActive(false);
				//				ApiKey		key = new ApiKey("CapitanHaddock09").setKeynumber(924767)
				//				                                    .setValidationcode("2qBKUY6I9ozYhKxYUBPnSIix0fHFCqveD1UEAv0GbYqLenLLTIfkkIWeOBejKX5P").setActive(true);
				//				key = new ApiKey("CapitanHaddock29").setKeynumber(6472981)
				//				                                    .setValidationcode("pj1NJKKb0pNO8LTp0qN2yJSxZoZUO0UYYq8qLtOeFXNsNBRpiz7orcqVAu7UGF7z").setActive(true);
				ApiKey key = new ApiKey("CapitanHaddock").setKeynumber(924767)
						.setValidationcode("2qBKUY6I9ozYhKxYUBPnSIix0fHFCqveD1UEAv0GbYqLenLLTIfkkIWeOBejKX5P").setActive(false);
				key = new ApiKey("CapitanHaddock").setKeynumber(6472981)
						.setValidationcode("pj1NJKKb0pNO8LTp0qN2yJSxZoZUO0UYYq8qLtOeFXNsNBRpiz7orcqVAu7UGF7z").setActive(false);
			}
		} catch (SQLException sqle) {
			logger.error("E> Error creating the initial table on the app database.");
			sqle.printStackTrace();
		}

		//--- C R E D E N T I A L S
		try {
			final long records = this.getCredentialDao().countOf();
			// If the table is empty then insert the seeded Credentials
			if ( records < 1 ) {
				Credential credential = new Credential(91734031)
						.setAccessToken("m58y5NBSK7T_1ki9jx4XsGgfu4laHIF9-3WRLeNqkABe-VKZ57tGFee8kpwFBO8RTtSIrHyz9UKtC17clitqsw2")
						.setAccountName("Zach Zender")
						.setRefreshToken("HB68Z3aeNjQxpA8ebcNijfMGv9wfkcn-dkcy5qchW88Pe0ackWDHCy2yr5RrY_ERE4aKNCsR-J2a_-V_tS2sV_21HMTYcIKQ-QJHhz6GugotFfrdRcl6nsVjEuxOay5c7t-0tFu2diGy-2cF9y4qYCJ53da5slsLjNBWiIvUTxP7PUOQIs0y23_LhMPku1O1AXZsKG383NOLYQTCFL6vrVjThKJKXX9xRqm2rsRoe7xA_hyV0PiSmxjclUl9XzY")
						.store();
				credential = new Credential(92223647)
						.setAccessToken("Su9nYs3_qDQBHB2utYKQyZVfDy1l4ScMo81rtiDmDTDpRKV4yIln_cxfXDQaAR81wj1oBu8S1Hjxbf7VcJavaA2")
						.setAccountName("Beth Ripley")
						.setRefreshToken("NyjPkFKg1nr1nBK1e8bSaezKENbLZKtXOu0hkvnbK1LyghAHim-shdiXjMXZ8z8uQwCxUGPmow-BnSF5BX--zvbKI_bEQ5tGE6jiCZNKv0EoUrM205wRtq7QEWt-I0E51_YzMMHW05YWAG7ds4I72fsKJMtA0HZmfrtRQtf6q_tCoGErf0cpwuwHtNxeTg87UkEqEXicWHAdRRXTHONtDqrWiZbzOL48BQNXcgV3goL-hMzzi0V6sY1JolAxQ47")
						.store();
				credential = new Credential(93813310)
						.setAccessToken("_WWshtZkjlNwXLRmvs3T0ZUaKAVo4QEl6JFwzIVIyNmdgjfqHhb41uY7ambYFDmjZsFZyLBjtH-90ONWu1E1sA2")
						.setAccountName("Perico Tuerto")
						.setRefreshToken("_rOthuCEPyRdKjNv6XyX84dguFmSkK4byrP3tTOj0Kv_3F_8GBvxsrUhrFZoRQPCjXXgzn5n0a5gdLeWA_hlS8Uv0LsK6upwKz2kfyG3mlANsAxfIDa2iGaGKq1pmFpe2w3lYuHl8cKGCItzL9uW4LL8gc8Uznqi9_jFNYC3Z-AXAPKNwN7hwQxcV7Znn2aprUC5BjaKrhBin-ptEPyVnNYvqBRBdXHYQcc-m4aaPu-4qD4lK4PXbcZanxrfDP_")
						.store();
			}
		} catch (SQLException sqle) {
			logger.error("E> Error creating the initial table on the app database.");
			sqle.printStackTrace();
		}
		logger.info("<< [NeoComSBDBHelper.loadSeedData]");
	}

	@Override
	public Dao<DatabaseVersion, String> getVersionDao () throws SQLException {
		if ( null == versionDao ) {
			versionDao = DaoManager.createDao(this.getConnectionSource(), DatabaseVersion.class);
		}
		return versionDao;
	}

	@Override
	public Dao<TimeStamp, String> getTimeStampDao () throws SQLException {
		if ( null == timeStampDao ) {
			timeStampDao = DaoManager.createDao(this.getConnectionSource(), TimeStamp.class);
		}
		return timeStampDao;
	}

	@Override
	public Dao<ApiKey, String> getApiKeysDao () throws SQLException {
		if ( null == apiKeysDao ) {
			apiKeysDao = DaoManager.createDao(this.getConnectionSource(), ApiKey.class);
		}
		return apiKeysDao;
	}

	@Override
	public Dao<Credential, String> getCredentialDao () throws SQLException {
		if ( null == credentialDao ) {
			credentialDao = DaoManager.createDao(this.getConnectionSource(), Credential.class);
		}
		return credentialDao;
	}

	@Override
	public Dao<Colony, String> getColonyDao () throws SQLException {
		if ( null == colonyDao ) {
			colonyDao = DaoManager.createDao(this.getConnectionSource(), Colony.class);
		}
		return colonyDao;
	}

	@Override
	public Dao<ColonyStorage, String> getColonyStorageDao () throws SQLException {
		if ( null == colonyStorageDao ) {
			colonyStorageDao = DaoManager.createDao(this.getConnectionSource(), ColonyStorage.class);
		}
		return colonyStorageDao;
	}

	public Dao<ColonySerialized, String> getColonySerializedDao () throws SQLException {
		if ( null == colonySerializedDao ) {
			colonySerializedDao = DaoManager.createDao(this.getConnectionSource(), ColonySerialized.class);
		}
		return colonySerializedDao;
	}

	public Dao<NeoComAsset, String> getAssetDao () throws SQLException {
		if ( null == assetDao ) {
			assetDao = DaoManager.createDao(this.getConnectionSource(), NeoComAsset.class);
		}
		return assetDao;
	}

	private void createConnectionSource () throws SQLException {
		//		try {
		final String localConnectionDescriptor = hostName + "/" + databaseName + "?user=" + databaseUser + "&password=" + databasePassword;
		if ( databaseValid ) connectionSource = new JdbcPooledConnectionSource(localConnectionDescriptor);
		else connectionSource = new JdbcPooledConnectionSource(DEFAULT_CONNECTION_DESCRIPTOR);
		// only keep the connections open for 5 minutes
		connectionSource.setMaxConnectionAgeMillis(TimeUnit.MINUTES.toMillis(5));
		// change the check-every milliseconds from 30 seconds to 60
		connectionSource.setCheckConnectionsEveryMillis(TimeUnit.SECONDS.toMillis(60));
		// for extra protection, enable the testing of connections
		// right before they are handed to the user
		connectionSource.setTestBeforeGet(true);
		//		} catch (SQLException sqle) {
		//			sqle.printStackTrace();
		//		}
	}

	@Override
	public String toString () {
		StringBuffer buffer = new StringBuffer("NeoComSBDBHelper [");
		final String localConnectionDescriptor = hostName + "/" + databaseName + "?user=" + databaseUser + "&password=" + databasePassword;
		buffer.append("Descriptor: ").append(localConnectionDescriptor);
		buffer.append("]");
		//		buffer.append("->").append(super.toString());
		return buffer.toString();
	}
}
// - UNUSED CODE ............................................................................................
//[01]
