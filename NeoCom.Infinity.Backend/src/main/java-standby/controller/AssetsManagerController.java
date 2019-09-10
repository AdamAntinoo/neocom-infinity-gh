//	PROJECT:      Neocom.Microservices (NEOC-MS)
//	AUTHORS:      Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:    (c) 2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:	SpringBoot-MS-Java 1.8.
//	DESCRIPTION:	This is the integration project for all the web server pieces. This is the launcher for
//								the SpringBoot+MicroServices+Angular unified web application.
package org.dimensinfin.eveonline.neocom.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;

import org.dimensinfin.core.model.AbstractComplexNode;
import org.dimensinfin.eveonline.neocom.connector.NeoComMSConnector;
import org.dimensinfin.eveonline.neocom.manager.DefaultAssetsContentManager;
import org.dimensinfin.eveonline.neocom.domain.EsiLocation;
import org.dimensinfin.eveonline.neocom.model.ExtendedLocation;
import org.dimensinfin.eveonline.neocom.model.NeoComAsset;
import org.dimensinfin.eveonline.neocom.model.NeoComCharacter;
import org.dimensinfin.eveonline.neocom.model.Ship;
import org.dimensinfin.eveonline.neocom.model.SpaceContainer;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

// - CLASS IMPLEMENTATION ...................................................................................
@RestController
public class AssetsManagerController {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = Logger.getLogger("AssetsManagerController");

	// - F I E L D - S E C T I O N ............................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................

	// - M E T H O D - S E C T I O N ..........................................................................
	/**
	 * Gt access tot the contents of a Container or Ship by getting its reference and then obtaining their
	 * collaboration.
	 * 
	 * @param login
	 * @param identifier
	 * @param containerid
	 * @return
	 */
	@CrossOrigin()
	@RequestMapping(value = "/api/v1/login/{login}/pilot/{identifier}/assetsmanager/container/{containerid}/downloadcontents", method = RequestMethod.GET, produces = "application/json")
	public List<?> planetaryContainerContents ( @PathVariable final String login, @PathVariable final String identifier,
					@PathVariable final String containerid ) {
		logger.info(">>>>>>>>>>>>>>>>>>>>NEW REQUEST: " + "/api/v1/login/{" + login + "}/pilot/{" + identifier
						+ "}/assetsmanager/container/{" + containerid + "}/downloadcontents");
		logger.info(">> [AssetsManagerController.planetaryContainerContents]");
		try {
			// Initialize the model data hierarchies.
			NeoComMSConnector.getSingleton().getModelStore().activateLoginIdentifier(login);
			NeoComCharacter pilot = NeoComMSConnector.getSingleton().getModelStore().activatePilot(Long.valueOf(identifier));
			// Get to the Ship or container to use their internal methods to download and process the data.
			long containeridnumber = Long.valueOf(containerid).longValue();
			NeoComAsset asset = NeoComMSConnector.getSingleton().getDBConnector().searchAssetByID(containeridnumber);
			if (null != asset) {
				if (asset.isShip()) {
					// Check if the ship is packaged. If packaged leave it as a simple asset.
					if (!asset.isPackaged()) {
						// Transform the asset to a ship.
						Ship ship = new Ship(pilot.getCharacterID()).copyFrom(asset);
						ArrayList<AbstractComplexNode> contents = ship.collaborate2Model("-DEFAULT-");
						//						// Process contents recursively for complex items such as Ships.
						//						ArrayList<AbstractComplexNode> results = new ArrayList<AbstractComplexNode>();
						//						for (AbstractComplexNode node : contents) {
						//							
						//						}
						return contents;
					}
				}
				if (asset.isContainer()) {
					// Check if the asset is packaged. If so leave as asset
					if (!asset.isPackaged()) {
						SpaceContainer container = new SpaceContainer().copyFrom(asset);
						ArrayList<AbstractComplexNode> contents = container.collaborate2Model("-DEFAULT-");
						return contents;
					}
				}
			}
			// If the contents need not be transformed then return this simple asset.
			return new Vector<NeoComAsset>();
		} catch (RuntimeException rtx) {
			rtx.printStackTrace();
			return new Vector<NeoComAsset>();
		} finally {
			logger.info("<< [AssetsManagerController.planetaryContainerContents]");
		}
	}

	@CrossOrigin()
	@RequestMapping(value = "/api/v1/login/{login}/pilot/{identifier}/assetsmanager/location/{locationid}/downloadcontents", method = RequestMethod.GET, produces = "application/json")
	public String planetaryLocationContents ( @PathVariable final String login, @PathVariable final String identifier,
					@PathVariable final String locationid ) {
		logger.info(">>>>>>>>>>>>>>>>>>>>NEW REQUEST: " + "/api/v1/login/{" + login + "}/pilot/{" + identifier
						+ "}/assetsmanager/location/{" + locationid + "}/downloadcontents");
		logger.info(">> [AssetsManagerController.planetaryLocationOptimization]");
		//			Vector<NeoComAsset> locationContents = new Vector<NeoComAsset>();
		try {
			// Initialize the model data hierarchies.
			NeoComMSConnector.getSingleton().getModelStore().activateLoginIdentifier(login);
			NeoComCharacter pilot = NeoComMSConnector.getSingleton().getModelStore().activatePilot(Long.valueOf(identifier));
			long locationidnumber = Long.valueOf(locationid).longValue();
			EsiLocation location = NeoComMSConnector.getSingleton().getCCPDBConnector().searchLocationbyID(locationidnumber);
			// Convert the Location to a new Extended Location with the new Contents Manager.
			ExtendedLocation newloc = new ExtendedLocation(pilot, location);
			newloc.setContentManager(new DefaultAssetsContentManager(newloc));
			List<NeoComAsset> contents = newloc.downloadContents();

			// Use my own serailization control to return the data to generate exactly what I want.
			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			final String contentsSerialized = mapper.writeValueAsString(contents);

			return contentsSerialized;
		} catch (RuntimeException | JsonProcessingException rtx) {
			rtx.printStackTrace();
			return "{errorMessage: 'No contents'}";
			//			return new Vector<NeoComAsset>();
		} finally {
			logger.info("<< [AssetsManagerController.planetaryLocationContents]");
		}
	}
}

// - UNUSED CODE ............................................................................................
