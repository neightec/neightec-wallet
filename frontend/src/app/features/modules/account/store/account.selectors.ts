import { createFeatureSelector, createSelector } from "@ngrx/store";
import * as AccountReducer from './account.reducer';
import { AccountState } from "./account.state";

export const selectFeature =
  createFeatureSelector<AccountState>(AccountReducer.accountReducerKey);

export const selectAccounts =
  createSelector(selectFeature, (state: AccountState) => state.data);
