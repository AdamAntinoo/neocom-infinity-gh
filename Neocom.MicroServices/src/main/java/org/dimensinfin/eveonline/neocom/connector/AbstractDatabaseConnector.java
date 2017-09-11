//	PROJECT:      NeoCom.Databases (NEOC.D)
//	AUTHORS:      Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:    (c) 2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:	Java 1.8 Library.
//	DESCRIPTION:	SQLite database access library. Isolates Neocom database access from any
//								environment limits.
package org.dimensinfin.eveonline.neocom.connector;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.dimensinfin.eveonline.neocom.enums.EMarketSide;
import org.dimensinfin.eveonline.neocom.industry.Job;
import org.dimensinfin.eveonline.neocom.industry.Resource;
import org.dimensinfin.eveonline.neocom.market.MarketDataSet;
import org.dimensinfin.eveonline.neocom.model.EveItem;
import org.dimensinfin.eveonline.neocom.model.EveLocation;
import org.dimensinfin.eveonline.neocom.model.NeoComAsset;

import com.j256.ormlite.dao.Dao;

// - CLASS IMPLEMENTATION ...................................................................................
public abstract class AbstractDatabaseConnector implements IDatabaseConnector {
	// - S T A T I C - S E C T I O N ..........................................................................
	protected static Logger logger = Logger.getLogger("AbstractDatabaseConnector");

	// - F I E L D - S E C T I O N ............................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public AbstractDatabaseConnector() {
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	public boolean checkInvention(int typeID) {
		throw new RuntimeException(
				"Application connector not defined. Functionality 'checkExpiration' disabled. Call intercepted by abstract class 'AbstractDatabaseConnector'.");
	}

	public boolean checkManufacturable(int typeid) {
		throw new RuntimeException(
				"Application connector not defined. Functionality 'checkExpiration' disabled. Call intercepted by abstract class 'AbstractDatabaseConnector'.");
	}

	public void closeDatabases() {
		throw new RuntimeException(
				"Application connector not defined. Functionality 'checkExpiration' disabled. Call intercepted by abstract class 'AbstractDatabaseConnector'.");
	}

	public Dao<Job, String> getJobDAO() throws SQLException {
		throw new RuntimeException(
				"Application connector not defined. Functionality 'checkExpiration' disabled. Call intercepted by abstract class 'AbstractDatabaseConnector'.");
	}

	public boolean openAppDataBase() {
		throw new RuntimeException(
				"Application connector not defined. Functionality 'openAppDataBase' disabled. Call intercepted by abstract class 'AbstractDatabaseConnector'.");
	}

	public boolean openCCPDataBase() {
		throw new RuntimeException(
				"Application connector not defined. Functionality 'openCCPDataBase' disabled. Call intercepted by abstract class 'AbstractDatabaseConnector'.");
	}

	public boolean openDAO() {
		throw new RuntimeException(
				"Application connector not defined. Functionality 'openDAO' disabled. Call intercepted by abstract class 'AbstractDatabaseConnector'.");
	}

	public int queryBlueprintDependencies(int bpitemID) {
		throw new RuntimeException(
				"Application connector not defined. Functionality 'queryBlueprintDependencies' disabled. Call intercepted by abstract class 'AbstractDatabaseConnector'.");
	}

	public ArrayList<Resource> refineOre(int itemID) {
		throw new RuntimeException(
				"Application connector not defined. Functionality 'refineOre' disabled. Call intercepted by abstract class 'AbstractDatabaseConnector'.");
	}

	public void replaceJobs(long characterID) {
		throw new RuntimeException(
				"Application connector not defined. Functionality 'replaceJobs' disabled. Call intercepted by abstract class 'AbstractDatabaseConnector'.");
	}

	public ArrayList<NeoComAsset> searchAsset4Type(long characterID, int typeID) {
		// TODO Auto-generated method stub
		return null;
	}

	public NeoComAsset searchAssetByID(long parentAssetID) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<NeoComAsset> searchAssetContainedAt(long pilotID, long assetID) {
		// TODO Auto-generated method stub
		return null;
	}

	public int searchBlueprint4Module(final int moduleID) {
		throw new RuntimeException(
				"Application connector not defined. Functionality 'searchBlueprint4Module' disabled. Call intercepted by abstract class 'AbstractDatabaseConnector'.");
	}

	public ArrayList<Integer> searchInventionableBlueprints(String resourceIDs) {
		throw new RuntimeException(
				"Application connector not defined. Functionality 'searchInventionableBlueprints' disabled. Call intercepted by abstract class 'AbstractDatabaseConnector'.");
	}

	public int searchInventionProduct(int typeID) {
		throw new RuntimeException(
				"Application connector not defined. Functionality 'searchInventionProduct' disabled. Call intercepted by abstract class 'AbstractDatabaseConnector'.");
	}

	public EveItem searchItembyID(int typeID) {
		throw new RuntimeException(
				"Application connector not defined. Functionality 'searchItembyID' disabled. Call intercepted by abstract class 'AbstractDatabaseConnector'.");
	}

	public ArrayList<Job> searchJob4Class(long characterID, String string) {
		throw new RuntimeException(
				"Application connector not defined. Functionality 'searchJob4Class' disabled. Call intercepted by abstract class 'AbstractDatabaseConnector'.");
	}

	public int searchJobExecutionTime(final int typeID, final int activityID) {
		throw new RuntimeException(
				"Application connector not defined. Functionality 'searchJobExecutionTime' disabled. Call intercepted by abstract class 'AbstractDatabaseConnector'.");
	}

	public ArrayList<Resource> searchListOfDatacores(final int itemID) {
		throw new RuntimeException(
				"Application connector not defined. Functionality 'searchListOfDatacores' disabled. Call intercepted by abstract class 'AbstractDatabaseConnector'.");
	}

	public ArrayList<Resource> searchListOfMaterials(int itemID) {
		throw new RuntimeException(
				"Application connector not defined. Functionality 'searchListOfMaterials' disabled. Call intercepted by abstract class 'AbstractDatabaseConnector'.");
	}

	public ArrayList<Resource> searchListOfReaction(int itemID) {
		throw new RuntimeException(
				"Application connector not defined. Functionality 'searchListOfReaction' disabled. Call intercepted by abstract class 'AbstractDatabaseConnector'.");
	}

	public EveLocation searchLocationbyID(long locationID) {
		throw new RuntimeException(
				"Application connector not defined. Functionality 'searchLocationbyID' disabled. Call intercepted by abstract class 'AbstractDatabaseConnector'.");
	}

	public EveLocation searchLocationBySystem(String system) {
		throw new RuntimeException(
				"Application connector not defined. Functionality 'searchLocationBySystem' disabled. Call intercepted by abstract class 'AbstractDatabaseConnector'.");
	}

	public MarketDataSet searchMarketData(int typeID, EMarketSide side) {
		throw new RuntimeException(
				"Application connector not defined. Functionality 'searchMarketData' disabled. Call intercepted by abstract class 'AbstractDatabaseConnector'.");
	}

	public int searchModule4Blueprint(int bpitemID) {
		throw new RuntimeException(
				"Application connector not defined. Functionality 'searchModule4Blueprint' disabled. Call intercepted by abstract class 'AbstractDatabaseConnector'.");
	}

	public int searchReactionOutputMultiplier(int itemID) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int searchStationType(long systemID) {
		throw new RuntimeException(
				"Application connector not defined. Functionality 'searchStationType' disabled. Call intercepted by abstract class 'AbstractDatabaseConnector'.");
	}

	public String searchTech4Blueprint(int blueprintID) {
		throw new RuntimeException(
				"Application connector not defined. Functionality 'searchTech4Blueprint' disabled. Call intercepted by abstract class 'AbstractDatabaseConnector'.");
	}

}

// - UNUSED CODE ............................................................................................
