//  PROJECT:     NeoCom.Angular (NEOC.A6)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Angular 6.0
//  DESCRIPTION: Angular source code to run on a web server almost the same code as on the Android platform.
//               The project has 3 clear parts. One is the Java libraries that are common for all platforms,
//               the second is the java microservices that compose the web application backend made with
//               SpringBoot technology and finally the web ui code made in typescript within the Angular
//               framework.

/*
 * Public API Surface of neocom-lib
 */
export * from './lib/neocom-lib.module';
//--- COMPONENTS
// export * from './lib/basepage.component';
// export * from './lib/components/node.component';
// export * from './lib/components/expandablenode.component';
// export * from './lib/components/centro/centro.component';
//--- INTERFACES
export * from './lib/interfaces/EPack.enumerated';
export * from './lib/interfaces/ICollaboration.interface';
export * from './lib/interfaces/INode.interface';
export * from './lib/interfaces/IViewer.interface';
export * from './lib/interfaces/INeoComNode.interface';
//--- MODELS
export * from './lib/models/conf/ESI.Singularity';
export * from './lib/models/conf/ESI.Tranquility';
export * from './lib/models/ui/ColorTheme.model';
export * from './lib/models/ui/NeoComSession.model';
export * from './lib/models/Alliance.model';
export * from './lib/models/Corporation.model';
export * from './lib/models/Credential.model';
export * from './lib/models/Location.model';
