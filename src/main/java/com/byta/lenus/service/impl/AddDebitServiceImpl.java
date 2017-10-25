package com.byta.lenus.service.impl;

import com.byta.lenus.service.AddDebitService;
import com.byta.lenus.domain.AddDebit;
import com.byta.lenus.repository.AddDebitRepository;
import com.byta.lenus.service.dto.AddDebitDTO;
import com.byta.lenus.service.mapper.AddDebitMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing AddDebit.
 */
@Service
@Transactional
public class AddDebitServiceImpl implements AddDebitService{

    private final Logger log = LoggerFactory.getLogger(AddDebitServiceImpl.class);

    private final AddDebitRepository addDebitRepository;

    private final AddDebitMapper addDebitMapper;

    public AddDebitServiceImpl(AddDebitRepository addDebitRepository, AddDebitMapper addDebitMapper) {
        this.addDebitRepository = addDebitRepository;
        this.addDebitMapper = addDebitMapper;
    }

    /**
     * Save a addDebit.
     *
     * @param addDebitDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AddDebitDTO save(AddDebitDTO addDebitDTO) {
        log.debug("Request to save AddDebit : {}", addDebitDTO);
        AddDebit addDebit = addDebitMapper.toEntity(addDebitDTO);
        addDebit = addDebitRepository.save(addDebit);
        return addDebitMapper.toDto(addDebit);
    }

    /**
     *  Get all the addDebits.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<AddDebitDTO> findAll() {
        log.debug("Request to get all AddDebits");
        return addDebitRepository.findAll().stream()
            .map(addDebitMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one addDebit by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AddDebitDTO findOne(Long id) {
        log.debug("Request to get AddDebit : {}", id);
        AddDebit addDebit = addDebitRepository.findOne(id);
        return addDebitMapper.toDto(addDebit);
    }

    /**
     *  Delete the  addDebit by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AddDebit : {}", id);
        addDebitRepository.delete(id);
    }
}
