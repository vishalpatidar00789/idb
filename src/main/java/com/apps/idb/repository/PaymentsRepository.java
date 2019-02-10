package com.apps.idb.repository;

import com.apps.idb.domain.Payments;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Payments entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentsRepository extends JpaRepository<Payments, Long> {

}
