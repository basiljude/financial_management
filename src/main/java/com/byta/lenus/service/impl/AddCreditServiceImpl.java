package com.byta.lenus.service.impl;

import com.byta.lenus.service.AddCreditService;
import com.byta.lenus.domain.AddCredit;
import com.byta.lenus.repository.AddCreditRepository;
import com.byta.lenus.service.dto.AddCreditDTO;
import com.byta.lenus.service.mapper.AddCreditMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing AddCredit.
 */
@Service
@Transactional
public class AddCreditServiceImpl implements AddCreditService{

    private final Logger log = LoggerFactory.getLogger(AddCreditServiceImpl.class);

    private final AddCreditRepository addCreditRepository;

    private final AddCreditMapper addCreditMapper;

    public AddCreditServiceImpl(AddCreditRepository addCreditRepository, AddCreditMapper addCreditMapper) {
        this.addCreditRepository = addCreditRepository;
        this.addCreditMapper = addCreditMapper;
    }

    /**
     * Save a addCredit.
     *
     * @param addCreditDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AddCreditDTO save(AddCreditDTO addCreditDTO) {
        log.debug("Request to save AddCredit : {}", addCreditDTO);
        AddCredit addCredit = addCreditMapper.toEntity(addCreditDTO);
        addCredit = addCreditRepository.save(addCredit);
        return addCreditMapper.toDto(addCredit);
    }

    /**
     *  Get all the addCredits.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<AddCreditDTO> findAll() {
        log.debug("Request to get all AddCredits");
        return addCreditRepository.findAll().stream()
            .map(addCreditMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one addCredit by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AddCreditDTO findOne(Long id) {
        log.debug("Request to get AddCredit : {}", id);
        AddCredit addCredit = addCreditRepository.findOne(id);
        return addCreditMapper.toDto(addCredit);
    }

    /**
     *  Delete the  addCredit by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AddCredit : {}", id);
        addCreditRepository.delete(id);
    }
}
