package com.byta.lenus.service;

import com.byta.lenus.service.dto.DebitTransactionTypeDTO;
import java.util.List;

/**
 * Service Interface for managing DebitTransactionType.
 */
public interface DebitTransactionTypeService {

    /**
     * Save a debitTransactionType.
     *
     * @param debitTransactionTypeDTO the entity to save
     * @return the persisted entity
     */
    DebitTransactionTypeDTO save(DebitTransactionTypeDTO debitTransactionTypeDTO);

    /**
     *  Get all the debitTransactionTypes.
     *
     *  @return the list of entities
     */
    List<DebitTransactionTypeDTO> findAll();

    /**
     *  Get the "id" debitTransactionType.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    DebitTransactionTypeDTO findOne(Long id);

    /**
     *  Delete the "id" debitTransactionType.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
