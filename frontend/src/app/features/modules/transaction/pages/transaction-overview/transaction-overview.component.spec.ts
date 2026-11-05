import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { By } from '@angular/platform-browser';
import { MockStore, provideMockStore } from '@ngrx/store/testing';
import { WalletTransactionDTO } from 'src/app/dto/walletTransactionDTO';
import { loadTransactionTableState } from '../../store/transaction.actions';
import { transactionReducerKey } from '../../store/transaction.reducer';
import { TransactionOverviewComponent } from './transaction-overview.component';

describe('TransactionOverviewComponent', () => {
  let fixture: ComponentFixture<TransactionOverviewComponent>;
  let component: TransactionOverviewComponent;
  let store: MockStore;
  let dispatchSpy: jasmine.Spy;

  const transactions: WalletTransactionDTO[] = [
    { name: 'Salary', category_type: 'WORK', price: 2500, currency_type: 'EUR' },
    { name: 'Groceries', category_type: 'FOOD', price: 45.99, currency_type: 'EUR' },
    { name: 'Rent', category_type: 'HOME', price: 900, currency_type: 'EUR' },
  ];

  const manyTransactions = (count: number): WalletTransactionDTO[] =>
    Array.from({ length: count }, (_, i) => ({
      name: `Item ${String(i).padStart(3, '0')}`,
      category_type: 'MISC',
      price: i,
      currency_type: 'EUR',
    }));

  const setTransactions = (data: WalletTransactionDTO[]) => {
    store.setState({ [transactionReducerKey]: { data } });
    store.refreshState();
    fixture.detectChanges();
  };

  const bodyRows = (): HTMLElement[] =>
    fixture.debugElement.queryAll(By.css('tr.mat-mdc-row')).map(row => row.nativeElement);

  const cellTexts = (columnIndex: number): string[] =>
    bodyRows().map(row => row.querySelectorAll('td')[columnIndex].textContent!.trim());

  const clickHeader = (label: string) => {
    const header = fixture.debugElement
      .queryAll(By.css('th'))
      .find(h => h.nativeElement.textContent.includes(label))!;
    header.nativeElement.click();
    fixture.detectChanges();
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TransactionOverviewComponent, NoopAnimationsModule],
      providers: [provideMockStore({ initialState: { [transactionReducerKey]: { data: [] } } })],
    });

    store = TestBed.inject(MockStore);
    dispatchSpy = spyOn(store as any, 'dispatch').and.callThrough();
    fixture = TestBed.createComponent(TransactionOverviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should dispatch loadTransactionTableState on init', () => {
    expect(dispatchSpy).toHaveBeenCalledWith(loadTransactionTableState());
  });

  it('should render the four column headers', () => {
    const headers = fixture.debugElement.queryAll(By.css('th')).map(h => h.nativeElement.textContent.trim());

    expect(headers).toEqual(['Name', 'Kategorie', 'Preis', 'Währung']);
  });

  it('should render no rows when there are no transactions', () => {
    expect(bodyRows().length).toBe(0);
  });

  it('should render one row per transaction with formatted price', () => {
    setTransactions(transactions);

    expect(bodyRows().length).toBe(3);
    expect(cellTexts(0)).toEqual(['Salary', 'Groceries', 'Rent']);
    expect(cellTexts(1)).toEqual(['WORK', 'FOOD', 'HOME']);
    expect(cellTexts(2)).toEqual(['2,500.00', '45.99', '900.00']);
    expect(cellTexts(3)).toEqual(['EUR', 'EUR', 'EUR']);
  });

  it('should update the table when the store emits new data', () => {
    setTransactions(transactions);
    setTransactions([transactions[0]]);

    expect(cellTexts(0)).toEqual(['Salary']);
  });

  describe('sorting', () => {
    beforeEach(() => setTransactions(transactions));

    it('should sort by name ascending on first header click', () => {
      clickHeader('Name');

      expect(cellTexts(0)).toEqual(['Groceries', 'Rent', 'Salary']);
    });

    it('should sort by name descending on second header click', () => {
      clickHeader('Name');
      clickHeader('Name');

      expect(cellTexts(0)).toEqual(['Salary', 'Rent', 'Groceries']);
    });

    it('should sort the price column numerically, not alphabetically', () => {
      clickHeader('Preis');

      expect(cellTexts(2)).toEqual(['45.99', '900.00', '2,500.00']);
    });

    it('should restore the original order on third header click', () => {
      clickHeader('Name');
      clickHeader('Name');
      clickHeader('Name');

      expect(cellTexts(0)).toEqual(['Salary', 'Groceries', 'Rent']);
    });
  });

  describe('pagination', () => {
    it('should offer the page sizes 10, 20, 50 and 100', () => {
      expect(component.pageSizeOptions).toEqual([10, 20, 50, 100]);
    });

    it('should show only the first 10 rows by default', () => {
      setTransactions(manyTransactions(25));

      expect(bodyRows().length).toBe(10);
      expect(cellTexts(0)[0]).toBe('Item 000');
      expect(cellTexts(0)[9]).toBe('Item 009');
    });

    it('should show the next rows on next page', () => {
      setTransactions(manyTransactions(25));

      fixture.debugElement.query(By.css('.mat-mdc-paginator-navigation-next')).nativeElement.click();
      fixture.detectChanges();

      expect(bodyRows().length).toBe(10);
      expect(cellTexts(0)[0]).toBe('Item 010');
    });

    it('should show the remaining rows on the last page', () => {
      setTransactions(manyTransactions(25));

      fixture.debugElement.query(By.css('.mat-mdc-paginator-navigation-last')).nativeElement.click();
      fixture.detectChanges();

      expect(bodyRows().length).toBe(5);
      expect(cellTexts(0)[0]).toBe('Item 020');
    });

    it('should show more rows when the page size is increased', () => {
      setTransactions(manyTransactions(25));

      component.dataSource.paginator!.pageSize = 20;
      component.dataSource.paginator!._changePageSize(20);
      fixture.detectChanges();

      expect(bodyRows().length).toBe(20);
    });

    it('should sort across all pages, not only the visible one', () => {
      setTransactions(manyTransactions(25));

      clickHeader('Name');
      clickHeader('Name');

      expect(cellTexts(0)[0]).toBe('Item 024');
    });
  });
});
