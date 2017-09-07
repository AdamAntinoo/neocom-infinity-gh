import { TestBed, inject } from '@angular/core/testing';

import { PilotListDataSourceService } from './pilot-list-data-source.service';

describe('PilotListDataSourceService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [PilotListDataSourceService]
    });
  });

  it('should be created', inject([PilotListDataSourceService], (service: PilotListDataSourceService) => {
    expect(service).toBeTruthy();
  }));
});
