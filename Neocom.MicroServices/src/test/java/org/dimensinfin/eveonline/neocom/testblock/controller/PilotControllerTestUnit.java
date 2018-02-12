//  PROJECT:     NeoCom.Microservices (NEOC.MS)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Java 1.8 / SpringBoot-1.3.5 / Angular 5.0
//  DESCRIPTION: This is the SpringBoot MicroServices module to run the backend services to complete the web
//               application based on Angular+SB. This is the web version for the NeoCom Android native
//               application. Most of the source code is common to both platforms and this module includes
//               the source for the specific functionality for the backend services.
package org.dimensinfin.eveonline.neocom.testblock.controller;

import java.sql.SQLException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.joda.JodaModule;

import org.junit.BeforeClass;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.dimensinfin.eveonline.neocom.NeoComMicroServiceApplication;
import org.dimensinfin.eveonline.neocom.conf.SpringBootConfigurationProvider;
import org.dimensinfin.eveonline.neocom.database.NeoComSBDBHelper;
import org.dimensinfin.eveonline.neocom.database.SDESBDBHelper;
import org.dimensinfin.eveonline.neocom.database.entity.Credential;
import org.dimensinfin.eveonline.neocom.datamngmt.manager.ESINetworkManager;
import org.dimensinfin.eveonline.neocom.datamngmt.manager.GlobalDataManager;
import org.dimensinfin.eveonline.neocom.datamngmt.manager.SDEExternalDataManager;
import org.dimensinfin.eveonline.neocom.esiswagger.model.GetCharactersCharacterIdFittings200Ok;
import org.dimensinfin.eveonline.neocom.model.Fitting;
import org.dimensinfin.eveonline.neocom.model.Ship;
import org.dimensinfin.eveonline.neocom.storage.DataManagementModelStore;

	// - CLASS IMPLEMENTATION ...................................................................................
	public class PilotControllerTestUnit {
		// - S T A T I C - S E C T I O N ..........................................................................
		private static Logger logger = LoggerFactory.getLogger("PilotControllerTestUnit");
		public static final ObjectMapper jsonMapper = new ObjectMapper();
		static {
			jsonMapper.enable(SerializationFeature.INDENT_OUTPUT);
			jsonMapper.registerModule(new JodaModule());
			// Add our own serializers.
			SimpleModule neocomSerializerModule = new SimpleModule();
			neocomSerializerModule.addSerializer(Ship.class, new NeoComMicroServiceApplication.ShipSerializer());
			neocomSerializerModule.addSerializer(Credential.class, new NeoComMicroServiceApplication.CredentialSerializer());
			jsonMapper.registerModule(neocomSerializerModule);
		}
		private static final ModelMapper modelMapper = new ModelMapper();
		static {
			modelMapper.getConfiguration()
					.setFieldMatchingEnabled(true)
					.setMethodAccessLevel(Configuration.AccessLevel.PRIVATE);
		}

		@BeforeClass
		public static void testOpenAndConnectGlobal() throws SQLException {
			// Connect the SDE database.
			logger.info(">> [PilotControllerTestUnit.testOpenAndConnectGlobal]> Connecting SDE database...");
			logger.info("-- [PilotControllerTestUnit.testOpenAndConnectGlobal]> Connecting the Configuration Manager...");
			GlobalDataManager.connectConfigurationManager(new SpringBootConfigurationProvider(null));
			// Connect the NeoCom database.
			logger.info("-- [NeoComMicroServiceApplication.main]> Connecting NeoCom private database...");
				GlobalDataManager.connectNeoComDBConnector(new NeoComSBDBHelper()
						.setDatabaseHost(GlobalDataManager
								.getResourceString("R.database.neocom.databasehost", "jdbc:mysql://localhost:3306"))
						.setDatabaseName("neocom")
						.setDatabaseUser("NEOCOM")
						.setDatabasePassword("01.Alpha")
						.setDatabaseVersion(GlobalDataManager.getResourceInt("R.database.neocom.databaseversion"))
						.build()
				);
				// Connect the SDE database.
				logger.info(">> [PilotControllerTestUnit.testOpenAndConnectGlobal]> Connecting SDE database...");
				GlobalDataManager.connectSDEDBConnector(new SDESBDBHelper()
						.setDatabaseSchema(GlobalDataManager.getResourceString("R.database.sdedatabase.databaseschema"))
						.setDatabasePath(GlobalDataManager.getResourceString("R.database.sdedatabase.databasepath"))
						.setDatabaseName(GlobalDataManager.getResourceString("R.database.sdedatabase.databasename"))
						.build()
				);
//				// Connect the MarketData service.
//				logger.info("-- [PilotControllerTestUnit.testOpenAndConnectGlobal]> Starting Market Data service...");
//				GlobalDataManager.setMarketDataManager(new MarketDataServer().start());

//			// Check the connection descriptor.
//			Assert.assertEquals(sdeHelper.getConnectionDescriptor(), schema + ":" + dbpath + dbname);
			// Check the database is open and has a valid connection.
//			Assert.assertTrue(GlobalDataManager.getSD.databaseIsValid());
		}

		// - F I E L D - S E C T I O N ............................................................................

		// - C O N S T R U C T O R - S E C T I O N ................................................................

		// - M E T H O D - S E C T I O N ..........................................................................
		@Test
		public void testFlagDetailSearch(){
			final SDEExternalDataManager.InventoryFlag detailedFlag = SDEExternalDataManager.searchFlag4Id(15);
		}
		@Test
	public void testFittingDownload( ) throws JsonProcessingException {
		logger.info(">> [PilotControllerTestUnit.testFittingDownload]");
			final Integer id = 92223647;
			// Activate the list of credentials.
			DataManagementModelStore.activateCredential(id);
			// Download the list of fittings.
			Credential credential = DataManagementModelStore.getCredential4Id(id);
			// Get to the Network and download the data from the ESI api.
			final List<GetCharactersCharacterIdFittings200Ok> fittings = ESINetworkManager.getCharactersCharacterIdFittings(id, credential.getRefreshToken(), "tranquility");
			final GetCharactersCharacterIdFittings200Ok fit = fittings.get(0);
			final Fitting newfitting = modelMapper.map(fit, Fitting.class);
			 String contentsSerialized = jsonMapper.writeValueAsString(fittings);
			contentsSerialized=jsonMapper.writeValueAsString(newfitting);
			logger.info("<< [PilotControllerTestUnit.testFittingDownload]");
	}

}
