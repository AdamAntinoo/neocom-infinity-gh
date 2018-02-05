//	PROJECT:      Neocom.Microservices (NEOC-MS)
//	AUTHORS:      Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:	SpringBoot-MS-Java 1.8.
//	DESCRIPTION:	This is the integration project for all the web server pieces. This is the launcher for
//								the SpringBoot+MicroServices+Angular unified web application.
package org.dimensinfin.eveonline.neocom.controller;


import java.util.List;

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
	public LoginController () {
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
	public String credentialsEntryPoint (@RequestParam(value = "force", required = false) final String force) {
		logger.info(">>>>>>>>>>>>>>>>>>>>NEW REQUEST: " + "/api/v1/credentials");
		logger.info(">> [LoginController.credentialsEntryPoint]");
		final Chrono totalElapsed = new Chrono();
		try {
			// If we receive a force command we should clear data before executing the request.
			if ( force != null ) if ( force.equalsIgnoreCase("true") ) DataManagementModelStore.getSingleton().cleanModel();
			final List<Credential> credentials = DataManagementModelStore.accessCredentialList();
			// Serialize the credentials as the Angular UI requires.
			return GlobalDataManager.serializeCredentialList(credentials);
		} catch (RuntimeException rtex) {
			return new JsonExceptionInstance(rtex.getMessage()).toJson();
		} finally {
			logger.info("<< [LoginController.credentialsEntryPoint]> [TIMING] Processing Time: {}", totalElapsed.printElapsed(Chrono.ChonoOptions.SHOWMILLIS));
		}
	}

	public static class JsonExceptionInstance {
		private String errorMessage = "-NO MESSAGE-";

		public JsonExceptionInstance (final String message) {
			errorMessage = message;
		}

		public String toJson () {
			return new StringBuffer()
					.append("{").append('\n')
					.append(quote("jsonClass")).append(":").append(quote("JsonException")).append(",")
					.append(quote("message")).append(":").append(quote(errorMessage)).append(" ")
					.append("}")
					.toString();
		}

		private String quote (final String content) {
			return String.format("\"%s\"", content);
		}
	}
}

// - UNUSED CODE ............................................................................................
