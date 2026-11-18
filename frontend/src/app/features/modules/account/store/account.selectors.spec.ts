import { AccountDTO } from 'src/app/dto/accountDTO';
import { accountReducerKey } from './account.reducer';
import { selectAccounts, selectFeature } from './account.selectors';

describe('Account selectors', () => {
  const accounts: AccountDTO[] = [{ name: 'Giro', balance: '1', currency: '€' }];
  const rootState = { [accountReducerKey]: { data: accounts } };

  it('should select the feature state', () => {
    expect(selectFeature(rootState)).toEqual({ data: accounts });
  });

  it('should select the accounts', () => {
    expect(selectAccounts(rootState)).toEqual(accounts);
  });
});
