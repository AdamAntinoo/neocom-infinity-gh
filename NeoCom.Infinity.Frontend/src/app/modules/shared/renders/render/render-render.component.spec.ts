//  PROJECT:     CitasCentro.frontend(CCF.A6+I)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2018, 2019 by Endless Dimensions Ltd., all rights reserved.
//  ENVIRONMENT: Angular 6.0 + Ionic 3.9
//  SITE:        citascentro.com
//  DESCRIPTION: CitasCentro. Sistema S2. Aplicación Angular modular para acceder a las funcionalidades de
//               administracion de calendarios de servicios, gestion de citaciones, gestión de recursos
//               y administración del sistema en general.
//               Este sistema utiliza como backend el sistema S1 para almacenar los datos de las citaciones.
// --- CORE
import { NO_ERRORS_SCHEMA } from '@angular/core';
// --- TESTING
import { TestBed } from '@angular/core/testing';
import { ComponentFixture } from '@angular/core/testing';
// --- MODELS
import { RenderComponent } from '@renders/render/render.component';

// - R E N D E R   T E S T S . V 2
describe('RENDER RenderComponent [Module: SHARED]', () => {
  let component: RenderComponent;
  let fixture: ComponentFixture<RenderComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      schemas: [NO_ERRORS_SCHEMA],
      declarations: [RenderComponent],
    })
      .compileComponents();
    // Create the component and all the rest of the dependencies.
    fixture = TestBed.createComponent(RenderComponent);
    component = fixture.componentInstance;
  });

  // - C O N S T R U C T I O N   P H A S E
  describe('Construction Phase', () => {
    it('should be created', () => {
      console.log('><[shared/RenderComponent]> should be created');
      expect(component).toBeDefined('component has not been created.');
    });
    it('Fields should be on initial state', () => {
      console.log('><[shared/RenderComponent]> Input "Render.container" should be undefined');
      expect(component.container).toBeUndefined('Input "Render.container" should be undefined');
      console.log('><[shared/RenderComponent]> Input "Render.variant" should be "-DEFAULT-"');
      expect(component.variant).toBe('-DEFAULT-', 'Input "Render.variant" should be "-DEFAULT-"');
      console.log('><[shared/RenderComponent]> Field "Render.development" should be false"');
      expect(component.development).toBeFalsy('Field "Render.development" should be false"');
    });
  });

  // - C O D E   C O V E R A G E   P H A S E
  describe('Code coverage Phase [IRenderComponent interface]', () => {
    it('IRenderComponent: validate interface methods', () => {
      console.log('><[RenderComponent]> IRenderComponent: validate interface methods');
      expect(component.getVariant()).toBe('-DEFAULT-', 'Input "Render.variant" should be "-DEFAULT-"');
      expect(component.getContainer()).toBeUndefined('Input "Render.container" should be undefined');
    });
  });

  describe('Code coverage Phase [IExpandable interface]', () => {
    it('IExpandable: validate interface methods', () => {
      console.log('><[RenderComponent]> IExpandable: validate interface methods');
      expect(component.isExpandable()).toBeFalsy();
      expect(component.isExpanded()).toBeFalsy();
      expect(component.toggleExpand()).toBeFalsy();
    });
  });
});
