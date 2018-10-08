//  PROJECT:     CitasMedico.frontend(CMF.A6+I)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2018, 2019 by Endless Dimensions Ltd., all rights reserved.
//  ENVIRONMENT: Angular 6.0 + Ionic 4.0
//  SITE:        citasmedico.com
//  DESCRIPTION: CitasMedico. Sistema S2. Aplicación Angular modular para acceder a las funcionalidades de
//               administracion de calendarios de servicios, gestion de citaciones, gestión de recursos
//               y administración del sistema en general.
//               Este sistema utiliza como backend el sistema S1 para almacenar los datos de las citaciones.
//--- INTERFACES
import { ISelectable } from './ISelectable.interface';

export interface IExpandable extends ISelectable {
  expanded: boolean;

  isExpanded(): boolean;
  collapse(): boolean;
  expand(): boolean;
  toggleExpanded();
  getContentsSize(): number;
}
