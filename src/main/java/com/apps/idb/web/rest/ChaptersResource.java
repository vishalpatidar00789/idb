package com.apps.idb.web.rest;
import com.apps.idb.service.ChaptersService;
import com.apps.idb.web.rest.errors.BadRequestAlertException;
import com.apps.idb.web.rest.util.HeaderUtil;
import com.apps.idb.web.rest.util.PaginationUtil;
import com.apps.idb.service.dto.ChaptersDTO;
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
 * REST controller for managing Chapters.
 */
@RestController
@RequestMapping("/api")
public class ChaptersResource {

    private final Logger log = LoggerFactory.getLogger(ChaptersResource.class);

    private static final String ENTITY_NAME = "chapters";

    private final ChaptersService chaptersService;

    public ChaptersResource(ChaptersService chaptersService) {
        this.chaptersService = chaptersService;
    }

    /**
     * POST  /chapters : Create a new chapters.
     *
     * @param chaptersDTO the chaptersDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new chaptersDTO, or with status 400 (Bad Request) if the chapters has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/chapters")
    public ResponseEntity<ChaptersDTO> createChapters(@Valid @RequestBody ChaptersDTO chaptersDTO) throws URISyntaxException {
        log.debug("REST request to save Chapters : {}", chaptersDTO);
        if (chaptersDTO.getId() != null) {
            throw new BadRequestAlertException("A new chapters cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChaptersDTO result = chaptersService.save(chaptersDTO);
        return ResponseEntity.created(new URI("/api/chapters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /chapters : Updates an existing chapters.
     *
     * @param chaptersDTO the chaptersDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated chaptersDTO,
     * or with status 400 (Bad Request) if the chaptersDTO is not valid,
     * or with status 500 (Internal Server Error) if the chaptersDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/chapters")
    public ResponseEntity<ChaptersDTO> updateChapters(@Valid @RequestBody ChaptersDTO chaptersDTO) throws URISyntaxException {
        log.debug("REST request to update Chapters : {}", chaptersDTO);
        if (chaptersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ChaptersDTO result = chaptersService.save(chaptersDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, chaptersDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /chapters : get all the chapters.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of chapters in body
     */
    @GetMapping("/chapters")
    public ResponseEntity<List<ChaptersDTO>> getAllChapters(Pageable pageable) {
        log.debug("REST request to get a page of Chapters");
        Page<ChaptersDTO> page = chaptersService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/chapters");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /chapters/:id : get the "id" chapters.
     *
     * @param id the id of the chaptersDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the chaptersDTO, or with status 404 (Not Found)
     */
    @GetMapping("/chapters/{id}")
    public ResponseEntity<ChaptersDTO> getChapters(@PathVariable Long id) {
        log.debug("REST request to get Chapters : {}", id);
        Optional<ChaptersDTO> chaptersDTO = chaptersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(chaptersDTO);
    }

    /**
     * DELETE  /chapters/:id : delete the "id" chapters.
     *
     * @param id the id of the chaptersDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/chapters/{id}")
    public ResponseEntity<Void> deleteChapters(@PathVariable Long id) {
        log.debug("REST request to delete Chapters : {}", id);
        chaptersService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
