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
import org.dimensinfin.eveonline.neocom.NeoComMicroServiceApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.dimensinfin.core.util.Chrono;
import org.dimensinfin.eveonline.neocom.database.entity.Credential;
import org.dimensinfin.eveonline.neocom.datamngmt.manager.GlobalDataManager;
import org.dimensinfin.eveonline.neocom.storage.DataManagementModelStore;

// - CLASS IMPLEMENTATION ...................................................................................
@RestController
public class LoginController {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = LoggerFactory.getLogger("LoginController");

	// - F I E L D - S E C T I O N ............................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public LoginController() {
	}

	// - M E T H O D - S E C T I O N ..........................................................................
//	@CrossOrigin()
//	@RequestMapping(value = "/api/v1/loginlist", method = RequestMethod.GET, produces = "application/json")
//	public Hashtable<String, Login> loginlistEntryPoint (
//			@RequestParam(value = "force", required = false) final String force) {
//		logger.info(">>>>>>>>>>>>>>>>>>>>NEW REQUEST: " + "/api/v1/loginlist");
//		logger.info(">> [LoginController.loginlistEntryPoint]");
//		try {
//			NeoComMSConnector.getSingleton().startChrono();
//			// If we receive a force command we should clear data before executing the request.
//			if ( force != null ) if ( force.equalsIgnoreCase("true") ) DataManagementModelStore.getSingleton().clearLoginList();
//			return AppModelStore.getSingleton().accessLoginList();
//		} catch (RuntimeException rtex) {
//			return new Hashtable<String, Login>();
//		} finally {
//			logger.info("<< [LoginController.loginlistEntryPoint]>[TIMING] Processing Time: - "
//					+ NeoComMSConnector.getSingleton().timeLapse());
//		}
//	}

	@CrossOrigin()
	@RequestMapping(value = "/api/v1/credentials", method = RequestMethod.GET, produces = "application/json")
	public String credentialsEntryPoint( @RequestParam(value = "force", required = false) final String force ) {
		logger.info(">>>>>>>>>>>>>>>>>>>>NEW REQUEST: " + "/api/v1/credentials");
		logger.info(">> [LoginController.credentialsEntryPoint]");
		final Chrono totalElapsed = new Chrono();
		try {
			// If we receive a force command we should clear data before executing the request.
			if (force != null) if (force.equalsIgnoreCase("true")) DataManagementModelStore.getSingleton().cleanModel();
			final List<Credential> credentials = DataManagementModelStore.getSingleton().coalesceCredentialList();
//			credentials = DataManagementModelStore.accessCredentialList();
			// Serialize the credentials as the Angular UI requires.
			return serializeCredentialList(credentials);
		} catch (RuntimeException rtex) {
			return new JsonExceptionInstance(rtex.getMessage()).toJson();
		} finally {
			logger.info("<< [LoginController.credentialsEntryPoint]> [TIMING] Processing Time: {}", totalElapsed.printElapsed(Chrono.ChronoOptions.SHOWMILLIS));
		}
	}

	public static class JsonExceptionInstance {
		private String errorMessage = "-NO MESSAGE-";

		public JsonExceptionInstance( final String message ) {
			errorMessage = message;
		}

		public String toJson() {
			return new StringBuffer()
					.append("{").append('\n')
					.append(quote("jsonClass")).append(":").append(quote("JsonException")).append(",")
					.append(quote("message")).append(":").append(quote(errorMessage)).append(" ")
					.append("}")
					.toString();
		}

		private String quote( final String content ) {
			return String.format("\"%s\"", content);
		}
	}
	private String serializeCredentialList( final List<Credential> credentials ) {
		// Use my own serialization control to return the data to generate exactly what I want.
		String contentsSerialized = "[jsonClass: \"Exception\"," +
				"message: \"Unprocessed data. Possible JsonProcessingException exception.\"]";
		try {
			contentsSerialized = NeoComMicroServiceApplication.jsonMapper.writeValueAsString(credentials);
		} catch (JsonProcessingException jpe) {
			jpe.printStackTrace();
			contentsSerialized=new JsonExceptionInstance(jpe.getMessage()).toJson();
		}
		return contentsSerialized;
	}

}

// - UNUSED CODE ............................................................................................
