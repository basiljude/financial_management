package com.byta.lenus.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.byta.lenus.service.TransactionTypenService;
import com.byta.lenus.web.rest.errors.BadRequestAlertException;
import com.byta.lenus.web.rest.util.HeaderUtil;
import com.byta.lenus.service.dto.TransactionTypenDTO;
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
 * REST controller for managing TransactionTypen.
 */
@RestController
@RequestMapping("/api")
public class TransactionTypenResource {

    private final Logger log = LoggerFactory.getLogger(TransactionTypenResource.class);

    private static final String ENTITY_NAME = "transactionTypen";

    private final TransactionTypenService transactionTypenService;

    public TransactionTypenResource(TransactionTypenService transactionTypenService) {
        this.transactionTypenService = transactionTypenService;
    }

    /**
     * POST  /transaction-typens : Create a new transactionTypen.
     *
     * @param transactionTypenDTO the transactionTypenDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new transactionTypenDTO, or with status 400 (Bad Request) if the transactionTypen has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/transaction-typens")
    @Timed
    public ResponseEntity<TransactionTypenDTO> createTransactionTypen(@RequestBody TransactionTypenDTO transactionTypenDTO) throws URISyntaxException {
        log.debug("REST request to save TransactionTypen : {}", transactionTypenDTO);
        if (transactionTypenDTO.getId() != null) {
            throw new BadRequestAlertException("A new transactionTypen cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TransactionTypenDTO result = transactionTypenService.save(transactionTypenDTO);
        return ResponseEntity.created(new URI("/api/transaction-typens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /transaction-typens : Updates an existing transactionTypen.
     *
     * @param transactionTypenDTO the transactionTypenDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated transactionTypenDTO,
     * or with status 400 (Bad Request) if the transactionTypenDTO is not valid,
     * or with status 500 (Internal Server Error) if the transactionTypenDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/transaction-typens")
    @Timed
    public ResponseEntity<TransactionTypenDTO> updateTransactionTypen(@RequestBody TransactionTypenDTO transactionTypenDTO) throws URISyntaxException {
        log.debug("REST request to update TransactionTypen : {}", transactionTypenDTO);
        if (transactionTypenDTO.getId() == null) {
            return createTransactionTypen(transactionTypenDTO);
        }
        TransactionTypenDTO result = transactionTypenService.save(transactionTypenDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, transactionTypenDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /transaction-typens : get all the transactionTypens.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of transactionTypens in body
     */
    @GetMapping("/transaction-typens")
    @Timed
    public List<TransactionTypenDTO> getAllTransactionTypens() {
        log.debug("REST request to get all TransactionTypens");
        return transactionTypenService.findAll();
        }

    /**
     * GET  /transaction-typens/:id : get the "id" transactionTypen.
     *
     * @param id the id of the transactionTypenDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the transactionTypenDTO, or with status 404 (Not Found)
     */
    @GetMapping("/transaction-typens/{id}")
    @Timed
    public ResponseEntity<TransactionTypenDTO> getTransactionTypen(@PathVariable Long id) {
        log.debug("REST request to get TransactionTypen : {}", id);
        TransactionTypenDTO transactionTypenDTO = transactionTypenService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(transactionTypenDTO));
    }

    /**
     * DELETE  /transaction-typens/:id : delete the "id" transactionTypen.
     *
     * @param id the id of the transactionTypenDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/transaction-typens/{id}")
    @Timed
    public ResponseEntity<Void> deleteTransactionTypen(@PathVariable Long id) {
        log.debug("REST request to delete TransactionTypen : {}", id);
        transactionTypenService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
