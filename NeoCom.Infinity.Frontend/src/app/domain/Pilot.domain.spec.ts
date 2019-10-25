// - TESTING
import { TestBed } from '@angular/core/testing';
// - PROVIDERS
import { SupportIsolationService } from '@app/testing/SupportIsolation.service';
// - DOMAIN
import { Pilot } from './Pilot.domain';
import { environment } from '@env/environment.prod';

describe('CLASS Pilot [Module: DOMAIN]', () => {
    let isolation: SupportIsolationService;

    beforeEach(() => {
        isolation = TestBed.get(SupportIsolationService);
    });
    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('Should be created', () => {
            console.log('><[domain/Pilot]> should be created');
            expect(new Pilot()).toBeTruthy();
        });
    });
    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [getters]', () => {
        it('getName.success: check the name field', () => {
            const expected = isolation.generateRandomString(32);
            const pilot = new Pilot({ name: expected })
            const obtained = pilot.getName();
            expect(obtained).toBe(expected);
        });
        it('getName.failure: check the name field', () => {
            const pilot = new Pilot()
            const obtained = pilot.getName();
            expect(obtained).toBe('-');
        });
        it('getPilotIcon.success: check the icon url field', () => {
            const expected = isolation.generateRandomString(32);
            const corporation = new Pilot({ url4Icon: expected })
            const obtained = corporation.getPilotIcon();
            expect(obtained).toBe(expected);
        });
        it('getPilotIcon.failure: check the icon url field', () => {
            const corporation = new Pilot()
            const obtained = corporation.getPilotIcon();
            expect(obtained).toBe(environment.DEFAULT_AVATAR_PLACEHOLDER);
        });
    });
});
