
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
//--- SERVICES
// import { AppModelStoreService } from '../services/app-model-store.service';
//--- INTERFACES
import { ICollaboration } from '../interfaces/ICollaboration.interface';
import { EVariant } from '../interfaces/EPack.enumerated';

export interface INeoComNode extends ICollaboration {
  toggleExpanded();
  getContentsCount(): number;
  getTypeId(): number;
}
