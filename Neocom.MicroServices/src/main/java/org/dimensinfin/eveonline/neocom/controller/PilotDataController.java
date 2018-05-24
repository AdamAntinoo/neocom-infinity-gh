//  PROJECT:     NeoCom.Microservices (NEOC.MS)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Java 1.8 / SpringBoot-1.3.5 / Angular 5.0
//  DESCRIPTION: This is the SpringBoot MicroServices module to run the backend services to complete the web
//               application based on Angular+SB. This is the web version for the NeoCom Android native
//               application. Most of the source code is common to both platforms and this module includes
//               the source for the specific functionality for the backend services.
package org.dimensinfin.eveonline.neocom.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.dimensinfin.eveonline.neocom.database.entity.Credential;
import org.dimensinfin.eveonline.neocom.database.entity.Job;
import org.dimensinfin.eveonline.neocom.database.entity.MarketOrder;
import org.dimensinfin.eveonline.neocom.datamngmt.GlobalDataManager;
import org.dimensinfin.eveonline.neocom.datamngmt.InfinityGlobalDataManager;
import org.dimensinfin.eveonline.neocom.esiswagger.model.GetCharactersCharacterIdIndustryJobs200Ok;
import org.dimensinfin.eveonline.neocom.exception.JsonExceptionInstance;
import org.dimensinfin.eveonline.neocom.exception.NeoComRegisteredException;
import org.dimensinfin.eveonline.neocom.model.PilotV2;

// - CLASS IMPLEMENTATION ...................................................................................

/**
 * This controller will be the entry point for all the actions related to the data stored or available from an Eve Online Pilot.
 * When accessing a pilot credential we should also be able to get to its parent Corporation and from there to the Alliance
 * the corporation belongs. Also we should be able to download the assets, the market trade actions, the industry operations
 * and many more other data that allows to collaborate on the industrial manufacturing activities.
 *
 * @author Adam Antinoo
 */
@RestController
public class PilotDataController {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = LoggerFactory.getLogger("PilotDataController");

	// - F I E L D - S E C T I O N ............................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................

	// - M E T H O D - S E C T I O N ..........................................................................
//	@Autowired
//	FindByIndexNameSessionRepository<? extends Session> sessions;

	/**
	 * This is the specific entry point to read the Pilot information to be presented on any dashboard. It should integrate all
	 * the information possible from public and from core calls. The validation uses the <b>xNeocom-Session-Locator</b> header to
	 * verify this is the Pilot that is really authenticated during the login process.
	 *
	 * @param identifier the pilot unique identifier requested. This should match the identifier stored at the session.
	 * @return a Json serialized set of data corresponding to a PilotV2 class instance.
	 */
	@CrossOrigin()
	@RequestMapping(value = "/api/v1/pilot/{identifier}/publicdata", method = RequestMethod.GET, produces =
			"application/json")
	public String pilotPublicData( @RequestHeader(value = "xNeocom-Session-Locator", required = false) String sessionLocator
			, @PathVariable final Integer identifier ) {
		logger.info(">>>>>>>>>>>>>>>>>>>>NEW REQUEST: /api/v1/pilot/{}/publicdata", identifier);
		logger.info(">> [PilotDataController.pilotPublicData]");
		logger.info(">> [PilotDataController.pilotPublicData]> sessionLocator: {}", sessionLocator);

		try {
//			sessionLocator=new StringBuffer("{
//					\"sessionLocator\" : \"-MANUALLY-CREATED-LOCATOR-\",
//					\"timeValid\" : ")
//			.append(Instant.now().getMillis())
//					.append(" }")
//					.toString();

			// Validate the session locator. Only if this test passes we are authorized to access the Pilot information.
//			if (NeoComMicroServiceApplication.validatePilotIdentifierMatch(sessionLocator, identifier)) {
			if (true) {
				// Create the session context to be used on this request.
//				final GlobalDataManager.SessionContext context = new GlobalDataManager.SessionContext();
				// Set the credential being used on this context.
				// Convert the locator to an instance.
//				final NeoComMicroServiceApplication.SessionLocator locator = NeoComMicroServiceApplication.modelMapper.map(sessionLocator
//						, NeoComMicroServiceApplication.SessionLocator.class);
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
//				context.setCredential(session.getCredential());
				// Get an instance of the v2 version with all the expanded public data that includes corporation information.
				final PilotV2 pilotv2 = InfinityGlobalDataManager.requestPilotV2(session.getCredential());
				final String contentsSerialized;
				contentsSerialized = NeoComMicroServiceApplication.jsonMapper.writeValueAsString(pilotv2);
				return contentsSerialized;
			} else return "Not Access.";
		} catch (JsonProcessingException jspe) {
			return new JsonExceptionInstance(jspe).toJson();
		} catch (NeoComRegisteredException neore) {
			neore.printStackTrace();
			return InfinityGlobalDataManager.serializedException(neore);
		} catch (RuntimeException rtx) {
			logger.error("EX [PilotDataController.pilotPublicData]> Unexpected Exception: {}", rtx.getMessage());
			rtx.printStackTrace();
			return InfinityGlobalDataManager.serializedException(rtx);
		} finally {
			logger.info("<< [PilotDataController.pilotPublicData]");
		}
	}
	@CrossOrigin()
	@RequestMapping(value = "/api/v1/pilot/{identifier}/industryjobs", method = RequestMethod.GET, produces =
			"application/json")
	public String pilotIndustryJobs( @RequestHeader(value = "xNeocom-Session-Locator", required = false) String sessionLocator
			, @PathVariable final Integer identifier ) {
		logger.info(">>>>>>>>>>>>>>>>>>>>NEW REQUEST: /api/v1/pilot/{}/industryjobs", identifier);
		logger.info(">> [PilotDataController.pilotIndustryJobs]");
		logger.info(">> [PilotDataController.pilotIndustryJobs]> sessionLocator: {}", sessionLocator);

		try {
			// Validate the session locator. Only if this test passes we are authorized to access the Pilot information.
//			if (NeoComMicroServiceApplication.validatePilotIdentifierMatch(sessionLocator, identifier)) {
			NeoComSession session = null;
			if (true) {
				final NeoComMicroServiceApplication.SessionLocator locator = new NeoComMicroServiceApplication.SessionLocator()
						.setSessionLocator("-MOCK-LOCATOR-IDENTIFIER-" + identifier + "-")
						.setTimeValid(Instant.now().getMillis());

				if (NeoComMicroServiceApplication.MOCK_UP) {
					// Read all the Credentials from the database and store the one with the pilot identifier on the store.
					final List<Credential> credentials = GlobalDataManager.accessAllCredentials();
					locator.setSessionLocator("-MOCK-LOCATOR-IDENTIFIER-" + identifier + "-");
					for (Credential cred : credentials) {
						if (cred.getAccountId() == identifier) {
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

			// Get all the jobs from the database. Filtering will be performed later.
			final List<Job> jobs = GlobalDataManager.accessIndustryJobs4Credential(session.getCredential());
			final String contentsSerialized = NeoComMicroServiceApplication.jsonMapper.writeValueAsString(jobs);
			return contentsSerialized;
		} catch (JsonProcessingException jspe) {
			return new JsonExceptionInstance(jspe).toJson();
		} catch (RuntimeException rtx) {
			logger.error("EX [FittingManagerController.fittingProcessFitting]> Unexpected Exception: {}", rtx.getMessage());
			rtx.printStackTrace();
			return InfinityGlobalDataManager.serializedException(rtx);
		} catch (SQLException sqle) {
			logger.error("EX [FittingManagerController.fittingProcessFitting]> Database query failed: {}", sqle.getMessage());
			return InfinityGlobalDataManager.serializedException(sqle);
		} finally {
			logger.info("<< [FittingManagerController.fittingProcessFitting]");
		}
	}
	@CrossOrigin()
	@RequestMapping(value = "/api/v1/pilot/{identifier}/marketorders", method = RequestMethod.GET, produces =
			"application/json")
	public String pilotMarketOrders( @RequestHeader(value = "xNeocom-Session-Locator", required = false) String sessionLocator
			, @PathVariable final Integer identifier ) {
		logger.info(">>>>>>>>>>>>>>>>>>>>NEW REQUEST: /api/v1/pilot/{}/marketorders", identifier);
		logger.info(">> [PilotDataController.pilotMarketOrders]");
		logger.info(">> [PilotDataController.pilotMarketOrders]> sessionLocator: {}", sessionLocator);

		try {
			// Validate the session locator. Only if this test passes we are authorized to access the Pilot information.
//			if (NeoComMicroServiceApplication.validatePilotIdentifierMatch(sessionLocator, identifier)) {
			NeoComSession session = null;
			if (true) {
				final NeoComMicroServiceApplication.SessionLocator locator = new NeoComMicroServiceApplication.SessionLocator()
						.setSessionLocator("-MOCK-LOCATOR-IDENTIFIER-" + identifier + "-")
						.setTimeValid(Instant.now().getMillis());

				if (NeoComMicroServiceApplication.MOCK_UP) {
					// Read all the Credentials from the database and store the one with the pilot identifier on the store.
					final List<Credential> credentials = GlobalDataManager.accessAllCredentials();
					locator.setSessionLocator("-MOCK-LOCATOR-IDENTIFIER-" + identifier + "-");
					for (Credential cred : credentials) {
						if (cred.getAccountId() == identifier) {
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

			// Get all the jobs from the database. Filtering will be performed later.
			final List<MarketOrder> orders = GlobalDataManager.accessMarketOrders4Credential(session.getCredential());
			final String contentsSerialized = NeoComMicroServiceApplication.jsonMapper.writeValueAsString(orders);
			return contentsSerialized;
		} catch (JsonProcessingException jspe) {
			return new JsonExceptionInstance(jspe).toJson();
		} catch (RuntimeException rtx) {
			logger.error("EX [FittingManagerController.pilotMarketOrders]> Unexpected Exception: {}", rtx.getMessage());
			rtx.printStackTrace();
			return InfinityGlobalDataManager.serializedException(rtx);
		} catch (SQLException sqle) {
			logger.error("EX [FittingManagerController.pilotMarketOrders]> Database query failed: {}", sqle.getMessage());
			return InfinityGlobalDataManager.serializedException(sqle);
		} finally {
			logger.info("<< [FittingManagerController.pilotMarketOrders]");
		}
	}

//	/**
//	 * Reads all the Pilots assets. Assrt grouping and processing is performed at the UI level so most of the AssetsManager
//	 * original code can be stripped out since the list of assets is returned as a plain and single level list of NeoComAsset
//	 * instances serialized.
//	 * Instead of issuing Region-Location-Conainer-Asset we download the complete and processed list of assets. The only
//	 * exception should be the Container and Ships that will be returned empty. Only when the user selectes to open their
//	 * contents the request should get the list of assets inside that container.
//	 * Locations and Regions are extracted from the list data and any item not located on space or an station on a Citadel will
//	 * not be processed because it is expected to be read when the specific container it belongs is open.
//	 *
//	 * @param identifier
//	 * @return
//	 */
//	@CrossOrigin()
//	@RequestMapping(value = "/api/v1/pilot/{identifier}/assets", method = RequestMethod.GET, produces =
//			"application/json")
//	public String pilotAssets( @PathVariable final Integer identifier ) {
//		logger.info(">>>>>>>>>>>>>>>>>>>>NEW REQUEST: /api/v1/pilot/{}/assets", identifier);
//		logger.info(">> [PilotDataController.pilotAssets]");
//		try {
//			// Activate the list of credentials.
//			final Credential credential = DataManagementModelStore.activateCredential(identifier);
//			final AssetsManager assetsManager = GlobalDataManager.getAssetsManager(credential);
//
//			// Get the list of assets and serialize them back to the front end.
//			final List<NeoComAsset> assets = assetsManager.getAllAssets();
//			final String contentsSerialized = NeoComMicroServiceApplication.jsonMapper.writeValueAsString(assets);
//			return contentsSerialized;
//		} catch (NumberFormatException nfe) {
//			logger.error("EX [PilotDataController.pilotAssets]> identifier received cannot be translated to number - " +
//					"{}", nfe.getMessage());
//			return new JsonExceptionInstance("Identifier received cannot be translated to number - " + nfe.getMessage()
//			).toJson();
//		} catch (JsonProcessingException jpe) {
//			jpe.printStackTrace();
//			return new JsonExceptionInstance(jpe.getMessage()).toJson();
//		} catch (RuntimeException rtx) {
//			rtx.printStackTrace();
//			return new JsonExceptionInstance(rtx.getMessage()).toJson();
//		} finally {
//			logger.info("<< [PilotDataController.pilotAssets]");
//		}
//	}
//
//	/**
//	 * This is a mock methos to return samples manually constructed from json data that should be converted to OK instances and
//	 * then to the MVC instances that are then being returned to the front end.
//	 *
//	 * @param identifier
//	 * @return
//	 */
//	@CrossOrigin()
//	@RequestMapping(value = "/api/v1/pilot/{identifier}/industryjobs", method = RequestMethod.GET, produces =
//			"application/json")
//	public String pilotIndustryJobs( @PathVariable final Integer identifier ) {
//		logger.info(">>>>>>>>>>>>>>>>>>>>NEW REQUEST: /api/v1/pilot/{}/industryjobs", identifier);
//		logger.info(">> [PilotDataController.pilotIndustryJobs]");
//		try {
//			// Construct the list of jobs manually from code. The deserialization of original ESI data did not work.
//			final ArrayList<Job> resultJobList = new ArrayList<Job>();
//			final Job job = new Job(229136101)
//					.setInstallerID(92002067)
//					.setFacilityId(60006526)
//					.setStationId(60006526)
//					.setActivityId(1)
//					.setBlueprintId(1022904992459L)
//					.setBlueprintTypeId(12744)
//					.setBlueprintLocationId(60006526)
//					.setOutputLocationId(1018763120134L)
//					.setRuns(1)
//					.setCost(185.12)
//					.setLicensedRuns(1)
//					.setStatus(GetCharactersCharacterIdIndustryJobs200Ok.StatusEnum.ACTIVE)
//					.setDuration(548)
//					.setStartDate(DateTime.now());
//			job.setEndDate(job.getStartDate().plus(TimeUnit.SECONDS.toMillis(job.getDuration())))
//					.store();
//			resultJobList.add(job);
//
//			final String contentsSerialized = NeoComMicroServiceApplication.jsonMapper.writeValueAsString(resultJobList);
//			return contentsSerialized;
//		} catch (NumberFormatException nfe) {
//			logger.error("EX [PilotDataController.pilotIndustryJobs]> identifier received cannot be translated to number - " +
//					"{}", nfe.getMessage());
//			return new JsonExceptionInstance("Identifier received cannot be translated to number - " + nfe.getMessage()
//			).toJson();
//		} catch (JsonProcessingException jpe) {
//			jpe.printStackTrace();
//			return new JsonExceptionInstance(jpe.getMessage()).toJson();
//		} catch (RuntimeException rtx) {
//			rtx.printStackTrace();
//			return new JsonExceptionInstance(rtx.getMessage()).toJson();
//		} catch (IOException ioe) {
//			ioe.printStackTrace();
//			return new JsonExceptionInstance(ioe.getMessage()).toJson();
//		} finally {
//			logger.info("<< [PilotDataController.pilotIndustryJobs]");
//		}
//	}
//



	private String readJsonMockData( final String filePath ) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		String line = null;
		StringBuilder stringBuilder = new StringBuilder();
		String ls = System.getProperty("line.separator");

		try {
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line);
//				stringBuilder.append(ls);
			}

			return stringBuilder.toString();
		} finally {
			reader.close();
		}
	}

	// TODO Code commented out until the session is implemented and tested.
//

//	protected Fitting searchFitting(final int identifier){
////		logger.info(">> [PilotDataController.searchFitting]");
//		return
////		logger.info("<< [PilotDataController.searchFitting]");
//	}
//[01]
}

// - UNUSED CODE ............................................................................................
//[01]
//	@CrossOrigin()
//	@RequestMapping(value = "/api/v1/login/{login}/pilot/{identifier}", method = RequestMethod.GET, produces = "application/json")
//	public NeoComCharacter pilotDetailed( @PathVariable final String login, @PathVariable final String identifier ) {
//		logger.info(">>>>>>>>>>>>>>>>>>>>NEW REQUEST: " + "/api/v1/login/{" + login + "}/pilot/{" + identifier + "}");
//		logger.info(">> [PilotRoasterController.pilotDetailed]");
//		//	Vector<AbstractManager> managerList = new Vector<AbstractManager>();
//		try {
//			// Initialize the model data hierarchies.
//			NeoComMSConnector.getSingleton().getModelStore().activateLoginIdentifier(login);
//			NeoComCharacter pilot = NeoComMSConnector.getSingleton().getModelStore().activatePilot(Long.valueOf(identifier));
//			return pilot;
//		} catch (RuntimeException rtx) {
//			rtx.printStackTrace();
//			return null;
//		} finally {
//			logger.info("<< [PilotRoasterController.pilotDetailed]");
//		}
//	}
//
//	@CrossOrigin()
//	@RequestMapping(value = "/api/v1/login/{login}/pilot/{identifier}/pilotmanagers", method = RequestMethod.GET, produces = "application/json")
//	public Vector<AbstractManager> pilotManagers( @PathVariable final String login,
//	                                              @PathVariable final String identifier ) {
//		logger.info(">>>>>>>>>>>>>>>>>>>>NEW REQUEST: " + "/api/v1/login/{" + login + "}/pilot/{" + identifier + "}"
//				+ "/pilotmanagers");
//		logger.info(">> [PilotRoasterController.pilotManagers]");
//		Vector<AbstractManager> managerList = new Vector<AbstractManager>();
//		try {
//			// Initialize the model data hierarchies.
//			NeoComMSConnector.getSingleton().getModelStore().activateLoginIdentifier(login);
//			NeoComCharacter pilot = NeoComMSConnector.getSingleton().getModelStore().activatePilot(Long.valueOf(identifier));
//			logger.info("-- [PilotRoasterController.pilotManagers]> Accessing AssetsManager.");
//			managerList.addElement(pilot.getAssetsManager());
//			logger.info("-- [PilotRoasterController.pilotManagers]> Accessing PlanetaryManager.");
//			managerList.addElement(pilot.getPlanetaryManager());
//			return managerList;
//		} catch (RuntimeException rtx) {
//			rtx.printStackTrace();
//			return new Vector<AbstractManager>();
//		} finally {
//			logger.info("<< [PilotRoasterController.pilotManagers]");
//		}
//	}
//
//	@CrossOrigin()
//	@RequestMapping(value = "/api/v1/login/{login}/pilot/{identifier}/planetarymanager", method = RequestMethod.GET, produces = "application/json")
//	public AbstractManager pilotPlanetaryManager( @PathVariable final String login,
//	                                              @PathVariable final String identifier ) {
//		logger.info(">>>>>>>>>>>>>>>>>>>>NEW REQUEST: " + "/api/v1/login/{" + login + "}/pilot/{" + identifier
//				+ "}/planetarymanager");
//		logger.info(">> [PilotRoasterController.pilotPlanetaryManager]");
//		try {
//			// Initialize the model data hierarchies.
//			NeoComMSConnector.getSingleton().getModelStore().activateLoginIdentifier(login);
//			NeoComMSConnector.getSingleton().getModelStore().activatePilot(Long.valueOf(identifier));
//			return NeoComMSConnector.getSingleton().getModelStore().getActiveCharacter().getPlanetaryManager().initialize();
//		} catch (RuntimeException rtx) {
//			rtx.printStackTrace();
//			return null;
//		} finally {
//			logger.info("<< [PilotRoasterController.pilotPlanetaryManager]");
//		}
//	}
