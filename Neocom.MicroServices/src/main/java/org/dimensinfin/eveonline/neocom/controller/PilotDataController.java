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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import org.dimensinfin.eveonline.neocom.NeoComMicroServiceApplication;
import org.dimensinfin.eveonline.neocom.database.entity.Colony;
import org.dimensinfin.eveonline.neocom.database.entity.ColonyStorage;
import org.dimensinfin.eveonline.neocom.database.entity.Credential;
import org.dimensinfin.eveonline.neocom.datamngmt.manager.GlobalDataManager;
import org.dimensinfin.eveonline.neocom.esiswagger.model.GetCharactersCharacterIdIndustryJobs200Ok;
import org.dimensinfin.eveonline.neocom.exception.JsonExceptionInstance;
import org.dimensinfin.eveonline.neocom.industry.Action;
import org.dimensinfin.eveonline.neocom.industry.FittingProcessor;
import org.dimensinfin.eveonline.neocom.industry.Job;
import org.dimensinfin.eveonline.neocom.manager.AssetsManager;
import org.dimensinfin.eveonline.neocom.model.Fitting;
import org.dimensinfin.eveonline.neocom.model.NeoComAsset;
import org.dimensinfin.eveonline.neocom.storage.DataManagementModelStore;

// - CLASS IMPLEMENTATION ...................................................................................
@RestController
public class PilotDataController {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = LoggerFactory.getLogger("PilotDataController");
	private static final HashMap<Integer, Fitting> fittingsCache = new HashMap<>();

	// - F I E L D - S E C T I O N ............................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................

	// - M E T H O D - S E C T I O N ..........................................................................

	/**
	 * Reads all the Pilots assets. Assrt grouping and processing is performed at the UI level so most of the AssetsManager
	 * original code can be stripped out since the list of assets is returned as a plain and single level list of NeoComAsset
	 * instances serialized.
	 * Instead of issuing Region-Location-Conainer-Asset we download the complete and processed list of assets. The only
	 * exception should be the Container and Ships that will be returned empty. Only when the user selectes to open their
	 * contents the request should get the list of assets inside that container.
	 * Locations and Regions are extracted from the list data and any item not located on space or an station on a Citadel will
	 * not be processed because it is expected to be read when the specific container it belongs is open.
	 *
	 * @param identifier
	 * @return
	 */
	@CrossOrigin()
	@RequestMapping(value = "/api/v1/pilot/{identifier}/assets", method = RequestMethod.GET, produces =
			"application/json")
	public String pilotAssets( @PathVariable final Integer identifier ) {
		logger.info(">>>>>>>>>>>>>>>>>>>>NEW REQUEST: /api/v1/pilot/{}/assets", identifier);
		logger.info(">> [PilotDataController.pilotAssets]");
		try {
			// Activate the list of credentials.
			final Credential credential = DataManagementModelStore.activateCredential(identifier);
			final AssetsManager assetsManager = GlobalDataManager.getAssetsManager(credential);

			// Get the list of assets and serialize them back to the front end.
			final List<NeoComAsset> assets = assetsManager.getAllAssets();
			final String contentsSerialized = NeoComMicroServiceApplication.jsonMapper.writeValueAsString(assets);
			return contentsSerialized;
		} catch (NumberFormatException nfe) {
			logger.error("EX [PilotDataController.pilotAssets]> identifier received cannot be translated to number - " +
					"{}", nfe.getMessage());
			return new JsonExceptionInstance("Identifier received cannot be translated to number - " + nfe.getMessage()
			).toJson();
		} catch (JsonProcessingException jpe) {
			jpe.printStackTrace();
			return new JsonExceptionInstance(jpe.getMessage()).toJson();
		} catch (RuntimeException rtx) {
			rtx.printStackTrace();
			return new JsonExceptionInstance(rtx.getMessage()).toJson();
		} finally {
			logger.info("<< [PilotDataController.pilotAssets]");
		}
	}

	/**
	 * This is a mock methos to return samples manually constructed from json data that should be converted to OK instances and
	 * then to the MVC instances that are then being returned to the front end.
	 *
	 * @param identifier
	 * @return
	 */
	@CrossOrigin()
	@RequestMapping(value = "/api/v1/pilot/{identifier}/industryjobs", method = RequestMethod.GET, produces =
			"application/json")
	public String pilotIndustryJobs( @PathVariable final Integer identifier ) {
		logger.info(">>>>>>>>>>>>>>>>>>>>NEW REQUEST: /api/v1/pilot/{}/industryjobs", identifier);
		logger.info(">> [PilotDataController.pilotIndustryJobs]");
		try {
			// Construct the list of jobs manually from code. The deserialization of original ESI data did not work.
			final ArrayList<Job> resultJobList = new ArrayList<Job>();
			final Job job = new Job(229136101)
					.setInstallerID(92002067)
					.setFacilityId(60006526)
					.setStationId(60006526)
					.setActivityId(1)
					.setBlueprintId(1022904992459L)
					.setBlueprintTypeId(12744)
					.setBlueprintLocationId(60006526)
					.setOutputLocationId(1018763120134L)
					.setRuns(1)
					.setCost(185.12)
					.setLicensedRuns(1)
					.setStatus(GetCharactersCharacterIdIndustryJobs200Ok.StatusEnum.ACTIVE)
					.setDuration(548)
					.setStartDate( DateTime.now());
			job.setEndDate(job.getStartDate().plus(TimeUnit.SECONDS.toMillis(job.getDuration())))
					.store();
			resultJobList.add(job);

			final String contentsSerialized = NeoComMicroServiceApplication.jsonMapper.writeValueAsString(resultJobList);
			return contentsSerialized;
		} catch (NumberFormatException nfe) {
			logger.error("EX [PilotDataController.pilotIndustryJobs]> identifier received cannot be translated to number - " +
					"{}", nfe.getMessage());
			return new JsonExceptionInstance("Identifier received cannot be translated to number - " + nfe.getMessage()
			).toJson();
		} catch (JsonProcessingException jpe) {
			jpe.printStackTrace();
			return new JsonExceptionInstance(jpe.getMessage()).toJson();
		} catch (RuntimeException rtx) {
			rtx.printStackTrace();
			return new JsonExceptionInstance(rtx.getMessage()).toJson();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return new JsonExceptionInstance(ioe.getMessage()).toJson();
		} finally {
			logger.info("<< [PilotDataController.pilotIndustryJobs]");
		}
	}
	public String pilotIndustryJobsold( @PathVariable final Integer identifier ) {
		logger.info(">>>>>>>>>>>>>>>>>>>>NEW REQUEST: /api/v1/pilot/{}/industryjobs", identifier);
		logger.info(">> [PilotDataController.pilotIndustryJobs]");
		try {
			// Read the json file to a local string.
			final String jsonData = readJsonMockData(GlobalDataManager.getResourceString("R.mock.path")
					+ GlobalDataManager.getResourceString("R.mock.industryjobs"));
			// Convert first to OK then to MVC.
			final List<GetCharactersCharacterIdIndustryJobs200Ok> jobsOK = Arrays.asList(NeoComMicroServiceApplication.jsonMapper
					.readValue(jsonData
							, GetCharactersCharacterIdIndustryJobs200Ok[].class));
			final ArrayList<Job> resultJobList = new ArrayList<Job>();
			for (GetCharactersCharacterIdIndustryJobs200Ok jobOK : jobsOK) {
				final Job job = NeoComMicroServiceApplication.modelMapper.map(jobOK, Job.class);
				resultJobList.add(job);
			}
			final String contentsSerialized = NeoComMicroServiceApplication.jsonMapper.writeValueAsString(resultJobList);
			return contentsSerialized;
		} catch (NumberFormatException nfe) {
			logger.error("EX [PilotDataController.pilotIndustryJobs]> identifier received cannot be translated to number - " +
					"{}", nfe.getMessage());
			return new JsonExceptionInstance("Identifier received cannot be translated to number - " + nfe.getMessage()
			).toJson();
		} catch (JsonProcessingException jpe) {
			jpe.printStackTrace();
			return new JsonExceptionInstance(jpe.getMessage()).toJson();
		} catch (RuntimeException rtx) {
			rtx.printStackTrace();
			return new JsonExceptionInstance(rtx.getMessage()).toJson();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return new JsonExceptionInstance(ioe.getMessage()).toJson();
		} finally {
			logger.info("<< [PilotDataController.pilotIndustryJobs]");
		}
	}

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

	/**
	 * Returns the list of fittings that are accessible to this Pilot identifier. This data will be processed at the Angular side
	 * to generate any UI structures required for a proper presentation.
	 *
	 * @param identifier identifier for the selected Pilot.
	 * @return list of OK class fittings serialized to Json.
	 */
	@CrossOrigin()
	@RequestMapping(value = "/api/v1/pilot/{identifier}/fittingmanager/fittings", method = RequestMethod.GET, produces =
			"application/json")
	public String pilotFittingManagerFittings( @PathVariable final String identifier ) {
		logger.info(">>>>>>>>>>>>>>>>>>>>NEW REQUEST: /api/v1/pilot/{}/fittingmanager/fittings", identifier);
		logger.info(">> [PilotDataController.pilotFittingManagerFittings]");
		try {
			final Integer id = Integer.valueOf(identifier);
			// Activate the list of credentials.
			DataManagementModelStore.activateCredential(id);
			// Get the list of fittings.
			final List<Fitting> fittings = GlobalDataManager.downloadFitting4Credential(id);
			final String contentsSerialized = NeoComMicroServiceApplication.jsonMapper.writeValueAsString(fittings);
//			// Initialize the model data hierarchies.
//			NeoComMSConnector.getSingleton().getModelStore().activateLoginIdentifier(login);
//			NeoComCharacter pilot = NeoComMSConnector.getSingleton().getModelStore().activatePilot(Long.valueOf(identifier));
//			AssetsManager assetsMan = pilot.getAssetsManager().initialize();
			//			// Download the contents for all locations.
			//			Hashtable<Long, ExtendedLocation> locs = assetsMan.getLocations();
			//			for (Long key : locs.keySet()) {
			//				locs.get(key).getContents();
			//			}
			return contentsSerialized;
		} catch (NumberFormatException nfe) {
			logger.error("EX [PilotDataController.pilotFittingManagerFittings]> identifier received cannot be translated to number - " +
					"{}", nfe.getMessage());
			return new JsonExceptionInstance("Identifier received cannot be translated to number - " + nfe.getMessage()
			).toJson();
		} catch (JsonProcessingException jpe) {
			jpe.printStackTrace();
			return new JsonExceptionInstance(jpe.getMessage()).toJson();
		} catch (RuntimeException rtx) {
			rtx.printStackTrace();
			return new JsonExceptionInstance(rtx.getMessage()).toJson();
		} finally {
			logger.info("<< [PilotDataController.pilotFittingManagerFittings]");
		}
	}

	/**
	 * Returns the list of fittings that are accesible to this Pilot identifier. This data will be processed at the Angular side
	 * to generate any UI structures required for a proper presentation.
	 *
	 * @param identifier identifier for the selected Pilot.
	 * @return list of OK class fittings serialized to Json.
	 */
	@CrossOrigin()
	@RequestMapping(value = "/api/v1/pilot/{identifier}/fittingmanager/processfitting/{fittingidentifier}/copies/{copies}"
			, method = RequestMethod.GET
			, produces = "application/json")
	public String pilotFittingManagerProcessFitting( @PathVariable final int identifier
			, @PathVariable final int fittingidentifier
			, @PathVariable final int copies ) {
		logger.info(">>>>>>>>>>>>>>>>>>>>NEW REQUEST: /api/v1/pilot/{}/fittingmanager/processfitting/{}/copies/{}"
				, identifier, fittingidentifier, copies);
		logger.info(">> [PilotDataController.pilotFittingManagerProcessFitting]");
		try {
			// Activate the list of credentials.
			final Credential credential = DataManagementModelStore.activateCredential(identifier);
			// Get the list of fittings.
			final List<Fitting> fittings = GlobalDataManager.downloadFitting4Credential(identifier);
			addFittings2Cache(fittings);
			// Search for the fitting
			final Fitting target = fittingsCache.get(fittingidentifier);
			final FittingProcessor processor = new FittingProcessor();
			final List<Action> actions = processor.processFitting(identifier, target, copies);

			// Searialize the results.
			final String contentsSerialized = NeoComMicroServiceApplication.jsonMapper.writeValueAsString(actions);
			return contentsSerialized;
		} catch (NumberFormatException nfe) {
			logger.error("EX [PilotDataController.pilotFittingManagerFittings]> identifier received cannot be translated to number - " +
					"{}", nfe.getMessage());
			return new JsonExceptionInstance("Identifier received cannot be translated to number - " + nfe.getMessage()
			).toJson();
		} catch (JsonProcessingException jpe) {
			jpe.printStackTrace();
			return new JsonExceptionInstance(jpe.getMessage()).toJson();
		} catch (RuntimeException rtx) {
			rtx.printStackTrace();
			try {
				return NeoComMicroServiceApplication.jsonMapper.writeValueAsString(new JsonExceptionInstance(rtx.getMessage()));
			} catch (JsonProcessingException e) {
				return new JsonExceptionInstance(rtx.getMessage()).toJson();
			}
		} finally {
			logger.info("<< [PilotDataController.pilotFittingManagerProcessFitting]");
		}
	}

	protected void addFittings2Cache( final List<Fitting> newfittings ) {
		logger.info(">> [PilotDataController.addFittings2Cache]");
		for (Fitting fit : newfittings) {
			fittingsCache.put(fit.getFittingId(), fit);
		}
		logger.info("<< [PilotDataController.addFittings2Cache]");
	}
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
