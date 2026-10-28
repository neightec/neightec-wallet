package com.neightec.neightecavserver.services.interfaces;

import com.neightec.neightecavserver.models.dto.WalletTransactionChartDataDTO;
import com.neightec.neightecavserver.models.dto.WalletTransactionDTO;
import com.neightec.neightecavserver.models.dto.WalletTransactionMonthlyBalanceDTO;
import com.neightec.neightecavserver.models.dto.WalletTransactionMonthlyBalanceSummaryDTO;
import com.neightec.neightecavserver.repositories.interfaces.WalletTransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface WalletTransactionService {

    List<WalletTransactionDTO> getAll();
    List<WalletTransactionDTO> getTransactionByType(String typeEnum);
    List<WalletTransactionDTO> getTransactionBalanceBySessionID(UUID sessionID);
    WalletTransactionMonthlyBalanceSummaryDTO getTransactionBalanceBySessionIDAndWalletTransactionType(UUID sessionID, String transactionType);
    WalletTransactionChartDataDTO getTransactionBalanceBySessionIDAndWallet(UUID sessionID);
}
