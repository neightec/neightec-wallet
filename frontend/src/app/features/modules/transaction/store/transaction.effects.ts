import { Injectable } from "@angular/core";
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { catchError, map, mergeMap, of} from 'rxjs';
import {
    loadTransactionTableState,
    loadTransactionTableStateError,
    loadTransactionTableStateSuccess,
} from "./transaction.actions";
import {WalletTransactionService} from "../../../../services/wallet-transaction.service";

@Injectable()
export class TransactionEffects {
  
  loadDataTransactionTable$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(loadTransactionTableState),
      mergeMap(action => {
        return this.walletTransactionService.fetchAllWalletTransactionBySessionID(null).pipe(
          map(response => loadTransactionTableStateSuccess({ data: response })),
          catchError(() => of(loadTransactionTableStateError()))
        )
      })
    );
  });

  constructor(
    private actions$: Actions,
    private walletTransactionService: WalletTransactionService
  ) {}
}