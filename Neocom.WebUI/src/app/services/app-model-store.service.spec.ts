import { TestBed, inject } from '@angular/core/testing';

import { AppModelStoreService } from './app-model-store.service';

describe('AppModelStoreService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [AppModelStoreService]
    });
  });

  it('should be created', inject([AppModelStoreService], (service: AppModelStoreService) => {
    expect(service).toBeTruthy();
  }));
});
