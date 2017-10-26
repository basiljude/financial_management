package com.byta.lenus.service;

import com.byta.lenus.service.dto.PatientPaimentDTO;
import java.util.List;

/**
 * Service Interface for managing PatientPaiment.
 */
public interface PatientPaimentService {

    /**
     * Save a patientPaiment.
     *
     * @param patientPaimentDTO the entity to save
     * @return the persisted entity
     */
    PatientPaimentDTO save(PatientPaimentDTO patientPaimentDTO);

    /**
     *  Get all the patientPaiments.
     *
     *  @return the list of entities
     */
    List<PatientPaimentDTO> findAll();

    /**
     *  Get the "id" patientPaiment.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PatientPaimentDTO findOne(Long id);

    /**
     *  Delete the "id" patientPaiment.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
