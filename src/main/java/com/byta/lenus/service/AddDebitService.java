package com.byta.lenus.service;

import com.byta.lenus.service.dto.AddDebitDTO;
import java.util.List;

/**
 * Service Interface for managing AddDebit.
 */
public interface AddDebitService {

    /**
     * Save a addDebit.
     *
     * @param addDebitDTO the entity to save
     * @return the persisted entity
     */
    AddDebitDTO save(AddDebitDTO addDebitDTO);

    /**
     *  Get all the addDebits.
     *
     *  @return the list of entities
     */
    List<AddDebitDTO> findAll();

    /**
     *  Get the "id" addDebit.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    AddDebitDTO findOne(Long id);

    /**
     *  Delete the "id" addDebit.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
