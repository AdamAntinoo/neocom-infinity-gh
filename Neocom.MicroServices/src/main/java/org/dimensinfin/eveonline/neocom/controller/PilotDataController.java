//	PROJECT:      Neocom.Microservices (NEOC-MS)
//	AUTHORS:      Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:    (c) 2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:	SpringBoot-MS-Java 1.8.
//	DESCRIPTION:	This is the integration project for all the web server pieces. This is the launcher for
//								the SpringBoot+MicroServices+Angular unified web application.
package org.dimensinfin.eveonline.neocom.controller;

import java.util.Vector;
import java.util.logging.Logger;

import org.dimensinfin.core.model.IGEFNode;
import org.dimensinfin.core.model.RootNode;
import org.dimensinfin.eveonline.neocom.connector.AppConnector;
import org.dimensinfin.eveonline.neocom.core.DataSourceLocator;
import org.dimensinfin.eveonline.neocom.enums.ENeoComVariants;
import org.dimensinfin.eveonline.neocom.generator.ModelGeneratorStore;
import org.dimensinfin.eveonline.neocom.generator.PilotManagersGenerator;
import org.dimensinfin.eveonline.neocom.generator.PilotRoasterGenerator;
import org.dimensinfin.eveonline.neocom.interfaces.IModelGenerator;
import org.dimensinfin.eveonline.neocom.manager.AbstractManager;
import org.dimensinfin.eveonline.neocom.manager.AssetsManager;
import org.dimensinfin.eveonline.neocom.model.NeoComCharacter;
import org.dimensinfin.eveonline.neocom.model.Pilot;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

// - CLASS IMPLEMENTATION ...................................................................................
@RestController
public class PilotDataController {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = Logger.getLogger("PilotRoasterController");

	// - F I E L D - S E C T I O N ............................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................

	// - M E T H O D - S E C T I O N ..........................................................................
	@CrossOrigin()
	@RequestMapping(value = "/api/v1/login/{login}/pilot/{identifier}", method = RequestMethod.GET, produces = "application/json")
	public NeoComCharacter pilotDetailed(@PathVariable final String login, @PathVariable final String identifier) {
		logger.info(">>>>>>>>>>>>>>>>>>>>NEW REQUEST: " + "/api/v1/login/{login}/pilot/{" + identifier + "}");
		logger.info(">> [PilotRoasterController.pilotDetailed]");
		//	Vector<AbstractManager> managerList = new Vector<AbstractManager>();
		try {
			// Initialize the model data hierarchies.
			AppConnector.getModelStore().activateLoginIdentifier(login);
			NeoComCharacter pilot = AppConnector.getModelStore().activatePilot(Long.valueOf(identifier));
			// Activate the managers one by one and return the new Character.
			// The Assets Manager is an special case. Remove and punt another clean one.
			pilot.setAssetsManager(new AssetsManager(pilot).initialize());
			pilot.getPlanetaryManager().initialize();
			// Get a new model interface for the Pilot roaster using as unique identifier the login.
			//			DataSourceLocator locator = new DataSourceLocator().addIdentifier(login).addIdentifier(identifier)
			//					.addIdentifier(ENeoComVariants.PILOT_MANAGERS.name());
			//			IModelGenerator adapter = ModelGeneratorStore.registerGenerator(new PilotManagersGenerator(locator,
			//					ENeoComVariants.PILOT_MANAGERS.name(), AppConnector.getModelStore().getCurrentPilot()));
			//			RootNode pilotNode = adapter.collaborate2Model();
			//			for (IGEFNode manager : pilotNode.getChildren()) {
			//				managerList.add((AbstractManager) manager);
			//			}
			//			}
			return pilot;
		} catch (RuntimeException rtx) {
			rtx.printStackTrace();
		} finally {
			logger.info("<< [PilotRoasterController.pilotDetailed]");
		}
		return null;
	}

	@CrossOrigin()
	@RequestMapping(value = "/api/v1/login/{login}/pilot/{identifier}/pilotmanagers", method = RequestMethod.GET, produces = "application/json")
	public Vector<AbstractManager> pilotManagers(@PathVariable final String login,
			@PathVariable final String identifier) {
		logger.info(">>>>>>>>>>>>>>>>>>>>NEW REQUEST: " + "/api/v1/login/{login}/pilot/{identifier}/pilotmanagers");
		logger.info(">> [PilotRoasterController.pilotManagers]");
		Vector<AbstractManager> managerList = new Vector<AbstractManager>();
		try {
			// Initialize the model data hierarchies.
			AppConnector.getModelStore().activateLoginIdentifier(login);
			AppConnector.getModelStore().activatePilot(Long.valueOf(identifier));
			//			if (null != login) {
			// Get a new model interface for the Pilot roaster using as unique identifier the login.
			DataSourceLocator locator = new DataSourceLocator().addIdentifier(login).addIdentifier(identifier)
					.addIdentifier(ENeoComVariants.PILOT_MANAGERS.name());
			IModelGenerator adapter = ModelGeneratorStore.registerGenerator(new PilotManagersGenerator(locator,
					ENeoComVariants.PILOT_MANAGERS.name(), AppConnector.getModelStore().getCurrentPilot()));
			RootNode pilotNode = adapter.collaborate2Model();
			for (IGEFNode manager : pilotNode.getChildren()) {
				managerList.add((AbstractManager) manager);
			}
			//			}
		} catch (RuntimeException rtx) {
			rtx.printStackTrace();
		}
		logger.info("<< [PilotRoasterController.pilotManagers]");
		return managerList;
	}

	@CrossOrigin()
	@RequestMapping(value = "/api/v1/login/{login}/pilot/{identifier}/planetarymanager", method = RequestMethod.GET, produces = "application/json")
	public AbstractManager pilotPlanetaryManager(@PathVariable final String login,
			@PathVariable final String identifier) {
		logger.info(">>>>>>>>>>>>>>>>>>>>NEW REQUEST: " + "/api/v1/pilot/{identifier}/planetarymanager");
		logger.info(">> [PilotRoasterController.pilotPlanetaryManager]");
		try {
			// Initialize the model data hierarchies.
			AppConnector.getModelStore().activateLoginIdentifier(login);
			AppConnector.getModelStore().activatePilot(Long.valueOf(identifier));
			return AppConnector.getModelStore().getCurrentPilot().getPlanetaryManager().initialize();
		} catch (RuntimeException rtx) {
			rtx.printStackTrace();
		}
		logger.info("<< [PilotRoasterController.pilotManagers]");
		return null;
	}

	//	@CrossOrigin()
	//	@RequestMapping(value = "/api/v1/pilotmanagers/{identifier}", method = RequestMethod.GET, produces = "application/json")
	//	public Vector<AbstractManager> pilotManagers(@PathVariable final String identifier) {
	//		logger.info(">> [PilotRoasterController.pilotManagers]");
	//		Vector<AbstractManager> managerList = new Vector<AbstractManager>();
	//		// Get the cookie and the login identifier inside it.
	//		String login = "Beth";
	//		try {
	//			AppModelStore.getSingleton().setLoginIdentifier(login);
	//			// Convert the parameter to a long variable.
	//			long selectedId = Long.valueOf(identifier);
	//			AppConnector.getModelStore().activatePilot(selectedId);
	//			if (null != login) {
	//				// Get a new model interface for the Pilot roaster using as unique identifier the login.
	//				IModelGenerator adapter = ModelGeneratorStore.registerGenerator(new PilotDirectorsGenerator(
	//						new DataSourceLocator().addIdentifier(login), ENeoComVariants.PILOT_DETAILS.name(), login));
	//				RootNode pilotNode = adapter.collaborate2Model();
	//				for (IGEFNode manager : pilotNode.getChildren()) {
	//					managerList.add((AbstractManager) manager);
	//				}
	//			}
	//		} catch (RuntimeException rtx) {
	//			rtx.printStackTrace();
	//		}
	//		logger.info("<< [PilotRoasterController.pilotManagers]");
	//		return managerList;
	//	}

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
	@RequestMapping(value = "/api/v1/login/{identifier}/pilotroaster", method = RequestMethod.GET, produces = "application/json")
	public Vector<Pilot> pilotRoaster(@PathVariable final String identifier) {
		logger.info(">>>>>>>>>>>>>>>>>>>>NEW REQUEST: " + "/api/v1/login/{identifier}/pilotroaster");
		logger.info(">> [PilotRoasterController.pilotRoaster]>identifier=" + identifier);
		// Get the cookie and the login identifier inside it.
		//		String login = "Beth";
		AppConnector.getModelStore().activateLoginIdentifier(identifier);
		Vector<Pilot> pilotList = new Vector<Pilot>();
		if (null != identifier) {
			// Get a new model interface for the Pilot roaster using as unique identifier the login.
			IModelGenerator adapter = ModelGeneratorStore.registerGenerator(new PilotRoasterGenerator(
					new DataSourceLocator().addIdentifier(identifier), ENeoComVariants.CAPSULEER_LIST.name(), identifier));
			RootNode pilotNode = adapter.collaborate2Model();
			for (IGEFNode pilot : pilotNode.getChildren()) {
				pilotList.add((Pilot) pilot);
			}
		}
		logger.info("<< [PilotRoasterController.pilotRoaster]");
		return pilotList;
	}

}

// - UNUSED CODE ............................................................................................
