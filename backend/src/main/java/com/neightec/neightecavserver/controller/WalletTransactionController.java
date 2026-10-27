package com.neightec.neightecavserver.controller;

import com.neightec.neightecavserver.models.dto.WalletTransactionDTO;
import com.neightec.neightecavserver.models.dto.WalletTransactionMonthlyBalanceDTO;
import com.neightec.neightecavserver.models.dto.WalletTransactionMonthlyBalanceSummaryDTO;
import com.neightec.neightecavserver.repositories.interfaces.WalletTransactionRepository;
import com.neightec.neightecavserver.services.interfaces.WalletTransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/wallet-transaction")
@Log4j2
@RequiredArgsConstructor
public class WalletTransactionController {

    private final WalletTransactionService walletTransactionService;

    /**
     * REST all wallet transactions
     * @return list of wallet transaction DTO
     */
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WalletTransactionDTO>> getAllFiles() {
        log.info("/wallet-transaction/all called!");
        return ResponseEntity.ok(walletTransactionService.getAll());
    }

    /**
     * REST all wallet transactions by typeEnum
     * @return list of wallet transaction DTO
     */
    @GetMapping(value = "/get-transaction-by-type", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WalletTransactionDTO>> getByTransactionType(
            @RequestParam("typeEnum") String typeEnum) {
        log.info("/get-transaction-by-type called with typeEnum={}", typeEnum);
        return ResponseEntity.ok(walletTransactionService.getTransactionByType(typeEnum));
    }

    /**
     * REST all wallet transactions by typeEnum
     * api to get dto values for charts
     * @return list of wallet transaction DTO
     */
    @GetMapping(value = "/get-transaction-balance", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WalletTransactionMonthlyBalanceSummaryDTO> getTransactionBalance(
            @RequestParam("sessionId") UUID sessionID,
            @RequestParam("transactionType") String transactionType
    ) {
        log.info("/get-transaction-balance called with sessionID={}, transactionType={}", sessionID, transactionType);
        return ResponseEntity.ok(walletTransactionService.getTransactionBalanceBySessionIDAndWalletTransactionType(sessionID, transactionType));
    }

    /**
     * REST all wallet transactions by typeEnum
     * api to get dto values for charts
     * @return list of wallet transaction DTO
     */
    @GetMapping(value = "/get-transaction-balance-chart", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getTransactionBalanceChart(
            @RequestParam("sessionId") UUID sessionID
    ) {
        log.info("/get-transaction-balance-chart called with sessionID={}", sessionID);
        return ResponseEntity.ok(walletTransactionService.getTransactionBalanceBySessionIDAndWallet(sessionID));
    }

}
