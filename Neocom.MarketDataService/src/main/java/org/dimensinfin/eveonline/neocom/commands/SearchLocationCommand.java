//	PROJECT:      Neocom.MarketDataService (NEOC-MKDS)
//	AUTHORS:      Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:	SpringBoot-MS-Java 1.8.
//	DESCRIPTION:	This project contains a MicroService specially dedicated to get and store the Market Data
//								information. I should be exposed on a new port and should be accesible to all the backend MS to consult 
//								Item Market Data.
package org.dimensinfin.eveonline.neocom.commands;

import java.util.logging.Logger;

import org.dimensinfin.eveonline.neocom.connector.MDSCCPDatabaseConnector;
import org.dimensinfin.eveonline.neocom.model.EveLocation;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

// - CLASS IMPLEMENTATION ...................................................................................
public class SearchLocationCommand extends HystrixCommand<EveLocation> {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger						logger					= Logger.getLogger("SearchLocationCommand");
	private static final String			COMMAND_GROUP		= "Locations";

	// - F I E L D - S E C T I O N ............................................................................
	private long										locationid			= -2;
	private MDSCCPDatabaseConnector	implementation	= new MDSCCPDatabaseConnector();

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public SearchLocationCommand() {
		super(HystrixCommandGroupKey.Factory.asKey(COMMAND_GROUP));
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	/**
	 * Search on the CCP Database or on the Application database for a new location ID that is not already on
	 * the cache. Locations are extended objects on the NeoCom model because to the standard and game defined
	 * locations we should add an external resource with the compilation of user deployed structures (Citadels,
	 * Refineries, etc) that can also store assets and that are becoming the real place where to have the items.
	 * We also have to include another external source that are the Outposts than come fom CCP sources but that
	 * are being slowly phased out.<br>
	 * The process starts searching for locations depending on range, first at the CCP database and then at the
	 * Locations table at the application database.
	 */
	@Override
	public EveLocation run() throws Exception {
		logger.info(">< [SearchLocationCommand.searchLocationbyID]> Searching ID: " + locationid);
		return implementation.searchLocationbyID2(locationid);
	}

	public SearchLocationCommand setLocationId(long identifier) {
		this.locationid = identifier;
		return this;
	}

	@Override
	protected EveLocation getFallback() {
		logger.warning("W> [SearchLocationCommand.searchLocationbyID]> Error detected. Falling back to default value.");
		EveLocation hit = new EveLocation(locationid);
		hit.setSystem("ID>" + Long.valueOf(locationid).toString());
		return hit;
	}
}

// - UNUSED CODE ............................................................................................
