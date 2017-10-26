package com.byta.lenus.service.impl;

import com.byta.lenus.service.PatientPaimentService;
import com.byta.lenus.domain.PatientPaiment;
import com.byta.lenus.repository.PatientPaimentRepository;
import com.byta.lenus.service.dto.PatientPaimentDTO;
import com.byta.lenus.service.mapper.PatientPaimentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing PatientPaiment.
 */
@Service
@Transactional
public class PatientPaimentServiceImpl implements PatientPaimentService{

    private final Logger log = LoggerFactory.getLogger(PatientPaimentServiceImpl.class);

    private final PatientPaimentRepository patientPaimentRepository;

    private final PatientPaimentMapper patientPaimentMapper;

    public PatientPaimentServiceImpl(PatientPaimentRepository patientPaimentRepository, PatientPaimentMapper patientPaimentMapper) {
        this.patientPaimentRepository = patientPaimentRepository;
        this.patientPaimentMapper = patientPaimentMapper;
    }

    /**
     * Save a patientPaiment.
     *
     * @param patientPaimentDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PatientPaimentDTO save(PatientPaimentDTO patientPaimentDTO) {
        log.debug("Request to save PatientPaiment : {}", patientPaimentDTO);
        PatientPaiment patientPaiment = patientPaimentMapper.toEntity(patientPaimentDTO);
        patientPaiment = patientPaimentRepository.save(patientPaiment);
        return patientPaimentMapper.toDto(patientPaiment);
    }

    /**
     *  Get all the patientPaiments.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PatientPaimentDTO> findAll() {
        log.debug("Request to get all PatientPaiments");
        return patientPaimentRepository.findAll().stream()
            .map(patientPaimentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one patientPaiment by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PatientPaimentDTO findOne(Long id) {
        log.debug("Request to get PatientPaiment : {}", id);
        PatientPaiment patientPaiment = patientPaimentRepository.findOne(id);
        return patientPaimentMapper.toDto(patientPaiment);
    }

    /**
     *  Delete the  patientPaiment by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PatientPaiment : {}", id);
        patientPaimentRepository.delete(id);
    }
}
