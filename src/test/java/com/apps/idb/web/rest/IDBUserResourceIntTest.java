package com.apps.idb.web.rest;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.apps.idb.IdbApp;

/**
 * Test class for the IDBUserResource REST controller.
 *
 * @see IDBUserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IdbApp.class)
public class IDBUserResourceIntTest {

	/*
	 * private static final String DEFAULT_EMAIL = "AAAAAAAAAA"; private static
	 * final String UPDATED_EMAIL = "BBBBBBBBBB";
	 * 
	 * private static final String DEFAULT_PASSWORD = "AAAAAAAAAA"; private static
	 * final String UPDATED_PASSWORD = "BBBBBBBBBB";
	 * 
	 * private static final Boolean DEFAULT_ACTIVATED = false; private static final
	 * Boolean UPDATED_ACTIVATED = true;
	 * 
	 * private static final String DEFAULT_ACCESS_TOKEN = "AAAAAAAAAA"; private
	 * static final String UPDATED_ACCESS_TOKEN = "BBBBBBBBBB";
	 * 
	 * private static final String DEFAULT_SESSION_TOKEN = "AAAAAAAAAA"; private
	 * static final String UPDATED_SESSION_TOKEN = "BBBBBBBBBB";
	 * 
	 * private static final LocalDate DEFAULT_LAST_LOGIN_DATE =
	 * LocalDate.ofEpochDay(0L); private static final LocalDate
	 * UPDATED_LAST_LOGIN_DATE = LocalDate.now(ZoneId.systemDefault());
	 * 
	 * private static final LocalDate DEFAULT_LAST_DEACTIVATED_DATE =
	 * LocalDate.ofEpochDay(0L); private static final LocalDate
	 * UPDATED_LAST_DEACTIVATED_DATE = LocalDate.now(ZoneId.systemDefault());
	 * 
	 * private static final UserRoles DEFAULT_USER_ROLES = UserRoles.ADMIN; private
	 * static final UserRoles UPDATED_USER_ROLES = UserRoles.USER;
	 * 
	 * private static final Boolean DEFAULT_VERIFIED = false; private static final
	 * Boolean UPDATED_VERIFIED = true;
	 * 
	 * private static final String DEFAULT_VERIFICATION_METHOD = "AAAAAAAAAA";
	 * private static final String UPDATED_VERIFICATION_METHOD = "BBBBBBBBBB";
	 * 
	 * private static final Boolean DEFAULT_IS_REPORTED_SCAM = false; private static
	 * final Boolean UPDATED_IS_REPORTED_SCAM = true;
	 * 
	 * private static final LocalDate DEFAULT_LAST_LOGOUT =
	 * LocalDate.ofEpochDay(0L); private static final LocalDate UPDATED_LAST_LOGOUT
	 * = LocalDate.now(ZoneId.systemDefault());
	 * 
	 * private static final LocalDate DEFAULT_LAST_ACTIVATED_DATE =
	 * LocalDate.ofEpochDay(0L); private static final LocalDate
	 * UPDATED_LAST_ACTIVATED_DATE = LocalDate.now(ZoneId.systemDefault());
	 * 
	 * private static final LocalDate DEFAULT_CREATED_DATE =
	 * LocalDate.ofEpochDay(0L); private static final LocalDate UPDATED_CREATED_DATE
	 * = LocalDate.now(ZoneId.systemDefault());
	 * 
	 * private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA"; private static
	 * final String UPDATED_CREATED_BY = "BBBBBBBBBB";
	 * 
	 * private static final LocalDate DEFAULT_LAST_UPDATED_DATE =
	 * LocalDate.ofEpochDay(0L); private static final LocalDate
	 * UPDATED_LAST_UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());
	 * 
	 * private static final String DEFAULT_LAST_UPDATED_BY = "AAAAAAAAAA"; private
	 * static final String UPDATED_LAST_UPDATED_BY = "BBBBBBBBBB";
	 * 
	 * @Autowired private IDBUserRepository iDBUserRepository;
	 * 
	 * @Autowired private IDBUserMapper iDBUserMapper;
	 * 
	 * @Autowired private IDBUserService iDBUserService;
	 * 
	 * @Autowired private MappingJackson2HttpMessageConverter
	 * jacksonMessageConverter;
	 * 
	 * @Autowired private PageableHandlerMethodArgumentResolver
	 * pageableArgumentResolver;
	 * 
	 * @Autowired private ExceptionTranslator exceptionTranslator;
	 * 
	 * @Autowired private EntityManager em;
	 * 
	 * @Autowired private Validator validator;
	 * 
	 * private MockMvc restIDBUserMockMvc;
	 * 
	 * private IDBUser iDBUser;
	 * 
	 * @Before public void setup() { MockitoAnnotations.initMocks(this); final
	 * IDBUserResource iDBUserResource = new
	 * IDBUserResource(iDBUserService,null,null); this.restIDBUserMockMvc =
	 * MockMvcBuilders.standaloneSetup(iDBUserResource)
	 * .setCustomArgumentResolvers(pageableArgumentResolver)
	 * .setControllerAdvice(exceptionTranslator)
	 * .setConversionService(createFormattingConversionService())
	 * .setMessageConverters(jacksonMessageConverter)
	 * .setValidator(validator).build(); }
	 * 
	 *//**
		 * Create an entity for this test.
		 *
		 * This is a static method, as tests for other entities might also need it, if
		 * they test an entity which requires the current entity.
		 *//*
			 * public static IDBUser createEntity(EntityManager em) { IDBUser iDBUser = new
			 * IDBUser() .email(DEFAULT_EMAIL) .password(DEFAULT_PASSWORD)
			 * .activated(DEFAULT_ACTIVATED) .accessToken(DEFAULT_ACCESS_TOKEN)
			 * .sessionToken(DEFAULT_SESSION_TOKEN) .lastLoginDate(DEFAULT_LAST_LOGIN_DATE)
			 * .lastDeactivatedDate(DEFAULT_LAST_DEACTIVATED_DATE)
			 * .userRoles(DEFAULT_USER_ROLES) .verified(DEFAULT_VERIFIED)
			 * .verificationMethod(DEFAULT_VERIFICATION_METHOD)
			 * .isReportedScam(DEFAULT_IS_REPORTED_SCAM) .lastLogout(DEFAULT_LAST_LOGOUT)
			 * .lastActivatedDate(DEFAULT_LAST_ACTIVATED_DATE)
			 * .createdDate(DEFAULT_CREATED_DATE) .createdBy(DEFAULT_CREATED_BY)
			 * .lastUpdatedDate(DEFAULT_LAST_UPDATED_DATE)
			 * .lastUpdatedBy(DEFAULT_LAST_UPDATED_BY); return iDBUser; }
			 * 
			 * @Before public void initTest() { iDBUser = createEntity(em); }
			 * 
			 * @Test
			 * 
			 * @Transactional public void createIDBUser() throws Exception { int
			 * databaseSizeBeforeCreate = iDBUserRepository.findAll().size();
			 * 
			 * // Create the IDBUser IDBUserDTO iDBUserDTO = iDBUserMapper.toDto(iDBUser);
			 * restIDBUserMockMvc.perform(post("/api/idb-users")
			 * .contentType(TestUtil.APPLICATION_JSON_UTF8)
			 * .content(TestUtil.convertObjectToJsonBytes(iDBUserDTO)))
			 * .andExpect(status().isCreated());
			 * 
			 * // Validate the IDBUser in the database List<IDBUser> iDBUserList =
			 * iDBUserRepository.findAll();
			 * assertThat(iDBUserList).hasSize(databaseSizeBeforeCreate + 1); IDBUser
			 * testIDBUser = iDBUserList.get(iDBUserList.size() - 1);
			 * assertThat(testIDBUser.getEmail()).isEqualTo(DEFAULT_EMAIL);
			 * assertThat(testIDBUser.getPassword()).isEqualTo(DEFAULT_PASSWORD);
			 * assertThat(testIDBUser.isActivated()).isEqualTo(DEFAULT_ACTIVATED);
			 * assertThat(testIDBUser.getAccessToken()).isEqualTo(DEFAULT_ACCESS_TOKEN);
			 * assertThat(testIDBUser.getSessionToken()).isEqualTo(DEFAULT_SESSION_TOKEN);
			 * assertThat(testIDBUser.getLastLoginDate()).isEqualTo(DEFAULT_LAST_LOGIN_DATE)
			 * ; assertThat(testIDBUser.getLastDeactivatedDate()).isEqualTo(
			 * DEFAULT_LAST_DEACTIVATED_DATE);
			 * assertThat(testIDBUser.getUserRoles()).isEqualTo(DEFAULT_USER_ROLES);
			 * assertThat(testIDBUser.isVerified()).isEqualTo(DEFAULT_VERIFIED);
			 * assertThat(testIDBUser.getVerificationMethod()).isEqualTo(
			 * DEFAULT_VERIFICATION_METHOD);
			 * assertThat(testIDBUser.isIsReportedScam()).isEqualTo(DEFAULT_IS_REPORTED_SCAM
			 * ); assertThat(testIDBUser.getLastLogout()).isEqualTo(DEFAULT_LAST_LOGOUT);
			 * assertThat(testIDBUser.getLastActivatedDate()).isEqualTo(
			 * DEFAULT_LAST_ACTIVATED_DATE);
			 * assertThat(testIDBUser.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
			 * assertThat(testIDBUser.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
			 * assertThat(testIDBUser.getLastUpdatedDate()).isEqualTo(
			 * DEFAULT_LAST_UPDATED_DATE);
			 * assertThat(testIDBUser.getLastUpdatedBy()).isEqualTo(DEFAULT_LAST_UPDATED_BY)
			 * ; }
			 * 
			 * @Test
			 * 
			 * @Transactional public void createIDBUserWithExistingId() throws Exception {
			 * int databaseSizeBeforeCreate = iDBUserRepository.findAll().size();
			 * 
			 * // Create the IDBUser with an existing ID iDBUser.setId(1L); IDBUserDTO
			 * iDBUserDTO = iDBUserMapper.toDto(iDBUser);
			 * 
			 * // An entity with an existing ID cannot be created, so this API call must
			 * fail restIDBUserMockMvc.perform(post("/api/idb-users")
			 * .contentType(TestUtil.APPLICATION_JSON_UTF8)
			 * .content(TestUtil.convertObjectToJsonBytes(iDBUserDTO)))
			 * .andExpect(status().isBadRequest());
			 * 
			 * // Validate the IDBUser in the database List<IDBUser> iDBUserList =
			 * iDBUserRepository.findAll();
			 * assertThat(iDBUserList).hasSize(databaseSizeBeforeCreate); }
			 * 
			 * @Test
			 * 
			 * @Transactional public void checkEmailIsRequired() throws Exception { int
			 * databaseSizeBeforeTest = iDBUserRepository.findAll().size(); // set the field
			 * null iDBUser.setEmail(null);
			 * 
			 * // Create the IDBUser, which fails. IDBUserDTO iDBUserDTO =
			 * iDBUserMapper.toDto(iDBUser);
			 * 
			 * restIDBUserMockMvc.perform(post("/api/idb-users")
			 * .contentType(TestUtil.APPLICATION_JSON_UTF8)
			 * .content(TestUtil.convertObjectToJsonBytes(iDBUserDTO)))
			 * .andExpect(status().isBadRequest());
			 * 
			 * List<IDBUser> iDBUserList = iDBUserRepository.findAll();
			 * assertThat(iDBUserList).hasSize(databaseSizeBeforeTest); }
			 * 
			 * @Test
			 * 
			 * @Transactional public void checkPasswordIsRequired() throws Exception { int
			 * databaseSizeBeforeTest = iDBUserRepository.findAll().size(); // set the field
			 * null iDBUser.setPassword(null);
			 * 
			 * // Create the IDBUser, which fails. IDBUserDTO iDBUserDTO =
			 * iDBUserMapper.toDto(iDBUser);
			 * 
			 * restIDBUserMockMvc.perform(post("/api/idb-users")
			 * .contentType(TestUtil.APPLICATION_JSON_UTF8)
			 * .content(TestUtil.convertObjectToJsonBytes(iDBUserDTO)))
			 * .andExpect(status().isBadRequest());
			 * 
			 * List<IDBUser> iDBUserList = iDBUserRepository.findAll();
			 * assertThat(iDBUserList).hasSize(databaseSizeBeforeTest); }
			 * 
			 * @Test
			 * 
			 * @Transactional public void checkActivatedIsRequired() throws Exception { int
			 * databaseSizeBeforeTest = iDBUserRepository.findAll().size(); // set the field
			 * null iDBUser.setActivated(null);
			 * 
			 * // Create the IDBUser, which fails. IDBUserDTO iDBUserDTO =
			 * iDBUserMapper.toDto(iDBUser);
			 * 
			 * restIDBUserMockMvc.perform(post("/api/idb-users")
			 * .contentType(TestUtil.APPLICATION_JSON_UTF8)
			 * .content(TestUtil.convertObjectToJsonBytes(iDBUserDTO)))
			 * .andExpect(status().isBadRequest());
			 * 
			 * List<IDBUser> iDBUserList = iDBUserRepository.findAll();
			 * assertThat(iDBUserList).hasSize(databaseSizeBeforeTest); }
			 * 
			 * @Test
			 * 
			 * @Transactional public void checkUserRolesIsRequired() throws Exception { int
			 * databaseSizeBeforeTest = iDBUserRepository.findAll().size(); // set the field
			 * null iDBUser.setUserRoles(null);
			 * 
			 * // Create the IDBUser, which fails. IDBUserDTO iDBUserDTO =
			 * iDBUserMapper.toDto(iDBUser);
			 * 
			 * restIDBUserMockMvc.perform(post("/api/idb-users")
			 * .contentType(TestUtil.APPLICATION_JSON_UTF8)
			 * .content(TestUtil.convertObjectToJsonBytes(iDBUserDTO)))
			 * .andExpect(status().isBadRequest());
			 * 
			 * List<IDBUser> iDBUserList = iDBUserRepository.findAll();
			 * assertThat(iDBUserList).hasSize(databaseSizeBeforeTest); }
			 * 
			 * @Test
			 * 
			 * @Transactional public void checkCreatedDateIsRequired() throws Exception {
			 * int databaseSizeBeforeTest = iDBUserRepository.findAll().size(); // set the
			 * field null iDBUser.setCreatedDate(null);
			 * 
			 * // Create the IDBUser, which fails. IDBUserDTO iDBUserDTO =
			 * iDBUserMapper.toDto(iDBUser);
			 * 
			 * restIDBUserMockMvc.perform(post("/api/idb-users")
			 * .contentType(TestUtil.APPLICATION_JSON_UTF8)
			 * .content(TestUtil.convertObjectToJsonBytes(iDBUserDTO)))
			 * .andExpect(status().isBadRequest());
			 * 
			 * List<IDBUser> iDBUserList = iDBUserRepository.findAll();
			 * assertThat(iDBUserList).hasSize(databaseSizeBeforeTest); }
			 * 
			 * @Test
			 * 
			 * @Transactional public void checkCreatedByIsRequired() throws Exception { int
			 * databaseSizeBeforeTest = iDBUserRepository.findAll().size(); // set the field
			 * null iDBUser.setCreatedBy(null);
			 * 
			 * // Create the IDBUser, which fails. IDBUserDTO iDBUserDTO =
			 * iDBUserMapper.toDto(iDBUser);
			 * 
			 * restIDBUserMockMvc.perform(post("/api/idb-users")
			 * .contentType(TestUtil.APPLICATION_JSON_UTF8)
			 * .content(TestUtil.convertObjectToJsonBytes(iDBUserDTO)))
			 * .andExpect(status().isBadRequest());
			 * 
			 * List<IDBUser> iDBUserList = iDBUserRepository.findAll();
			 * assertThat(iDBUserList).hasSize(databaseSizeBeforeTest); }
			 * 
			 * @Test
			 * 
			 * @Transactional public void getAllIDBUsers() throws Exception { // Initialize
			 * the database iDBUserRepository.saveAndFlush(iDBUser);
			 * 
			 * // Get all the iDBUserList
			 * restIDBUserMockMvc.perform(get("/api/idb-users?sort=id,desc"))
			 * .andExpect(status().isOk())
			 * .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
			 * .andExpect(jsonPath("$.[*].id").value(hasItem(iDBUser.getId().intValue())))
			 * .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
			 * .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString
			 * ()))) .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.
			 * booleanValue())))
			 * .andExpect(jsonPath("$.[*].accessToken").value(hasItem(DEFAULT_ACCESS_TOKEN.
			 * toString())))
			 * .andExpect(jsonPath("$.[*].sessionToken").value(hasItem(DEFAULT_SESSION_TOKEN
			 * .toString()))) .andExpect(jsonPath("$.[*].lastLoginDate").value(hasItem(
			 * DEFAULT_LAST_LOGIN_DATE.toString())))
			 * .andExpect(jsonPath("$.[*].lastDeactivatedDate").value(hasItem(
			 * DEFAULT_LAST_DEACTIVATED_DATE.toString())))
			 * .andExpect(jsonPath("$.[*].userRoles").value(hasItem(DEFAULT_USER_ROLES.
			 * toString())))
			 * .andExpect(jsonPath("$.[*].verified").value(hasItem(DEFAULT_VERIFIED.
			 * booleanValue())))
			 * .andExpect(jsonPath("$.[*].verificationMethod").value(hasItem(
			 * DEFAULT_VERIFICATION_METHOD.toString())))
			 * .andExpect(jsonPath("$.[*].isReportedScam").value(hasItem(
			 * DEFAULT_IS_REPORTED_SCAM.booleanValue())))
			 * .andExpect(jsonPath("$.[*].lastLogout").value(hasItem(DEFAULT_LAST_LOGOUT.
			 * toString()))) .andExpect(jsonPath("$.[*].lastActivatedDate").value(hasItem(
			 * DEFAULT_LAST_ACTIVATED_DATE.toString())))
			 * .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.
			 * toString())))
			 * .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.
			 * toString()))) .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(
			 * DEFAULT_LAST_UPDATED_DATE.toString())))
			 * .andExpect(jsonPath("$.[*].lastUpdatedBy").value(hasItem(
			 * DEFAULT_LAST_UPDATED_BY.toString()))); }
			 * 
			 * @Test
			 * 
			 * @Transactional public void getIDBUser() throws Exception { // Initialize the
			 * database iDBUserRepository.saveAndFlush(iDBUser);
			 * 
			 * // Get the iDBUser restIDBUserMockMvc.perform(get("/api/idb-users/{id}",
			 * iDBUser.getId())) .andExpect(status().isOk())
			 * .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
			 * .andExpect(jsonPath("$.id").value(iDBUser.getId().intValue()))
			 * .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
			 * .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()))
			 * .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
			 * .andExpect(jsonPath("$.accessToken").value(DEFAULT_ACCESS_TOKEN.toString()))
			 * .andExpect(jsonPath("$.sessionToken").value(DEFAULT_SESSION_TOKEN.toString())
			 * )
			 * .andExpect(jsonPath("$.lastLoginDate").value(DEFAULT_LAST_LOGIN_DATE.toString
			 * ())) .andExpect(jsonPath("$.lastDeactivatedDate").value(
			 * DEFAULT_LAST_DEACTIVATED_DATE.toString()))
			 * .andExpect(jsonPath("$.userRoles").value(DEFAULT_USER_ROLES.toString()))
			 * .andExpect(jsonPath("$.verified").value(DEFAULT_VERIFIED.booleanValue()))
			 * .andExpect(jsonPath("$.verificationMethod").value(DEFAULT_VERIFICATION_METHOD
			 * .toString()))
			 * .andExpect(jsonPath("$.isReportedScam").value(DEFAULT_IS_REPORTED_SCAM.
			 * booleanValue()))
			 * .andExpect(jsonPath("$.lastLogout").value(DEFAULT_LAST_LOGOUT.toString()))
			 * .andExpect(jsonPath("$.lastActivatedDate").value(DEFAULT_LAST_ACTIVATED_DATE.
			 * toString()))
			 * .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
			 * .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
			 * .andExpect(jsonPath("$.lastUpdatedDate").value(DEFAULT_LAST_UPDATED_DATE.
			 * toString()))
			 * .andExpect(jsonPath("$.lastUpdatedBy").value(DEFAULT_LAST_UPDATED_BY.toString
			 * ())); }
			 * 
			 * @Test
			 * 
			 * @Transactional public void getNonExistingIDBUser() throws Exception { // Get
			 * the iDBUser restIDBUserMockMvc.perform(get("/api/idb-users/{id}",
			 * Long.MAX_VALUE)) .andExpect(status().isNotFound()); }
			 * 
			 * @Test
			 * 
			 * @Transactional public void updateIDBUser() throws Exception { // Initialize
			 * the database iDBUserRepository.saveAndFlush(iDBUser);
			 * 
			 * int databaseSizeBeforeUpdate = iDBUserRepository.findAll().size();
			 * 
			 * // Update the iDBUser IDBUser updatedIDBUser =
			 * iDBUserRepository.findById(iDBUser.getId()).get(); // Disconnect from session
			 * so that the updates on updatedIDBUser are not directly saved in db
			 * em.detach(updatedIDBUser); updatedIDBUser .email(UPDATED_EMAIL)
			 * .password(UPDATED_PASSWORD) .activated(UPDATED_ACTIVATED)
			 * .accessToken(UPDATED_ACCESS_TOKEN) .sessionToken(UPDATED_SESSION_TOKEN)
			 * .lastLoginDate(UPDATED_LAST_LOGIN_DATE)
			 * .lastDeactivatedDate(UPDATED_LAST_DEACTIVATED_DATE)
			 * .userRoles(UPDATED_USER_ROLES) .verified(UPDATED_VERIFIED)
			 * .verificationMethod(UPDATED_VERIFICATION_METHOD)
			 * .isReportedScam(UPDATED_IS_REPORTED_SCAM) .lastLogout(UPDATED_LAST_LOGOUT)
			 * .lastActivatedDate(UPDATED_LAST_ACTIVATED_DATE)
			 * .createdDate(UPDATED_CREATED_DATE) .createdBy(UPDATED_CREATED_BY)
			 * .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
			 * .lastUpdatedBy(UPDATED_LAST_UPDATED_BY); IDBUserDTO iDBUserDTO =
			 * iDBUserMapper.toDto(updatedIDBUser);
			 * 
			 * restIDBUserMockMvc.perform(put("/api/idb-users")
			 * .contentType(TestUtil.APPLICATION_JSON_UTF8)
			 * .content(TestUtil.convertObjectToJsonBytes(iDBUserDTO)))
			 * .andExpect(status().isOk());
			 * 
			 * // Validate the IDBUser in the database List<IDBUser> iDBUserList =
			 * iDBUserRepository.findAll();
			 * assertThat(iDBUserList).hasSize(databaseSizeBeforeUpdate); IDBUser
			 * testIDBUser = iDBUserList.get(iDBUserList.size() - 1);
			 * assertThat(testIDBUser.getEmail()).isEqualTo(UPDATED_EMAIL);
			 * assertThat(testIDBUser.getPassword()).isEqualTo(UPDATED_PASSWORD);
			 * assertThat(testIDBUser.isActivated()).isEqualTo(UPDATED_ACTIVATED);
			 * assertThat(testIDBUser.getAccessToken()).isEqualTo(UPDATED_ACCESS_TOKEN);
			 * assertThat(testIDBUser.getSessionToken()).isEqualTo(UPDATED_SESSION_TOKEN);
			 * assertThat(testIDBUser.getLastLoginDate()).isEqualTo(UPDATED_LAST_LOGIN_DATE)
			 * ; assertThat(testIDBUser.getLastDeactivatedDate()).isEqualTo(
			 * UPDATED_LAST_DEACTIVATED_DATE);
			 * assertThat(testIDBUser.getUserRoles()).isEqualTo(UPDATED_USER_ROLES);
			 * assertThat(testIDBUser.isVerified()).isEqualTo(UPDATED_VERIFIED);
			 * assertThat(testIDBUser.getVerificationMethod()).isEqualTo(
			 * UPDATED_VERIFICATION_METHOD);
			 * assertThat(testIDBUser.isIsReportedScam()).isEqualTo(UPDATED_IS_REPORTED_SCAM
			 * ); assertThat(testIDBUser.getLastLogout()).isEqualTo(UPDATED_LAST_LOGOUT);
			 * assertThat(testIDBUser.getLastActivatedDate()).isEqualTo(
			 * UPDATED_LAST_ACTIVATED_DATE);
			 * assertThat(testIDBUser.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
			 * assertThat(testIDBUser.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
			 * assertThat(testIDBUser.getLastUpdatedDate()).isEqualTo(
			 * UPDATED_LAST_UPDATED_DATE);
			 * assertThat(testIDBUser.getLastUpdatedBy()).isEqualTo(UPDATED_LAST_UPDATED_BY)
			 * ; }
			 * 
			 * @Test
			 * 
			 * @Transactional public void updateNonExistingIDBUser() throws Exception { int
			 * databaseSizeBeforeUpdate = iDBUserRepository.findAll().size();
			 * 
			 * // Create the IDBUser IDBUserDTO iDBUserDTO = iDBUserMapper.toDto(iDBUser);
			 * 
			 * // If the entity doesn't have an ID, it will throw BadRequestAlertException
			 * restIDBUserMockMvc.perform(put("/api/idb-users")
			 * .contentType(TestUtil.APPLICATION_JSON_UTF8)
			 * .content(TestUtil.convertObjectToJsonBytes(iDBUserDTO)))
			 * .andExpect(status().isBadRequest());
			 * 
			 * // Validate the IDBUser in the database List<IDBUser> iDBUserList =
			 * iDBUserRepository.findAll();
			 * assertThat(iDBUserList).hasSize(databaseSizeBeforeUpdate); }
			 * 
			 * @Test
			 * 
			 * @Transactional public void deleteIDBUser() throws Exception { // Initialize
			 * the database iDBUserRepository.saveAndFlush(iDBUser);
			 * 
			 * int databaseSizeBeforeDelete = iDBUserRepository.findAll().size();
			 * 
			 * // Delete the iDBUser
			 * restIDBUserMockMvc.perform(delete("/api/idb-users/{id}", iDBUser.getId())
			 * .accept(TestUtil.APPLICATION_JSON_UTF8)) .andExpect(status().isOk());
			 * 
			 * // Validate the database is empty List<IDBUser> iDBUserList =
			 * iDBUserRepository.findAll();
			 * assertThat(iDBUserList).hasSize(databaseSizeBeforeDelete - 1); }
			 * 
			 * @Test
			 * 
			 * @Transactional public void equalsVerifier() throws Exception {
			 * TestUtil.equalsVerifier(IDBUser.class); IDBUser iDBUser1 = new IDBUser();
			 * iDBUser1.setId(1L); IDBUser iDBUser2 = new IDBUser();
			 * iDBUser2.setId(iDBUser1.getId()); assertThat(iDBUser1).isEqualTo(iDBUser2);
			 * iDBUser2.setId(2L); assertThat(iDBUser1).isNotEqualTo(iDBUser2);
			 * iDBUser1.setId(null); assertThat(iDBUser1).isNotEqualTo(iDBUser2); }
			 * 
			 * @Test
			 * 
			 * @Transactional public void dtoEqualsVerifier() throws Exception {
			 * TestUtil.equalsVerifier(IDBUserDTO.class); IDBUserDTO iDBUserDTO1 = new
			 * IDBUserDTO(); iDBUserDTO1.setId(1L); IDBUserDTO iDBUserDTO2 = new
			 * IDBUserDTO(); assertThat(iDBUserDTO1).isNotEqualTo(iDBUserDTO2);
			 * iDBUserDTO2.setId(iDBUserDTO1.getId());
			 * assertThat(iDBUserDTO1).isEqualTo(iDBUserDTO2); iDBUserDTO2.setId(2L);
			 * assertThat(iDBUserDTO1).isNotEqualTo(iDBUserDTO2); iDBUserDTO1.setId(null);
			 * assertThat(iDBUserDTO1).isNotEqualTo(iDBUserDTO2); }
			 * 
			 * @Test
			 * 
			 * @Transactional public void testEntityFromId() {
			 * assertThat(iDBUserMapper.fromId(42L).getId()).isEqualTo(42);
			 * assertThat(iDBUserMapper.fromId(null)).isNull(); }
			 */
}
