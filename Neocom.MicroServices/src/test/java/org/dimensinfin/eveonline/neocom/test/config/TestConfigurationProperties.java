//  PROJECT:     Neocom.Microservices (NEOC-MS)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Java 1.8 / SpringBoot-1.3.5 / Angular 5.0
//  DESCRIPTION: This is the SpringBoot MicroServices module to run the backend services to complete the web
//               application based on Angular+SB. This is the web version for the NeoCom Android native
//               application. Most of the source code is common to both platforms and this module includes
//               the source for the specific functionality for the backend services.
package org.dimensinfin.eveonline.neocom.test.config;

import org.dimensinfin.eveonline.neocom.conf.SpringBootConfigurationProvider;
import org.junit.Assert;
import org.junit.Test;

public class TestConfigurationProperties {
	// - F I E L D - S E C T I O N ............................................................................
private  SpringBootConfigurationProvider sbconfig=null;

	// - C O N S T R U C T O R - S E C T I O N ................................................................

	// - M E T H O D - S E C T I O N ..........................................................................
	@Test
	public void testConfigurationInitialization () {
		 sbconfig = new SpringBootConfigurationProvider();
		sbconfig.initialize();
	}
	public void testConfigurationReadString () {
		testConfigurationInitialization();
		final String target = sbconfig.getResourceString("R.string.defaultvalue", "MANUAL DEFAULT");
		Assert.assertTrue(target.equalsIgnoreCase("DEFAULT"));
		final String targetNotFound = sbconfig.getResourceString("R.string.defaultvaluenotfound", "MANUAL DEFAULT");
		Assert.assertTrue(targetNotFound.equalsIgnoreCase("MANUAL DEFAULT"));
	}
}
