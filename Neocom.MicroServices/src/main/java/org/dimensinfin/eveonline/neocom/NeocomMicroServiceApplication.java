//	PROJECT:      Neocom.Microservices (NEOC-MS)
//	AUTHORS:      Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:    (c) 2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:	SpringBoot-MS-Java 1.8.
//	DESCRIPTION:	This is the integration project for all the web server pieces. This is the launcher for
//								the SpringBoot+MicroServices+Angular unified web application.
package org.dimensinfin.eveonline.neocom;

import java.util.logging.Logger;

import org.dimensinfin.eveonline.neocom.connector.AppConnector;
import org.dimensinfin.eveonline.neocom.connector.AppModelStore;
import org.dimensinfin.eveonline.neocom.connector.CCPDatabaseConnector;
import org.dimensinfin.eveonline.neocom.connector.ICCPDatabaseConnector;
import org.dimensinfin.eveonline.neocom.connector.ICacheConnector;
import org.dimensinfin.eveonline.neocom.connector.IConnector;
import org.dimensinfin.eveonline.neocom.connector.IDatabaseConnector;
import org.dimensinfin.eveonline.neocom.connector.IStorageConnector;
import org.dimensinfin.eveonline.neocom.connector.MicroServicesCacheConnector;
import org.dimensinfin.eveonline.neocom.connector.SpringDatabaseConnector;
import org.dimensinfin.eveonline.neocom.constant.R;
import org.dimensinfin.eveonline.neocom.interfaces.INeoComModelStore;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

// - CLASS IMPLEMENTATION ...................................................................................
/**
 * This is the initial class and loader for the Spring Boot application. It will be used to integrate the
 * different modules and libraries, instantiate the adapters and serve as the base point to integrate the
 * different controllers. Most of the code will be imported and integrated from the depending libraries.
 * 
 * @author Adam Antinoo
 */
//@EnableCaching
@SpringBootApplication
@ImportResource(value = "classpath*:hsql_configuration.xml")
//@EnableScheduling
public class NeocomMicroServiceApplication implements IConnector/* ,CacheResolver */ {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger												logger						= Logger.getLogger("NeocomMicroServiceApplication");
	public static final String									APPLICATION_NAME	= "NeocomMicroServiceApplication";
	public static NeocomMicroServiceApplication	singleton					= null;

	// - M A I N   E N T R Y P O I N T ........................................................................
	/**
	 * Just create the Spring application and launch it to run.
	 * 
	 * @param args
	 */
	public static void main(final String[] args) {
		// Instance and connect the Adaptors.
		SpringApplication.run(NeocomMicroServiceApplication.class, args);
	}

	// - F I E L D - S E C T I O N ............................................................................
	private IDatabaseConnector		dbNeocomConnector	= null;
	private ICCPDatabaseConnector	dbCCPConnector		= null;
	private ICacheConnector				cacheConnector		= null;
	//	@Autowired
	//	public CacheManager					cacheManager;

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public NeocomMicroServiceApplication() {
		logger.info(">> [NeocomMicroServiceApplication.<constructor>]");
		// Create and connect the adapters.
		if (null == singleton) {
			logger.info("-- [NeocomMicroServiceApplication.<constructor>]> Instantiating the singleton.");
			singleton = this;
		}
		AppConnector.setConnector(singleton);
		logger.info("<< [NeocomMicroServiceApplication.<constructor>]");
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	@Override
	public void addCharacterUpdateRequest(final long characterID) {
		this.getCacheConnector().addCharacterUpdateRequest(characterID);
	}

	public String getAppName() {
		return APPLICATION_NAME;
	}
	//	@Bean
	//	public CacheManagerCustomizer<ConcurrentMapCacheManager> cacheManagerCustomizer() {
	//		return new CacheManagerCustomizer<ConcurrentMapCacheManager>() {
	//			@Override
	//			public void customize(ConcurrentMapCacheManager cacheManager) {
	//				cacheManager.setAllowNullValues(false);
	//			}
	//		};
	//	}

	@Override
	public ICacheConnector getCacheConnector() {
		if (null == cacheConnector) cacheConnector = new MicroServicesCacheConnector();
		return cacheConnector;
	}

	@Override
	public ICCPDatabaseConnector getCCPDBConnector() {
		if (null == dbCCPConnector) {
			dbCCPConnector = new CCPDatabaseConnector();
		}
		return dbCCPConnector;
	}

	@Override
	public IDatabaseConnector getDBConnector() {
		if (null == dbNeocomConnector) {
			String dblocation = R.getResourceString("R.string.appdatabasepath");
			String dbname = R.getResourceString("R.string.appdatabasefilename");
			String dbversion = R.getResourceString("R.string.databaseversion");
			dbNeocomConnector = new SpringDatabaseConnector(dblocation, dbname, dbversion);
			dbNeocomConnector.loadSeedData();
		}
		return dbNeocomConnector;
	}

	public INeoComModelStore getModelStore() {
		return AppModelStore.getSingleton();
	}

	public NeocomMicroServiceApplication getSingletonApp() {
		return NeocomMicroServiceApplication.singleton;
	}

	@Override
	public IStorageConnector getStorageConnector() {
		return null;
	}
}
// - UNUSED CODE ............................................................................................
