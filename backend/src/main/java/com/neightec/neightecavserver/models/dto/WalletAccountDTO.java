package com.neightec.neightecavserver.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletAccountDTO {
    private String name;
    private String balance;
    private String currency;
}
