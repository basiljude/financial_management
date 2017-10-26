package com.byta.lenus.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.byta.lenus.service.PatientPaimentService;
import com.byta.lenus.web.rest.errors.BadRequestAlertException;
import com.byta.lenus.web.rest.util.HeaderUtil;
import com.byta.lenus.service.dto.PatientPaimentDTO;
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
 * REST controller for managing PatientPaiment.
 */
@RestController
@RequestMapping("/api")
public class PatientPaimentResource {

    private final Logger log = LoggerFactory.getLogger(PatientPaimentResource.class);

    private static final String ENTITY_NAME = "patientPaiment";

    private final PatientPaimentService patientPaimentService;

    public PatientPaimentResource(PatientPaimentService patientPaimentService) {
        this.patientPaimentService = patientPaimentService;
    }

    /**
     * POST  /patient-paiments : Create a new patientPaiment.
     *
     * @param patientPaimentDTO the patientPaimentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new patientPaimentDTO, or with status 400 (Bad Request) if the patientPaiment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/patient-paiments")
    @Timed
    public ResponseEntity<PatientPaimentDTO> createPatientPaiment(@RequestBody PatientPaimentDTO patientPaimentDTO) throws URISyntaxException {
        log.debug("REST request to save PatientPaiment : {}", patientPaimentDTO);
        if (patientPaimentDTO.getId() != null) {
            throw new BadRequestAlertException("A new patientPaiment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PatientPaimentDTO result = patientPaimentService.save(patientPaimentDTO);
        return ResponseEntity.created(new URI("/api/patient-paiments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /patient-paiments : Updates an existing patientPaiment.
     *
     * @param patientPaimentDTO the patientPaimentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated patientPaimentDTO,
     * or with status 400 (Bad Request) if the patientPaimentDTO is not valid,
     * or with status 500 (Internal Server Error) if the patientPaimentDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/patient-paiments")
    @Timed
    public ResponseEntity<PatientPaimentDTO> updatePatientPaiment(@RequestBody PatientPaimentDTO patientPaimentDTO) throws URISyntaxException {
        log.debug("REST request to update PatientPaiment : {}", patientPaimentDTO);
        if (patientPaimentDTO.getId() == null) {
            return createPatientPaiment(patientPaimentDTO);
        }
        PatientPaimentDTO result = patientPaimentService.save(patientPaimentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, patientPaimentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /patient-paiments : get all the patientPaiments.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of patientPaiments in body
     */
    @GetMapping("/patient-paiments")
    @Timed
    public List<PatientPaimentDTO> getAllPatientPaiments() {
        log.debug("REST request to get all PatientPaiments");
        return patientPaimentService.findAll();
        }

    /**
     * GET  /patient-paiments/:id : get the "id" patientPaiment.
     *
     * @param id the id of the patientPaimentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the patientPaimentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/patient-paiments/{id}")
    @Timed
    public ResponseEntity<PatientPaimentDTO> getPatientPaiment(@PathVariable Long id) {
        log.debug("REST request to get PatientPaiment : {}", id);
        PatientPaimentDTO patientPaimentDTO = patientPaimentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(patientPaimentDTO));
    }

    /**
     * DELETE  /patient-paiments/:id : delete the "id" patientPaiment.
     *
     * @param id the id of the patientPaimentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/patient-paiments/{id}")
    @Timed
    public ResponseEntity<Void> deletePatientPaiment(@PathVariable Long id) {
        log.debug("REST request to delete PatientPaiment : {}", id);
        patientPaimentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
