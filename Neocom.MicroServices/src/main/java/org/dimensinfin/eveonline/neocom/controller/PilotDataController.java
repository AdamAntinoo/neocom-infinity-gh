//	PROJECT:      Neocom.Microservices (NEOC-MS)
//	AUTHORS:      Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:    (c) 2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:	SpringBoot-MS-Java 1.8.
//	DESCRIPTION:	This is the integration project for all the web server pieces. This is the launcher for
//								the SpringBoot+MicroServices+Angular unified web application.
package org.dimensinfin.eveonline.neocom.controller;

import java.util.Vector;
import java.util.logging.Logger;

import org.dimensinfin.eveonline.neocom.connector.NeoComMSConnector;
import org.dimensinfin.eveonline.neocom.manager.AbstractManager;
import org.dimensinfin.eveonline.neocom.model.NeoComCharacter;
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

	@CrossOrigin()
	@RequestMapping(value = "/api/v1/login/{login}/pilot/{identifier}/assetsmanager", method = RequestMethod.GET, produces = "application/json")
	public AbstractManager pilotAssetsManager(@PathVariable final String login, @PathVariable final String identifier) {
		logger.info(
				">>>>>>>>>>>>>>>>>>>>NEW REQUEST: " + "/api/v1/login/{" + login + "}/pilot/{" + identifier + "}/assetsmanager");
		logger.info(">> [PilotRoasterController.pilotAssetsManager]");
		try {
			// Initialize the model data hierarchies.
			NeoComMSConnector.getSingleton().getModelStore().activateLoginIdentifier(login);
			NeoComMSConnector.getSingleton().getModelStore().activatePilot(Long.valueOf(identifier));
			return NeoComMSConnector.getSingleton().getModelStore().getActiveCharacter().getAssetsManager().initialize();
		} catch (RuntimeException rtx) {
			rtx.printStackTrace();
			return null;
		} finally {
			logger.info("<< [PilotRoasterController.pilotPlanetaryManager]");
		}
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	@CrossOrigin()
	@RequestMapping(value = "/api/v1/login/{login}/pilot/{identifier}", method = RequestMethod.GET, produces = "application/json")
	public NeoComCharacter pilotDetailed(@PathVariable final String login, @PathVariable final String identifier) {
		logger.info(">>>>>>>>>>>>>>>>>>>>NEW REQUEST: " + "/api/v1/login/{" + login + "}/pilot/{" + identifier + "}");
		logger.info(">> [PilotRoasterController.pilotDetailed]");
		//	Vector<AbstractManager> managerList = new Vector<AbstractManager>();
		try {
			// Initialize the model data hierarchies.
			NeoComMSConnector.getSingleton().getModelStore().activateLoginIdentifier(login);
			NeoComCharacter pilot = NeoComMSConnector.getSingleton().getModelStore().activatePilot(Long.valueOf(identifier));
			return pilot;
		} catch (RuntimeException rtx) {
			rtx.printStackTrace();
			return null;
		} finally {
			logger.info("<< [PilotRoasterController.pilotDetailed]");
		}
	}

	@CrossOrigin()
	@RequestMapping(value = "/api/v1/login/{login}/pilot/{identifier}/pilotmanagers", method = RequestMethod.GET, produces = "application/json")
	public Vector<AbstractManager> pilotManagers(@PathVariable final String login,
			@PathVariable final String identifier) {
		logger.info(">>>>>>>>>>>>>>>>>>>>NEW REQUEST: " + "/api/v1/login/{" + login + "}/pilot/{" + identifier + "}"
				+ "/pilotmanagers");
		logger.info(">> [PilotRoasterController.pilotManagers]");
		Vector<AbstractManager> managerList = new Vector<AbstractManager>();
		try {
			// Initialize the model data hierarchies.
			NeoComMSConnector.getSingleton().getModelStore().activateLoginIdentifier(login);
			NeoComCharacter pilot = NeoComMSConnector.getSingleton().getModelStore().activatePilot(Long.valueOf(identifier));
			managerList.addElement(pilot.getAssetsManager().initialize());
			managerList.addElement(pilot.getPlanetaryManager().initialize());
			return managerList;
		} catch (RuntimeException rtx) {
			rtx.printStackTrace();
			return new Vector<AbstractManager>();
		} finally {
			logger.info("<< [PilotRoasterController.pilotManagers]");
		}
	}

	@CrossOrigin()
	@RequestMapping(value = "/api/v1/login/{login}/pilot/{identifier}/planetarymanager", method = RequestMethod.GET, produces = "application/json")
	public AbstractManager pilotPlanetaryManager(@PathVariable final String login,
			@PathVariable final String identifier) {
		logger.info(">>>>>>>>>>>>>>>>>>>>NEW REQUEST: " + "/api/v1/login/{" + login + "}/pilot/{" + identifier
				+ "}/planetarymanager");
		logger.info(">> [PilotRoasterController.pilotPlanetaryManager]");
		try {
			// Initialize the model data hierarchies.
			NeoComMSConnector.getSingleton().getModelStore().activateLoginIdentifier(login);
			NeoComMSConnector.getSingleton().getModelStore().activatePilot(Long.valueOf(identifier));
			return NeoComMSConnector.getSingleton().getModelStore().getCurrentPilot().getPlanetaryManager().initialize();
		} catch (RuntimeException rtx) {
			rtx.printStackTrace();
			return null;
		} finally {
			logger.info("<< [PilotRoasterController.pilotPlanetaryManager]");
		}
	}
}

// - UNUSED CODE ............................................................................................
