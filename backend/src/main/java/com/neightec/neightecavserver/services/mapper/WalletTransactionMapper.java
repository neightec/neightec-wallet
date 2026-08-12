package com.neightec.neightecavserver.services.mapper;

import com.neightec.neightecavserver.models.dto.FileTypesDTO;
import com.neightec.neightecavserver.models.dto.WalletTransactionDTO;
import com.neightec.neightecavserver.models.neightec_data.FileTypes;
import com.neightec.neightecavserver.models.neightec_data.WalletTransaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WalletTransactionMapper {
    @Mapping(target="name", source="name")
    @Mapping(target="category_type", source="category_type")
    @Mapping(target="price", source="price")
    @Mapping(target="currency_type", source="currency_type")
    WalletTransactionDTO toDTO(WalletTransaction walletTransaction);

    WalletTransaction toEntity(WalletTransactionDTO walletTransactionDTO);
}
