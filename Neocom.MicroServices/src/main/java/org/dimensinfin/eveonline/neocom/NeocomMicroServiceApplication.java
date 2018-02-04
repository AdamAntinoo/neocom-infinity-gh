//  PROJECT:     Neocom.Microservices (NEOC-MS)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Java 1.8 / SpringBoot-1.3.5 / Angular 5.0
//  DESCRIPTION: This is the SpringBoot MicroServices module to run the backend services to complete the web
//               application based on Angular+SB. This is the web version for the NeoCom Android native
//               application. Most of the source code is common to both platforms and this module includes
//               the source for the specific functionality for the backend services.
package org.dimensinfin.eveonline.neocom;

import org.dimensinfin.eveonline.neocom.conf.SpringBootConfigurationProvider;
import org.dimensinfin.eveonline.neocom.database.NeoComSBDBHelper;
import org.dimensinfin.eveonline.neocom.database.SDESBDBHelper;
import org.dimensinfin.eveonline.neocom.datamngmt.manager.GlobalDataManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.sql.SQLException;

// - CLASS IMPLEMENTATION ...................................................................................

/**
 * This is the initial class and loader for the Spring Boot application. It will be used to integrate the
 * different modules and libraries, instantiate the adapters and serve as the base point to integrate the
 * different controllers. Most of the code will be imported and integrated from the depending libraries.
 *
 * @author Adam Antinoo
 */
//@EnableCaching
//@EnableCircuitBreaker
@EnableScheduling
//@EnableAsync
@SpringBootApplication
public class NeocomMicroServiceApplication /*implements INeoComMSConnector*/ {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = LoggerFactory.getLogger("NeocomMicroServiceApplication");
	public static final String APPLICATION_NAME = "NeocomMicroServiceApplication";

	//	public static NeocomMicroServiceApplication	singleton					= null;
	//
	//	public static INeoComMSConnector getSingleton() {
	//		return singleton;
	//	}

	// - M A I N   E N T R Y P O I N T ........................................................................

	/**
	 * Just create the Spring application and launch it to run.
	 *
	 * @param args
	 */
	public static void main (final String[] args) {
		logger.info(">> [NeocomMicroServiceApplication.main]");
		// Instance and connect the Adaptors.
		// Connect the Configuration manager.
		logger.info(">> [NeocomMicroServiceApplication.main]> Connecting the Configuration Manager...");
		GlobalDataManager.connectConfigurationManager(new SpringBootConfigurationProvider(null));
		// Connect the NeoCom database.
		logger.info(">> [NeocomMicroServiceApplication.main]> Connecting NeoCom private database...");
		try {
			GlobalDataManager.connectNeoComDBConnector(new NeoComSBDBHelper()
					.setDatabaseHost(GlobalDataManager
							.getResourceString("R.database.neocom.databasehost", "jdbc:mysql://localhost:3306"))
					.setDatabaseName("neocom")
					.setDatabaseUser("NEOCOMTEST")
					.setDatabasePassword("01.Alpha")
					.setDatabaseVersion(GlobalDataManager.getResourceInt("R.database.neocom.databaseversion"))
					.build()
			);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		// Connect the SDE database.
		logger.info(">> [NeocomMicroServiceApplication.main]> Connecting SDE database...");
		try {
			GlobalDataManager.connectSDEDBConnector(new SDESBDBHelper()
					.setDatabaseSchema("jdbc:sqlite")
					.setDatabasePath("src/main/resources/")
					.setDatabaseName("eve.db")
					.build()
			);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		logger.info(">> [NeocomMicroServiceApplication.main]> Starting application instance...");
		SpringApplication.run(NeocomMicroServiceApplication.class, args);
		logger.info("<< [NeocomMicroServiceApplication.main]");
	}

	// - F I E L D - S E C T I O N ............................................................................
//	private NeoComMSConnector _connector = null;
//	private Instant chrono = null;

//	private INeoComModelDatabase dbNeocomConnector = null;
//	private ICCPDatabaseConnector dbCCPConnector = null;
//	private ICacheConnector cacheConnector = null;

	// - C O N S T R U C T O R - S E C T I O N ................................................................
//	public NeocomMicroServiceApplication () {
//		logger.info(">> [NeocomMicroServiceApplication.<constructor>]");
//		// Create and connect the adapters.
//		//		if (null == singleton) {
//		//			logger.info("-- [NeocomMicroServiceApplication.<constructor>]> Instantiating the singleton.");
//		//			singleton = this;
//		//		}
//		_connector = new NeoComMSConnector(this);
//		logger.info("<< [NeocomMicroServiceApplication.<constructor>]");
//	}

	// - M E T H O D - S E C T I O N ..........................................................................
	//	@Override
	//	public void addCharacterUpdateRequest(final long characterID) {
	//		this.getCacheConnector().addCharacterUpdateRequest(characterID);
	//	}

	public String getAppName () {
		return APPLICATION_NAME;
	}

//	@Override
//	public ICacheConnector getCacheConnector () {
//		if (null == cacheConnector) cacheConnector = new MicroServicesCacheConnector();
//		return cacheConnector;
//	}
//
//	@Override
//	public ICCPDatabaseConnector getCCPDBConnector () {
//		if (null == dbCCPConnector) {
//			dbCCPConnector = new CCPDatabaseConnector();
//		}
//		return dbCCPConnector;
//	}
//
//	@Override
//	public INeoComModelDatabase getDBConnector () {
//		if (null == dbNeocomConnector) {
//			String dblocation = R.getResourceString("R.string.appdatabasepath");
//			String dbname = R.getResourceString("R.string.appdatabasefilename");
//			String dbversion = R.getResourceString("R.string.databaseversion");
//			dbNeocomConnector = new SpringDatabaseConnector(dblocation, dbname, dbversion);
//			dbNeocomConnector.loadSeedData();
//		}
//		return dbNeocomConnector;
//	}
//
//	@Override
//	public INeoComModelStore getModelStore () {
//		return AppModelStore.getSingleton();
//	}

	/**
	 * Run this after the application is initialized. The contents are to read back from persistence storage the
	 * cache contents before starting the application.
	 */
	@PostConstruct
	public void postConstruct () {
		logger.info(">> [NeocomMicroServiceApplication.postConstruct]");
		// Read back the cache contents
		GlobalDataManager.readMarketDataCacheFromStorage();
		logger.info("<< [NeocomMicroServiceApplication.postConstruct]");
	}
//
//	public void startChrono () {
//		chrono = new Instant();
//	}
//
//	public Duration timeLapse () {
//		return new Duration(chrono, new Instant());
//	}
}
// - UNUSED CODE ............................................................................................
