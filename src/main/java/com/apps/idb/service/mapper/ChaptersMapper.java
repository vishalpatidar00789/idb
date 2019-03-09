package com.apps.idb.service.mapper;

import com.apps.idb.domain.*;
import com.apps.idb.service.dto.ChaptersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Chapters and its DTO ChaptersDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface ChaptersMapper extends EntityMapper<ChaptersDTO, Chapters> {

    @Mapping(source = "initiator.id", target = "initiatorId")
    @Mapping(source = "initiator.email", target = "initiatorEmail")
    @Mapping(source = "partner.id", target = "partnerId")
    @Mapping(source = "partner.email", target = "partnerEmail")
    ChaptersDTO toDto(Chapters chapters);

    @Mapping(source = "initiatorId", target = "initiator")
    @Mapping(source = "partnerId", target = "partner")
    Chapters toEntity(ChaptersDTO chaptersDTO);

    default Chapters fromId(Long id) {
        if (id == null) {
            return null;
        }
        Chapters chapters = new Chapters();
        chapters.setId(id);
        return chapters;
    }
}
