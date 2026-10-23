# README #

This README would normally document whatever steps are necessary to get your application up and running.

### What is this repository for? ###

* Quick summary
* Version
* [Learn Markdown](https://bitbucket.org/tutorials/markdowndemo)

### How do I get set up? ###

* Summary of set up
* Configuration
* Dependencies
* Database configuration
* How to run tests
* Deployment instructions

### Contribution guidelines ###

* Writing tests
* Code review
* Other guidelines

### Who do I talk to? ###

* Repo owner or admin
* Other community or team contact

## Monthly balance comparison (`/wallet-transaction/get-transaction-balance`)

Returns the monthly totals of one transaction type (e.g. `Einnahmen` or `Ausgaben`) for a wallet session,
how each month changed compared with the previous month, and the month with the highest total.

### Request

```
GET /wallet-transaction/get-transaction-balance?sessionId=<uuid>&transactionType=Einnahmen
```

| Parameter         | Description                                                         |
|-------------------|---------------------------------------------------------------------|
| `sessionId`       | Wallet session ID                                                   |
| `transactionType` | Name in `wallet_transaction_type.name`, e.g. `Einnahmen`, `Ausgaben` |

Unknown session: `{ "months": [], "max_month": null }`.

### Calculation

Implemented in SQL in `WalletTransactionRepository.findTransactionBySessionIdAndWalletTransactionType`;
`max_month` is picked in `WalletTransactionServiceImpl`.

1. **Monthly total**: sum `wallet_transaction.price` of the session's transactions of the requested type,
   grouped by type, currency and month (`date_trunc('month', timestamp)`).
2. **Previous month**: for every month, take the total of the previous month of the same type and
   currency (`LAG(total)` over months in ascending order).
3. **Difference**: `difference = total - previous_total`
4. **Percentage change**: `percentage_change = round(difference * 100 / previous_total, 2)`
   - positive = increase, negative = decrease
5. **Max month**: the month with the highest `total` across all returned months.

Edge cases:

- First month has no previous month: `difference` and `percentage_change` are `null`.
- Previous total is `0`: `percentage_change` is `null` (no division by zero); `difference` is still set.
- "Previous month" means the previous month **that has data**, not the calendar month before.
  If October has no transactions, November is compared with September.
- Different currencies are calculated separately (they are never mixed).
- If several months share the highest total, one of them is returned as `max_month`.

### Example

| Month         | Total   | difference | percentage_change            |
|---------------|---------|------------|------------------------------|
| November 2026 | 1000 €  | `null`     | `null`                       |
| December 2026 | 1020 €  | 20         | 20 * 100 / 1000 = **2.00**   |
| January 2027  | 918 €   | -102       | -102 * 100 / 1020 = **-10.00** |

`max_month` = December 2026 (1020 €).

### Response

```json
{
  "months": [
    { "name": "Einnahmen", "total": 1000, "currency_type": "EUR", "month": "November", "year": "2026", "difference": null, "percentage_change": null },
    { "name": "Einnahmen", "total": 1020, "currency_type": "EUR", "month": "December", "year": "2026", "difference": 20, "percentage_change": 2.00 },
    { "name": "Einnahmen", "total": 918, "currency_type": "EUR", "month": "January", "year": "2027", "difference": -102, "percentage_change": -10.00 }
  ],
  "max_month": { "name": "Einnahmen", "total": 1020, "currency_type": "EUR", "month": "December", "year": "2026", "difference": 20, "percentage_change": 2.00 }
}
```
