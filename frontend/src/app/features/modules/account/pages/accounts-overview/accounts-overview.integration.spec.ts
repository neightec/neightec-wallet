import { provideHttpClient } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { EffectsModule } from '@ngrx/effects';
import { Store, StoreModule } from '@ngrx/store';
import { neightEnvironment } from 'src/environments/environment';
import { addAccount } from '../../store/account.actions';
import { AccountEffects } from '../../store/account.effects';
import { accountReducer, accountReducerKey } from '../../store/account.reducer';
import { AccountsOverviewComponent } from './accounts-overview.component';

/**
 * Integration: component + real store + real effects + real AccountService.
 * Only the HTTP layer is faked.
 */
describe('AccountsOverviewComponent (integration)', () => {
  let fixture: ComponentFixture<AccountsOverviewComponent>;
  let httpMock: HttpTestingController;
  let store: Store;

  const getUrl = `${neightEnvironment.api_url}wallet-account/get-accounts-by-session-id`;
  const addUrl = `${neightEnvironment.api_url}wallet-account/add-account-by-session-id`;

  const accountNames = (): string[] =>
    Array.from<HTMLElement>(fixture.nativeElement.querySelectorAll('.account-name'))
      .map(el => el.textContent!.trim());

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        AccountsOverviewComponent,
        NoopAnimationsModule,
        StoreModule.forRoot({}),
        StoreModule.forFeature(accountReducerKey, accountReducer),
        EffectsModule.forRoot([AccountEffects]),
      ],
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });

    httpMock = TestBed.inject(HttpTestingController);
    store = TestBed.inject(Store);
    fixture = TestBed.createComponent(AccountsOverviewComponent);
    fixture.detectChanges();
  });

  afterEach(() => httpMock.verify());

  it('should request accounts without a literal "null" session id', () => {
    const request = httpMock.expectOne(r => r.url === getUrl);
    expect(request.request.method).toBe('GET');
    expect(request.request.urlWithParams).not.toContain('null');
    request.flush([]);
  });

  it('should render the accounts returned by the backend', () => {
    httpMock.expectOne(r => r.url === getUrl).flush([
      { name: 'Giro', balance: '100.00', currency: '€' },
      { name: 'Savings', balance: '-5', currency: null },
    ]);
    fixture.detectChanges();

    expect(accountNames()).toEqual(['Giro', 'Savings']);
    expect(fixture.nativeElement.querySelector('.accounts-empty')).toBeNull();
  });

  it('should show the empty hint when the request fails', () => {
    httpMock.expectOne(r => r.url === getUrl).flush('error', { status: 500, statusText: 'Server Error' });
    fixture.detectChanges();

    expect(accountNames()).toEqual([]);
    expect(fixture.nativeElement.querySelector('.accounts-empty')).not.toBeNull();
  });

  it('should POST a new account and append it to the list', () => {
    httpMock.expectOne(r => r.url === getUrl).flush([{ name: 'Giro', balance: '1', currency: '€' }]);

    const newAccount = { name: 'Savings', balance: '50', currency: '€' };
    store.dispatch(addAccount({ data: newAccount }));
    const post = httpMock.expectOne(r => r.url === addUrl);
    expect(post.request.method).toBe('POST');
    expect(post.request.body).toEqual(newAccount);
    post.flush(newAccount);
    fixture.detectChanges();

    expect(accountNames()).toEqual(['Giro', 'Savings']);
  });

  it('should keep the list unchanged when adding fails', () => {
    httpMock.expectOne(r => r.url === getUrl).flush([{ name: 'Giro', balance: '1', currency: '€' }]);

    store.dispatch(addAccount({ data: { name: 'X', balance: '1', currency: '€' } }));
    httpMock.expectOne(r => r.url === addUrl).flush('error', { status: 500, statusText: 'Server Error' });
    fixture.detectChanges();

    expect(accountNames()).toEqual(['Giro']);
  });
});
