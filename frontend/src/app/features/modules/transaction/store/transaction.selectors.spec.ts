import { WalletTransactionDTO } from 'src/app/dto/walletTransactionDTO';
import { transactionReducerKey } from './transaction.reducer';
import { selectFeature, selectTransactions } from './transaction.selectors';
import { TransactionState } from './transaction.state';

describe('transaction selectors', () => {
  const transactions: WalletTransactionDTO[] = [
    { name: 'Salary', category_type: 'WORK', price: 2500, currency_type: 'EUR' },
  ];

  const appState = (feature: TransactionState) => ({ [transactionReducerKey]: feature });

  it('selectFeature should return the transaction feature state', () => {
    const feature: TransactionState = { data: transactions };

    expect(selectFeature(appState(feature))).toBe(feature);
  });

  it('selectTransactions should return the loaded transactions', () => {
    expect(selectTransactions(appState({ data: transactions }))).toEqual(transactions);
  });

  it('selectTransactions should return an empty array when nothing is loaded', () => {
    expect(selectTransactions(appState({ data: [] }))).toEqual([]);
  });
});
