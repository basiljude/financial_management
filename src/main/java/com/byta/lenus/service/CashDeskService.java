package com.byta.lenus.service;

import com.byta.lenus.service.dto.CashDeskDTO;
import java.util.List;

/**
 * Service Interface for managing CashDesk.
 */
public interface CashDeskService {

    /**
     * Save a cashDesk.
     *
     * @param cashDeskDTO the entity to save
     * @return the persisted entity
     */
    CashDeskDTO save(CashDeskDTO cashDeskDTO);

    /**
     *  Get all the cashDesks.
     *
     *  @return the list of entities
     */
    List<CashDeskDTO> findAll();

    /**
     *  Get the "id" cashDesk.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CashDeskDTO findOne(Long id);

    /**
     *  Delete the "id" cashDesk.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
