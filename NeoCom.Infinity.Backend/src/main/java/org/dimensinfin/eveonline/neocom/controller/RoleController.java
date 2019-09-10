//  PROJECT:     NeoCom.Microservices (NEOC.MS)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Java 1.8 / SpringBoot-1.3.5 / Angular 5.0
//  DESCRIPTION: This is the SpringBoot MicroServices module to run the backend services to complete the web
//               application based on Angular+SB. This is the web version for the NeoCom Android native
//               application. Most of the source code is common to both platforms and this module includes
//               the source for the specific functionality for the backend services.
package org.dimensinfin.eveonline.neocom.controller;

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
import org.dimensinfin.eveonline.neocom.datamngmt.GlobalDataManager;
import org.dimensinfin.eveonline.neocom.entities.Credential;
import org.dimensinfin.eveonline.neocom.entities.Property;
import org.dimensinfin.eveonline.neocom.exception.NeoComRuntimeException;
import org.dimensinfin.eveonline.neocom.datamngmt.InfinityGlobalDataManager;
import org.dimensinfin.eveonline.neocom.exception.JsonExceptionInstance;
import org.dimensinfin.eveonline.neocom.exception.NeoComRegisteredException;
import org.dimensinfin.eveonline.neocom.model.PilotV2;

/**
 * This controller manages the Location role assignments and their operation. Location can have more than one role and all that
 * information is stored on the Properties table along other data that is not related to the Locations. The Roles are stored on
 * the Properties table as EPropertyTypes.LOCATIONROLE.
 *
 * @author Adam Antinoo
 */
// - CLASS IMPLEMENTATION ...................................................................................
@RestController
public class RoleController {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = LoggerFactory.getLogger("RoleController");

	// - F I E L D - S E C T I O N ............................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................

	// - M E T H O D - S E C T I O N ..........................................................................

	/**
	 * This entry point add a new role to a location by writing a new Property with the information. Because there is no database
	 * control for the assignment of roles to locations this role can already be attached to the location so a duplicated record
	 * can be generated. For this reason we should first check the roles already atached to a location through the use of other
	 * application methods.
	 *
	 * @param sessionLocator
	 * @param identifier
	 * @param locationIdentifier
	 * @param role
	 * @return
	 */
	@CrossOrigin()
	@RequestMapping(value = "/api/v1/pilot/{identifier}/locationrole/add/{locationIdentifier}/{role}"
			, method = RequestMethod.GET, produces = "application/json")
	public String locationAddRole( @RequestHeader(value = "xNeocom-Session-Locator", required = false) String sessionLocator
			, @PathVariable final Integer identifier
			, @PathVariable final Long locationIdentifier
			, @PathVariable final String role ) {
		logger.info(">>>>>>>>>>>>>>>>>>>>NEW REQUEST: /api/v1/pilot/{}/locationrole/add/{}/{}", identifier, locationIdentifier, role);
		logger.info(">> [RoleController.locationAddRole]");
		logger.info(">> [RoleController.locationAddRole]> sessionLocator: {}", sessionLocator);

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
				// Location Role information is attached to the Pilot. Get this pilot instance.
				final PilotV2 pilotv2 = InfinityGlobalDataManager.requestPilotV2(session.getCredential());
				final List<Property> roles = pilotv2.getLocationRoles();
				for (Property locrole : roles) {
					// Filter out all the roles not related to the target location. The identifier is on the numeric side of the Property.
					if (locationIdentifier == Double.valueOf(locrole.getNumericValue()).longValue()) {
						// Check if the role is already atached. If so we have terminated the processing.
						if (role.equalsIgnoreCase(locrole.getStringValue())) {
							final String contentsSerialized = NeoComMicroServiceApplication.jsonMapper.writeValueAsString(roles);
							return contentsSerialized;
						}
					}
				}
				// If we reach this point the role is not attached and we should add it.
				pilotv2.addLocationRole(new InfinityGlobalDataManager().searchLocation4Id(locationIdentifier), role);
				final String contentsSerialized = NeoComMicroServiceApplication.jsonMapper.writeValueAsString(pilotv2.getLocationRoles());
				return contentsSerialized;
			} else throw new NeoComRuntimeException("Not access.");
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

	@CrossOrigin()
	@RequestMapping(value = "/api/v1/pilot/{identifier}/locationrole/delete/{locationIdentifier}/{role}"
			, method = RequestMethod.GET, produces = "application/json")
	public String locationDeleteRole( @RequestHeader(value = "xNeocom-Session-Locator", required = false) String sessionLocator
			, @PathVariable final Integer identifier
			, @PathVariable final Long locationIdentifier
			, @PathVariable final String role ) {
		logger.info(">>>>>>>>>>>>>>>>>>>>NEW REQUEST: /api/v1/pilot/{}/locationrole/add/{}/{}", identifier, locationIdentifier, role);
		logger.info(">> [RoleController.locationDeleteRole]");
		logger.info(">> [RoleController.locationDeleteRole]> sessionLocator: {}", sessionLocator);

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
				// Location Role information is attached to the Pilot. Get this pilot instance.
				final PilotV2 pilotv2 = InfinityGlobalDataManager.requestPilotV2(session.getCredential());
				final List<Property> roles = pilotv2.getLocationRoles();

				// Search for the role to be deleted.
				for (Property locrole : roles) {
					// Filter out all the roles not related to the target location. The identifier is on the numeric side of the Property.
					if (locationIdentifier == Double.valueOf(locrole.getNumericValue()).longValue()) {
						// Check if the role is already atached. If so we have terminated the processing.
						if (role.equalsIgnoreCase(locrole.getStringValue())) {
							pilotv2.deleteRole(locrole);
							final String contentsSerialized = NeoComMicroServiceApplication.jsonMapper.writeValueAsString(pilotv2.getLocationRoles());
							return contentsSerialized;
						}
					}
				}
				// If we reach this point the role is not attached and we should add it.
				pilotv2.addLocationRole(new InfinityGlobalDataManager().searchLocation4Id(locationIdentifier), role);
				final String contentsSerialized = NeoComMicroServiceApplication.jsonMapper.writeValueAsString(pilotv2.getLocationRoles());
				return contentsSerialized;
			} else throw new NeoComRuntimeException("Not access.");
		} catch (JsonProcessingException jspe) {
			return new JsonExceptionInstance(jspe).toJson();
		} catch (NeoComRegisteredException neore) {
			neore.printStackTrace();
			return InfinityGlobalDataManager.serializedException(neore);
		} catch (RuntimeException rtx) {
			logger.error("EX [RoleController.locationDeleteRole]> Unexpected Exception: {}", rtx.getMessage());
			rtx.printStackTrace();
			return InfinityGlobalDataManager.serializedException(rtx);
		} finally {
			logger.info("<< [RoleController.locationDeleteRole]");
		}
	}
}

// - UNUSED CODE ............................................................................................
//[01]
