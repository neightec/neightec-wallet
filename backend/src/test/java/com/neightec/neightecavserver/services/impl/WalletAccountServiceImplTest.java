package com.neightec.neightecavserver.services.impl;

import com.neightec.neightecavserver.models.dto.WalletAccountDTO;
import com.neightec.neightecavserver.models.neightec_data.WalletAccount;
import com.neightec.neightecavserver.repositories.interfaces.WalletAccountRepository;
import com.neightec.neightecavserver.services.mapper.WalletAccountMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
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
}
