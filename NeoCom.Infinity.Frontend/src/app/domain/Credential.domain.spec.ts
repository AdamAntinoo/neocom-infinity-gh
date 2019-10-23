// - TESTING
import { TestBed } from '@angular/core/testing';
// - PROVIDERS
import { SupportIsolationService } from '@app/testing/SupportIsolation.service';
// - DOMAIN
import { Credential } from './Credential.domain';

describe('CLASS Credential [Module: DOMAIN]', () => {
    let isolation: SupportIsolationService;

    beforeEach(() => {
        isolation = TestBed.get(SupportIsolationService);
    });
    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('Should be created', () => {
            console.log('><[domain/Credential]> should be created');
            expect(new Credential()).toBeTruthy();
        });
    });
    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [getters]', () => {
        it('Credential.getAccountId: check the account id field', () => {
            const expected = isolation.generateRandomNum(100000, 1000000);
            const credential = new Credential({ accountId: expected })
            const obtained = credential.getAccountId();
            expect(obtained).toBe(expected);
        });
        it('Credential.getAccountName: check the account name field', () => {
            const expected = isolation.generateRandomString(32);
            const credential = new Credential({ accountName: expected })
            const obtained = credential.getAccountName();
            expect(obtained).toBe(expected);
        });
        it('Credential.getUniqueId: check the account name field', () => {
            const expected = isolation.generateRandomString(32);
            const credential = new Credential({ uniqueId: expected })
            const obtained = credential.getUniqueId();
            expect(obtained).toBe(expected);
        });
        it('Credential.getRaceName: check the account id field', () => {
            const expected = isolation.generateRandomString(32);
            const credential = new Credential({ raceName: expected })
            const obtained = credential.getRaceName();
            expect(obtained).toBe(expected);
        });
    });
});
