import { Injectable } from "@angular/core";
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { catchError, map, mergeMap, of } from 'rxjs';
import {
  addAccount,
  addAccountError,
  addAccountSuccess,
  loadAccounts,
  loadAccountsError,
  loadAccountsSuccess,
} from "./account.actions";
import { AccountService } from "../../../../services/account.service";

@Injectable()
export class AccountEffects {

  loadAccounts$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(loadAccounts),
      mergeMap(() =>
        this.accountService.fetchAccountsBySessionID(null).pipe(
          map(response => loadAccountsSuccess({ data: response })),
          catchError(() => of(loadAccountsError()))
        )
      )
    );
  });

  addAccount$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(addAccount),
      mergeMap(() =>
        this.accountService.addAccountBySessionID(null).pipe(
          map(response => addAccountSuccess({ data: response })),
          catchError(() => of(addAccountError()))
        )
      )
    );
  });

  constructor(
    private actions$: Actions,
    private accountService: AccountService
  ) {}
}
