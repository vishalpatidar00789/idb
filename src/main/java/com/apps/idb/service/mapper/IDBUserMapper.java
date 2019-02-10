package com.apps.idb.service.mapper;

import com.apps.idb.domain.*;
import com.apps.idb.service.dto.IDBUserDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity IDBUser and its DTO IDBUserDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface IDBUserMapper extends EntityMapper<IDBUserDTO, IDBUser> {


    @Mapping(target = "initiatedChapters", ignore = true)
    @Mapping(target = "partnerChapters", ignore = true)
    @Mapping(target = "userProfile", ignore = true)
    @Mapping(target = "userAccount", ignore = true)
    IDBUser toEntity(IDBUserDTO iDBUserDTO);

    default IDBUser fromId(Long id) {
        if (id == null) {
            return null;
        }
        IDBUser iDBUser = new IDBUser();
        iDBUser.setId(id);
        return iDBUser;
    }
}
