package com.apps.idb.service.mapper;

import com.apps.idb.domain.*;
import com.apps.idb.service.dto.PaymentsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Payments and its DTO PaymentsDTO.
 */
@Mapper(componentModel = "spring", uses = {UserAccountMapper.class, PackagesMapper.class})
public interface PaymentsMapper extends EntityMapper<PaymentsDTO, Payments> {

    @Mapping(source = "userAccount.id", target = "userAccountId")
    @Mapping(source = "appliedPackage.id", target = "appliedPackageId")
    PaymentsDTO toDto(Payments payments);

    @Mapping(source = "userAccountId", target = "userAccount")
    @Mapping(source = "appliedPackageId", target = "appliedPackage")
    Payments toEntity(PaymentsDTO paymentsDTO);

    default Payments fromId(Long id) {
        if (id == null) {
            return null;
        }
        Payments payments = new Payments();
        payments.setId(id);
        return payments;
    }
}
