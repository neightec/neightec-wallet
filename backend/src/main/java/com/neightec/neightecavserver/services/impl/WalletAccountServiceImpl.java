package com.neightec.neightecavserver.services.impl;

import com.neightec.neightecavserver.models.dto.WalletAccountDTO;
import com.neightec.neightecavserver.models.neightec_data.WalletAccount;
import com.neightec.neightecavserver.models.neightec_data.WalletSessionToAccount;
import com.neightec.neightecavserver.repositories.interfaces.WalletAccountRepository;
import com.neightec.neightecavserver.repositories.interfaces.WalletSessionRepository;
import com.neightec.neightecavserver.repositories.interfaces.WalletSessionToAccountRepository;
import com.neightec.neightecavserver.services.interfaces.WalletAccountService;
import com.neightec.neightecavserver.services.mapper.WalletAccountMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WalletAccountServiceImpl implements WalletAccountService {

    private final WalletAccountRepository walletAccountRepository;
    private final WalletAccountMapper walletAccountMapper;
    private final WalletSessionRepository walletSessionRepository;
    private final WalletSessionToAccountRepository walletSessionToAccountRepository;

    @Value("${com.neightec.server.temp.sessionID}")
    private String sessionID;

    @Override
    public List<WalletAccountDTO> getAccountsBySessionID(UUID sessionID) {
        sessionID = sessionID == null ? UUID.fromString(this.sessionID) : sessionID;

        return walletAccountRepository.getAccountsBySessionID(sessionID)
                .stream()
                .map(walletAccountMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public WalletAccountDTO addAccountBySessionID(UUID sessionID, WalletAccountDTO account) {
        sessionID = sessionID == null ? UUID.fromString(this.sessionID) : sessionID;

        if (!walletSessionRepository.existsById(sessionID)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Wallet session not found: " + sessionID);
        }
        if (account == null || account.getName() == null || account.getName().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account name is required");
        }

        BigDecimal balance;
        try {
            balance = account.getBalance() == null || account.getBalance().isBlank()
                    ? BigDecimal.ZERO
                    : new BigDecimal(account.getBalance().trim().replace(',', '.'));
        } catch (NumberFormatException nfe) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid balance: " + account.getBalance());
        }

        WalletAccount walletAccount = new WalletAccount();
        walletAccount.setName(account.getName().trim());
        walletAccount.setBalance(balance);
        walletAccount.setCreation_timestamp(Timestamp.from(Instant.now()));
        walletAccount.setIs_archived(false);
        walletAccount = walletAccountRepository.save(walletAccount);

        WalletSessionToAccount link = new WalletSessionToAccount();
        link.setWallet_session_id(sessionID);
        link.setWallet_account_id(walletAccount.getId());
        walletSessionToAccountRepository.save(link);

        return walletAccountMapper.toDTO(walletAccount);
    }
}
