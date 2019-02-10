package com.apps.idb.service.impl;

import com.apps.idb.service.PaymentsService;
import com.apps.idb.domain.Payments;
import com.apps.idb.repository.PaymentsRepository;
import com.apps.idb.service.dto.PaymentsDTO;
import com.apps.idb.service.mapper.PaymentsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Payments.
 */
@Service
@Transactional
public class PaymentsServiceImpl implements PaymentsService {

    private final Logger log = LoggerFactory.getLogger(PaymentsServiceImpl.class);

    private final PaymentsRepository paymentsRepository;

    private final PaymentsMapper paymentsMapper;

    public PaymentsServiceImpl(PaymentsRepository paymentsRepository, PaymentsMapper paymentsMapper) {
        this.paymentsRepository = paymentsRepository;
        this.paymentsMapper = paymentsMapper;
    }

    /**
     * Save a payments.
     *
     * @param paymentsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PaymentsDTO save(PaymentsDTO paymentsDTO) {
        log.debug("Request to save Payments : {}", paymentsDTO);
        Payments payments = paymentsMapper.toEntity(paymentsDTO);
        payments = paymentsRepository.save(payments);
        return paymentsMapper.toDto(payments);
    }

    /**
     * Get all the payments.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PaymentsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Payments");
        return paymentsRepository.findAll(pageable)
            .map(paymentsMapper::toDto);
    }


    /**
     * Get one payments by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PaymentsDTO> findOne(Long id) {
        log.debug("Request to get Payments : {}", id);
        return paymentsRepository.findById(id)
            .map(paymentsMapper::toDto);
    }

    /**
     * Delete the payments by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Payments : {}", id);        paymentsRepository.deleteById(id);
    }
}
