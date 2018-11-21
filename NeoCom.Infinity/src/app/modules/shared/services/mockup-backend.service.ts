//  PROJECT:     NeoCom.Infinity(NCI.A6)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018-2019 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Angular 6.1
//  DESCRIPTION: Angular source code to run on a web server almost the same code as on the Android platform.
//               The project has 3 clear parts. One is the Java libraries that are common for all platforms,
//               the second is the java microservices that compose the web application backend made with
//               SpringBoot technology and finally the web ui code made in typescript within the Angular
//               framework.
//--- CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { Injectable } from '@angular/core';
import { Inject } from '@angular/core';
import { environment } from 'src/environments/environment';
//--- ENVIRONMENT
// import { environment } from 'environments/environment';

@Injectable({
  providedIn: 'root'
})
export class MockUpBackendService {
  // --- MOCK SECTION
  // Define mock data references to input data on files.
  protected responseTable = {
    // SDE EVE ITEMS
    '/api/v1/eveitem/34':
      '/assets/mockData/eveitem-34.json',
    '/api/v1/eveitem/35':
      '/assets/mockData/eveitem-35.json',
    '/api/v1/eveitem/36':
      '/assets/mockData/eveitem-36.json',
    '/api/v1/eveitem/37':
      '/assets/mockData/eveitem-37.json',
    '/api/v1/eveitem/38':
      '/assets/mockData/eveitem-38.json',
    '/api/v1/eveitem/39':
      '/assets/mockData/eveitem-39.json',
    '/api/v1/eveitem/40':
      '/assets/mockData/eveitem-40.json',
    '/api/v1/eveitem/11399':
      '/assets/mockData/eveitem-11399.json',
    // PILOT PUBLIC DATA
    '/api/v1/status':
      '/assets/mockData/status.json',
    '/api/v1/pilot/92002067/publicdata':
      '/assets/mockData/pilot-92002067.json',
    '/api/v1/pilot/90475644/publicdata':
      '/assets/mockData/pilot-90475644.json',
    '/api/v1/credentials':
      '/assets/mockData/credentials.json',
    // PILOT ASSETS
    '/api/v1/pilot/92002067/assets':
      '/assets/mockData/92002067-assets.json',
    // PILOT FITTINGS
    '/api/v1/pilot/92002067/fittingmanager/fittings':
      '/assets/mockData/92002067-fittingmanager-fittings.json',
    '/api/v1/pilot/92002067/fittingmanager/fittingrequests':
      '/assets/mockData/92002067-fittingrequests.json',
    // FITTING PROCESSING
    '/api/v1/pilot/92002067/fittingmanager/processfitting/10042893/copies/1':
      '/assets/mockData/92002067-fittingmanager-processfitting-10042893-copies-1.json',
    '/api/v1/pilot/92002067/fittingmanager/processfitting/48137848/copies/1':
      '/assets/mockData/92002067-fittingmanager-processfitting-48137848-copies-1.json',
    '/api/v1/pilot/92002067/fittingmanager/processfitting/47773679/copies/1':
      '/assets/mockData/92002067-fittingmanager-processfitting-47773679-copies-1.MOVE.json',
    // INDUSTRY DATA
    '/api/v1/pilot/92002067/industryjobs':
      '/assets/mockData/92002067-industryjobs.json',
    '/api/v1/pilot/92002067/marketorders':
      '/assets/mockData/92002067-marketorders.json',
    '/api/v1/pilot/92002067/contracts':
      '/assets/mockData/92002067-contracts.json',
    '/api/v1/pilot/92002067/manufactureresources/hullmanufacture':
      '/assets/mockData/92002067-manufactureresources-hullmanufacture.json',
    '/api/v1/pilot/92002067/manufactureresources/structuremanufacture':
      '/assets/mockData/92002067-manufactureresources-structuremanufacture.json',
    '/api/v1/pilot/92002067/manufactureresources/resourcesavailable':
      '/assets/mockData/92002067-manufactureresources-resourcesavailable.json',
    // PLANETARY
    '/api/v1/pilot/92002067/planetarymanager/planetaryresources':
      '/assets/mockData/92002067-planetarymanager-planetaryresources.json',
    '/api/v1/pilot/92002067/planetarymanager/optimizeprocess/system/30003752':
      '/assets/mockData/92002067-planetarymanager-optimizeprocess-system-30003752.json',
    // CORPORATION PUBLIC DATA
    '/api/v1/corporations/1427661573/publicdata':
      '/assets/mockData/corporations-1427661573.json'
  };
  public getMockStatus(): boolean {
    return environment.mockStatus;
  }
}