package com.apps.idb.web.rest;
import com.apps.idb.service.IDBUserService;
import com.apps.idb.web.rest.errors.BadRequestAlertException;
import com.apps.idb.web.rest.util.HeaderUtil;
import com.apps.idb.web.rest.util.PaginationUtil;
import com.apps.idb.service.dto.IDBUserDTO;
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
import java.util.stream.StreamSupport;

/**
 * REST controller for managing IDBUser.
 */
@RestController
@RequestMapping("/api")
public class IDBUserResource {

    private final Logger log = LoggerFactory.getLogger(IDBUserResource.class);

    private static final String ENTITY_NAME = "iDBUser";

    private final IDBUserService iDBUserService;

    public IDBUserResource(IDBUserService iDBUserService) {
        this.iDBUserService = iDBUserService;
    }

    /**
     * POST  /idb-users : Create a new iDBUser.
     *
     * @param iDBUserDTO the iDBUserDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new iDBUserDTO, or with status 400 (Bad Request) if the iDBUser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/idb-users")
    public ResponseEntity<IDBUserDTO> createIDBUser(@Valid @RequestBody IDBUserDTO iDBUserDTO) throws URISyntaxException {
        log.debug("REST request to save IDBUser : {}", iDBUserDTO);
        if (iDBUserDTO.getId() != null) {
            throw new BadRequestAlertException("A new iDBUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IDBUserDTO result = iDBUserService.save(iDBUserDTO);
        return ResponseEntity.created(new URI("/api/idb-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /idb-users : Updates an existing iDBUser.
     *
     * @param iDBUserDTO the iDBUserDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated iDBUserDTO,
     * or with status 400 (Bad Request) if the iDBUserDTO is not valid,
     * or with status 500 (Internal Server Error) if the iDBUserDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/idb-users")
    public ResponseEntity<IDBUserDTO> updateIDBUser(@Valid @RequestBody IDBUserDTO iDBUserDTO) throws URISyntaxException {
        log.debug("REST request to update IDBUser : {}", iDBUserDTO);
        if (iDBUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        IDBUserDTO result = iDBUserService.save(iDBUserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, iDBUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /idb-users : get all the iDBUsers.
     *
     * @param pageable the pagination information
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of iDBUsers in body
     */
    @GetMapping("/idb-users")
    public ResponseEntity<List<IDBUserDTO>> getAllIDBUsers(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("userprofile-is-null".equals(filter)) {
            log.debug("REST request to get all IDBUsers where userProfile is null");
            return new ResponseEntity<>(iDBUserService.findAllWhereUserProfileIsNull(),
                    HttpStatus.OK);
        }
        if ("useraccount-is-null".equals(filter)) {
            log.debug("REST request to get all IDBUsers where userAccount is null");
            return new ResponseEntity<>(iDBUserService.findAllWhereUserAccountIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of IDBUsers");
        Page<IDBUserDTO> page = iDBUserService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/idb-users");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /idb-users/:id : get the "id" iDBUser.
     *
     * @param id the id of the iDBUserDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the iDBUserDTO, or with status 404 (Not Found)
     */
    @GetMapping("/idb-users/{id}")
    public ResponseEntity<IDBUserDTO> getIDBUser(@PathVariable Long id) {
        log.debug("REST request to get IDBUser : {}", id);
        Optional<IDBUserDTO> iDBUserDTO = iDBUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(iDBUserDTO);
    }

    /**
     * DELETE  /idb-users/:id : delete the "id" iDBUser.
     *
     * @param id the id of the iDBUserDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/idb-users/{id}")
    public ResponseEntity<Void> deleteIDBUser(@PathVariable Long id) {
        log.debug("REST request to delete IDBUser : {}", id);
        iDBUserService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
