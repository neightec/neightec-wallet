package com.neightec.neightecavserver.services;

import com.neightec.neightecavserver.models.dto.FileTypesDTO;
import com.neightec.neightecavserver.models.dto.WalletTransactionDTO;
import com.neightec.neightecavserver.repositories.WalletTransactionRepository;
import com.neightec.neightecavserver.services.mapper.WalletTransactionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class WalletTransactionService {

    private final WalletTransactionRepository walletTransactionRepository;
    private final WalletTransactionMapper walletTransactionMapper;
    public List<WalletTransactionDTO> getAll() {
        return walletTransactionRepository.findAll()
                .stream()
                .map(walletTransactionMapper::toDTO)
                .collect(Collectors.toList());
    }
}
