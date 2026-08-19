package com.neightec.neightecavserver.controller;

import com.neightec.neightecavserver.models.dto.WalletTransactionDTO;
import com.neightec.neightecavserver.services.FileStorageService;
import com.neightec.neightecavserver.services.WalletTransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/wallet-transaction")
@Log4j2
@RequiredArgsConstructor
public class WalletTransactionController {

    private final WalletTransactionService walletTransactionService;

    /**
     * REST all wallet transactions
     * @return boolean
     */
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WalletTransactionDTO>> getAllFiles() {
        log.info("/wallet-transaction/all called!");
        return ResponseEntity.ok(walletTransactionService.getAll());
    }

}
