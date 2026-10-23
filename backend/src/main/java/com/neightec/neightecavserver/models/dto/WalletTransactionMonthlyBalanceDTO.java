package com.neightec.neightecavserver.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class WalletTransactionMonthlyBalanceDTO {
    private String name;
    private BigDecimal total;
    private String currency_type;
    private String month;
    private String year;
    private BigDecimal difference;
    private BigDecimal percentage_change;
}
