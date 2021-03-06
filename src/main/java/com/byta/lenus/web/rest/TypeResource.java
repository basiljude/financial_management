package com.byta.lenus.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.byta.lenus.service.TypeService;
import com.byta.lenus.web.rest.errors.BadRequestAlertException;
import com.byta.lenus.web.rest.util.HeaderUtil;
import com.byta.lenus.service.dto.TypeDTO;
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
 * REST controller for managing Type.
 */
@RestController
@RequestMapping("/api")
public class TypeResource {

    private final Logger log = LoggerFactory.getLogger(TypeResource.class);

    private static final String ENTITY_NAME = "type";

    private final TypeService typeService;

    public TypeResource(TypeService typeService) {
        this.typeService = typeService;
    }

    /**
     * POST  /types : Create a new type.
     *
     * @param typeDTO the typeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typeDTO, or with status 400 (Bad Request) if the type has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/types")
    @Timed
    public ResponseEntity<TypeDTO> createType(@RequestBody TypeDTO typeDTO) throws URISyntaxException {
        log.debug("REST request to save Type : {}", typeDTO);
        if (typeDTO.getId() != null) {
            throw new BadRequestAlertException("A new type cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeDTO result = typeService.save(typeDTO);
        return ResponseEntity.created(new URI("/api/types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /types : Updates an existing type.
     *
     * @param typeDTO the typeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typeDTO,
     * or with status 400 (Bad Request) if the typeDTO is not valid,
     * or with status 500 (Internal Server Error) if the typeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/types")
    @Timed
    public ResponseEntity<TypeDTO> updateType(@RequestBody TypeDTO typeDTO) throws URISyntaxException {
        log.debug("REST request to update Type : {}", typeDTO);
        if (typeDTO.getId() == null) {
            return createType(typeDTO);
        }
        TypeDTO result = typeService.save(typeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, typeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /types : get all the types.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of types in body
     */
    @GetMapping("/types")
    @Timed
    public List<TypeDTO> getAllTypes() {
        log.debug("REST request to get all Types");
        return typeService.findAll();
        }

    /**
     * GET  /types/:id : get the "id" type.
     *
     * @param id the id of the typeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/types/{id}")
    @Timed
    public ResponseEntity<TypeDTO> getType(@PathVariable Long id) {
        log.debug("REST request to get Type : {}", id);
        TypeDTO typeDTO = typeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(typeDTO));
    }

    /**
     * DELETE  /types/:id : delete the "id" type.
     *
     * @param id the id of the typeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/types/{id}")
    @Timed
    public ResponseEntity<Void> deleteType(@PathVariable Long id) {
        log.debug("REST request to delete Type : {}", id);
        typeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
