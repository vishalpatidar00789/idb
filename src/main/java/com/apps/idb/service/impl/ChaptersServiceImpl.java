package com.apps.idb.service.impl;

import com.apps.idb.service.ChaptersService;
import com.apps.idb.domain.Chapters;
import com.apps.idb.repository.ChaptersRepository;
import com.apps.idb.service.dto.ChaptersDTO;
import com.apps.idb.service.mapper.ChaptersMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Chapters.
 */
@Service
@Transactional
public class ChaptersServiceImpl implements ChaptersService {

    private final Logger log = LoggerFactory.getLogger(ChaptersServiceImpl.class);

    private final ChaptersRepository chaptersRepository;

    private final ChaptersMapper chaptersMapper;

    public ChaptersServiceImpl(ChaptersRepository chaptersRepository, ChaptersMapper chaptersMapper) {
        this.chaptersRepository = chaptersRepository;
        this.chaptersMapper = chaptersMapper;
    }

    /**
     * Save a chapters.
     *
     * @param chaptersDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ChaptersDTO save(ChaptersDTO chaptersDTO) {
        log.debug("Request to save Chapters : {}", chaptersDTO);
        Chapters chapters = chaptersMapper.toEntity(chaptersDTO);
        chapters = chaptersRepository.save(chapters);
        return chaptersMapper.toDto(chapters);
    }

    /**
     * Get all the chapters.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ChaptersDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Chapters");
        return chaptersRepository.findAll(pageable)
            .map(chaptersMapper::toDto);
    }


    /**
     * Get one chapters by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ChaptersDTO> findOne(Long id) {
        log.debug("Request to get Chapters : {}", id);
        return chaptersRepository.findById(id)
            .map(chaptersMapper::toDto);
    }

    /**
     * Delete the chapters by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Chapters : {}", id);        chaptersRepository.deleteById(id);
    }
}
