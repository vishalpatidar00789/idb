package com.apps.idb.web.rest;
import com.apps.idb.service.PaymentsService;
import com.apps.idb.web.rest.errors.BadRequestAlertException;
import com.apps.idb.web.rest.util.HeaderUtil;
import com.apps.idb.web.rest.util.PaginationUtil;
import com.apps.idb.service.dto.PaymentsDTO;
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
 * REST controller for managing Payments.
 */
@RestController
@RequestMapping("/api")
public class PaymentsResource {

    private final Logger log = LoggerFactory.getLogger(PaymentsResource.class);

    private static final String ENTITY_NAME = "payments";

    private final PaymentsService paymentsService;

    public PaymentsResource(PaymentsService paymentsService) {
        this.paymentsService = paymentsService;
    }

    /**
     * POST  /payments : Create a new payments.
     *
     * @param paymentsDTO the paymentsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new paymentsDTO, or with status 400 (Bad Request) if the payments has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/payments")
    public ResponseEntity<PaymentsDTO> createPayments(@Valid @RequestBody PaymentsDTO paymentsDTO) throws URISyntaxException {
        log.debug("REST request to save Payments : {}", paymentsDTO);
        if (paymentsDTO.getId() != null) {
            throw new BadRequestAlertException("A new payments cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaymentsDTO result = paymentsService.save(paymentsDTO);
        return ResponseEntity.created(new URI("/api/payments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /payments : Updates an existing payments.
     *
     * @param paymentsDTO the paymentsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated paymentsDTO,
     * or with status 400 (Bad Request) if the paymentsDTO is not valid,
     * or with status 500 (Internal Server Error) if the paymentsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/payments")
    public ResponseEntity<PaymentsDTO> updatePayments(@Valid @RequestBody PaymentsDTO paymentsDTO) throws URISyntaxException {
        log.debug("REST request to update Payments : {}", paymentsDTO);
        if (paymentsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PaymentsDTO result = paymentsService.save(paymentsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, paymentsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /payments : get all the payments.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of payments in body
     */
    @GetMapping("/payments")
    public ResponseEntity<List<PaymentsDTO>> getAllPayments(Pageable pageable) {
        log.debug("REST request to get a page of Payments");
        Page<PaymentsDTO> page = paymentsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/payments");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /payments/:id : get the "id" payments.
     *
     * @param id the id of the paymentsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the paymentsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/payments/{id}")
    public ResponseEntity<PaymentsDTO> getPayments(@PathVariable Long id) {
        log.debug("REST request to get Payments : {}", id);
        Optional<PaymentsDTO> paymentsDTO = paymentsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paymentsDTO);
    }

    /**
     * DELETE  /payments/:id : delete the "id" payments.
     *
     * @param id the id of the paymentsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/payments/{id}")
    public ResponseEntity<Void> deletePayments(@PathVariable Long id) {
        log.debug("REST request to delete Payments : {}", id);
        paymentsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
