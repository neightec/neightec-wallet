package com.neightec.neightecavserver.services.mapper;

import com.neightec.neightecavserver.models.dto.FileTypesDTO;
import com.neightec.neightecavserver.models.neightec_data.FileTypes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FileTypesMapper {
    @Mapping(target="name", source="name")
    @Mapping(target="extension", source="extension")
    FileTypesDTO toDTO(FileTypes fileTypes);
    FileTypes toEntity(FileTypesDTO fileTypesDTO);
}
