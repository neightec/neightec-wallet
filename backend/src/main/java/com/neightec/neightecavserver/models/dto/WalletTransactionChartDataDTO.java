package com.neightec.neightecavserver.models.dto;

import java.math.BigDecimal;
import java.util.List;

public record WalletTransactionChartDataDTO(
        List<String> labels,
        List<BigDecimal> einnahmen,
        List<BigDecimal> ausgaben,
        List<BigDecimal> ersparnisse
) {
}
