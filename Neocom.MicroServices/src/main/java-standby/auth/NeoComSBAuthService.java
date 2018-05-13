//  PROJECT:     NeoCom.Android (NEOC.A)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API22.
//  DESCRIPTION: Android Application related to the Eve Online game. The purpose is to download and organize
//               the game data to help capsuleers organize and prioritize activities. The strong points are
//               help at the Industry level tracking and calculating costs and benefits. Also the market
//               information update service will help to identify best prices and locations.
//               Planetary Interaction and Ship fittings are point under development.
//               ESI authorization is a new addition that will give continuity and allow download game data
//               from the new CCP data services.
//               This is the Android application version but shares libraries and code with other application
//               designed for Spring Boot Angular 4 platform.
//               The model management is shown using a generic Model View Controller that allows make the
//               rendering of the model data similar on all the platforms used.
package org.dimensinfin.eveonline.neocom.auth;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.oauth.OAuth20Service;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * @author Adam Antinoo
 */

// - CLASS IMPLEMENTATION ...................................................................................
public class NeoComSBAuthService {
	// - S T A T I C - S E C T I O N ..........................................................................
//	public static String CLIENT_ID = "ef68298d582c4dfebb67886e30d088a8";
//	public static String SECRET_KEY = "zgPfxO63QPR2eH11rNpgPAL0J2mB6fZJ7HQCri0q";
//	public static String CALLBACK = "http://localhost:4200/validateauthorization";
//	public static final String CONTENT_TYPE = "application/json";
//	public static final String PECK = "ZWY2ODI5OGQ1ODJjNGRmZWJiNjc4ODZlMzBkMDg4YTg6emdQZnhPNjNRUFIyZUgxMXJOcGdQQUwwSjJtQjZmWko3SFFDcmkwcQ==";

	// - F I E L D - S E C T I O N ............................................................................
	private OAuth20Service service;
	private OAuth2AccessToken accessToken;
	private String authorizationCode;

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public NeoComSBAuthService() {
		super();
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	public void createService () {
		service = new ServiceBuilder(CLIENT_ID)
				.apiKey(CLIENT_ID)
				.apiSecret(SECRET_KEY)
				.callback(CALLBACK)
				.scope("publicData characterStatsRead characterFittingsRead characterLocationRead characterWalletRead characterAssetsRead characterIndustryJobsRead characterMarketOrdersRead characterNotificationsRead characterResearchRead characterSkillsRead characterAccountRead characterClonesRead corporationWalletRead corporationAssetsRead corporationIndustryJobsRead corporationMarketOrdersRead corporationStructuresRead corporationContractsRead esi-location.read_location.v1 esi-location.read_ship_type.v1 esi-skills.read_skills.v1 esi-skills.read_skillqueue.v1 esi-wallet.read_character_wallet.v1 esi-wallet.read_corporation_wallet.v1 esi-search.search_structures.v1 esi-clones.read_clones.v1 esi-universe.read_structures.v1 esi-assets.read_assets.v1 esi-planets.manage_planets.v1 esi-fittings.read_fittings.v1 esi-markets.structure_markets.v1 esi-corporations.read_structures.v1 esi-markets.read_character_orders.v1 esi-characters.read_blueprints.v1 esi-characters.read_corporation_roles.v1 esi-contracts.read_character_contracts.v1 esi-clones.read_implants.v1 esi-wallet.read_corporation_wallets.v1 esi-characters.read_notifications.v1 esi-corporations.read_divisions.v1 esi-assets.read_corporation_assets.v1 esi-corporations.read_blueprints.v1 esi-contracts.read_corporation_contracts.v1 esi-corporations.read_starbases.v1 esi-industry.read_corporation_jobs.v1 esi-markets.read_corporation_orders.v1 esi-corporations.read_container_logs.v1 esi-industry.read_character_mining.v1 esi-industry.read_corporation_mining.v1 esi-planets.read_customs_offices.v1 esi-corporations.read_facilities.v1 esi-corporations.read_outposts.v1 esi-characterstats.read.v1")
				.state("NEOCOM-VERIFICATION-STATE")
				.build(NeoComAuthApi20.instance());
	}

	public String getAuthorizationUrl () {
		String authUrl = service.getAuthorizationUrl();
		return authUrl;
	}

	public OAuth2AccessToken getAccessToken (final String authorizationCode) throws InterruptedException, ExecutionException, IOException {
		this.authorizationCode = authorizationCode;
		return accessToken;
	}
}
// - UNUSED CODE ............................................................................................
//[01]
