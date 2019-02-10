package com.apps.idb.service;

import com.apps.idb.service.dto.UserAccountDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing UserAccount.
 */
public interface UserAccountService {

    /**
     * Save a userAccount.
     *
     * @param userAccountDTO the entity to save
     * @return the persisted entity
     */
    UserAccountDTO save(UserAccountDTO userAccountDTO);

    /**
     * Get all the userAccounts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<UserAccountDTO> findAll(Pageable pageable);


    /**
     * Get the "id" userAccount.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<UserAccountDTO> findOne(Long id);

    /**
     * Delete the "id" userAccount.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
