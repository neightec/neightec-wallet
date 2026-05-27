package com.neightec.neightecavserver.services.mapper;

import com.neightec.neightecavserver.models.dto.FileTypesDTO;
import com.neightec.neightecavserver.models.dto.GuestDTO;
import com.neightec.neightecavserver.models.neightec_data.FileTypes;
import com.neightec.neightecavserver.models.neightec_data.Guest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GuestMapper {
    @Mapping(target="name", source="fullName")
    @Mapping(target="attendanceStatus", source="attendanceStatus")
    @Mapping(target="date", source="validStart")
    GuestDTO toDTO(Guest guest);
    Guest toEntity(GuestDTO guestDTO);
}
