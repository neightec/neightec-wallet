import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MockStore, provideMockStore } from '@ngrx/store/testing';

import { AccountsOverviewComponent } from './accounts-overview.component';
import { loadAccounts } from '../../store/account.actions';
import { selectAccounts } from '../../store/account.selectors';

describe('AccountsOverviewComponent', () => {
  let fixture: ComponentFixture<AccountsOverviewComponent>;
  let store: MockStore;
  let dispatchSpy: jasmine.Spy;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AccountsOverviewComponent],
      providers: [provideMockStore({ selectors: [{ selector: selectAccounts, value: [] }] })]
    }).compileComponents();

    store = TestBed.inject(MockStore);
    dispatchSpy = spyOn(store, 'dispatch');
    fixture = TestBed.createComponent(AccountsOverviewComponent);
  });

  it('should dispatch loadAccounts on init', () => {
    fixture.detectChanges();
    expect(dispatchSpy.calls.mostRecent().args[0]).toEqual(loadAccounts());
  });

  it('should render one card per account', () => {
    store.overrideSelector(selectAccounts, [
      { name: 'Girokonto', balance: '1830.10', currency: null },
      { name: 'Tagesgeld', balance: '5600.00', currency: null },
      { name: 'Bargeld', balance: '145.50', currency: null },
    ]);
    store.refreshState();
    fixture.detectChanges();

    const cards = fixture.nativeElement.querySelectorAll('neightec-accounts-card');
    expect(cards.length).toBe(3);
  });

  it('should show empty hint without accounts', () => {
    fixture.detectChanges();
    expect(fixture.nativeElement.querySelector('.accounts-empty')).not.toBeNull();
  });
});
