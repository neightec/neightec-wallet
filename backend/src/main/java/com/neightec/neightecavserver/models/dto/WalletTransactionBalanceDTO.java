package com.neightec.neightecavserver.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WalletTransactionBalanceDTO {
    private long totalIncome;
    private long totalExpense;
    private long totalSaving;
    private String totalComparisonIncomePrevMonth;
    private String totalComparisonOutcomePrevMonth;
    private String totalComparisonSavingPrevMonth;
}
