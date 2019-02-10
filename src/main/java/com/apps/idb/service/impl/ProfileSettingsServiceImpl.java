package com.apps.idb.service.impl;

import com.apps.idb.service.ProfileSettingsService;
import com.apps.idb.domain.ProfileSettings;
import com.apps.idb.repository.ProfileSettingsRepository;
import com.apps.idb.service.dto.ProfileSettingsDTO;
import com.apps.idb.service.mapper.ProfileSettingsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing ProfileSettings.
 */
@Service
@Transactional
public class ProfileSettingsServiceImpl implements ProfileSettingsService {

    private final Logger log = LoggerFactory.getLogger(ProfileSettingsServiceImpl.class);

    private final ProfileSettingsRepository profileSettingsRepository;

    private final ProfileSettingsMapper profileSettingsMapper;

    public ProfileSettingsServiceImpl(ProfileSettingsRepository profileSettingsRepository, ProfileSettingsMapper profileSettingsMapper) {
        this.profileSettingsRepository = profileSettingsRepository;
        this.profileSettingsMapper = profileSettingsMapper;
    }

    /**
     * Save a profileSettings.
     *
     * @param profileSettingsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProfileSettingsDTO save(ProfileSettingsDTO profileSettingsDTO) {
        log.debug("Request to save ProfileSettings : {}", profileSettingsDTO);
        ProfileSettings profileSettings = profileSettingsMapper.toEntity(profileSettingsDTO);
        profileSettings = profileSettingsRepository.save(profileSettings);
        return profileSettingsMapper.toDto(profileSettings);
    }

    /**
     * Get all the profileSettings.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProfileSettingsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProfileSettings");
        return profileSettingsRepository.findAll(pageable)
            .map(profileSettingsMapper::toDto);
    }


    /**
     * Get one profileSettings by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProfileSettingsDTO> findOne(Long id) {
        log.debug("Request to get ProfileSettings : {}", id);
        return profileSettingsRepository.findById(id)
            .map(profileSettingsMapper::toDto);
    }

    /**
     * Delete the profileSettings by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProfileSettings : {}", id);        profileSettingsRepository.deleteById(id);
    }
}
