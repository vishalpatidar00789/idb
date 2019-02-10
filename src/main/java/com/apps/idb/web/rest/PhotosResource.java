package com.apps.idb.web.rest;
import com.apps.idb.service.PhotosService;
import com.apps.idb.web.rest.errors.BadRequestAlertException;
import com.apps.idb.web.rest.util.HeaderUtil;
import com.apps.idb.web.rest.util.PaginationUtil;
import com.apps.idb.service.dto.PhotosDTO;
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
 * REST controller for managing Photos.
 */
@RestController
@RequestMapping("/api")
public class PhotosResource {

    private final Logger log = LoggerFactory.getLogger(PhotosResource.class);

    private static final String ENTITY_NAME = "photos";

    private final PhotosService photosService;

    public PhotosResource(PhotosService photosService) {
        this.photosService = photosService;
    }

    /**
     * POST  /photos : Create a new photos.
     *
     * @param photosDTO the photosDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new photosDTO, or with status 400 (Bad Request) if the photos has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/photos")
    public ResponseEntity<PhotosDTO> createPhotos(@Valid @RequestBody PhotosDTO photosDTO) throws URISyntaxException {
        log.debug("REST request to save Photos : {}", photosDTO);
        if (photosDTO.getId() != null) {
            throw new BadRequestAlertException("A new photos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PhotosDTO result = photosService.save(photosDTO);
        return ResponseEntity.created(new URI("/api/photos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /photos : Updates an existing photos.
     *
     * @param photosDTO the photosDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated photosDTO,
     * or with status 400 (Bad Request) if the photosDTO is not valid,
     * or with status 500 (Internal Server Error) if the photosDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/photos")
    public ResponseEntity<PhotosDTO> updatePhotos(@Valid @RequestBody PhotosDTO photosDTO) throws URISyntaxException {
        log.debug("REST request to update Photos : {}", photosDTO);
        if (photosDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PhotosDTO result = photosService.save(photosDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, photosDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /photos : get all the photos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of photos in body
     */
    @GetMapping("/photos")
    public ResponseEntity<List<PhotosDTO>> getAllPhotos(Pageable pageable) {
        log.debug("REST request to get a page of Photos");
        Page<PhotosDTO> page = photosService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/photos");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /photos/:id : get the "id" photos.
     *
     * @param id the id of the photosDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the photosDTO, or with status 404 (Not Found)
     */
    @GetMapping("/photos/{id}")
    public ResponseEntity<PhotosDTO> getPhotos(@PathVariable Long id) {
        log.debug("REST request to get Photos : {}", id);
        Optional<PhotosDTO> photosDTO = photosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(photosDTO);
    }

    /**
     * DELETE  /photos/:id : delete the "id" photos.
     *
     * @param id the id of the photosDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/photos/{id}")
    public ResponseEntity<Void> deletePhotos(@PathVariable Long id) {
        log.debug("REST request to delete Photos : {}", id);
        photosService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
