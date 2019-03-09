package com.apps.idb.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.apps.idb.IdbApp;
import com.apps.idb.config.Constants;
import com.apps.idb.domain.Authority;
import com.apps.idb.domain.IDBUser;
import com.apps.idb.repository.AuthorityRepository;
import com.apps.idb.repository.IDBUserRepository;
import com.apps.idb.security.AuthoritiesConstants;
import com.apps.idb.service.IDBUserService;
import com.apps.idb.service.MailService;
import com.apps.idb.service.dto.IDBUserDTO;
import com.apps.idb.service.dto.PasswordChangeDTO;
import com.apps.idb.web.rest.errors.ExceptionTranslator;
import com.apps.idb.web.rest.vm.KeyAndPasswordVM;
import com.apps.idb.web.rest.vm.ManagedUserVM;

/**
 * Test class for the AccountResource REST controller.
 *
 * @see AccountResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IdbApp.class)
public class AccountResourceIntTest {

    @Autowired
    private IDBUserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private IDBUserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private HttpMessageConverter<?>[] httpMessageConverters;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Mock
    private IDBUserService mockUserService;

    @Mock
    private MailService mockMailService;

    private MockMvc restMvc;

    private MockMvc restUserMockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        doNothing().when(mockMailService).sendActivationEmail(any());
        AccountResource accountResource =
            new AccountResource(userRepository, userService, mockMailService);

        AccountResource accountUserMockResource =
            new AccountResource(userRepository, mockUserService, mockMailService);
        this.restMvc = MockMvcBuilders.standaloneSetup(accountResource)
            .setMessageConverters(httpMessageConverters)
            .setControllerAdvice(exceptionTranslator)
            .build();
        this.restUserMockMvc = MockMvcBuilders.standaloneSetup(accountUserMockResource)
            .setControllerAdvice(exceptionTranslator)
            .build();
    }

    @Test
    public void testNonAuthenticatedUser() throws Exception {
        restUserMockMvc.perform(get("/api/authenticate")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(""));
    }

    @Test
    public void testAuthenticatedUser() throws Exception {
        restUserMockMvc.perform(get("/api/authenticate")
            .with(request -> {
                request.setRemoteUser("test");
                return request;
            })
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string("test"));
    }

    @Test
    public void testGetExistingAccount() throws Exception {
        Set<Authority> authorities = new HashSet<>();
        Authority authority = new Authority();
        authority.setName(AuthoritiesConstants.ADMIN);
        authorities.add(authority);

        IDBUser user = new IDBUser();
        user.setEmail("john.doe@jhipster.com");
        user.setImageUrl("http://placehold.it/50x50");
        user.setLangKey("en");
        user.setAuthorities(authorities);
        when(mockUserService.getUserWithAuthorities()).thenReturn(Optional.of(user));

        restUserMockMvc.perform(get("/api/account")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.login").value("test"))
            .andExpect(jsonPath("$.firstName").value("john"))
            .andExpect(jsonPath("$.lastName").value("doe"))
            .andExpect(jsonPath("$.email").value("john.doe@jhipster.com"))
            .andExpect(jsonPath("$.imageUrl").value("http://placehold.it/50x50"))
            .andExpect(jsonPath("$.langKey").value("en"))
            .andExpect(jsonPath("$.authorities").value(AuthoritiesConstants.ADMIN));
    }

    @Test
    public void testGetUnknownAccount() throws Exception {
        when(mockUserService.getUserWithAuthorities()).thenReturn(Optional.empty());

        restUserMockMvc.perform(get("/api/account")
            .accept(MediaType.APPLICATION_PROBLEM_JSON))
            .andExpect(status().isInternalServerError());
    }
	/*
	 * @Test
	 * 
	 * @Transactional public void testRegisterValid() throws Exception {
	 * ManagedUserVM validUser = new ManagedUserVM();
	 * validUser.setPassword("password");
	 * validUser.setEmail("test-register-valid@example.com");
	 * validUser.setImageUrl("http://placehold.it/50x50");
	 * validUser.setLangKey(Constants.DEFAULT_LANGUAGE);
	 * validUser.setAuthorities(Collections.singleton(AuthoritiesConstants.USER));
	 * assertThat(userRepository.findOneByLogin("test-register-valid").isPresent()).
	 * isFalse();
	 * 
	 * restMvc.perform( post("/api/register")
	 * .contentType(TestUtil.APPLICATION_JSON_UTF8)
	 * .content(TestUtil.convertObjectToJsonBytes(validUser)))
	 * .andExpect(status().isCreated());
	 * 
	 * assertThat(userRepository.findOneByLogin("test-register-valid").isPresent()).
	 * isTrue(); }
	 * 
	 * @Test
	 * 
	 * @Transactional public void testRegisterInvalidLogin() throws Exception {
	 * ManagedUserVM invalidUser = new ManagedUserVM();
	 * invalidUser.setPassword("password");
	 * invalidUser.setEmail("funky@example.com"); invalidUser.setActivated(true);
	 * invalidUser.setImageUrl("http://placehold.it/50x50");
	 * invalidUser.setLangKey(Constants.DEFAULT_LANGUAGE);
	 * invalidUser.setAuthorities(Collections.singleton(AuthoritiesConstants.USER));
	 * 
	 * restUserMockMvc.perform( post("/api/register")
	 * .contentType(TestUtil.APPLICATION_JSON_UTF8)
	 * .content(TestUtil.convertObjectToJsonBytes(invalidUser)))
	 * .andExpect(status().isBadRequest());
	 * 
	 * Optional<IDBUser> user =
	 * userRepository.findOneByEmailIgnoreCase("funky@example.com");
	 * assertThat(user.isPresent()).isFalse(); }
	 * 
	 * @Test
	 * 
	 * @Transactional public void testRegisterInvalidEmail() throws Exception {
	 * ManagedUserVM invalidUser = new ManagedUserVM();
	 * invalidUser.setPassword("password"); invalidUser.setEmail("invalid");// <--
	 * invalid invalidUser.setActivated(true);
	 * invalidUser.setImageUrl("http://placehold.it/50x50");
	 * invalidUser.setLangKey(Constants.DEFAULT_LANGUAGE);
	 * invalidUser.setAuthorities(Collections.singleton(AuthoritiesConstants.USER));
	 * 
	 * restUserMockMvc.perform( post("/api/register")
	 * .contentType(TestUtil.APPLICATION_JSON_UTF8)
	 * .content(TestUtil.convertObjectToJsonBytes(invalidUser)))
	 * .andExpect(status().isBadRequest());
	 * 
	 * Optional<IDBUser> user = userRepository.findOneByLogin("bob");
	 * assertThat(user.isPresent()).isFalse(); }
	 * 
	 * @Test
	 * 
	 * @Transactional public void testRegisterInvalidPassword() throws Exception {
	 * ManagedUserVM invalidUser = new ManagedUserVM();
	 * invalidUser.setPassword("123");// password with only 3 digits
	 * invalidUser.setEmail("bob@example.com"); invalidUser.setActivated(true);
	 * invalidUser.setImageUrl("http://placehold.it/50x50");
	 * invalidUser.setLangKey(Constants.DEFAULT_LANGUAGE);
	 * invalidUser.setAuthorities(Collections.singleton(AuthoritiesConstants.USER));
	 * 
	 * restUserMockMvc.perform( post("/api/register")
	 * .contentType(TestUtil.APPLICATION_JSON_UTF8)
	 * .content(TestUtil.convertObjectToJsonBytes(invalidUser)))
	 * .andExpect(status().isBadRequest());
	 * 
	 * Optional<IDBUser> user = userRepository.findOneByLogin("bob");
	 * assertThat(user.isPresent()).isFalse(); }
	 * 
	 * @Test
	 * 
	 * @Transactional public void testRegisterNullPassword() throws Exception {
	 * ManagedUserVM invalidUser = new ManagedUserVM();
	 * invalidUser.setPassword(null);// invalid null password
	 * invalidUser.setEmail("bob@example.com"); invalidUser.setActivated(true);
	 * invalidUser.setImageUrl("http://placehold.it/50x50");
	 * invalidUser.setLangKey(Constants.DEFAULT_LANGUAGE);
	 * invalidUser.setAuthorities(Collections.singleton(AuthoritiesConstants.USER));
	 * 
	 * restUserMockMvc.perform( post("/api/register")
	 * .contentType(TestUtil.APPLICATION_JSON_UTF8)
	 * .content(TestUtil.convertObjectToJsonBytes(invalidUser)))
	 * .andExpect(status().isBadRequest());
	 * 
	 * Optional<IDBUser> user = userRepository.findOneByLogin("bob");
	 * assertThat(user.isPresent()).isFalse(); }
	 * 
	 * @Test
	 * 
	 * @Transactional public void testRegisterDuplicateLogin() throws Exception { //
	 * First registration ManagedUserVM firstUser = new ManagedUserVM();
	 * firstUser.setPassword("password"); firstUser.setEmail("alice@example.com");
	 * firstUser.setImageUrl("http://placehold.it/50x50");
	 * firstUser.setLangKey(Constants.DEFAULT_LANGUAGE);
	 * firstUser.setAuthorities(Collections.singleton(AuthoritiesConstants.USER));
	 * 
	 * // Duplicate login, different email ManagedUserVM secondUser = new
	 * ManagedUserVM(); secondUser.setPassword(firstUser.getPassword());
	 * secondUser.setEmail("alice2@example.com");
	 * secondUser.setImageUrl(firstUser.getImageUrl());
	 * secondUser.setLangKey(firstUser.getLangKey());
	 * secondUser.setCreatedBy(firstUser.getCreatedBy());
	 * secondUser.setCreatedDate(firstUser.getCreatedDate());
	 * secondUser.setLastUpdatedBy(firstUser.getLastUpdatedBy());
	 * secondUser.setLastUpdatedDate(firstUser.getLastUpdatedDate());
	 * secondUser.setAuthorities(new HashSet<>(firstUser.getAuthorities()));
	 * 
	 * // First user restMvc.perform( post("/api/register")
	 * .contentType(TestUtil.APPLICATION_JSON_UTF8)
	 * .content(TestUtil.convertObjectToJsonBytes(firstUser)))
	 * .andExpect(status().isCreated());
	 * 
	 * // Second (non activated) user restMvc.perform( post("/api/register")
	 * .contentType(TestUtil.APPLICATION_JSON_UTF8)
	 * .content(TestUtil.convertObjectToJsonBytes(secondUser)))
	 * .andExpect(status().isCreated());
	 * 
	 * Optional<IDBUser> testUser =
	 * userRepository.findOneByEmailIgnoreCase("alice2@example.com");
	 * assertThat(testUser.isPresent()).isTrue(); testUser.get().setActivated(true);
	 * userRepository.save(testUser.get());
	 * 
	 * // Second (already activated) user restMvc.perform( post("/api/register")
	 * .contentType(TestUtil.APPLICATION_JSON_UTF8)
	 * .content(TestUtil.convertObjectToJsonBytes(secondUser)))
	 * .andExpect(status().is4xxClientError()); }
	 * 
	 * @Test
	 * 
	 * @Transactional public void testRegisterDuplicateEmail() throws Exception { //
	 * First user ManagedUserVM firstUser = new ManagedUserVM();
	 * firstUser.setPassword("password");
	 * firstUser.setEmail("test-register-duplicate-email@example.com");
	 * firstUser.setImageUrl("http://placehold.it/50x50");
	 * firstUser.setLangKey(Constants.DEFAULT_LANGUAGE);
	 * firstUser.setAuthorities(Collections.singleton(AuthoritiesConstants.USER));
	 * 
	 * // Register first user restMvc.perform( post("/api/register")
	 * .contentType(TestUtil.APPLICATION_JSON_UTF8)
	 * .content(TestUtil.convertObjectToJsonBytes(firstUser)))
	 * .andExpect(status().isCreated());
	 * 
	 * Optional<IDBUser> testUser1 =
	 * userRepository.findOneByLogin("test-register-duplicate-email");
	 * assertThat(testUser1.isPresent()).isTrue();
	 * 
	 * // Duplicate email, different login ManagedUserVM secondUser = new
	 * ManagedUserVM(); secondUser.setPassword(firstUser.getPassword());
	 * secondUser.setEmail(firstUser.getEmail());
	 * secondUser.setImageUrl(firstUser.getImageUrl());
	 * secondUser.setLangKey(firstUser.getLangKey()); secondUser.setAuthorities(new
	 * HashSet<>(firstUser.getAuthorities()));
	 * 
	 * // Register second (non activated) user restMvc.perform(
	 * post("/api/register") .contentType(TestUtil.APPLICATION_JSON_UTF8)
	 * .content(TestUtil.convertObjectToJsonBytes(secondUser)))
	 * .andExpect(status().isCreated());
	 * 
	 * Optional<IDBUser> testUser2 =
	 * userRepository.findOneByLogin("test-register-duplicate-email");
	 * assertThat(testUser2.isPresent()).isFalse();
	 * 
	 * Optional<IDBUser> testUser3 =
	 * userRepository.findOneByLogin("test-register-duplicate-email-2");
	 * assertThat(testUser3.isPresent()).isTrue();
	 * 
	 * // Duplicate email - with uppercase email address ManagedUserVM
	 * userWithUpperCaseEmail = new ManagedUserVM();
	 * userWithUpperCaseEmail.setId(firstUser.getId());
	 * userWithUpperCaseEmail.setPassword(firstUser.getPassword());
	 * userWithUpperCaseEmail.setEmail("TEST-register-duplicate-email@example.com");
	 * userWithUpperCaseEmail.setImageUrl(firstUser.getImageUrl());
	 * userWithUpperCaseEmail.setLangKey(firstUser.getLangKey());
	 * userWithUpperCaseEmail.setAuthorities(new
	 * HashSet<>(firstUser.getAuthorities()));
	 * 
	 * // Register third (not activated) user restMvc.perform( post("/api/register")
	 * .contentType(TestUtil.APPLICATION_JSON_UTF8)
	 * .content(TestUtil.convertObjectToJsonBytes(userWithUpperCaseEmail)))
	 * .andExpect(status().isCreated());
	 * 
	 * Optional<IDBUser> testUser4 =
	 * userRepository.findOneByLogin("test-register-duplicate-email-3");
	 * assertThat(testUser4.isPresent()).isTrue();
	 * assertThat(testUser4.get().getEmail()).isEqualTo(
	 * "test-register-duplicate-email@example.com");
	 * 
	 * testUser4.get().setActivated(true); userService.updateUser((new
	 * IDBUserDTO(testUser4.get())));
	 * 
	 * // Register 4th (already activated) user restMvc.perform(
	 * post("/api/register") .contentType(TestUtil.APPLICATION_JSON_UTF8)
	 * .content(TestUtil.convertObjectToJsonBytes(secondUser)))
	 * .andExpect(status().is4xxClientError()); }
	 * 
	 * @Test
	 * 
	 * @Transactional public void testRegisterAdminIsIgnored() throws Exception {
	 * ManagedUserVM validUser = new ManagedUserVM();
	 * validUser.setPassword("password"); validUser.setEmail("badguy@example.com");
	 * validUser.setActivated(true);
	 * validUser.setImageUrl("http://placehold.it/50x50");
	 * validUser.setLangKey(Constants.DEFAULT_LANGUAGE);
	 * validUser.setAuthorities(Collections.singleton(AuthoritiesConstants.ADMIN));
	 * 
	 * restMvc.perform( post("/api/register")
	 * .contentType(TestUtil.APPLICATION_JSON_UTF8)
	 * .content(TestUtil.convertObjectToJsonBytes(validUser)))
	 * .andExpect(status().isCreated());
	 * 
	 * Optional<IDBUser> userDup = userRepository.findOneByLogin("badguy");
	 * assertThat(userDup.isPresent()).isTrue();
	 * assertThat(userDup.get().getAuthorities()).hasSize(1)
	 * .containsExactly(authorityRepository.findById(AuthoritiesConstants.USER).get(
	 * )); }
	 * 
	 * @Test
	 * 
	 * @Transactional public void testActivateAccount() throws Exception { final
	 * String activationKey = "some activation key"; IDBUser user = new IDBUser();
	 * user.setEmail("activate-account@example.com");
	 * user.setPassword(RandomStringUtils.random(60)); user.setActivated(false);
	 * user.setActivationKey(activationKey);
	 * 
	 * userRepository.saveAndFlush(user);
	 * 
	 * restMvc.perform(get("/api/activate?key={activationKey}", activationKey))
	 * .andExpect(status().isOk());
	 * 
	 * user = userRepository.findOneByLogin(user.getEmail()).orElse(null);
	 * assertThat(user.getActivated()).isTrue(); }
	 * 
	 * @Test
	 * 
	 * @Transactional public void testActivateAccountWithWrongKey() throws Exception
	 * { restMvc.perform(get("/api/activate?key=wrongActivationKey"))
	 * .andExpect(status().isInternalServerError()); }
	 * 
	 * @Test
	 * 
	 * @Transactional
	 * 
	 * @WithMockUser("save-account") public void testSaveAccount() throws Exception
	 * { IDBUser user = new IDBUser(); user.setEmail("save-account@example.com");
	 * user.setPassword(RandomStringUtils.random(60)); user.setActivated(true);
	 * 
	 * userRepository.saveAndFlush(user);
	 * 
	 * IDBUserDTO userDTO = new IDBUserDTO();
	 * userDTO.setEmail("save-account@example.com"); userDTO.setActivated(false);
	 * userDTO.setImageUrl("http://placehold.it/50x50");
	 * userDTO.setLangKey(Constants.DEFAULT_LANGUAGE);
	 * userDTO.setAuthorities(Collections.singleton(AuthoritiesConstants.ADMIN));
	 * 
	 * restMvc.perform( post("/api/account")
	 * .contentType(TestUtil.APPLICATION_JSON_UTF8)
	 * .content(TestUtil.convertObjectToJsonBytes(userDTO)))
	 * .andExpect(status().isOk());
	 * 
	 * IDBUser updatedUser =
	 * userRepository.findOneByLogin(user.getEmail()).orElse(null);
	 * assertThat(updatedUser.getEmail()).isEqualTo(userDTO.getEmail());
	 * assertThat(updatedUser.getLangKey()).isEqualTo(userDTO.getLangKey());
	 * assertThat(updatedUser.getPassword()).isEqualTo(user.getPassword());
	 * assertThat(updatedUser.getImageUrl()).isEqualTo(userDTO.getImageUrl());
	 * assertThat(updatedUser.getActivated()).isEqualTo(true);
	 * assertThat(updatedUser.getAuthorities()).isEmpty(); }
	 * 
	 * @Test
	 * 
	 * @Transactional
	 * 
	 * @WithMockUser("save-invalid-email") public void testSaveInvalidEmail() throws
	 * Exception { IDBUser user = new IDBUser();
	 * user.setEmail("save-invalid-email@example.com");
	 * user.setPassword(RandomStringUtils.random(60)); user.setActivated(true);
	 * 
	 * userRepository.saveAndFlush(user);
	 * 
	 * IDBUserDTO userDTO = new IDBUserDTO(); userDTO.setEmail("invalid email");
	 * userDTO.setActivated(false);
	 * userDTO.setImageUrl("http://placehold.it/50x50");
	 * userDTO.setLangKey(Constants.DEFAULT_LANGUAGE);
	 * userDTO.setAuthorities(Collections.singleton(AuthoritiesConstants.ADMIN));
	 * 
	 * restMvc.perform( post("/api/account")
	 * .contentType(TestUtil.APPLICATION_JSON_UTF8)
	 * .content(TestUtil.convertObjectToJsonBytes(userDTO)))
	 * .andExpect(status().isBadRequest());
	 * 
	 * assertThat(userRepository.findOneByEmailIgnoreCase("invalid email")).
	 * isNotPresent(); }
	 * 
	 * @Test
	 * 
	 * @Transactional
	 * 
	 * @WithMockUser("save-existing-email") public void testSaveExistingEmail()
	 * throws Exception { IDBUser user = new IDBUser();
	 * user.setEmail("save-existing-email@example.com");
	 * user.setPassword(RandomStringUtils.random(60)); user.setActivated(true);
	 * 
	 * userRepository.saveAndFlush(user);
	 * 
	 * IDBUser anotherUser = new IDBUser();
	 * anotherUser.setEmail("save-existing-email2@example.com");
	 * anotherUser.setPassword(RandomStringUtils.random(60));
	 * anotherUser.setActivated(true);
	 * 
	 * userRepository.saveAndFlush(anotherUser);
	 * 
	 * IDBUserDTO userDTO = new IDBUserDTO();
	 * userDTO.setEmail("save-existing-email2@example.com");
	 * userDTO.setActivated(false);
	 * userDTO.setImageUrl("http://placehold.it/50x50");
	 * userDTO.setLangKey(Constants.DEFAULT_LANGUAGE);
	 * userDTO.setAuthorities(Collections.singleton(AuthoritiesConstants.ADMIN));
	 * 
	 * restMvc.perform( post("/api/account")
	 * .contentType(TestUtil.APPLICATION_JSON_UTF8)
	 * .content(TestUtil.convertObjectToJsonBytes(userDTO)))
	 * .andExpect(status().isBadRequest());
	 * 
	 * IDBUser updatedUser =
	 * userRepository.findOneByLogin("save-existing-email").orElse(null);
	 * assertThat(updatedUser.getEmail()).isEqualTo(
	 * "save-existing-email@example.com"); }
	 * 
	 * @Test
	 * 
	 * @Transactional
	 * 
	 * @WithMockUser("save-existing-email-and-login") public void
	 * testSaveExistingEmailAndLogin() throws Exception { IDBUser user = new
	 * IDBUser(); user.setEmail("save-existing-email-and-login@example.com");
	 * user.setPassword(RandomStringUtils.random(60)); user.setActivated(true);
	 * 
	 * userRepository.saveAndFlush(user);
	 * 
	 * IDBUserDTO userDTO = new IDBUserDTO();
	 * userDTO.setEmail("save-existing-email-and-login@example.com");
	 * userDTO.setActivated(false);
	 * userDTO.setImageUrl("http://placehold.it/50x50");
	 * userDTO.setLangKey(Constants.DEFAULT_LANGUAGE);
	 * userDTO.setAuthorities(Collections.singleton(AuthoritiesConstants.ADMIN));
	 * 
	 * restMvc.perform( post("/api/account")
	 * .contentType(TestUtil.APPLICATION_JSON_UTF8)
	 * .content(TestUtil.convertObjectToJsonBytes(userDTO)))
	 * .andExpect(status().isOk());
	 * 
	 * IDBUser updatedUser =
	 * userRepository.findOneByLogin("save-existing-email-and-login").orElse(null);
	 * assertThat(updatedUser.getEmail()).isEqualTo(
	 * "save-existing-email-and-login@example.com"); }
	 * 
	 * @Test
	 * 
	 * @Transactional
	 * 
	 * @WithMockUser("change-password-wrong-existing-password") public void
	 * testChangePasswordWrongExistingPassword() throws Exception { IDBUser user =
	 * new IDBUser(); String currentPassword = RandomStringUtils.random(60);
	 * user.setPassword(passwordEncoder.encode(currentPassword));
	 * user.setEmail("change-password-wrong-existing-password@example.com");
	 * userRepository.saveAndFlush(user);
	 * 
	 * restMvc.perform(post("/api/account/change-password")
	 * .contentType(TestUtil.APPLICATION_JSON_UTF8)
	 * .content(TestUtil.convertObjectToJsonBytes(new
	 * PasswordChangeDTO("1"+currentPassword, "new password"))))
	 * .andExpect(status().isBadRequest());
	 * 
	 * IDBUser updatedUser =
	 * userRepository.findOneByLogin("change-password-wrong-existing-password").
	 * orElse(null); assertThat(passwordEncoder.matches("new password",
	 * updatedUser.getPassword())).isFalse();
	 * assertThat(passwordEncoder.matches(currentPassword,
	 * updatedUser.getPassword())).isTrue(); }
	 * 
	 * @Test
	 * 
	 * @Transactional
	 * 
	 * @WithMockUser("change-password") public void testChangePassword() throws
	 * Exception { IDBUser user = new IDBUser(); String currentPassword =
	 * RandomStringUtils.random(60);
	 * user.setPassword(passwordEncoder.encode(currentPassword));
	 * user.setEmail("change-password@example.com");
	 * userRepository.saveAndFlush(user);
	 * 
	 * restMvc.perform(post("/api/account/change-password")
	 * .contentType(TestUtil.APPLICATION_JSON_UTF8)
	 * .content(TestUtil.convertObjectToJsonBytes(new
	 * PasswordChangeDTO(currentPassword, "new password"))))
	 * .andExpect(status().isOk());
	 * 
	 * IDBUser updatedUser =
	 * userRepository.findOneByLogin("change-password").orElse(null);
	 * assertThat(passwordEncoder.matches("new password",
	 * updatedUser.getPassword())).isTrue(); }
	 * 
	 * @Test
	 * 
	 * @Transactional
	 * 
	 * @WithMockUser("change-password-too-small") public void
	 * testChangePasswordTooSmall() throws Exception { IDBUser user = new IDBUser();
	 * String currentPassword = RandomStringUtils.random(60);
	 * user.setPassword(passwordEncoder.encode(currentPassword));
	 * user.setEmail("change-password-too-small@example.com");
	 * userRepository.saveAndFlush(user);
	 * 
	 * String newPassword =
	 * RandomStringUtils.random(ManagedUserVM.PASSWORD_MIN_LENGTH - 1);
	 * 
	 * restMvc.perform(post("/api/account/change-password")
	 * .contentType(TestUtil.APPLICATION_JSON_UTF8)
	 * .content(TestUtil.convertObjectToJsonBytes(new
	 * PasswordChangeDTO(currentPassword, newPassword))))
	 * .andExpect(status().isBadRequest());
	 * 
	 * IDBUser updatedUser =
	 * userRepository.findOneByLogin("change-password-too-small").orElse(null);
	 * assertThat(updatedUser.getPassword()).isEqualTo(user.getPassword()); }
	 * 
	 * @Test
	 * 
	 * @Transactional
	 * 
	 * @WithMockUser("change-password-too-long") public void
	 * testChangePasswordTooLong() throws Exception { IDBUser user = new IDBUser();
	 * String currentPassword = RandomStringUtils.random(60);
	 * user.setPassword(passwordEncoder.encode(currentPassword));
	 * user.setEmail("change-password-too-long@example.com");
	 * userRepository.saveAndFlush(user);
	 * 
	 * String newPassword =
	 * RandomStringUtils.random(ManagedUserVM.PASSWORD_MAX_LENGTH + 1);
	 * 
	 * restMvc.perform(post("/api/account/change-password")
	 * .contentType(TestUtil.APPLICATION_JSON_UTF8)
	 * .content(TestUtil.convertObjectToJsonBytes(new
	 * PasswordChangeDTO(currentPassword, newPassword))))
	 * .andExpect(status().isBadRequest());
	 * 
	 * IDBUser updatedUser =
	 * userRepository.findOneByLogin("change-password-too-long").orElse(null);
	 * assertThat(updatedUser.getPassword()).isEqualTo(user.getPassword()); }
	 * 
	 * @Test
	 * 
	 * @Transactional
	 * 
	 * @WithMockUser("change-password-empty") public void testChangePasswordEmpty()
	 * throws Exception { IDBUser user = new IDBUser(); String currentPassword =
	 * RandomStringUtils.random(60);
	 * user.setPassword(passwordEncoder.encode(currentPassword));
	 * user.setEmail("change-password-empty@example.com");
	 * userRepository.saveAndFlush(user);
	 * 
	 * restMvc.perform(post("/api/account/change-password")
	 * .contentType(TestUtil.APPLICATION_JSON_UTF8)
	 * .content(TestUtil.convertObjectToJsonBytes(new
	 * PasswordChangeDTO(currentPassword, ""))))
	 * .andExpect(status().isBadRequest());
	 * 
	 * IDBUser updatedUser =
	 * userRepository.findOneByLogin("change-password-empty").orElse(null);
	 * assertThat(updatedUser.getPassword()).isEqualTo(user.getPassword()); }
	 * 
	 * @Test
	 * 
	 * @Transactional public void testRequestPasswordReset() throws Exception {
	 * IDBUser user = new IDBUser(); user.setPassword(RandomStringUtils.random(60));
	 * user.setActivated(true); user.setEmail("password-reset@example.com");
	 * userRepository.saveAndFlush(user);
	 * 
	 * restMvc.perform(post("/api/account/reset-password/init")
	 * .content("password-reset@example.com")) .andExpect(status().isOk()); }
	 * 
	 * @Test
	 * 
	 * @Transactional public void testRequestPasswordResetUpperCaseEmail() throws
	 * Exception { IDBUser user = new IDBUser();
	 * user.setPassword(RandomStringUtils.random(60)); user.setActivated(true);
	 * user.setEmail("password-reset@example.com");
	 * userRepository.saveAndFlush(user);
	 * 
	 * restMvc.perform(post("/api/account/reset-password/init")
	 * .content("password-reset@EXAMPLE.COM")) .andExpect(status().isOk()); }
	 * 
	 * @Test public void testRequestPasswordResetWrongEmail() throws Exception {
	 * restMvc.perform( post("/api/account/reset-password/init")
	 * .content("password-reset-wrong-email@example.com"))
	 * .andExpect(status().isBadRequest()); }
	 * 
	 * @Test
	 * 
	 * @Transactional public void testFinishPasswordReset() throws Exception {
	 * IDBUser user = new IDBUser(); user.setPassword(RandomStringUtils.random(60));
	 * user.setEmail("finish-password-reset@example.com");
	 * user.setResetDate(Instant.now().plusSeconds(60));
	 * user.setResetKey("reset key"); userRepository.saveAndFlush(user);
	 * 
	 * KeyAndPasswordVM keyAndPassword = new KeyAndPasswordVM();
	 * keyAndPassword.setKey(user.getResetKey());
	 * keyAndPassword.setNewPassword("new password");
	 * 
	 * restMvc.perform( post("/api/account/reset-password/finish")
	 * .contentType(TestUtil.APPLICATION_JSON_UTF8)
	 * .content(TestUtil.convertObjectToJsonBytes(keyAndPassword)))
	 * .andExpect(status().isOk());
	 * 
	 * IDBUser updatedUser =
	 * userRepository.findOneByLogin(user.getEmail()).orElse(null);
	 * assertThat(passwordEncoder.matches(keyAndPassword.getNewPassword(),
	 * updatedUser.getPassword())).isTrue(); }
	 * 
	 * @Test
	 * 
	 * @Transactional public void testFinishPasswordResetTooSmall() throws Exception
	 * { IDBUser user = new IDBUser();
	 * user.setPassword(RandomStringUtils.random(60));
	 * user.setEmail("finish-password-reset-too-small@example.com");
	 * user.setResetDate(Instant.now().plusSeconds(60));
	 * user.setResetKey("reset key too small"); userRepository.saveAndFlush(user);
	 * 
	 * KeyAndPasswordVM keyAndPassword = new KeyAndPasswordVM();
	 * keyAndPassword.setKey(user.getResetKey());
	 * keyAndPassword.setNewPassword("foo");
	 * 
	 * restMvc.perform( post("/api/account/reset-password/finish")
	 * .contentType(TestUtil.APPLICATION_JSON_UTF8)
	 * .content(TestUtil.convertObjectToJsonBytes(keyAndPassword)))
	 * .andExpect(status().isBadRequest());
	 * 
	 * IDBUser updatedUser =
	 * userRepository.findOneByLogin(user.getEmail()).orElse(null);
	 * assertThat(passwordEncoder.matches(keyAndPassword.getNewPassword(),
	 * updatedUser.getPassword())).isFalse(); }
	 */


    @Test
    @Transactional
    public void testFinishPasswordResetWrongKey() throws Exception {
        KeyAndPasswordVM keyAndPassword = new KeyAndPasswordVM();
        keyAndPassword.setKey("wrong reset key");
        keyAndPassword.setNewPassword("new password");

        restMvc.perform(
            post("/api/account/reset-password/finish")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(keyAndPassword)))
            .andExpect(status().isInternalServerError());
    }
}
