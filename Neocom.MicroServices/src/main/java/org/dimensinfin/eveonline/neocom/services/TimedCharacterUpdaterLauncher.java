//	PROJECT:      Neocom.Microservices (NEOC-MS)
//	AUTHORS:      Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:	SpringBoot-MS-Java 1.8.
//	DESCRIPTION:	This is the integration project for all the web server pieces. This is the launcher for
//								the SpringBoot+MicroServices+Angular unified web application.
package org.dimensinfin.eveonline.neocom.services;

import java.util.Hashtable;
import java.util.logging.Logger;

import org.dimensinfin.eveonline.neocom.connector.AppConnector;
import org.dimensinfin.eveonline.neocom.enums.EDataBlock;
import org.dimensinfin.eveonline.neocom.model.Login;
import org.dimensinfin.eveonline.neocom.model.NeoComCharacter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

// - CLASS IMPLEMENTATION ...................................................................................
@Component
public class TimedCharacterUpdaterLauncher {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger		logger					= Logger.getLogger("TimedCharacterUpdaterLauncher");
	private static boolean	BLOCKED_STATUS	= false;
	private static int			LAUNCH_LIMIT		= 30;

	// - F I E L D - S E C T I O N ............................................................................
	private final int				limit						= 0;

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public TimedCharacterUpdaterLauncher() {
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	@Scheduled(initialDelay = 10000, fixedDelay = 15000)
	public void onTime() {
		logger.info(">> [TimedCharacterUpdaterLauncher.onTime]");

		// STEP 02. Check characters for pending structures to update.
		Hashtable<String, Login> logins = AppConnector.getModelStore().accessLoginList();
		for (String key : logins.keySet()) {
			for (NeoComCharacter eveChar : logins.get(key).getCharacters()) {
				EDataBlock updateCode = eveChar.needsUpdate();
				if (updateCode != EDataBlock.READY) {
					logger.info("-- [TimeTickReceiver.onTime] EDataBlock to update: " + eveChar.getName() + " - " + updateCode);
					CharacterUpdaterService.processCharacter(eveChar);
				}
			}
		}
		logger.info("<< [TimedCharacterUpdaterLauncher.onTime]");
	}

	//	@Bean
	//	public TaskExecutor taskExecutor() {
	//		return new SimpleAsyncTaskExecutor(); // Or use another one of your liking
	//	}

	//	private boolean blockedDownload() {
	//		//		// Read the flag values from the preferences.
	//		//		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(_context);
	//		//		boolean blockDownload = sharedPrefs.getBoolean(AppWideConstants.preference.PREF_BLOCKDOWNLOAD, false);
	//		return false;
	//	}
	//
	//	private boolean blockedMarket() {
	//		//		// Read the flag values from the preferences.
	//		//		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(_context);
	//		//		boolean blockDownload = sharedPrefs.getBoolean(AppWideConstants.preference.PREF_BLOCKMARKET, false);
	//		return false;
	//	}
}
// - UNUSED CODE ............................................................................................
