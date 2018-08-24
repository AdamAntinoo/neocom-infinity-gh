//  PROJECT:     NeoCom.WS (NEOC.WS)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Angular 4
//  DESCRIPTION: Angular source code to run on a web server almost the same code as on the Android platform.
//               The project has 3 clear parts. One is the Java libraries that are common for all platforms,
//               the second is the java microservices that compose the web application backend made with
//               SpringBoot technology and finally the web ui code made in typescript within the Angular
//               framework.
//--- JASMINE
import { ComponentFixture } from '@angular/core/testing';
import { TestBed } from '@angular/core/testing';
import { async } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
//--- HTTP CLIENT
import { HttpModule } from '@angular/http';
import { HttpClientModule } from '@angular/common/http';
//--- TOAST NOTIFICATIONS
import { ToasterModule } from 'angular5-toaster';
//--- MODULES
import { UIModule } from 'app/modules/ui/ui.module';
//--- SERVICES
import { AppModelStoreService } from '../../services/app-model-store.service';
import { ToasterService } from 'angular5-toaster';
//--- COMPONENTS
import { PilotDashboardPageComponent } from './pilot-dashboard-page.component';
//--- MODELS
import { Pilot } from 'app/models/Pilot.model';

fdescribe('PilotDashboardPageComponent', () => {
  let component: PilotDashboardPageComponent;
  let fixture: ComponentFixture<PilotDashboardPageComponent>;
  let originalTimeout: number;

  //--- B E F O R E E A C H
  beforeEach(function() {
    TestBed.configureTestingModule({
      declarations: [
        PilotDashboardPageComponent
      ],
      imports: [
        //--- JASMINE
        RouterTestingModule,
        //--- HTTP CLIENT
        HttpModule,
        HttpClientModule,
        //--- TOAST NOTIFICATIONS
        ToasterModule,
        //--- MODULES
        UIModule
      ],
      providers: [
        AppModelStoreService,
        ToasterService
      ]
    })
      .compileComponents();
  });
  // Change the default respose timeout.
  beforeEach(function() {
    originalTimeout = jasmine.DEFAULT_TIMEOUT_INTERVAL;
    jasmine.DEFAULT_TIMEOUT_INTERVAL = 30000;
  });
  // Create the component.
  beforeEach(() => {
    fixture = TestBed.createComponent(PilotDashboardPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  // Check that the component exists and that the component fields are initialized.
  it('should have been created', () => {
    expect(component).toBeTruthy();
  });
  it('pilot should exist', () => {
    // Fields default initialization values check.
    expect(component.pilot).toBeTruthy();
  });
  // Check component existence and fields defaults for not initialized inputs.
  it('pilot has valid IconUrl', () => {
    expect(component.pilot.getIconUrl()).toBeTruthy();
  });
  it('pilot has a corporation', () => {
    expect(component.pilot.corporation).toBeTruthy();
  });
  it('pilot corporation has valid IconUrl', () => {
    expect(component.pilot.corporation.getIconUrl()).toBeTruthy();
  });
  it('pilot has a alliance', () => {
    expect(component.pilot.alliance).toBeTruthy();
  });
  it('pilot alliance has valid IconUrl', () => {
    expect(component.pilot.alliance.getIconUrl()).toBeTruthy();
  });
  it('pilot has name', () => {
    expect(component.pilot.name).toBeTruthy();
  });
  it('pilot has birthday', () => {
    expect(component.pilot.birthday).toBeTruthy();
  });
  it('pilot balance positive', () => {
    expect(component.pilot.accountBalance).toBeTruthy();
  });

  // Set back to original values the attributes changed.
  afterEach(function() {
    jasmine.DEFAULT_TIMEOUT_INTERVAL = originalTimeout;
  });
});
