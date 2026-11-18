package com.neightec.neightecavserver.repositories.interfaces;

import com.neightec.neightecavserver.models.neightec_data.WalletAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WalletAccountRepository extends JpaRepository<WalletAccount, UUID> {
    @Query(value = """
            select wa.*
            from wallet_account wa
            join wallet_session_to_account wsta
                on wa.id = wsta.wallet_account_id
            where wsta.wallet_session_id = :sessionId
            """, nativeQuery = true)
    List<WalletAccount> getAccountsBySessionID(@Param("sessionId") UUID sessionID);
}
