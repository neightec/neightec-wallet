package com.neightec.neightecavserver.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class WalletTransactionDTO {
    private String name;
    private String category_type;
    private BigDecimal price;
    private String currency_type;
}
