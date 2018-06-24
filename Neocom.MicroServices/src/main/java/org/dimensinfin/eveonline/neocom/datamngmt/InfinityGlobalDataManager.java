//  PROJECT:     NeoCom.Microservices (NEOC.MS)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Java 1.8 / SpringBoot-1.3.5 / Angular 5.0
//  DESCRIPTION: This is the SpringBoot MicroServices module to run the backend services to complete the web
//               application based on Angular+SB. This is the web version for the NeoCom Android native
//               application. Most of the source code is common to both platforms and this module includes
//               the source for the specific functionality for the backend services.
package org.dimensinfin.eveonline.neocom.datamngmt;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.dimensinfin.eveonline.neocom.NeoComMicroServiceApplication;
import org.dimensinfin.eveonline.neocom.database.entity.Credential;
import org.dimensinfin.eveonline.neocom.database.entity.FittingRequest;
import org.dimensinfin.eveonline.neocom.esiswagger.model.GetAlliancesAllianceIdOk;
import org.dimensinfin.eveonline.neocom.esiswagger.model.GetCharactersCharacterIdClonesOk;
import org.dimensinfin.eveonline.neocom.esiswagger.model.GetCharactersCharacterIdOk;
import org.dimensinfin.eveonline.neocom.esiswagger.model.GetCorporationsCorporationIdOk;
import org.dimensinfin.eveonline.neocom.exception.JsonExceptionInstance;
import org.dimensinfin.eveonline.neocom.exception.NEOE;
import org.dimensinfin.eveonline.neocom.exception.NeoComRegisteredException;
import org.dimensinfin.eveonline.neocom.industry.Fitting;
import org.dimensinfin.eveonline.neocom.model.AllianceV1;
import org.dimensinfin.eveonline.neocom.model.CorporationV1;
import org.dimensinfin.eveonline.neocom.model.PilotV2;
import org.dimensinfin.eveonline.neocom.database.entity.Property;

/**
 * This is the Infinity special extension to get .
 * <p>
 * The initial release will start transferring the ModelFactory functionality.
 *
 * @author Adam Antinoo
 */

// - CLASS IMPLEMENTATION ...................................................................................
public class InfinityGlobalDataManager extends GlobalDataManager {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = LoggerFactory.getLogger("InfinityGlobalDataManager");

	//--- F I T T I N G S
	private static final HashMap<Integer, Fitting> fittingsCache = new HashMap<>();

	public static List<Fitting> accessFittings4Credential( final Credential credential ) {
		List<Fitting> fittings = null;
		try {
			logger.info(">> [InfinityGlobalDataManager.accessFittings4Credential]> Credential: {}", credential.getAccountName());
			// Get the list of Fittings for the specified credential and update them on the cache for easy search.
			fittings = GlobalDataManager.downloadFittings4Credential(credential);
			addFittings2Cache(fittings);
			return fittings;
		} finally {
			logger.info("<< [InfinityGlobalDataManager.accessFittings4Credential]> Fittings found: {}", fittings.size());
		}
	}

	public static Fitting searchFitting4Id( final int fittingIdentifier ) {
		logger.info(">< [InfinityGlobalDataManager.searchFitting4Id]> fittingIdentifier: {}", fittingIdentifier);
		return fittingsCache.get(fittingIdentifier);
	}

	protected static void addFittings2Cache( final List<Fitting> newfittings ) {
		logger.info(">> [InfinityGlobalDataManager.addFittings2Cache]");
		for (Fitting fit : newfittings) {
			fittingsCache.put(fit.getFittingId(), fit);
		}
		logger.info("<< [InfinityGlobalDataManager.addFittings2Cache]");
	}

	public static List<FittingRequest> accessCorporationFittingRequests( final int corporationId ) {
		logger.info(">> [InfinityGlobalDataManager.accessCorporationFittingRequests]> Corporation: {}", corporationId);
		try {
			return new GlobalDataManager().getNeocomDBHelper().getFittingRequestDao().queryForEq("corporationId", corporationId);
		} catch (SQLException sqle) {
			return new ArrayList<>();
		} finally {
			logger.info("<< [InfinityGlobalDataManager.accessCorporationFittingRequests]");
		}
	}

	public static List<FittingRequest> accessPilotFittingRequests( final int pilotId ) {
		logger.info(">> [InfinityGlobalDataManager.accessPilotFittingRequests]> Pilot: {}", pilotId);
		try {
			return new GlobalDataManager().getNeocomDBHelper().getFittingRequestDao().queryForEq("corporationId", pilotId);
		} catch (SQLException sqle) {
			return new ArrayList<>();
		} finally {
			logger.info("<< [InfinityGlobalDataManager.accessPilotFittingRequests]");
		}
	}

	public static String serializedException( final Exception exc ) {
		try {
			final String serialized = NeoComMicroServiceApplication.jsonMapper.writeValueAsString(exc);
			return serialized;
		} catch (JsonProcessingException jpe) {
			// Do manual serialization for the exception.
			return new JsonExceptionInstance(exc).toJson();
		}
	}
	// - F I E L D - S E C T I O N ............................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................

	// - M E T H O D - S E C T I O N ..........................................................................
}
// - UNUSED CODE ............................................................................................
//[01]
