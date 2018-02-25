//  PROJECT:     NeoCom.Microservices (NEOC.MS)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Java 1.8 / SpringBoot-1.3.5 / Angular 5.0
//  DESCRIPTION: This is the SpringBoot MicroServices module to run the backend services to complete the web
//               application based on Angular+SB. This is the web version for the NeoCom Android native
//               application. Most of the source code is common to both platforms and this module includes
//               the source for the specific functionality for the backend services.
package org.dimensinfin.eveonline.neocom.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.dimensinfin.eveonline.neocom.database.entity.Credential;
import org.dimensinfin.eveonline.neocom.database.entity.TimeStamp;
import org.dimensinfin.eveonline.neocom.datamngmt.manager.GlobalDataManager;
import org.dimensinfin.eveonline.neocom.storage.DataManagementModelStore;

/**
 * @author Adam Antinoo
 */
// - CLASS IMPLEMENTATION ...................................................................................
public class TimedUpdater {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = LoggerFactory.getLogger("TimedUpdater");

	// - F I E L D - S E C T I O N ............................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public TimedUpdater() {
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	public void timeTick() {
		logger.info(">> [TimedUpdater.timeTick]");
//			// STEP 01. Launch pending Data Requests
//			// Get requests pending from the queue service.
//			Vector<PendingRequestEntry> requests = NeoComApp.getSingletonApp().getCacheConnector().getAndroidPendingRequests();
//			if ( requests.size() > 0 ) logger.info("-- [TimeTickReceiver.onReceive]> Pending requests: {}",
//					requests.size());
//			synchronized (requests) {
//				// Process request by priority. Additions to queue are limited.
//				limit = 0;
//				for (PendingRequestEntry entry : requests)
//					if ( entry.state == ERequestState.PENDING ) {
//						// Filter only MARKETDATA requests.
//						if ( entry.reqClass == ERequestClass.MARKETDATA ) {
//							if ( this.blockedMarket() ) continue;
//							if ( limit <= TimeTickReceiver.LAUNCH_LIMIT ) {
//								this.launchMarketUpdate(entry);
//							}
//						}
//						// Filter the rest of the character data to be updated
//						if ( entry.reqClass == ERequestClass.CHARACTERUPDATE ) {
//							this.launchCharacterDataUpdate(entry);
//						}
//						if ( entry.reqClass == ERequestClass.CITADELUPDATE ) {
//							// Launch the update and remove the event from the queue.
//							new UpdateCitadelsTask().execute();
//							NeoComApp.getSingletonApp().getCacheConnector().clearPendingRequest(entry.getIdentifier());
//						}
//						if ( entry.reqClass == ERequestClass.OUTPOSTUPDATE ) {
//							// Launch the update and remove the event from the queue.
//							new UpdateOutpostsTask().execute();
//							NeoComApp.getSingletonApp().getCacheConnector().clearPendingRequest(entry.getIdentifier());
//						}
//					}
//			}

		// STEP 02. Check Credentials for pending data to update.
		final List<Credential> credentialList = DataManagementModelStore.accessCredentialList();
		logger.info("-- [TimedUpdater.timeTick]> Accessing credentials. Credentials found: {}"
				, credentialList.size());
		for (Credential cred : credentialList) {
			//			// Get the list of timestamps pending for this Credential.
			//			final List<TimeStamp> stamps = cred.needsUpdate();
			//			TimeTickReceiver.logger.info("-- [TimeTickReceiver.onReceive]> Credential [{}] - Received {} timestamps.", cred.getAccountName(), stamps.size());

			// Scan all the possible TimeStamps we have to check for each Credential.
			// If the TS do not exists then we launch the job and create it.
			// Set up the complete list.
			final ArrayList<GlobalDataManager.EDataUpdateJobs> joblist = new ArrayList<>();
			SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(NeoComModelStore.getActivity());
			boolean blockCharacter = sharedPrefs.getBoolean(AppWideConstants.preferenceKeys.prefkey_BlockCharacterUpdate.name
					(), false);
			if (!blockCharacter) joblist.add(GlobalDataManager.EDataUpdateJobs.CHARACTER_CORE);
			//			joblist.add(EDataUpdateJobs.CHARACTER_FULL);
			sharedPrefs = PreferenceManager.getDefaultSharedPreferences(NeoComModelStore.getActivity());
			boolean blockAssets = sharedPrefs.getBoolean(AppWideConstants.preferenceKeys.prefkey_BlockAssetsUpdate
					.name(), false);
			if (!blockAssets) joblist.add(GlobalDataManager.EDataUpdateJobs.ASSETDATA);
			//		joblist.add(EDataUpdateJobs.BLUEPRINTDATA);
			//			joblist.add(EDataUpdateJobs.INDUSTRYJOBS);
			//			joblist.add(EDataUpdateJobs.MARKETORDERS);
			sharedPrefs = PreferenceManager.getDefaultSharedPreferences(NeoComModelStore.getActivity());
			boolean blockColony = sharedPrefs.getBoolean(AppWideConstants.preferenceKeys.prefkey_BlockColonyUpdate
					.name(), false);
			if (!blockColony) joblist.add(GlobalDataManager.EDataUpdateJobs.COLONYDATA);
			//			joblist.add(EDataUpdateJobs.SKILL_DATA);


			// Not all timestamps should have to be defined so scan them and remove from the list all the ones processed.
			//			for (TimeStamp job : stamps) {
			//				doProcessJob(job, cred);
			//				//				joblist.remove(EDataUpdateJobs.CHARACTER_CORE);
			//			}

			// Now process all the rest of the jobs that where not cleared from the list. That means they had no timestamp.
			for (GlobalDataManager.EDataUpdateJobs jobName : joblist) {
				try {
					final String reference = Job.constructReference(jobName, cred.getAccountId());
					// Search for the TS and check the expiration time.
					final TimeStamp ts = GlobalAndroidDataManager.getHelper().getTimeStampDao().queryForId(reference);
					if (null == ts) {
						TimeTickReceiver.logger.info("-- [TimeTickReceiver.onReceive]> Generating job request for {}.", reference);
						final TimeStamp newts = new TimeStamp(reference, Instant.now())
								.setCredentialId(cred.getAccountId());
						doProcessJob(newts, cred);
					} else {
						// Check if time point has already happened.
						if (ts.getTimeStamp() < Instant.now().getMillis()) {
							TimeTickReceiver.logger.info("-- [TimeTickReceiver.onReceive]> Time point past. Generating job request for {}.",
									reference);
							doProcessJob(ts, cred);
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			logger.info("<< [TimedUpdater.timeTick]");
		}}
		@Override
		public String toString () {
			return new StringBuffer("TimedUpdater[")
					.append("field:").append(0).append(" ")
					.append("]")
//				.append("->").append(super.toString())
					.toString();
		}
	}

// - UNUSED CODE ............................................................................................
//[01]
