package com.neightec.neightecavserver.controller;

import com.neightec.neightecavserver.models.dto.WalletAccountDTO;
import com.neightec.neightecavserver.services.interfaces.WalletAccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
}
