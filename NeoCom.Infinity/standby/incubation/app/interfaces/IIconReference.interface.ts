//  PROJECT:     NeoCom.WS (NEOC.WS)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Angular 4
//  DESCRIPTION: Angular source code to run on a web server almost the same code as on the Android platform.
//               The project has 3 clear parts. One is the Java libraries that are common for all platforms,
//               the second is the java microservices that compose the web application backend made with
//               SpringBoot technology and finally the web ui code made in typescript within the Angular
//               framework.
export interface IIconReference {
  getReference(): string;
}
export interface IGroupIconReference {
  getReference(): string;
}
export class URLGroupIconReference implements IIconReference {
  private static FITTING_SHIP_URL_BASE = "http://image.eveonline.com/Type/";

  constructor(private iconType: number) { }
  public getReference(): string {
    return URLGroupIconReference.FITTING_SHIP_URL_BASE + this.iconType + "_64.png";
  }
}
export class AssetGroupIconReference implements IIconReference {
  private static FITTING_SHIP_ASSET_LOCATION: string = "/assets/res-fitting/drawable/";

  constructor(private iconName: string) { }
  public getReference(): string {
    return AssetGroupIconReference.FITTING_SHIP_ASSET_LOCATION + this.iconName.toLowerCase() + ".png";
  }
}
export class IndustryIconReference implements IIconReference {
  private static INDUSTRY_ASSET_LOCATION: string = "/assets/res-industry/drawable/";

  constructor(private iconName: string) { }
  public getReference(): string {
    return IndustryIconReference.INDUSTRY_ASSET_LOCATION + this.iconName.toLowerCase() + ".png";
  }
}
export class UIIconReference implements IIconReference {
  private static UI_ASSET_LOCATION: string = "/assets/res-window/drawable/";

  constructor(private iconName: string) { }
  public getReference(): string {
    return UIIconReference.UI_ASSET_LOCATION + this.iconName.toLowerCase() + ".png";
  }
}
