package com.apps.idb.service.mapper;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.apps.idb.IdbApp;
import com.apps.idb.domain.IDBUser;
import com.apps.idb.service.dto.IDBUserDTO;

/**
 * Test class for the UserMapper.
 *
 * @see UserMapper
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IdbApp.class)
public class UserMapperTest {

    private static final String DEFAULT_LOGIN = "johndoe";

    @Autowired
    private UserMapper userMapper;

    private IDBUser user;
    private IDBUserDTO userDto;

    private static final Long DEFAULT_ID = 1L;

    @Before
    public void init() {
        user = new IDBUser();
        user.setPassword(RandomStringUtils.random(60));
        user.setActivated(true);
        user.setEmail("johndoe@localhost");
        user.setImageUrl("image_url");
        user.setLangKey("en");

        userDto = new IDBUserDTO(user);
    }

    @Test
    public void usersToUserDTOsShouldMapOnlyNonNullUsers(){
        List<IDBUser> users = new ArrayList<>();
        users.add(user);
        users.add(null);

        List<IDBUserDTO> userDTOS = userMapper.usersToUserDTOs(users);

        assertThat(userDTOS).isNotEmpty();
        assertThat(userDTOS).size().isEqualTo(1);
    }

    @Test
    public void userDTOsToUsersShouldMapOnlyNonNullUsers(){
        List<IDBUserDTO> usersDto = new ArrayList<>();
        usersDto.add(userDto);
        usersDto.add(null);

        List<IDBUser> users = userMapper.userDTOsToUsers(usersDto);

        assertThat(users).isNotEmpty();
        assertThat(users).size().isEqualTo(1);
    }

    @Test
    public void userDTOsToUsersWithAuthoritiesStringShouldMapToUsersWithAuthoritiesDomain(){
        Set<String> authoritiesAsString = new HashSet<>();
        authoritiesAsString.add("ADMIN");
        userDto.setAuthorities(authoritiesAsString);

        List<IDBUserDTO> usersDto = new ArrayList<>();
        usersDto.add(userDto);

        List<IDBUser> users = userMapper.userDTOsToUsers(usersDto);

        assertThat(users).isNotEmpty();
        assertThat(users).size().isEqualTo(1);
        assertThat(users.get(0).getAuthorities()).isNotNull();
        assertThat(users.get(0).getAuthorities()).isNotEmpty();
        assertThat(users.get(0).getAuthorities().iterator().next().getName()).isEqualTo("ADMIN");
    }

    @Test
    public void userDTOsToUsersMapWithNullAuthoritiesStringShouldReturnUserWithEmptyAuthorities(){
        userDto.setAuthorities(null);

        List<IDBUserDTO> usersDto = new ArrayList<>();
        usersDto.add(userDto);

        List<IDBUser> users = userMapper.userDTOsToUsers(usersDto);

        assertThat(users).isNotEmpty();
        assertThat(users).size().isEqualTo(1);
        assertThat(users.get(0).getAuthorities()).isNotNull();
        assertThat(users.get(0).getAuthorities()).isEmpty();
    }

    @Test
    public void userDTOToUserMapWithAuthoritiesStringShouldReturnUserWithAuthorities(){
        Set<String> authoritiesAsString = new HashSet<>();
        authoritiesAsString.add("ADMIN");
        userDto.setAuthorities(authoritiesAsString);

        userDto.setAuthorities(authoritiesAsString);

        IDBUser user = userMapper.userDTOToUser(userDto);

        assertThat(user).isNotNull();
        assertThat(user.getAuthorities()).isNotNull();
        assertThat(user.getAuthorities()).isNotEmpty();
        assertThat(user.getAuthorities().iterator().next().getName()).isEqualTo("ADMIN");
    }

    @Test
    public void userDTOToUserMapWithNullAuthoritiesStringShouldReturnUserWithEmptyAuthorities(){
        userDto.setAuthorities(null);

        IDBUser user = userMapper.userDTOToUser(userDto);

        assertThat(user).isNotNull();
        assertThat(user.getAuthorities()).isNotNull();
        assertThat(user.getAuthorities()).isEmpty();
    }

    @Test
    public void userDTOToUserMapWithNullUserShouldReturnNull(){
        assertThat(userMapper.userDTOToUser(null)).isNull();
    }

    @Test
    public void testUserFromId() {
        assertThat(userMapper.userFromId(DEFAULT_ID).getId()).isEqualTo(DEFAULT_ID);
        assertThat(userMapper.userFromId(null)).isNull();
    }
}
