//  PROJECT:     NeoCom.Microservices (NEOC.MS)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Java 1.8 / SpringBoot-1.3.5 / Angular 5.0
//  DESCRIPTION: This is the SpringBoot MicroServices module to run the backend services to complete the web
//               application based on Angular+SB. This is the web version for the NeoCom Android native
//               application. Most of the source code is common to both platforms and this module includes
//               the source for the specific functionality for the backend services.
package org.dimensinfin.eveonline.neocom.datamngmt.manager;

import org.dimensinfin.eveonline.neocom.constant.ModelWideConstants;
import org.dimensinfin.eveonline.neocom.enums.EMarketSide;
import org.dimensinfin.eveonline.neocom.market.MarketDataSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;

/**
 * This static class centralizes all the functionality to access data. It will provide a consistent api to the rest
 * of the application and will hide the internals of how that data is obtained, managed and stored.
 * All thet now are direct database access or cache access or even Model list accesses will be hidden under an api
 * that will decide at any point from where to get the information and if there are more jobs to do to keep
 * that information available and up to date.
 * <p>
 * The initial release will start transferring the ModelFactory functionality.
 *
 * @author Adam Antinoo
 */

// - CLASS IMPLEMENTATION ...................................................................................
public class GlobalSpringDataManager extends GlobalDataManager {
	// --- P U B L I C   E N U M E R A T O R S

	// --- P R I V A T E   E N U M E R A T O R S

	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = LoggerFactory.getLogger(GlobalSpringDataManager.class);

	// --- C A C H E   S T O R A G E   S E C T I O N
	/**
	 * Search for this market data on the cache.
	 * The cache used for the search depends on the side parameter received on the call. All default prices are references to
	 * the cost of the price to be spent to buy the item.
	 * Cached items no longer are read from disk on demand. The disk cache is read at initialization and then on the new data is
	 * just stored on memory and write back down to disk sometimes. This way the cache is simplified to a single file and all the
	 * io related to disk is minimized.
	 * If the data is not located on the case call the market data downloader and processor to get a new copy
	 * and store it on the cache.
	 *
	 * @param itemID
	 *          item id code of the item assigned to this market request.
	 * @param side
	 *          differentiates if we like to BUY or SELL the item.
	 * @return the cached data or an empty locator ready to receive downloaded data.
	 */
	public MarketDataSet searchMarketData(final int itemID, final EMarketSide side) {
		logger.info(">> [GklobalDataManager.searchMarketData]> ItemId: {}/{}.",itemID,side.name());
		// Search on the cache. By default load the SELLER as If I am buying the item.
		HashMap<Integer, MarketDataSet> cache = sellMarketDataCache;
		if (side == EMarketSide.BUYER) {
			cache = buyMarketDataCache;
		}
		MarketDataSet entry = cache.get(itemID);
		if (null == entry) {
//			// Try to get the data from disk.
//			entry = NeoComAppConnector.getSingleton().getStorageConnector().readDiskMarketData(itemID, side);
//			if (null == entry) {
			// The data is not on the cache and neither on the latest disk copy read at initialization. Post a request.
			entry = new MarketDataSet(itemID, side);
			MarketDataDownloadJobMananger.addMarketDataRequest(itemID);
//				}
//			}
			cache.put(itemID, entry);
		} else {
			Log.i("EVEI", "-- MarketUpdaterService.searchMarketDataByID. Cache hit on memory.");
			// Check again the location. If is the default then request a new
			// update and remove it from the cache.
			long lid = entry.getBestMarket().getLocation().getID();
			if (lid < 0) {
				NeoComAppConnector.getSingleton().getCacheConnector().addMarketDataRequest(itemID);
				cache.put(itemID, null);
			}
		}
		// Check entry timestamp before return. Post an update if old.
		// if (side.equalsIgnoreCase(ModelWideConstants.marketSide.CALCULATE))
		// return entry;
		// else {
		if (NeoComAppConnector.getSingleton().checkExpiration(entry.getTS().getMillis(), ModelWideConstants.HOURS24))
			if (true) {
				NeoComAppConnector.getSingleton().getCacheConnector().addMarketDataRequest(itemID);
			}
		return entry;
		// }
	}
}
