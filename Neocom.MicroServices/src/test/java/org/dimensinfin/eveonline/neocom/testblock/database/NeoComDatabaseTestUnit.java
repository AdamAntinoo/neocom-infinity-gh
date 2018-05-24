//  PROJECT:     NeoCom.Microservices (NEOC.MS)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Java 1.8 / SpringBoot-1.3.5 / Angular 5.0
//  DESCRIPTION: This is the SpringBoot MicroServices module to run the backend services to complete the web
//               application based on Angular+SB. This is the web version for the NeoCom Android native
//               application. Most of the source code is common to both platforms and this module includes
//               the source for the specific functionality for the backend services.
package org.dimensinfin.eveonline.neocom.testblock.database;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import org.joda.time.Instant;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.dimensinfin.eveonline.neocom.database.NeoComSBDBHelper;
import org.dimensinfin.eveonline.neocom.database.SDESBDBHelper;
import org.dimensinfin.eveonline.neocom.datamngmt.GlobalDataManager;

/**
 * @author Adam Antinoo
 */
// - CLASS IMPLEMENTATION ...................................................................................
public class NeoComDatabaseTestUnit {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = LoggerFactory.getLogger("NeoComDatabaseTestUnit");

	//	@BeforeClass
	public static void testOpenAndConnectDatabase() throws SQLException {
		logger.info(">> [NeoComDatabaseTestUnit.testOpenAndConnectDatabase]");
		// Connect the SDE database.
		logger.info("-- [NeoComDatabaseTestUnit.testOpenAndConnectDatabase]> Connecting SDE database...");
		GlobalDataManager.connectSDEDBConnector(new SDESBDBHelper()
				.setDatabaseSchema(GlobalDataManager.getResourceString("R.database.sdedatabase.databaseschema"))
				.setDatabasePath(GlobalDataManager.getResourceString("R.database.sdedatabase.databasepath"))
				.setDatabaseName(GlobalDataManager.getResourceString("R.database.sdedatabase.databasename"))
				.build()
		);
		// Connect the NeoCom database.
		logger.info("-- [NeoComDatabaseTestUnit.testOpenAndConnectDatabase]> Connecting NeoCom private database...");
		GlobalDataManager.connectNeoComDBConnector(new NeoComSBDBHelper()
				.setDatabaseHost(GlobalDataManager
						.getResourceString("R.database.neocom.databasehost", "jdbc:mysql://localhost:3306"))
				.setDatabaseName("neocom")
				.setDatabaseUser("NEOCOM")
				.setDatabasePassword("01.Alpha")
				.setDatabaseVersion(GlobalDataManager.getResourceInt("R.database.neocom.databaseversion"))
				.build()
		);
		// Check the connection descriptor.
		Assert.assertEquals("-> Validating the database is valid..."
				, new GlobalDataManager().getNeocomDBHelper().isDatabaseValid()
				, true);
		// Check the database is open and has a valid connection.
		Assert.assertEquals("-> Validating the database is open..."
				, new GlobalDataManager().getNeocomDBHelper().isOpen()
				, true);
		logger.info("<< [NeoComDatabaseTestUnit.testOpenAndConnectDatabase]");
	}

	// - F I E L D - S E C T I O N ............................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................

	// - M E T H O D - S E C T I O N ..........................................................................
//	@Test
	public void test01VerifyTimeStamps() throws SQLException {
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
	}

	@Test
	public void test02VerifyTimeStampsAdditions() {
		logger.info(">> [NeoComDatabaseTestUnit.test02VerifyTimeStampsAdditions]");
		final Instant instantNow = Instant.now();
		final long nowMillis = instantNow.getMillis();
		logger.info("-- [NeoComDatabaseTestUnit.test02VerifyTimeStampsAdditions]> nowMillis: {}", nowMillis);

		final long difference = GlobalDataManager.getCacheTime4Type(GlobalDataManager.ECacheTimes.ASSETS_ASSETS);
		final Instant futureNow = instantNow.plus(GlobalDataManager.getCacheTime4Type(GlobalDataManager.ECacheTimes.ASSETS_ASSETS));
		final long futureMillis = futureNow.getMillis();
		logger.info("-- [NeoComDatabaseTestUnit.test02VerifyTimeStampsAdditions]> differenc: {}", difference);
		logger.info("-- [NeoComDatabaseTestUnit.test02VerifyTimeStampsAdditions]> futMillis: {}", futureMillis);

		final long assetsCacheTime = TimeUnit.SECONDS.toMillis(3600);
		logger.info("-- [NeoComDatabaseTestUnit.test02VerifyTimeStampsAdditions]> checkTime: {}", assetsCacheTime);
		logger.info("<< [NeoComDatabaseTestUnit.test02VerifyTimeStampsAdditions]");
	}
}

// - UNUSED CODE ............................................................................................
//[01]
