// - TESTING
import { TestBed } from '@angular/core/testing';
// - PROVIDERS
import { SupportIsolationService } from '@app/testing/SupportIsolation.service';
// - DOMAIN
import { environment } from '@env/environment.prod';
import { TabDefinition } from './TabDefinition.domain';

fdescribe('CLASS TabDefinition [Module: DOMAIN]', () => {
    let isolation: SupportIsolationService;

    beforeEach(() => {
        isolation = TestBed.get(SupportIsolationService);
    });
    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('Should be created', () => {
            console.log('><[domain/TabDefinition]> should be created');
            expect(new TabDefinition()).toBeTruthy();
        });
    });
    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [getters]', () => {
        it('isActive.false: the tab is not selected', () => {
            const obtained = new TabDefinition().isActive();
            expect(obtained).toBeFalsy();
        });
        it('isActive.true: the tab is selected', () => {
            const tab = new TabDefinition();
            tab.select();
            const obtained = tab.isActive();
            expect(obtained).toBeTruthy();
        });
        it('getName: get the tab name', () => {
            const tab = new TabDefinition();
            let expected = isolation.generateRandomString(12);
            tab.name = expected;
            const obtained = tab.getName();
            expect(obtained).toBe(expected);
        });
    });
    describe('Code Coverage Phase [setters]', () => {
        it('select: activate the tab', () => {
            const tab = new TabDefinition();
            let obtained = tab.isActive();
            expect(obtained).toBeFalsy();
            tab.select();
            obtained = tab.isActive();
            expect(obtained).toBeTruthy();
        });
    });
});
