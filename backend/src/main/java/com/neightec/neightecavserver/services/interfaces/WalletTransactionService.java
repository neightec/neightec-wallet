package com.neightec.neightecavserver.services.interfaces;

import com.neightec.neightecavserver.models.dto.WalletTransactionDTO;
import com.neightec.neightecavserver.models.enums.WalletTransactionTypeEnum;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WalletTransactionService {

    List<WalletTransactionDTO> getAll();
    List<WalletTransactionDTO> getTransactionBalanceBalance(String typeEnum);
}
