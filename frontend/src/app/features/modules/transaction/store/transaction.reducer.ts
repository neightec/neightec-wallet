import { createReducer, on } from "@ngrx/store";
import {
    loadTransactionTableStateError,
    loadTransactionTableStateSuccess
} from "./transaction.actions";
import {TransactionState} from "./transaction.state";

export const transactionReducerKey = 'transaction-reducer-key';

export const initialState: TransactionState = {
  data: []
};

export const transactionReducer = createReducer(
  initialState,
  on(
    loadTransactionTableStateSuccess,
    (state, action) => ({
      ...state as TransactionState,
      data: action.data,
    })
  ),
  on(
    loadTransactionTableStateError,
    (state, action) => ({
      ...state as TransactionState,
      data: [],
    })
  )
  
);