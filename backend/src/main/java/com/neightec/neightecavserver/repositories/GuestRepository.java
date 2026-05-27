package com.neightec.neightecavserver.repositories;

import com.neightec.neightecavserver.models.neightec_data.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GuestRepository extends JpaRepository<Guest, UUID> {

    Guest findByFullName(String fullName);

    @Query("select count(c) > 0 from Guest c where fullName = :fullName")
    boolean existsByGuestFullName(String fullName);
}
