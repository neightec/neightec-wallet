package com.neightec.neightecavserver.controller;

import com.neightec.neightecavserver.models.dto.WalletTransactionDTO;
import com.neightec.neightecavserver.services.interfaces.WalletTransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(WalletTransactionController.class)
@AutoConfigureMockMvc(addFilters = false)
public class WalletTransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WalletTransactionService walletTransactionService;

    @Test
    void getAllFiles_shouldReturnAllWalletTransactions() throws Exception {
        List<WalletTransactionDTO> transactions = List.of(
                new WalletTransactionDTO(
                        "Salary",
                        "INCOME",
                        BigDecimal.valueOf(2500.00),
                        "EUR"
                ),
                new WalletTransactionDTO(
                        "Groceries",
                        "FOOD",
                        BigDecimal.valueOf(45.99),
                        "EUR"
                )
        );

        when(walletTransactionService.getAll()).thenReturn(transactions);

        mockMvc.perform(get("/wallet-transaction/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Salary"))
                .andExpect(jsonPath("$[0].category_type").value("INCOME"))
                .andExpect(jsonPath("$[0].price").value(2500.00))
                .andExpect(jsonPath("$[0].currency_type").value("EUR"))
                .andExpect(jsonPath("$[1].name").value("Groceries"))
                .andExpect(jsonPath("$[1].category_type").value("FOOD"))
                .andExpect(jsonPath("$[1].price").value(45.99))
                .andExpect(jsonPath("$[1].currency_type").value("EUR"));

        verify(walletTransactionService).getAll();
    }

    @Test
    void getByTransactionType_shouldReturnWalletTransactionsByType() throws Exception {
        String typeEnum = "INCOME";

        List<WalletTransactionDTO> transactions = List.of(
                new WalletTransactionDTO(
                        "Salary",
                        "WORK",
                        BigDecimal.valueOf(2500.00),
                        "EUR"
                )
        );

        when(walletTransactionService.getTransactionByType(typeEnum)).thenReturn(transactions);

        mockMvc.perform(get("/wallet-transaction/get-transaction-by-type")
                        .param("typeEnum", typeEnum)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Salary"))
                .andExpect(jsonPath("$[0].category_type").value("WORK"))
                .andExpect(jsonPath("$[0].price").value(2500.00))
                .andExpect(jsonPath("$[0].currency_type").value("EUR"));

        verify(walletTransactionService).getTransactionByType(typeEnum);
    }

    @Test
    void getTransactionBalance_shouldReturnWalletTransactionsBySessionId() throws Exception {
        UUID sessionId = UUID.fromString("11111111-1111-1111-1111-111111111111");

        List<WalletTransactionDTO> transactions = List.of(
                new WalletTransactionDTO(
                        "Coffee",
                        "FOOD",
                        BigDecimal.valueOf(3.50),
                        "EUR"
                ),
                new WalletTransactionDTO(
                        "Bus Ticket",
                        "TRANSPORT",
                        BigDecimal.valueOf(2.90),
                        "EUR"
                )
        );

        when(walletTransactionService.getTransactionBalanceBySessionID(sessionId)).thenReturn(transactions);

        mockMvc.perform(get("/wallet-transaction/get-transaction-balance")
                        .param("sessionId", sessionId.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Coffee"))
                .andExpect(jsonPath("$[0].category_type").value("FOOD"))
                .andExpect(jsonPath("$[0].price").value(3.50))
                .andExpect(jsonPath("$[0].currency_type").value("EUR"))
                .andExpect(jsonPath("$[1].name").value("Bus Ticket"))
                .andExpect(jsonPath("$[1].category_type").value("TRANSPORT"))
                .andExpect(jsonPath("$[1].price").value(2.90))
                .andExpect(jsonPath("$[1].currency_type").value("EUR"));

        verify(walletTransactionService).getTransactionBalanceBySessionID(sessionId);
    }

    @Test
    void getByTransactionType_withoutTypeEnumParam_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(get("/wallet-transaction/get-transaction-by-type")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getTransactionBalance_withoutSessionIdParam_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(get("/wallet-transaction/get-transaction-balance")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getTransactionBalance_withInvalidSessionId_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(get("/wallet-transaction/get-transaction-balance")
                        .param("sessionId", "invalid-uuid")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
