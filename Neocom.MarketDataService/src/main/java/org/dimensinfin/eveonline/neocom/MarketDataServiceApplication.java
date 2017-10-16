//	PROJECT:      Neocom.Microservices (NEOC-MS)
//	AUTHORS:      Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:    (c) 2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:	SpringBoot-MS-Java 1.8.
//	DESCRIPTION:	This is the integration project for all the web server pieces. This is the launcher for
//								the SpringBoot+MicroServices+Angular unified web application.
package org.dimensinfin.eveonline.neocom;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.dimensinfin.eveonline.neocom.connector.CCPDatabaseConnector;
import org.dimensinfin.eveonline.neocom.connector.ICCPDatabaseConnector;
import org.dimensinfin.eveonline.neocom.connector.ICacheConnector;
import org.dimensinfin.eveonline.neocom.connector.IDatabaseConnector;
import org.dimensinfin.eveonline.neocom.connector.INeoComMSConnector;
import org.dimensinfin.eveonline.neocom.connector.MarketDataServiceCacheConnector;
import org.dimensinfin.eveonline.neocom.connector.NeoComMSConnector;
import org.dimensinfin.eveonline.neocom.interfaces.INeoComModelStore;
import org.dimensinfin.eveonline.neocom.services.MarketDataServer;
import org.joda.time.Duration;
import org.joda.time.Instant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

// - CLASS IMPLEMENTATION ...................................................................................
/**
 * This is an standalone Micro Service with Hystrix and statistics that should manage all Market Data
 * information. The Market Data takes a lot of time to be retrieved, parsed and structured to be associated
 * with the Eve Items. And if can be centralized on a Service that can share all that data structures over a
 * cache. The current Android code structure allows that the access to that data can be delayed to
 * asynchronous download until a new request is active and the data is updated on the calling service.<br>
 * 
 * Code from the Orange labs can be used to setup this stand alone service and maybe can be deployed onto an
 * external network server, be it another local computer or a free external service such as Amazon or any
 * other free service provider.
 * 
 * @author Adam Antinoo
 */
//@EnableCaching
@EnableScheduling
@SpringBootApplication
//@ImportResource(value = "classpath*:hsql_configuration.xml")
public class MarketDataServiceApplication implements INeoComMSConnector {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger												logger		= Logger.getLogger("MarketDataServiceApplication");
	public static MarketDataServiceApplication	singleton	= null;

	// - M A I N   E N T R Y P O I N T ........................................................................
	/**
	 * Just create the Spring application and launch it to run.
	 * 
	 * @param args
	 */
	public static void main(final String[] args) {
		// Instance and connect the Adaptors.
		SpringApplication.run(MarketDataServiceApplication.class, args);
	}

	// - F I E L D - S E C T I O N ............................................................................
	private NeoComMSConnector			_connector			= null;
	private Instant								chrono					= null;
	private ICCPDatabaseConnector	dbCCPConnector	= null;
	private ICacheConnector				cacheConnector	= null;

	//	@Autowired
	//	public CacheManager						cacheManager;

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public MarketDataServiceApplication() {
		logger.info(">> [MarketDataServiceApplication.<constructor>]");
		// Create and connect the adapters.
		//		if (null == singleton) {
		//			logger.info("-- [MarketDataServiceApplication.<constructor>]> Instantiating the singleton.");
		//			singleton = this;
		//		}
		//		ModelAppConnector.getSingleton().setConnector(singleton);
		_connector = new NeoComMSConnector(this);
		logger.info("<< [MarketDataServiceApplication.<constructor>]");
	}

	//	@Override
	public boolean getAssetsFormat() {
		return true;
	}

	@Override
	public ICacheConnector getCacheConnector() {
		if (null == cacheConnector) cacheConnector = new MarketDataServiceCacheConnector();
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
		throw new RuntimeException("Application connector not defined. Functionality 'getDBConnector' disabled.");
	}

	@Override
	public INeoComModelStore getModelStore() {
		throw new RuntimeException("Application connector not defined. Functionality 'getModelStore' disabled.");
	}

	//	@Override
	//	public IStorageConnector getStorageConnector() {
	//		throw new RuntimeException("Application connector not defined. Functionality 'getStorageConnector' disabled.");
	//	}

	/**
	 * Run this after the application is initialized. The contents are to read back from persistence storage the
	 * cache contents before starting the application.
	 */
	@PostConstruct
	public void postConstruct() {
		logger.info(">> [MarketDataServiceApplication.postConstruct]");
		// Read back the cache contents
		MarketDataServer.readCacheFromStorage();
		logger.info("<< [MarketDataServiceApplication.postConstruct]");
	}

	// - W E B   E N T R Y   P O I N T S
	// Register the hystrix.stream to publish hystrix data to the dashboard
	//	@Bean
	//	public ServletRegistrationBean servletRegistrationBean() {
	//		return new ServletRegistrationBean(new HystrixMetricsStreamServlet(), "/hystrix.stream");
	//	}

	// - M E T H O D - S E C T I O N ..........................................................................
	//	@Override
	//	public void addCharacterUpdateRequest(long characterID) {
	//	}
	//
	//	@Override
	//	public IConnector getAppSingleton() {
	//		return singleton;
	//	}
	public void startChrono() {
		chrono = new Instant();
	}

	/**
	 * Run this before termianting the application. I should write the cache to disk for persistence.
	 */
	@PreDestroy
	public void terminate() {
		logger.info(">> [MarketDataServiceApplication.terminate]");
		// Write the cache contents
		MarketDataServer.writeCacheToStorage();
		logger.info("<< [MarketDataServiceApplication.terminate]");
	}

	public Duration timeLapse() {
		return new Duration(chrono, new Instant());
	}
}
// - UNUSED CODE ............................................................................................
