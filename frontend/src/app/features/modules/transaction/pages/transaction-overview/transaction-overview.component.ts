import { Component, DestroyRef, OnInit, ViewChild, inject } from '@angular/core';
import { DecimalPipe } from '@angular/common';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { Store } from '@ngrx/store';
import { WalletTransactionDTO } from 'src/app/dto/walletTransactionDTO';
import { loadTransactionTableState } from '../../store/transaction.actions';
import { selectTransactions } from '../../store/transaction.selectors';

@Component({
  selector: 'neightec-transaction-overview',
  templateUrl: './transaction-overview.component.html',
  styleUrls: ['./transaction-overview.component.scss'],
  standalone: true,
  imports: [MatTableModule, MatSortModule, MatPaginatorModule, DecimalPipe]
})
export class TransactionOverviewComponent implements OnInit {

  displayedColumns: string[] = ['name', 'category_type', 'price', 'currency_type'];
  pageSizeOptions: number[] = [10, 20, 50, 100];
  dataSource = new MatTableDataSource<WalletTransactionDTO>([]);

  private destroyRef = inject(DestroyRef);

  @ViewChild(MatSort) set sort(sort: MatSort) {
    this.dataSource.sort = sort;
  }

  @ViewChild(MatPaginator) set paginator(paginator: MatPaginator) {
    this.dataSource.paginator = paginator;
  }

  constructor(private store: Store) {}

  ngOnInit(): void {
    this.store.select(selectTransactions)
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe(data => this.dataSource.data = data ?? []);

    this.store.dispatch(loadTransactionTableState());
  }
}
