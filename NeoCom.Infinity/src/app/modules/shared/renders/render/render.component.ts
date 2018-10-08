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
import { IContainerController } from '../../interfaces/core/IContainerController.interface';

@Component({
  selector: 'notused-render',
  templateUrl: './notused.html'
})
export class RenderComponent {
  @Input() container: IContainerController;
  @Input() variant: string = "-DEFAULT-";
  public development: boolean = false;

  //--- G L O B A L S
  public WEEKDAYS: string[] = [
    "Domingo",
    "Lunes",
    "Martes",
    "Miércoles",
    "Jueves",
    "Viernes",
    "Sábado"
  ];
  public MONTHNAMES: string[] = [
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
  ]

  //--- G E T T E R S   &   S E T T E R S
  public getVariant(): string {
    return this.variant;
  }
}
