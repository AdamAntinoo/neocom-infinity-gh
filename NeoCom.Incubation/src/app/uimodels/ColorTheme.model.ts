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
import { Observable } from 'rxjs/Rx';
// Import RxJs required methods
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
//--- SERVICES
import { AppModelStoreService } from '../services/app-model-store.service';
//--- INTERFACES
import { EThemeSelector } from '../interfaces/EPack.enumerated';
// import { EVariant } from '../classes/EVariant.enumerated';
// import { ESeparator } from '../classes/ESeparator.enumerated';
// import { ESlotGroup } from '../models/SlotLocation.model';
//--- MODELS
// import { NeoComNode } from '../models/NeoComNode.model';
// import { EveItem } from '../models/EveItem.model';
// import { FittingItem } from '../models/FittingItem.model';
// import { Separator } from '../models/Separator.model';
// import { GroupContainer } from '../models/GroupContainer.model';
// import { AssetGroupIconReference } from '../models/GroupContainer.model';

export class ColorTheme {
  private selectorColor: EThemeSelector = EThemeSelector.WHITE;
  private panelBorderColor: string = "panelborder-white";
  private expandedBackgroundColor: string = 'white';

  constructor(values: Object = {}) {
    Object.assign(this, values);
    this.adjustColors();
  }
  private adjustColors() {
    // Depending on the selector color set the rest of the fields.
    if (null != this.selectorColor) {
      if (this.selectorColor == EThemeSelector.WHITE) {
        this.panelBorderColor = "panelborder-white";
        this.expandedBackgroundColor = '#252525';
      }
      if (this.selectorColor == EThemeSelector.GREEN) {
        this.panelBorderColor = "panelborder-green"
        this.expandedBackgroundColor = '#052406';
      }
      if (this.selectorColor == EThemeSelector.BLUE) {
        this.panelBorderColor = "panelborder-blue"
        this.expandedBackgroundColor = '#080825';
      }
      if (this.selectorColor == EThemeSelector.RED) {
        this.panelBorderColor = "panelborder-red"
        this.expandedBackgroundColor = '#250000';
      }
    }
  }
  // --- GETTERS & SETTERS
  public getPanelColor(): string {
    this.adjustColors();
    return this.panelBorderColor;
  }
  public getExpandedTint(): string {
    this.adjustColors();
    return this.expandedBackgroundColor;
  }
}
