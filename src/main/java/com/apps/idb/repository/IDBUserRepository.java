package com.apps.idb.repository;

import com.apps.idb.domain.IDBUser;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the IDBUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IDBUserRepository extends JpaRepository<IDBUser, Long> {

}
