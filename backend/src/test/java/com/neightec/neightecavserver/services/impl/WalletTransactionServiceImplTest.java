package com.neightec.neightecavserver.services.impl;

import com.neightec.neightecavserver.models.dto.WalletTransactionChartDataDTO;
import com.neightec.neightecavserver.models.dto.WalletTransactionDTO;
import com.neightec.neightecavserver.models.dto.WalletTransactionMonthlyBalanceDTO;
import com.neightec.neightecavserver.models.dto.WalletTransactionMonthlyBalanceSummaryDTO;
import com.neightec.neightecavserver.models.enums.WalletTransactionTypeEnum;
import com.neightec.neightecavserver.models.neightec_data.WalletSession;
import com.neightec.neightecavserver.models.neightec_data.WalletTransaction;
import com.neightec.neightecavserver.repositories.interfaces.WalletSessionRepository;
import com.neightec.neightecavserver.repositories.interfaces.WalletTransactionRepository;
import com.neightec.neightecavserver.repositories.interfaces.WalletTransactionRepository.WalletTransactionBalanceByType;
import com.neightec.neightecavserver.services.mapper.WalletTransactionMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WalletTransactionServiceImplTest {

    private static final UUID SESSION_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    @Mock
    private WalletTransactionRepository walletTransactionRepository;

    @Mock
    private WalletTransactionMapper walletTransactionMapper;

    @Mock
    private WalletSessionRepository walletSessionRepository;

    @InjectMocks
    private WalletTransactionServiceImpl walletTransactionService;

    @Test
    void getAll_shouldMapAllTransactionsToDTOs() {
        WalletTransaction salary = new WalletTransaction();
        WalletTransaction groceries = new WalletTransaction();
        WalletTransactionDTO salaryDTO = transactionDTO("Salary", 2500);
        WalletTransactionDTO groceriesDTO = transactionDTO("Groceries", 45);

        when(walletTransactionRepository.findAll()).thenReturn(List.of(salary, groceries));
        when(walletTransactionMapper.toDTO(salary)).thenReturn(salaryDTO);
        when(walletTransactionMapper.toDTO(groceries)).thenReturn(groceriesDTO);

        List<WalletTransactionDTO> result = walletTransactionService.getAll();

        assertThat(result).containsExactly(salaryDTO, groceriesDTO);
    }

    @Test
    void getAll_withNoTransactions_shouldReturnEmptyList() {
        when(walletTransactionRepository.findAll()).thenReturn(List.of());

        List<WalletTransactionDTO> result = walletTransactionService.getAll();

        assertThat(result).isEmpty();
        verifyNoInteractions(walletTransactionMapper);
    }

    @Test
    void getTransactionByType_withValidType_shouldReturnMappedTransactions() {
        WalletTransaction salary = new WalletTransaction();
        WalletTransactionDTO salaryDTO = transactionDTO("Salary", 2500);

        when(walletTransactionRepository.findTransactionByType(WalletTransactionTypeEnum.EINKOMMEN))
                .thenReturn(List.of(salary));
        when(walletTransactionMapper.toDTO(salary)).thenReturn(salaryDTO);

        List<WalletTransactionDTO> result = walletTransactionService.getTransactionByType("EINKOMMEN");

        assertThat(result).containsExactly(salaryDTO);
        verify(walletTransactionRepository).findTransactionByType(WalletTransactionTypeEnum.EINKOMMEN);
    }

    @Test
    void getTransactionByType_withInvalidType_shouldReturnEmptyListWithoutQuerying() {
        List<WalletTransactionDTO> result = walletTransactionService.getTransactionByType("UNKNOWN");

        assertThat(result).isEmpty();
        verify(walletTransactionRepository, never()).findTransactionByType(any());
    }

    @Test
    void getTransactionByType_withDisplayNameInsteadOfEnumName_shouldReturnEmptyList() {
        List<WalletTransactionDTO> result = walletTransactionService.getTransactionByType("Einnahmen");

        assertThat(result).isEmpty();
        verify(walletTransactionRepository, never()).findTransactionByType(any());
    }

    @Test
    void getTransactionBalanceBySessionID_withExistingSession_shouldReturnMappedTransactions() {
        WalletTransaction salary = new WalletTransaction();
        WalletTransactionDTO salaryDTO = transactionDTO("Salary", 2500);

        when(walletSessionRepository.findById(SESSION_ID)).thenReturn(Optional.of(new WalletSession()));
        when(walletTransactionRepository.findTransactionBySessionId(SESSION_ID)).thenReturn(List.of(salary));
        when(walletTransactionMapper.toDTO(salary)).thenReturn(salaryDTO);

        List<WalletTransactionDTO> result = walletTransactionService.getTransactionBalanceBySessionID(SESSION_ID);

        assertThat(result).containsExactly(salaryDTO);
    }

    @Test
    void getTransactionBalanceBySessionID_withUnknownSession_shouldReturnEmptyListWithoutQuerying() {
        when(walletSessionRepository.findById(SESSION_ID)).thenReturn(Optional.empty());

        List<WalletTransactionDTO> result = walletTransactionService.getTransactionBalanceBySessionID(SESSION_ID);

        assertThat(result).isEmpty();
        verify(walletTransactionRepository, never()).findTransactionBySessionId(any());
    }

    // getTransactionBalanceBySessionIDAndWalletTransactionType

    @Test
    void getTransactionBalanceBySessionIDAndWalletTransactionType_shouldMapMonthsAndPickMaxMonth() {
        WalletTransactionBalanceByType november = balance("Einnahmen", "1000", "November", "2026", null, null);
        WalletTransactionBalanceByType december = balance("Einnahmen", "1020", "December", "2026", "20", "2.00");
        WalletTransactionBalanceByType january = balance("Einnahmen", "900", "January", "2027", "-120", "-11.76");

        when(walletSessionRepository.findById(SESSION_ID)).thenReturn(Optional.of(new WalletSession()));
        when(walletTransactionRepository.findTransactionBySessionIdAndWalletTransactionType(SESSION_ID, "Einnahmen"))
                .thenReturn(List.of(november, december, january));

        WalletTransactionMonthlyBalanceSummaryDTO result =
                walletTransactionService.getTransactionBalanceBySessionIDAndWalletTransactionType(SESSION_ID, "Einnahmen");

        assertThat(result.getMonths()).hasSize(3);

        WalletTransactionMonthlyBalanceDTO first = result.getMonths().get(0);
        assertThat(first.getName()).isEqualTo("Einnahmen");
        assertThat(first.getTotal()).isEqualByComparingTo("1000");
        assertThat(first.getCurrency_type()).isEqualTo("EUR");
        assertThat(first.getMonth()).isEqualTo("November");
        assertThat(first.getYear()).isEqualTo("2026");
        assertThat(first.getDifference()).isNull();
        assertThat(first.getPercentage_change()).isNull();

        WalletTransactionMonthlyBalanceDTO second = result.getMonths().get(1);
        assertThat(second.getDifference()).isEqualByComparingTo("20");
        assertThat(second.getPercentage_change()).isEqualByComparingTo("2.00");

        assertThat(result.getMax_month()).isSameAs(second);
        assertThat(result.getMax_month().getMonth()).isEqualTo("December");
    }

    @Test
    void getTransactionBalanceBySessionIDAndWalletTransactionType_withNoRows_shouldReturnEmptyMonthsAndNullMax() {
        when(walletSessionRepository.findById(SESSION_ID)).thenReturn(Optional.of(new WalletSession()));
        when(walletTransactionRepository.findTransactionBySessionIdAndWalletTransactionType(SESSION_ID, "Ausgaben"))
                .thenReturn(List.of());

        WalletTransactionMonthlyBalanceSummaryDTO result =
                walletTransactionService.getTransactionBalanceBySessionIDAndWalletTransactionType(SESSION_ID, "Ausgaben");

        assertThat(result.getMonths()).isEmpty();
        assertThat(result.getMax_month()).isNull();
    }

    @Test
    void getTransactionBalanceBySessionIDAndWalletTransactionType_withUnknownSession_shouldReturnEmptySummary() {
        when(walletSessionRepository.findById(SESSION_ID)).thenReturn(Optional.empty());

        WalletTransactionMonthlyBalanceSummaryDTO result =
                walletTransactionService.getTransactionBalanceBySessionIDAndWalletTransactionType(SESSION_ID, "Einnahmen");

        assertThat(result.getMonths()).isEmpty();
        assertThat(result.getMax_month()).isNull();
        verify(walletTransactionRepository, never())
                .findTransactionBySessionIdAndWalletTransactionType(any(), anyString());
    }

    @Test
    void getTransactionBalanceBySessionIDAndWallet_shouldGroupTotalsByMonthAndType() {
        when(walletSessionRepository.findById(SESSION_ID)).thenReturn(Optional.of(new WalletSession()));
        when(walletTransactionRepository.findTransactionBySessionIdAndWallet(SESSION_ID)).thenReturn(List.of(
                balance("Ausgaben", "400", "November", "2026", null, null),
                balance("Einnahmen", "1000", "November", "2026", null, null),
                balance("Ersparnisse", "200", "November", "2026", null, null),
                balance("Einnahmen", "1020", "December", "2026", "20", "2.00")
        ));

        WalletTransactionChartDataDTO result = walletTransactionService.getTransactionBalanceBySessionIDAndWallet(SESSION_ID);

        assertThat(result.labels()).containsExactly("November 2026", "December 2026");
        assertThat(result.einnahmen()).usingElementComparator(BigDecimal::compareTo)
                .containsExactly(new BigDecimal("1000"), new BigDecimal("1020"));
        assertThat(result.ausgaben()).usingElementComparator(BigDecimal::compareTo)
                .containsExactly(new BigDecimal("400"), BigDecimal.ZERO);
        assertThat(result.ersparnisse()).usingElementComparator(BigDecimal::compareTo)
                .containsExactly(new BigDecimal("200"), BigDecimal.ZERO);
    }

    @Test
    void getTransactionBalanceBySessionIDAndWallet_withUnknownTypeName_shouldOnlyAddLabelWithZeroTotals() {
        when(walletSessionRepository.findById(SESSION_ID)).thenReturn(Optional.of(new WalletSession()));
        when(walletTransactionRepository.findTransactionBySessionIdAndWallet(SESSION_ID)).thenReturn(List.of(
                balance("Sonstiges", "50", "November", "2026", null, null)
        ));

        WalletTransactionChartDataDTO result = walletTransactionService.getTransactionBalanceBySessionIDAndWallet(SESSION_ID);

        assertThat(result.labels()).containsExactly("November 2026");
        assertThat(result.einnahmen()).containsExactly(BigDecimal.ZERO);
        assertThat(result.ausgaben()).containsExactly(BigDecimal.ZERO);
        assertThat(result.ersparnisse()).containsExactly(BigDecimal.ZERO);
    }

    @Test
    void getTransactionBalanceBySessionIDAndWallet_shouldSkipNullRows() {
        when(walletSessionRepository.findById(SESSION_ID)).thenReturn(Optional.of(new WalletSession()));
        when(walletTransactionRepository.findTransactionBySessionIdAndWallet(SESSION_ID)).thenReturn(Arrays.asList(
                null,
                balance("Einnahmen", "1000", "November", "2026", null, null)
        ));

        WalletTransactionChartDataDTO result = walletTransactionService.getTransactionBalanceBySessionIDAndWallet(SESSION_ID);

        assertThat(result.labels()).containsExactly("November 2026");
        assertThat(result.einnahmen()).usingElementComparator(BigDecimal::compareTo)
                .containsExactly(new BigDecimal("1000"));
    }

    @Test
    void getTransactionBalanceBySessionIDAndWallet_withNoRows_shouldReturnEmptyLists() {
        when(walletSessionRepository.findById(SESSION_ID)).thenReturn(Optional.of(new WalletSession()));
        when(walletTransactionRepository.findTransactionBySessionIdAndWallet(SESSION_ID)).thenReturn(List.of());

        WalletTransactionChartDataDTO result = walletTransactionService.getTransactionBalanceBySessionIDAndWallet(SESSION_ID);

        assertThat(result.labels()).isEmpty();
        assertThat(result.einnahmen()).isEmpty();
        assertThat(result.ausgaben()).isEmpty();
        assertThat(result.ersparnisse()).isEmpty();
    }

    @Test
    void getTransactionBalanceBySessionIDAndWallet_withNullResult_shouldReturnDTOWithNullFields() {
        when(walletSessionRepository.findById(SESSION_ID)).thenReturn(Optional.of(new WalletSession()));
        when(walletTransactionRepository.findTransactionBySessionIdAndWallet(SESSION_ID)).thenReturn(null);

        WalletTransactionChartDataDTO result = walletTransactionService.getTransactionBalanceBySessionIDAndWallet(SESSION_ID);

        assertThat(result).isEqualTo(new WalletTransactionChartDataDTO(null, null, null, null));
    }

    @Test
    void getTransactionBalanceBySessionIDAndWallet_withUnknownSession_shouldReturnNull() {
        when(walletSessionRepository.findById(SESSION_ID)).thenReturn(Optional.empty());

        WalletTransactionChartDataDTO result = walletTransactionService.getTransactionBalanceBySessionIDAndWallet(SESSION_ID);

        assertThat(result).isNull();
        verify(walletTransactionRepository, never()).findTransactionBySessionIdAndWallet(any());
    }

    private static WalletTransactionDTO transactionDTO(String name, long price) {
        return new WalletTransactionDTO(name, "CATEGORY", BigDecimal.valueOf(price), "EUR", "November", "2026");
    }

    private static WalletTransactionBalanceByType balance(String name, String total, String month, String year,
                                                          String difference, String percentageChange) {
        return new WalletTransactionBalanceByType() {
            @Override
            public String getName() {
                return name;
            }

            @Override
            public BigDecimal getTotal() {
                return new BigDecimal(total);
            }

            @Override
            public String getCurrency_type() {
                return "EUR";
            }

            @Override
            public String getMonth() {
                return month;
            }

            @Override
            public String getYear() {
                return year;
            }

            @Override
            public String getMonth_year() {
                return month + " " + year;
            }

            @Override
            public BigDecimal getDifference() {
                return difference == null ? null : new BigDecimal(difference);
            }

            @Override
            public BigDecimal getPercentage_change() {
                return percentageChange == null ? null : new BigDecimal(percentageChange);
            }
        };
    }
}
