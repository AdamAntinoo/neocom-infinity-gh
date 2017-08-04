//	PROJECT:      NeoCom.Databases (NEOC.D)
//	AUTHORS:      Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:    (c) 2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:	Java 1.8 Library.
//	DESCRIPTION:	SQLite database access library. Isolates Neocom database access from any
//					environment limits.
package org.dimensinfin.eveonline.neocom.database;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

import com.j256.ormlite.db.DatabaseTypeUtils;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.support.DatabaseConnection;

// - CLASS IMPLEMENTATION ...................................................................................
public class NeocomConnectionSource extends JdbcConnectionSource {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = Logger.getLogger("NeocomConnectionSource.java");

	// - F I E L D - S E C T I O N ............................................................................
	//	private DatabaseConnection	neocomDatabase	= null;

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public NeocomConnectionSource() {
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	/**
	 * Create a data source for a particular database URL.
	 * 
	 * @param url
	 *          The database URL which should start jdbc:...
	 * @throws SQLException
	 *           If the driver associated with the database driver is not found in the classpath.
	 */
	public NeocomConnectionSource(String url) throws SQLException {
		super(url, null, null, null);
	}

	public DatabaseConnection getReadWriteConnection(String tableName) throws SQLException {
		if (!initialized) { throw new SQLException(getClass().getSimpleName() + " was not initialized properly"); }
		if (connection != null) {
			if (connection.isClosed()) {
				throw new SQLException("Connection has already been closed");
			} else {
				return connection;
			}
		}
		connection = makeConnection(logger);
		return connection;
	}

	/**
	 * Initialize the class after the setters have been called. If you are using the no-arg constructor and
	 * Spring type wiring, this should be called after all of the set methods.
	 * 
	 * @throws SQLException
	 *           If the driver associated with the database URL is not found in the classpath.
	 */
	@Override
	public void initialize() throws SQLException {
		if (initialized) { return; }
		if (getUrl() == null) { throw new SQLException("url was never set on " + getClass().getSimpleName()); }
		if (databaseType == null) {
			databaseType = DatabaseTypeUtils.createDatabaseType(getUrl());
		}
		databaseType.loadDriver();
		databaseType.setDriver(DriverManager.getDriver(getUrl()));
		connection = makeConnection(logger);
		initialized = true;
	}

	/**
	 * Make a connection to the database.
	 * 
	 * @param logger
	 *          This is here so we can use the right logger associated with the sub-class.
	 */
	@SuppressWarnings("resource")
	protected DatabaseConnection makeConnection(Logger logger) throws SQLException {
		return super.makeConnection(LoggerFactory.getLogger(NeocomConnectionSource.class));
	}
}

// - UNUSED CODE ............................................................................................
