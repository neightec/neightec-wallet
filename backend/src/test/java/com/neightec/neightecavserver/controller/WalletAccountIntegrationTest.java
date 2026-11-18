package com.neightec.neightecavserver.controller;

import com.neightec.neightecavserver.models.neightec_data.WalletAccount;
import com.neightec.neightecavserver.models.neightec_data.WalletSessionToAccount;
import com.neightec.neightecavserver.repositories.interfaces.WalletAccountRepository;
import com.neightec.neightecavserver.repositories.interfaces.WalletSessionRepository;
import com.neightec.neightecavserver.repositories.interfaces.WalletSessionToAccountRepository;
import com.neightec.neightecavserver.services.impl.WalletAccountServiceImpl;
import com.neightec.neightecavserver.services.mapper.WalletAccountMapperImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
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
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration test for the account endpoint: real controller, real service and real
 * MapStruct mapper. Only the repositories (the database) are mocked.
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

    @MockBean
    private WalletSessionRepository walletSessionRepository;

    @MockBean
    private WalletSessionToAccountRepository walletSessionToAccountRepository;

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

    @Test
    void addAccountBySessionID_shouldPersistAccountLinkItAndReturnDTO() throws Exception {
        UUID accountId = UUID.randomUUID();
        when(walletSessionRepository.existsById(SESSION_ID)).thenReturn(true);
        when(walletAccountRepository.save(any(WalletAccount.class))).thenAnswer(invocation -> {
            WalletAccount saved = invocation.getArgument(0);
            saved.setId(accountId);
            return saved;
        });

        mockMvc.perform(post("/wallet-account/add-account-by-session-id")
                        .param("sessionId", SESSION_ID.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"  Sparkonto \",\"balance\":\"100,5\",\"currency\":\"€\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Sparkonto"))
                .andExpect(jsonPath("$.balance").value("100.5"));

        ArgumentCaptor<WalletSessionToAccount> linkCaptor = ArgumentCaptor.forClass(WalletSessionToAccount.class);
        verify(walletSessionToAccountRepository).save(linkCaptor.capture());
        assertThat(linkCaptor.getValue().getWallet_session_id()).isEqualTo(SESSION_ID);
        assertThat(linkCaptor.getValue().getWallet_account_id()).isEqualTo(accountId);
    }

    @Test
    void addAccountBySessionID_withUnknownSession_shouldReturnNotFoundAndPersistNothing() throws Exception {
        when(walletSessionRepository.existsById(SESSION_ID)).thenReturn(false);

        mockMvc.perform(post("/wallet-account/add-account-by-session-id")
                        .param("sessionId", SESSION_ID.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Giro\",\"balance\":\"1\"}"))
                .andExpect(status().isNotFound());

        verify(walletAccountRepository, never()).save(any());
        verify(walletSessionToAccountRepository, never()).save(any());
    }

    @Test
    void addAccountBySessionID_withInvalidBalance_shouldReturnBadRequest() throws Exception {
        when(walletSessionRepository.existsById(UUID.fromString(TEMP_SESSION))).thenReturn(true);

        mockMvc.perform(post("/wallet-account/add-account-by-session-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Giro\",\"balance\":\"abc\"}"))
                .andExpect(status().isBadRequest());

        verify(walletAccountRepository, never()).save(any());
    }

    private static WalletAccount account(String name, String balance) {
        WalletAccount account = new WalletAccount();
        account.setName(name);
        account.setBalance(new BigDecimal(balance));
        return account;
    }
}
