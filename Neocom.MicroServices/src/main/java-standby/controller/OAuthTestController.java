//	PROJECT:      Neocom.Microservices (NEOC-MS)
//	AUTHORS:      Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:	SpringBoot-MS-Java 1.8.
//	DESCRIPTION:	This is the integration project for all the web server pieces. This is the launcher for
//								the SpringBoot+MicroServices+Angular unified web application.
package org.dimensinfin.eveonline.neocom.controller;

import java.util.logging.Logger;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

// - CLASS IMPLEMENTATION ...................................................................................
@RestController
public class OAuthTestController {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = Logger.getLogger("OAuthTestController");

	// - F I E L D - S E C T I O N ............................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public OAuthTestController () {
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	@CrossOrigin()
	@RequestMapping(value = "/oauth", method = RequestMethod.POST, produces = "application/json")
	public String oauth () {
		logger.info(">>>>>>>>>>>>>>>>>>>>NEW REQUEST: " + "/api/v1/loginlist");
		logger.info(">> [OAuthTestController.loginlistEntryPoint]");
		//		try {
		//			NeoComMSConnector.getSingleton().startChrono();
		//			// If we receive a force command we should clear data before executing the request.
		//			if (force != null) if (force.equalsIgnoreCase("true")) AppModelStore.getSingleton().clearLoginList();
		//			return AppModelStore.getSingleton().accessLoginList();
		//		} catch (RuntimeException rtex) {
		//			return new Hashtable<String, Login>();
		//		} finally {
		//			logger.info("<< [OAuthTestController.loginlistEntryPoint]>[TIMING] Processing Time: - "
		//					+ NeoComMSConnector.getSingleton().timeLapse());
		//		}
		return "data";
	}
}

// - UNUSED CODE ............................................................................................
