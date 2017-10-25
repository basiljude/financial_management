package com.byta.lenus.service;

import com.byta.lenus.service.dto.TransactionTypenDTO;
import java.util.List;

/**
 * Service Interface for managing TransactionTypen.
 */
public interface TransactionTypenService {

    /**
     * Save a transactionTypen.
     *
     * @param transactionTypenDTO the entity to save
     * @return the persisted entity
     */
    TransactionTypenDTO save(TransactionTypenDTO transactionTypenDTO);

    /**
     *  Get all the transactionTypens.
     *
     *  @return the list of entities
     */
    List<TransactionTypenDTO> findAll();

    /**
     *  Get the "id" transactionTypen.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TransactionTypenDTO findOne(Long id);

    /**
     *  Delete the "id" transactionTypen.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
