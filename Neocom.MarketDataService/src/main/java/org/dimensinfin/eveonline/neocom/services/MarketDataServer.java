//	PROJECT:        POC (POC)
//	AUTHORS:        Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:		Java 1.7.
//	DESCRIPTION:		Projects for Proof Of Concept desings.
package org.dimensinfin.eveonline.neocom.services;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Logger;

import org.dimensinfin.eveonline.neocom.connector.ModelAppConnector;
import org.dimensinfin.eveonline.neocom.connector.NeoComMSConnector;
import org.dimensinfin.eveonline.neocom.constant.ModelWideConstants;
import org.dimensinfin.eveonline.neocom.enums.EMarketSide;
import org.dimensinfin.eveonline.neocom.market.EVEMarketDataParser;
import org.dimensinfin.eveonline.neocom.market.MarketDataEntry;
import org.dimensinfin.eveonline.neocom.market.MarketDataSet;
import org.dimensinfin.eveonline.neocom.market.TrackEntry;
import org.dimensinfin.eveonline.neocom.model.EveItem;
import org.dimensinfin.eveonline.neocom.model.EveLocation;
import org.joda.time.Instant;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

// - CLASS IMPLEMENTATION ...................................................................................
/**
 * This class interfaces the downloading eve online market data services and serves as the integration layer
 * to the different platforms. Based on the Android Service pattern it will implement a core class that will
 * be usable on any environment.
 * 
 * @author Adam Antinoo
 */
@Service
@CacheConfig(cacheNames = "MarketData")
public class MarketDataServer {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger														logger							= Logger.getLogger("MarketDataService");
	private static final String											CACHESTORE_FILENAME	= "./MarketDataService.store";
	public static Hashtable<Integer, MarketDataSet>	buyMarketDataCache	= new Hashtable<Integer, MarketDataSet>();
	public static Hashtable<Integer, MarketDataSet>	sellMarketDataCache	= new Hashtable<Integer, MarketDataSet>();

	@SuppressWarnings("unchecked")
	public synchronized static void readCacheFromStorage() {
		File modelStoreFile = new File(CACHESTORE_FILENAME);
		try {
			final BufferedInputStream buffer = new BufferedInputStream(new FileInputStream(modelStoreFile));
			final ObjectInputStream input = new ObjectInputStream(buffer);
			try {
				//				this.getStore().setApiKeys((HashMap<Integer, NeoComApiKey>) input.readObject());
				buyMarketDataCache = (Hashtable<Integer, MarketDataSet>) input.readObject();
				logger.info("-- [MarketDataServer.readCacheFromStorage]> Restored cache BUY: " + buyMarketDataCache.size()
						+ " entries.");
				sellMarketDataCache = (Hashtable<Integer, MarketDataSet>) input.readObject();
				logger.info("-- [MarketDataServer.readCacheFromStorage]> Restored cache SELL: " + sellMarketDataCache.size()
						+ " entries.");
			} finally {
				input.close();
				buffer.close();
			}
		} catch (final ClassNotFoundException ex) {
			logger.warning("W> [MarketDataServer.readCacheFromStorage]>ClassNotFoundException."); //$NON-NLS-1$
		} catch (final FileNotFoundException fnfe) {
			logger.warning("W> [MarketDataServer.readCacheFromStorage]>FileNotFoundException."); //$NON-NLS-1$
		} catch (final IOException ex) {
			logger.warning("W> [MarketDataServer.readCacheFromStorage]>IOException."); //$NON-NLS-1$
		} catch (final RuntimeException rex) {
			rex.printStackTrace();
		}
	}

	public synchronized static void writeCacheToStorage() {
		File modelStoreFile = new File(CACHESTORE_FILENAME);
		try {
			final BufferedOutputStream buffer = new BufferedOutputStream(new FileOutputStream(modelStoreFile));
			final ObjectOutput output = new ObjectOutputStream(buffer);
			try {
				output.writeObject(buyMarketDataCache);
				logger.info(
						"-- [MarketDataServer.writeCacheToStorage]> Wrote cache BUY: " + buyMarketDataCache.size() + " entries.");
				output.writeObject(sellMarketDataCache);
				logger.info(
						"-- [MarketDataServer.writeCacheToStorage]> Wrote cache SELL: " + sellMarketDataCache.size() + " entries.");
			} finally {
				output.flush();
				output.close();
				buffer.close();
			}
		} catch (final FileNotFoundException fnfe) {
			logger.warning("W> [MarketDataServer.writeCacheToStorage]>FileNotFoundException."); //$NON-NLS-1$
		} catch (final IOException ex) {
			logger.warning("W> [MarketDataServer.writeCacheToStorage]>IOException."); //$NON-NLS-1$
		}
	}

	// - F I E L D - S E C T I O N ............................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................

	// - M E T H O D - S E C T I O N ..........................................................................
	@CacheEvict(allEntries = true)
	public void clearCache() {
	}

	/**
	 * This should represent the service entry point. It will be called by the common implementation. It should
	 * receive the localized key for the eve item and be called after the network availability has been checked
	 * to avoid calling the methods and falling back to other market providers. <br>
	 * The implementation depends on some eve core functions to access the CCP item database so this should be
	 * added to a library that has common access to all that functions (market and CCP database) at the same
	 * time.
	 */
	//	@HystrixCommand(fallbackMethod = "downloadMarketDataFallback")
	@Cacheable()
	public MarketDataSet downloadMarketData(final int localizer, EMarketSide side) {
		MarketDataServer.logger.info(">< [MarketDataService.downloadMarketData]");
		ModelAppConnector.getSingleton().startChrono();
		String itemName = "";
		try {
			// Locate the Eve Item name to be used on the market data search.
			final EveItem item = ModelAppConnector.getSingleton().getCCPDBConnector().searchItembyID(localizer);
			itemName = item.getName();
			Vector<TrackEntry> marketEntries = parseMarketDataEMD(itemName, side);
			Vector<MarketDataEntry> hubData = extractMarketData(marketEntries);
			MarketDataSet reference = new MarketDataSet(localizer, side);
			reference.setData(hubData);
			// Mark the result as a valid and cacheable entry.
			reference.setValid(true);
			// Add the data to the cache.
			Hashtable<Integer, MarketDataSet> cache = sellMarketDataCache;
			if (side == EMarketSide.BUYER) {
				cache = buyMarketDataCache;
			}
			// Update the timestamp of the data to be cached.
			reference.setTimestamp(Instant.now());
			cache.put(localizer, reference);
			return reference;
		} catch (SAXException saxe) {
			logger.severe(
					"E [MarketDataService.downloadMarketData]> Parsing exception while downloading market data for module ["
							+ itemName + "]. " + saxe.getMessage());
			return new MarketDataSet(localizer, side);
		} catch (IOException ioe) {
			ioe.printStackTrace();
			logger.severe(
					"E [MarketDataService.downloadMarketData]> Error parsing the market information. " + ioe.getMessage());
			return new MarketDataSet(localizer, side);
		} finally {
			logger.info("~~ [MarketDataService.downloadMarketData]> Time lapse for Download MarketData " + localizer + " - "
					+ ModelAppConnector.getSingleton().timeLapse());
		}
	}

	public MarketDataSet downloadMarketDataFallback(final int localizer, EMarketSide side) {
		return new MarketDataSet(localizer, side);
	}

	public MarketDataSet marketDataServiceEntryPoint(final int localizer, EMarketSide side) {
		logger.info(
				">< [MarketDataServer.marketDataServiceEntryPoint]>localizer: " + localizer + " side: " + side.toString());
		// Cache interception performed by EHCache. If we reach this point that means we have not cached the data.
		Hashtable<Integer, MarketDataSet> cache = sellMarketDataCache;
		if (side == EMarketSide.BUYER) {
			cache = buyMarketDataCache;
		}
		MarketDataSet entry = cache.get(localizer);
		if (null == entry) {
			logger.info("-- [MarketDataServer.marketDataServiceEntryPoint]>[MISS] localizer: " + localizer
					+ " not cached. Posting download request");
			// Post request and return the data placeholder.
			ModelAppConnector.getSingleton().getCacheConnector().addMarketDataRequest(localizer);
			return new MarketDataSet(localizer, side);
		} else {
			logger.info("-- [MarketDataServer.marketDataServiceEntryPoint]>[HIT] localizer: " + localizer
					+ " Using cached information.");
			// Check if the cached data is staled. If we detect taht we then post an update of the data.
			if (NeoComMSConnector.getSingleton().checkExpiration(entry.getTimestamp(), ModelWideConstants.HOURS24)) {
				logger.info("-- [MarketDataServer.marketDataServiceEntryPoint]> Cached data stale. Posting update");
				// Post request and return the data placeholder.
				ModelAppConnector.getSingleton().getCacheConnector().addMarketDataRequest(localizer);
			}
			return entry;
		}
	}

	/**
	 * Check if the requested data is on the cache. Once the Spring cache is active we should not receive calls
	 * to this place. If the data is not at the cache, port and update event and return the fail data message.
	 * 
	 * @param localizer
	 * @param itemName
	 * @param side
	 * @return
	 */
	public MarketDataSet marketDataServiceEntryPoint(final int localizer, String itemName, EMarketSide side) {
		logger.info(
				">> [MarketDataServer.marketDataServiceEntryPoint]> localizer: " + localizer + " side: " + side.toString());
		// Cache interception performed by EHCache. If we reach this point that means we have not cached the data.
		Hashtable<Integer, MarketDataSet> cache = sellMarketDataCache;
		if (side == EMarketSide.BUYER) {
			cache = buyMarketDataCache;
		}
		MarketDataSet entry = cache.get(localizer);
		if (null == entry) {
			// Post request and return the data placeholder.
			ModelAppConnector.getSingleton().getCacheConnector().addMarketDataRequest(localizer);
			return new MarketDataSet(localizer, side);
		} else
			return entry;
	}

	/**
	 * Converts the raw TrakEntry structures into aggregated data by location and system. This has a new
	 * implementation that will use real location data for the system to better classify and store the market
	 * data information. It will also remove the current limit on the selected market hubs and will aggregate
	 * all the systems found into the highsec and other sec categories.
	 * 
	 * @param item
	 * @param entries
	 * @return
	 * @return
	 */
	private Vector<MarketDataEntry> extractMarketData(final Vector<TrackEntry> entries) {
		final Hashtable<String, MarketDataEntry> stations = new Hashtable<String, MarketDataEntry>();
		final Vector<String> stationList = getMarketHubs();
		final Iterator<TrackEntry> meit = entries.iterator();
		while (meit.hasNext()) {
			final TrackEntry entry = meit.next();
			// Filtering for only preferred market hubs.
			if (filterStations(entry, stationList)) {
				// Start searching for more records to sum all entries with the same or a close price to get
				// a better understanding of the market depth. That information is not to relevant so make a
				// best try.
				int stationQty = entry.getQty();
				final String stationName = entry.getStationName();
				final double stationPrice = entry.getPrice();
				while (meit.hasNext()) {
					final TrackEntry searchEntry = meit.next();
					// Check that station and prices are the same or price is inside margin.
					if (searchEntry.getStationName().equals(stationName)) {
						if ((stationPrice >= (searchEntry.getPrice() * 0.99))
								&& (stationPrice <= (searchEntry.getPrice() * 1.01))) {
							stationQty += searchEntry.getQty();
						} else {
							break;
						}
					} else {
						break;
					}
				}
				// Convert to standard location.
				final EveLocation entryLocation = generateLocation(stationName);
				final MarketDataEntry data = new MarketDataEntry(entryLocation);
				data.setQty(stationQty);
				data.setPrice(stationPrice);
				stations.put(stationName, data);
				stationList.remove(entry.getStationName());
			}
		}
		return new Vector<MarketDataEntry>(stations.values());
	}

	private boolean filterStations(final TrackEntry entry, final Vector<String> stationList) {
		final Iterator<String> slit = stationList.iterator();
		while (slit.hasNext()) {
			final String stationNameMatch = slit.next();
			final String station = entry.getStationName();
			if (station.contains(stationNameMatch)) return true;
		}
		return false;
	}

	private EveLocation generateLocation(String hubName) {
		// Extract system name from the station information.
		final int pos = hubName.indexOf(" ");
		final String hubSecurity = hubName.substring(0, pos);
		// Divide the name into region-system
		hubName = hubName.substring(pos + 1, hubName.length());
		final String[] parts = hubName.split(" - ");
		final String hubSystem = parts[1].trim();
		final String hubRegion = parts[0].trim();

		// Search for the system on the list of locations.
		return ModelAppConnector.getSingleton().getCCPDBConnector().searchLocationBySystem(hubSystem);
	}

	private Vector<String> getMarketHubs() {
		final Vector<String> stationList = new Vector<String>();
		//		stationList.add("0.8 Tash-Murkon - Tash-Murkon Prime");
		stationList.add("1.0 Domain - Amarr");
		//		stationList.add("1.0 Domain - Sarum Prime");
		//		stationList.add("0.8 Devoid - Hati");
		//		stationList.add("0.6 Devoid - Esescama");
		//		stationList.add("0.8 Heimatar - Odatrik");
		stationList.add("0.9 Heimatar - Rens");
		//		stationList.add("0.8 Heimatar - Frarn");
		//		stationList.add("0.9 Heimatar - Lustrevik");
		//		stationList.add("0.9 Heimatar - Eystur");
		stationList.add("0.5 Metropolis - Hek");
		//		stationList.add("0.5 Sinq Laison - Deltole");
		//		stationList.add("0.5 Sinq Laison - Aufay");
		//		stationList.add("0.9 Sinq Laison - Dodixie");
		//		stationList.add("0.9 Essence - Renyn");
		//		stationList.add("0.7 Kador - Romi");
		//		stationList.add("0.8 The Citadel - Kaaputenen");
		//		stationList.add("1.0 The Forge - Urlen");
		stationList.add("1.0 The Forge - Perimeter");
		stationList.add("0.9 The Forge - Jita");
		return stationList;
	}

	/**
	 * Get the eve-marketdata link for a requested module and market side.
	 * 
	 * @param moduleName
	 *          The module name to be used on the link.
	 * @param opType
	 *          if the set is from sell or buy orders.
	 * @return the URL to access the HTML page with the data.
	 */
	private String getModuleLink(final String moduleName, final String opType) {
		// Adjust the module name to a URL suitable name.
		String name = moduleName.replace(" ", "+");
		return "http://eve-marketdata.com/price_check.php?type=" + opType.toLowerCase() + "&region_id=-1&type_name_header="
				+ name;
	}

	//	/**
	//	 * New version that downloads the information from eve-central in json format.
	//	 */
	//	private Vector<TrackEntry> parseMarketDataEC(final int itemid, final EMarketSide opType) {
	//		logger.info(">> AndroidStorageConnector.parseMarketData");
	//		Vector<TrackEntry> marketEntries = new Vector<TrackEntry>();
	//		// try {
	//		// Making a request to url and getting response
	//		String jsonStr = this.readJsonData(itemid);
	//		try {
	//			JsonElement jelement = new JsonParser().parse(jsonStr);
	//			JsonArray jsonObj = jelement.getAsJsonArray();
	//			JsonObject part1 = jsonObj.getAsJsonObject(0);
	//			// Get the three blocks.
	//			JsonObject buy = part1.getAsJsonObject("buy");
	//			JsonObject all = part1.getAsJsonObject("all");
	//			JsonObject sell = part1.getAsJsonObject("sell");
	//			JsonObject target = null;
	//			if (opType == EMarketSide.SELLER) {
	//				target = sell;
	//			} else {
	//				target = buy;
	//			}
	//			double price = 0.0;
	//			if (opType == EMarketSide.SELLER) {
	//				price = target.getDouble("min");
	//			} else {
	//				price = target.getDouble("max");
	//			}
	//			long volume = target.getLong("volume");
	//			TrackEntry entry = new TrackEntry();
	//			entry.setPrice(Double.valueOf(price).toString());
	//			entry.setQty(Long.valueOf(volume).toString());
	//			entry.setStationName("0.9 The Forge - Jita");
	//			marketEntries.add(entry);
	//		} catch (JSONException e) {
	//			e.printStackTrace();
	//		}
	//		// } catch (SAXException saxe) {
	//		// logger.severe("E> Parsing exception while downloading market data for module [" + itemName + "]. "
	//		// + saxe.getMessage());
	//		// } catch (IOException ioe) {
	//		// // TODO Auto-generated catch block
	//		// ioe.printStackTrace();
	//		// logger.severe("E> Error parsing the market information. " + ioe.getMessage());
	//		// }
	//		logger.info("<< AndroidStorageConnector.parseMarketData. marketEntries [" + marketEntries.size() + "]");
	//		return marketEntries;
	//	}

	/**
	 * Processing of market data from eve-marketdata.com.
	 * 
	 * @param itemName
	 * @param opType
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 */
	private Vector<TrackEntry> parseMarketDataEMD(final String itemName, final EMarketSide opType)
			throws SAXException, IOException {
		MarketDataServer.logger.info(">> [MarketDataService.parseMarketData]");
		Vector<TrackEntry> marketEntries = new Vector<TrackEntry>();
		//		try {
		org.xml.sax.XMLReader reader;
		reader = org.xml.sax.helpers.XMLReaderFactory.createXMLReader("org.htmlparser.sax.XMLReader");

		// Create out specific parser for this type of content.
		EVEMarketDataParser content = new EVEMarketDataParser();
		reader.setContentHandler(content);
		reader.setErrorHandler(content);
		String URLDestination = null;
		if (opType == EMarketSide.SELLER) {
			URLDestination = getModuleLink(itemName, "SELL");
		}
		if (opType == EMarketSide.BUYER) {
			URLDestination = getModuleLink(itemName, "BUY");
		}
		if (null != URLDestination) {
			reader.parse(URLDestination);
			marketEntries = content.getEntries();
		}
		//		} catch (SAXException saxe) {
		//			MarketDataService.logger.severe(
		//					"E> Parsing exception while downloading market data for module [" + itemName + "]. " + saxe.getMessage());
		//		} catch (IOException ioe) {
		//			// TODO Auto-generated catch block
		//			ioe.printStackTrace();
		//			MarketDataService.logger.severe("E> Error parsing the market information. " + ioe.getMessage());
		//		}
		MarketDataServer.logger
				.info("<< [MarketDataService.parseMarketData]> marketEntries [" + marketEntries.size() + "]");
		return marketEntries;
	}

	private String readJsonData(final int typeid) {
		StringBuffer data = new StringBuffer();
		try {
			String str = "";
			URL url = new URL("http://api.eve-central.com/api/marketstat/json?typeid=" + typeid + "&regionlimit=10000002");
			URLConnection urlConnection = url.openConnection();
			InputStream is = new BufferedInputStream(urlConnection.getInputStream());
			// InputStream is = AppConnector.getStorageConnector().accessNetworkResource(
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			if (is != null) {
				while ((str = reader.readLine()) != null) {
					data.append(str);
				}
			}
			is.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return data.toString();
	}

}

// - UNUSED CODE ............................................................................................
