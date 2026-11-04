import { createAction, props } from "@ngrx/store";
import { WalletTransactionDTO } from "src/app/dto/walletTransactionDTO";

enum TransactionActions {
  LoadTransactionTableState = '[Transaction Table] Load Data',
  LoadTransactionTableStateSuccess = '[Transaction Table] Load Data Success',
  LoadTransactionTableStateError = '[Transaction Table] Load Data Error',
}

export const loadTransactionTableState
  = createAction(TransactionActions.LoadTransactionTableState);

export const loadTransactionTableStateSuccess
  = createAction(
    TransactionActions.LoadTransactionTableStateSuccess,
    props<{ data: WalletTransactionDTO[] }>()
  );

export const loadTransactionTableStateError
  = createAction(TransactionActions.LoadTransactionTableStateError);
