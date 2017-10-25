package com.byta.lenus.service;

import com.byta.lenus.service.dto.AddCreditDTO;
import java.util.List;

/**
 * Service Interface for managing AddCredit.
 */
public interface AddCreditService {

    /**
     * Save a addCredit.
     *
     * @param addCreditDTO the entity to save
     * @return the persisted entity
     */
    AddCreditDTO save(AddCreditDTO addCreditDTO);

    /**
     *  Get all the addCredits.
     *
     *  @return the list of entities
     */
    List<AddCreditDTO> findAll();

    /**
     *  Get the "id" addCredit.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    AddCreditDTO findOne(Long id);

    /**
     *  Delete the "id" addCredit.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
