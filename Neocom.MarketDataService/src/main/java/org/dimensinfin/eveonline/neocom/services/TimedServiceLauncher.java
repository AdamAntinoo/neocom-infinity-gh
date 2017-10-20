//	PROJECT:      Neocom.Microservices (NEOC-MS)
//	AUTHORS:      Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:	SpringBoot-MS-Java 1.8.
//	DESCRIPTION:	This is the integration project for all the web server pieces. This is the launcher for
//								the SpringBoot+MicroServices+Angular unified web application.
package org.dimensinfin.eveonline.neocom.services;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.logging.Logger;

import org.dimensinfin.eveonline.neocom.connector.ModelAppConnector;
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
	private static Logger			logger				= Logger.getLogger("TimedServiceLauncher");
	//	private static boolean		BLOCKED_STATUS	= false;
	private static int				LAUNCH_LIMIT	= 10;

	// - F I E L D - S E C T I O N ............................................................................
	@Autowired
	private MarketDataServer	marketDataService;
	private int								limit					= 0;

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public TimedServiceLauncher() {
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	/**
	 * Process some number of elements from the queue before sleeping for 5 seconds. During tghe processing the
	 * extraction retrieves completed elements that should be discarded.
	 */
	@Scheduled(initialDelay = 10000, fixedDelay = 5000)
	public void onTime() {
		// STEP 01. Launch pending Data Requests
		// Get requests pending from the queue service.
		PriorityBlockingQueue<PendingRequestEntry> requests = ModelAppConnector.getSingleton().getCacheConnector()
				.getPendingRequests();
		if (requests.size() > 0)
			logger.info(">> [TimedServiceLauncher.onTime]> Pending requests level: " + requests.size());
		limit = 0;
		if (this.blockedMarket()) {
			if (requests.size() > 0) logger.info("<< [TimedServiceLauncher.onTime]> Blocked the Market processing. Exiting.");
			return;
		}
		while (limit <= LAUNCH_LIMIT) {
			// Process request by priority. Additions to queue are limited.
			PendingRequestEntry entry = requests.poll();
			if (null == entry) {
				if (requests.size() > 0) logger.info("<< [TimedServiceLauncher.onTime]> Queue empty. Exiting.");
				return;
			}
			// Remove completed entries.
			if (entry.state == ERequestState.COMPLETED) continue;
			if (entry.state == ERequestState.PENDING) {
				// Filter only MARKETDATA requests.
				if (entry.reqClass == ERequestClass.MARKETDATA) {
					int localizer = entry.getContent().intValue();
					logger.info("-- [TimedServiceLauncher.onTime]> Update Request Class [" + entry.reqClass + "] - " + localizer);
					entry.state = ERequestState.ON_PROGRESS;
					limit++;
					marketDataService.downloadMarketData(localizer, EMarketSide.BUYER);
					marketDataService.downloadMarketData(localizer, EMarketSide.SELLER);
				}
			}
		}
		logger.info("<< [TimedServiceLauncher.onTime]> Terminating run.");
	}

	private boolean blockedMarket() {
		//		// Read the flag values from the preferences.
		//		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(_context);
		//		boolean blockDownload = sharedPrefs.getBoolean(AppWideConstants.preference.PREF_BLOCKMARKET, false);
		return false;
	}
}
// - UNUSED CODE ............................................................................................
