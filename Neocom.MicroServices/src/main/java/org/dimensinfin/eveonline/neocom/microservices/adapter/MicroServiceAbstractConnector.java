//	PROJECT:        MS-PlanetaryOptimizer (MS-PO)
//	AUTHORS:        Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:	SpringBoot-MS-Java 1.8.
//	DESCRIPTION:	Initial step to have an Eve application based on the Android code. This version exports
//								a list of assets and also has access to the Eve Item CCP database to get some of the
//								asset items properties..
package org.dimensinfin.eveonline.neocom.microservices.adapter;

import java.util.logging.Logger;

import org.dimensinfin.eveonline.neocom.connector.ICCPDatabaseConnector;
import org.dimensinfin.eveonline.neocom.connector.IConnector;
import org.dimensinfin.eveonline.neocom.connector.IDatabaseConnector;
import org.dimensinfin.eveonline.neocom.connector.IStorageConnector;
import org.dimensinfin.eveonline.neocom.connector.NeocomDatabaseConnector;
import org.dimensinfin.eveonline.neocom.core.INeoComModelStore;

// - CLASS IMPLEMENTATION ...................................................................................
public abstract class MicroServiceAbstractConnector implements IConnector {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger				logger						= Logger.getLogger("MicroServiceAbstractConnector");

	// - F I E L D - S E C T I O N ............................................................................
	private IDatabaseConnector	dbNeocomConnector	= null;

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public MicroServiceAbstractConnector() {
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	@Override
	public void addCharacterUpdateRequest(long characterID) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean checkExpiration(long timestamp, long window) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getAppFilePath(int fileresourceid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAppFilePath(String fileresourceid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ICCPDatabaseConnector getCCPDBConnector() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IDatabaseConnector getDBConnector() {
		if (null == dbNeocomConnector) dbNeocomConnector = new NeocomDatabaseConnector();
		return dbNeocomConnector;
	}

	@Override
	public INeoComModelStore getModelStore() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getResourceString(int reference) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IConnector getSingleton() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IStorageConnector getStorageConnector() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean sdcardAvailable() {
		// TODO Auto-generated method stub
		return false;
	}
}

// - UNUSED CODE ............................................................................................
