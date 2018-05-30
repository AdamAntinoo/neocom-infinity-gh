//  PROJECT:     NeoCom.Microservices (NEOC.MS)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Java 1.8 / SpringBoot-1.3.5 / Angular 5.0
//  DESCRIPTION: This is the SpringBoot MicroServices module to run the backend services to complete the web
//               application based on Angular+SB. This is the web version for the NeoCom Android native
//               application. Most of the source code is common to both platforms and this module includes
//               the source for the specific functionality for the backend services.
package org.dimensinfin.eveonline.neocom.database;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import com.j256.ormlite.dao.Dao;

import org.joda.time.Instant;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.dimensinfin.eveonline.neocom.FullInitializationTestBase;
import org.dimensinfin.eveonline.neocom.datamngmt.GlobalDataManager;
import org.dimensinfin.eveonline.neocom.datamngmt.InfinityGlobalDataManager;
import org.dimensinfin.eveonline.neocom.enums.ELocationType;
import org.dimensinfin.eveonline.neocom.model.EveLocation;

/**
 * @author Adam Antinoo
 */
// - CLASS IMPLEMENTATION ...................................................................................
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NeoComDatabaseTestUnit extends FullInitializationTestBase {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = LoggerFactory.getLogger("NeoComDatabaseTestUnit");

	// - F I E L D - S E C T I O N ............................................................................

	// - M E T H O D - S E C T I O N ..........................................................................
	@Test
	public void test01DatabaseConnectionActive() {
		logger.info(">> [NeoComDatabaseTestUnit.test01DatabaseConnectionActive]");
		final INeoComDBHelper helper = new InfinityGlobalDataManager().getNeocomDBHelper();
		Assert.assertNotNull("-> NeoComDBHelper does not exist.", helper);
		Assert.assertTrue("-> Database is not valid.", helper.isDatabaseValid());
		Assert.assertTrue("-> Database is not open.", helper.isOpen());
		final int version = helper.getDatabaseVersion();
		Assert.assertTrue("-> Database version does not match.", version > 100);
		logger.info(">> [NeoComDatabaseTestUnit.test01DatabaseConnectionActive]");
	}

	@Test
	public void test02DaosValidation() throws SQLException {
		logger.info(">> [NeoComDatabaseTestUnit.test02DaosValidation]");
		final INeoComDBHelper helper = new InfinityGlobalDataManager().getNeocomDBHelper();
		Assert.assertNotNull("-> AssetDao is not available.", helper.getAssetDao());
		Assert.assertNotNull("-> ColonyDao is not available.", helper.getColonyDao());
		Assert.assertNotNull("-> CredentialDao is not available.", helper.getCredentialDao());
		Assert.assertNotNull("-> FittingRequest is not available.", helper.getFittingRequestDao());
		Assert.assertNotNull("-> JobDao is not available.", helper.getJobDao());
		Assert.assertNotNull("-> LocationDao is not available.", helper.getLocationDao());
		logger.info(">> [NeoComDatabaseTestUnit.test02DaosValidation]");
	}

	@Test
	public void test03LocationEntityVerification() throws SQLException {
		logger.info(">> [NeoComDatabaseTestUnit.test03LocationEntityVerification]");
		// Check that we can create, verify and delete a Location on the NeoCom database.
		final Dao<EveLocation, String> locDao = new InfinityGlobalDataManager().getNeocomDBHelper().getLocationDao();
		Assert.assertNotNull("-> LocationDao is not available.", locDao);
		final EveLocation testLocation = new EveLocation(1001001)
				.setRegionId(10)
				.setRegion("Test Region")
				.setConstellationId(20)
				.setConstellation("Test Constellation")
				.setSystemId(30)
				.setSystem("Test System")
				.setStationId(40)
				.setStation("Test Station")
				.setSecurity("0.5")
				.setTypeId(ELocationType.DEEP_SPACE)
				.store();
		Assert.assertNotNull("-> Location not properly created.", testLocation);

		final EveLocation backLocation = new InfinityGlobalDataManager().getNeocomDBHelper().getLocationDao().queryForId("1001001");
		Assert.assertNotNull("-> Location not retrieved from database.", backLocation);
		Assert.assertEquals("-> Attribute RegionId not matches.", 10, backLocation.getRegionId());
		Assert.assertEquals("-> Attribute Region not matches.", "Test Region", backLocation.getRegion());

		new InfinityGlobalDataManager().getNeocomDBHelper().getLocationDao().delete(backLocation);
		final EveLocation deleteLocation = new InfinityGlobalDataManager().getNeocomDBHelper().getLocationDao().queryForId("1001001");
		Assert.assertNull("-> Location not deleted.", deleteLocation);

		logger.info(">> [NeoComDatabaseTestUnit.test03LocationEntityVerification]");
	}

	@Test
	public void test04VerifyTimeStampsAdditions() {
		logger.info(">> [NeoComDatabaseTestUnit.test04VerifyTimeStampsAdditions]");
		final Instant instantNow = Instant.now();
		final long nowMillis = instantNow.getMillis();
		logger.info("-- [NeoComDatabaseTestUnit.test04VerifyTimeStampsAdditions]> nowMillis: {}", nowMillis);

		final long difference = GlobalDataManager.getCacheTime4Type(GlobalDataManager.ECacheTimes.ASSETS_ASSETS);
		final Instant futureNow = instantNow.plus(GlobalDataManager.getCacheTime4Type(GlobalDataManager.ECacheTimes.ASSETS_ASSETS));
		final long futureMillis = futureNow.getMillis();
		logger.info("-- [NeoComDatabaseTestUnit.test04VerifyTimeStampsAdditions]> differenc: {}", difference);
		logger.info("-- [NeoComDatabaseTestUnit.test04VerifyTimeStampsAdditions]> futMillis: {}", futureMillis);

		final long assetsCacheTime = TimeUnit.SECONDS.toMillis(3600);
		logger.info("-- [NeoComDatabaseTestUnit.test04VerifyTimeStampsAdditions]> checkTime: {}", assetsCacheTime);
		logger.info("<< [NeoComDatabaseTestUnit.test04VerifyTimeStampsAdditions]");
	}

	@Test
	public void test05LocationSearch() throws SQLException {
		logger.info(">> [NeoComDatabaseTestUnit.test05LocationSearch]");
		final EveLocation location = new InfinityGlobalDataManager().searchLocation4Id(30002510);
		Assert.assertNotNull("-> Location not retrieved from database.", location);
		logger.info(">> [NeoComDatabaseTestUnit.test05LocationSearch]");
	}
}

// - UNUSED CODE ............................................................................................
//[01]
//	@Test
//	public void test01VerifyTimeStamps() throws SQLException {
//		logger.info(">> [NeoComDatabaseTestUnit.test01VerifyTimeStamps]");
//		// Get the current value for the TimeStamp.
//		final Credential credential = DataManagementModelStore.activateCredential(92002067);
//		final String reference = TimedUpdater.Job.constructReference(GlobalDataManager.EDataUpdateJobs.ASSETDATA
//				, credential.getAccountId());
//		TimeStamp ts = GlobalDataManager.getNeocomDBHelper().getTimeStampDao().queryForId(reference);
//		Assert.assertNotNull("-> Validating the timestamp is not null...", ts);
//		// Check if time point has already happened.
//		long nowMillis = Instant.now().getMillis();
//		long tsMillis = ts.getTimeStamp();
//		logger.info("-- [NeoComDatabaseTestUnit.test01VerifyTimeStamps]> nowMillis: {}", nowMillis);
//		logger.info("-- [NeoComDatabaseTestUnit.test01VerifyTimeStamps]> tsMillis : {}", tsMillis);
//		if (tsMillis < nowMillis) {
//			logger.info("-- [NeoComDatabaseTestUnit.test01VerifyTimeStamps]> TimeStamp is elapsed.");
//		} else {
//			logger.info("-- [NeoComDatabaseTestUnit.test01VerifyTimeStamps]> TimeStamp in the future.");
//		}
//		logger.info("-- [NeoComDatabaseTestUnit.test01VerifyTimeStamps]> Updating TimeStamp to 3600 seconds in the future.");
//
//		// Update the timer for this download at the database.
//		final Instant validUntil = Instant.now()
//				.plus(GlobalDataManager.getCacheTime4Type(GlobalDataManager.ECacheTimes.ASSETS_ASSETS));
//		nowMillis = validUntil.getMillis();
//		logger.info("-- [NeoComDatabaseTestUnit.test01VerifyTimeStamps]> futMillis: {}", nowMillis);
//		ts = new TimeStamp(reference, validUntil)
//				.setCredentialId(credential.getAccountId())
//				.store();
//
//		// validation after the update.
//		ts = GlobalDataManager.getNeocomDBHelper().getTimeStampDao().queryForId(reference);
//		Assert.assertNotNull("-> Validating the timestamp is not null...", ts);
//		// Check if time point has already happened.
//		nowMillis = Instant.now().getMillis();
//		tsMillis = ts.getTimeStamp();
//		logger.info("-- [NeoComDatabaseTestUnit.test01VerifyTimeStamps]> nowMillis: {}", nowMillis);
//		logger.info("-- [NeoComDatabaseTestUnit.test01VerifyTimeStamps]> tsMillis : {}", tsMillis);
//		if (tsMillis < nowMillis) {
//			logger.info("-- [NeoComDatabaseTestUnit.test01VerifyTimeStamps]> TimeStamp is elapsed.");
//		} else {
//			logger.info("-- [NeoComDatabaseTestUnit.test01VerifyTimeStamps]> TimeStamp in the future.");
//		}
//		logger.info("-- [NeoComDatabaseTestUnit.test01VerifyTimeStamps]> Updating TimeStamp to 3600 seconds in the future.");
//		logger.info("<< [NeoComDatabaseTestUnit.test01VerifyTimeStamps]");
//	}
