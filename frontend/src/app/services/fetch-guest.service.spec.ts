import { TestBed } from '@angular/core/testing';

import { FetchGuestService } from './fetch-guest.service';

describe('FetchGuestService', () => {
  let service: FetchGuestService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FetchGuestService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
