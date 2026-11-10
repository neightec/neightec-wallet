package com.neightec.neightecavserver.services.interfaces;

import com.neightec.neightecavserver.models.dto.WalletAccountDTO;
import com.neightec.neightecavserver.models.dto.WalletTransactionDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface WalletAccountService {

    List<WalletAccountDTO> getAccountsBySessionID(UUID sessionID);
}
