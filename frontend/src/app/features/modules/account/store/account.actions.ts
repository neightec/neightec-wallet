import { createAction, props } from "@ngrx/store";
import { AccountDTO } from "src/app/dto/accountDTO";

enum AccountActions {
  LoadAccounts = '[Account] Load Data',
  LoadAccountsSuccess = '[Account] Load Data Success',
  LoadAccountsError = '[Account] Load Data Error',
  AddAccount = '[Account] Add Account',
  AddAccountSuccess = '[Account] Add Account Success',
  AddAccountError = '[Account] Add Account Error',
}

export const loadAccounts
  = createAction(AccountActions.LoadAccounts);

export const loadAccountsSuccess
  = createAction(
    AccountActions.LoadAccountsSuccess,
    props<{ data: AccountDTO[] }>()
  );

export const loadAccountsError
  = createAction(AccountActions.LoadAccountsError);

export const addAccount
  = createAction(AccountActions.AddAccount);

export const addAccountSuccess
  = createAction(
    AccountActions.AddAccountSuccess,
    props<{ data: AccountDTO }>()
  );

export const addAccountError
  = createAction(AccountActions.AddAccountError);
