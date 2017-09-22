//	PROJECT:      Neocom.Microservices (NEOC-MS)
//	AUTHORS:      Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:	SpringBoot-MS-Java 1.8.
//	DESCRIPTION:	This is the integration project for all the web server pieces. This is the launcher for
//								the SpringBoot+MicroServices+Angular unified web application.
package org.dimensinfin.eveonline.neocom.connector;

import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Logger;

import org.dimensinfin.eveonline.neocom.core.CoreCacheConnector;
import org.dimensinfin.eveonline.neocom.enums.EMarketSide;
import org.dimensinfin.eveonline.neocom.market.MarketDataSet;
import org.dimensinfin.eveonline.neocom.services.MarketDataService;

// - CLASS IMPLEMENTATION ...................................................................................
public class MicroServicesCacheConnector extends CoreCacheConnector implements ICacheConnector {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger	logger				= Logger.getLogger("MicroServicesCacheConnector");

	// - F I E L D - S E C T I O N ............................................................................
	private int						topCounter		= 0;
	private int						marketCounter	= 0;

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public MicroServicesCacheConnector() {
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	@Override
	public int decrementMarketCounter() {
		this.marketCounter--;
		if (marketCounter < 0) {
			marketCounter = 0;
		}
		return marketCounter;
	}

	@Override
	public int decrementTopCounter() {
		topCounter--;
		if (topCounter < 0) {
			topCounter = 0;
		}
		return topCounter;
	}

	@Override
	public int incrementMarketCounter() {
		return this.marketCounter++;
	}

	@Override
	public int incrementTopCounter() {
		return this.topCounter++;
	}

	/**
	 * This implementation overrides the main implementation valid for Android and replaces it by a network
	 * access to another Micro Service specialized on the serving of Market Data. If that Micro Service fails of
	 * returns a non cached response we can use the Hystrix pattern to replace it by an alternate response that
	 * is structurally valid but with stale data awaiting for a new data update from the Service that will
	 * update the cache.
	 * 
	 * @param itemID
	 *          item id code of the item assigned to this market request.
	 * @param side
	 *          differentiates if we like to BUY or SELL the item.
	 * @return the cached data or an empty locator ready to receive downloaded data.
	 */
	//	@Cacheable("MarketData")
	@Override
	public MarketDataSet searchMarketData(final int itemID, final EMarketSide side) {
		logger.info(">> [MicroServicesCacheConnector.searchMarketData]> itemid: " + itemID + " side: " + side.name());
		// Search on the cache. By default load the SELLER as If I am buying the item.
		HashMap<Integer, MarketDataSet> cache = sellMarketDataCache;
		if (side == EMarketSide.BUYER) {
			cache = buyMarketDataCache;
		}
		MarketDataSet entry = cache.get(itemID);
		if (null == entry) {
			// We do not have the data on the local cache. Call the service provider to get a fresh copy of the data.

			// Download and process the market data by posting a Market Update Request.
			boolean doInmediately = false;
			if (doInmediately) {
				// Download and process the market data right now.
				Vector<MarketDataSet> entries = MarketDataService.marketDataServiceEntryPoint(itemID);
				for (MarketDataSet data : entries) {
					if (data.getSide() == EMarketSide.BUYER) {
						buyMarketDataCache.put(itemID, entry);
						if (side == data.getSide()) {
							entry = data;
						}
					}
					if (data.getSide() == EMarketSide.SELLER) {
						sellMarketDataCache.put(itemID, entry);
						if (side == data.getSide()) {
							entry = data;
						}
					}
				}
			} else {
				// Post the download of the market data to the background service.
				this.addMarketDataRequest(itemID);
			}
		}
		CoreCacheConnector.logger.info("<< [SpringDatabaseConnector.searchMarketData]");
		return entry;
	}

}

// - UNUSED CODE ............................................................................................
