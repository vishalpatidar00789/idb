package com.apps.idb.service;

import com.apps.idb.service.dto.PhotosDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Photos.
 */
public interface PhotosService {

    /**
     * Save a photos.
     *
     * @param photosDTO the entity to save
     * @return the persisted entity
     */
    PhotosDTO save(PhotosDTO photosDTO);

    /**
     * Get all the photos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PhotosDTO> findAll(Pageable pageable);


    /**
     * Get the "id" photos.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PhotosDTO> findOne(Long id);

    /**
     * Delete the "id" photos.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
