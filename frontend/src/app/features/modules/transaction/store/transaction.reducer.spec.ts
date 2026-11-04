import { WalletTransactionDTO } from 'src/app/dto/walletTransactionDTO';
import {
  loadTransactionTableState,
  loadTransactionTableStateError,
  loadTransactionTableStateSuccess,
} from './transaction.actions';
import { initialState, transactionReducer } from './transaction.reducer';

describe('transactionReducer', () => {
  const transactions: WalletTransactionDTO[] = [
    { name: 'Salary', category_type: 'WORK', price: 2500, currency_type: 'EUR' },
    { name: 'Groceries', category_type: 'FOOD', price: 45.99, currency_type: 'EUR' },
  ];

  it('should return the initial state with empty data for an unknown action', () => {
    const state = transactionReducer(undefined, { type: 'unknown' } as any);

    expect(state).toBe(initialState);
    expect(state.data).toEqual([]);
  });

  it('should not change the state when loading starts', () => {
    const state = transactionReducer(initialState, loadTransactionTableState());

    expect(state).toEqual(initialState);
  });

  it('should store the transactions on load success', () => {
    const state = transactionReducer(initialState, loadTransactionTableStateSuccess({ data: transactions }));

    expect(state.data).toEqual(transactions);
  });

  it('should replace previously loaded transactions on load success', () => {
    const loaded = transactionReducer(initialState, loadTransactionTableStateSuccess({ data: transactions }));

    const state = transactionReducer(loaded, loadTransactionTableStateSuccess({ data: [transactions[0]] }));

    expect(state.data).toEqual([transactions[0]]);
  });

  it('should reset the data to an empty array on load error', () => {
    const loaded = transactionReducer(initialState, loadTransactionTableStateSuccess({ data: transactions }));

    const state = transactionReducer(loaded, loadTransactionTableStateError());

    expect(state.data).toEqual([]);
  });

  it('should not mutate the previous state', () => {
    const previous = { data: [] as WalletTransactionDTO[] };

    transactionReducer(previous, loadTransactionTableStateSuccess({ data: transactions }));

    expect(previous.data).toEqual([]);
  });
});
