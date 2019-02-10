package com.apps.idb.service.mapper;

import com.apps.idb.domain.*;
import com.apps.idb.service.dto.PackagesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Packages and its DTO PackagesDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PackagesMapper extends EntityMapper<PackagesDTO, Packages> {


    @Mapping(target = "payments", ignore = true)
    Packages toEntity(PackagesDTO packagesDTO);

    default Packages fromId(Long id) {
        if (id == null) {
            return null;
        }
        Packages packages = new Packages();
        packages.setId(id);
        return packages;
    }
}
