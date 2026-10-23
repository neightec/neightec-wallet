package com.neightec.neightecavserver.controller;

import com.neightec.neightecavserver.models.dto.WalletTransactionDTO;
import com.neightec.neightecavserver.models.dto.WalletTransactionMonthlyBalanceDTO;
import com.neightec.neightecavserver.models.dto.WalletTransactionMonthlyBalanceSummaryDTO;
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
                        "EUR",
                        "November",
                        "2027"
                ),
                new WalletTransactionDTO(
                        "Groceries",
                        "FOOD",
                        BigDecimal.valueOf(45.99),
                        "EUR",
                        "Dezember",
                        "2027"
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
                        "EUR",
                        "Dezember",
                        "2027"
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
    void getTransactionBalance_shouldReturnMonthlyComparisonAndMaxMonth() throws Exception {
        UUID sessionId = UUID.fromString("11111111-1111-1111-1111-111111111111");

        WalletTransactionMonthlyBalanceDTO november = new WalletTransactionMonthlyBalanceDTO(
                "Einnahmen", BigDecimal.valueOf(1000), "EUR", "November", "2026", null, null);
        WalletTransactionMonthlyBalanceDTO december = new WalletTransactionMonthlyBalanceDTO(
                "Einnahmen", BigDecimal.valueOf(1020), "EUR", "December", "2026",
                BigDecimal.valueOf(20), new BigDecimal("2.00"));

        when(walletTransactionService.getTransactionBalanceBySessionIDAndWalletTransactionType(sessionId, "Einnahmen"))
                .thenReturn(new WalletTransactionMonthlyBalanceSummaryDTO(List.of(november, december), december));

        mockMvc.perform(get("/wallet-transaction/get-transaction-balance")
                        .param("sessionId", sessionId.toString())
                        .param("transactionType", "Einnahmen")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.months", hasSize(2)))
                .andExpect(jsonPath("$.months[0].total").value(1000))
                .andExpect(jsonPath("$.months[0].difference").doesNotExist())
                .andExpect(jsonPath("$.months[0].percentage_change").doesNotExist())
                .andExpect(jsonPath("$.months[1].difference").value(20))
                .andExpect(jsonPath("$.months[1].percentage_change").value(2.00))
                .andExpect(jsonPath("$.max_month.month").value("December"))
                .andExpect(jsonPath("$.max_month.total").value(1020));

        verify(walletTransactionService).getTransactionBalanceBySessionIDAndWalletTransactionType(sessionId, "Einnahmen");
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
