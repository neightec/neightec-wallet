package com.neightec.neightecavserver.repositories.interfaces;

import com.neightec.neightecavserver.models.enums.WalletTransactionTypeEnum;
import com.neightec.neightecavserver.models.neightec_data.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WalletSessionRepository extends JpaRepository<WalletTransaction, UUID> {

    @Query(value = """
            select wt.*
            from wallet_transaction wt
            join wallet_transaction_type wtt
                on wt.wallet_transaction_type_id = wtt.id
            where wtt.name = :#{#typeEnum.walletTransactionTypeName}
            """, nativeQuery = true)
    List<WalletTransaction> findTransactionByType(@Param("typeEnum") WalletTransactionTypeEnum typeEnum);
}
