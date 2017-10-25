package com.byta.lenus.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.byta.lenus.service.AddDebitService;
import com.byta.lenus.web.rest.errors.BadRequestAlertException;
import com.byta.lenus.web.rest.util.HeaderUtil;
import com.byta.lenus.service.dto.AddDebitDTO;
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
 * REST controller for managing AddDebit.
 */
@RestController
@RequestMapping("/api")
public class AddDebitResource {

    private final Logger log = LoggerFactory.getLogger(AddDebitResource.class);

    private static final String ENTITY_NAME = "addDebit";

    private final AddDebitService addDebitService;

    public AddDebitResource(AddDebitService addDebitService) {
        this.addDebitService = addDebitService;
    }

    /**
     * POST  /add-debits : Create a new addDebit.
     *
     * @param addDebitDTO the addDebitDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new addDebitDTO, or with status 400 (Bad Request) if the addDebit has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/add-debits")
    @Timed
    public ResponseEntity<AddDebitDTO> createAddDebit(@RequestBody AddDebitDTO addDebitDTO) throws URISyntaxException {
        log.debug("REST request to save AddDebit : {}", addDebitDTO);
        if (addDebitDTO.getId() != null) {
            throw new BadRequestAlertException("A new addDebit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AddDebitDTO result = addDebitService.save(addDebitDTO);
        return ResponseEntity.created(new URI("/api/add-debits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /add-debits : Updates an existing addDebit.
     *
     * @param addDebitDTO the addDebitDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated addDebitDTO,
     * or with status 400 (Bad Request) if the addDebitDTO is not valid,
     * or with status 500 (Internal Server Error) if the addDebitDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/add-debits")
    @Timed
    public ResponseEntity<AddDebitDTO> updateAddDebit(@RequestBody AddDebitDTO addDebitDTO) throws URISyntaxException {
        log.debug("REST request to update AddDebit : {}", addDebitDTO);
        if (addDebitDTO.getId() == null) {
            return createAddDebit(addDebitDTO);
        }
        AddDebitDTO result = addDebitService.save(addDebitDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, addDebitDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /add-debits : get all the addDebits.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of addDebits in body
     */
    @GetMapping("/add-debits")
    @Timed
    public List<AddDebitDTO> getAllAddDebits() {
        log.debug("REST request to get all AddDebits");
        return addDebitService.findAll();
        }

    /**
     * GET  /add-debits/:id : get the "id" addDebit.
     *
     * @param id the id of the addDebitDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the addDebitDTO, or with status 404 (Not Found)
     */
    @GetMapping("/add-debits/{id}")
    @Timed
    public ResponseEntity<AddDebitDTO> getAddDebit(@PathVariable Long id) {
        log.debug("REST request to get AddDebit : {}", id);
        AddDebitDTO addDebitDTO = addDebitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(addDebitDTO));
    }

    /**
     * DELETE  /add-debits/:id : delete the "id" addDebit.
     *
     * @param id the id of the addDebitDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/add-debits/{id}")
    @Timed
    public ResponseEntity<Void> deleteAddDebit(@PathVariable Long id) {
        log.debug("REST request to delete AddDebit : {}", id);
        addDebitService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
