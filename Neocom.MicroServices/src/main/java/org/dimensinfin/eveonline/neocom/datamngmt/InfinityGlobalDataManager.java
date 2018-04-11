//  PROJECT:     NeoCom.Microservices (NEOC.MS)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Java 1.8 / SpringBoot-1.3.5 / Angular 5.0
//  DESCRIPTION: This is the SpringBoot MicroServices module to run the backend services to complete the web
//               application based on Angular+SB. This is the web version for the NeoCom Android native
//               application. Most of the source code is common to both platforms and this module includes
//               the source for the specific functionality for the backend services.
package org.dimensinfin.eveonline.neocom.datamngmt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.dimensinfin.eveonline.neocom.database.entity.Credential;
import org.dimensinfin.eveonline.neocom.esiswagger.model.GetAlliancesAllianceIdOk;
import org.dimensinfin.eveonline.neocom.esiswagger.model.GetCharactersCharacterIdOk;
import org.dimensinfin.eveonline.neocom.esiswagger.model.GetCorporationsCorporationIdOk;
import org.dimensinfin.eveonline.neocom.model.AllianceV1;
import org.dimensinfin.eveonline.neocom.model.CorporationV1;
import org.dimensinfin.eveonline.neocom.model.PilotV2;

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

	// --- M O D E L - S T O R E   I N T E R F A C E
	//--- ALLIANCE
//	public static AllianceV1 reachAllianceV1( final int identifier, final SessionContext context ) {
		public static AllianceV1 reachAllianceV1( final Credential credential ) {
		logger.info(">> [InfinityGlobalDataManager.reachAllianceV1]> Identifier: {}", credential.getAccountId());
		try {
//			// Check if this request is already available on the cache.
//			final ICollaboration hit = modelCache.access(EModelVariants.ALLIANCEV1, identifier);
//			if (null == hit) {
//				logger.info("-- [InfinityGlobalDataManager.reachAllianceV1]> Instance not found at cache. Downloading Alliance <{}> info.",
//						identifier);
				final AllianceV1 newalliance = new AllianceV1();
				// Get the credential from the Store.
//				final Credential credential = context.getCredential();

				// Corporation information.
				logger.info("-- [InfinityGlobalDataManager.reachAllianceV1]> ESI Compatible. Download corporation information.");
				final GetAlliancesAllianceIdOk publicData = ESINetworkManager.getAlliancesAllianceId(Long.valueOf(credential.getAccountId()).intValue()
						, credential.getRefreshToken()
						, SERVER_DATASOURCE);
				newalliance.setPublicData(publicData);
				return newalliance;
//			} else {
//				logger.info("-- [InfinityGlobalDataManager.reachAllianceV1]> Alliance <{}> found at cache.", identifier);
//				return (AllianceV1) hit;
//			}
		} finally {
			logger.info("<< [InfinityGlobalDataManager.reachAllianceV1]");
		}
	}

	//--- CORPORATION
//	public static CorporationV1 reachCorporationV1( final int identifier, final SessionContext context ) {
		public static CorporationV1 reachCorporationV1( Credential credential ) {
		logger.info(">> [InfinityGlobalDataManager.reachCorporationV1]> Identifier: {}", credential.getAccountId());
		try {
			// Check if this request is already available on the cache.
//			final ICollaboration hit = modelCache.access(EModelVariants.CORPORATIONV1, identifier);
//			if (null == hit) {
//				logger.info("-- [InfinityGlobalDataManager.reachCorporationV1]> Instance not found at cache. Downloading Corporation <{}> info.",identifier);
				final CorporationV1 newcorp = new CorporationV1();
				// Get the credential from the Session.
//				final Credential credential = context.getCredential();

				// Corporation information.
				logger.info("-- [InfinityGlobalDataManager.reachCorporationV1]> ESI Compatible. Download corporation information.");
				final GetCorporationsCorporationIdOk publicData = ESINetworkManager.getCorporationsCorporationId(Long.valueOf(credential.getAccountId()).intValue()
						, credential.getRefreshToken()
						, SERVER_DATASOURCE);
				newcorp.setPublicData(publicData);
				// Process the public data and get the referenced instances for the Corporation, race, etc.
				newcorp.setAlliance(InfinityGlobalDataManager.reachAllianceV1(credential));

				return newcorp;
//			} else {
//				logger.info("-- [InfinityGlobalDataManager.useCorporationV1]> Corporation <{}> found at cache.", identifier);
//				return (CorporationV1) hit;
//			}
		} finally {
			logger.info("<< [InfinityGlobalDataManager.reachCorporationV1]");
		}
	}

	//--- PILOT
	/**
	 * Construct a minimal implementation of a Pilot from the XML api. This will get deprecated soon but during
	 * some time It will be compatible and I will have a better view of what variants are being used.
	 * <p>
	 * Once the XML api is deprecated we implement the Pilot version 2. This will replace old data structures by its equivalents
	 * and also add new data and dependencies. This is the most up to date evolver version and comes from the Infinity requirements.
	 *
	 * @param credential current credential to be used for ESI authorization to access the server data.
	 * @return an instance of a PilotV2 class that has some of the required information to be shown on the ui at this
	 * point.
	 */
	public static PilotV2 requestPilotV2( final Credential credential ) {
		logger.info(">> [InfinityGlobalDataManager.requestPilotV2]> Identifier: {}", credential.getAccountId());
		try {
//			// Check if this request is already available on the cache.
//			final ICollaboration hit = modelCache.access(EModelVariants.PILOTV2, identifier);
//			if (null == hit) {
//				logger.info("-- [GlobalDataManager.getPilotV2]> Instance not found at cache. Downloading pilot <{}> info.", identifier);
				final PilotV2 newchar = new PilotV2();
				// Get the credential from the Store and check if this identifier has access to the XML api.
//				final Credential credential = context.getCredential();
//				if (null != credential) {
					logger.info("-- [InfinityGlobalDataManager.reachPilotV2]> Processing data with Credential <{}>.", credential.getAccountName());

					// Public information.
					logger.info("-- [InfinityGlobalDataManager.reachPilotV2]> ESI Compatible. Download public data information.");
					final GetCharactersCharacterIdOk publicData = ESINetworkManager.getCharactersCharacterId(credential.getAccountId()
							, credential.getRefreshToken()
							, SERVER_DATASOURCE);
					newchar.setPublicData(publicData);
					// TODO First checkpoint --------------------------------------
//					// Process the public data and get the referenced instances for the Corporation, race, etc.
//					newchar.setCorporation ( InfinityGlobalDataManager.reachCorporationV1(publicData.getCorporationId(),context))
//							.setAlliance (InfinityGlobalDataManager.reachAllianceV1(publicData.getAllianceId(),context))
//							.setRace (GlobalDataManager.searchSDERace(publicData.getRaceId(),context))
//							.setBloodline (GlobalDataManager.searchSDEBloodline(publicData.getBloodlineId(),context))
//							.setAncestry (GlobalDataManager.searchSDEAncestry(publicData.getAncestryId(),context));
//
//					// Clone data
//					logger.info("-- [InfinityGlobalDataManager.reachPilotV2]> ESI Compatible. Download clone information.");
//					final GetCharactersCharacterIdClonesOk cloneInformation = ESINetworkManager.getCharactersCharacterIdClones(Long.valueOf(identifier).intValue(), credential.getRefreshToken(), "tranquility");
//					if (null != cloneInformation) {
//						newchar.setCloneInformation(cloneInformation);
//						newchar.setHomeLocation(cloneInformation.getHomeLocation());
//					}
//
//					// Roles
//					// TODO To be implemented
//					// Register instance into the cache. Expiration time is about 3600 seconds.
//					try {
//						final Instant expirationTime = Instant.now().plus(TimeUnit.SECONDS.toMillis(3600));
//						modelCache.store(EModelVariants.PILOTV2, newchar, expirationTime, identifier);
//						// Store this same information on the database to record the TimeStamp.
//						final String reference = GlobalDataManager.constructModelStoreReference(GlobalDataManager.EDataUpdateJobs.CHARACTER_CORE, credential.getAccountId());
//						TimeStamp timestamp = getNeocomDBHelper().getTimeStampDao().queryForId(reference);
//						if (null == timestamp) timestamp = new TimeStamp(reference, expirationTime);
//						logger.info("-- [InfinityGlobalDataManager.reachPilotV2]> Updating character TimeStamp {}.", reference);
//						timestamp.setTimeStamp(expirationTime)
//								.setCredentialId(credential.getAccountId())
//								.store();
//					} catch (SQLException sqle) {
//						sqle.printStackTrace();
//					}
					// TODO End checkpoint --------------------------------------------
//				}
				return newchar;
//			} else {
//				logger.info("-- [InfinityGlobalDataManager.getPilotV2]> Pilot <{}> found at cache.", identifier);
//				return (PilotV2) hit;
//			}
		} finally {
			logger.info("<< [InfinityGlobalDataManager.reachPilotV2]");
		}
	}

	// - F I E L D - S E C T I O N ............................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................

	// - M E T H O D - S E C T I O N ..........................................................................
}
// - UNUSED CODE ............................................................................................
//[01]
