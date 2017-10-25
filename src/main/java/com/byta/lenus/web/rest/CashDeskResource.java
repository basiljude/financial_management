package com.byta.lenus.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.byta.lenus.service.CashDeskService;
import com.byta.lenus.web.rest.errors.BadRequestAlertException;
import com.byta.lenus.web.rest.util.HeaderUtil;
import com.byta.lenus.service.dto.CashDeskDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CashDesk.
 */
@RestController
@RequestMapping("/api")
public class CashDeskResource {

    private final Logger log = LoggerFactory.getLogger(CashDeskResource.class);

    private static final String ENTITY_NAME = "cashDesk";

    private final CashDeskService cashDeskService;

    public CashDeskResource(CashDeskService cashDeskService) {
        this.cashDeskService = cashDeskService;
    }

    /**
     * POST  /cash-desks : Create a new cashDesk.
     *
     * @param cashDeskDTO the cashDeskDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cashDeskDTO, or with status 400 (Bad Request) if the cashDesk has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cash-desks")
    @Timed
    public ResponseEntity<CashDeskDTO> createCashDesk(@RequestBody CashDeskDTO cashDeskDTO) throws URISyntaxException {
        log.debug("REST request to save CashDesk : {}", cashDeskDTO);
        if (cashDeskDTO.getId() != null) {
            throw new BadRequestAlertException("A new cashDesk cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CashDeskDTO result = cashDeskService.save(cashDeskDTO);
        return ResponseEntity.created(new URI("/api/cash-desks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cash-desks : Updates an existing cashDesk.
     *
     * @param cashDeskDTO the cashDeskDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cashDeskDTO,
     * or with status 400 (Bad Request) if the cashDeskDTO is not valid,
     * or with status 500 (Internal Server Error) if the cashDeskDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cash-desks")
    @Timed
    public ResponseEntity<CashDeskDTO> updateCashDesk(@RequestBody CashDeskDTO cashDeskDTO) throws URISyntaxException {
        log.debug("REST request to update CashDesk : {}", cashDeskDTO);
        if (cashDeskDTO.getId() == null) {
            return createCashDesk(cashDeskDTO);
        }
        CashDeskDTO result = cashDeskService.save(cashDeskDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cashDeskDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cash-desks : get all the cashDesks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cashDesks in body
     */
    @GetMapping("/cash-desks")
    @Timed
    public List<CashDeskDTO> getAllCashDesks() {
        log.debug("REST request to get all CashDesks");
        return cashDeskService.findAll();
        }

    /**
     * GET  /cash-desks/:id : get the "id" cashDesk.
     *
     * @param id the id of the cashDeskDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cashDeskDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cash-desks/{id}")
    @Timed
    public ResponseEntity<CashDeskDTO> getCashDesk(@PathVariable Long id) {
        log.debug("REST request to get CashDesk : {}", id);
        CashDeskDTO cashDeskDTO = cashDeskService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(cashDeskDTO));
    }

    /**
     * DELETE  /cash-desks/:id : delete the "id" cashDesk.
     *
     * @param id the id of the cashDeskDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cash-desks/{id}")
    @Timed
    public ResponseEntity<Void> deleteCashDesk(@PathVariable Long id) {
        log.debug("REST request to delete CashDesk : {}", id);
        cashDeskService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
