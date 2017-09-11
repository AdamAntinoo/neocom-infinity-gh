//	PROJECT:      Neocom.Microservices (NEOC-MS)
//	AUTHORS:      Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:	SpringBoot-MS-Java 1.8.
//	DESCRIPTION:	This is the integration project for all the web server pieces. This is the launcher for
//								the SpringBoot+MicroServices+Angular unified web application.
package org.dimensinfin.eveonline.neocom.services;

import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;

import org.dimensinfin.eveonline.neocom.connector.AppConnector;
import org.dimensinfin.eveonline.neocom.core.ComparatorFactory;
import org.dimensinfin.eveonline.neocom.enums.EComparatorField;
import org.dimensinfin.eveonline.neocom.enums.EDataBlock;
import org.dimensinfin.eveonline.neocom.enums.ERequestClass;
import org.dimensinfin.eveonline.neocom.enums.ERequestState;
import org.dimensinfin.eveonline.neocom.model.NeoComCharacter;
import org.dimensinfin.eveonline.neocom.services.CharacterUpdaterService;
import org.dimensinfin.eveonline.neocom.services.MarketDataService;
import org.dimensinfin.eveonline.neocom.services.PendingRequestEntry;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

// - CLASS IMPLEMENTATION ...................................................................................
@Component
public class TimedServiceLauncher {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger		logger					= Logger.getLogger("TimedServiceLauncher");
	private static boolean	BLOCKED_STATUS	= false;
	private static int			LAUNCH_LIMIT		= 30;

	// - F I E L D - S E C T I O N ............................................................................
	private int							limit						= 0;

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public TimedServiceLauncher() {
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	public CommandLineRunner launchCharacterDataUpdate(final PendingRequestEntry entry) {
		logger.info(
				"-- [TimeTickReceiver.launchCharacterDataUpdate]> Character Update Request Class [" + entry.reqClass + "]");
		Number content = entry.getContent();
		entry.state = ERequestState.ON_PROGRESS;
		TaskExecutor executor = taskExecutor();
		return new CommandLineRunner() {
			public void run(String... args) throws Exception {
				executor.execute(new CharacterUpdaterService(content.longValue()));
			}
		};
	}

	public CommandLineRunner launchMarketUpdate(final PendingRequestEntry entry) {
		logger.info("-- [TimeTickReceiver.launchService Market]> Update Request Class [" + entry.reqClass + "]");
		Number content = entry.getContent();
		entry.state = ERequestState.ON_PROGRESS;
		AppConnector.getCacheConnector().incrementMarketCounter();
		limit++;
		TaskExecutor executor = taskExecutor();
		return new CommandLineRunner() {
			public void run(String... args) throws Exception {
				executor.execute(new MarketDataService(content.intValue()));
			}
		};
	}

	@Scheduled(fixedRate = 60000)
	public void onTime() {
		logger.info(">> [TimedServiceLauncher.onReceive]");
		//		// Run only if the network is active.
		//		if (!NeoComApp.checkNetworkAccess()) return;
		//		// Or if the service is disables by configuration.
		//		if (this.blockedDownload()) return;

		// STEP 01. Launch pending Data Requests
		// Get requests pending from the queue service.
		Vector<PendingRequestEntry> requests = AppConnector.getCacheConnector().getPendingRequests();
		synchronized (requests) {
			// Get the pending requests and order them by the priority.
			Collections.sort(requests, ComparatorFactory.createComparator(EComparatorField.REQUEST_PRIORITY));

			// Process request by priority. Additions to queue are limited.
			limit = 0;
			for (PendingRequestEntry entry : requests)
				if (entry.state == ERequestState.PENDING) {
					// Filter only MARKETDATA requests.
					if (entry.reqClass == ERequestClass.MARKETDATA) if (limit <= LAUNCH_LIMIT) if (this.blockedMarket()) {
						continue;
					} else {
						this.launchMarketUpdate(entry);
					}
					// Filter the rest of the character data to be updated
					if (entry.reqClass == ERequestClass.CHARACTERUPDATE) {
						this.launchCharacterDataUpdate(entry);
					}
					//					if (entry.reqClass == ERequestClass.CITADELUPDATE) {
					//						// Launch the update and remove the event from the queue.
					//						new UpdateCitadelsTask().execute();
					//						NeoComApp.getTheCacheConnector().clearPendingRequest(entry.getIdentifier());
					//					}
					//					if (entry.reqClass == ERequestClass.OUTPOSTUPDATE) {
					//						// Launch the update and remove the event from the queue.
					//						new UpdateOutpostsTask().execute();
					//						NeoComApp.getTheCacheConnector().clearPendingRequest(entry.getIdentifier());
					//					}
				}
		}

		// STEP 02. Check characters for pending structures to update.
		List<NeoComCharacter> characters = AppConnector.getModelStore().getActiveCharacters();
		for (NeoComCharacter eveChar : characters) {
			EDataBlock updateCode = eveChar.needsUpdate();
			if (updateCode != EDataBlock.READY) {
				logger.info("-- [TimeTickReceiver.onReceive] EDataBlock to update: " + eveChar.getName() + " - " + updateCode);
				AppConnector.getCacheConnector().addCharacterUpdateRequest(eveChar.getCharacterID());
			}
		}
		//		Activity activity = AppModelStore.getSingleton().getActivity();
		//		if (null != activity) {
		//			activity.runOnUiThread(new Runnable() {
		//				public void run() {
		//					NeoComApp.updateProgressSpinner();
		//				}
		//			});
		//		}
		//		logger.info("<< TimeTickReceiver.onReceive [" + requests.size() + " - " + AppConnector.getCacheConnector().getmarketCounter
		//				+ "/" + NeoComApp.topCounter + "]");

	}

	@Bean
	public TaskExecutor taskExecutor() {
		return new SimpleAsyncTaskExecutor(); // Or use another one of your liking
	}

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
