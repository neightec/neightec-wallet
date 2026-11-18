package com.neightec.neightecavserver.services.mapper;

import com.neightec.neightecavserver.models.dto.WalletAccountDTO;
import com.neightec.neightecavserver.models.neightec_data.WalletAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WalletAccountMapper {
    @Mapping(target = "currency", ignore = true)
    WalletAccountDTO toDTO(WalletAccount walletAccount);

    WalletAccount toEntity(WalletAccountDTO walletAccountDTO);
}
