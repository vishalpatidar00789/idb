package com.apps.idb.service.mapper;

import com.apps.idb.domain.*;
import com.apps.idb.service.dto.UserProfileDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UserProfile and its DTO UserProfileDTO.
 */
@Mapper(componentModel = "spring", uses = {IDBUserMapper.class})
public interface UserProfileMapper extends EntityMapper<UserProfileDTO, UserProfile> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.email", target = "userEmail")
    UserProfileDTO toDto(UserProfile userProfile);

    @Mapping(source = "userId", target = "user")
    @Mapping(target = "pics", ignore = true)
    @Mapping(target = "profileSettings", ignore = true)
    UserProfile toEntity(UserProfileDTO userProfileDTO);

    default UserProfile fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserProfile userProfile = new UserProfile();
        userProfile.setId(id);
        return userProfile;
    }
}
