//  PROJECT:     CitasCentro.frontend(CCF.A6+I)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2018, 2019 by Endless Dimensions Ltd., all rights reserved.
//  ENVIRONMENT: Angular 6.0 + Ionic 4.0
//  SITE:        citascentro.com
//  DESCRIPTION: CitasCentro. Sistema S2. Aplicación Angular modular para acceder a las funcionalidades de
//               administracion de calendarios de servicios, gestion de citaciones, gestión de recursos
//               y administración del sistema en general.
//               Este sistema utiliza como backend el sistema S1 para almacenar los datos de las citaciones.
// --- CORE
import { Injectable } from '@angular/core';
import { Inject } from '@angular/core';
import { Observable } from 'rxjs';
import { throwError } from 'rxjs';
import { BehaviorSubject } from 'rxjs';
import { map } from 'rxjs/operators';
import { catchError } from 'rxjs/operators';
// --- ENVIRONMENT
import { environment } from '@env/environment';
// --- WEBSTORAGE
import { LOCAL_STORAGE } from 'angular-webstorage-service';
import { SESSION_STORAGE } from 'angular-webstorage-service';
import { WebStorageService } from 'angular-webstorage-service';
// --- HTTP PACKAGE
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';
// --- ROUTER
import { Router } from '@angular/router';
// // --- NOTIFICATIONS
// //import { NotificationsService } from 'angular2-notifications';
// //--- DATE FUNCTIONS
// import { differenceInMilliseconds } from 'date-fns';
// // --- SERVICES
// import { IsolationService } from '@app/platform/isolation.service';
// // --- INTERFACES
// import { INode } from '@interfaces/core/INode.interface';
// // --- MODELS
// import { Centro } from '@models/Centro.model';
// import { Cita } from '@models/Cita.model';
// import { Medico } from '@models/Medico.model';
// import { Credencial } from '@models/Credencial.model';
// import { Limitador } from '@models/Limitador.model';
// import { TemplateBuildingBlock } from '@models/TemplateBuildingBlock.model';
// import { CitasTemplate } from '@models/CitasTemplate.model';
// import { CredentialRequest } from '@app/models/CredentialRequest.model';
// import { AppointmentReport } from '@app/models/AppointmentReport.model';
// import { FormularioCreacionCitas } from '@app/models/FormularioCreacionCitas.model';
// import { PatientData } from '@app/models/PatientData.model';
// import { OpenAppointment } from '@app/models/OpenAppointment.model';
// import { Perfil } from '@app/models/Perfil.model';
// import { ServiceAuthorized } from '@app/models/ServiceAuthorized.model';
// import { AppModule } from '@app/models/AppModule.model';
// import { NewMedico } from '@app/models/NewMedico.model';
// import { ActoMedicoCentro } from '@models/ActoMedicoCentro.model';
// import { IAppointmentEnabled } from '@interfaces/IAppointmentEnabled.interface';

// --- C O N S T A N T S
/**
 // tslint:disable-next-line:max-line-length
 * This service will be the responsible to manage all the api interface with the backend. Most of the data can be cached so to keep consistency with the asynchronicity of all other calls we will implement an Observable pattern to access the data also asynchronously.
 * This service must be completely isolated and can only depend on external configuration.
 * The service also defines a URL translation to mock data to be replaced during development or demo modes.
 * @param @Inject(LOCAL_STORAGE [description]
 */
@Injectable({
  providedIn: 'root'
})
export class RuntimeBackendService {
  // --- MOCK SECTION
  // Define mock data references to input data on files.
  protected responseTable = {
    // '/api/v1/centro/1000003/templates':
    //   '/assets/mockdata/centro-1000003-templates.json',
    // 'https://backcitamed.herokuapp.com/api/v1/centro/1000003/templates':
    //   '/assets/mockdata/centro-1000003-templates.json',
    // 'https://backcitamed.herokuapp.com/api/v1/medicos/942 314 112/citas/byreferencia':
    //   '/assets/mockdata/medicos-942314112-citas-byreferencia.json',
    // '/api/v1/properties/specilities':
    //   '/assets/properties/specialities.json',
    // 'https://backcitas.herokuapp.com/api/v1/properties/specilities':
    //   '/assets/properties/specialities.json',
    // '/api/v1/properties/limits':
    //   '/assets/properties/limits.json',
    // 'https://backcitas.herokuapp.com/api/v1/properties/limits':
    //   '/assets/properties/limits.json',
    // '/api/v1/credencial/942 314 112/limits':
    //   '/assets/mockdata/credencial-942314112-limits.json',
    // 'https://backcitas.herokuapp.com/api/v1/credencial/81456123J/limits':
    //   'LOCALSTORAGE::credencial-limits',
    '/api/v1/centros/100001/actosmedicos':
      '/assets/mockdata/centro-100006-actosmedicos.json',
    '/api/v1/centros/100002/actosmedicos':
      '/assets/mockdata/centro-100006-actosmedicos.json',
    'https://backcitas.herokuapp.com/api/v1/centros/100001/actosmedicos':
      '/assets/mockdata/centro-100006-actosmedicos.json'
  };
  public getMockStatus(): boolean {
    return environment.mockStatus;
  }
}
