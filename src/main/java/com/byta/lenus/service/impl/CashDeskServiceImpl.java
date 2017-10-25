package com.byta.lenus.service.impl;

import com.byta.lenus.service.CashDeskService;
import com.byta.lenus.domain.CashDesk;
import com.byta.lenus.repository.CashDeskRepository;
import com.byta.lenus.service.dto.CashDeskDTO;
import com.byta.lenus.service.mapper.CashDeskMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing CashDesk.
 */
@Service
@Transactional
public class CashDeskServiceImpl implements CashDeskService{

    private final Logger log = LoggerFactory.getLogger(CashDeskServiceImpl.class);

    private final CashDeskRepository cashDeskRepository;

    private final CashDeskMapper cashDeskMapper;

    public CashDeskServiceImpl(CashDeskRepository cashDeskRepository, CashDeskMapper cashDeskMapper) {
        this.cashDeskRepository = cashDeskRepository;
        this.cashDeskMapper = cashDeskMapper;
    }

    /**
     * Save a cashDesk.
     *
     * @param cashDeskDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CashDeskDTO save(CashDeskDTO cashDeskDTO) {
        log.debug("Request to save CashDesk : {}", cashDeskDTO);
        CashDesk cashDesk = cashDeskMapper.toEntity(cashDeskDTO);
        cashDesk = cashDeskRepository.save(cashDesk);
        return cashDeskMapper.toDto(cashDesk);
    }

    /**
     *  Get all the cashDesks.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CashDeskDTO> findAll() {
        log.debug("Request to get all CashDesks");
        return cashDeskRepository.findAll().stream()
            .map(cashDeskMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one cashDesk by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CashDeskDTO findOne(Long id) {
        log.debug("Request to get CashDesk : {}", id);
        CashDesk cashDesk = cashDeskRepository.findOne(id);
        return cashDeskMapper.toDto(cashDesk);
    }

    /**
     *  Delete the  cashDesk by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CashDesk : {}", id);
        cashDeskRepository.delete(id);
    }
}
