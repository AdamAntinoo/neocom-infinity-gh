//	PROJECT:      Neocom.MarketDataService (NEOC-MKDS)
//	AUTHORS:      Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:	SpringBoot-MS-Java 1.8.
//	DESCRIPTION:	This project contains a MicroService specially dedicated to get and store the Market Data
//								information. I should be exposed on a new port and should be accesible to all the backend MS to consult 
//								Item Market Data.
package org.dimensinfin.eveonline.neocom.connector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.dimensinfin.eveonline.neocom.commands.SearchLocationCommand;
import org.dimensinfin.eveonline.neocom.enums.ELocationType;
import org.dimensinfin.eveonline.neocom.model.EveLocation;

// - CLASS IMPLEMENTATION ...................................................................................
public class MDSCCPDatabaseConnector extends CCPDatabaseConnector implements ICCPDatabaseConnector {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger				logger																= Logger.getLogger("MDSCCPDatabaseConnector");

	// - F I E L D   I N D E X   D E F I N I T I O N S
	private static int					LOCATIONBYID_SYSTEMID_CONINDEX				= 5;
	private static int					LOCATIONBYID_SYSTEM_CONINDEX					= 6;
	private static int					LOCATIONBYID_LOCATIONNAME_CONINDEX		= 3;
	private static int					LOCATIONBYID_CONSTELLATIONID_CONINDEX	= 7;
	private static int					LOCATIONBYID_CONSTELLATION_CONINDEX		= 8;
	private static int					LOCATIONBYID_REGIONID_CONINDEX				= 9;
	private static int					LOCATIONBYID_REGION_CONINDEX					= 10;
	private static int					LOCATIONBYID_TYPEID_CONINDEX					= 2;
	private static int					LOCATIONBYID_LOCATIONID_CONINDEX			= 1;
	private static int					LOCATIONBYID_SECURITY_CONINDEX				= 4;

	// - S Q L   C O M M A N D S
	private static final String	SELECT_LOCATIONBYID										= "SELECT md.itemID AS locationID, md.typeID AS typeID, md.itemName AS locationName, md.security AS security"
			+ " , IFNULL(md.solarSystemID, -1) AS systemID, ms.solarSystemName AS system"
			+ " , IFNULL(md.constellationID, -1) AS constellationID, mc.constellationName AS constellation"
			+ " , IFNULL(md.regionID, -1) AS regionID, mr.regionName AS region" + " FROM mapDenormalize md"
			+ " LEFT OUTER JOIN mapRegions mr ON mr.regionID = md.regionID"
			+ " LEFT OUTER JOIN mapConstellations mc ON mc.constellationID = md.constellationID"
			+ " LEFT OUTER JOIN mapSolarSystems ms ON ms.solarSystemID = md.solarSystemID" + " WHERE itemID = ?";

	// - F I E L D - S E C T I O N ............................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................

	// - M E T H O D - S E C T I O N ..........................................................................
	@Override
	public EveLocation searchLocationbyID(final long locationID) {
		SearchLocationCommand fallbackCommand = new SearchLocationCommand();
		fallbackCommand.setLocationId(locationID);
		return fallbackCommand.execute();
	}

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
	public EveLocation searchLocationbyID2(final long locationID) {
		logger.info(">< [MDSCCPDatabaseConnector.searchLocationbyID]> Searching ID: " + locationID);
		// First check if the location is already on the cache table.
		EveLocation hit = locationsCache.get(locationID);
		if (null != hit) {
			int access = locationsCacheStatistics.accountAccess(true);
			int hits = locationsCacheStatistics.getHits();
			logger.info(">< [MDSCCPDatabaseConnector.searchLocationbyID]> [HIT-" + hits + "/" + access + "] Location "
					+ locationID + " found at cache.");
			return hit;
		} else {
			// Try to get that id from the cache tables
			int access = locationsCacheStatistics.accountAccess(false);
			List<EveLocation> locationList = new ArrayList();
			//			try {
			//				Dao<EveLocation, String> locationDao = ModelAppConnector.getSingleton().getDBConnector().getLocationDAO();
			//				QueryBuilder<EveLocation, String> queryBuilder = locationDao.queryBuilder();
			//				Where<EveLocation, String> where = queryBuilder.where();
			//				where.eq("id", locationID);
			//				PreparedQuery<EveLocation> preparedQuery = queryBuilder.prepare();
			//				locationList = locationDao.query(preparedQuery);
			//			} catch (java.sql.SQLException sqle) {
			//				sqle.printStackTrace();
			//				return new EveLocation(locationID);
			//			}

			// Check list contents. If found we have the location. Else then check if Office
			if (locationList.size() < 1) {
				//				 logger.info(
				//						"-- [ searchLocationbyID]> Location: " + locationID + " not found on local Database.");
				// Offices
				long fixedLocationID = locationID;
				if (fixedLocationID >= 66000000) {
					if (fixedLocationID < 66014933) {
						fixedLocationID = fixedLocationID - 6000001;
					} else {
						fixedLocationID = fixedLocationID - 6000000;
					}
				}
				hit = new EveLocation(fixedLocationID);
				ResultSet cursor = null;
				try {
					PreparedStatement prepStmt = this.getCCPDatabase().prepareStatement(SELECT_LOCATIONBYID);
					prepStmt.setString(1, Long.valueOf(fixedLocationID).toString());
					cursor = prepStmt.executeQuery();
					if (null != cursor) {
						boolean detected = false;
						if (cursor.next()) {
							detected = true;
							//							 logger.info(
							//									"-- [ searchLocationbyID]> Location: " + locationID + " Obtained from CCP data.");
							// Check returned values when doing the assignments.
							long fragmentID = cursor.getLong(LOCATIONBYID_SYSTEMID_CONINDEX);
							if (fragmentID > 0) {
								hit.setSystemID(fragmentID);
								hit.setSystem(cursor.getString(LOCATIONBYID_SYSTEM_CONINDEX));
							} else {
								hit.setSystem(cursor.getString(LOCATIONBYID_LOCATIONNAME_CONINDEX));
							}
							fragmentID = cursor.getLong(LOCATIONBYID_CONSTELLATIONID_CONINDEX);
							if (fragmentID > 0) {
								hit.setConstellationID(fragmentID);
								hit.setConstellation(cursor.getString(LOCATIONBYID_CONSTELLATION_CONINDEX));
							}
							fragmentID = cursor.getLong(LOCATIONBYID_REGIONID_CONINDEX);
							if (fragmentID > 0) {
								hit.setRegionID(fragmentID);
								hit.setRegion(cursor.getString(LOCATIONBYID_REGION_CONINDEX));
							}
							hit.setTypeID(ELocationType.CCPLOCATION);
							hit.setStation(cursor.getString(LOCATIONBYID_LOCATIONNAME_CONINDEX));
							hit.setLocationID(cursor.getLong(LOCATIONBYID_LOCATIONID_CONINDEX));
							hit.setSecurity(cursor.getString(LOCATIONBYID_SECURITY_CONINDEX));
							// Update the final ID
							hit.getID();

							// Location found on CCP database.
							int hits = locationsCacheStatistics.getHits();
							logger.info(">< [MDSCCPDatabaseConnector.searchLocationbyID]> [HIT-" + hits + "/" + access + "] Location "
									+ locationID + " found at CCP Database.");
							locationsCache.put(hit.getID(), hit);
						}
						if (!detected) {
							logger.info("-- [MDSCCPDatabaseConnector.searchLocationbyID]> Location: " + locationID
									+ " not found on any Database - UNKNOWN-.");
							hit.setSystem("ID>" + Long.valueOf(locationID).toString());
						}
					}
				} catch (final Exception ex) {
					logger.warning(
							"W- [MDSCCPDatabaseConnector.searchLocationbyID]> Location <" + fixedLocationID + "> not found.");
				} finally {
					try {
						cursor.close();
					} catch (SQLException ex) {
						// TODO Auto-generated catch block
						ex.printStackTrace();
					}
				}
				// If the location is not cached nor in the CCP database. Return default location
				return hit;
			} else {
				// Location found on the Application database.
				int hits = locationsCacheStatistics.getHits();
				logger.info(">< [MDSCCPDatabaseConnector.searchLocationbyID]> [HIT-" + hits + "/" + access + "] Location "
						+ locationID + " found at Application Database.");
				EveLocation foundLoc = locationList.get(0);
				locationsCache.put(foundLoc.getID(), foundLoc);
				return locationList.get(0);
			}
		}
	}
}
// - UNUSED CODE ............................................................................................
