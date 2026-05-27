package com.neightec.neightecavserver.repositories;

import com.neightec.neightecavserver.models.neightec_data.NeightecUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NeightecUserRepository extends JpaRepository<NeightecUser, UUID> {

    NeightecUser findByFirstName(String firstname);
}
