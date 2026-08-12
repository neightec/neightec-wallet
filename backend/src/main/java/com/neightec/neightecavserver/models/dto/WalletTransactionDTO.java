package com.neightec.neightecavserver.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WalletTransactionDTO {
    private String name;
    private String category_type;
    private String price;
    private String currency_type;
}
