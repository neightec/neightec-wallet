package com.neightec.neightecavserver.repositories.interfaces;

import com.neightec.neightecavserver.models.neightec_data.WalletSessionToAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WalletSessionToAccountRepository extends JpaRepository<WalletSessionToAccount, UUID> {
}
