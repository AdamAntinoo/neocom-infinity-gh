// - TESTING
import { TestBed } from '@angular/core/testing';
// - PROVIDERS
import { SupportIsolationService } from '@app/testing/SupportIsolation.service';
// - DOMAIN
import { NeoCom } from './NeoCom.domain';

describe('CLASS NeoCom [Module: DOMAIN]', () => {
    let isolation: SupportIsolationService;

    beforeEach(() => {
        isolation = TestBed.get(SupportIsolationService);
    });
    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('Should be created', () => {
            console.log('><[domain/NeoCom]> should be created');
            expect(new NeoCom()).toBeTruthy();
        });
    });
    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [getters]', () => {
        it('getJsonClass: check the object has a json class name', () => {
            const neocom = new NeoCom()
            const obtained = neocom.getJsonClass();
            expect(obtained).toBe('NeoCom');
        });
        it('isEmpty:success check is a null is empty', () => {
            const neocom = new NeoCom()
            let neocomAsAny = neocom as any;
            expect(neocomAsAny.isEmpty()).toBeTruthy();
        });
        it('isEmpty:success check is a null is empty', () => {
            const neocom = new NeoCom()
            let neocomAsAny = neocom as any;
            expect(neocomAsAny.isEmpty(null)).toBeTruthy();
        });
        it('isEmpty:success check is a undefined is empty', () => {
            const neocom = new NeoCom()
            let neocomAsAny = neocom as any;
            expect(neocomAsAny.isEmpty(undefined)).toBeTruthy();
        });
        it('isEmpty:success check is an array is empty', () => {
            const neocom = new NeoCom()
            let neocomAsAny = neocom as any;
            expect(neocomAsAny.isEmpty([])).toBeTruthy();
        });
        it('isEmpty:success check is a string is empty', () => {
            const neocom = new NeoCom()
            let neocomAsAny = neocom as any;
            expect(neocomAsAny.isEmpty('')).toBeTruthy();
        });
        it('isEmpty:success check is a object is empty', () => {
            const neocom = new NeoCom()
            let neocomAsAny = neocom as any;
            expect(neocomAsAny.isEmpty({})).toBeTruthy();
        });
        it('isEmpty:failure check is an array is not empty', () => {
            const neocom = new NeoCom()
            let neocomAsAny = neocom as any;
            expect(neocomAsAny.isEmpty(['not', 'empty'])).toBeFalsy();
        });
        it('isEmpty:failure check is a string is not empty', () => {
            const neocom = new NeoCom()
            let neocomAsAny = neocom as any;
            expect(neocomAsAny.isEmpty('not empty')).toBeFalsy();
        });
        it('isEmpty:failure check is a object is not empty', () => {
            const neocom = new NeoCom()
            let neocomAsAny = neocom as any;
            expect(neocomAsAny.isEmpty({ not: 'empty' })).toBeFalsy();
        });
    });
});
