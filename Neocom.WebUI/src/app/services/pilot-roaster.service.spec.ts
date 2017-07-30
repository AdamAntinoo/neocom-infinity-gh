import { TestBed, inject } from '@angular/core/testing';

import { PilotRoasterService } from './pilot-roaster.service';

describe('PilotRoasterService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [PilotRoasterService]
    });
  });

  it('should be created', inject([PilotRoasterService], (service: PilotRoasterService) => {
    expect(service).toBeTruthy();
  }));
});
