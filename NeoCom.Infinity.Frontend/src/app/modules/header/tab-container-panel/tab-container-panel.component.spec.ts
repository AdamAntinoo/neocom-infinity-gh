// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core';
// - TESTING
import { async } from '@angular/core/testing';
import { fakeAsync } from '@angular/core/testing';
import { tick } from '@angular/core/testing';
import { ComponentFixture } from '@angular/core/testing';
import { TestBed } from '@angular/core/testing';
// - PROVIDERS
import { IsolationService } from '@app/platform/isolation.service';

import { TabContainerPanelComponent } from './tab-container-panel.component';
import { SupportIsolationService } from '@app/testing/SupportIsolation.service';
import { AppStoreService } from '@app/services/appstore.service';
import { SupportAppStoreService } from '@app/testing/SupportAppStore.service';
import { Observable } from 'rxjs';

fdescribe('PANEL TabContainerPanelComponent [Module: HEADER]', () => {
    let component: TabContainerPanelComponent;
    let fixture: ComponentFixture<TabContainerPanelComponent>;
    let isolationService: SupportIsolationService;
    let appStoreService: SupportAppStoreService;

    beforeEach(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            declarations: [
                TabContainerPanelComponent,
            ],
            providers: [
                { provide: IsolationService, useClass: SupportIsolationService },
                { provide: AppStoreService, useClass: SupportAppStoreService },
            ]
        })
            .compileComponents();
        fixture = TestBed.createComponent(TabContainerPanelComponent);
        component = fixture.componentInstance;
        appStoreService = TestBed.get(AppStoreService);
    });

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('Should be created', () => {
            console.log('><[header/TabContainerPanelComponent]> should be created');
            expect(component).toBeDefined('component has not been created.');
        });
    });

    // - L I F E C Y C L E   P H A S E
    describe('Lifecycle Phase', () => {
        fit('Lifecycle: OnInit -> get the tab definitions. List definitions', fakeAsync(() => {
            console.log('><[header/TabContainerPanelComponent]> Lifecycle: OnInit -> get the tab definitions');
            let componentAsAny = component as any;
            expect(component.tabs).toBeDefined();
            expect(component.tabs.length).toBe(0);
            // Initialize the component
            component.ngOnInit();
            expect(component.tabs.length).toBe(2);
        }));
        fit('Lifecycle: OnInit -> get the tab definitions. List definitions. Tab selected', fakeAsync(() => {
            console.log('><[header/TabContainerPanelComponent]> Lifecycle: OnInit -> get the tab definitions');
            let componentAsAny = component as any;
            expect(component.tabs).toBeDefined();
            expect(component.tabs.length).toBe(0);
            // Initialize the component
            component.tabName = "Fittings";
            component.ngOnInit();
            expect(component.tabs.length).toBe(2);
            expect(component.tabs[0].isActive()).toBeTruthy();
        }));
        fit('Lifecycle: OnInit -> get the tab definitions. Single definition', fakeAsync(() => {
            console.log('><[header/TabContainerPanelComponent]> Lifecycle: OnInit -> get the tab definitions');
            let componentAsAny = component as any;
            expect(component.tabs).toBeDefined();
            expect(component.tabs.length).toBe(0);
            // Mock the source function.
            let mockAppStoreService = TestBed.get(AppStoreService);
            mockAppStoreService.accessProperties = function () {
                return Observable.create((observer) => {
                    observer.next({
                        "jsonClass": "TabDefinition",
                        "name": "Fittings",
                        "icon": "assets/res-sde/drawable/fittingmanagement.png",
                        "pageLink": "/fittings"
                    });
                    observer.complete();
                });
            }
            // Initialize the component
            component.ngOnInit();
            expect(component.tabs.length).toBe(1);
        }));
        fit('Lifecycle: OnInit -> get the tab definitions. Single definition tab active', fakeAsync(() => {
            console.log('><[header/TabContainerPanelComponent]> Lifecycle: OnInit -> get the tab definitions');
            let componentAsAny = component as any;
            expect(component.tabs).toBeDefined();
            expect(component.tabs.length).toBe(0);
            // Mock the source function.
            let mockAppStoreService = TestBed.get(AppStoreService);
            mockAppStoreService.accessProperties = function () {
                return Observable.create((observer) => {
                    observer.next({
                        "jsonClass": "TabDefinition",
                        "name": "Fittings",
                        "icon": "assets/res-sde/drawable/fittingmanagement.png",
                        "pageLink": "/fittings"
                    });
                    observer.complete();
                });
            }
            // Initialize the component
            component.tabName = "Fittings";
            component.ngOnInit();
            expect(component.tabs.length).toBe(1);
            expect(component.tabs[0].isActive()).toBeTruthy();
        }));
    });
});
