package com.neightec.neightecavserver.controller;

import com.neightec.neightecavserver.models.dto.WalletAccountDTO;
import com.neightec.neightecavserver.models.dto.WalletTransactionDTO;
import com.neightec.neightecavserver.services.interfaces.WalletAccountService;
import com.neightec.neightecavserver.services.interfaces.WalletTransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/wallet-account")
@Log4j2
@RequiredArgsConstructor
public class WalletAccountController {

    private final WalletAccountService walletAccountService;

    /**
     * REST all wallet accounts
     * @return list of wallet account DTO
     */
    @GetMapping(value = "/get-accounts-by-session-id", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WalletAccountDTO>> getAccountsByID(
        @RequestParam(value = "sessionId", required = false) UUID sessionID
    ) {
        log.info("/wallet-account/get-accounts-by-session-id called!");
        return ResponseEntity.ok(walletAccountService.getAccountsBySessionID(sessionID));
    }

    /**
     * TODO
     * add account
     * remove account
     * export csv
     */

}
