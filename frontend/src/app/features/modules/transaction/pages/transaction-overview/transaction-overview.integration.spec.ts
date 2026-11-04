import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { EffectsModule } from '@ngrx/effects';
import { Store, StoreModule } from '@ngrx/store';
import { NEIGHT_CONFIG } from 'src/neight.config';
import { NeightApiService } from 'src/neight-api.service';
import { neightEnvironment } from 'src/environments/environment';
import { TransactionEffects } from '../../store/transaction.effects';
import { transactionReducer, transactionReducerKey } from '../../store/transaction.reducer';
import { TransactionOverviewComponent } from './transaction-overview.component';

/**
 * Integration: component + real store + real effects + real WalletTransactionService.
 * Only the HTTP layer is faked.
 */
describe('TransactionOverviewComponent (integration)', () => {
  let fixture: ComponentFixture<TransactionOverviewComponent>;
  let httpMock: HttpTestingController;
  let store: Store;

  const url = `${neightEnvironment.api_url}wallet-transaction/get-transaction-by-id`;

  const rowTexts = (): string[] =>
    fixture.debugElement
      .queryAll(By.css('tr.mat-mdc-row'))
      .map(row => row.nativeElement.querySelector('td').textContent.trim());

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        TransactionOverviewComponent,
        NoopAnimationsModule,
        StoreModule.forRoot({}),
        StoreModule.forFeature(transactionReducerKey, transactionReducer),
        EffectsModule.forRoot([TransactionEffects]),
      ],
      providers: [
        provideHttpClient(),
        provideHttpClientTesting(),
        NeightApiService,
        { provide: NEIGHT_CONFIG, useValue: neightEnvironment },
      ],
    });

    httpMock = TestBed.inject(HttpTestingController);
    store = TestBed.inject(Store);
    fixture = TestBed.createComponent(TransactionOverviewComponent);
    fixture.detectChanges(); // ngOnInit -> dispatch -> effect -> HTTP request
  });

  afterEach(() => httpMock.verify());

  it('should request the transactions without a literal "null" session id', () => {
    const request = httpMock.expectOne(r => r.url === url);

    expect(request.request.method).toBe('GET');
    expect(request.request.params.has('sessionId')).toBeFalse();
    expect(request.request.urlWithParams).not.toContain('null');

    request.flush([]);
  });

  it('should render the rows returned by the backend', () => {
    httpMock.expectOne(r => r.url === url).flush([
      { name: 'Salary', category_type: 'WORK', price: 2500, currency_type: 'EUR' },
      { name: 'Groceries', category_type: 'FOOD', price: 45.99, currency_type: 'EUR' },
    ]);
    fixture.detectChanges();

    expect(rowTexts()).toEqual(['Salary', 'Groceries']);
  });

  it('should render an empty table when the backend returns no transactions', () => {
    httpMock.expectOne(r => r.url === url).flush([]);
    fixture.detectChanges();

    expect(rowTexts()).toEqual([]);
  });

  it('should render an empty table when the request fails', () => {
    httpMock.expectOne(r => r.url === url).flush('error', { status: 500, statusText: 'Server Error' });
    fixture.detectChanges();

    expect(rowTexts()).toEqual([]);
  });

  it('should clear previously loaded rows when a reload fails', () => {
    httpMock.expectOne(r => r.url === url).flush([
      { name: 'Salary', category_type: 'WORK', price: 2500, currency_type: 'EUR' },
    ]);
    fixture.detectChanges();
    expect(rowTexts()).toEqual(['Salary']);

    store.dispatch({ type: '[Transaction Table] Load Data' });
    httpMock.expectOne(r => r.url === url).flush('error', { status: 500, statusText: 'Server Error' });
    fixture.detectChanges();

    expect(rowTexts()).toEqual([]);
  });
});
