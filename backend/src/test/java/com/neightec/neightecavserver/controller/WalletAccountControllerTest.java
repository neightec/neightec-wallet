package com.neightec.neightecavserver.controller;

import com.neightec.neightecavserver.models.dto.WalletAccountDTO;
import com.neightec.neightecavserver.services.interfaces.WalletAccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WalletAccountController.class)
@AutoConfigureMockMvc(addFilters = false)
class WalletAccountControllerTest {

    private static final UUID SESSION_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WalletAccountService walletAccountService;

    @Test
    void getAccountsByID_shouldReturnAccountsOfSession() throws Exception {
        when(walletAccountService.getAccountsBySessionID(SESSION_ID)).thenReturn(List.of(
                new WalletAccountDTO("Girokonto", "2450.75", "EUR"),
                new WalletAccountDTO("Sparkonto", "10200.00", "EUR")
        ));

        mockMvc.perform(get("/wallet-account/get-accounts-by-session-id")
                        .param("sessionId", SESSION_ID.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Girokonto"))
                .andExpect(jsonPath("$[0].balance").value("2450.75"))
                .andExpect(jsonPath("$[0].currency").value("EUR"))
                .andExpect(jsonPath("$[1].name").value("Sparkonto"));
    }

    @Test
    void getAccountsByID_withoutSessionId_shouldPassNullToService() throws Exception {
        when(walletAccountService.getAccountsBySessionID(null)).thenReturn(List.of());

        mockMvc.perform(get("/wallet-account/get-accounts-by-session-id")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(walletAccountService).getAccountsBySessionID(null);
    }

    @Test
    void getAccountsByID_withInvalidSessionId_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(get("/wallet-account/get-accounts-by-session-id")
                        .param("sessionId", "not-a-uuid")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addAccountBySessionID_shouldReturnCreatedAccount() throws Exception {
        WalletAccountDTO request = new WalletAccountDTO("Giro", "12.5", "€");
        when(walletAccountService.addAccountBySessionID(SESSION_ID, request))
                .thenReturn(new WalletAccountDTO("Giro", "12.50", null));

        mockMvc.perform(post("/wallet-account/add-account-by-session-id")
                        .param("sessionId", SESSION_ID.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Giro\",\"balance\":\"12.5\",\"currency\":\"€\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Giro"))
                .andExpect(jsonPath("$.balance").value("12.50"));
    }

    @Test
    void addAccountBySessionID_withoutSessionId_shouldPassNullToService() throws Exception {
        when(walletAccountService.addAccountBySessionID(eq(null), any()))
                .thenReturn(new WalletAccountDTO("Giro", "0", null));

        mockMvc.perform(post("/wallet-account/add-account-by-session-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Giro\",\"balance\":\"0\"}"))
                .andExpect(status().isCreated());

        verify(walletAccountService).addAccountBySessionID(eq(null), any());
    }

    @Test
    void addAccountBySessionID_whenServiceRejects_shouldPropagateStatus() throws Exception {
        when(walletAccountService.addAccountBySessionID(eq(SESSION_ID), any()))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Wallet session not found"));

        mockMvc.perform(post("/wallet-account/add-account-by-session-id")
                        .param("sessionId", SESSION_ID.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Giro\",\"balance\":\"1\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void addAccountBySessionID_withoutBody_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/wallet-account/add-account-by-session-id")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
