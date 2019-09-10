//	PROJECT:      Neocom.MarketDataService (NEOC-MKDS)
//	AUTHORS:      Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:	SpringBoot-MS-Java 1.8.
//	DESCRIPTION:	This project contains a MicroService specially dedicated to get and store the Market Data
//								information. I should be exposed on a new port and should be accesible to all the backend MS to consult 
//								Item Market Data.
package org.dimensinfin.eveonline.neocom.controller;

import java.util.logging.Logger;

import org.dimensinfin.eveonline.neocom.commands.MarketDataCommand;
import org.dimensinfin.eveonline.neocom.connector.ModelAppConnector;
import org.dimensinfin.eveonline.neocom.market.MarketDataSet;
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
	@RequestMapping(value = "/api/v1/marketdata/{itemid}/{side}", method = RequestMethod.GET, produces = "application/json")
	public MarketDataSet marketData(@PathVariable final String itemid, @PathVariable String side) {
		logger.info(">>>>>>>>>>>>>>>>>>>>NEW REQUEST: " + "/api/v1/marketdata/{" + itemid + "}/{" + side + "}");
		logger.info(">> [MarketDataController.marketData]>itemid: " + itemid);
		logger.info(">> [MarketDataController.marketData]>side: " + side);
		try {
			ModelAppConnector.getSingleton().startChrono();
			MarketDataCommand fallbackCommand = new MarketDataCommand();
			fallbackCommand.setItemId(itemid).setSide(side);
			return fallbackCommand.execute();
		} finally {
			logger.info("<< [MarketDataController.marketData]>[TIMING] Processing Time for [" + itemid + "] - "
					+ ModelAppConnector.getSingleton().timeLapse());
		}
	}
}

// - UNUSED CODE ............................................................................................
