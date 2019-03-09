package com.apps.idb.repository;

import com.apps.idb.domain.IDBUser;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the IDBUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IDBUserRepository extends JpaRepository<IDBUser, Long> {

    String USERS_BY_LOGIN_CACHE = "usersByLogin";

    String USERS_BY_EMAIL_CACHE = "usersByEmail";

    Optional<IDBUser> findOneByActivationKey(String activationKey);

    List<IDBUser> findAllByActivatedIsFalseAndCreatedDateBefore(Instant dateTime);

    Optional<IDBUser> findOneByResetKey(String resetKey);

    Optional<IDBUser> findOneByEmailIgnoreCase(String email);

   // Optional<IDBUser> findOneByLogin(String login);

    @EntityGraph(attributePaths = "authorities")
    Optional<IDBUser> findOneWithAuthoritiesById(Long id);

	/*
	 * @EntityGraph(attributePaths = "authorities")
	 * 
	 * @Cacheable(cacheNames = USERS_BY_LOGIN_CACHE) Optional<IDBUser>
	 * findOneWithAuthoritiesByLogin(String email);
	 */
    @EntityGraph(attributePaths = "authorities")
    @Cacheable(cacheNames = USERS_BY_EMAIL_CACHE)
    Optional<IDBUser> findOneWithAuthoritiesByEmail(String email);

    Page<IDBUser> findAllByEmailNot(Pageable pageable, String email);
}
