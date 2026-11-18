package com.neightec.neightecavserver.controller;

import com.neightec.neightecavserver.models.dto.WalletAccountDTO;
import com.neightec.neightecavserver.services.interfaces.WalletAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
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
     * Creates an account and links it to the session.
     * Without a session ID the backend falls back to com.neightec.server.temp.sessionID.
     * @return created account
     */
    @PostMapping(value = "/add-account-by-session-id",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WalletAccountDTO> addAccountBySessionID(
        @RequestParam(value = "sessionId", required = false) UUID sessionID,
        @RequestBody WalletAccountDTO account
    ) {
        log.info("/wallet-account/add-account-by-session-id called!");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(walletAccountService.addAccountBySessionID(sessionID, account));
    }

    /**
     * TODO
     * remove account
     * export csv
     */

}
