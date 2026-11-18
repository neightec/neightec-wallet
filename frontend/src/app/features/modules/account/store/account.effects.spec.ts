import { TestBed } from '@angular/core/testing';
import { provideMockActions } from '@ngrx/effects/testing';
import { Action } from '@ngrx/store';
import { Observable, of, throwError } from 'rxjs';
import { AccountDTO } from 'src/app/dto/accountDTO';
import { AccountService } from 'src/app/services/account.service';
import {
  addAccount,
  addAccountError,
  addAccountSuccess,
  loadAccounts,
  loadAccountsError,
  loadAccountsSuccess,
} from './account.actions';
import { AccountEffects } from './account.effects';

describe('AccountEffects', () => {
  let actions$: Observable<Action>;
  let effects: AccountEffects;
  let accountService: jasmine.SpyObj<AccountService>;

  const giro: AccountDTO = { name: 'Giro', balance: '100', currency: '€' };

  beforeEach(() => {
    accountService = jasmine.createSpyObj<AccountService>(
      'AccountService',
      ['fetchAccountsBySessionID', 'addAccountBySessionID']
    );

    TestBed.configureTestingModule({
      providers: [
        AccountEffects,
        provideMockActions(() => actions$),
        { provide: AccountService, useValue: accountService },
      ],
    });

    effects = TestBed.inject(AccountEffects);
  });

  describe('loadAccounts$', () => {
    it('should dispatch success with the fetched accounts', (done) => {
      accountService.fetchAccountsBySessionID.and.returnValue(of([giro]));
      actions$ = of(loadAccounts());

      effects.loadAccounts$.subscribe(action => {
        expect(action).toEqual(loadAccountsSuccess({ data: [giro] }));
        expect(accountService.fetchAccountsBySessionID).toHaveBeenCalledWith(null);
        done();
      });
    });

    it('should dispatch error when the request fails', (done) => {
      accountService.fetchAccountsBySessionID.and.returnValue(throwError(() => new Error('boom')));
      actions$ = of(loadAccounts());

      effects.loadAccounts$.subscribe(action => {
        expect(action).toEqual(loadAccountsError());
        done();
      });
    });

    it('should keep working after a failed request', (done) => {
      accountService.fetchAccountsBySessionID.and.returnValues(
        throwError(() => new Error('boom')),
        of([giro])
      );
      actions$ = of(loadAccounts(), loadAccounts());

      const results: Action[] = [];
      effects.loadAccounts$.subscribe({
        next: action => results.push(action),
        complete: () => {
          expect(results).toEqual([loadAccountsError(), loadAccountsSuccess({ data: [giro] })]);
          done();
        },
      });
    });
  });

  describe('addAccount$', () => {
    it('should dispatch success with the saved account', (done) => {
      accountService.addAccountBySessionID.and.returnValue(of(giro));
      actions$ = of(addAccount({ data: giro }));

      effects.addAccount$.subscribe(action => {
        expect(action).toEqual(addAccountSuccess({ data: giro }));
        expect(accountService.addAccountBySessionID).toHaveBeenCalledWith(null, giro);
        done();
      });
    });

    it('should dispatch error when the request fails', (done) => {
      accountService.addAccountBySessionID.and.returnValue(throwError(() => new Error('boom')));
      actions$ = of(addAccount({ data: giro }));

      effects.addAccount$.subscribe(action => {
        expect(action).toEqual(addAccountError());
        done();
      });
    });
  });

  it('should ignore unrelated actions', () => {
    actions$ = of({ type: 'unrelated' });
    const emitted = jasmine.createSpy('emitted');
    effects.loadAccounts$.subscribe(emitted);
    effects.addAccount$.subscribe(emitted);

    expect(emitted).not.toHaveBeenCalled();
    expect(accountService.fetchAccountsBySessionID).not.toHaveBeenCalled();
    expect(accountService.addAccountBySessionID).not.toHaveBeenCalled();
  });
});
