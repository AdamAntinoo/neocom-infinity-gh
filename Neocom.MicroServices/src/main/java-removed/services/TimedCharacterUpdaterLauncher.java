//  PROJECT:     NeoCom.Microservices (NEOC.MS)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Java 1.8 / SpringBoot-1.3.5 / Angular 5.0
//  DESCRIPTION: This is the SpringBoot MicroServices module to run the backend services to complete the web
//               application based on Angular+SB. This is the web version for the NeoCom Android native
//               application. Most of the source code is common to both platforms and this module includes
//               the source for the specific functionality for the backend services.
package org.dimensinfin.eveonline.neocom.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

// - CLASS IMPLEMENTATION ...................................................................................
@Component
public class TimedCharacterUpdaterLauncher {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = LoggerFactory.getLogger("TimedCharacterUpdaterLauncher");

	// - F I E L D - S E C T I O N ............................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public TimedCharacterUpdaterLauncher() {
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	@Scheduled(initialDelay = 120000, fixedDelay = 30000)
	public void onTime() {
		logger.info(">> [TimedCharacterUpdaterLauncher.onTime]");
		Hashtable<String, Login> logins = NeoComMSConnector.getSingleton().getModelStore().accessLoginList();
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
}
// - UNUSED CODE ............................................................................................
