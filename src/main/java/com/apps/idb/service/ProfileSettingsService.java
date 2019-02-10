package com.apps.idb.service;

import com.apps.idb.service.dto.ProfileSettingsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ProfileSettings.
 */
public interface ProfileSettingsService {

    /**
     * Save a profileSettings.
     *
     * @param profileSettingsDTO the entity to save
     * @return the persisted entity
     */
    ProfileSettingsDTO save(ProfileSettingsDTO profileSettingsDTO);

    /**
     * Get all the profileSettings.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ProfileSettingsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" profileSettings.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ProfileSettingsDTO> findOne(Long id);

    /**
     * Delete the "id" profileSettings.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
