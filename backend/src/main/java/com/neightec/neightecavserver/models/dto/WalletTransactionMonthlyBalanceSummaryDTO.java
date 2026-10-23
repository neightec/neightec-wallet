package com.neightec.neightecavserver.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class WalletTransactionMonthlyBalanceSummaryDTO {
    private List<WalletTransactionMonthlyBalanceDTO> months;
    private WalletTransactionMonthlyBalanceDTO max_month;
}
