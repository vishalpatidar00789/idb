package com.apps.idb.service;

import com.apps.idb.service.dto.ChaptersDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Chapters.
 */
public interface ChaptersService {

    /**
     * Save a chapters.
     *
     * @param chaptersDTO the entity to save
     * @return the persisted entity
     */
    ChaptersDTO save(ChaptersDTO chaptersDTO);

    /**
     * Get all the chapters.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ChaptersDTO> findAll(Pageable pageable);


    /**
     * Get the "id" chapters.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ChaptersDTO> findOne(Long id);

    /**
     * Delete the "id" chapters.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
