//  PROJECT:     NeoCom.WS (NEOC.WS)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Angular 4
//  DESCRIPTION: Angular source code to run on a web server almost the same code as on the Android platform.
//               The project has 3 clear parts. One is the Java libraries that are common for all platforms,
//               the second is the java microservices that compose the web application backend made with
//               SpringBoot technology and finally the web ui code made in typescript within the Angular
//               framework.
//--- CORE
// import { Observable } from 'rxjs/Rx';
// // Import RxJs required methods
// import 'rxjs/add/operator/map';
// import 'rxjs/add/operator/catch';
// //--- SERVICES
// import { AppModelStoreService } from 'app/services/app-model-store.service';
// //--- INTERFACES
// import { EVariant } from 'app/interfaces/EPack.enumerated';
// import { ESeparator } from 'app/interfaces/EPack.enumerated';
// //--- MODELS
// import { NeoComNode } from 'app/models/NeoComNode.model';


export class ESIConfigurationS {
  //--- SINGULARITY CREDENTIALS
  public static AUTHORIZATION_SERVER = "https://sisilogin.testeveonline.com/";
  public static CLIENT_ID = "ddfafe24dcdb43e3ae964bf580636172";
  public static SECRET_KEY = "kKWgfyzBVs2ra0wn8Hcwto0llWoeUgWz1P1FkPlb";
  public static CALLBACK = "http://localhost:4200/validateauthorization";
  public static CONTENT_TYPE = "application/json";
  public static PECK = "ZWY2ODI5OGQ1ODJjNGRmZWJiNjc4ODZlMzBkMDg4YTg6emdQZnhPNjNRUFIyZUgxMXJOcGdQQUwwSjJtQjZmWko3SFFDcmkwcQ==";
  // public static SCOPE = "publicData esi-location.read_location.v1 esi-location.read_ship_type.v1 esi-mail.organize_mail.v1 esi-skills.read_skills.v1 esi-skills.read_skillqueue.v1 esi-wallet.read_character_wallet.v1 esi-characters.read_contacts.v1 esi-search.search_structures.v1 esi-clones.read_clones.v1 esi-universe.read_structures.v1 esi-killmails.read_killmails.v1 esi-assets.read_assets.v1 esi-planets.manage_planets.v1 esi-markets.structure_markets.v1 esi-characters.read_corporation_roles.v1 esi-location.read_online.v1 esi-fleets.read_fleet.v1 esi-ui.open_window.v1 esi-ui.write_waypoint.v1 esi-fittings.read_fittings.v1  esi-characters.read_standings.v1 esi-industry.read_character_jobs.v1 esi-markets.read_character_orders.v1 esi-characters.read_blueprints.v1 esi-contracts.read_character_contracts.v1 esi-clones.read_implants.v1 esi-characters.read_fatigue.v1 esi-characters.read_notifications.v1 esi-industry.read_character_mining.v1 esi-characters.read_fw_stats.v1";
  // public static SCOPE = "publicData esi-location.read_location.v1 esi-location.read_ship_type.v1 esi-wallet.read_character_wallet.v1 esi-clones.read_clones.v1 esi-universe.read_structures.v1 esi-assets.read_assets.v1 esi-planets.manage_planets.v1 esi-characters.read_corporation_roles.v1 esi-location.read_online.v1 esi-fleets.read_fleet.v1 esi-fittings.read_fittings.v1  esi-characters.read_standings.v1 esi-industry.read_character_jobs.v1 esi-markets.read_character_orders.v1 esi-characters.read_blueprints.v1 esi-contracts.read_character_contracts.v1 esi-clones.read_implants.v1 esi-industry.read_character_mining.v1";
  // public static SCOPE = "publicData esi-location.read_location.v1 esi-location.read_ship_type.v1 esi-wallet.read_character_wallet.v1 esi-assets.read_assets.v1 esi-planets.manage_planets.v1 esi-fittings.read_fittings.v1 esi-industry.read_character_jobs.v1 esi-markets.read_character_orders.v1 esi-characters.read_blueprints.v1 esi-contracts.read_character_contracts.v1 esi-clones.read_implants.v1 esi-industry.read_character_mining.v1";
  public static SCOPE = "esi-assets.read_assets.v1 esi-industry.read_character_jobs.v1 esi-planets.manage_planets.v1 esi-contracts.read_character_contracts.v1 esi-fittings.read_fittings.v1";
  public static AUTHORIZE_URL = ESIConfigurationS.AUTHORIZATION_SERVER + "oauth/authorize";
  public static ACCESS_TOKEN_RESOURCE = ESIConfigurationS.AUTHORIZATION_SERVER + "oauth/token";
  public static passphrase = "The access to Singularity is open for all developers.";
}
