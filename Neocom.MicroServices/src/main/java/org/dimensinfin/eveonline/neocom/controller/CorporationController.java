//  PROJECT:     NeoCom.Microservices (NEOC.MS)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Java 1.8 / SpringBoot-1.3.5 / Angular 5.0
//  DESCRIPTION: This is the SpringBoot MicroServices module to run the backend services to complete the web
//               application based on Angular+SB. This is the web version for the NeoCom Android native
//               application. Most of the source code is common to both platforms and this module includes
//               the source for the specific functionality for the backend services.
package org.dimensinfin.eveonline.neocom.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.dimensinfin.eveonline.neocom.constant.ModelWideConstants;
import org.dimensinfin.eveonline.neocom.datamngmt.ESINetworkManager;
import org.dimensinfin.eveonline.neocom.datamngmt.GlobalDataManager;
import org.dimensinfin.eveonline.neocom.datamngmt.InfinityGlobalDataManager;
import org.dimensinfin.eveonline.neocom.entities.NeoComAsset;
import org.dimensinfin.eveonline.neocom.enums.ELocationType;
import org.dimensinfin.eveonline.neocom.esiswagger.model.GetCorporationsCorporationIdAssets200Ok;
import org.dimensinfin.eveonline.neocom.exception.NeoComRegisteredException;
import org.dimensinfin.eveonline.neocom.interfaces.ILocatableAsset;
import org.dimensinfin.eveonline.neocom.model.EveItem;
import org.dimensinfin.eveonline.neocom.model.EveLocation;
import org.dimensinfin.eveonline.neocom.model.PilotV2;
import org.dimensinfin.eveonline.neocom.security.SessionManager;

/**
 * @author Adam Antinoo
 */
// - CLASS IMPLEMENTATION ...................................................................................
@RestController
public class CorporationController {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = LoggerFactory.getLogger("CorporationController");

	// - F I E L D - S E C T I O N ............................................................................
	private final List<NeoComAsset> unlocatedAssets = new ArrayList<>();

	// - M E T H O D - S E C T I O N ..........................................................................
	@CrossOrigin()
	@GetMapping("/api/v2/corporation/assets")
	public ResponseEntity<List<NeoComAsset>> corporationAssets( @RequestHeader(value = "xApp-Authentication", required = true) String
			                                                            _token ) {
		// --- C O N T R O L L E R   B O D Y
		// The headers should have the authorization data enough to retrieve the session.
		final SessionManager.AppSession session = SessionManager.retrieve(_token);
		if ( null != session ) {
			try {
				// Get the corporation identifier from the session credential.
				final PilotV2 pilotData = InfinityGlobalDataManager.requestPilotV2(session.getCredential());
				final int corporationIdentifier = pilotData.getCorporation().getCorporationId();
				// Go to the ESI api interface and get the list of assets or an error message if not allowed.
				final List<GetCorporationsCorporationIdAssets200Ok> assetOkList = ESINetworkManager.getCorporationsCorporationIdAssets(corporationIdentifier
						, session.getCredential().getRefreshToken(), InfinityGlobalDataManager.SERVER_DATASOURCE);

				// With the list of assets do the processing to get an adapted NeoCom asset.
				final List<NeoComAsset> assetResultList = new ArrayList<>();
				this.unlocatedAssets.clear();
				// Assets may be parent of other assets so process them recursively if the hierarchical mode is selected.
				for ( final GetCorporationsCorporationIdAssets200Ok assetOk : assetOkList ) {
					//--- A S S E T   P R O C E S S I N G
					try {
						// Convert the asset from the OK format to a MVC compatible structure.
						final NeoComAsset myasset = this.convert2AssetFromESI(assetOk);
						if ( myasset.getCategoryName().equalsIgnoreCase("Ship") ) {
							myasset.setAsShip(true);
						}
						if ( myasset.getCategoryName().equalsIgnoreCase("Blueprint") ) {
							myasset.setBlueprintType(assetOk.getQuantity());
						}
//						if ( myasset.isShip() ) {
//							downloadAssetEveName(myasset.getAssetId());
//						}
//						if ( myasset.isContainer() ) {
//							downloadAssetEveName(myasset.getAssetId());
//						}
						// Mark the asset owner to the work in progress value.
						myasset.setOwnerId(corporationIdentifier);
						assetResultList.add(myasset);
						// With assets separate the update from the creation because they use a generated unique key.
//						new GlobalDataManager().getNeocomDBHelper().getAssetDao().create(myasset);
//						logger.info("-- Wrote asset to database id [" + myasset.getAssetId() + "]");

						//--- L O C A T I O N   P R O C E S S I N G
						// Check the asset location. The location can be a known game station, a known user structure, another asset
						// or an unknown player structure. Check which one is this location.
						EveLocation targetLoc = new GlobalDataManager().searchLocation4Id(myasset.getLocationId());
						if ( targetLoc.getTypeId() == ELocationType.UNKNOWN ) {
							// Add this asset to the list of items to be reprocessed.
							this.unlocatedAssets.add(myasset);
						}
					} catch ( final RuntimeException rtex ) {
						logger.info("RTEX ´[AssetsManager.downloadPilotAssetsESI]> Processing asset: {} - {}"
								, assetOk.getItemId(), rtex.getMessage());
						rtex.printStackTrace();
					}
				}
				//--- O R P H A N   L O C A T I O N   A S S E T S
				// Second pass. All the assets in unknown locations should be readjusted for hierarchy changes.
				for ( NeoComAsset asset : this.unlocatedAssets ) {
					this.validateLocation(asset);
				}

				return new ResponseEntity<List<NeoComAsset>>(assetResultList, HttpStatus.OK);
			} catch ( NeoComRegisteredException ee ) {
				ee.printStackTrace();
				return new ResponseEntity<List<NeoComAsset>>(HttpStatus.BAD_REQUEST);
			}
		} else return new ResponseEntity<List<NeoComAsset>>(HttpStatus.UNAUTHORIZED);
	}

	// --- P R I V A T E   M E T H O D S
	private NeoComAsset convert2AssetFromESI( final GetCorporationsCorporationIdAssets200Ok asset200Ok ) {
		// Create the asset from the API asset.
		final NeoComAsset newAsset = new NeoComAsset(asset200Ok.getTypeId())
				.setAssetId(asset200Ok.getItemId());
		// TODO -- Location management is done outside this transformation. This is duplicated code.
		Long locid = asset200Ok.getLocationId();
		if ( null == locid ) {
			locid = (long) -2;
		}
		newAsset.setLocationId(locid)
				.setLocationType(asset200Ok.getLocationType())
				.setQuantity(asset200Ok.getQuantity())
				.setLocationFlag(asset200Ok.getLocationFlag())
				.setSingleton(asset200Ok.getIsSingleton());
		// Get access to the Item and update the copied fields.
		final EveItem item = new GlobalDataManager().searchItem4Id(newAsset.getTypeId());
		if ( null != item ) {
			newAsset.setName(item.getName());
			newAsset.setCategory(item.getCategoryName());
			newAsset.setGroupName(item.getGroupName());
			newAsset.setTech(item.getTech());
		}
		// Add the asset value to the database.
		newAsset.setIskValue(this.calculateAssetValue(newAsset));
		return newAsset;
	}

	private synchronized double calculateAssetValue( final NeoComAsset asset ) {
		// Skip blueprints from the value calculations
		double assetValueISK = 0.0;
		if ( null != asset ) {
			EveItem item = asset.getItem();
			if ( null != item ) {
				String category = item.getCategoryName();
				String group = item.getGroupName();
				if ( null != category ) if ( !category.equalsIgnoreCase(ModelWideConstants.eveglobal.Blueprint) ) {
					// Add the value and volume of the stack to the global result.
					long quantity = asset.getQuantity();
					double price = 0.0;
					try {
						// First try to set the average market price. If it fails search for the market data.
						price = asset.getItem().getPrice();
						if ( price < 0 )
							price = asset.getItem().getHighestBuyerPrice().getPrice();
					} catch ( ExecutionException ee ) {
						price = asset.getItem().getPrice();
					} catch ( InterruptedException ee ) {
						price = asset.getItem().getPrice();
					}
					assetValueISK = price * quantity;
				}
			}
		}
		return assetValueISK;
	}

	/**
	 * Checks if the Location can be found on the two lists of Locations, the CCP game list or the player
	 * compiled list. If the Location can't be found on any of those lists then it can be another asset
	 * (Container, Ship, etc) or another player/corporation structure resource that is not listed on the asset
	 * list.
	 */
	private ELocationType validateLocation( final ILocatableAsset locatable ) {
		long targetLocationid = locatable.getLocationId();
		EveLocation targetLoc = new GlobalDataManager().searchLocation4Id(targetLocationid);
		if ( targetLoc.getTypeId() == ELocationType.UNKNOWN ) {
			try {
				// Need to check if asset or unreachable location. Search for asset with locationid.
				List<NeoComAsset> targetList = new GlobalDataManager().getNeocomDBHelper().getAssetDao()
						.queryForEq("assetId", Long.valueOf(targetLocationid));
				NeoComAsset target = null;
				if ( targetList.size() > 0 ) target = targetList.get(0);
				if ( null == target )
					return ELocationType.UNKNOWN;
				else {
					// Change the asset parentship and update the asset location with the location of the parent.
					locatable.setParentId(targetLocationid);

					// Search recursively on the parentship chain until a leaf is found. Then check that location.
					long parentIdentifier = target.getParentContainerId();
					while ( parentIdentifier != -1 ) {
						validateLocation(target);
						targetList = new GlobalDataManager().getNeocomDBHelper().getAssetDao()
								.queryForEq("assetId", Long.valueOf(parentIdentifier));
						if ( targetList.size() > 0 ) target = targetList.get(0);
						parentIdentifier = target.getParentContainerId();
					}
					// Now target contains a parent with parentship -1.
					// Set to this asset the parent location whichever it is.
					locatable.setLocationId(target.getLocationId());
					locatable.setLocationType(target.getLocationType());
					locatable.setLocationFlag(target.getLocationFlag());
					locatable.store();
					return target.getLocation().getTypeId();
				}
			} catch ( SQLException sqle ) {
				return ELocationType.UNKNOWN;
			}
		} else
			return targetLoc.getTypeId();
	}
}

// - UNUSED CODE ............................................................................................
//[01]
