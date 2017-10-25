package com.byta.lenus.service.mapper;

import com.byta.lenus.domain.*;
import com.byta.lenus.service.dto.TransactionTypenDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TransactionTypen and its DTO TransactionTypenDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TransactionTypenMapper extends EntityMapper<TransactionTypenDTO, TransactionTypen> {

    

    

    default TransactionTypen fromId(Long id) {
        if (id == null) {
            return null;
        }
        TransactionTypen transactionTypen = new TransactionTypen();
        transactionTypen.setId(id);
        return transactionTypen;
    }
}
