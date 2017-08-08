//	PROJECT:      Neocom.Microservices (NEOC-MS)
//	AUTHORS:      Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:    (c) 2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:	SpringBoot-MS-Java 1.8.
//	DESCRIPTION:	This is the integration project for all the web server pieces. This is the launcher for
//								the SpringBoot+MicroServices+Angular unified web application.
package org.dimensinfin.eveonline.neocom.microservices.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;

import org.dimensinfin.core.model.IGEFNode;
import org.dimensinfin.core.model.RootNode;
import org.dimensinfin.eveonline.neocom.datamodel.DataSourceLocator;
import org.dimensinfin.eveonline.neocom.datamodel.IModelAdapter;
import org.dimensinfin.eveonline.neocom.datamodel.PilotRoasterModelAdapter;
import org.dimensinfin.eveonline.neocom.model.Pilot;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

// - CLASS IMPLEMENTATION ...................................................................................
@RestController
public class PilotRoasterController {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = Logger.getLogger("PilotRoasterController");

	// - F I E L D - S E C T I O N ............................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................

	// - M E T H O D - S E C T I O N ..........................................................................
	@CrossOrigin()
	@RequestMapping(value = "/api/v1/pilot", method = RequestMethod.GET, produces = "application/json")
	public Pilot pilotDetail(@PathVariable final String identifier) {
		String login = "Beth";
		logger.info(">> [PilotRoasterController.pilotDetail]");
		Vector<Pilot> pilotList = new Vector<Pilot>();
		// Get the cookie and the login identifier inside it.
		if (null != login) {
			// Get a new model interface for the Pilot roaster using as unique identifier the login.
			IModelAdapter adapter = ModelAdapterManager
					.registerAdapter(new PilotRoasterModelAdapter(new DataSourceLocator().addIdentifier(login), login));
			RootNode pilotNode = adapter.collaborate2Model();
			for (IGEFNode pilot : pilotNode.getChildren()) {
				pilotList.add((Pilot) pilot);
			}
		}
		logger.info("<< [PilotRoasterController.pilotDetail]");
		return pilotList.get(0);
	}

	/**
	 * This requests will extract the login identifier from the session cookie and then with that identifier
	 * search for the api keys on the database. Those keys will be the input to the datasource to get the list
	 * of pilots associated with the identified login. With that information the datasource will be able to go
	 * to the CCP servers to get the each <code>Pilot</code> information.
	 * 
	 * @return list of <code>Pilot</code>s associated to the current session login. Each of this may become a
	 *         different datasource item that can be cached.
	 */
	@CrossOrigin()
	@RequestMapping(value = "/api/v1/pilotroaster", method = RequestMethod.GET, produces = "application/json")
	//public List<Pilot> pilotRoaster(@CookieValue("login") String login) {
	public List<Pilot> pilotRoaster(/* @CookieValue("login") String login */) {
		String login = "Beth";
		logger.info(">> [PilotRoasterController.pilotRoaster]");
		Vector<Pilot> pilotList = new Vector<Pilot>();
		// Get the cookie and the login identifier inside it.
		if (null != login) {
			// Get a new model interface for the Pilot roaster using as unique identifier the login.
			IModelAdapter adapter = ModelAdapterManager
					.registerAdapter(new PilotRoasterModelAdapter(new DataSourceLocator().addIdentifier(login), login));
			RootNode pilotNode = adapter.collaborate2Model();
			for (IGEFNode pilot : pilotNode.getChildren()) {
				pilotList.add((Pilot) pilot);
			}
		}
		logger.info("<< [PilotRoasterController.pilotRoaster]");
		return pilotList;
	}

}

// - UNUSED CODE ............................................................................................
class ModelAdapterManager {
	// - F I E L D - S E C T I O N ............................................................................
	private static final HashMap<String, IModelAdapter> adapters = new HashMap<String, IModelAdapter>();

	// - C O N S T R U C T O R - S E C T I O N ................................................................

	// - M E T H O D - S E C T I O N ..........................................................................
	public static IModelAdapter registerAdapter(final IModelAdapter newAdapter) {
		DataSourceLocator locator = newAdapter.getDataSourceLocator();
		// Search for locator on cache.
		IModelAdapter found = adapters.get(locator.getIdentity());
		// REFACTOR Do not return cached datasources.
		found = null;
		if (null == found) {
			adapters.put(locator.getIdentity(), newAdapter);
			//	logger					.info("-- [DataSourceManager.registerDataSource]> Registering new DataSource: " + locator.getIdentity());
			//		newAdapter.connect(this);
			return newAdapter;
		} else
			return found;
	}
}
