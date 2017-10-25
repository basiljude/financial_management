package com.byta.lenus.service.impl;

import com.byta.lenus.service.TransactionTypenService;
import com.byta.lenus.domain.TransactionTypen;
import com.byta.lenus.repository.TransactionTypenRepository;
import com.byta.lenus.service.dto.TransactionTypenDTO;
import com.byta.lenus.service.mapper.TransactionTypenMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing TransactionTypen.
 */
@Service
@Transactional
public class TransactionTypenServiceImpl implements TransactionTypenService{

    private final Logger log = LoggerFactory.getLogger(TransactionTypenServiceImpl.class);

    private final TransactionTypenRepository transactionTypenRepository;

    private final TransactionTypenMapper transactionTypenMapper;

    public TransactionTypenServiceImpl(TransactionTypenRepository transactionTypenRepository, TransactionTypenMapper transactionTypenMapper) {
        this.transactionTypenRepository = transactionTypenRepository;
        this.transactionTypenMapper = transactionTypenMapper;
    }

    /**
     * Save a transactionTypen.
     *
     * @param transactionTypenDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TransactionTypenDTO save(TransactionTypenDTO transactionTypenDTO) {
        log.debug("Request to save TransactionTypen : {}", transactionTypenDTO);
        TransactionTypen transactionTypen = transactionTypenMapper.toEntity(transactionTypenDTO);
        transactionTypen = transactionTypenRepository.save(transactionTypen);
        return transactionTypenMapper.toDto(transactionTypen);
    }

    /**
     *  Get all the transactionTypens.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TransactionTypenDTO> findAll() {
        log.debug("Request to get all TransactionTypens");
        return transactionTypenRepository.findAll().stream()
            .map(transactionTypenMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one transactionTypen by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TransactionTypenDTO findOne(Long id) {
        log.debug("Request to get TransactionTypen : {}", id);
        TransactionTypen transactionTypen = transactionTypenRepository.findOne(id);
        return transactionTypenMapper.toDto(transactionTypen);
    }

    /**
     *  Delete the  transactionTypen by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TransactionTypen : {}", id);
        transactionTypenRepository.delete(id);
    }
}
