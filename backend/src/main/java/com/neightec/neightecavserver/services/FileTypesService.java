package com.neightec.neightecavserver.services;

import com.neightec.neightecavserver.models.dto.FileTypesDTO;
import com.neightec.neightecavserver.repositories.FileTypesRepository;
import com.neightec.neightecavserver.services.mapper.FileTypesMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class FileTypesService {

    private final FileTypesRepository fileTypesRepository;
    private final FileTypesMapper fileTypesMapper;
    public List<FileTypesDTO> getAll() {
        return fileTypesRepository.findAll()
                .stream()
                .map(fileTypesMapper::toDTO)
                .collect(Collectors.toList());
    }
}
