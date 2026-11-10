package com.neightec.neightecavserver.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class WalletAccountDTO {
    private String name;
    private String balance;
    private String currency;
}
