//	PROJECT:      Neocom.MarketDataService (NEOC-MKDS)
//	AUTHORS:      Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:	SpringBoot-MS-Java 1.8.
//	DESCRIPTION:	This project contains a MicroService specially dedicated to get and store the Market Data
//								information. I should be exposed on a new port and should be accesible to all the backend MS to consult 
//								Item Market Data.
package org.dimensinfin.eveonline.neocom.services;

import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Logger;

import org.dimensinfin.evemarket.model.TrackEntry;
import org.dimensinfin.eveonline.neocom.connector.AppConnector;
import org.dimensinfin.eveonline.neocom.enums.EMarketSide;
import org.dimensinfin.eveonline.neocom.market.MarketDataEntry;
import org.dimensinfin.eveonline.neocom.market.MarketDataSet;
import org.dimensinfin.eveonline.neocom.model.EveItem;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

// - CLASS IMPLEMENTATION ...................................................................................
@EnableCircuitBreaker
@Service
public class MarketDataService {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = Logger.getLogger("MarketDataService");
	//	private static final ArrayList<String>	fixedBookList	= new ArrayList<String>();
	//
	//	static {
	//		fixedBookList.add("El señor de los anillos");
	//		fixedBookList.add("Ender el Genocida");
	//		fixedBookList.add("El quijote");
	//	}

	// - F I E L D - S E C T I O N ............................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	//	public MarketDataService() {
	//	}

	// - M E T H O D - S E C T I O N ..........................................................................
	@HystrixCommand(fallbackMethod = "reliable")
	public static Vector<MarketDataSet> marketDataServiceEntryPoint(final int localizer, EMarketSide side) {
		MarketDataService.logger.info(">> [MarketDataService.marketDataServiceEntryPoint]");
		// Create the result structure to be processed by the caller.
		//		Vector<MarketDataSet> results = new Vector<MarketDataSet>(2);
		//		final Integer localizer = (Integer) intent.getSerializableExtra(AppWideConstants.extras.EXTRA_MARKETDATA_LOCALIZER);
		// Be sure we have access to the network. Otherwise intercept the exceptions.
		//		if (NeoComApp.checkNetworkAccess()) {

		// Locate the Eve Item name to be used on the market data search.
		final EveItem item = AppConnector.getDBConnector().searchItembyID(localizer);
		Vector<TrackEntry> marketEntries = MarketDataService.parseMarketDataEMD(item.getName(), side);
		Vector<MarketDataEntry> hubData = MarketDataService.extractMarketData(marketEntries);
		MarketDataSet reference = new MarketDataSet(localizer, side);
		reference.setData(hubData);
		// Mark the result as a valid and cacheable entry.
		reference.setValid(true);

		if (marketEntries.size() > 1) {
			results.add(reference);
		}

		// Do the same for the other side.
		marketEntries = MarketDataService.parseMarketDataEMD(item.getName(), EMarketSide.BUYER);
		//		if (marketEntries.size() < 1) {
		//			marketEntries = AppConnector.getStorageConnector().parseMarketDataEC(item.getTypeID(), EMarketSide.BUYER);
		//		}
		hubData = MarketDataService.extractMarketData(marketEntries);
		reference = new MarketDataSet(localizer, EMarketSide.BUYER);
		reference.setData(hubData);
		if (marketEntries.size() > 1) {
			results.add(reference);
		}
		// Create a new method to access the cache for requests and change the state
		//			NeoComApp.getTheCacheConnector().clearPendingRequest(localizer.toString());
		//		}
		MarketDataService.logger.info("<< [MarketDataService.marketDataServiceEntryPoint]");
		return results;
	}

	public ArrayList<String> reliable() {
		ArrayList<String> result = new ArrayList<String>();
		result.add("Cloud Native Java (O'Reilly)");
		return result;
	}
}

// - UNUSED CODE ............................................................................................
