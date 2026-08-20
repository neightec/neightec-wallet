package com.neightec.neightecavserver.controller;

import com.neightec.neightecavserver.models.dto.WalletTransactionDTO;
import com.neightec.neightecavserver.services.interfaces.WalletTransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping(value = "/get-by-transaction-type", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WalletTransactionDTO>> getByTransactionType(
            @RequestParam("typeEnum") String typeEnum) {
        log.info("/get-by-transaction-type called with typeEnum={}", typeEnum);
        return ResponseEntity.ok(walletTransactionService.getTransactionBalanceBalance(typeEnum));
    }

}
