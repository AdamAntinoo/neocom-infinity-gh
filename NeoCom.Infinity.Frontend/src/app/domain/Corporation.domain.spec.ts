// - TESTING
import { TestBed } from '@angular/core/testing';
// - PROVIDERS
import { SupportIsolationService } from '@app/testing/SupportIsolation.service';
// - DOMAIN
import { Corporation } from './Corporation.domain';
import { Alliance } from './Alliance.domain';

describe('CLASS Corporation [Module: DOMAIN]', () => {
    let isolation: SupportIsolationService;

    beforeEach(() => {
        isolation = TestBed.get(SupportIsolationService);
    });
    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('Should be created', () => {
            console.log('><[domain/Corporation]> should be created');
            expect(new Corporation()).toBeTruthy();
        });
    });
    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [getters]', () => {
        it('getName.success: check the name field', () => {
            const expectedName = isolation.generateRandomString(32);
            const expected = new Object({ name: expectedName });
            const corporation = new Corporation({ corporationPublicData: expected })
            const obtained = corporation.getName();
            expect(obtained).toBe(expectedName);
        });
        it('getName.failure: check the name field', () => {
            const corporation = new Corporation()
            const obtained = corporation.getName();
            expect(obtained).toBe('-');
        });
        it('getAlliance.success: check the alliance field', () => {
            const expectedName = isolation.generateRandomString(32);
            const expected = new Alliance({ name: expectedName });
            const corporation = new Corporation({ alliance: expected })
            const obtained: Alliance = corporation.getAlliance();
            expect(obtained).toBeDefined();
            expect(obtained.getName()).toBe(expectedName);
        });
        it('getAlliance.failure: check the alliance field', () => {
            const corporation = new Corporation()
            const obtained = corporation.getAlliance();
            expect(obtained).toBeUndefined();
        });
        it('getTicker.success: check the ticker field', () => {
            const expectedName = isolation.generateRandomString(32);
            const expected = new Object({ ticker: expectedName });
            const corporation = new Corporation({ corporationPublicData: expected })
            const obtained = corporation.getTicker();
            expect(obtained).toBe(expectedName);
        });
        it('getTicker.failure: check the ticker field', () => {
            const corporation = new Corporation()
            const obtained = corporation.getTicker();
            expect(obtained).toBe('----');
        });
        it('getMemberCount.success: check the member_count field', () => {
            const expectedNumber = isolation.generateRandomNum(10, 1000);
            const expected = new Object({ member_count: expectedNumber });
            const corporation = new Corporation({ corporationPublicData: expected })
            const obtained = corporation.getMemberCount();
            expect(obtained).toBe(expectedNumber);
        });
        it('getMemberCount.failure: check the member_count field', () => {
            const corporation = new Corporation()
            const obtained = corporation.getMemberCount();
            expect(obtained).toBe(0);
        });
        it('getIconUrl.success: check the icon url field', () => {
            const expected = isolation.generateRandomString(32);
            const corporation = new Corporation({ url4Icon: expected })
            const obtained = corporation.getIconUrl();
            expect(obtained).toBe(expected);
        });
        it('getIconUrl.failure: check the icon url field', () => {
            const corporation = new Corporation()
            const obtained = corporation.getIconUrl();
            expect(obtained).toBe('/assets/res-sde/drawable/corporation.png');
        });
    });
});
