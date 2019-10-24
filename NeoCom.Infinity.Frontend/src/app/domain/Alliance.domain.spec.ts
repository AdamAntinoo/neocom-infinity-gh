// - TESTING
import { TestBed } from '@angular/core/testing';
// - PROVIDERS
import { SupportIsolationService } from '@app/testing/SupportIsolation.service';
// - DOMAIN
import { Alliance } from "./Alliance.domain";

describe('CLASS Alliance [Module: DOMAIN]', () => {
    let isolation: SupportIsolationService;

    beforeEach(() => {
        isolation = TestBed.get(SupportIsolationService);
    });
    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('Should be created', () => {
            console.log('><[domain/Alliance]> should be created');
            expect(new Alliance()).toBeTruthy();
        });
    });
    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [getters]', () => {
        it('getId.success: check the id field', () => {
            const expected = isolation.generateRandomNum(100000, 1000000);
            const alliance = new Alliance({ allianceId: expected })
            const obtained = alliance.getId();
            expect(obtained).toBe(expected);
        });
        it('getId.failure: check the id field', () => {
            const alliance = new Alliance()
            const obtained = alliance.getId();
            expect(obtained).toBe(-5);
        });
        it('Alliance.getName: check the id field', () => {
            const expected = isolation.generateRandomString(32);
            const alliance = new Alliance({ name: expected })
            const obtained = alliance.getName();
            expect(obtained).toBe(expected);
        });
        it('getAllianceIcon.success: check the id field', () => {
            const expected = isolation.generateRandomString(32);
            const alliance = new Alliance({ url4Icon: expected })
            const obtained = alliance.getAllianceIcon();
            expect(obtained).toBe(expected);
        });
        it('getAllianceIcon.failure: check the id field', () => {
            const alliance = new Alliance()
            const obtained = alliance.getAllianceIcon();
            expect(obtained).toBe('https://image.eveonline.com/Alliance/-5_128.jpg');
        });
    });
});
