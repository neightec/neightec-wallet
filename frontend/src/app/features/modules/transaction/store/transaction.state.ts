import {WalletTransactionDTO} from "../../../../dto/walletTransactionDTO";

export interface TransactionState {
    data: WalletTransactionDTO[] | [];
}