package com.apps.idb.repository;

import com.apps.idb.domain.UserProfile;
import com.apps.idb.service.dto.UserProfileDTO;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the UserProfile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
	
	@Query("SELECT up FROM UserProfile up WHERE " +
            "up.gender = :gender AND " +
            "up.age >= (:age-5) AND " +
            "up.age <= (:age+5) AND " +
            "up.city = :city  ORDER BY up.age ASC")
	List<UserProfileDTO> findAllMatchedUserProfiles(@Param("gender") String gender, @Param("age") Integer age, @Param("city") String city);
	
	
}
