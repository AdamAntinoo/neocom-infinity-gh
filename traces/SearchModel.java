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
	public EsiLocation searchLocationbyID(final long locationID) {
		CCPDatabaseConnector.logger.info(">< [CCPDatabaseConnector.searchLocationbyID]> Searching ID: " + locationID);
		// First check if the location is already on the cache table.
		EsiLocation hit = locationsCache.get(locationID);
		if (null != hit) {
			int access = CCPDatabaseConnector.locationsCacheStatistics.accountAccess(true);
			int hits = CCPDatabaseConnector.locationsCacheStatistics.getHits();
			CCPDatabaseConnector.logger.info(">< [CCPDatabaseConnector.searchLocationbyID]> [HIT-" + hits + "/" + access
					+ "] Location " + locationID + " found at cache.");
			return hit;
		} else {
			// Try to get that id from the cache tables
			int access = CCPDatabaseConnector.locationsCacheStatistics.accountAccess(false);
			List<EsiLocation> locationList = null;
			try {
				Dao<EsiLocation, String> locationDao = ModelAppConnector.getSingleton().getDBConnector().getLocationDAO();
				QueryBuilder<EsiLocation, String> queryBuilder = locationDao.queryBuilder();
				Where<EsiLocation, String> where = queryBuilder.where();
				where.eq("id", locationID);
				PreparedQuery<EsiLocation> preparedQuery = queryBuilder.prepare();
				locationList = locationDao.query(preparedQuery);
			} catch (java.sql.SQLException sqle) {
				sqle.printStackTrace();
				return new EsiLocation(locationID);
			}

			// Check list contents. If found we have the location. Else then check if Office
			if (locationList.size() < 1) {
				//				CCPDatabaseConnector.logger.info(
				//						"-- [CCPDatabaseConnector.searchLocationbyID]> Location: " + locationID + " not found on local Database.");
				// Offices
				long fixedLocationID = locationID;
				if (fixedLocationID >= 66000000) {
					if (fixedLocationID < 66014933) {
						fixedLocationID = fixedLocationID - 6000001;
					} else {
						fixedLocationID = fixedLocationID - 6000000;
					}
				}
				hit = new EsiLocation(fixedLocationID);
				ResultSet cursor = null;
				try {
					PreparedStatement prepStmt = this.getCCPDatabase().prepareStatement(CCPDatabaseConnector.SELECT_LOCATIONBYID);
					prepStmt.setString(1, Long.valueOf(fixedLocationID).toString());
					cursor = prepStmt.executeQuery();
					if (null != cursor) {
						boolean detected = false;
						if (cursor.next()) {
							detected = true;
							//							CCPDatabaseConnector.logger.info(
							//									"-- [CCPDatabaseConnector.searchLocationbyID]> Location: " + locationID + " Obtained from CCP data.");
							// Check returned values when doing the assignments.
							long fragmentID = cursor.getLong(CCPDatabaseConnector.LOCATIONBYID_SYSTEMID_CONINDEX);
							if (fragmentID > 0) {
								hit.setSystemID(fragmentID);
								hit.setSystem(cursor.getString(CCPDatabaseConnector.LOCATIONBYID_SYSTEM_CONINDEX));
							} else {
								hit.setSystem(cursor.getString(CCPDatabaseConnector.LOCATIONBYID_LOCATIONNAME_CONINDEX));
							}
							fragmentID = cursor.getLong(CCPDatabaseConnector.LOCATIONBYID_CONSTELLATIONID_CONINDEX);
							if (fragmentID > 0) {
								hit.setConstellationID(fragmentID);
								hit.setConstellation(cursor.getString(CCPDatabaseConnector.LOCATIONBYID_CONSTELLATION_CONINDEX));
							}
							fragmentID = cursor.getLong(CCPDatabaseConnector.LOCATIONBYID_REGIONID_CONINDEX);
							if (fragmentID > 0) {
								hit.setRegionID(fragmentID);
								hit.setRegion(cursor.getString(CCPDatabaseConnector.LOCATIONBYID_REGION_CONINDEX));
							}
							hit.setTypeID(ELocationType.CCPLOCATION);
							hit.setStation(cursor.getString(CCPDatabaseConnector.LOCATIONBYID_LOCATIONNAME_CONINDEX));
							hit.setLocationID(cursor.getLong(CCPDatabaseConnector.LOCATIONBYID_LOCATIONID_CONINDEX));
							hit.setSecurity(cursor.getString(CCPDatabaseConnector.LOCATIONBYID_SECURITY_CONINDEX));
							// Update the final ID
							hit.getID();
							cursor.close();

							// Location found on CCP database.
							int hits = CCPDatabaseConnector.locationsCacheStatistics.getHits();
							CCPDatabaseConnector.logger.info(">< [CCPDatabaseConnector.searchLocationbyID]> [HIT-" + hits + "/"
									+ access + "] Location " + locationID + " found at CCP Database.");
							locationsCache.put(hit.getID(), hit);
						}
						if (!detected) {
							CCPDatabaseConnector.logger
									.info("-- [searchLocationbyID]> Location: " + locationID + " not found on any Database - UNKNOWN-.");
							hit.setSystem("ID>" + Long.valueOf(locationID).toString());
						}
					}
				} catch (final Exception ex) {
					CCPDatabaseConnector.logger.warning(
							"W- [AndroidDatabaseConnector.searchLocationbyID]> Location <" + fixedLocationID + "> not found.");
				} finally {
				}
				// If the location is not cached nor in the CCP database. Return default location
				return hit;
			} else {
				// Location found on the Application database.
				int hits = CCPDatabaseConnector.locationsCacheStatistics.getHits();
				CCPDatabaseConnector.logger.info(">< [CCPDatabaseConnector.searchLocationbyID]> [HIT-" + hits + "/" + access
						+ "] Location " + locationID + " found at Application Database.");
				EsiLocation foundLoc = locationList.get(0);
				locationsCache.put(foundLoc.getID(), foundLoc);
				return locationList.get(0);
			}
		}
	}
