package com.apps.idb.service.mapper;

import com.apps.idb.domain.*;
import com.apps.idb.service.dto.PhotosDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Photos and its DTO PhotosDTO.
 */
@Mapper(componentModel = "spring", uses = {UserProfileMapper.class})
public interface PhotosMapper extends EntityMapper<PhotosDTO, Photos> {

    @Mapping(source = "userProfile.id", target = "userProfileId")
    PhotosDTO toDto(Photos photos);

    @Mapping(source = "userProfileId", target = "userProfile")
    Photos toEntity(PhotosDTO photosDTO);

    default Photos fromId(Long id) {
        if (id == null) {
            return null;
        }
        Photos photos = new Photos();
        photos.setId(id);
        return photos;
    }
}
