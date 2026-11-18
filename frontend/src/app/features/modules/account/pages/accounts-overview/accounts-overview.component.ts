import { Component, OnInit, inject } from '@angular/core';
import { AsyncPipe } from '@angular/common';
import { Store } from '@ngrx/store';
import { AccountsCardComponent } from '../../components/accounts-card/accounts-card.component';
import { loadAccounts } from '../../store/account.actions';
import { selectAccounts } from '../../store/account.selectors';

@Component({
  selector: 'neightec-accounts-overview',
  imports: [AsyncPipe, AccountsCardComponent],
  templateUrl: './accounts-overview.component.html',
  styleUrl: './accounts-overview.component.scss'
})
export class AccountsOverviewComponent implements OnInit {

  private store = inject(Store);

  accounts$ = this.store.select(selectAccounts);

  ngOnInit(): void {
    this.store.dispatch(loadAccounts());
  }
}
