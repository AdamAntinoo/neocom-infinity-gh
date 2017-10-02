//	PROJECT:      Neocom.Microservices (NEOC-MS)
//	AUTHORS:      Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:    (c) 2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:	SpringBoot-MS-Java 1.8.
//	DESCRIPTION:	This is the integration project for all the web server pieces. This is the launcher for
//								the SpringBoot+MicroServices+Angular unified web application.
package org.dimensinfin.eveonline.neocom.controller;

import java.util.Vector;
import java.util.logging.Logger;

import org.dimensinfin.eveonline.neocom.connector.AppConnector;
import org.dimensinfin.eveonline.neocom.industry.Resource;
import org.dimensinfin.eveonline.neocom.manager.AssetsManager;
import org.dimensinfin.eveonline.neocom.manager.PlanetaryManager;
import org.dimensinfin.eveonline.neocom.planetary.PlanetaryProcessor;
import org.dimensinfin.eveonline.neocom.planetary.PlanetaryScenery;
import org.dimensinfin.eveonline.neocom.planetary.ProcessingAction;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

// - CLASS IMPLEMENTATION ...................................................................................
@RestController
public class PlanetaryManagerController {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = Logger.getLogger("PlanetaryManagerController");

	// - F I E L D - S E C T I O N ............................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................

	// - M E T H O D - S E C T I O N ..........................................................................
	@CrossOrigin()
	@RequestMapping(value = "/api/v1/login/{login}/pilot/{identifier}/planetarymanager/location/{locationid}/optimizeprocess", method = RequestMethod.GET, produces = "application/json")
	public Vector<ProcessingAction> planetaryLocationOptimization(@PathVariable final String login,
			@PathVariable final String identifier, @PathVariable final String locationid) {
		logger.info(">>>>>>>>>>>>>>>>>>>>NEW REQUEST: "
				+ "/api/v1/login/{login}/pilot/{identifier}/planetarymanager/location/{locationid}/optimizeprocess");
		logger.info(">> [PilotRoasterController.planetaryLocationOptimization]>login: " + login);
		logger.info(">> [PilotRoasterController.planetaryLocationOptimization]>identifier: " + identifier);
		logger.info(">> [PilotRoasterController.planetaryLocationOptimization]>locationid: " + locationid);
		Vector<ProcessingAction> bestScenario = new Vector<ProcessingAction>();
		try {
			// Initialize the model data hierarchies.
			AppConnector.getModelStore().activateLoginIdentifier(login);
			AppConnector.getModelStore().activatePilot(Long.valueOf(identifier));
			// Get the Planetary Manager for this Character. Make sure it is initialized and then get the resources
			// at the indicated location and optimize processing them.
			AssetsManager assets = AppConnector.getModelStore().getCurrentPilot().getAssetsManager();
			PlanetaryManager planetary = AppConnector.getModelStore().getCurrentPilot().getPlanetaryManager();
			if (!planetary.isInitialized()) planetary.initialize();
			Vector<Resource> resources = planetary.getLocationContents(locationid);

			// The Planetary Advisor requires a list of Planetary Resources to be stocked to start the profit calculations.
			PlanetaryScenery scenery = new PlanetaryScenery();
			scenery.stock(resources);
			// Create the initial processing point and start the optimization recursively.
			PlanetaryProcessor proc = new PlanetaryProcessor(scenery);
			// Start running the best profit search.
			bestScenario = proc.startProfitSearch(null);

			// Get the results back to the UI. Format them to tho expected data structure.
			return bestScenario;
		} catch (RuntimeException rtx) {
			rtx.printStackTrace();
			return new Vector<ProcessingAction>();
		} finally {
			logger.info("<< [PilotRoasterController.planetaryLocationOptimization]");
		}
	}
}

// - UNUSED CODE ............................................................................................
