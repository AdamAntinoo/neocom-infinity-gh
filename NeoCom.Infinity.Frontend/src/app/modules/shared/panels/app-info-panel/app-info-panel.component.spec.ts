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
import { SupportIsolationService } from '@app/testing/SupportIsolation.service';
import { environment } from '@env/environment';

import { AppInfoPanelComponent } from '@app/modules/shared/panels/app-info-panel/app-info-panel.component';

describe('PANEL AppInfoPanelComponent [Module: SHARED]', () => {
    let component: AppInfoPanelComponent;
    let fixture: ComponentFixture<AppInfoPanelComponent>;
    let isolationService: SupportIsolationService;

    beforeEach(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            declarations: [
                AppInfoPanelComponent,
            ],
            providers: [
                { provide: IsolationService, useClass: SupportIsolationService },
            ]
        })
            .compileComponents();
        fixture = TestBed.createComponent(AppInfoPanelComponent);
        component = fixture.componentInstance;
        isolationService = TestBed.get(IsolationService);
    });

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('Should be created', () => {
            console.log('><[shared/AppInfoPanelComponent]> should be created');
            expect(component).toBeDefined('component has not been created.');
        });
    });

    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [getters]', () => {
        it('getName: validate the name field', () => {
           const obtained = component.getName();
           const expected = environment.appName as string;
           expect(obtained).toBe(expected.toUpperCase());
        });
        it('getVersion: validate the version field', () => {
            const obtained = component.getVersion();
           expect(obtained).toBe(environment.appVersion);
        });
        it('getCopyright: validate the copyright field', () => {
            const obtained = component.getCopyright();
           expect(obtained).toBe(environment.copyright);
        });
    });
});
