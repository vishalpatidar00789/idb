package com.apps.idb.web.rest;
import com.apps.idb.service.ProfileSettingsService;
import com.apps.idb.web.rest.errors.BadRequestAlertException;
import com.apps.idb.web.rest.util.HeaderUtil;
import com.apps.idb.web.rest.util.PaginationUtil;
import com.apps.idb.service.dto.ProfileSettingsDTO;
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
 * REST controller for managing ProfileSettings.
 */
@RestController
@RequestMapping("/api")
public class ProfileSettingsResource {

    private final Logger log = LoggerFactory.getLogger(ProfileSettingsResource.class);

    private static final String ENTITY_NAME = "profileSettings";

    private final ProfileSettingsService profileSettingsService;

    public ProfileSettingsResource(ProfileSettingsService profileSettingsService) {
        this.profileSettingsService = profileSettingsService;
    }

    /**
     * POST  /profile-settings : Create a new profileSettings.
     *
     * @param profileSettingsDTO the profileSettingsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new profileSettingsDTO, or with status 400 (Bad Request) if the profileSettings has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/profile-settings")
    public ResponseEntity<ProfileSettingsDTO> createProfileSettings(@Valid @RequestBody ProfileSettingsDTO profileSettingsDTO) throws URISyntaxException {
        log.debug("REST request to save ProfileSettings : {}", profileSettingsDTO);
        if (profileSettingsDTO.getId() != null) {
            throw new BadRequestAlertException("A new profileSettings cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProfileSettingsDTO result = profileSettingsService.save(profileSettingsDTO);
        return ResponseEntity.created(new URI("/api/profile-settings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /profile-settings : Updates an existing profileSettings.
     *
     * @param profileSettingsDTO the profileSettingsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated profileSettingsDTO,
     * or with status 400 (Bad Request) if the profileSettingsDTO is not valid,
     * or with status 500 (Internal Server Error) if the profileSettingsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/profile-settings")
    public ResponseEntity<ProfileSettingsDTO> updateProfileSettings(@Valid @RequestBody ProfileSettingsDTO profileSettingsDTO) throws URISyntaxException {
        log.debug("REST request to update ProfileSettings : {}", profileSettingsDTO);
        if (profileSettingsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProfileSettingsDTO result = profileSettingsService.save(profileSettingsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, profileSettingsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /profile-settings : get all the profileSettings.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of profileSettings in body
     */
    @GetMapping("/profile-settings")
    public ResponseEntity<List<ProfileSettingsDTO>> getAllProfileSettings(Pageable pageable) {
        log.debug("REST request to get a page of ProfileSettings");
        Page<ProfileSettingsDTO> page = profileSettingsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/profile-settings");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /profile-settings/:id : get the "id" profileSettings.
     *
     * @param id the id of the profileSettingsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the profileSettingsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/profile-settings/{id}")
    public ResponseEntity<ProfileSettingsDTO> getProfileSettings(@PathVariable Long id) {
        log.debug("REST request to get ProfileSettings : {}", id);
        Optional<ProfileSettingsDTO> profileSettingsDTO = profileSettingsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(profileSettingsDTO);
    }

    /**
     * DELETE  /profile-settings/:id : delete the "id" profileSettings.
     *
     * @param id the id of the profileSettingsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/profile-settings/{id}")
    public ResponseEntity<Void> deleteProfileSettings(@PathVariable Long id) {
        log.debug("REST request to delete ProfileSettings : {}", id);
        profileSettingsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
