package com.apps.idb.service.impl;

import com.apps.idb.service.IDBUserService;
import com.apps.idb.domain.IDBUser;
import com.apps.idb.repository.IDBUserRepository;
import com.apps.idb.service.dto.IDBUserDTO;
import com.apps.idb.service.mapper.IDBUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing IDBUser.
 */
@Service
@Transactional
public class IDBUserServiceImpl implements IDBUserService {

    private final Logger log = LoggerFactory.getLogger(IDBUserServiceImpl.class);

    private final IDBUserRepository iDBUserRepository;

    private final IDBUserMapper iDBUserMapper;

    public IDBUserServiceImpl(IDBUserRepository iDBUserRepository, IDBUserMapper iDBUserMapper) {
        this.iDBUserRepository = iDBUserRepository;
        this.iDBUserMapper = iDBUserMapper;
    }

    /**
     * Save a iDBUser.
     *
     * @param iDBUserDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public IDBUserDTO save(IDBUserDTO iDBUserDTO) {
        log.debug("Request to save IDBUser : {}", iDBUserDTO);
        IDBUser iDBUser = iDBUserMapper.toEntity(iDBUserDTO);
        iDBUser = iDBUserRepository.save(iDBUser);
        return iDBUserMapper.toDto(iDBUser);
    }

    /**
     * Get all the iDBUsers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<IDBUserDTO> findAll(Pageable pageable) {
        log.debug("Request to get all IDBUsers");
        return iDBUserRepository.findAll(pageable)
            .map(iDBUserMapper::toDto);
    }



    /**
     *  get all the iDBUsers where UserProfile is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<IDBUserDTO> findAllWhereUserProfileIsNull() {
        log.debug("Request to get all iDBUsers where UserProfile is null");
        return StreamSupport
            .stream(iDBUserRepository.findAll().spliterator(), false)
            .filter(iDBUser -> iDBUser.getUserProfile() == null)
            .map(iDBUserMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     *  get all the iDBUsers where UserAccount is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<IDBUserDTO> findAllWhereUserAccountIsNull() {
        log.debug("Request to get all iDBUsers where UserAccount is null");
        return StreamSupport
            .stream(iDBUserRepository.findAll().spliterator(), false)
            .filter(iDBUser -> iDBUser.getUserAccount() == null)
            .map(iDBUserMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one iDBUser by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<IDBUserDTO> findOne(Long id) {
        log.debug("Request to get IDBUser : {}", id);
        return iDBUserRepository.findById(id)
            .map(iDBUserMapper::toDto);
    }

    /**
     * Delete the iDBUser by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete IDBUser : {}", id);        iDBUserRepository.deleteById(id);
    }
}
