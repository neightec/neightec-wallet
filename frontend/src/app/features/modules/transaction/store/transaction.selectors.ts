import { createFeatureSelector, createSelector } from "@ngrx/store";
import * as TransactionReducer from './transaction.reducer';
import {TransactionState} from "./transaction.state";

export const selectFeature =
    createFeatureSelector<TransactionState>(TransactionReducer.transactionReducerKey);

export const selectTransactions =
    createSelector(selectFeature, (state: TransactionState) => state.data);