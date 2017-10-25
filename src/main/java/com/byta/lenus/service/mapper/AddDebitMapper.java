package com.byta.lenus.service.mapper;

import com.byta.lenus.domain.*;
import com.byta.lenus.service.dto.AddDebitDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AddDebit and its DTO AddDebitDTO.
 */
@Mapper(componentModel = "spring", uses = {DebitCashDeskMapper.class})
public interface AddDebitMapper extends EntityMapper<AddDebitDTO, AddDebit> {

    @Mapping(source = "debitCashDesk.id", target = "debitCashDeskId")
    AddDebitDTO toDto(AddDebit addDebit); 

    @Mapping(source = "debitCashDeskId", target = "debitCashDesk")
    @Mapping(target = "debitTransactionTypes", ignore = true)
    AddDebit toEntity(AddDebitDTO addDebitDTO);

    default AddDebit fromId(Long id) {
        if (id == null) {
            return null;
        }
        AddDebit addDebit = new AddDebit();
        addDebit.setId(id);
        return addDebit;
    }
}
