package com.apps.idb.repository;

import com.apps.idb.domain.ProfileSettings;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProfileSettings entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProfileSettingsRepository extends JpaRepository<ProfileSettings, Long> {

}
