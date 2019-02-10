package com.apps.idb.service.mapper;

import com.apps.idb.domain.*;
import com.apps.idb.service.dto.UserAccountDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UserAccount and its DTO UserAccountDTO.
 */
@Mapper(componentModel = "spring", uses = {IDBUserMapper.class, PackagesMapper.class})
public interface UserAccountMapper extends EntityMapper<UserAccountDTO, UserAccount> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.email", target = "userEmail")
    @Mapping(source = "currentPackage.id", target = "currentPackageId")
    UserAccountDTO toDto(UserAccount userAccount);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "currentPackageId", target = "currentPackage")
    @Mapping(target = "payments", ignore = true)
    UserAccount toEntity(UserAccountDTO userAccountDTO);

    default UserAccount fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserAccount userAccount = new UserAccount();
        userAccount.setId(id);
        return userAccount;
    }
}
