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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.dimensinfin.eveonline.neocom.core.NeoComRuntimeException;
import org.dimensinfin.eveonline.neocom.database.entity.Credential;
import org.dimensinfin.eveonline.neocom.database.entity.FittingRequest;
import org.dimensinfin.eveonline.neocom.database.entity.Property;
import org.dimensinfin.eveonline.neocom.datamngmt.GlobalDataManager;
import org.dimensinfin.eveonline.neocom.datamngmt.InfinityGlobalDataManager;
import org.dimensinfin.eveonline.neocom.exception.JsonExceptionInstance;
import org.dimensinfin.eveonline.neocom.exception.NeoComRegisteredException;
import org.dimensinfin.eveonline.neocom.industry.FacetedAssetContainer;
import org.dimensinfin.eveonline.neocom.industry.Fitting;
import org.dimensinfin.eveonline.neocom.industry.Resource;
import org.dimensinfin.eveonline.neocom.model.ANeoComEntity;
import org.dimensinfin.eveonline.neocom.model.EveItem;
import org.dimensinfin.eveonline.neocom.model.EveLocation;
import org.dimensinfin.eveonline.neocom.model.ManufactureResourcesRequest;
import org.dimensinfin.eveonline.neocom.model.NeoComAsset;
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
			} else throw new NeoComRuntimeException("Not access.");
		} catch (JsonProcessingException jspe) {
			return new JsonExceptionInstance(jspe).toJson();
		} catch (NeoComRegisteredException neore) {
			neore.printStackTrace();
			return InfinityGlobalDataManager.serializedException(neore);
		} catch (RuntimeException rtx) {
			logger.error("EX [ManufactureResourcesController.manufactureResourcesHullManufacture]> Unexpected Exception: {}", rtx.getMessage());
			rtx.printStackTrace();
			return InfinityGlobalDataManager.serializedException(rtx);
		} finally {
			logger.info("<< [ManufactureResourcesController.manufactureResourcesHullManufacture]");
		}
	}

	@CrossOrigin()
	@RequestMapping(value = "/api/v1/pilot/{identifier}/manufactureresources/structuremanufacture"
			, method = RequestMethod.GET, produces = "application/json")
	public String manufactureResourcesStructureManufacture( @RequestHeader(value = "xNeocom-Session-Locator", required = false) String sessionLocator
			, @PathVariable final Integer identifier ) {
		logger.info(">>>>>>>>>>>>>>>>>>>>NEW REQUEST: /api/v1/pilot/{}/manufactureresources/structuremanufacture"
				, identifier);
		logger.info(">> [ManufactureResourcesController.manufactureResourcesStructureManufacture]");
		logger.info(">> [ManufactureResourcesController.manufactureResourcesStructureManufacture]> sessionLocator: {}", sessionLocator);

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
				final List<ManufactureResourcesRequest> jobList = new ArrayList<>();
				// Search for all the blueprints that match to Structure Components.
				HashMap<String, Object> where = new HashMap<String, Object>();
				where.put("ownerId", session.getCredential().getAccountId());
				where.put("category", "Blueprint");
				where.put("groupName", "Construction Component Blueprints");
				final List<NeoComAsset> structureBlueprints = new InfinityGlobalDataManager().getNeocomDBHelper().getAssetDao()
						.queryForFieldValues(where);
				// Filter out only the Structure blueprints.
				for (NeoComAsset blueprint : structureBlueprints) {
					if (blueprint.getName().startsWith("Structure")) {
						// Generate the LOM for the blueprint and the number of copies.
						final List<Resource> lom = InfinityGlobalDataManager.searchListOfMaterials4Blueprint(blueprint.getTypeId());

						// Create the Job Request to define the target and the resources required.
						// TODO - Download the blueprints to et access to the number of runs.
						jobList.add(new ManufactureResourcesRequest(blueprint.getTypeId())
								.setNumberOfCopies(2)
								.setLOM(lom));
					}
				}

				final String contentsSerialized = NeoComMicroServiceApplication.jsonMapper.writeValueAsString(jobList);
				return contentsSerialized;
			} else throw new NeoComRuntimeException("Not access.");
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
			logger.info("<< [ManufactureResourcesController.manufactureResourcesStructureManufacture]");
		}
	}

	@CrossOrigin()
	@RequestMapping(value = "/api/v1/pilot/{identifier}/manufactureresources/resourcesavailable"
			, method = RequestMethod.GET, produces = "application/json")
	public String manufactureResourcesAvailable( @RequestHeader(value = "xNeocom-Session-Locator", required = false) String
			                                             sessionLocator
			, @PathVariable final Integer identifier ) {
		logger.info(">>>>>>>>>>>>>>>>>>>>NEW REQUEST: /api/v1/pilot/{}/manufactureresources/resourcesavailable"
				, identifier);
		logger.info(">> [ManufactureResourcesController.manufactureResourcesAvailable]");
		logger.info(">> [ManufactureResourcesController.manufactureResourcesAvailable]> sessionLocator: {}", sessionLocator);

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
				// Get the list of assets of Mineral type.
				HashMap<String, Object> where = new HashMap<String, Object>();
				where.put("ownerId", session.getCredential().getAccountId());
				where.put("category", "Material");
				where.put("groupName", "Mineral");
				final List<NeoComAsset> manufactureResources = new InfinityGlobalDataManager().getNeocomDBHelper().getAssetDao()
						.queryForFieldValues(where);

				// Get the list of assets of Ore type.
				where = new HashMap<String, Object>();
				where.put("ownerId", session.getCredential().getAccountId());
				where.put("category", "Asteroid");
				manufactureResources.addAll(new InfinityGlobalDataManager().getNeocomDBHelper().getAssetDao()
						.queryForFieldValues(where));

				// Classify them into System type Locations but only for Systems with REFINING role.
				where = new HashMap<String, Object>();
				where.put("ownerId", session.getCredential().getAccountId());
				where.put("propertyType", "LOCATIONROLE");
				where.put("stringValue", "REFINING");
				final List<Property> roles = new InfinityGlobalDataManager().getNeocomDBHelper().getPropertyDao()
						.queryForFieldValues(where);
				final List<Long> stations = new ArrayList<>();
				for (Property role : roles) {
					stations.add(Double.valueOf(role.getNumericValue()).longValue());
				}

				// Create the Processor where ot store and refine the resources and classify them into the final Locations.
				final MineralResourceProcessor processor = new MineralResourceProcessor();
				for (NeoComAsset resource : manufactureResources) {
					// Check the location against the list of valid locations.
					if (stations.contains(resource.getLocationId())) {
						// Add the resource to the processor for storage or for refining.
						processor.addResource(resource);
					}
				}

				final String contentsSerialized = NeoComMicroServiceApplication.jsonMapper.writeValueAsString(processor
						.getStorageLocations().values());
				return contentsSerialized;
			} else throw new NeoComRuntimeException("Not access.");
		} catch (JsonProcessingException jspe) {
			return new JsonExceptionInstance(jspe).toJson();
//		} catch (NeoComRegisteredException neore) {
//			neore.printStackTrace();
//			return InfinityGlobalDataManager.serializedException(neore);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return InfinityGlobalDataManager.serializedException(sqle);
		} catch (RuntimeException rtx) {
			logger.error("EX [ManufactureResourcesController.manufactureResourcesAvailable]> Unexpected Exception: {}", rtx.getMessage());
			rtx.printStackTrace();
			return InfinityGlobalDataManager.serializedException(rtx);
		} finally {
			logger.info("<< [ManufactureResourcesController.manufactureResourcesAvailable]");
		}
	}

	public static class MineralResourceProcessor {
		private Map<Long, FacetedAssetContainer<EveLocation>> _storageLocations = new HashMap<>();

		/**
		 * Add the resource depending on the type. For Mineral resources they are added automatically to the matching Location
		 * Storage. For Ore resources they are wrapped into a refining processor that is then added to the target location.
		 *
		 * @param resource
		 */
		public void addResource( final NeoComAsset resource ) {
			if (resource.getCategoryName().equalsIgnoreCase("Asteroid")) {
				// Wrap into a RefiningProcess before adding it to the location.
				RefiningProcess refiner = new RefiningProcess(resource);
				add2Location(refiner, resource.getLocation());
			} else {
				add2Location(resource, resource.getLocation());
			}
		}

		private void add2Location( final RefiningProcess refiner, final EveLocation location ) {
			FacetedAssetContainer<EveLocation> hit = _storageLocations.get(location.getSystemId());
			if (null == hit) {
				// Add this new location to the list.
				hit = new FacetedAssetContainer(location);
				_storageLocations.put(location.getSystemId(), hit);
			}
			hit.addResource(refiner);
		}

		private void add2Location( final NeoComAsset asset, final EveLocation location ) {
			FacetedAssetContainer<EveLocation> hit = _storageLocations.get(location.getSystemId());
			if (null == hit) {
				// Add this new location to the list.
				hit = new FacetedAssetContainer(location);
				_storageLocations.put(location.getSystemId(), hit);
			}
			hit.addResource(new Resource(asset.getTypeId(), asset.getQuantity()));
		}

		public Map<Long, FacetedAssetContainer<EveLocation>> getStorageLocations() {
			return _storageLocations;
		}

		public MineralResourceProcessor setStorageLocations( final Map<Long, FacetedAssetContainer<EveLocation>> _storageLocations ) {
			this._storageLocations = _storageLocations;
			return this;
		}
	}

	public static class RefiningProcess extends Resource {
		private RefiningFacility facility = null;

		public RefiningProcess( final int typeId ) {
			super(typeId);
		}

		public RefiningProcess( final int typeId, final int newQty ) {
			super(typeId, newQty);
		}

		public RefiningProcess( final NeoComAsset resource ) {
			super(resource.getTypeId(), resource.getQuantity());
			// Create the refining station where to do the job.
			this.facility = new RefiningFacility(resource.getLocation());
		}

		public RefiningFacility getFacility() {
			return facility;
		}

		public Map<Integer, Resource> refine() {
			Map<Integer, Resource> refineResults = new HashMap<>();
			// Perform the refining of the resource with the facility yield.
			final List<Resource> refineParameters = accessGlobal().getSDEDBHelper().refineOre(getTypeId());
			for (final Resource rc : refineParameters) {
				final double mineral = Math.floor(Math.floor(getQuantity() / rc.getStackSize())
						* (rc.getBaseQuantity() * facility.getYield()));
				refineResults.put(rc.getTypeId(), new Resource(rc.getTypeId(), Double.valueOf(mineral).intValue()));
			}
			return refineResults;
		}
	}
}

// - UNUSED CODE ............................................................................................
//[01]


final class RefiningFacility extends ANeoComEntity {
	private EveLocation location = null;
	private double yield = 0.6;

	public RefiningFacility( final EveLocation facilityLocation ) {
		this.location = facilityLocation;
		List<Property> yieldList = new ArrayList();
		try {
			// Get the refining yield percentage from the Properties or set the default.
			HashMap<String, Object> where = new HashMap<String, Object>();
//		where.put("ownerId", session.getCredential().getAccountId());
			where.put("propertyType", "LOCATIONPROPERTY");
			where.put("stringValue", "REFININGYIELD");
			where.put("targetId", this.location.getSystemId());
			yieldList = accessGlobal().getNeocomDBHelper().getPropertyDao()
					.queryForFieldValues(where);
		} catch (SQLException sqle) {
		}
		if (yieldList.size() > 0) {
			for (Property yieldValue : yieldList) {
				this.yield = Math.max(this.yield, yieldValue.getNumericValue());
			}
		}
	}

	public double getYield() {
		return this.yield;
	}
}