package com.byta.lenus.service.impl;

import com.byta.lenus.service.DebitCashDeskService;
import com.byta.lenus.domain.DebitCashDesk;
import com.byta.lenus.repository.DebitCashDeskRepository;
import com.byta.lenus.service.dto.DebitCashDeskDTO;
import com.byta.lenus.service.mapper.DebitCashDeskMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing DebitCashDesk.
 */
@Service
@Transactional
public class DebitCashDeskServiceImpl implements DebitCashDeskService{

    private final Logger log = LoggerFactory.getLogger(DebitCashDeskServiceImpl.class);

    private final DebitCashDeskRepository debitCashDeskRepository;

    private final DebitCashDeskMapper debitCashDeskMapper;

    public DebitCashDeskServiceImpl(DebitCashDeskRepository debitCashDeskRepository, DebitCashDeskMapper debitCashDeskMapper) {
        this.debitCashDeskRepository = debitCashDeskRepository;
        this.debitCashDeskMapper = debitCashDeskMapper;
    }

    /**
     * Save a debitCashDesk.
     *
     * @param debitCashDeskDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DebitCashDeskDTO save(DebitCashDeskDTO debitCashDeskDTO) {
        log.debug("Request to save DebitCashDesk : {}", debitCashDeskDTO);
        DebitCashDesk debitCashDesk = debitCashDeskMapper.toEntity(debitCashDeskDTO);
        debitCashDesk = debitCashDeskRepository.save(debitCashDesk);
        return debitCashDeskMapper.toDto(debitCashDesk);
    }

    /**
     *  Get all the debitCashDesks.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<DebitCashDeskDTO> findAll() {
        log.debug("Request to get all DebitCashDesks");
        return debitCashDeskRepository.findAll().stream()
            .map(debitCashDeskMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one debitCashDesk by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DebitCashDeskDTO findOne(Long id) {
        log.debug("Request to get DebitCashDesk : {}", id);
        DebitCashDesk debitCashDesk = debitCashDeskRepository.findOne(id);
        return debitCashDeskMapper.toDto(debitCashDesk);
    }

    /**
     *  Delete the  debitCashDesk by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DebitCashDesk : {}", id);
        debitCashDeskRepository.delete(id);
    }
}
