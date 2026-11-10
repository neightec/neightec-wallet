import { Injectable } from "@angular/core";
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { catchError, map, mergeMap, of } from 'rxjs';
import {
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
        this.accountService.fetchAllAccounts().pipe(
          map(response => loadAccountsSuccess({ data: response })),
          catchError(() => of(loadAccountsError()))
        )
      )
    );
  });

  constructor(
    private actions$: Actions,
    private accountService: AccountService
  ) {}
}
