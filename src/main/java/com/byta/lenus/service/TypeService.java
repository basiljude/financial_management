package com.byta.lenus.service;

import com.byta.lenus.service.dto.TypeDTO;
import java.util.List;

/**
 * Service Interface for managing Type.
 */
public interface TypeService {

    /**
     * Save a type.
     *
     * @param typeDTO the entity to save
     * @return the persisted entity
     */
    TypeDTO save(TypeDTO typeDTO);

    /**
     *  Get all the types.
     *
     *  @return the list of entities
     */
    List<TypeDTO> findAll();

    /**
     *  Get the "id" type.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TypeDTO findOne(Long id);

    /**
     *  Delete the "id" type.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
