//	PROJECT:      Neocom.Microservices (NEOC-MS)
//	AUTHORS:      Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:    (c) 2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:	SpringBoot-MS-Java 1.8.
//	DESCRIPTION:	This is the integration project for all the web server pieces. This is the launcher for
//								the SpringBoot+MicroServices+Angular unified web application.
package org.dimensinfin.eveonline.neocom.controller;

import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;

import org.dimensinfin.eveonline.neocom.connector.NeoComMSConnector;
import org.dimensinfin.eveonline.neocom.manager.AssetsManager;
import org.dimensinfin.eveonline.neocom.model.ExtendedLocation;
import org.dimensinfin.eveonline.neocom.model.NeoComAsset;
import org.dimensinfin.eveonline.neocom.model.NeoComCharacter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

// - CLASS IMPLEMENTATION ...................................................................................
@RestController
public class AssetsManagerController {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = Logger.getLogger("AssetsManagerController");

	// - F I E L D - S E C T I O N ............................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................

	// - M E T H O D - S E C T I O N ..........................................................................
	@CrossOrigin()
	@RequestMapping(value = "/api/v1/login/{login}/pilot/{identifier}/assetsmanager/location/{locationid}/downloadcontents", method = RequestMethod.GET, produces = "application/json")
	public Vector<NeoComAsset> planetaryLocationOptimization(@PathVariable final String login,
			@PathVariable final String identifier, @PathVariable final String locationid) {
		logger.info(">>>>>>>>>>>>>>>>>>>>NEW REQUEST: " + "/api/v1/login/{login}/pilot/{" + identifier
				+ "}/assetsmanager/location/{" + locationid + "}/downloadcontents");
		logger.info(">> [AssetsManagerController.planetaryLocationOptimization]");
		//			Vector<NeoComAsset> locationContents = new Vector<NeoComAsset>();
		try {
			// Initialize the model data hierarchies.
			NeoComMSConnector.getSingleton().getModelStore().activateLoginIdentifier(login);
			NeoComCharacter pilot = NeoComMSConnector.getSingleton().getModelStore().activatePilot(Long.valueOf(identifier));
			// Get the Assets Manager for this Character. Make sure it is initialized and then get the resources
			// at the indicated location and optimize processing them.
			AssetsManager assetsMan = pilot.getAssetsManager();
			if (!assetsMan.isInitialized()) assetsMan.initialize();
			ExtendedLocation loc = assetsMan.getLocationById(Long.valueOf(locationid).longValue());
			if (null == loc)
				return new Vector<NeoComAsset>();
			else {
				// get sure the Locations contens are on place.
				List<NeoComAsset> contents = loc.getContents();
				return (Vector<NeoComAsset>) contents;
			}
		} catch (RuntimeException rtx) {
			rtx.printStackTrace();
			return new Vector<NeoComAsset>();
		} finally {
			logger.info("<< [AssetsManagerController.planetaryLocationOptimization]");
		}
	}
}

// - UNUSED CODE ............................................................................................
