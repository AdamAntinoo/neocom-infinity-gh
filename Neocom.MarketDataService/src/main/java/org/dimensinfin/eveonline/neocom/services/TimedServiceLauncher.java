//	PROJECT:      Neocom.Microservices (NEOC-MS)
//	AUTHORS:      Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:	SpringBoot-MS-Java 1.8.
//	DESCRIPTION:	This is the integration project for all the web server pieces. This is the launcher for
//								the SpringBoot+MicroServices+Angular unified web application.
package org.dimensinfin.eveonline.neocom.services;

import java.util.Vector;
import java.util.logging.Logger;

import org.dimensinfin.eveonline.neocom.connector.AppConnector;
import org.dimensinfin.eveonline.neocom.enums.EMarketSide;
import org.dimensinfin.eveonline.neocom.enums.ERequestClass;
import org.dimensinfin.eveonline.neocom.enums.ERequestState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

// - CLASS IMPLEMENTATION ...................................................................................
@Component
public class TimedServiceLauncher {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger			logger					= Logger.getLogger("TimedServiceLauncher");
	private static boolean		BLOCKED_STATUS	= false;
	private static int				LAUNCH_LIMIT		= 10;

	// - F I E L D - S E C T I O N ............................................................................
	@Autowired
	private MarketDataServer	marketDataService;
	private int								limit						= 0;

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public TimedServiceLauncher() {
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	@Scheduled(initialDelay = 10000, fixedDelay = 10000)
	public void onTime() {
		logger.info(">> [TimedServiceLauncher.onTime]");

		// STEP 01. Launch pending Data Requests
		// Get requests pending from the queue service.
		Vector<PendingRequestEntry> requests = AppConnector.getCacheConnector().getPendingRequests();
		logger.info("-- [TimedServiceLauncher.onTime]> Pending requests level: " + requests.size());
		//		synchronized (requests) {
		//			// Get the pending requests and order them by the priority.
		//			Collections.sort(requests, ComparatorFactory.createComparator(EComparatorField.REQUEST_PRIORITY));
		//		}

		// Process request by priority. Additions to queue are limited.
		limit = 0;
		for (PendingRequestEntry entry : requests)
			if (entry.state == ERequestState.PENDING) {
				// Filter only MARKETDATA requests.
				if (entry.reqClass == ERequestClass.MARKETDATA) {
					if ((limit >= LAUNCH_LIMIT) || (this.blockedMarket())) {
						logger.info("<< [TimedServiceLauncher.onTime]> Processing limite reached. Terminating run.");
						return;
					}
					logger.info("-- [TimedServiceLauncher.onTime]> Update Request Class [" + entry.reqClass + "]");
					entry.state = ERequestState.ON_PROGRESS;
					limit++;
					marketDataService.downloadMarketData(entry.getContent().intValue(), EMarketSide.BUYER);
					marketDataService.downloadMarketData(entry.getContent().intValue(), EMarketSide.SELLER);
				}
			}
		logger.info("<< [TimedServiceLauncher.onTime]");
	}

	//	@Bean
	//	public TaskExecutor taskExecutor() {
	//		return new SimpleAsyncTaskExecutor(); // Or use another one of your liking
	//	}

	private boolean blockedDownload() {
		//		// Read the flag values from the preferences.
		//		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(_context);
		//		boolean blockDownload = sharedPrefs.getBoolean(AppWideConstants.preference.PREF_BLOCKDOWNLOAD, false);
		return false;
	}

	private boolean blockedMarket() {
		//		// Read the flag values from the preferences.
		//		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(_context);
		//		boolean blockDownload = sharedPrefs.getBoolean(AppWideConstants.preference.PREF_BLOCKMARKET, false);
		return false;
	}
}
// - UNUSED CODE ............................................................................................
