//	PROJECT:      Neocom.Microservices (NEOC-MS)
//	AUTHORS:      Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:	SpringBoot-MS-Java 1.8.
//	DESCRIPTION:	This is the integration project for all the web server pieces. This is the launcher for
//								the SpringBoot+MicroServices+Angular unified web application.
package org.dimensinfin.eveonline.neocom.controller;

import java.util.Hashtable;
import java.util.logging.Logger;

import org.dimensinfin.eveonline.neocom.connector.AppConnector;
import org.dimensinfin.eveonline.neocom.connector.AppModelStore;
import org.dimensinfin.eveonline.neocom.model.Login;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

// - CLASS IMPLEMENTATION ...................................................................................
@RestController
public class LoginController {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = Logger.getLogger("LoginController");

	// - F I E L D - S E C T I O N ............................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public LoginController() {
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	@CrossOrigin()
	@RequestMapping(value = "/api/v1/loginlist", method = RequestMethod.GET, produces = "application/json")
	public Hashtable<String, Login> loginlistEntryPoint() {
		logger.info(">>>>>>>>>>>>>>>>>>>>NEW REQUEST: " + "/api/v1/loginlist");
		logger.info(">> [LoginController.loginlistEntryPoint]");
		try {
			AppConnector.startChrono();
			return AppModelStore.getSingleton().accessLoginList();
		} catch (RuntimeException rtex) {
			return new Hashtable<String, Login>();
		} finally {
			logger.info("<< [LoginController.loginlistEntryPoint]>[TIMING] Processing Time: - " + AppConnector.timeLapse());
		}
	}
}

// - UNUSED CODE ............................................................................................
