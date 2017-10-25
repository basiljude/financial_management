package com.byta.lenus.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.byta.lenus.service.DebitTransactionTypeService;
import com.byta.lenus.web.rest.errors.BadRequestAlertException;
import com.byta.lenus.web.rest.util.HeaderUtil;
import com.byta.lenus.service.dto.DebitTransactionTypeDTO;
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
 * REST controller for managing DebitTransactionType.
 */
@RestController
@RequestMapping("/api")
public class DebitTransactionTypeResource {

    private final Logger log = LoggerFactory.getLogger(DebitTransactionTypeResource.class);

    private static final String ENTITY_NAME = "debitTransactionType";

    private final DebitTransactionTypeService debitTransactionTypeService;

    public DebitTransactionTypeResource(DebitTransactionTypeService debitTransactionTypeService) {
        this.debitTransactionTypeService = debitTransactionTypeService;
    }

    /**
     * POST  /debit-transaction-types : Create a new debitTransactionType.
     *
     * @param debitTransactionTypeDTO the debitTransactionTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new debitTransactionTypeDTO, or with status 400 (Bad Request) if the debitTransactionType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/debit-transaction-types")
    @Timed
    public ResponseEntity<DebitTransactionTypeDTO> createDebitTransactionType(@RequestBody DebitTransactionTypeDTO debitTransactionTypeDTO) throws URISyntaxException {
        log.debug("REST request to save DebitTransactionType : {}", debitTransactionTypeDTO);
        if (debitTransactionTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new debitTransactionType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DebitTransactionTypeDTO result = debitTransactionTypeService.save(debitTransactionTypeDTO);
        return ResponseEntity.created(new URI("/api/debit-transaction-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /debit-transaction-types : Updates an existing debitTransactionType.
     *
     * @param debitTransactionTypeDTO the debitTransactionTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated debitTransactionTypeDTO,
     * or with status 400 (Bad Request) if the debitTransactionTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the debitTransactionTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/debit-transaction-types")
    @Timed
    public ResponseEntity<DebitTransactionTypeDTO> updateDebitTransactionType(@RequestBody DebitTransactionTypeDTO debitTransactionTypeDTO) throws URISyntaxException {
        log.debug("REST request to update DebitTransactionType : {}", debitTransactionTypeDTO);
        if (debitTransactionTypeDTO.getId() == null) {
            return createDebitTransactionType(debitTransactionTypeDTO);
        }
        DebitTransactionTypeDTO result = debitTransactionTypeService.save(debitTransactionTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, debitTransactionTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /debit-transaction-types : get all the debitTransactionTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of debitTransactionTypes in body
     */
    @GetMapping("/debit-transaction-types")
    @Timed
    public List<DebitTransactionTypeDTO> getAllDebitTransactionTypes() {
        log.debug("REST request to get all DebitTransactionTypes");
        return debitTransactionTypeService.findAll();
        }

    /**
     * GET  /debit-transaction-types/:id : get the "id" debitTransactionType.
     *
     * @param id the id of the debitTransactionTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the debitTransactionTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/debit-transaction-types/{id}")
    @Timed
    public ResponseEntity<DebitTransactionTypeDTO> getDebitTransactionType(@PathVariable Long id) {
        log.debug("REST request to get DebitTransactionType : {}", id);
        DebitTransactionTypeDTO debitTransactionTypeDTO = debitTransactionTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(debitTransactionTypeDTO));
    }

    /**
     * DELETE  /debit-transaction-types/:id : delete the "id" debitTransactionType.
     *
     * @param id the id of the debitTransactionTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/debit-transaction-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteDebitTransactionType(@PathVariable Long id) {
        log.debug("REST request to delete DebitTransactionType : {}", id);
        debitTransactionTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
