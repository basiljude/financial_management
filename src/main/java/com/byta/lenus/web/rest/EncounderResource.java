package com.byta.lenus.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.byta.lenus.service.EncounderService;
import com.byta.lenus.web.rest.errors.BadRequestAlertException;
import com.byta.lenus.web.rest.util.HeaderUtil;
import com.byta.lenus.service.dto.EncounderDTO;
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
 * REST controller for managing Encounder.
 */
@RestController
@RequestMapping("/api")
public class EncounderResource {

    private final Logger log = LoggerFactory.getLogger(EncounderResource.class);

    private static final String ENTITY_NAME = "encounder";

    private final EncounderService encounderService;

    public EncounderResource(EncounderService encounderService) {
        this.encounderService = encounderService;
    }

    /**
     * POST  /encounders : Create a new encounder.
     *
     * @param encounderDTO the encounderDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new encounderDTO, or with status 400 (Bad Request) if the encounder has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/encounders")
    @Timed
    public ResponseEntity<EncounderDTO> createEncounder(@RequestBody EncounderDTO encounderDTO) throws URISyntaxException {
        log.debug("REST request to save Encounder : {}", encounderDTO);
        if (encounderDTO.getId() != null) {
            throw new BadRequestAlertException("A new encounder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EncounderDTO result = encounderService.save(encounderDTO);
        return ResponseEntity.created(new URI("/api/encounders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /encounders : Updates an existing encounder.
     *
     * @param encounderDTO the encounderDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated encounderDTO,
     * or with status 400 (Bad Request) if the encounderDTO is not valid,
     * or with status 500 (Internal Server Error) if the encounderDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/encounders")
    @Timed
    public ResponseEntity<EncounderDTO> updateEncounder(@RequestBody EncounderDTO encounderDTO) throws URISyntaxException {
        log.debug("REST request to update Encounder : {}", encounderDTO);
        if (encounderDTO.getId() == null) {
            return createEncounder(encounderDTO);
        }
        EncounderDTO result = encounderService.save(encounderDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, encounderDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /encounders : get all the encounders.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of encounders in body
     */
    @GetMapping("/encounders")
    @Timed
    public List<EncounderDTO> getAllEncounders() {
        log.debug("REST request to get all Encounders");
        return encounderService.findAll();
        }

    /**
     * GET  /encounders/:id : get the "id" encounder.
     *
     * @param id the id of the encounderDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the encounderDTO, or with status 404 (Not Found)
     */
    @GetMapping("/encounders/{id}")
    @Timed
    public ResponseEntity<EncounderDTO> getEncounder(@PathVariable Long id) {
        log.debug("REST request to get Encounder : {}", id);
        EncounderDTO encounderDTO = encounderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(encounderDTO));
    }

    /**
     * DELETE  /encounders/:id : delete the "id" encounder.
     *
     * @param id the id of the encounderDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/encounders/{id}")
    @Timed
    public ResponseEntity<Void> deleteEncounder(@PathVariable Long id) {
        log.debug("REST request to delete Encounder : {}", id);
        encounderService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
