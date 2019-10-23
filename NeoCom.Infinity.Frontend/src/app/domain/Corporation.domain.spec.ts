// - TESTING
import { TestBed } from '@angular/core/testing';
// - PROVIDERS
import { SupportIsolationService } from '@app/testing/SupportIsolation.service';
// - DOMAIN
import { Corporation } from './Corporation.domain';

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
      // it('Corporation.getId: check the id field', () => {
      //    const expected = isolation.generateRandomNum(100000, 1000000);
      //    const corporation = new Corporation({ CorporationId: expected })
      //    const obtained = corporation.getId();
      //    expect(obtained).toBe(expected);
      // });
      // it('Corporation.getName: check the id field', () => {
      //    const expected = isolation.generateRandomString(32);
      //    const corporation = new Corporation({ name: expected })
      //    const obtained = corporation.getName();
      //    expect(obtained).toBe(expected);
      // });
      it('Corporation.getIconUrl: check the icon url field', () => {
         const expected = isolation.generateRandomString(32);
         const corporation = new Corporation({ url4Icon: expected })
         const obtained = corporation.getIconUrl();
         expect(obtained).toBe(expected);
      });
      it('Corporation.getIconUrl: check the icon url field', () => {
         const corporation = new Corporation()
         const obtained = corporation.getIconUrl();
         expect(obtained).toBe('/assets/res-sde/drawable/corporation.png');
      });
   });
});
