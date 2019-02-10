package com.apps.idb.service;

import com.apps.idb.service.dto.UserProfileDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing UserProfile.
 */
public interface UserProfileService {

    /**
     * Save a userProfile.
     *
     * @param userProfileDTO the entity to save
     * @return the persisted entity
     */
    UserProfileDTO save(UserProfileDTO userProfileDTO);

    /**
     * Get all the userProfiles.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<UserProfileDTO> findAll(Pageable pageable);


    /**
     * Get the "id" userProfile.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<UserProfileDTO> findOne(Long id);

    /**
     * Delete the "id" userProfile.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
