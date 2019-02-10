package com.apps.idb.service.mapper;

import com.apps.idb.domain.*;
import com.apps.idb.service.dto.ProfileSettingsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ProfileSettings and its DTO ProfileSettingsDTO.
 */
@Mapper(componentModel = "spring", uses = {UserProfileMapper.class})
public interface ProfileSettingsMapper extends EntityMapper<ProfileSettingsDTO, ProfileSettings> {

    @Mapping(source = "userProfile.id", target = "userProfileId")
    ProfileSettingsDTO toDto(ProfileSettings profileSettings);

    @Mapping(source = "userProfileId", target = "userProfile")
    ProfileSettings toEntity(ProfileSettingsDTO profileSettingsDTO);

    default ProfileSettings fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProfileSettings profileSettings = new ProfileSettings();
        profileSettings.setId(id);
        return profileSettings;
    }
}
