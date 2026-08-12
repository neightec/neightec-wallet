package com.neightec.neightecavserver.repositories;

import com.neightec.neightecavserver.models.neightec_data.NeightecUser;
import com.neightec.neightecavserver.models.neightec_data.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, UUID> {

}
