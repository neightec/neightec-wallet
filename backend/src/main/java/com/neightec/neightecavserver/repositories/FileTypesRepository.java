package com.neightec.neightecavserver.repositories;

import com.neightec.neightecavserver.models.neightec_data.FileTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FileTypesRepository extends JpaRepository<FileTypes, UUID> {

    FileTypes findByName(String name);
}
