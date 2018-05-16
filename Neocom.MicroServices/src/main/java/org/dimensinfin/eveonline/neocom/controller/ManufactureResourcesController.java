//  PROJECT:     NeoCom.Microservices (NEOC.MS)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Java 1.8 / SpringBoot-1.3.5 / Angular 5.0
//  DESCRIPTION: This is the SpringBoot MicroServices module to run the backend services to complete the web
//               application based on Angular+SB. This is the web version for the NeoCom Android native
//               application. Most of the source code is common to both platforms and this module includes
//               the source for the specific functionality for the backend services.
package org.dimensinfin.eveonline.neocom.controller;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;

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
import org.dimensinfin.eveonline.neocom.constant.ModelWideConstants;
import org.dimensinfin.eveonline.neocom.core.NeocomRuntimeException;
import org.dimensinfin.eveonline.neocom.database.entity.Credential;
import org.dimensinfin.eveonline.neocom.database.entity.FittingRequest;
import org.dimensinfin.eveonline.neocom.datamngmt.GlobalDataManager;
import org.dimensinfin.eveonline.neocom.datamngmt.InfinityGlobalDataManager;
import org.dimensinfin.eveonline.neocom.exception.JsonExceptionInstance;
import org.dimensinfin.eveonline.neocom.exception.NeoComRegisteredException;
import org.dimensinfin.eveonline.neocom.industry.Fitting;
import org.dimensinfin.eveonline.neocom.industry.Resource;
import org.dimensinfin.eveonline.neocom.model.EveItem;
import org.dimensinfin.eveonline.neocom.model.ManufactureResourcesRequest;
import org.dimensinfin.eveonline.neocom.model.PilotV2;

/**
 * This controller has the entry point to manage the resources for input and output. By the output we have the entry points to
 * get the hull list and LOM for their construction. Also on the output we have the Structure blueprints and their LOM for
 * building that are marked on special Locations/Containers tagged as CITADEL STORAGES.
 *
 * @author Adam Antinoo
 */
// - CLASS IMPLEMENTATION ...................................................................................
@RestController
public class ManufactureResourcesController {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = LoggerFactory.getLogger("ManufactureResourcesController");

	// - F I E L D - S E C T I O N ............................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................

	// - M E T H O D - S E C T I O N ..........................................................................

	/**
	 * This entry point will search for all Pilot/Corporation Fitting Requests and extract their most mineral resource consuming
	 * items like the Hulls. With that information we should locate the matching blueprint and get the List Of Materials required
	 * for their construction including cost data.
	 *
	 * @param sessionLocator the session to locate the credentials and the rest of the session cached information.
	 * @param identifier     the Pilot unique identifier.
	 * @return a list of Manufacture Jobs with their blueprint and LOM for their construction.
	 */
	@CrossOrigin()
	@RequestMapping(value = "/api/v1/pilot/{identifier}/manufactureresources/hullmanufacture"
			, method = RequestMethod.GET, produces = "application/json")
	public String manufactureResourcesHullManufacture( @RequestHeader(value = "xNeocom-Session-Locator", required = false) String
			                                                   sessionLocator
			, @PathVariable final Integer identifier ) {
		logger.info(">>>>>>>>>>>>>>>>>>>>NEW REQUEST: /api/v1/pilot/{}/manufactureresources/hullmanufacture"
				, identifier);
		logger.info(">> [ManufactureResourcesController.manufactureResourcesHullManufacture]");
		logger.info(">> [ManufactureResourcesController.manufactureResourcesHullManufacture]> sessionLocator: {}", sessionLocator);

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
				// Get the list of Fittings to be matched against when we get the Fitting Request numbers.
				InfinityGlobalDataManager.accessFittings4Credential(session.getCredential());
				// Get the list of Fitting Requests.
				final PilotV2 pilot = InfinityGlobalDataManager.requestPilotV2(session.getCredential());
				final int corp = pilot.getCorporation().getCorporationId();
				final List<FittingRequest> corpRequests = InfinityGlobalDataManager.accessCorporationFittingRequests(corp);
				final List<FittingRequest> pilotRequests = InfinityGlobalDataManager.accessPilotFittingRequests(
						session.getCredential().getAccountId()
				);
				// Process the Requests to get the hulls and any other resource that is a heavy mineral consumer.
				final List<FittingRequest> requests = new ArrayList<>();
				requests.addAll(corpRequests);
				requests.addAll(pilotRequests);
				final List<ManufactureResourcesRequest> jobList = new ArrayList<>();
				for (FittingRequest req : requests) {
					final Fitting fitting = InfinityGlobalDataManager.searchFitting4Id(req.targetFitting);
					if (null != fitting) {
						final EveItem hull = fitting.getShipHullInfo();
						// Locate the matching blueprint for the Item.
						final int bpid = new InfinityGlobalDataManager().searchBlueprint4Module(hull.getTypeId());
						// Get the type of Blueprint. Tech > 1 will not use so many mineral resources but a Tech 1 null. Discard them.
//						final EveItem blueprint = new InfinityGlobalDataManager().searchItem4Id(bpid);
						if (hull.getTech().equalsIgnoreCase(ModelWideConstants.eveglobal.TechI)) {
							// Generate the LOM for the blueprint and the number of copies.
							final List<Resource> lom = InfinityGlobalDataManager.searchListOfMaterials4Blueprint(bpid);

							// Create the Job Request to define the target and the resources required.
							jobList.add(new ManufactureResourcesRequest(bpid)
									.setNumberOfCopies(req.copies)
									.setLOM(lom));
						}
					}
				}


				final String contentsSerialized = NeoComMicroServiceApplication.jsonMapper.writeValueAsString(jobList);
				return contentsSerialized;
			} else throw new NeocomRuntimeException("Not access.");
		} catch (JsonProcessingException jspe) {
			return new JsonExceptionInstance(jspe).toJson();
		} catch (NeoComRegisteredException neore) {
			neore.printStackTrace();
			return InfinityGlobalDataManager.serializedException(neore);
		} catch (RuntimeException rtx) {
			logger.error("EX [RoleController.locationAddRole]> Unexpected Exception: {}", rtx.getMessage());
			rtx.printStackTrace();
			return InfinityGlobalDataManager.serializedException(rtx);
		} finally {
			logger.info("<< [RoleController.locationAddRole]");
		}
	}
}

// - UNUSED CODE ............................................................................................
//[01]
