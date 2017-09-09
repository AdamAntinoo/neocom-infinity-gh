//	PROJECT:      Neocom.Microservices (NEOC-MS)
//	AUTHORS:      Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:    (c) 2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:	SpringBoot-MS-Java 1.8.
//	DESCRIPTION:	This is the integration project for all the web server pieces. This is the launcher for
//								the SpringBoot+MicroServices+Angular unified web application.
package org.dimensinfin.eveonline.neocom.microservices.controller;

import java.util.Vector;
import java.util.logging.Logger;

import org.dimensinfin.core.model.IGEFNode;
import org.dimensinfin.core.model.RootNode;
import org.dimensinfin.eveonline.neocom.connector.AppConnector;
import org.dimensinfin.eveonline.neocom.connector.DataSourceLocator;
import org.dimensinfin.eveonline.neocom.connector.IModelGenerator;
import org.dimensinfin.eveonline.neocom.constant.ENeoComVariants;
import org.dimensinfin.eveonline.neocom.generator.PilotDirectorsGenerator;
import org.dimensinfin.eveonline.neocom.generator.PilotRoasterModelGenerator;
import org.dimensinfin.eveonline.neocom.manager.AbstractManager;
import org.dimensinfin.eveonline.neocom.manager.ModelGeneratorManager;
import org.dimensinfin.eveonline.neocom.microservices.adapter.AppModelStore;
import org.dimensinfin.eveonline.neocom.model.NeoComCharacter;
import org.dimensinfin.eveonline.neocom.model.Pilot;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

// - CLASS IMPLEMENTATION ...................................................................................
@RestController
public class PilotRoasterController {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = Logger.getLogger("PilotRoasterController");

	// - F I E L D - S E C T I O N ............................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................

	// - M E T H O D - S E C T I O N ..........................................................................
	@CrossOrigin()
	@RequestMapping(value = "/api/v1/pilot/{identifier}", method = RequestMethod.GET, produces = "application/json")
	public NeoComCharacter pilotDetail(@PathVariable final String identifier) {
		logger.info(">> [PilotRoasterController.pilotDetail]");
		String login = "Beth";
		AppModelStore.getSingleton().setLoginIdentifier(login);
		// Convert the parameter to a long variable.
		long selectedId = Long.valueOf(identifier);
		NeoComCharacter pilot = AppModelStore.getSingleton().searchCharacter(selectedId);
		AppConnector.getModelStore().activatePilot(selectedId);
		return pilot;
	}

	@CrossOrigin()
	@RequestMapping(value = "/api/v1/pilotmanagers/{identifier}", method = RequestMethod.GET, produces = "application/json")
	public Vector<AbstractManager> pilotManagers(
			@PathVariable final String identifier/* @CookieValue("login") String login */) {
		logger.info(">> [PilotRoasterController.pilotManagers]");
		// Get the cookie and the login identifier inside it.
		String login = "Beth";
		AppModelStore.getSingleton().setLoginIdentifier(login);
		// Convert the parameter to a long variable.
		long selectedId = Long.valueOf(identifier);
		AppConnector.getModelStore().activatePilot(selectedId);
		Vector<AbstractManager> managerList = new Vector<AbstractManager>();
		if (null != login) {
			// Get a new model interface for the Pilot roaster using as unique identifier the login.
			IModelGenerator adapter = ModelGeneratorManager.registerGenerator(new PilotDirectorsGenerator(
					new DataSourceLocator().addIdentifier(login), ENeoComVariants.PILOT_DETAILS.name(), login));
			RootNode pilotNode = adapter.collaborate2Model();
			for (IGEFNode manager : pilotNode.getChildren()) {
				managerList.add((AbstractManager) manager);
			}
		}
		logger.info("<< [PilotRoasterController.pilotManagers]");
		return managerList;
	}

	/**
	 * This requests will extract the login identifier from the session cookie and then with that identifier
	 * search for the api keys on the database. Those keys will be the input to the datasource to get the list
	 * of pilots associated with the identified login. With that information the datasource will be able to go
	 * to the CCP servers to get the each <code>Pilot</code> information.
	 * 
	 * @return list of <code>Pilot</code>s associated to the current session login. Each of this may become a
	 *         different datasource item that can be cached.
	 */
	@CrossOrigin()
	@RequestMapping(value = "/api/v1/pilotroaster", method = RequestMethod.GET, produces = "application/json")
	//public List<Pilot> pilotRoaster(@CookieValue("login") String login) {
	public Vector<Pilot> pilotRoaster(/* @CookieValue("login") String login */) {
		logger.info(">> [PilotRoasterController.pilotRoaster]");
		// Get the cookie and the login identifier inside it.
		String login = "Beth";
		AppModelStore.getSingleton().setLoginIdentifier(login);
		Vector<Pilot> pilotList = new Vector<Pilot>();
		if (null != login) {
			// Get a new model interface for the Pilot roaster using as unique identifier the login.
			IModelGenerator adapter = ModelGeneratorManager.registerGenerator(new PilotRoasterModelGenerator(
					new DataSourceLocator().addIdentifier(login), ENeoComVariants.CAPSULEER_LIST.name(), login));
			RootNode pilotNode = adapter.collaborate2Model();
			for (IGEFNode pilot : pilotNode.getChildren()) {
				pilotList.add((Pilot) pilot);
			}
		}
		logger.info("<< [PilotRoasterController.pilotRoaster]");
		return pilotList;
		//	return AppModelStore.getSingleton().getPilotRoaster();
	}

}

// - UNUSED CODE ............................................................................................
//class ModelAdapterManager {
//	// - F I E L D - S E C T I O N ............................................................................
//	private static final HashMap<String, IModelAdapter> adapters = new HashMap<String, IModelAdapter>();
//
//	// - C O N S T R U C T O R - S E C T I O N ................................................................
//
//	// - M E T H O D - S E C T I O N ..........................................................................
//	public static IModelAdapter registerAdapter(final IModelAdapter newAdapter) {
//		DataSourceLocator locator = newAdapter.getDataSourceLocator();
//		// Search for locator on cache.
//		IModelAdapter found = adapters.get(locator.getIdentity());
//		// REFACTOR Do not return cached datasources.
//		found = null;
//		if (null == found) {
//			adapters.put(locator.getIdentity(), newAdapter);
//			//	logger					.info("-- [DataSourceManager.registerDataSource]> Registering new DataSource: " + locator.getIdentity());
//			//		newAdapter.connect(this);
//			return newAdapter;
//		} else
//			return found;
//	}
//}
