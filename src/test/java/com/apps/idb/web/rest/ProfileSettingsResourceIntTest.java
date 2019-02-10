package com.apps.idb.web.rest;

import com.apps.idb.IdbApp;

import com.apps.idb.domain.ProfileSettings;
import com.apps.idb.repository.ProfileSettingsRepository;
import com.apps.idb.service.ProfileSettingsService;
import com.apps.idb.service.dto.ProfileSettingsDTO;
import com.apps.idb.service.mapper.ProfileSettingsMapper;
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

/**
 * Test class for the ProfileSettingsResource REST controller.
 *
 * @see ProfileSettingsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IdbApp.class)
public class ProfileSettingsResourceIntTest {

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_PUBLIC = false;
    private static final Boolean UPDATED_IS_PUBLIC = true;

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_LAST_UPDATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_LAST_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_UPDATED_BY = "BBBBBBBBBB";

    @Autowired
    private ProfileSettingsRepository profileSettingsRepository;

    @Autowired
    private ProfileSettingsMapper profileSettingsMapper;

    @Autowired
    private ProfileSettingsService profileSettingsService;

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

    private MockMvc restProfileSettingsMockMvc;

    private ProfileSettings profileSettings;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProfileSettingsResource profileSettingsResource = new ProfileSettingsResource(profileSettingsService);
        this.restProfileSettingsMockMvc = MockMvcBuilders.standaloneSetup(profileSettingsResource)
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
    public static ProfileSettings createEntity(EntityManager em) {
        ProfileSettings profileSettings = new ProfileSettings()
            .key(DEFAULT_KEY)
            .isPublic(DEFAULT_IS_PUBLIC)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .lastUpdatedDate(DEFAULT_LAST_UPDATED_DATE)
            .lastUpdatedBy(DEFAULT_LAST_UPDATED_BY);
        return profileSettings;
    }

    @Before
    public void initTest() {
        profileSettings = createEntity(em);
    }

    @Test
    @Transactional
    public void createProfileSettings() throws Exception {
        int databaseSizeBeforeCreate = profileSettingsRepository.findAll().size();

        // Create the ProfileSettings
        ProfileSettingsDTO profileSettingsDTO = profileSettingsMapper.toDto(profileSettings);
        restProfileSettingsMockMvc.perform(post("/api/profile-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileSettingsDTO)))
            .andExpect(status().isCreated());

        // Validate the ProfileSettings in the database
        List<ProfileSettings> profileSettingsList = profileSettingsRepository.findAll();
        assertThat(profileSettingsList).hasSize(databaseSizeBeforeCreate + 1);
        ProfileSettings testProfileSettings = profileSettingsList.get(profileSettingsList.size() - 1);
        assertThat(testProfileSettings.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testProfileSettings.isIsPublic()).isEqualTo(DEFAULT_IS_PUBLIC);
        assertThat(testProfileSettings.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProfileSettings.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProfileSettings.getLastUpdatedDate()).isEqualTo(DEFAULT_LAST_UPDATED_DATE);
        assertThat(testProfileSettings.getLastUpdatedBy()).isEqualTo(DEFAULT_LAST_UPDATED_BY);
    }

    @Test
    @Transactional
    public void createProfileSettingsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = profileSettingsRepository.findAll().size();

        // Create the ProfileSettings with an existing ID
        profileSettings.setId(1L);
        ProfileSettingsDTO profileSettingsDTO = profileSettingsMapper.toDto(profileSettings);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfileSettingsMockMvc.perform(post("/api/profile-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileSettingsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProfileSettings in the database
        List<ProfileSettings> profileSettingsList = profileSettingsRepository.findAll();
        assertThat(profileSettingsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = profileSettingsRepository.findAll().size();
        // set the field null
        profileSettings.setKey(null);

        // Create the ProfileSettings, which fails.
        ProfileSettingsDTO profileSettingsDTO = profileSettingsMapper.toDto(profileSettings);

        restProfileSettingsMockMvc.perform(post("/api/profile-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileSettingsDTO)))
            .andExpect(status().isBadRequest());

        List<ProfileSettings> profileSettingsList = profileSettingsRepository.findAll();
        assertThat(profileSettingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsPublicIsRequired() throws Exception {
        int databaseSizeBeforeTest = profileSettingsRepository.findAll().size();
        // set the field null
        profileSettings.setIsPublic(null);

        // Create the ProfileSettings, which fails.
        ProfileSettingsDTO profileSettingsDTO = profileSettingsMapper.toDto(profileSettings);

        restProfileSettingsMockMvc.perform(post("/api/profile-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileSettingsDTO)))
            .andExpect(status().isBadRequest());

        List<ProfileSettings> profileSettingsList = profileSettingsRepository.findAll();
        assertThat(profileSettingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = profileSettingsRepository.findAll().size();
        // set the field null
        profileSettings.setCreatedDate(null);

        // Create the ProfileSettings, which fails.
        ProfileSettingsDTO profileSettingsDTO = profileSettingsMapper.toDto(profileSettings);

        restProfileSettingsMockMvc.perform(post("/api/profile-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileSettingsDTO)))
            .andExpect(status().isBadRequest());

        List<ProfileSettings> profileSettingsList = profileSettingsRepository.findAll();
        assertThat(profileSettingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = profileSettingsRepository.findAll().size();
        // set the field null
        profileSettings.setCreatedBy(null);

        // Create the ProfileSettings, which fails.
        ProfileSettingsDTO profileSettingsDTO = profileSettingsMapper.toDto(profileSettings);

        restProfileSettingsMockMvc.perform(post("/api/profile-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileSettingsDTO)))
            .andExpect(status().isBadRequest());

        List<ProfileSettings> profileSettingsList = profileSettingsRepository.findAll();
        assertThat(profileSettingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProfileSettings() throws Exception {
        // Initialize the database
        profileSettingsRepository.saveAndFlush(profileSettings);

        // Get all the profileSettingsList
        restProfileSettingsMockMvc.perform(get("/api/profile-settings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profileSettings.getId().intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY.toString())))
            .andExpect(jsonPath("$.[*].isPublic").value(hasItem(DEFAULT_IS_PUBLIC.booleanValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(DEFAULT_LAST_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastUpdatedBy").value(hasItem(DEFAULT_LAST_UPDATED_BY.toString())));
    }
    
    @Test
    @Transactional
    public void getProfileSettings() throws Exception {
        // Initialize the database
        profileSettingsRepository.saveAndFlush(profileSettings);

        // Get the profileSettings
        restProfileSettingsMockMvc.perform(get("/api/profile-settings/{id}", profileSettings.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(profileSettings.getId().intValue()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY.toString()))
            .andExpect(jsonPath("$.isPublic").value(DEFAULT_IS_PUBLIC.booleanValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.lastUpdatedDate").value(DEFAULT_LAST_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.lastUpdatedBy").value(DEFAULT_LAST_UPDATED_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProfileSettings() throws Exception {
        // Get the profileSettings
        restProfileSettingsMockMvc.perform(get("/api/profile-settings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProfileSettings() throws Exception {
        // Initialize the database
        profileSettingsRepository.saveAndFlush(profileSettings);

        int databaseSizeBeforeUpdate = profileSettingsRepository.findAll().size();

        // Update the profileSettings
        ProfileSettings updatedProfileSettings = profileSettingsRepository.findById(profileSettings.getId()).get();
        // Disconnect from session so that the updates on updatedProfileSettings are not directly saved in db
        em.detach(updatedProfileSettings);
        updatedProfileSettings
            .key(UPDATED_KEY)
            .isPublic(UPDATED_IS_PUBLIC)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .lastUpdatedBy(UPDATED_LAST_UPDATED_BY);
        ProfileSettingsDTO profileSettingsDTO = profileSettingsMapper.toDto(updatedProfileSettings);

        restProfileSettingsMockMvc.perform(put("/api/profile-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileSettingsDTO)))
            .andExpect(status().isOk());

        // Validate the ProfileSettings in the database
        List<ProfileSettings> profileSettingsList = profileSettingsRepository.findAll();
        assertThat(profileSettingsList).hasSize(databaseSizeBeforeUpdate);
        ProfileSettings testProfileSettings = profileSettingsList.get(profileSettingsList.size() - 1);
        assertThat(testProfileSettings.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testProfileSettings.isIsPublic()).isEqualTo(UPDATED_IS_PUBLIC);
        assertThat(testProfileSettings.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProfileSettings.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProfileSettings.getLastUpdatedDate()).isEqualTo(UPDATED_LAST_UPDATED_DATE);
        assertThat(testProfileSettings.getLastUpdatedBy()).isEqualTo(UPDATED_LAST_UPDATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingProfileSettings() throws Exception {
        int databaseSizeBeforeUpdate = profileSettingsRepository.findAll().size();

        // Create the ProfileSettings
        ProfileSettingsDTO profileSettingsDTO = profileSettingsMapper.toDto(profileSettings);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfileSettingsMockMvc.perform(put("/api/profile-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileSettingsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProfileSettings in the database
        List<ProfileSettings> profileSettingsList = profileSettingsRepository.findAll();
        assertThat(profileSettingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProfileSettings() throws Exception {
        // Initialize the database
        profileSettingsRepository.saveAndFlush(profileSettings);

        int databaseSizeBeforeDelete = profileSettingsRepository.findAll().size();

        // Delete the profileSettings
        restProfileSettingsMockMvc.perform(delete("/api/profile-settings/{id}", profileSettings.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProfileSettings> profileSettingsList = profileSettingsRepository.findAll();
        assertThat(profileSettingsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProfileSettings.class);
        ProfileSettings profileSettings1 = new ProfileSettings();
        profileSettings1.setId(1L);
        ProfileSettings profileSettings2 = new ProfileSettings();
        profileSettings2.setId(profileSettings1.getId());
        assertThat(profileSettings1).isEqualTo(profileSettings2);
        profileSettings2.setId(2L);
        assertThat(profileSettings1).isNotEqualTo(profileSettings2);
        profileSettings1.setId(null);
        assertThat(profileSettings1).isNotEqualTo(profileSettings2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProfileSettingsDTO.class);
        ProfileSettingsDTO profileSettingsDTO1 = new ProfileSettingsDTO();
        profileSettingsDTO1.setId(1L);
        ProfileSettingsDTO profileSettingsDTO2 = new ProfileSettingsDTO();
        assertThat(profileSettingsDTO1).isNotEqualTo(profileSettingsDTO2);
        profileSettingsDTO2.setId(profileSettingsDTO1.getId());
        assertThat(profileSettingsDTO1).isEqualTo(profileSettingsDTO2);
        profileSettingsDTO2.setId(2L);
        assertThat(profileSettingsDTO1).isNotEqualTo(profileSettingsDTO2);
        profileSettingsDTO1.setId(null);
        assertThat(profileSettingsDTO1).isNotEqualTo(profileSettingsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(profileSettingsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(profileSettingsMapper.fromId(null)).isNull();
    }
}
