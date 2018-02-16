//  PROJECT:     NeoCom.Microservices (NEOC.MS)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Java 1.8 / SpringBoot-1.3.5 / Angular 5.0
//  DESCRIPTION: This is the SpringBoot MicroServices module to run the backend services to complete the web
//               application based on Angular+SB. This is the web version for the NeoCom Android native
//               application. Most of the source code is common to both platforms and this module includes
//               the source for the specific functionality for the backend services.
package org.dimensinfin.eveonline.neocom.testblock.conf;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.dimensinfin.eveonline.neocom.conf.SpringBootConfigurationProvider;
import org.dimensinfin.eveonline.neocom.datamngmt.manager.GlobalDataManager;

/**
 * Performs the tests to validate the read and processing of configuration properties. Those properties are stored on the
 * .../resources/properties folder. The Configuration Manager should read all the *.properties files found on that resources
 * folder and generate a unique memory list for all the configured properties.
 * It will use a last read/last value to declare the properties. If the same property reference is used more than one time the
 * latest read value is the value kept. The properties are read in alphabetical order so if duplicated values dependencies
 * should be kept then the files should be named accordingly.
 * The Manager declares methods to transform the properties string values to other type of variable types like integers or
 * numbers.
 */
public class ConfigurationPropertiesTestUnit {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = LoggerFactory.getLogger("ConfigurationPropertiesTestUnit");
	private static SpringBootConfigurationProvider sbconfig = null;

	@BeforeClass
	public static void testConfigurationInitialization() {
		logger.info(">> [ConfigurationPropertiesTestUnit.testConfigurationInitialization]");
		sbconfig = new SpringBootConfigurationProvider("src/test/resources/properties");
		Assert.assertNotNull("-> Verification Configuration Manager is not null...", sbconfig);
		logger.info("-- [ConfigurationPropertiesTestUnit.testConfigurationInitialization]> Connecting the Configuration Manager...");
		GlobalDataManager.connectConfigurationManager(sbconfig);
//		sbconfig.initialize();
		Assert.assertEquals("-> Verifying the number of properties read match...", sbconfig.contentCount(), 17);
		logger.info("<< [ConfigurationPropertiesTestUnit.testConfigurationInitialization]");
	}

	// - F I E L D - S E C T I O N ............................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................

	// - M E T H O D - S E C T I O N ..........................................................................

	@Test
	public void testConfigurationReadString() {
		logger.info(">> [ConfigurationPropertiesTestUnit.testConfigurationReadString]");
		final String target = sbconfig.getResourceString("R.test.string", "DEFAULT");
		Assert.assertTrue("-> Verification that property value match:", target.equalsIgnoreCase("String Value"));
		final String targetNotFound = sbconfig.getResourceString("R.string.defaultvaluenotfound", "DEFAULT");
		Assert.assertTrue("-> Verification that default value match:", targetNotFound.equalsIgnoreCase("DEFAULT"));
	}

	@Test
	public void testConfigurationReadBoolean() {
		logger.info(">> [ConfigurationPropertiesTestUnit.testConfigurationReadBoolean]");
		boolean target = GlobalDataManager.getResourceBoolean("R.test.true", false);
		Assert.assertTrue("-> Verification that property value match:", target);
		target = GlobalDataManager.getResourceBoolean("R.test.false", true);
		Assert.assertFalse("-> Verification that property value match:", target);
		target = GlobalDataManager.getResourceBoolean("R.test.one");
		Assert.assertFalse("-> Verification that property value match:", target);
		target = GlobalDataManager.getResourceBoolean("R.test.zero");
		Assert.assertFalse("-> Verification that property value match:", target);
		target = GlobalDataManager.getResourceBoolean("R.test.negative");
		Assert.assertFalse("-> Verification that property value match:", target);
		target = GlobalDataManager.getResourceBoolean("R.test.one",true);
		Assert.assertFalse("-> Verification that property value match:", target);

		target = GlobalDataManager.getResourceBoolean("R.test.notfound",true);
		Assert.assertTrue("-> Verification that property value match:", target);
		target = GlobalDataManager.getResourceBoolean("R.test.notfound",false);
		Assert.assertFalse("-> Verification that property value match:", target);
		logger.info("<< [ConfigurationPropertiesTestUnit.testConfigurationReadBoolean]");
	}
}
