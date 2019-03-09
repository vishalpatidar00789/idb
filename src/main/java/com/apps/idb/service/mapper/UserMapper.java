package com.apps.idb.service.mapper;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.apps.idb.domain.Authority;
import com.apps.idb.domain.IDBUser;
import com.apps.idb.service.dto.IDBUserDTO;

/**
 * Mapper for the entity User and its DTO called UserDTO.
 *
 * Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct
 * support is still in beta, and requires a manual step with an IDE.
 */
@Service
public class UserMapper {

    public List<IDBUserDTO> usersToUserDTOs(List<IDBUser> users) {
        return users.stream()
            .filter(Objects::nonNull)
            .map(this::userToUserDTO)
            .collect(Collectors.toList());
    }

    public IDBUserDTO userToUserDTO(IDBUser user) {
        return new IDBUserDTO(user);
    }

    public List<IDBUser> userDTOsToUsers(List<IDBUserDTO> userDTOs) {
        return userDTOs.stream()
            .filter(Objects::nonNull)
            .map(this::userDTOToUser)
            .collect(Collectors.toList());
    }

    public IDBUser userDTOToUser(IDBUserDTO userDTO) {
        if (userDTO == null) {
            return null;
        } else {
        	IDBUser user = new IDBUser();
            user.setId(userDTO.getId());
            user.setEmail(userDTO.getEmail());
            user.setImageUrl(userDTO.getImageUrl());
            user.setActivated(userDTO.isActivated());
            user.setLangKey(userDTO.getLangKey());
            Set<Authority> authorities = this.authoritiesFromStrings(userDTO.getAuthorities());
            user.setAuthorities(authorities);
            return user;
        }
    }


    private Set<Authority> authoritiesFromStrings(Set<String> authoritiesAsString) {
        Set<Authority> authorities = new HashSet<>();

        if(authoritiesAsString != null){
            authorities = authoritiesAsString.stream().map(string -> {
                Authority auth = new Authority();
                auth.setName(string);
                return auth;
            }).collect(Collectors.toSet());
        }

        return authorities;
    }

    public IDBUser userFromId(Long id) {
        if (id == null) {
            return null;
        }
        IDBUser user = new IDBUser();
        user.setId(id);
        return user;
    }
}
