//  PROJECT:     CitasMedico.frontend(CMF.A6+I)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2018, 2019 by Endless Dimensions Ltd., all rights reserved.
//  ENVIRONMENT: Angular 6.0 + Ionic 4.0
//  SITE:        citasmedico.com
//  DESCRIPTION: CitasMedico. Sistema S2. Aplicación Angular modular para acceder a las funcionalidades de
//               administracion de calendarios de servicios, gestion de citaciones, gestión de recursos
//               y administración del sistema en general.
//               Este sistema utiliza como backend el sistema S1 para almacenar los datos de las citaciones.
//--- CORE
import { Component } from '@angular/core';
import { Input } from '@angular/core';
//--- INTERFACES
import { IRenderComponent } from '@app/domain/interfaces/IRenderComponent.interface';
// import { IContainerController } from '@interfaces/core/IContainerController.interface';

// --- CONSTANTS
export const WEEKDAYS = [
    "Domingo",
    "Lunes",
    "Martes",
    "Miércoles",
    "Jueves",
    "Viernes",
    "Sábado"
];
export const MONTHNAMES = [
    "enero",
    "febrero",
    "marzo",
    "abril",
    "mayo",
    "junio",
    "julio",
    "agosto",
    "septiembre",
    "octubre",
    "noviembre",
    "diciembre"
];

@Component({
    selector: 'x-render',
    templateUrl: './notused.html'
})
export class RenderComponent implements IRenderComponent {
    //   @Input() container: IContainerController;
    @Input() variant: string = '-DEFAULT-';
    // public development: boolean = false;

    // - I R E N D E R C O M P O N E N T   I N T E R F A C E
    public getVariant(): string {
        return this.variant;
    }
    //   public getContainer(): IContainerController {
    //     return this.container;
    //   }

    // - I E X P A N D A B L E C O M P O N E N T   I N T E R F A C E
    public isExpandable(): boolean {
        return false;
    }
    public isExpanded(): boolean {
        return false;
    }
    public toggleExpand(): boolean {
        return false;
    }
}
