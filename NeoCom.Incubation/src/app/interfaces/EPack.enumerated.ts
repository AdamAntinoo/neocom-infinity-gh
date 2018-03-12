//  PROJECT:     NeoCom.WS (NEOC.WS)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Angular 4
//  DESCRIPTION: Angular source code to run on a web server almost the same code as on the Android platform.
//               The project has 3 clear parts. One is the Java libraries that are common for all platforms,
//               the second is the java microservices that compose the web application backend made with
//               SpringBoot technology and finally the web ui code made in typescript within the Angular
//               framework.
/**
This declares the types of colored panels that are defined. Used by the Color themes to set a group of visual properties.
*/
export enum EThemeSelector {
  RED, ORANGE, YELLOW, GREEN, BLUE, WHITE
}

/**
Declares the types of Separators. They are an extension of the Color Theme decause some of them carry some functionality.
*/
export enum ESeparator {
  RED, ORANGE, YELLOW, GREEN, BLUE, WHITE, EMPTY, SPINNER
}
export enum EVariant {
  DEFAULT, CREDENTIALLIST, LOGINLIST, PILOTDETAILS, PILOTROASTER, PILOTMANAGERS, ASSETSMANAGER, PLANETARYMANAGER, PLANETARYOPTIMIZATION,
  FITTINGLIST, FITTING_ACTIONS_BYCLASS
}
