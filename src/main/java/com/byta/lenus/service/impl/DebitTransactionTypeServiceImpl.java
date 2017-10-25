package com.byta.lenus.service.impl;

import com.byta.lenus.service.DebitTransactionTypeService;
import com.byta.lenus.domain.DebitTransactionType;
import com.byta.lenus.repository.DebitTransactionTypeRepository;
import com.byta.lenus.service.dto.DebitTransactionTypeDTO;
import com.byta.lenus.service.mapper.DebitTransactionTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing DebitTransactionType.
 */
@Service
@Transactional
public class DebitTransactionTypeServiceImpl implements DebitTransactionTypeService{

    private final Logger log = LoggerFactory.getLogger(DebitTransactionTypeServiceImpl.class);

    private final DebitTransactionTypeRepository debitTransactionTypeRepository;

    private final DebitTransactionTypeMapper debitTransactionTypeMapper;

    public DebitTransactionTypeServiceImpl(DebitTransactionTypeRepository debitTransactionTypeRepository, DebitTransactionTypeMapper debitTransactionTypeMapper) {
        this.debitTransactionTypeRepository = debitTransactionTypeRepository;
        this.debitTransactionTypeMapper = debitTransactionTypeMapper;
    }

    /**
     * Save a debitTransactionType.
     *
     * @param debitTransactionTypeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DebitTransactionTypeDTO save(DebitTransactionTypeDTO debitTransactionTypeDTO) {
        log.debug("Request to save DebitTransactionType : {}", debitTransactionTypeDTO);
        DebitTransactionType debitTransactionType = debitTransactionTypeMapper.toEntity(debitTransactionTypeDTO);
        debitTransactionType = debitTransactionTypeRepository.save(debitTransactionType);
        return debitTransactionTypeMapper.toDto(debitTransactionType);
    }

    /**
     *  Get all the debitTransactionTypes.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<DebitTransactionTypeDTO> findAll() {
        log.debug("Request to get all DebitTransactionTypes");
        return debitTransactionTypeRepository.findAll().stream()
            .map(debitTransactionTypeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one debitTransactionType by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DebitTransactionTypeDTO findOne(Long id) {
        log.debug("Request to get DebitTransactionType : {}", id);
        DebitTransactionType debitTransactionType = debitTransactionTypeRepository.findOne(id);
        return debitTransactionTypeMapper.toDto(debitTransactionType);
    }

    /**
     *  Delete the  debitTransactionType by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DebitTransactionType : {}", id);
        debitTransactionTypeRepository.delete(id);
    }
}
