//  PROJECT:     NeoCom.Infinity(NCI.A6)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018-2019 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Angular 6.1
//  DESCRIPTION: Angular source code to run on a web server almost the same code as on the Android platform.
//               The project has 3 clear parts. One is the Java libraries that are common for all platforms,
//               the second is the java microservices that compose the web application backend made with
//               SpringBoot technology and finally the web ui code made in typescript within the Angular
//               framework.
export const environment = {
  name: require('../../package.json').name,
  version: require('../../package.json').version + " dev",
  production: false,
  development: true,
  mockStatus: true,
  showexceptions: true,
  // serverName: "https://backcitas.herokuapp.com",
  serverName: "",
  apiVersion1: "/api/v1",
  apiVersion2: "/api/v2",
  //--- C O N S T A N T S
  TOKEN_KEY: "-TOKEN_KEY-"
};
