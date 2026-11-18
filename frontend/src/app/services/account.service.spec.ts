import { provideHttpClient } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { neightEnvironment } from 'src/environments/environment';
import { AccountDTO } from '../dto/accountDTO';
import { AccountService } from './account.service';

describe('AccountService', () => {
  let service: AccountService;
  let httpMock: HttpTestingController;

  const getUrl = `${neightEnvironment.api_url}wallet-account/get-accounts-by-session-id`;
  const addUrl = `${neightEnvironment.api_url}wallet-account/add-account-by-session-id`;
  const giro: AccountDTO = { name: 'Giro', balance: '100', currency: '€' };

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    service = TestBed.inject(AccountService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => httpMock.verify());

  it('should GET accounts without sessionId param when session id is null', () => {
    service.fetchAccountsBySessionID(null).subscribe(result => expect(result).toEqual([giro]));

    const request = httpMock.expectOne(r => r.url === getUrl);
    expect(request.request.method).toBe('GET');
    expect(request.request.params.has('sessionId')).toBeFalse();
    request.flush([giro]);
  });

  it('should GET accounts with sessionId param when session id is given', () => {
    service.fetchAccountsBySessionID('abc').subscribe();

    const request = httpMock.expectOne(r => r.url === getUrl);
    expect(request.request.params.get('sessionId')).toBe('abc');
    request.flush([]);
  });

  it('should POST the account and return the saved one', () => {
    service.addAccountBySessionID(null, giro).subscribe(result => expect(result).toEqual(giro));

    const request = httpMock.expectOne(r => r.url === addUrl);
    expect(request.request.method).toBe('POST');
    expect(request.request.body).toEqual(giro);
    expect(request.request.params.has('sessionId')).toBeFalse();
    request.flush(giro);
  });

  it('should POST with sessionId param when session id is given', () => {
    service.addAccountBySessionID('abc', giro).subscribe();

    const request = httpMock.expectOne(r => r.url === addUrl);
    expect(request.request.params.get('sessionId')).toBe('abc');
    request.flush(giro);
  });
});
