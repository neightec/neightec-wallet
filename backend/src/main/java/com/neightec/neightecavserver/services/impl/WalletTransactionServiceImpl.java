package com.neightec.neightecavserver.services.impl;

import com.neightec.neightecavserver.config.WalletConfig;
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

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
}
