package com.byta.lenus.service.mapper;

import com.byta.lenus.domain.*;
import com.byta.lenus.service.dto.AddCreditDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AddCredit and its DTO AddCreditDTO.
 */
@Mapper(componentModel = "spring", uses = {TransactionTypenMapper.class})
public interface AddCreditMapper extends EntityMapper<AddCreditDTO, AddCredit> {

    @Mapping(source = "transactionTypen.id", target = "transactionTypenId")
    AddCreditDTO toDto(AddCredit addCredit); 

    @Mapping(source = "transactionTypenId", target = "transactionTypen")
    @Mapping(target = "cashdesks", ignore = true)
    AddCredit toEntity(AddCreditDTO addCreditDTO);

    default AddCredit fromId(Long id) {
        if (id == null) {
            return null;
        }
        AddCredit addCredit = new AddCredit();
        addCredit.setId(id);
        return addCredit;
    }
}
