package com.byta.lenus.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.byta.lenus.service.AddCreditService;
import com.byta.lenus.web.rest.errors.BadRequestAlertException;
import com.byta.lenus.web.rest.util.HeaderUtil;
import com.byta.lenus.service.dto.AddCreditDTO;
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
 * REST controller for managing AddCredit.
 */
@RestController
@RequestMapping("/api")
public class AddCreditResource {

    private final Logger log = LoggerFactory.getLogger(AddCreditResource.class);

    private static final String ENTITY_NAME = "addCredit";

    private final AddCreditService addCreditService;

    public AddCreditResource(AddCreditService addCreditService) {
        this.addCreditService = addCreditService;
    }

    /**
     * POST  /add-credits : Create a new addCredit.
     *
     * @param addCreditDTO the addCreditDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new addCreditDTO, or with status 400 (Bad Request) if the addCredit has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/add-credits")
    @Timed
    public ResponseEntity<AddCreditDTO> createAddCredit(@RequestBody AddCreditDTO addCreditDTO) throws URISyntaxException {
        log.debug("REST request to save AddCredit : {}", addCreditDTO);
        if (addCreditDTO.getId() != null) {
            throw new BadRequestAlertException("A new addCredit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AddCreditDTO result = addCreditService.save(addCreditDTO);
        return ResponseEntity.created(new URI("/api/add-credits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /add-credits : Updates an existing addCredit.
     *
     * @param addCreditDTO the addCreditDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated addCreditDTO,
     * or with status 400 (Bad Request) if the addCreditDTO is not valid,
     * or with status 500 (Internal Server Error) if the addCreditDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/add-credits")
    @Timed
    public ResponseEntity<AddCreditDTO> updateAddCredit(@RequestBody AddCreditDTO addCreditDTO) throws URISyntaxException {
        log.debug("REST request to update AddCredit : {}", addCreditDTO);
        if (addCreditDTO.getId() == null) {
            return createAddCredit(addCreditDTO);
        }
        AddCreditDTO result = addCreditService.save(addCreditDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, addCreditDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /add-credits : get all the addCredits.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of addCredits in body
     */
    @GetMapping("/add-credits")
    @Timed
    public List<AddCreditDTO> getAllAddCredits() {
        log.debug("REST request to get all AddCredits");
        return addCreditService.findAll();
        }

    /**
     * GET  /add-credits/:id : get the "id" addCredit.
     *
     * @param id the id of the addCreditDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the addCreditDTO, or with status 404 (Not Found)
     */
    @GetMapping("/add-credits/{id}")
    @Timed
    public ResponseEntity<AddCreditDTO> getAddCredit(@PathVariable Long id) {
        log.debug("REST request to get AddCredit : {}", id);
        AddCreditDTO addCreditDTO = addCreditService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(addCreditDTO));
    }

    /**
     * DELETE  /add-credits/:id : delete the "id" addCredit.
     *
     * @param id the id of the addCreditDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/add-credits/{id}")
    @Timed
    public ResponseEntity<Void> deleteAddCredit(@PathVariable Long id) {
        log.debug("REST request to delete AddCredit : {}", id);
        addCreditService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
