//	PROJECT:      Neocom.Microservices (NEOC-MS)
//	AUTHORS:      Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:    (c) 2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:	SpringBoot-MS-Java 1.8.
//	DESCRIPTION:	This is the integration project for all the web server pieces. This is the launcher for
//								the SpringBoot+MicroServices+Angular unified web application.
package org.dimensinfin.eveonline.neocom;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import org.dimensinfin.eveonline.neocom.connector.AppModelStore;
import org.dimensinfin.eveonline.neocom.connector.CCPDatabaseConnector;
import org.dimensinfin.eveonline.neocom.connector.ICCPDatabaseConnector;
import org.dimensinfin.eveonline.neocom.connector.ICacheConnector;
import org.dimensinfin.eveonline.neocom.connector.INeoComMSConnector;
import org.dimensinfin.eveonline.neocom.connector.INeoComModelDatabase;
import org.dimensinfin.eveonline.neocom.connector.MicroServicesCacheConnector;
import org.dimensinfin.eveonline.neocom.connector.NeoComMSConnector;
import org.dimensinfin.eveonline.neocom.connector.SpringDatabaseConnector;
import org.dimensinfin.eveonline.neocom.constant.R;
import org.dimensinfin.eveonline.neocom.interfaces.INeoComModelStore;
import org.joda.time.Duration;
import org.joda.time.Instant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.scheduling.annotation.EnableScheduling;

// - CLASS IMPLEMENTATION ...................................................................................
/**
 * This is the initial class and loader for the Spring Boot application. It will be used to integrate the
 * different modules and libraries, instantiate the adapters and serve as the base point to integrate the
 * different controllers. Most of the code will be imported and integrated from the depending libraries.
 * 
 * @author Adam Antinoo
 */
//@EnableCaching
@EnableCircuitBreaker
@EnableScheduling
//@EnableAsync
@SpringBootApplication
public class NeocomMicroServiceApplication implements INeoComMSConnector {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = Logger.getLogger("NeocomMicroServiceApplication");
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
	public static void main ( final String[] args ) {
		// Instance and connect the Adaptors.
		SpringApplication.run(NeocomMicroServiceApplication.class, args);
	}

	// - F I E L D - S E C T I O N ............................................................................
	private NeoComMSConnector _connector = null;
	private Instant chrono = null;

	private INeoComModelDatabase dbNeocomConnector = null;
	private ICCPDatabaseConnector dbCCPConnector = null;
	private ICacheConnector cacheConnector = null;

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public NeocomMicroServiceApplication () {
		logger.info(">> [NeocomMicroServiceApplication.<constructor>]");
		// Create and connect the adapters.
		//		if (null == singleton) {
		//			logger.info("-- [NeocomMicroServiceApplication.<constructor>]> Instantiating the singleton.");
		//			singleton = this;
		//		}
		_connector = new NeoComMSConnector(this);
		logger.info("<< [NeocomMicroServiceApplication.<constructor>]");
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	//	@Override
	//	public void addCharacterUpdateRequest(final long characterID) {
	//		this.getCacheConnector().addCharacterUpdateRequest(characterID);
	//	}

	public String getAppName () {
		return APPLICATION_NAME;
	}

	@Override
	public ICacheConnector getCacheConnector () {
		if (null == cacheConnector) cacheConnector = new MicroServicesCacheConnector();
		return cacheConnector;
	}

	@Override
	public ICCPDatabaseConnector getCCPDBConnector () {
		if (null == dbCCPConnector) {
			dbCCPConnector = new CCPDatabaseConnector();
		}
		return dbCCPConnector;
	}

	@Override
	public INeoComModelDatabase getDBConnector () {
		if (null == dbNeocomConnector) {
			String dblocation = R.getResourceString("R.string.appdatabasepath");
			String dbname = R.getResourceString("R.string.appdatabasefilename");
			String dbversion = R.getResourceString("R.string.databaseversion");
			dbNeocomConnector = new SpringDatabaseConnector(dblocation, dbname, dbversion);
			dbNeocomConnector.loadSeedData();
		}
		return dbNeocomConnector;
	}

	@Override
	public INeoComModelStore getModelStore () {
		return AppModelStore.getSingleton();
	}

	/**
	 * Run this after the application is initialized. The contents are to read back from persistence storage the
	 * cache contents before starting the application.
	 */
	@PostConstruct
	public void postConstruct () {
		logger.info(">> [MarketDataServiceApplication.postConstruct]");
		// Read back the cache contents
		MicroServicesCacheConnector.readCacheFromStorage();
		logger.info("<< [MarketDataServiceApplication.postConstruct]");
	}

	public void startChrono () {
		chrono = new Instant();
	}

	public Duration timeLapse () {
		return new Duration(chrono, new Instant());
	}
}
// - UNUSED CODE ............................................................................................
