package com.apps.idb.service;

import com.apps.idb.service.dto.IDBUserDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing IDBUser.
 */
public interface IDBUserService {

    /**
     * Save a iDBUser.
     *
     * @param iDBUserDTO the entity to save
     * @return the persisted entity
     */
    IDBUserDTO save(IDBUserDTO iDBUserDTO);

    /**
     * Get all the iDBUsers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<IDBUserDTO> findAll(Pageable pageable);
    /**
     * Get all the IDBUserDTO where UserProfile is null.
     *
     * @return the list of entities
     */
    List<IDBUserDTO> findAllWhereUserProfileIsNull();
    /**
     * Get all the IDBUserDTO where UserAccount is null.
     *
     * @return the list of entities
     */
    List<IDBUserDTO> findAllWhereUserAccountIsNull();


    /**
     * Get the "id" iDBUser.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<IDBUserDTO> findOne(Long id);

    /**
     * Delete the "id" iDBUser.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
