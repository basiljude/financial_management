package com.byta.lenus.service;

import com.byta.lenus.service.dto.EncounderDTO;
import java.util.List;

/**
 * Service Interface for managing Encounder.
 */
public interface EncounderService {

    /**
     * Save a encounder.
     *
     * @param encounderDTO the entity to save
     * @return the persisted entity
     */
    EncounderDTO save(EncounderDTO encounderDTO);

    /**
     *  Get all the encounders.
     *
     *  @return the list of entities
     */
    List<EncounderDTO> findAll();

    /**
     *  Get the "id" encounder.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    EncounderDTO findOne(Long id);

    /**
     *  Delete the "id" encounder.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
