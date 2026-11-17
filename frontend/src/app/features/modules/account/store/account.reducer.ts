import { createReducer, on } from "@ngrx/store";
import { addAccountSuccess, loadAccountsError, loadAccountsSuccess } from "./account.actions";
import { AccountState } from "./account.state";

export const accountReducerKey = 'account-reducer-key';

export const initialState: AccountState = {
  data: []
};

export const accountReducer = createReducer(
  initialState,
  on(loadAccountsSuccess, (state, action) => ({
    ...state,
    data: action.data,
  })),
  on(loadAccountsError, (state) => ({
    ...state,
    data: [],
  })),
  on(addAccountSuccess, (state, action) => ({
    ...state,
    data: [...state.data, action.data],
  }))
);
