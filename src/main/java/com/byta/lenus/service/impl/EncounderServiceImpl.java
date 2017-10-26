package com.byta.lenus.service.impl;

import com.byta.lenus.service.EncounderService;
import com.byta.lenus.domain.Encounder;
import com.byta.lenus.repository.EncounderRepository;
import com.byta.lenus.service.dto.EncounderDTO;
import com.byta.lenus.service.mapper.EncounderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Encounder.
 */
@Service
@Transactional
public class EncounderServiceImpl implements EncounderService{

    private final Logger log = LoggerFactory.getLogger(EncounderServiceImpl.class);

    private final EncounderRepository encounderRepository;

    private final EncounderMapper encounderMapper;

    public EncounderServiceImpl(EncounderRepository encounderRepository, EncounderMapper encounderMapper) {
        this.encounderRepository = encounderRepository;
        this.encounderMapper = encounderMapper;
    }

    /**
     * Save a encounder.
     *
     * @param encounderDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EncounderDTO save(EncounderDTO encounderDTO) {
        log.debug("Request to save Encounder : {}", encounderDTO);
        Encounder encounder = encounderMapper.toEntity(encounderDTO);
        encounder = encounderRepository.save(encounder);
        return encounderMapper.toDto(encounder);
    }

    /**
     *  Get all the encounders.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<EncounderDTO> findAll() {
        log.debug("Request to get all Encounders");
        return encounderRepository.findAll().stream()
            .map(encounderMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one encounder by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EncounderDTO findOne(Long id) {
        log.debug("Request to get Encounder : {}", id);
        Encounder encounder = encounderRepository.findOne(id);
        return encounderMapper.toDto(encounder);
    }

    /**
     *  Delete the  encounder by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Encounder : {}", id);
        encounderRepository.delete(id);
    }
}
