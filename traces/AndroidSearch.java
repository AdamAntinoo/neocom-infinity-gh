	/**
	 * Loads the system and other information for a location based on the ID received as a parameter. New
	 * implementation use this ID to calculate if this matches a corporation outpost or a corporation office
	 * hangar.
	 */
	public EsiLocation searchLocationbyID(final long locationID) {
		// Try to get that id from the cache tables
		try {
			Dao<EsiLocation, String> locationDao = NeoComAppConnector.getSingleton().getDBConnector().getLocationDAO();
			QueryBuilder<EsiLocation, String> queryBuilder = locationDao.queryBuilder();
			Where<EsiLocation, String> where = queryBuilder.where();
			where.eq("id", locationID);
			PreparedQuery<EsiLocation> preparedQuery = queryBuilder.prepare();
			List<EsiLocation> locationList = locationDao.query(preparedQuery);

			// Check list contents. If found we have the location. Else then check if Office
			if (locationList.size() < 1) {
				AndroidDatabaseConnector.logger
						.info("-- [searchLocationbyID]> Location: " + locationID + " not found on cache.");
				// Offices
				long fixedLocationID = locationID;
				if (fixedLocationID >= 66000000) {
					if (fixedLocationID < 66014933) {
						fixedLocationID = fixedLocationID - 6000001;
					} else {
						fixedLocationID = fixedLocationID - 6000000;
					}
				}
				EsiLocation hit = new EsiLocation(fixedLocationID);
				try {
					final Cursor cursor = this.getCCPDatabase().rawQuery(AndroidCCPDatabaseConnector.SELECT_LOCATIONBYID,
							new String[] { Long.valueOf(fixedLocationID).toString() });
					if (null != cursor) {
						boolean detected = false;
						if (cursor.moveToFirst()) {
							AndroidDatabaseConnector.logger
									.info("-- [searchLocationbyID]> Location: " + locationID + " Obtained from CCP data.");
							detected = true;
							// Check returned values when doing the assignments.
							long fragmentID = cursor.getLong(cursor.getColumnIndex("systemID"));
							if (fragmentID > 0) {
								hit.setSystemID(fragmentID);
								hit.setSystem(cursor.getString(cursor.getColumnIndex("system")));
							} else {
								hit.setSystem(cursor.getString(cursor.getColumnIndex("locationName")));
							}
							fragmentID = cursor.getLong(cursor.getColumnIndex("constellationID"));
							if (fragmentID > 0) {
								hit.setConstellationID(fragmentID);
								hit.setConstellation(cursor.getString(cursor.getColumnIndex("constellation")));
							}
							fragmentID = cursor.getLong(cursor.getColumnIndex("regionID"));
							if (fragmentID > 0) {
								hit.setRegionID(fragmentID);
								hit.setRegion(cursor.getString(cursor.getColumnIndex("region")));
							}
							hit.setTypeID(ELocationType.CCPLOCATION);
							hit.setStation(cursor.getString(cursor.getColumnIndex("locationName")));
							hit.setLocationID(cursor.getLong(cursor.getColumnIndex("locationID")));
							hit.setSecurity(cursor.getString(cursor.getColumnIndex("security")));
							cursor.close();
						}
						if (!detected) {
							AndroidDatabaseConnector.logger
									.info("-- [searchLocationbyID]> Location: " + locationID + " not found on CCP data.");
							hit.setSystem("ID>" + Long.valueOf(locationID).toString());
						}
					}
				} catch (final Exception ex) {
					AndroidDatabaseConnector.logger.warning(
							"W- [AndroidDatabaseConnector.searchLocationbyID]> Location <" + fixedLocationID + "> not found.");
				}
				// If the location is not cached nor in the CCP database. Return default location
				return hit;
			}
			return locationList.get(0);
		} catch (java.sql.SQLException sqle) {
			sqle.printStackTrace();
			return new EsiLocation(locationID);
		}
	}
