package com.byta.lenus.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.byta.lenus.service.DebitCashDeskService;
import com.byta.lenus.web.rest.errors.BadRequestAlertException;
import com.byta.lenus.web.rest.util.HeaderUtil;
import com.byta.lenus.service.dto.DebitCashDeskDTO;
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
 * REST controller for managing DebitCashDesk.
 */
@RestController
@RequestMapping("/api")
public class DebitCashDeskResource {

    private final Logger log = LoggerFactory.getLogger(DebitCashDeskResource.class);

    private static final String ENTITY_NAME = "debitCashDesk";

    private final DebitCashDeskService debitCashDeskService;

    public DebitCashDeskResource(DebitCashDeskService debitCashDeskService) {
        this.debitCashDeskService = debitCashDeskService;
    }

    /**
     * POST  /debit-cash-desks : Create a new debitCashDesk.
     *
     * @param debitCashDeskDTO the debitCashDeskDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new debitCashDeskDTO, or with status 400 (Bad Request) if the debitCashDesk has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/debit-cash-desks")
    @Timed
    public ResponseEntity<DebitCashDeskDTO> createDebitCashDesk(@RequestBody DebitCashDeskDTO debitCashDeskDTO) throws URISyntaxException {
        log.debug("REST request to save DebitCashDesk : {}", debitCashDeskDTO);
        if (debitCashDeskDTO.getId() != null) {
            throw new BadRequestAlertException("A new debitCashDesk cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DebitCashDeskDTO result = debitCashDeskService.save(debitCashDeskDTO);
        return ResponseEntity.created(new URI("/api/debit-cash-desks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /debit-cash-desks : Updates an existing debitCashDesk.
     *
     * @param debitCashDeskDTO the debitCashDeskDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated debitCashDeskDTO,
     * or with status 400 (Bad Request) if the debitCashDeskDTO is not valid,
     * or with status 500 (Internal Server Error) if the debitCashDeskDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/debit-cash-desks")
    @Timed
    public ResponseEntity<DebitCashDeskDTO> updateDebitCashDesk(@RequestBody DebitCashDeskDTO debitCashDeskDTO) throws URISyntaxException {
        log.debug("REST request to update DebitCashDesk : {}", debitCashDeskDTO);
        if (debitCashDeskDTO.getId() == null) {
            return createDebitCashDesk(debitCashDeskDTO);
        }
        DebitCashDeskDTO result = debitCashDeskService.save(debitCashDeskDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, debitCashDeskDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /debit-cash-desks : get all the debitCashDesks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of debitCashDesks in body
     */
    @GetMapping("/debit-cash-desks")
    @Timed
    public List<DebitCashDeskDTO> getAllDebitCashDesks() {
        log.debug("REST request to get all DebitCashDesks");
        return debitCashDeskService.findAll();
        }

    /**
     * GET  /debit-cash-desks/:id : get the "id" debitCashDesk.
     *
     * @param id the id of the debitCashDeskDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the debitCashDeskDTO, or with status 404 (Not Found)
     */
    @GetMapping("/debit-cash-desks/{id}")
    @Timed
    public ResponseEntity<DebitCashDeskDTO> getDebitCashDesk(@PathVariable Long id) {
        log.debug("REST request to get DebitCashDesk : {}", id);
        DebitCashDeskDTO debitCashDeskDTO = debitCashDeskService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(debitCashDeskDTO));
    }

    /**
     * DELETE  /debit-cash-desks/:id : delete the "id" debitCashDesk.
     *
     * @param id the id of the debitCashDeskDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/debit-cash-desks/{id}")
    @Timed
    public ResponseEntity<Void> deleteDebitCashDesk(@PathVariable Long id) {
        log.debug("REST request to delete DebitCashDesk : {}", id);
        debitCashDeskService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
