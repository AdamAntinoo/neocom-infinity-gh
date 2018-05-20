//  PROJECT:     NeoCom.Microservices (NEOC.MS)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Java 1.8 / SpringBoot-1.3.5 / Angular 5.0
//  DESCRIPTION: This is the SpringBoot MicroServices module to run the backend services to complete the web
//               application based on Angular+SB. This is the web version for the NeoCom Android native
//               application. Most of the source code is common to both platforms and this module includes
//               the source for the specific functionality for the backend services.
package org.dimensinfin.eveonline.neocom.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sun.org.apache.xpath.internal.operations.Gt;

import org.joda.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import org.dimensinfin.eveonline.neocom.NeoComMicroServiceApplication;
import org.dimensinfin.eveonline.neocom.NeoComSession;
import org.dimensinfin.eveonline.neocom.core.NeocomRuntimeException;
import org.dimensinfin.eveonline.neocom.database.entity.Credential;
import org.dimensinfin.eveonline.neocom.datamngmt.GlobalDataManager;
import org.dimensinfin.eveonline.neocom.datamngmt.InfinityGlobalDataManager;
import org.dimensinfin.eveonline.neocom.exception.JsonExceptionInstance;
import org.dimensinfin.eveonline.neocom.exception.NeoComRegisteredException;
import org.dimensinfin.eveonline.neocom.model.NeoComAsset;
import org.dimensinfin.eveonline.neocom.planetary.PlanetaryProcessor;
import org.dimensinfin.eveonline.neocom.planetary.PlanetaryScenery;
import org.dimensinfin.eveonline.neocom.planetary.ProcessingAction;

// - CLASS IMPLEMENTATION ...................................................................................
@RestController
public class PlanetaryManagerController {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = LoggerFactory.getLogger("PlanetaryManagerController");

	// - F I E L D - S E C T I O N ............................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................

	// - M E T H O D - S E C T I O N ..........................................................................
	@CrossOrigin()
	@RequestMapping(value = "/api/v1/pilot/{identifier}/planetarymanager/planetaryresources"
			, method = RequestMethod.GET, produces = "application/json")
	public String planetaryResources( @RequestHeader(value = "xNeocom-Session-Locator", required = false) String
			                                                    sessionLocator
			, @PathVariable final Integer identifier ) {
		logger.info(">>>>>>>>>>>>>>>>>>>>NEW REQUEST: /api/v1/pilot/{}/manufactureresources/structuremanufacture"
				, identifier);
		logger.info(">> [PlanetaryManagerController.planetaryResources]");
		logger.info(">> [PlanetaryManagerController.planetaryResources]> sessionLocator: {}", sessionLocator);

		int processed = 0;
		try {
			// Validate the session locator. Only if this test passes we are authorized to access the Pilot information.
//			if (NeoComMicroServiceApplication.validatePilotIdentifierMatch(sessionLocator, identifier)) {
			if (true) {
				// Set the credential being used on this context.
				// Convert the locator to an instance.
				final NeoComMicroServiceApplication.SessionLocator locator = new NeoComMicroServiceApplication.SessionLocator()
						.setSessionLocator("-MOCK-LOCATOR-IDENTIFIER-" + identifier + "-")
						.setTimeValid(Instant.now().getMillis());

				if (NeoComMicroServiceApplication.MOCK_UP) {
					// Read all the Credentials from the database and store the one with the pilot identifier on the store.
					final List<Credential> credentials = GlobalDataManager.accessAllCredentials();
					locator.setSessionLocator("-MOCK-LOCATOR-IDENTIFIER-" + identifier + "-");
					for (Credential cred : credentials) {
						if (cred.getAccountId() == identifier) {
							final NeoComSession session = new NeoComSession()
									.setCredential(cred)
									.setPublicKey("-INVALID-PUBLIC-KEY-");
							NeoComMicroServiceApplication.sessionStore.put(locator.getSessionLocator(), session);
						}
					}
				}
				final NeoComSession session = NeoComMicroServiceApplication.sessionStore.get(locator.getSessionLocator());

				// --- C O N T R O L L E R   B O D Y
				// Search the complete list of Planetary Resources fro this Pilot.
				// Search for all the blueprints that match to Structure Components.
				HashMap<String, Object> where = new HashMap<String, Object>();
				where.put("ownerId", session.getCredential().getAccountId());
				where.put("category", "Planetary Resources");
				final List<NeoComAsset> planetaryResources = new InfinityGlobalDataManager().getNeocomDBHelper().getAssetDao()
						.queryForFieldValues(where);
				where = new HashMap<String, Object>();
				where.put("ownerId", session.getCredential().getAccountId());
				where.put("category", "Planetary Commodities");
				planetaryResources.addAll( new InfinityGlobalDataManager().getNeocomDBHelper().getAssetDao()
						.queryForFieldValues(where));

				processed=planetaryResources.size();
				final String contentsSerialized = NeoComMicroServiceApplication.jsonMapper.writeValueAsString(planetaryResources);
				return contentsSerialized;
			} else throw new NeocomRuntimeException("Not access.");
		} catch (JsonProcessingException jspe) {
			return new JsonExceptionInstance(jspe).toJson();
//		} catch (NeoComRegisteredException neore) {
//			neore.printStackTrace();
//			return InfinityGlobalDataManager.serializedException(neore);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return InfinityGlobalDataManager.serializedException(sqle);
		} catch (RuntimeException rtx) {
			logger.error("EX [ManufactureResourcesController.manufactureResourcesStructureManufacture]> Unexpected Exception: {}", rtx.getMessage());
			rtx.printStackTrace();
			return InfinityGlobalDataManager.serializedException(rtx);
		} finally {
			logger.info("<< [ManufactureResourcesController.manufactureResourcesStructureManufacture]> Resources processed: ",
					processed);
		}
	}

//	@CrossOrigin()
//	@RequestMapping(value = "/api/v1/login/{login}/pilot/{identifier}/planetarymanager/location/{locationid}/optimizeprocess", method = RequestMethod.GET, produces = "application/json")
//	public Vector<ProcessingAction> planetaryLocationOptimization( @PathVariable final String login,
//	                                                               @PathVariable final String identifier, @PathVariable final String locationid ) {
//		logger.info(">>>>>>>>>>>>>>>>>>>>NEW REQUEST: "
//				+ "/api/v1/login/{login}/pilot/{identifier}/planetarymanager/location/{locationid}/optimizeprocess");
//		logger.info(">> [PilotRoasterController.planetaryLocationOptimization]>login: " + login);
//		logger.info(">> [PilotRoasterController.planetaryLocationOptimization]>identifier: " + identifier);
//		logger.info(">> [PilotRoasterController.planetaryLocationOptimization]>locationid: " + locationid);
//		Vector<ProcessingAction> bestScenario = new Vector<ProcessingAction>();
//		try {
//			// Initialize the model data hierarchies.
//			NeoComMSConnector.getSingleton().getModelStore().activateLoginIdentifier(login);
//			NeoComMSConnector.getSingleton().getModelStore().activatePilot(Long.valueOf(identifier));
//			// Get the Planetary Manager for this Character. Make sure it is initialized and then get the resources
//			// at the indicated location and optimize processing them.
//			PlanetaryManager planetary = NeoComMSConnector.getSingleton().getModelStore().getActiveCharacter()
//					.getPlanetaryManager();
//			planetary.initialize();
//			Vector<Resource> resources = planetary.getLocationContents(locationid);
//
//			// The Planetary Advisor requires a list of Planetary Resources to be stocked to start the profit calculations.
//			PlanetaryScenery scenery = new PlanetaryScenery();
//			scenery.stock(resources);
//			// Create the initial processing point and start the optimization recursively.
//			PlanetaryProcessor proc = new PlanetaryProcessor(scenery);
//			// Start running the best profit search.
//			bestScenario = proc.startProfitSearch(null);
//
//			// Get the results back to the UI. Format them to tho expected data structure.
//			return bestScenario;
//		} catch (RuntimeException rtx) {
//			rtx.printStackTrace();
//			return new Vector<ProcessingAction>();
//		} finally {
//			logger.info("<< [PilotRoasterController.planetaryLocationOptimization]");
//		}
//	}
}

// - UNUSED CODE ............................................................................................
