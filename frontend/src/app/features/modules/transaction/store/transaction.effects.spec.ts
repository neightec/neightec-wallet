import { TestBed } from '@angular/core/testing';
import { provideMockActions } from '@ngrx/effects/testing';
import { Action } from '@ngrx/store';
import { Observable, of, throwError } from 'rxjs';
import { WalletTransactionDTO } from 'src/app/dto/walletTransactionDTO';
import { WalletTransactionService } from 'src/app/services/wallet-transaction.service';
import {
  loadTransactionTableState,
  loadTransactionTableStateError,
  loadTransactionTableStateSuccess,
} from './transaction.actions';
import { TransactionEffects } from './transaction.effects';

describe('TransactionEffects', () => {
  let actions$: Observable<Action>;
  let effects: TransactionEffects;
  let walletTransactionService: jasmine.SpyObj<WalletTransactionService>;

  const transactions: WalletTransactionDTO[] = [
    { name: 'Salary', category_type: 'WORK', price: 2500, currency_type: 'EUR' },
    { name: 'Groceries', category_type: 'FOOD', price: 45.99, currency_type: 'EUR' },
  ];

  beforeEach(() => {
    walletTransactionService = jasmine.createSpyObj<WalletTransactionService>(
      'WalletTransactionService',
      ['fetchAllWalletTransactionBySessionID']
    );

    TestBed.configureTestingModule({
      providers: [
        TransactionEffects,
        provideMockActions(() => actions$),
        { provide: WalletTransactionService, useValue: walletTransactionService },
      ],
    });

    effects = TestBed.inject(TransactionEffects);
  });

  it('should dispatch success with the fetched transactions', (done) => {
    walletTransactionService.fetchAllWalletTransactionBySessionID.and.returnValue(of(transactions));
    actions$ = of(loadTransactionTableState());

    effects.loadDataTransactionTable$.subscribe(action => {
      expect(action).toEqual(loadTransactionTableStateSuccess({ data: transactions }));
      done();
    });
  });

  it('should dispatch success with an empty list when the backend returns no transactions', (done) => {
    walletTransactionService.fetchAllWalletTransactionBySessionID.and.returnValue(of([]));
    actions$ = of(loadTransactionTableState());

    effects.loadDataTransactionTable$.subscribe(action => {
      expect(action).toEqual(loadTransactionTableStateSuccess({ data: [] }));
      done();
    });
  });

  it('should dispatch error when the request fails', (done) => {
    walletTransactionService.fetchAllWalletTransactionBySessionID
      .and.returnValue(throwError(() => new Error('boom')));
    actions$ = of(loadTransactionTableState());

    effects.loadDataTransactionTable$.subscribe(action => {
      expect(action).toEqual(loadTransactionTableStateError());
      done();
    });
  });

  it('should keep working after a failed request', (done) => {
    walletTransactionService.fetchAllWalletTransactionBySessionID.and.returnValues(
      throwError(() => new Error('boom')),
      of(transactions)
    );
    actions$ = of(loadTransactionTableState(), loadTransactionTableState());

    const results: Action[] = [];
    effects.loadDataTransactionTable$.subscribe({
      next: action => results.push(action),
      complete: () => {
        expect(results).toEqual([
          loadTransactionTableStateError(),
          loadTransactionTableStateSuccess({ data: transactions }),
        ]);
        done();
      },
    });
  });

  it('should ignore unrelated actions', () => {
    actions$ = of({ type: 'unrelated' });

    const emitted = jasmine.createSpy('emitted');
    effects.loadDataTransactionTable$.subscribe(emitted);

    expect(emitted).not.toHaveBeenCalled();
    expect(walletTransactionService.fetchAllWalletTransactionBySessionID).not.toHaveBeenCalled();
  });
});
