package com.apps.idb.service.impl;

import com.apps.idb.service.PhotosService;
import com.apps.idb.domain.Photos;
import com.apps.idb.repository.PhotosRepository;
import com.apps.idb.service.dto.PhotosDTO;
import com.apps.idb.service.mapper.PhotosMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Photos.
 */
@Service
@Transactional
public class PhotosServiceImpl implements PhotosService {

    private final Logger log = LoggerFactory.getLogger(PhotosServiceImpl.class);

    private final PhotosRepository photosRepository;

    private final PhotosMapper photosMapper;

    public PhotosServiceImpl(PhotosRepository photosRepository, PhotosMapper photosMapper) {
        this.photosRepository = photosRepository;
        this.photosMapper = photosMapper;
    }

    /**
     * Save a photos.
     *
     * @param photosDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PhotosDTO save(PhotosDTO photosDTO) {
        log.debug("Request to save Photos : {}", photosDTO);
        Photos photos = photosMapper.toEntity(photosDTO);
        photos = photosRepository.save(photos);
        return photosMapper.toDto(photos);
    }

    /**
     * Get all the photos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PhotosDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Photos");
        return photosRepository.findAll(pageable)
            .map(photosMapper::toDto);
    }


    /**
     * Get one photos by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PhotosDTO> findOne(Long id) {
        log.debug("Request to get Photos : {}", id);
        return photosRepository.findById(id)
            .map(photosMapper::toDto);
    }

    /**
     * Delete the photos by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Photos : {}", id);        photosRepository.deleteById(id);
    }
}
