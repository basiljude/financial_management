package com.byta.lenus.service.mapper;

import com.byta.lenus.domain.*;
import com.byta.lenus.service.dto.DebitCashDeskDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity DebitCashDesk and its DTO DebitCashDeskDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DebitCashDeskMapper extends EntityMapper<DebitCashDeskDTO, DebitCashDesk> {

    

    

    default DebitCashDesk fromId(Long id) {
        if (id == null) {
            return null;
        }
        DebitCashDesk debitCashDesk = new DebitCashDesk();
        debitCashDesk.setId(id);
        return debitCashDesk;
    }
}
