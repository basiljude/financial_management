package com.byta.lenus.service.mapper;

import com.byta.lenus.domain.*;
import com.byta.lenus.service.dto.DebitTransactionTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity DebitTransactionType and its DTO DebitTransactionTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {AddDebitMapper.class})
public interface DebitTransactionTypeMapper extends EntityMapper<DebitTransactionTypeDTO, DebitTransactionType> {

    @Mapping(source = "addDebit.id", target = "addDebitId")
    DebitTransactionTypeDTO toDto(DebitTransactionType debitTransactionType); 

    @Mapping(source = "addDebitId", target = "addDebit")
    DebitTransactionType toEntity(DebitTransactionTypeDTO debitTransactionTypeDTO);

    default DebitTransactionType fromId(Long id) {
        if (id == null) {
            return null;
        }
        DebitTransactionType debitTransactionType = new DebitTransactionType();
        debitTransactionType.setId(id);
        return debitTransactionType;
    }
}
