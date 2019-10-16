// - TESTING
import { TestBed, async, inject } from '@angular/core/testing';

import { TokenAuthorizationGuard } from './token-authorization.guard';

describe('TokenAuthorizationGuard', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
        providers: [TokenAuthorizationGuard]
    });
  });

    it('should ...', inject([TokenAuthorizationGuard], (guard: TokenAuthorizationGuard) => {
    expect(guard).toBeTruthy();
  }));
});
