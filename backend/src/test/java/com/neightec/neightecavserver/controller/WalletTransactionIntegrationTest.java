package com.neightec.neightecavserver.controller;

import com.neightec.neightecavserver.config.WalletConfig;
import com.neightec.neightecavserver.models.neightec_data.WalletTransaction;
import com.neightec.neightecavserver.repositories.interfaces.WalletSessionRepository;
import com.neightec.neightecavserver.repositories.interfaces.WalletTransactionRepository;
import com.neightec.neightecavserver.services.impl.WalletTransactionServiceImpl;
import com.neightec.neightecavserver.services.mapper.WalletTransactionMapperImpl;
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
 * Integration test for the transaction table endpoint: real controller, real service and real
 * MapStruct mapper. Only the repositories (the database) are mocked.
 */
@WebMvcTest(WalletTransactionController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import({WalletTransactionServiceImpl.class, WalletTransactionMapperImpl.class})
@TestPropertySource(properties = "com.neightec.server.temp.sessionID=" + WalletTransactionIntegrationTest.TEMP_SESSION)
class WalletTransactionIntegrationTest {

    static final String TEMP_SESSION = "22222222-2222-2222-2222-222222222222";
    private static final UUID SESSION_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WalletTransactionRepository walletTransactionRepository;

    @MockBean
    private WalletSessionRepository walletSessionRepository;

    @MockBean
    private WalletConfig walletConfig;

    @Test
    void getTransactionsByID_shouldMapEntitiesToDTOsForSession() throws Exception {
        when(walletTransactionRepository.findTransactionBySessionId(SESSION_ID))
                .thenReturn(List.of(transaction("Salary", "WORK", "2500.00"), transaction("Groceries", "FOOD", "45.99")));

        mockMvc.perform(get("/wallet-transaction/get-transaction-by-id")
                        .param("sessionId", SESSION_ID.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Salary"))
                .andExpect(jsonPath("$[0].category_type").value("WORK"))
                .andExpect(jsonPath("$[0].price").value(2500.00))
                .andExpect(jsonPath("$[0].currency_type").value("EUR"))
                .andExpect(jsonPath("$[1].name").value("Groceries"))
                .andExpect(jsonPath("$[1].price").value(45.99));
    }

    @Test
    void getTransactionsByID_withoutSessionId_shouldUseTempSessionFromProperties() throws Exception {
        when(walletTransactionRepository.findTransactionBySessionId(UUID.fromString(TEMP_SESSION)))
                .thenReturn(List.of(transaction("Salary", "WORK", "2500.00")));

        mockMvc.perform(get("/wallet-transaction/get-transaction-by-id")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Salary"));

        verify(walletTransactionRepository).findTransactionBySessionId(UUID.fromString(TEMP_SESSION));
    }

    @Test
    void getTransactionsByID_withUnknownSession_shouldReturnEmptyList() throws Exception {
        when(walletTransactionRepository.findTransactionBySessionId(SESSION_ID)).thenReturn(List.of());

        mockMvc.perform(get("/wallet-transaction/get-transaction-by-id")
                        .param("sessionId", SESSION_ID.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    private static WalletTransaction transaction(String name, String category, String price) {
        WalletTransaction transaction = new WalletTransaction();
        transaction.setName(name);
        transaction.setCategory_type(category);
        transaction.setPrice(new BigDecimal(price));
        transaction.setCurrency_type("EUR");
        return transaction;
    }
}
