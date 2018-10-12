//  PROJECT:     NeoCom.Microservices (NEOC.MS)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Java 1.8 / SpringBoot-1.3.5 / Angular 5.0
//  DESCRIPTION: This is the SpringBoot MicroServices module to run the backend services to complete the web
//               application based on Angular+SB. This is the web version for the NeoCom Android native
//               application. Most of the source code is common to both platforms and this module includes
//               the source for the specific functionality for the backend services.
package org.dimensinfin.eveonline.neocom.controller;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

import org.joda.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.dimensinfin.eveonline.neocom.NeoComMicroServiceApplication;
import org.dimensinfin.eveonline.neocom.NeoComSession;
import org.dimensinfin.eveonline.neocom.datamngmt.GlobalDataManager;
import org.dimensinfin.eveonline.neocom.datamngmt.InfinityGlobalDataManager;
import org.dimensinfin.eveonline.neocom.entities.Credential;
import org.dimensinfin.eveonline.neocom.entities.FittingRequest;
import org.dimensinfin.eveonline.neocom.exception.JsonExceptionInstance;
import org.dimensinfin.eveonline.neocom.exception.NeoComException;
import org.dimensinfin.eveonline.neocom.exception.NeoComRegisteredException;
import org.dimensinfin.eveonline.neocom.industry.Action;
import org.dimensinfin.eveonline.neocom.industry.Fitting;
import org.dimensinfin.eveonline.neocom.processor.FittingProcessor;
import org.dimensinfin.eveonline.neocom.security.SessionManager;

// - CLASS IMPLEMENTATION ...................................................................................

/**
 * This controller will be responsible to manage the Fitting list and all the Fitting management duties like the registration
 * of new fitting requests of the processing for a fitting into the list of actions and tasks that have to be accomplished to
 * complete a request.
 * <p>
 * Fitting Requests are volatile and should be checked for completion of associated jobs and logistics. While the Pilot
 * download for such data is responsibility of the Pilot controller all data matching and request processing responsibility for
 * this controller.
 * @author Adam Antinoo
 */
@RestController
public class FittingManagerController {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = LoggerFactory.getLogger("FittingManagerController");
//	private static final HashMap<Integer, Fitting> fittingsCache = new HashMap<>();

	// - F I E L D - S E C T I O N ............................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................

	// - M E T H O D - S E C T I O N ..........................................................................

	/**
	 * Returns the list of fittings that are accessible to this Pilot identifier. This data will be processed at the Angular side
	 * to generate any UI structures required for a proper presentation.
	 * @param identifier identifier for the selected Pilot.
	 * @return list of OK class fittings serialized to Json.
	 */
	@CrossOrigin()
	@RequestMapping(value = "/api/v1/pilot/{identifier}/fittingmanager/fittings", method = RequestMethod.GET, produces =
			"application/json")
	public String pilotFittingManagerFittings( @RequestHeader(value = "xNeocom-Session-Locator", required = false) String sessionLocator
			, @PathVariable final Integer identifier ) {
		logger.info(">>>>>>>>>>>>>>>>>>>>NEW REQUEST: /api/v1/pilot/{}/fittingmanager/fittings", identifier);
		logger.info(">> [FittingManagerController.pilotFittingManagerFittings]");
		logger.info(">> [FittingManagerController.pilotFittingManagerFittings]> sessionLocator: {}", sessionLocator);
		try {
			// Validate the session locator. Only if this test passes we are authorized to access the Pilot information.
//			if (NeoComMicroServiceApplication.validatePilotIdentifierMatch(sessionLocator, identifier)) {
			NeoComSession session = null;
			if ( true ) {
				final NeoComMicroServiceApplication.SessionLocator locator = new NeoComMicroServiceApplication.SessionLocator()
						.setSessionLocator("-MOCK-LOCATOR-IDENTIFIER-" + identifier + "-")
						.setTimeValid(Instant.now().getMillis());

				if ( NeoComMicroServiceApplication.MOCK_UP ) {
					// Read all the Credentials from the database and store the one with the pilot identifier on the store.
					final List<Credential> credentials = GlobalDataManager.accessAllCredentials();
					locator.setSessionLocator("-MOCK-LOCATOR-IDENTIFIER-" + identifier + "-");
					for ( Credential cred : credentials ) {
						if ( cred.getAccountId() == identifier ) {
							session = new NeoComSession()
									.setCredential(cred)
									.setPublicKey("-INVALID-PUBLIC-KEY-");
							NeoComMicroServiceApplication.sessionStore.put(locator.getSessionLocator(), session);
						}
					}
				}
				// Validate the location against the session store.
				session = NeoComMicroServiceApplication.sessionStore.get(locator.getSessionLocator());
			} else return "Not Access.";

			// --- C O N T R O L L E R   B O D Y
			// Access the Fittings. Download them and store on cache.
			final List<Fitting> fittings = InfinityGlobalDataManager.accessFittings4Credential(session.getCredential());
			final String contentsSerialized = NeoComMicroServiceApplication.jsonMapper.writeValueAsString(fittings);
			return contentsSerialized;
		} catch ( JsonProcessingException jspe ) {
			return new JsonExceptionInstance(jspe).toJson();
//		} catch (NeoComRegisteredException neore) {
//			neore.printStackTrace();
//			return InfinityGlobalDataManager.serializedException(neore);
		} catch ( RuntimeException rtx ) {
			logger.error("EX [FittingManagerController.pilotFittingManagerFittings]> Unexpected Exception: {}", rtx.getMessage());
			rtx.printStackTrace();
			return InfinityGlobalDataManager.serializedException(rtx);
		} finally {
			logger.info("<< [FittingManagerController.pilotFittingManagerFittings]");
		}
	}

	/**
	 * This is the specific entry point to read the Pilot information to be presented on any dashboard. It should integrate all
	 * the information possible from public and from core calls. The validation uses the <b>xNeocom-Session-Locator</b> header to
	 * verify this is the Pilot that is really authenticated during the login process.
	 * @param identifier the pilot unique identifier requested. This should match the identifier stored at the session.
	 * @return a Json serialized set of data corresponding to a PilotV2 class instance.
	 */
	@CrossOrigin()
	@RequestMapping(value = "/api/v1/pilot/{identifier}/fittingmanager/fittingrequests", method = RequestMethod.GET, produces =
			"application/json")
	public String fittingFittingRequests( @RequestHeader(value = "xNeocom-Session-Locator", required = false) String sessionLocator
			, @PathVariable final Integer identifier ) {
		logger.info(">>>>>>>>>>>>>>>>>>>>NEW REQUEST: /api/v1/pilot/{}/fittingmanager/fittingrequests", identifier);
		logger.info(">> [FittingManagerController.fittingFittingRequests]");
		logger.info(">> [FittingManagerController.fittingFittingRequests]> sessionLocator: {}", sessionLocator);

		try {
			// Validate the session locator. Only if this test passes we are authorized to access the Pilot information.
//			if (NeoComMicroServiceApplication.validatePilotIdentifierMatch(sessionLocator, identifier)) {
			NeoComSession session = null;
			if ( true ) {
				final NeoComMicroServiceApplication.SessionLocator locator = new NeoComMicroServiceApplication.SessionLocator()
						.setSessionLocator("-MOCK-LOCATOR-IDENTIFIER-" + identifier + "-")
						.setTimeValid(Instant.now().getMillis());

				if ( NeoComMicroServiceApplication.MOCK_UP ) {
					// Read all the Credentials from the database and store the one with the pilot identifier on the store.
					final List<Credential> credentials = GlobalDataManager.accessAllCredentials();
					locator.setSessionLocator("-MOCK-LOCATOR-IDENTIFIER-" + identifier + "-");
					for ( Credential cred : credentials ) {
						if ( cred.getAccountId() == identifier ) {
							session = new NeoComSession()
									.setCredential(cred)
									.setPublicKey("-INVALID-PUBLIC-KEY-");
							NeoComMicroServiceApplication.sessionStore.put(locator.getSessionLocator(), session);
						}
					}
				}
				// Validate the location against the session store.
				session = NeoComMicroServiceApplication.sessionStore.get(locator.getSessionLocator());
			} else return "Not Access.";

			// Once we have a session we can start to request data.
			// Go to the database and get the complete list of requests for this pilot Corporation.
			final int corp = InfinityGlobalDataManager.requestPilotV2(session.getCredential()).getCorporation().getCorporationId();
			final List<FittingRequest> requests = InfinityGlobalDataManager.accessCorporationFittingRequests(corp);
			final String contentsSerialized = NeoComMicroServiceApplication.jsonMapper.writeValueAsString(requests);
			return contentsSerialized;
		} catch ( JsonProcessingException jspe ) {
			return new JsonExceptionInstance(jspe).toJson();
		} catch ( NeoComRegisteredException neore ) {
			neore.printStackTrace();
			return InfinityGlobalDataManager.serializedException(neore);
		} catch ( RuntimeException rtx ) {
			logger.error("EX [FittingManagerController.fittingFittingRequests]> Unexpected Exception: {}", rtx.getMessage());
			rtx.printStackTrace();
			return InfinityGlobalDataManager.serializedException(rtx);
		} finally {
			logger.info("<< [FittingManagerController.fittingFittingRequests]");
		}
	}

	/**
	 * Returns the list of fittings that are accesible to this Pilot identifier. This data will be processed at the Angular side
	 * to generate any UI structures required for a proper presentation.
	 * @param identifier identifier for the selected Pilot.
	 * @return list of OK class fittings serialized to Json.
	 */
	@CrossOrigin()
	@RequestMapping(value = "/api/v1/pilot/{identifier}/fittingmanager/processfitting/{fittingidentifier}/copies/{copies}"
			, method = RequestMethod.GET
			, produces = "application/json")
	public String fittingProcessFitting( @RequestHeader(value = "xNeocom-Session-Locator", required = false) String
			                                     sessionLocator
			, @PathVariable final int identifier
			, @PathVariable final int fittingidentifier
			, @PathVariable final int copies ) {
		logger.info(">>>>>>>>>>>>>>>>>>>>NEW REQUEST: /api/v1/pilot/{}/fittingmanager/processfitting/{}/copies/{}"
				, identifier, fittingidentifier, copies);
		logger.info(">> [FittingManagerController.fittingProcessFitting]");
		logger.info(">> [FittingManagerController.fittingProcessFitting]> sessionLocator: {}", sessionLocator);

		try {
			// Validate the session locator. Only if this test passes we are authorized to access the Pilot information.
//			if (NeoComMicroServiceApplication.validatePilotIdentifierMatch(sessionLocator, identifier)) {
			NeoComSession session = null;
			if ( true ) {
				final NeoComMicroServiceApplication.SessionLocator locator = new NeoComMicroServiceApplication.SessionLocator()
						.setSessionLocator("-MOCK-LOCATOR-IDENTIFIER-" + identifier + "-")
						.setTimeValid(Instant.now().getMillis());

				if ( NeoComMicroServiceApplication.MOCK_UP ) {
					// Read all the Credentials from the database and store the one with the pilot identifier on the store.
					final List<Credential> credentials = GlobalDataManager.accessAllCredentials();
					locator.setSessionLocator("-MOCK-LOCATOR-IDENTIFIER-" + identifier + "-");
					for ( Credential cred : credentials ) {
						if ( cred.getAccountId() == identifier ) {
							session = new NeoComSession()
									.setCredential(cred)
									.setPublicKey("-INVALID-PUBLIC-KEY-");
							NeoComMicroServiceApplication.sessionStore.put(locator.getSessionLocator(), session);
						}
					}
				}
				// Validate the location against the session store.
				session = NeoComMicroServiceApplication.sessionStore.get(locator.getSessionLocator());
			} else return "Not Access.";

			// Go to the service and process the fitting to return the result to the front end.
			final FittingProcessor processor = new FittingProcessor();
			InfinityGlobalDataManager.accessFittings4Credential(session.getCredential());
			// Search for the fitting
			final Fitting target = InfinityGlobalDataManager.searchFitting4Id(fittingidentifier);
			if ( null != target ) {
				final List<Action> actions = processor.processFitting(session.getCredential(), target, copies);
				final String contentsSerialized = NeoComMicroServiceApplication.jsonMapper.writeValueAsString(actions);
				return contentsSerialized;
			} else {
				return NeoComMicroServiceApplication.jsonMapper.writeValueAsString(new NeoComException("Fitting id " + fittingidentifier + " not found."));
			}
		} catch ( JsonProcessingException jspe ) {
			return new JsonExceptionInstance(jspe).toJson();
//		} catch (NeoComRegisteredException neore) {
//			neore.printStackTrace();
//			return InfinityGlobalDataManager.serializedException(neore);
		} catch ( RuntimeException rtx ) {
			logger.error("EX [FittingManagerController.fittingProcessFitting]> Unexpected Exception: {}", rtx.getMessage());
			rtx.printStackTrace();
			return InfinityGlobalDataManager.serializedException(rtx);
		} finally {
			logger.info("<< [FittingManagerController.fittingProcessFitting]");
		}
	}

	//--- V E R S I O N   2   E N T R Y P O I N T S
	/**
	 * Returns the list of fittings that are accessible to this Pilot identifier. This data will be processed at the Angular side
	 * to generate any UI structures required for a proper presentation.
	 * @return list of OK class fittings serialized to Json.
	 */
	@CrossOrigin()
	@GetMapping("/api/v2/pilot/fittings")
	public ResponseEntity<List<Fitting>> pilotFittingManagerFittings( @RequestHeader(value = "xApp-Authentication", required = true) String _token ) {
		// The headers should have the authorization data enough to retrieve the session.
		final SessionManager.AppSession session = SessionManager.retrieve(_token);
		if ( null != session ) {
			try {
				// Activate the list of credentials.
				final Credential credential = session.getCredential();

				// --- C O N T R O L L E R   B O D Y
				// Access the Fittings. Download them and store on cache.
				final List<Fitting> fittings = InfinityGlobalDataManager.accessFittings4Credential(credential);
				return new ResponseEntity<List<Fitting>>(fittings, HttpStatus.OK);
			} catch ( RuntimeException rtx ) {
				rtx.printStackTrace();
				return new ResponseEntity<List<Fitting>>(HttpStatus.BAD_REQUEST);
			}
		} else return new ResponseEntity<List<Fitting>>(HttpStatus.UNAUTHORIZED);
	}
}

// - UNUSED CODE ............................................................................................
//[01]
