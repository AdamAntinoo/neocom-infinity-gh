//  PROJECT:     NeoCom.Microservices (NEOC.MS)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Java 1.8 / SpringBoot-1.3.5 / Angular 5.0
//  DESCRIPTION: This is the SpringBoot MicroServices module to run the backend services to complete the web
//               application based on Angular+SB. This is the web version for the NeoCom Android native
//               application. Most of the source code is common to both platforms and this module includes
//               the source for the specific functionality for the backend services.
package org.dimensinfin.eveonline.neocom.controller;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.dimensinfin.eveonline.neocom.NeoComMicroServiceApplication;
import org.dimensinfin.eveonline.neocom.datamngmt.InfinityGlobalDataManager;
import org.dimensinfin.eveonline.neocom.exception.JsonExceptionInstance;
import org.dimensinfin.eveonline.neocom.market.MarketDataEntry;
import org.dimensinfin.eveonline.neocom.domain.EveItem;

// - CLASS IMPLEMENTATION ...................................................................................
@RestController
public class EveItemController {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = LoggerFactory.getLogger("EveItemController");

	// - F I E L D - S E C T I O N ............................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................

	// - M E T H O D - S E C T I O N ..........................................................................

	/**
	 * Gets the Eve Database information for an eve item identified by it's identifier.
	 * @param typeId identifier of the type to search.
	 * @return json information of the type from the CCP item database.
	 */
	@CrossOrigin()
	@RequestMapping(value = "/api/v1/eveitem/{typeId}", method = RequestMethod.GET, produces = "application/json")
	public String eveItemV1( @PathVariable final Integer typeId ) {
		logger.info(">>>>>>>>>>>>>>>>>>>>NEW REQUEST: /api/v1/eveitem/{}", typeId);
		logger.info(">> [EveItemController.eveItem]");

		// --- C O N T R O L L E R   B O D Y
		EveItem item = null;
		try {
			// Connect to the eve database and generate an output for the query related to the eve item received as parameter.
			item = new InfinityGlobalDataManager().searchItem4Id(typeId);
			// Initialize the market data from start because this is a requirements on serialization.
			final MarketDataEntry high = item.getHighestBuyerPrice();
			item.getLowestSellerPrice();
			final String contentsSerialized = NeoComMicroServiceApplication.jsonMapper.writeValueAsString(item);
			return contentsSerialized;
		} catch ( JsonProcessingException jspe ) {
			return new JsonExceptionInstance(jspe).toJson();
		} catch ( RuntimeException rtx ) {
			logger.error("EX [EveItemController.eveItem]> Unexpected Exception: {}", rtx.getMessage());
			rtx.printStackTrace();
			return InfinityGlobalDataManager.serializedException(rtx);
		} catch ( ExecutionException ee ) {
			ee.printStackTrace();
			return InfinityGlobalDataManager.serializedException(ee);
		} catch ( InterruptedException ie ) {
			ie.printStackTrace();
			return InfinityGlobalDataManager.serializedException(ie);
		} finally {
			logger.info("-- [EveItemController.eveItem]> [#" + item.getTypeId() + "]" + item.getName());
			logger.info("<< [EveItemController.eveItem]");
		}
	}

	@CrossOrigin()
	@GetMapping("/api/v2/eveitem/{typeId}")
	public ResponseEntity<EveItem> eveItemV2( @PathVariable final Integer typeId ) {
		logger.info(">>>>>>>>>>>>>>>>>>>>NEW REQUEST: /api/v1/eveitem/{}", typeId);
		logger.info(">> [EveItemController.eveItem]");

		// --- C O N T R O L L E R   B O D Y
		EveItem item = null;
		try {
			// Connect to the eve database and generate an output for the query related to the eve item received as parameter.
			item = new InfinityGlobalDataManager().searchItem4Id(typeId);
			// Initialize the market data from start because this is a requirements on serialization.
			item.getHighestBuyerPrice();
			item.getLowestSellerPrice();
			return new ResponseEntity<EveItem>(item, HttpStatus.OK);
		} catch ( ExecutionException ee ) {
			ee.printStackTrace();
			return new ResponseEntity<EveItem>(HttpStatus.BAD_REQUEST);
		} catch ( InterruptedException ie ) {
			ie.printStackTrace();
			return new ResponseEntity<EveItem>(HttpStatus.BAD_REQUEST);
		} finally {
			logger.info("-- [EveItemController.eveItem]> [#" + item.getTypeId() + "]" + item.getName());
			logger.info("<< [EveItemController.eveItem]");
		}
	}
}
// - UNUSED CODE ............................................................................................
