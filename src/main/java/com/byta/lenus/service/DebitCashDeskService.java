package com.byta.lenus.service;

import com.byta.lenus.service.dto.DebitCashDeskDTO;
import java.util.List;

/**
 * Service Interface for managing DebitCashDesk.
 */
public interface DebitCashDeskService {

    /**
     * Save a debitCashDesk.
     *
     * @param debitCashDeskDTO the entity to save
     * @return the persisted entity
     */
    DebitCashDeskDTO save(DebitCashDeskDTO debitCashDeskDTO);

    /**
     *  Get all the debitCashDesks.
     *
     *  @return the list of entities
     */
    List<DebitCashDeskDTO> findAll();

    /**
     *  Get the "id" debitCashDesk.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    DebitCashDeskDTO findOne(Long id);

    /**
     *  Delete the "id" debitCashDesk.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
