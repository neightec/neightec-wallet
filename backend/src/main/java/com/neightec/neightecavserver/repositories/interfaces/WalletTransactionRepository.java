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

    /**
     * Monthly totals for one transaction type, each compared with the previous month of the same
     * type and currency. difference/percentage_change are null for the first month;
     * percentage_change is also null when the previous total is 0.
     */
    @Query(value = """
            with monthly as (
                select wtt.name as name,
                       wt.currency_type as currency_type,
                       date_trunc('month', wt."timestamp") as month_start,
                       sum(wt.price) as total
                from wallet_transaction wt
                join wallet_session_to_transaction wstt
                    on wt.id = wstt.wallet_transaction_id
                join wallet_transaction_type wtt
                    on wtt.id = wt.wallet_transaction_type_id
                where wstt.wallet_session_id = :sessionId
                and wtt.name = :transactionType
                group by wtt.name, wt.currency_type, date_trunc('month', wt."timestamp")
            )
            select name,
                   currency_type,
                   total,
                   to_char(month_start, 'FMMonth') as month,
                   to_char(month_start, 'YYYY') as year,
                   total - lag(total) over w as difference,
                   round((total - lag(total) over w) * 100 / nullif(lag(total) over w, 0), 2) as percentage_change
            from monthly
            window w as (partition by name, currency_type order by month_start)
            order by month_start, name
            """, nativeQuery = true)
    List<WalletTransactionBalanceByType> findTransactionBySessionIdAndWalletTransactionType(
            @Param("sessionId") UUID sessionID,
            @Param("transactionType") String transactionType
    );

    /**
     * Monthly totals for one transaction type, each compared with the previous month of the same
     * type and currency. difference/percentage_change are null for the first month;
     * percentage_change is also null when the previous total is 0.
     */
    @Query(value = """
            with monthly as (
                select wtt.name as name,
                       wt.currency_type as currency_type,
                       date_trunc('month', wt."timestamp") as month_start,
                       sum(wt.price) as total
                from wallet_transaction wt
                join wallet_session_to_transaction wstt
                    on wt.id = wstt.wallet_transaction_id
                join wallet_transaction_type wtt
                    on wtt.id = wt.wallet_transaction_type_id
                where wstt.wallet_session_id = :sessionId
                group by wtt.name, wt.currency_type, date_trunc('month', wt."timestamp")
            )
            select name,
                   currency_type,
                   total,
                   to_char(month_start, 'FMMonth') as month,
                   to_char(month_start, 'YYYY') as year,
                   total - lag(total) over w as difference,
                   round((total - lag(total) over w) * 100 / nullif(lag(total) over w, 0), 2) as percentage_change
            from monthly
            window w as (partition by name, currency_type order by month_start)
            order by month_start, name
            """, nativeQuery = true)
    List<WalletTransactionBalanceByType> findTransactionBySessionIdAndWallet(
            @Param("sessionId") UUID sessionID
    );

    /**
     * Aggregate projection: total price per wallet transaction type.
     * This query returns no entity rows, so it must not be mapped to WalletTransaction.
     */
    interface WalletTransactionBalanceByType {
        String getName();
        BigDecimal getTotal();
        String getCurrency_type();
        String getMonth();
        String getYear();
        String getMonth_year();
        BigDecimal getDifference();
        BigDecimal getPercentage_change();
    }

    interface WalletTransactionBalance {
        List<String> getName();
        List<BigDecimal> einnahmen();
        List<BigDecimal> ausgaben();
        List<BigDecimal> ersparnisse();
    }

}
