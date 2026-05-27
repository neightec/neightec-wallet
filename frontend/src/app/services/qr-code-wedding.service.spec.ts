import { TestBed } from '@angular/core/testing';

import { QrCodeWeddingService } from './qr-code-wedding.service';

describe('QrCodeWeddingService', () => {
  let service: QrCodeWeddingService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(QrCodeWeddingService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
