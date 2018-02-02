//  PROJECT:     Neocom.Microservices (NEOC-MS)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Java 1.8 / SpringBoot-1.3.5 / Angular 5.0
//  DESCRIPTION: This is the SpringBoot MicroServices module to run the backend services to complete the web
//               application based on Angular+SB. This is the web version for the NeoCom Android native
//               application. Most of the source code is common to both platforms and this module includes
//               the source for the specific functionality for the backend services.
package org.dimensinfin.eveonline.neocom.database;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

/**
 * @author Adam Antinoo
 */
// - CLASS IMPLEMENTATION ...................................................................................
public class SDESBDBHelper implements ISDEDBHelper{
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = LoggerFactory.getLogger(NeoComSBDBHelper.class);
//	private static final String CCPDATABASE_URL = "jdbc:sqlite:src/main/resources/eve.db-this reference is platform specific-";
private static String DEFAULT_CONNECTION_DESCRIPTOR = "jdbc:sqlite:src/main/resources/eve.db";

	// - F I E L D   I N D E X   D E F I N I T I O N S
	private static int ITEMGROUP_GROUPID_COLINDEX = 1;
	private static int ITEMGROUP_CATEGORYID_COLINDEX = 2;
	private static int ITEMGROUP_GROUPNAME_COLINDEX = 3;
	private static int ITEMGROUP_ICONLINKNAME_COLINDEX = 4;
	private static int ITEMCATEGORY_CATEGORYID_COLINDEX = 1;
	private static int ITEMCATEGORY_CATEGORYNAME_COLINDEX = 2;
	private static int ITEMCATEGORY_ICONLINKNAME_COLINDEX = 3;

	// - S Q L   C O M M A N D S
	private static final String SELECT_ITEMGROUP = "SELECT ig.groupID AS groupID"
			+ " , ig.categoryID AS categoryID"
			+ " , ig.groupName AS groupName"
			+ " , ei.iconFile AS iconLinkName"
			+ " FROM invGroups ig"
			+ " LEFT OUTER JOIN eveIcons ei ON ig.iconID = ei.iconID"
			+ " WHERE ig.groupID = ?";
	private static final String SELECT_ITEMCATEGORY = "SELECT ic.categoryID AS categoryID"
			+ " , ic.categoryName AS categoryName"
			+ " , ei.iconFile AS iconLinkName"
			+ " FROM invCategories ic"
			+ " LEFT OUTER JOIN eveIcons ei ON ic.iconID = ei.iconID"
			+ " WHERE ic.categoryID = ?";

	// - F I E L D - S E C T I O N ............................................................................
	private String schema = "jdbc:sqlite";
	private String databasePath = "src/main/resources/";
	private String databaseName = "eve.db";
//	private int databaseCurrentVersion = 0;
	private boolean databaseValid = false;
	private JdbcPooledConnectionSource connectionSource = null;
	private Connection ccpDatabase = null;

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public SDESBDBHelper () {
		super();
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	public ISDEDBHelper setDatabaseSchema (final String newschema) {
		this.schema = newschema;
		return this;
	}

	public ISDEDBHelper setDatabasePath (final String newpath) {
		this.databasePath = newpath;
		return this;
	}

	public ISDEDBHelper setDatabaseName (final String instanceName) {
		this.databaseName = instanceName;
		return this;
	}

//	public ISDEDBHelper setDatabaseVersion (final int newVersion) {
//		this.databaseCurrentVersion = newVersion;
//		return this;
//	}

	public ISDEDBHelper build () throws SQLException {
		if (StringUtils.isEmpty(schema))
			throw new SQLException("Cannot create connection: 'schema' is empty.");
		if (StringUtils.isEmpty(databasePath))
			throw new SQLException("Cannot create connection: 'databasePath' is empty.");
		if (StringUtils.isEmpty(databaseName))
			throw new SQLException("Cannot create connection: 'databaseName' is empty.");
		databaseValid = true;
		createConnectionSource();
		return this;
	}

//	public boolean openCCPDataBase () {
//		if (null == ccpDatabase) {
//			try {
//				Class.forName("org.sqlite.JDBC");
//				ccpDatabase = DriverManager.getConnection(CCPDatabaseModelConnector.CCPDATABASE_URL);
//				ccpDatabase.setAutoCommit(false);
//			} catch (Exception sqle) {
//				logger.warn("W- [CCPDatabaseModelConnector.openCCPDataBase]> "
//						+ sqle.getClass().getSimpleName() + ": " + sqle.getMessage());
//			}
//			logger
//					.info("-- [CCPDatabaseModelConnector.openCCPDataBase]> Opened CCP database successfully.");
//		}
//		return true;
//	}

	private void createConnectionSource () throws SQLException {
		final String localConnectionDescriptor = schema + ":" + databasePath + databaseName;
		if (databaseValid) connectionSource = new JdbcPooledConnectionSource(localConnectionDescriptor);
		else connectionSource = new JdbcPooledConnectionSource(DEFAULT_CONNECTION_DESCRIPTOR);
		// only keep the connections open for 5 minutes
		connectionSource.setMaxConnectionAgeMillis(TimeUnit.MINUTES.toMillis(5));
		// change the check-every milliseconds from 30 seconds to 60
		connectionSource.setCheckConnectionsEveryMillis(TimeUnit.SECONDS.toMillis(60));
		// for extra protection, enable the testing of connections
		// right before they are handed to the user
		connectionSource.setTestBeforeGet(true);
	}

	@Override
	public String toString () {
		StringBuffer buffer = new StringBuffer("NeoComSBDBHelper [");
		final String localConnectionDescriptor = schema + ":" + databasePath + databaseName;
		buffer.append("Descriptor: ").append(localConnectionDescriptor);
		buffer.append("]");
		//		buffer.append("->").append(super.toString());
		return buffer.toString();
	}
}
// - UNUSED CODE ............................................................................................
//[01]
