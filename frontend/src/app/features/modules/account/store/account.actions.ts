import { createAction, props } from "@ngrx/store";
import { AccountDTO } from "src/app/dto/accountDTO";

enum AccountActions {
  LoadAccounts = '[Account] Load Data',
  LoadAccountsSuccess = '[Account] Load Data Success',
  LoadAccountsError = '[Account] Load Data Error',
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
