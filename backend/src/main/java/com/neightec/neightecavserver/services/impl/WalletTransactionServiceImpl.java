package com.neightec.neightecavserver.services.impl;

import com.neightec.neightecavserver.config.WalletConfig;
import com.neightec.neightecavserver.models.dto.WalletTransactionChartDataDTO;
import com.neightec.neightecavserver.models.dto.WalletTransactionMonthlyBalanceDTO;
import com.neightec.neightecavserver.models.dto.WalletTransactionMonthlyBalanceSummaryDTO;
import com.neightec.neightecavserver.models.dto.WalletTransactionDTO;
import com.neightec.neightecavserver.models.enums.WalletTransactionTypeEnum;
import com.neightec.neightecavserver.models.neightec_data.WalletSession;
import com.neightec.neightecavserver.repositories.interfaces.WalletSessionRepository;
import com.neightec.neightecavserver.repositories.interfaces.WalletTransactionRepository;
import com.neightec.neightecavserver.services.interfaces.WalletTransactionService;
import com.neightec.neightecavserver.services.mapper.WalletTransactionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.neightec.neightecavserver.models.enums.WalletTransactionTypeEnum.*;

@Service
@RequiredArgsConstructor
@Log4j2
public class WalletTransactionServiceImpl implements WalletTransactionService {

    private final WalletTransactionRepository walletTransactionRepository;
    private final WalletTransactionMapper walletTransactionMapper;
    private final WalletConfig walletConfig; // TODO use this
    private final WalletSessionRepository walletSessionRepository;

    @Override
    public List<WalletTransactionDTO> getAll() {
        return walletTransactionRepository.findAll()
                .stream()
                .map(walletTransactionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<WalletTransactionDTO> getTransactionByType(String typeEnum) {

        WalletTransactionTypeEnum walletTransactionTypeEnum;

        try {
            walletTransactionTypeEnum = WalletTransactionTypeEnum.valueOf(typeEnum);
        } catch (IllegalArgumentException iae) {
            log.error("Invalid transaction type enum: {}", typeEnum);
            return List.of();
        }

        return walletTransactionRepository.findTransactionByType(walletTransactionTypeEnum)
                .stream()
                .map(walletTransactionMapper::toDTO)
                .collect(Collectors.toList());

    }

    @Override
    public List<WalletTransactionDTO> getTransactionBalanceBySessionID(UUID sessionID) {
        WalletSession session = walletSessionRepository.findById(sessionID).orElse(null);

        if (session == null) {
            log.error("Wallet session not found for session ID: {}", sessionID);
            return List.of();
        }

        return walletTransactionRepository.findTransactionBySessionId(sessionID)
                .stream()
                .map(walletTransactionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public WalletTransactionMonthlyBalanceSummaryDTO getTransactionBalanceBySessionIDAndWalletTransactionType(UUID sessionID, String transactionType) {
        WalletSession session = walletSessionRepository.findById(sessionID).orElse(null);

        if (session == null) {
            log.error("Wallet session not found for session ID: {}", sessionID);
            return new WalletTransactionMonthlyBalanceSummaryDTO(List.of(), null);
        }

        List<WalletTransactionMonthlyBalanceDTO> months = walletTransactionRepository.findTransactionBySessionIdAndWalletTransactionType(sessionID, transactionType)
                .stream()
                .map(balance -> new WalletTransactionMonthlyBalanceDTO(
                        balance.getName(),
                        balance.getTotal(),
                        balance.getCurrency_type(),
                        balance.getMonth(),
                        balance.getYear(),
                        balance.getDifference(),
                        balance.getPercentage_change()))
                .collect(Collectors.toList());

        WalletTransactionMonthlyBalanceDTO maxMonth = months.stream()
                .max(Comparator.comparing(WalletTransactionMonthlyBalanceDTO::getTotal))
                .orElse(null);

        return new WalletTransactionMonthlyBalanceSummaryDTO(months, maxMonth);
    }

    @Override
    public WalletTransactionChartDataDTO getTransactionBalanceBySessionIDAndWallet(UUID sessionID) {
        WalletSession session = walletSessionRepository.findById(sessionID).orElse(null);

        if (session == null) {
            log.error("Wallet session not found for session ID: {}", sessionID);
            return null;
        }

        Map<String, BigDecimal> einnahmenByMonth = new LinkedHashMap<>();
        Map<String, BigDecimal> ausgabenByMonth = new LinkedHashMap<>();
        Map<String, BigDecimal> ersparnisseByMonth = new LinkedHashMap<>();

        List<WalletTransactionRepository.WalletTransactionBalanceByType> transactionBalance = walletTransactionRepository.findTransactionBySessionIdAndWallet(sessionID);

        if (transactionBalance == null) {
            return new WalletTransactionChartDataDTO(null, null, null, null);
        }

        transactionBalance.forEach(tb -> {
            if (tb == null) {
                return;
            }

            String month = tb.getMonth() + " " + tb.getYear();

            einnahmenByMonth.putIfAbsent(month, BigDecimal.ZERO);
            ausgabenByMonth.putIfAbsent(month, BigDecimal.ZERO);
            ersparnisseByMonth.putIfAbsent(month, BigDecimal.ZERO);

            if (EINKOMMEN.getWalletTransactionTypeName().equals(tb.getName())) {
                einnahmenByMonth.put(month, tb.getTotal());
            }

            if (AUSGABE.getWalletTransactionTypeName().equals(tb.getName())) {
                ausgabenByMonth.put(month, tb.getTotal());
            }

            if (ERSPARNIS.getWalletTransactionTypeName().equals(tb.getName())) {
                ersparnisseByMonth.put(month, tb.getTotal());
            }
        });

        return new WalletTransactionChartDataDTO(
            new ArrayList<>(einnahmenByMonth.keySet()),
            new ArrayList<>(einnahmenByMonth.values()),
            new ArrayList<>(ausgabenByMonth.values()),
            new ArrayList<>(ersparnisseByMonth.values())
        );
    }
}
