//	PROJECT:      Neocom.Microservices (NEOC-MS)
//	AUTHORS:      Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:    (c) 2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:	SpringBoot-MS-Java 1.8.
//	DESCRIPTION:	This is the integration project for all the web server pieces. This is the launcher for
//								the SpringBoot+MicroServices+Angular unified web application.
package org.dimensinfin.eveonline.neocom.microservices;

import java.util.logging.Logger;

import org.dimensinfin.eveonline.neocom.connector.AppConnector;
import org.dimensinfin.eveonline.neocom.connector.ICCPDatabaseConnector;
import org.dimensinfin.eveonline.neocom.connector.ICacheConnector;
import org.dimensinfin.eveonline.neocom.connector.IConnector;
import org.dimensinfin.eveonline.neocom.connector.IDatabaseConnector;
import org.dimensinfin.eveonline.neocom.connector.IStorageConnector;
import org.dimensinfin.eveonline.neocom.connector.MicroServicesCacheConnector;
import org.dimensinfin.eveonline.neocom.connector.NeocomDatabaseConnector;
import org.dimensinfin.eveonline.neocom.interfaces.INeoComModelStore;
import org.dimensinfin.eveonline.neocom.microservices.adapter.AppModelStore;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

// - CLASS IMPLEMENTATION ...................................................................................
/**
 * This is the initial class and loader for the Spring Boot application. It will be used to integrate the
 * different modules and libraries, instantiate the adapters and serve as the base point to integrate the
 * different controllers. Most of the code will be imported and integrated from the depending libraries.
 * 
 * @author Adam Antinoo
 */
@SpringBootApplication
@EnableScheduling
public class NeocomMicroServiceApplication implements IConnector {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger													logger		= Logger.getLogger("NeocomMicroServiceApplication");
	private static NeocomMicroServiceApplication	singleton	= null;

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
	private IDatabaseConnector	dbNeocomConnector	= null;
	private ICacheConnector			cacheConnector		= null;

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public NeocomMicroServiceApplication() {
		logger.info(">>[NeocomMicroServiceApplication.<constructor>]");
		// Create and connect the adapters.
		if (null == singleton) {
			logger.info("--[NeocomMicroServiceApplication.<constructor>]> Instantiating the singleton.");
			singleton = this;
		}
		AppConnector.setConnector(singleton);
		logger.info("<<[NeocomMicroServiceApplication.<constructor>]");
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	@Override
	public void addCharacterUpdateRequest(long characterID) {
		//		this.getCacheConnector().addCharacterUpdateRequest(characterID);
	}

	@Override
	public ICacheConnector getCacheConnector() {
		if (null == cacheConnector) cacheConnector = new MicroServicesCacheConnector();
		return cacheConnector;
	}

	@Override
	public ICCPDatabaseConnector getCCPDBConnector() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IDatabaseConnector getDBConnector() {
		if (null == dbNeocomConnector) dbNeocomConnector = new NeocomDatabaseConnector();
		return dbNeocomConnector;
	}

	//	@Override
	//	public String getResourceString(int reference) {
	//		// TODO Auto-generated method stub
	//		return null;
	//	}

	@Override
	public INeoComModelStore getModelStore() {
		return AppModelStore.getSingleton();
	}

	@Override
	public IConnector getSingleton() {
		return NeocomMicroServiceApplication.singleton;
	}

	@Override
	public IStorageConnector getStorageConnector() {
		return null;
	}
}
// - UNUSED CODE ............................................................................................
