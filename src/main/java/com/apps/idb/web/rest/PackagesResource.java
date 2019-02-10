package com.apps.idb.web.rest;
import com.apps.idb.service.PackagesService;
import com.apps.idb.web.rest.errors.BadRequestAlertException;
import com.apps.idb.web.rest.util.HeaderUtil;
import com.apps.idb.web.rest.util.PaginationUtil;
import com.apps.idb.service.dto.PackagesDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Packages.
 */
@RestController
@RequestMapping("/api")
public class PackagesResource {

    private final Logger log = LoggerFactory.getLogger(PackagesResource.class);

    private static final String ENTITY_NAME = "packages";

    private final PackagesService packagesService;

    public PackagesResource(PackagesService packagesService) {
        this.packagesService = packagesService;
    }

    /**
     * POST  /packages : Create a new packages.
     *
     * @param packagesDTO the packagesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new packagesDTO, or with status 400 (Bad Request) if the packages has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/packages")
    public ResponseEntity<PackagesDTO> createPackages(@Valid @RequestBody PackagesDTO packagesDTO) throws URISyntaxException {
        log.debug("REST request to save Packages : {}", packagesDTO);
        if (packagesDTO.getId() != null) {
            throw new BadRequestAlertException("A new packages cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PackagesDTO result = packagesService.save(packagesDTO);
        return ResponseEntity.created(new URI("/api/packages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /packages : Updates an existing packages.
     *
     * @param packagesDTO the packagesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated packagesDTO,
     * or with status 400 (Bad Request) if the packagesDTO is not valid,
     * or with status 500 (Internal Server Error) if the packagesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/packages")
    public ResponseEntity<PackagesDTO> updatePackages(@Valid @RequestBody PackagesDTO packagesDTO) throws URISyntaxException {
        log.debug("REST request to update Packages : {}", packagesDTO);
        if (packagesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PackagesDTO result = packagesService.save(packagesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, packagesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /packages : get all the packages.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of packages in body
     */
    @GetMapping("/packages")
    public ResponseEntity<List<PackagesDTO>> getAllPackages(Pageable pageable) {
        log.debug("REST request to get a page of Packages");
        Page<PackagesDTO> page = packagesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/packages");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /packages/:id : get the "id" packages.
     *
     * @param id the id of the packagesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the packagesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/packages/{id}")
    public ResponseEntity<PackagesDTO> getPackages(@PathVariable Long id) {
        log.debug("REST request to get Packages : {}", id);
        Optional<PackagesDTO> packagesDTO = packagesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(packagesDTO);
    }

    /**
     * DELETE  /packages/:id : delete the "id" packages.
     *
     * @param id the id of the packagesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/packages/{id}")
    public ResponseEntity<Void> deletePackages(@PathVariable Long id) {
        log.debug("REST request to delete Packages : {}", id);
        packagesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
