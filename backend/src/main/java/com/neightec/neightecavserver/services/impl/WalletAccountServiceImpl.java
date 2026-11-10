package com.neightec.neightecavserver.services.impl;

import com.neightec.neightecavserver.models.dto.WalletAccountDTO;
import com.neightec.neightecavserver.repositories.interfaces.WalletAccountRepository;
import com.neightec.neightecavserver.services.interfaces.WalletAccountService;
import com.neightec.neightecavserver.services.mapper.WalletAccountMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WalletAccountServiceImpl implements WalletAccountService {

    private final WalletAccountRepository walletAccountRepository;
    private final WalletAccountMapper walletAccountMapper;

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
}
