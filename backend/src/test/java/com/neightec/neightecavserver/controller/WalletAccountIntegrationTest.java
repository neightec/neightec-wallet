package com.neightec.neightecavserver.controller;

import com.neightec.neightecavserver.models.neightec_data.WalletAccount;
import com.neightec.neightecavserver.repositories.interfaces.WalletAccountRepository;
import com.neightec.neightecavserver.services.impl.WalletAccountServiceImpl;
import com.neightec.neightecavserver.services.mapper.WalletAccountMapperImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration test for the account endpoint: real controller, real service and real
 * MapStruct mapper. Only the repository (the database) is mocked.
 */
@WebMvcTest(WalletAccountController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import({WalletAccountServiceImpl.class, WalletAccountMapperImpl.class})
@TestPropertySource(properties = "com.neightec.server.temp.sessionID=" + WalletAccountIntegrationTest.TEMP_SESSION)
class WalletAccountIntegrationTest {

    static final String TEMP_SESSION = "22222222-2222-2222-2222-222222222222";
    private static final UUID SESSION_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WalletAccountRepository walletAccountRepository;

    @Test
    void getAccountsByID_shouldMapEntitiesToDTOsForSession() throws Exception {
        when(walletAccountRepository.getAccountsBySessionID(SESSION_ID))
                .thenReturn(List.of(account("Girokonto", "2450.75"), account("Kreditkarte", "-320.40")));

        mockMvc.perform(get("/wallet-account/get-accounts-by-session-id")
                        .param("sessionId", SESSION_ID.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Girokonto"))
                .andExpect(jsonPath("$[0].balance").value("2450.75"))
                .andExpect(jsonPath("$[0].currency").doesNotExist())
                .andExpect(jsonPath("$[1].name").value("Kreditkarte"))
                .andExpect(jsonPath("$[1].balance").value("-320.40"));
    }

    @Test
    void getAccountsByID_withoutSessionId_shouldUseTempSessionFromProperties() throws Exception {
        when(walletAccountRepository.getAccountsBySessionID(UUID.fromString(TEMP_SESSION)))
                .thenReturn(List.of(account("Girokonto", "2450.75")));

        mockMvc.perform(get("/wallet-account/get-accounts-by-session-id")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Girokonto"));

        verify(walletAccountRepository).getAccountsBySessionID(UUID.fromString(TEMP_SESSION));
    }

    @Test
    void getAccountsByID_withUnknownSession_shouldReturnEmptyList() throws Exception {
        when(walletAccountRepository.getAccountsBySessionID(SESSION_ID)).thenReturn(List.of());

        mockMvc.perform(get("/wallet-account/get-accounts-by-session-id")
                        .param("sessionId", SESSION_ID.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    private static WalletAccount account(String name, String balance) {
        WalletAccount account = new WalletAccount();
        account.setName(name);
        account.setBalance(new BigDecimal(balance));
        return account;
    }
}
