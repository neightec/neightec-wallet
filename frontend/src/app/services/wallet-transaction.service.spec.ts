import { TestBed } from '@angular/core/testing';

import { GuestWeddingListService } from './guest-wedding-list.service';

describe('GuestWeddingListService', () => {
  let service: GuestWeddingListService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GuestWeddingListService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
