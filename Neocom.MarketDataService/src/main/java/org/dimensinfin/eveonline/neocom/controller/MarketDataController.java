//	PROJECT:      Neocom.MarketDataService (NEOC-MKDS)
//	AUTHORS:      Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:	SpringBoot-MS-Java 1.8.
//	DESCRIPTION:	This project contains a MicroService specially dedicated to get and store the Market Data
//								information. I should be exposed on a new port and should be accesible to all the backend MS to consult 
//								Item Market Data.
package org.dimensinfin.eveonline.neocom.controller;

import java.util.Vector;
import java.util.logging.Logger;

import org.dimensinfin.eveonline.neocom.planetary.ProcessingAction;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

// - CLASS IMPLEMENTATION ...................................................................................
@RestController
public class MarketDataController {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = Logger.getLogger("MarketDataController");

	// - F I E L D - S E C T I O N ............................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public MarketDataController() {
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	@CrossOrigin()
	@RequestMapping(value = "/api/v1/marketdata/{itemid}", method = RequestMethod.GET, produces = "application/json")
	public Vector<ProcessingAction> marketData(@PathVariable final String itemid) {
		logger.info(">>>>>>>>>>>>>>>>>>>>NEW REQUEST: " + "/api/v1/marketdata/{itemid}");
		logger.info(">> [PilotRoasterController.planetaryLocationOptimization]>itemid: " + itemid);
		//		final Timer.Context context = getResponses(appName).time();
		//		try {
		//			getRequests().mark();
		//			return fixedBookList;
		//		} finally {
		//			context.stop();
		//		}
		logger.info("<< [PilotRoasterController.planetaryLocationOptimization]>");
		return null;
	}
}

// - UNUSED CODE ............................................................................................
