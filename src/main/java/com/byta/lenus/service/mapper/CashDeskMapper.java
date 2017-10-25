package com.byta.lenus.service.mapper;

import com.byta.lenus.domain.*;
import com.byta.lenus.service.dto.CashDeskDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CashDesk and its DTO CashDeskDTO.
 */
@Mapper(componentModel = "spring", uses = {AddCreditMapper.class})
public interface CashDeskMapper extends EntityMapper<CashDeskDTO, CashDesk> {

    @Mapping(source = "addCredit.id", target = "addCreditId")
    CashDeskDTO toDto(CashDesk cashDesk); 

    @Mapping(source = "addCreditId", target = "addCredit")
    CashDesk toEntity(CashDeskDTO cashDeskDTO);

    default CashDesk fromId(Long id) {
        if (id == null) {
            return null;
        }
        CashDesk cashDesk = new CashDesk();
        cashDesk.setId(id);
        return cashDesk;
    }
}
