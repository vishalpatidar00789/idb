package com.apps.idb.web.rest;

import com.apps.idb.IdbApp;

import com.apps.idb.domain.UserAccount;
import com.apps.idb.repository.UserAccountRepository;
import com.apps.idb.service.UserAccountService;
import com.apps.idb.service.dto.UserAccountDTO;
import com.apps.idb.service.mapper.UserAccountMapper;
import com.apps.idb.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.apps.idb.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apps.idb.domain.enumeration.AccountType;
/**
 * Test class for the UserAccountResource REST controller.
 *
 * @see UserAccountResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IdbApp.class)
public class UserAccountResourceIntTest {

    private static final Integer DEFAULT_REMAINING_CHAPTERS = 1;
    private static final Integer UPDATED_REMAINING_CHAPTERS = 2;

    private static final LocalDate DEFAULT_CURR_PACKAGE_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CURR_PACKAGE_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_CURR_PACKAGE_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CURR_PACKAGE_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_USER_DISCOUNT = 1;
    private static final Integer UPDATED_USER_DISCOUNT = 2;

    private static final String DEFAULT_ACTIVATED = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVATED = "BBBBBBBBBB";

    private static final AccountType DEFAULT_ACCOUNT_TYPE = AccountType.Paid;
    private static final AccountType UPDATED_ACCOUNT_TYPE = AccountType.Free;

    private static final Integer DEFAULT_PER_DAY_CHAPTER_LIMIT = 1;
    private static final Integer UPDATED_PER_DAY_CHAPTER_LIMIT = 2;

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_LAST_UPDATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_LAST_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_UPDATED_BY = "BBBBBBBBBB";

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private UserAccountMapper userAccountMapper;

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restUserAccountMockMvc;

    private UserAccount userAccount;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserAccountResource userAccountResource = new UserAccountResource(userAccountService);
        this.restUserAccountMockMvc = MockMvcBuilders.standaloneSetup(userAccountResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserAccount createEntity(EntityManager em) {
        UserAccount userAccount = new UserAccount()
            .remainingChapters(DEFAULT_REMAINING_CHAPTERS)
            .currPackageStartDate(DEFAULT_CURR_PACKAGE_START_DATE)
            .currPackageEndDate(DEFAULT_CURR_PACKAGE_END_DATE)
            .userDiscount(DEFAULT_USER_DISCOUNT)
            .activated(DEFAULT_ACTIVATED)
            .accountType(DEFAULT_ACCOUNT_TYPE)
            .perDayChapterLimit(DEFAULT_PER_DAY_CHAPTER_LIMIT)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .lastUpdatedDate(DEFAULT_LAST_UPDATED_DATE)
            .lastUpdatedBy(DEFAULT_LAST_UPDATED_BY);
        return userAccount;
    }

    @Before
    public void initTest() {
        userAccount = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserAccount() throws Exception {
        int databaseSizeBeforeCreate = userAccountRepository.findAll().size();

        // Create the UserAccount
        UserAccountDTO userAccountDTO = userAccountMapper.toDto(userAccount);
        restUserAccountMockMvc.perform(post("/api/user-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userAccountDTO)))
            .andExpect(status().isCreated());

        // Validate the UserAccount in the database
        List<UserAccount> userAccountList = userAccountRepository.findAll();
        assertThat(userAccountList).hasSize(databaseSizeBeforeCreate + 1);
        UserAccount testUserAccount = userAccountList.get(userAccountList.size() - 1);
        assertThat(testUserAccount.getRemainingChapters()).isEqualTo(DEFAULT_REMAINING_CHAPTERS);
        assertThat(testUserAccount.getCurrPackageStartDate()).isEqualTo(DEFAULT_CURR_PACKAGE_START_DATE);
        assertThat(testUserAccount.getCurrPackageEndDate()).isEqualTo(DEFAULT_CURR_PACKAGE_END_DATE);
        assertThat(testUserAccount.getUserDiscount()).isEqualTo(DEFAULT_USER_DISCOUNT);
        assertThat(testUserAccount.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testUserAccount.getAccountType()).isEqualTo(DEFAULT_ACCOUNT_TYPE);
        assertThat(testUserAccount.getPerDayChapterLimit()).isEqualTo(DEFAULT_PER_DAY_CHAPTER_LIMIT);
        assertThat(testUserAccount.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testUserAccount.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testUserAccount.getLastUpdatedDate()).isEqualTo(DEFAULT_LAST_UPDATED_DATE);
        assertThat(testUserAccount.getLastUpdatedBy()).isEqualTo(DEFAULT_LAST_UPDATED_BY);
    }

    @Test
    @Transactional
    public void createUserAccountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userAccountRepository.findAll().size();

        // Create the UserAccount with an existing ID
        userAccount.setId(1L);
        UserAccountDTO userAccountDTO = userAccountMapper.toDto(userAccount);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserAccountMockMvc.perform(post("/api/user-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userAccountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserAccount in the database
        List<UserAccount> userAccountList = userAccountRepository.findAll();
        assertThat(userAccountList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkRemainingChaptersIsRequired() throws Exception {
        int databaseSizeBeforeTest = userAccountRepository.findAll().size();
        // set the field null
        userAccount.setRemainingChapters(null);

        // Create the UserAccount, which fails.
        UserAccountDTO userAccountDTO = userAccountMapper.toDto(userAccount);

        restUserAccountMockMvc.perform(post("/api/user-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userAccountDTO)))
            .andExpect(status().isBadRequest());

        List<UserAccount> userAccountList = userAccountRepository.findAll();
        assertThat(userAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCurrPackageStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = userAccountRepository.findAll().size();
        // set the field null
        userAccount.setCurrPackageStartDate(null);

        // Create the UserAccount, which fails.
        UserAccountDTO userAccountDTO = userAccountMapper.toDto(userAccount);

        restUserAccountMockMvc.perform(post("/api/user-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userAccountDTO)))
            .andExpect(status().isBadRequest());

        List<UserAccount> userAccountList = userAccountRepository.findAll();
        assertThat(userAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCurrPackageEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = userAccountRepository.findAll().size();
        // set the field null
        userAccount.setCurrPackageEndDate(null);

        // Create the UserAccount, which fails.
        UserAccountDTO userAccountDTO = userAccountMapper.toDto(userAccount);

        restUserAccountMockMvc.perform(post("/api/user-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userAccountDTO)))
            .andExpect(status().isBadRequest());

        List<UserAccount> userAccountList = userAccountRepository.findAll();
        assertThat(userAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActivatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = userAccountRepository.findAll().size();
        // set the field null
        userAccount.setActivated(null);

        // Create the UserAccount, which fails.
        UserAccountDTO userAccountDTO = userAccountMapper.toDto(userAccount);

        restUserAccountMockMvc.perform(post("/api/user-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userAccountDTO)))
            .andExpect(status().isBadRequest());

        List<UserAccount> userAccountList = userAccountRepository.findAll();
        assertThat(userAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAccountTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = userAccountRepository.findAll().size();
        // set the field null
        userAccount.setAccountType(null);

        // Create the UserAccount, which fails.
        UserAccountDTO userAccountDTO = userAccountMapper.toDto(userAccount);

        restUserAccountMockMvc.perform(post("/api/user-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userAccountDTO)))
            .andExpect(status().isBadRequest());

        List<UserAccount> userAccountList = userAccountRepository.findAll();
        assertThat(userAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPerDayChapterLimitIsRequired() throws Exception {
        int databaseSizeBeforeTest = userAccountRepository.findAll().size();
        // set the field null
        userAccount.setPerDayChapterLimit(null);

        // Create the UserAccount, which fails.
        UserAccountDTO userAccountDTO = userAccountMapper.toDto(userAccount);

        restUserAccountMockMvc.perform(post("/api/user-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userAccountDTO)))
            .andExpect(status().isBadRequest());

        List<UserAccount> userAccountList = userAccountRepository.findAll();
        assertThat(userAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = userAccountRepository.findAll().size();
        // set the field null
        userAccount.setCreatedDate(null);

        // Create the UserAccount, which fails.
        UserAccountDTO userAccountDTO = userAccountMapper.toDto(userAccount);

        restUserAccountMockMvc.perform(post("/api/user-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userAccountDTO)))
            .andExpect(status().isBadRequest());

        List<UserAccount> userAccountList = userAccountRepository.findAll();
        assertThat(userAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = userAccountRepository.findAll().size();
        // set the field null
        userAccount.setCreatedBy(null);

        // Create the UserAccount, which fails.
        UserAccountDTO userAccountDTO = userAccountMapper.toDto(userAccount);

        restUserAccountMockMvc.perform(post("/api/user-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userAccountDTO)))
            .andExpect(status().isBadRequest());

        List<UserAccount> userAccountList = userAccountRepository.findAll();
        assertThat(userAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserAccounts() throws Exception {
        // Initialize the database
        userAccountRepository.saveAndFlush(userAccount);

        // Get all the userAccountList
        restUserAccountMockMvc.perform(get("/api/user-accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].remainingChapters").value(hasItem(DEFAULT_REMAINING_CHAPTERS)))
            .andExpect(jsonPath("$.[*].currPackageStartDate").value(hasItem(DEFAULT_CURR_PACKAGE_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].currPackageEndDate").value(hasItem(DEFAULT_CURR_PACKAGE_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].userDiscount").value(hasItem(DEFAULT_USER_DISCOUNT)))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.toString())))
            .andExpect(jsonPath("$.[*].accountType").value(hasItem(DEFAULT_ACCOUNT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].perDayChapterLimit").value(hasItem(DEFAULT_PER_DAY_CHAPTER_LIMIT)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(DEFAULT_LAST_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastUpdatedBy").value(hasItem(DEFAULT_LAST_UPDATED_BY.toString())));
    }
    
    @Test
    @Transactional
    public void getUserAccount() throws Exception {
        // Initialize the database
        userAccountRepository.saveAndFlush(userAccount);

        // Get the userAccount
        restUserAccountMockMvc.perform(get("/api/user-accounts/{id}", userAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userAccount.getId().intValue()))
            .andExpect(jsonPath("$.remainingChapters").value(DEFAULT_REMAINING_CHAPTERS))
            .andExpect(jsonPath("$.currPackageStartDate").value(DEFAULT_CURR_PACKAGE_START_DATE.toString()))
            .andExpect(jsonPath("$.currPackageEndDate").value(DEFAULT_CURR_PACKAGE_END_DATE.toString()))
            .andExpect(jsonPath("$.userDiscount").value(DEFAULT_USER_DISCOUNT))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.toString()))
            .andExpect(jsonPath("$.accountType").value(DEFAULT_ACCOUNT_TYPE.toString()))
            .andExpect(jsonPath("$.perDayChapterLimit").value(DEFAULT_PER_DAY_CHAPTER_LIMIT))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.lastUpdatedDate").value(DEFAULT_LAST_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.lastUpdatedBy").value(DEFAULT_LAST_UPDATED_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUserAccount() throws Exception {
        // Get the userAccount
        restUserAccountMockMvc.perform(get("/api/user-accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserAccount() throws Exception {
        // Initialize the database
        userAccountRepository.saveAndFlush(userAccount);

        int databaseSizeBeforeUpdate = userAccountRepository.findAll().size();

        // Update the userAccount
        UserAccount updatedUserAccount = userAccountRepository.findById(userAccount.getId()).get();
        // Disconnect from session so that the updates on updatedUserAccount are not directly saved in db
        em.detach(updatedUserAccount);
        updatedUserAccount
            .remainingChapters(UPDATED_REMAINING_CHAPTERS)
            .currPackageStartDate(UPDATED_CURR_PACKAGE_START_DATE)
            .currPackageEndDate(UPDATED_CURR_PACKAGE_END_DATE)
            .userDiscount(UPDATED_USER_DISCOUNT)
            .activated(UPDATED_ACTIVATED)
            .accountType(UPDATED_ACCOUNT_TYPE)
            .perDayChapterLimit(UPDATED_PER_DAY_CHAPTER_LIMIT)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .lastUpdatedBy(UPDATED_LAST_UPDATED_BY);
        UserAccountDTO userAccountDTO = userAccountMapper.toDto(updatedUserAccount);

        restUserAccountMockMvc.perform(put("/api/user-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userAccountDTO)))
            .andExpect(status().isOk());

        // Validate the UserAccount in the database
        List<UserAccount> userAccountList = userAccountRepository.findAll();
        assertThat(userAccountList).hasSize(databaseSizeBeforeUpdate);
        UserAccount testUserAccount = userAccountList.get(userAccountList.size() - 1);
        assertThat(testUserAccount.getRemainingChapters()).isEqualTo(UPDATED_REMAINING_CHAPTERS);
        assertThat(testUserAccount.getCurrPackageStartDate()).isEqualTo(UPDATED_CURR_PACKAGE_START_DATE);
        assertThat(testUserAccount.getCurrPackageEndDate()).isEqualTo(UPDATED_CURR_PACKAGE_END_DATE);
        assertThat(testUserAccount.getUserDiscount()).isEqualTo(UPDATED_USER_DISCOUNT);
        assertThat(testUserAccount.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testUserAccount.getAccountType()).isEqualTo(UPDATED_ACCOUNT_TYPE);
        assertThat(testUserAccount.getPerDayChapterLimit()).isEqualTo(UPDATED_PER_DAY_CHAPTER_LIMIT);
        assertThat(testUserAccount.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testUserAccount.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testUserAccount.getLastUpdatedDate()).isEqualTo(UPDATED_LAST_UPDATED_DATE);
        assertThat(testUserAccount.getLastUpdatedBy()).isEqualTo(UPDATED_LAST_UPDATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingUserAccount() throws Exception {
        int databaseSizeBeforeUpdate = userAccountRepository.findAll().size();

        // Create the UserAccount
        UserAccountDTO userAccountDTO = userAccountMapper.toDto(userAccount);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserAccountMockMvc.perform(put("/api/user-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userAccountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserAccount in the database
        List<UserAccount> userAccountList = userAccountRepository.findAll();
        assertThat(userAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserAccount() throws Exception {
        // Initialize the database
        userAccountRepository.saveAndFlush(userAccount);

        int databaseSizeBeforeDelete = userAccountRepository.findAll().size();

        // Delete the userAccount
        restUserAccountMockMvc.perform(delete("/api/user-accounts/{id}", userAccount.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserAccount> userAccountList = userAccountRepository.findAll();
        assertThat(userAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserAccount.class);
        UserAccount userAccount1 = new UserAccount();
        userAccount1.setId(1L);
        UserAccount userAccount2 = new UserAccount();
        userAccount2.setId(userAccount1.getId());
        assertThat(userAccount1).isEqualTo(userAccount2);
        userAccount2.setId(2L);
        assertThat(userAccount1).isNotEqualTo(userAccount2);
        userAccount1.setId(null);
        assertThat(userAccount1).isNotEqualTo(userAccount2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserAccountDTO.class);
        UserAccountDTO userAccountDTO1 = new UserAccountDTO();
        userAccountDTO1.setId(1L);
        UserAccountDTO userAccountDTO2 = new UserAccountDTO();
        assertThat(userAccountDTO1).isNotEqualTo(userAccountDTO2);
        userAccountDTO2.setId(userAccountDTO1.getId());
        assertThat(userAccountDTO1).isEqualTo(userAccountDTO2);
        userAccountDTO2.setId(2L);
        assertThat(userAccountDTO1).isNotEqualTo(userAccountDTO2);
        userAccountDTO1.setId(null);
        assertThat(userAccountDTO1).isNotEqualTo(userAccountDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(userAccountMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(userAccountMapper.fromId(null)).isNull();
    }
}
