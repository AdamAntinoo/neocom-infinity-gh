//  PROJECT:     Neocom.Microservices (NEOC-MS)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Java 1.8 / SpringBoot-1.3.5 / Angular 5.0
//  DESCRIPTION: This is the SpringBoot MicroServices module to run the backend services to complete the web
//               application based on Angular+SB. This is the web version for the NeoCom Android native
//               application. Most of the source code is common to both platforms and this module includes
//               the source for the specific functionality for the backend services.
package org.dimensinfin.eveonline.neocom.services;

import org.dimensinfin.eveonline.neocom.constant.ModelWideConstants;
import org.dimensinfin.eveonline.neocom.core.NeoComException;
import org.dimensinfin.eveonline.neocom.datamngmt.interfaces.IMarketDataManagerService;
import org.dimensinfin.eveonline.neocom.datamngmt.manager.GlobalDataManager;
import org.dimensinfin.eveonline.neocom.enums.EIndustryGroup;
import org.dimensinfin.eveonline.neocom.enums.EMarketSide;
import org.dimensinfin.eveonline.neocom.market.MarketDataSet;
import org.dimensinfin.eveonline.neocom.model.ItemCategory;
import org.dimensinfin.eveonline.neocom.model.ItemGroup;
import org.dimensinfin.eveonline.neocom.model.NeoComNode;
import org.joda.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.batch.BatchProperties;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

// - CLASS IMPLEMENTATION ...................................................................................
public class MarketDataServer implements IMarketDataManagerService {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = LoggerFactory.getLogger(MarketDataServer.class);
	private static final int cpuCount = Runtime.getRuntime().availableProcessors();

	// - F I E L D - S E C T I O N ............................................................................
	private HashMap<Integer, MarketDataSet> buyMarketDataCache = new HashMap<Integer, MarketDataSet>(1000);
	private HashMap<Integer, MarketDataSet> sellMarketDataCache = new HashMap<Integer, MarketDataSet>(1000);
	private HashMap<Integer, Instant> expirationTimeMarketData = new HashMap<Integer, Instant>(1000);
	private final MarketDataJobDownloadManager downloadManager = new MarketDataJobDownloadManager(cpuCount);

	// - C O N S T R U C T O R - S E C T I O N ................................................................

	// - M E T H O D - S E C T I O N ..........................................................................

	/**
	 * Does the service initialization and start the service. This mean to read back from storage the latest saved cache and then
	 * start the background services to download more Market data.
	 *
	 * @return
	 */
	public MarketDataServer start() {
		logger.info(">> [MarketDataServer.start]");
		readMarketDataCacheFromStorage();
		downloadManager.clear();
		logger.info("<< [MarketDataServer.start]");
		return this;
	}

	public synchronized void readMarketDataCacheFromStorage() {
		File modelStoreFile = new File(getCacheStoreName());
		try {
			final BufferedInputStream buffer = new BufferedInputStream(new FileInputStream(modelStoreFile));
			final ObjectInputStream input = new ObjectInputStream(buffer);
			try {
				//				this.getStore().setApiKeys((HashMap<Integer, NeoComApiKey>) input.readObject());
				buyMarketDataCache = (HashMap<Integer, MarketDataSet>) input.readObject();
				logger.info("-- [MarketDataServer.readMarketDataCacheFromStorage]> Restored cache BUY: " + buyMarketDataCache.size()
						+ " entries.");
				sellMarketDataCache = (HashMap<Integer, MarketDataSet>) input.readObject();
				logger.info("-- [MarketDataServer.readMarketDataCacheFromStorage]> Restored cache SELL: " + sellMarketDataCache.size()
						+ " entries.");
			} finally {
				input.close();
				buffer.close();
			}
		} catch (final ClassNotFoundException ex) {
			logger.warn("W> [MarketDataServer.readMarketDataCacheFromStorage]>ClassNotFoundException."); //$NON-NLS-1$
		} catch (final FileNotFoundException fnfe) {
			logger.warn("W> [MarketDataServer.readMarketDataCacheFromStorage]>FileNotFoundException."); //$NON-NLS-1$
		} catch (final IOException ex) {
			logger.warn("W> [MarketDataServer.readMarketDataCacheFromStorage]>IOException."); //$NON-NLS-1$
		} catch (final RuntimeException rex) {
			rex.printStackTrace();
		}
	}

	public synchronized void writeMarketDataCacheToStorage() {
		File modelStoreFile = new File(getCacheStoreName());
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
			logger.warn("W> [MarketDataServer.writeCacheToStorage]>FileNotFoundException."); //$NON-NLS-1$
		} catch (final IOException ex) {
			logger.warn("W> [MarketDataServer.writeCacheToStorage]>IOException."); //$NON-NLS-1$
		}
	}

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
	 * @param itemID item id code of the item assigned to this market request.
	 * @param side   differentiates if we like to BUY or SELL the item.
	 * @return the cached data or an empty locator ready to receive downloaded data.
	 */
	public MarketDataSet searchMarketData( final int itemID, final EMarketSide side ) {
		logger.info(">> [MarketDataServer.searchMarketData]> ItemId: {}/{}.", itemID, side.name());
		try {
			// Search on the cache. By default load the SELLER as If I am buying the item.
			HashMap<Integer, MarketDataSet> cache = sellMarketDataCache;
			if (side == EMarketSide.BUYER) {
				cache = buyMarketDataCache;
			}
			MarketDataSet entry = cache.get(itemID);
			if (null == entry) {
				// The data is not on the cache and neither on the latest disk copy read at initialization. Post a request.
				entry = new MarketDataSet(itemID, side);
				downloadManager.addMarketDataRequest(itemID);
			} else {
				logger.info("-- [MarketDataServer.searchMarketData]> Cache hit on memory.");
				// Check again the expiration time. If expired request a refresh.
				final Instant expirationTime = expirationTimeMarketData.get(itemID);
				if (expirationTime.isBefore(Instant.now())) downloadManager.addMarketDataRequest(itemID);
			}
			return entry;
		} finally {
			logger.info("<< [MarketDataServer.searchMarketData]");
		}
	}

	/**
	 * Read the configured cache location for the Market data save/restore serialization file.
	 *
	 * @return
	 */
	private String getCacheStoreName() {
		return GlobalDataManager.getResourceString("R.cache.marketdata.cachepath")
				+ GlobalDataManager.getResourceString("R.cache.marketdata.cachename");
	}

	/**
	 * The main responsibility of this class is to have a unique list of update jobs. If every minute we check for
	 * data to update and that data is already scheduled but not completed we can found a second and third requests
	 * that will also have to be executes.
	 * So we need something between the launcher of updated and the executor that removed already registered
	 * updates and do not request them again.
	 * Using an specific executor for this task will isolate the run effect from other tasks but anyway it
	 * requires some way for the job to notify its state so it can clear the request after completed or remove it
	 * if the process fails or gets interrupted.
	 * With the use of utures we can track pending jobs and be sure the update mechanics are followed as
	 * requested.
	 */
	//- CLASS IMPLEMENTATION ...................................................................................
	public static class MarketDataJobDownloadManager {
		// - S T A T I C - S E C T I O N ..........................................................................
		//		private static final List<Job> jobList = new Vector();
		private static ExecutorService marketDataDownloadExecutor = null;
		public static final Hashtable<String, Future<?>> runningJobs = new Hashtable();

		// - F I E L D - S E C T I O N ............................................................................
		private final int threadSize;

		// - C O N S T R U C T O R - S E C T I O N ................................................................
		public MarketDataJobDownloadManager( final int threadSize ) {
			this.threadSize = threadSize;
			marketDataDownloadExecutor = Executors.newFixedThreadPool(threadSize);
		}

		// - M E T H O D - S E C T I O N ..........................................................................
		public void clear() {
			runningJobs.clear();
		}

		/**
		 * Submits the job to out private executor. Store the Future to control job duplicated and to check when the
		 * job completes. The job reference can be used a primary key to detect job duplicates and collision.
		 *
		 * @param newJob the job to update some information.
		 */
		public synchronized void addMarketDataRequest( final int typeId ) {
//			try {
//				// Search for the job to detect duplications
//				final String identifier = newJob.getReference();
//				final Future<?> hit = runningJobs.get(identifier);
//				if (null == hit) {
//					// New job. Launch it and store the reference.
//					logger.info("-- [UpdateJobManager.submit]> Launching job {}", newJob.getReference());
//					final Future<?> future = newJob.submit();
//					runningJobs.put(identifier, future);
//				} else {
//					// Check for job completed.
//					if (hit.isDone()) {
//						// The job with this same reference has completed. We can launch a new one.
//						final Future<?> future = newJob.submit();
//						runningJobs.put(identifier, future);
//					}
//				}
//			} catch (NeoComException neoe) {
//				neoe.printStackTrace();
//			}
//			// Count the running or pending jobs to update the ActionBar counter.
//			int counter = 0;
//			for (Future<?> future : runningJobs.values()) {
//				if (!future.isDone()) counter++;
//			}
//			NeoComModelStore.setJobCounter(counter);
		}
	}
}