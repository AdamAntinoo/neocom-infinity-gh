//	PROJECT:      MS-PlanetaryOptimizer (MS-PO)
//	AUTHORS:      Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:    (c) 2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:	SpringBoot-MS-Java 1.8.
//	DESCRIPTION:	Initial step to have an Eve application based on the Android code. This version exports
//								a list of assets and also has access to the Eve Item CCP database to get some of the
//								asset items properties..
package org.dimensinfin.eveonline.neocom.microservices.controller;

import java.util.logging.Logger;

import org.dimensinfin.eveonline.neocom.connector.AppConnector;
import org.dimensinfin.eveonline.neocom.model.EveItem;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

// - CLASS IMPLEMENTATION ...................................................................................
@RestController
public class EveItemController {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = Logger.getLogger("PlanetaryOptimizerServiceApp");

	// - F I E L D - S E C T I O N ............................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................

	// - M E T H O D - S E C T I O N ..........................................................................
	/**
	 * Gets the Eve Database information for an eve item identified by it's identifier.
	 * 
	 * @param typeID
	 *          identifier of the type to search.
	 * @return json information of the type from the CCP item database.
	 */
	@CrossOrigin()
	@RequestMapping(value = "/api/v1/eveitem/{typeID}", method = RequestMethod.GET, produces = "application/json")
	public EveItem eveItem(@PathVariable final String typeID/* , @PathVariable final String debug */) {
		logger.info(">> [NeoComApplication.eveItem]");
		logger.info("-- [NeoComApplication.eveItem]> typeID: " + typeID);
		// Connect to the eve database and generate an output for the query related to the eve item received as parameter.
		EveItem item = AppConnector.getDBConnector().searchItembyID(Integer.parseInt(typeID));
		// Initialize the market data from start because this is a requirements on serialization.
		item.getHighestBuyerPrice();
		item.getLowestSellerPrice();
		logger.info("-- [NeoComApplication.eveItem]> [#" + item.getItemID() + "]" + item.getName());
		logger.info("<< [NeoComApplication.eveItem]");
		printCacheInfo();
		return item;
	}

	@SuppressWarnings("unused")
	private void printCacheInfo() {
		//		Cache cache = NeocomMicroServiceApplication.singleton.cacheManager.getCache("MarketData");
		int dummy = 1;
	}
}
// - UNUSED CODE ............................................................................................
