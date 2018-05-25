//  PROJECT:     NeoCom.DataManagement(NEOC.DTM)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Java 1.8 Library.
//  DESCRIPTION: NeoCom project library that comes from the old Models package but that includes much more
//               functionality than the model definitions for the Eve Online NeoCom application.
//               If now defines the pure java code for all the repositories, caches and managers that do
//               not have an specific Android implementation serving as a code base for generic platform
//               development. The architecture model has also changed to a better singleton/static
//               implementation that reduces dependencies and allows separate use of the modules. Still
//               there should be some initialization/configuration code to connect the new library to the
//               runtime implementation provided by the Application.
package org.dimensinfin.eveonline.neocom.planetary;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.dimensinfin.eveonline.neocom.database.SDESBDBHelper;
import org.dimensinfin.eveonline.neocom.datamngmt.ESINetworkManager;
import org.dimensinfin.eveonline.neocom.datamngmt.FileSystemSBImplementation;
import org.dimensinfin.eveonline.neocom.datamngmt.GlobalDataManager;
import org.dimensinfin.eveonline.neocom.datamngmt.GlobalSBConfigurationProvider;
import org.dimensinfin.eveonline.neocom.datamngmt.InfinityGlobalDataManager;
import org.dimensinfin.eveonline.neocom.datamngmt.MarketDataServer;
import org.dimensinfin.eveonline.neocom.industry.Resource;
import org.dimensinfin.eveonline.neocom.model.ANeoComEntity;
import org.dimensinfin.eveonline.neocom.model.NeoComAsset;

/**
 * @author Adam Antinoo
 */
// - CLASS IMPLEMENTATION ...................................................................................
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PlanetaryProcessorTestUnit {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = LoggerFactory.getLogger("PlanetaryProcessorTestUnit");

	@BeforeClass
	public static void testBefore01Initialize() throws IOException {
		logger.info(">> [PlanetaryProcessorTestUnit.testBefore01Initialize]");

		// Connect the file system to be able to read the assets and other application resources stored externally.
		logger.info("-- [NeoComApp.onCreate]> Connecting the File System to Global...");
		InfinityGlobalDataManager.installFileSystem(new FileSystemSBImplementation(
				System.getenv("appname"))
		);

		// Connect the Configuration manager.
		logger.info("-- [PlanetaryProcessorTestUnit.testBefore01Initialize]> Connecting the Configuration Manager...");
		InfinityGlobalDataManager.connectConfigurationManager(new GlobalSBConfigurationProvider("properties"));

		// Initialize the Model with the current global instance.
		logger.info("-- [PlanetaryProcessorTestUnit.testBefore01Initialize]> Connecting Global to Model...");
		ANeoComEntity.connectGlobal(new InfinityGlobalDataManager());

		// Initializing the ESI api network controller.
		ESINetworkManager.initialize();

		// Connect the SDE database.
		logger.info("-- [NeoComMicroServiceApplication.main]> Connecting SDE database...");
		try {
			InfinityGlobalDataManager.connectSDEDBConnector(new SDESBDBHelper()
					.setDatabaseSchema(GlobalDataManager.getResourceString("R.database.sdedatabase.databaseschema"))
					.setDatabasePath(GlobalDataManager.getResourceString("R.database.sdedatabase.databasepath"))
					.setDatabaseName(GlobalDataManager.getResourceString("R.database.sdedatabase.databasename"))
					.build()
			);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		ANeoComEntity.connectSDEHelper(new GlobalDataManager().getSDEDBHelper());

		// Connect the MarketData service.
		logger.info("-- [NeoComMicroServiceApplication.main]> Starting Market Data service...");
		final MarketDataServer mdServer = new MarketDataServer().start();
		InfinityGlobalDataManager.setMarketDataManager(mdServer);

		logger.info("<< [PlanetaryProcessorTestUnit.testBefore01Initialize]");
	}
	// - F I E L D - S E C T I O N ............................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................

	// - M E T H O D - S E C T I O N ..........................................................................
	@Test
	public void test01GetSorageResourcesTier4() {
		logger.info(">> [PlanetaryProcessorTestUnit.test01GetSorageResourcesTier4]");
		// Read the list of Planetary Resources from the mock data file.
		final String inputFileName = InfinityGlobalDataManager.getResourceString("R.runtime.mockdata.location")
						+ "PlanetaryResources430003752system.data";
		List<Resource> resources = new ArrayList<>();
		try {
			final BufferedInputStream buffer = new BufferedInputStream(InfinityGlobalDataManager.openAsset4Input(inputFileName));
			final ObjectInputStream input = new ObjectInputStream(buffer);
			try {
				resources = (List<Resource>) input.readObject();
				logger.info("-- [PlanetaryProcessorTestUnit.test01GetSorageResourcesTier4]> Restored Planetary Resources: "
						+ resources.size() + " entries.");
			} finally {
				input.close();
				buffer.close();
			}
		} catch (final ClassNotFoundException ex) {
			logger.warn("W> [PlanetaryProcessorTestUnit.test01GetSorageResourcesTier4]> ClassNotFoundException."); //$NON-NLS-1$
		} catch (final FileNotFoundException fnfe) {
			logger.warn("W> [PlanetaryProcessorTestUnit.test01GetSorageResourcesTier4]> FileNotFoundException."); //$NON-NLS-1$
		} catch (final IOException ex) {
			logger.warn("W> [PlanetaryProcessorTestUnit.test01GetSorageResourcesTier4]> IOException."); //$NON-NLS-1$
		} catch (final RuntimeException rex) {
			rex.printStackTrace();
		}

		// Process the assets into a list of resources for the scenario.
//		List<Resource> resources = new Vector();
//		List<ProcessingAction> bestScenario = new Vector<ProcessingAction>();
//		for (NeoComAsset resource : planetaryResources) {
//			if (resource.getLocation().getSystemId() == 30003752) {
//				resources.add(new Resource(resource.getTypeId(), resource.getQuantity()));
//			}
//		}
		// The Planetary Advisor requires a list of Planetary Resources to be stocked to start the profit calculations.
		PlanetaryScenery scenery = new PlanetaryScenery();
		scenery.stock(resources);
		// Create the initial processing point and start the optimization recursively.
		PlanetaryProcessor proc = new PlanetaryProcessorV3(scenery);

		// Main entry test point. Get the list of Tier 4 for this system.

		final PlanetaryScenery data = ((PlanetaryProcessorV3) proc).startProfitSearchNew();
		logger.info("<< [PlanetaryProcessorTestUnit.test01GetSorageResourcesTier4]");
	}
}

// - UNUSED CODE ............................................................................................
//[01]
