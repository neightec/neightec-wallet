package com.neightec.neightecavserver.repositories.interfaces;

import com.neightec.neightecavserver.models.enums.WalletTransactionTypeEnum;
import com.neightec.neightecavserver.models.neightec_data.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, UUID> {

    @Query(value = """
            select wt.*
            from wallet_transaction wt
            join wallet_transaction_type wtt
                on wt.wallet_transaction_type_id = wtt.id
            where wtt.name = :#{#typeEnum.walletTransactionTypeName}
            """, nativeQuery = true)
    List<WalletTransaction> findTransactionByType(@Param("typeEnum") WalletTransactionTypeEnum typeEnum);

    @Query(value = """
            select wt.*\s
            from wallet_transaction wt
            join wallet_session_to_transaction wstt
                on wt.id = wstt.wallet_transaction_id
            where wstt.wallet_session_id = :sessionId
           \s""", nativeQuery = true)
    List<WalletTransaction> findTransactionBySessionId(@Param("sessionId") UUID sessionID);

    //TODO with month
    @Query(value = """
            select wtt.name as name, wt.currency_type as currency_type, sum(wt.price) as total
            from wallet_transaction wt
            join wallet_session_to_transaction wstt
                on wt.id = wstt.wallet_transaction_id
            join wallet_transaction_type wtt
                on wtt.id = wt.wallet_transaction_type_id
            where wstt.wallet_session_id = :sessionId
            group by wtt.name, wt.currency_type
           \s""", nativeQuery = true)
    List<WalletTransactionBalanceByType> findTransactionBySessionIdAndWalletTransactionType(@Param("sessionId") UUID sessionID);

    /**
     * Aggregate projection: total price per wallet transaction type.
     * This query returns no entity rows, so it must not be mapped to WalletTransaction.
     */
    BigDecimal getTotal();
    interface WalletTransactionBalanceByType {
        String getName();
        String getCurrency_type();
        //TODO with month
    }

}
