package com.neightec.neightecavserver.services.impl;

import com.neightec.neightecavserver.models.dto.WalletAccountDTO;
import com.neightec.neightecavserver.models.neightec_data.WalletAccount;
import com.neightec.neightecavserver.models.neightec_data.WalletSessionToAccount;
import com.neightec.neightecavserver.repositories.interfaces.WalletAccountRepository;
import com.neightec.neightecavserver.repositories.interfaces.WalletSessionRepository;
import com.neightec.neightecavserver.repositories.interfaces.WalletSessionToAccountRepository;
import com.neightec.neightecavserver.services.mapper.WalletAccountMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WalletAccountServiceImplTest {

    private static final UUID SESSION_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");
    private static final UUID TEMP_SESSION_ID = UUID.fromString("22222222-2222-2222-2222-222222222222");

    @Mock
    private WalletAccountRepository walletAccountRepository;

    @Mock
    private WalletAccountMapper walletAccountMapper;

    @Mock
    private WalletSessionRepository walletSessionRepository;

    @Mock
    private WalletSessionToAccountRepository walletSessionToAccountRepository;

    @InjectMocks
    private WalletAccountServiceImpl walletAccountService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(walletAccountService, "sessionID", TEMP_SESSION_ID.toString());
    }

    @Test
    void getAccountsBySessionID_shouldMapAccountsOfSessionToDTOs() {
        WalletAccount giro = new WalletAccount();
        WalletAccount savings = new WalletAccount();
        WalletAccountDTO giroDTO = new WalletAccountDTO("Girokonto", "2450.75", null);
        WalletAccountDTO savingsDTO = new WalletAccountDTO("Sparkonto", "10200.00", null);

        when(walletAccountRepository.getAccountsBySessionID(SESSION_ID)).thenReturn(List.of(giro, savings));
        when(walletAccountMapper.toDTO(giro)).thenReturn(giroDTO);
        when(walletAccountMapper.toDTO(savings)).thenReturn(savingsDTO);

        List<WalletAccountDTO> result = walletAccountService.getAccountsBySessionID(SESSION_ID);

        assertThat(result).containsExactly(giroDTO, savingsDTO);
    }

    @Test
    void getAccountsBySessionID_withoutSessionId_shouldUseTempSessionFromProperties() {
        when(walletAccountRepository.getAccountsBySessionID(TEMP_SESSION_ID)).thenReturn(List.of());

        List<WalletAccountDTO> result = walletAccountService.getAccountsBySessionID(null);

        assertThat(result).isEmpty();
        verify(walletAccountRepository).getAccountsBySessionID(TEMP_SESSION_ID);
    }

    @Test
    void getAccountsBySessionID_withUnknownSession_shouldReturnEmptyListWithoutMapping() {
        when(walletAccountRepository.getAccountsBySessionID(SESSION_ID)).thenReturn(List.of());

        List<WalletAccountDTO> result = walletAccountService.getAccountsBySessionID(SESSION_ID);

        assertThat(result).isEmpty();
        verifyNoInteractions(walletAccountMapper);
    }

    @Test
    void addAccountBySessionID_shouldSaveTrimmedAccountAndLinkToSession() {
        UUID accountId = UUID.randomUUID();
        WalletAccountDTO expected = new WalletAccountDTO("Giro", "12.50", null);
        when(walletSessionRepository.existsById(SESSION_ID)).thenReturn(true);
        when(walletAccountRepository.save(any(WalletAccount.class))).thenAnswer(invocation -> {
            WalletAccount saved = invocation.getArgument(0);
            saved.setId(accountId);
            return saved;
        });
        when(walletAccountMapper.toDTO(any(WalletAccount.class))).thenReturn(expected);

        WalletAccountDTO result = walletAccountService.addAccountBySessionID(
                SESSION_ID, new WalletAccountDTO("  Giro  ", " 12,50 ", null));

        assertThat(result).isEqualTo(expected);

        ArgumentCaptor<WalletAccount> accountCaptor = ArgumentCaptor.forClass(WalletAccount.class);
        verify(walletAccountRepository).save(accountCaptor.capture());
        WalletAccount saved = accountCaptor.getValue();
        assertThat(saved.getName()).isEqualTo("Giro");
        assertThat(saved.getBalance()).isEqualByComparingTo("12.50");
        assertThat(saved.getIs_archived()).isFalse();
        assertThat(saved.getCreation_timestamp()).isNotNull();

        ArgumentCaptor<WalletSessionToAccount> linkCaptor = ArgumentCaptor.forClass(WalletSessionToAccount.class);
        verify(walletSessionToAccountRepository).save(linkCaptor.capture());
        assertThat(linkCaptor.getValue().getWallet_session_id()).isEqualTo(SESSION_ID);
        assertThat(linkCaptor.getValue().getWallet_account_id()).isEqualTo(accountId);
    }

    @Test
    void addAccountBySessionID_withoutSessionId_shouldUseTempSession() {
        when(walletSessionRepository.existsById(TEMP_SESSION_ID)).thenReturn(true);
        when(walletAccountRepository.save(any(WalletAccount.class))).thenAnswer(invocation -> invocation.getArgument(0));

        walletAccountService.addAccountBySessionID(null, new WalletAccountDTO("Giro", "1", null));

        ArgumentCaptor<WalletSessionToAccount> linkCaptor = ArgumentCaptor.forClass(WalletSessionToAccount.class);
        verify(walletSessionToAccountRepository).save(linkCaptor.capture());
        assertThat(linkCaptor.getValue().getWallet_session_id()).isEqualTo(TEMP_SESSION_ID);
    }

    @Test
    void addAccountBySessionID_withBlankBalance_shouldDefaultToZero() {
        when(walletSessionRepository.existsById(SESSION_ID)).thenReturn(true);
        when(walletAccountRepository.save(any(WalletAccount.class))).thenAnswer(invocation -> invocation.getArgument(0));

        walletAccountService.addAccountBySessionID(SESSION_ID, new WalletAccountDTO("Giro", " ", null));

        ArgumentCaptor<WalletAccount> accountCaptor = ArgumentCaptor.forClass(WalletAccount.class);
        verify(walletAccountRepository).save(accountCaptor.capture());
        assertThat(accountCaptor.getValue().getBalance()).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    void addAccountBySessionID_withUnknownSession_shouldThrowNotFound() {
        when(walletSessionRepository.existsById(SESSION_ID)).thenReturn(false);

        assertThatThrownBy(() -> walletAccountService.addAccountBySessionID(
                SESSION_ID, new WalletAccountDTO("Giro", "1", null)))
                .isInstanceOfSatisfying(ResponseStatusException.class,
                        e -> assertThat(e.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND));
        verify(walletAccountRepository, never()).save(any());
        verifyNoInteractions(walletSessionToAccountRepository);
    }

    @Test
    void addAccountBySessionID_withBlankName_shouldThrowBadRequest() {
        when(walletSessionRepository.existsById(SESSION_ID)).thenReturn(true);

        assertThatThrownBy(() -> walletAccountService.addAccountBySessionID(
                SESSION_ID, new WalletAccountDTO("   ", "1", null)))
                .isInstanceOfSatisfying(ResponseStatusException.class,
                        e -> assertThat(e.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST));
        verify(walletAccountRepository, never()).save(any());
    }

    @Test
    void addAccountBySessionID_withInvalidBalance_shouldThrowBadRequest() {
        when(walletSessionRepository.existsById(SESSION_ID)).thenReturn(true);

        assertThatThrownBy(() -> walletAccountService.addAccountBySessionID(
                SESSION_ID, new WalletAccountDTO("Giro", "abc", null)))
                .isInstanceOfSatisfying(ResponseStatusException.class,
                        e -> assertThat(e.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST));
        verify(walletAccountRepository, never()).save(any());
    }
}
