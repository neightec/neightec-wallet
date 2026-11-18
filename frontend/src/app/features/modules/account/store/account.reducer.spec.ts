import { AccountDTO } from 'src/app/dto/accountDTO';
import {
  addAccount,
  addAccountError,
  addAccountSuccess,
  loadAccounts,
  loadAccountsError,
  loadAccountsSuccess,
} from './account.actions';
import { accountReducer, initialState } from './account.reducer';

describe('accountReducer', () => {
  const giro: AccountDTO = { name: 'Giro', balance: '100.50', currency: '€' };
  const savings: AccountDTO = { name: 'Savings', balance: '-20', currency: null };

  it('should return the initial state for an unknown action', () => {
    expect(accountReducer(undefined, { type: 'unknown' })).toEqual(initialState);
  });

  it('should replace data on loadAccountsSuccess', () => {
    const state = accountReducer({ data: [savings] }, loadAccountsSuccess({ data: [giro] }));
    expect(state.data).toEqual([giro]);
  });

  it('should clear data on loadAccountsError', () => {
    const state = accountReducer({ data: [giro] }, loadAccountsError());
    expect(state.data).toEqual([]);
  });

  it('should append the new account on addAccountSuccess without mutating the old state', () => {
    const before = { data: [giro] };
    const after = accountReducer(before, addAccountSuccess({ data: savings }));
    expect(after.data).toEqual([giro, savings]);
    expect(before.data).toEqual([giro]);
  });

  it('should leave state unchanged on loadAccounts, addAccount and addAccountError', () => {
    const before = { data: [giro] };
    expect(accountReducer(before, loadAccounts())).toBe(before);
    expect(accountReducer(before, addAccount({ data: savings }))).toBe(before);
    expect(accountReducer(before, addAccountError())).toBe(before);
  });
});
