package com.apps.idb.web.rest;

import com.apps.idb.IdbApp;

import com.apps.idb.domain.Packages;
import com.apps.idb.repository.PackagesRepository;
import com.apps.idb.service.PackagesService;
import com.apps.idb.service.dto.PackagesDTO;
import com.apps.idb.service.mapper.PackagesMapper;
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
 * Test class for the PackagesResource REST controller.
 *
 * @see PackagesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IdbApp.class)
public class PackagesResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final Integer DEFAULT_PRICE = 1;
    private static final Integer UPDATED_PRICE = 2;

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_EXPIRY = 1;
    private static final Integer UPDATED_EXPIRY = 2;

    private static final Integer DEFAULT_TOTAL_CHAPTERS = 1;
    private static final Integer UPDATED_TOTAL_CHAPTERS = 2;

    private static final Integer DEFAULT_DISCOUNT = 1;
    private static final Integer UPDATED_DISCOUNT = 2;

    private static final String DEFAULT_ACTIVATED = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVATED = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_LAST_UPDATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_LAST_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_UPDATED_BY = "BBBBBBBBBB";

    @Autowired
    private PackagesRepository packagesRepository;

    @Autowired
    private PackagesMapper packagesMapper;

    @Autowired
    private PackagesService packagesService;

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

    private MockMvc restPackagesMockMvc;

    private Packages packages;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PackagesResource packagesResource = new PackagesResource(packagesService);
        this.restPackagesMockMvc = MockMvcBuilders.standaloneSetup(packagesResource)
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
    public static Packages createEntity(EntityManager em) {
        Packages packages = new Packages()
            .title(DEFAULT_TITLE)
            .price(DEFAULT_PRICE)
            .type(DEFAULT_TYPE)
            .expiry(DEFAULT_EXPIRY)
            .totalChapters(DEFAULT_TOTAL_CHAPTERS)
            .discount(DEFAULT_DISCOUNT)
            .activated(DEFAULT_ACTIVATED)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .lastUpdatedDate(DEFAULT_LAST_UPDATED_DATE)
            .lastUpdatedBy(DEFAULT_LAST_UPDATED_BY);
        return packages;
    }

    @Before
    public void initTest() {
        packages = createEntity(em);
    }

    @Test
    @Transactional
    public void createPackages() throws Exception {
        int databaseSizeBeforeCreate = packagesRepository.findAll().size();

        // Create the Packages
        PackagesDTO packagesDTO = packagesMapper.toDto(packages);
        restPackagesMockMvc.perform(post("/api/packages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(packagesDTO)))
            .andExpect(status().isCreated());

        // Validate the Packages in the database
        List<Packages> packagesList = packagesRepository.findAll();
        assertThat(packagesList).hasSize(databaseSizeBeforeCreate + 1);
        Packages testPackages = packagesList.get(packagesList.size() - 1);
        assertThat(testPackages.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testPackages.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testPackages.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testPackages.getExpiry()).isEqualTo(DEFAULT_EXPIRY);
        assertThat(testPackages.getTotalChapters()).isEqualTo(DEFAULT_TOTAL_CHAPTERS);
        assertThat(testPackages.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);
        assertThat(testPackages.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testPackages.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPackages.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPackages.getLastUpdatedDate()).isEqualTo(DEFAULT_LAST_UPDATED_DATE);
        assertThat(testPackages.getLastUpdatedBy()).isEqualTo(DEFAULT_LAST_UPDATED_BY);
    }

    @Test
    @Transactional
    public void createPackagesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = packagesRepository.findAll().size();

        // Create the Packages with an existing ID
        packages.setId(1L);
        PackagesDTO packagesDTO = packagesMapper.toDto(packages);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPackagesMockMvc.perform(post("/api/packages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(packagesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Packages in the database
        List<Packages> packagesList = packagesRepository.findAll();
        assertThat(packagesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = packagesRepository.findAll().size();
        // set the field null
        packages.setTitle(null);

        // Create the Packages, which fails.
        PackagesDTO packagesDTO = packagesMapper.toDto(packages);

        restPackagesMockMvc.perform(post("/api/packages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(packagesDTO)))
            .andExpect(status().isBadRequest());

        List<Packages> packagesList = packagesRepository.findAll();
        assertThat(packagesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = packagesRepository.findAll().size();
        // set the field null
        packages.setPrice(null);

        // Create the Packages, which fails.
        PackagesDTO packagesDTO = packagesMapper.toDto(packages);

        restPackagesMockMvc.perform(post("/api/packages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(packagesDTO)))
            .andExpect(status().isBadRequest());

        List<Packages> packagesList = packagesRepository.findAll();
        assertThat(packagesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = packagesRepository.findAll().size();
        // set the field null
        packages.setType(null);

        // Create the Packages, which fails.
        PackagesDTO packagesDTO = packagesMapper.toDto(packages);

        restPackagesMockMvc.perform(post("/api/packages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(packagesDTO)))
            .andExpect(status().isBadRequest());

        List<Packages> packagesList = packagesRepository.findAll();
        assertThat(packagesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkExpiryIsRequired() throws Exception {
        int databaseSizeBeforeTest = packagesRepository.findAll().size();
        // set the field null
        packages.setExpiry(null);

        // Create the Packages, which fails.
        PackagesDTO packagesDTO = packagesMapper.toDto(packages);

        restPackagesMockMvc.perform(post("/api/packages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(packagesDTO)))
            .andExpect(status().isBadRequest());

        List<Packages> packagesList = packagesRepository.findAll();
        assertThat(packagesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalChaptersIsRequired() throws Exception {
        int databaseSizeBeforeTest = packagesRepository.findAll().size();
        // set the field null
        packages.setTotalChapters(null);

        // Create the Packages, which fails.
        PackagesDTO packagesDTO = packagesMapper.toDto(packages);

        restPackagesMockMvc.perform(post("/api/packages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(packagesDTO)))
            .andExpect(status().isBadRequest());

        List<Packages> packagesList = packagesRepository.findAll();
        assertThat(packagesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActivatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = packagesRepository.findAll().size();
        // set the field null
        packages.setActivated(null);

        // Create the Packages, which fails.
        PackagesDTO packagesDTO = packagesMapper.toDto(packages);

        restPackagesMockMvc.perform(post("/api/packages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(packagesDTO)))
            .andExpect(status().isBadRequest());

        List<Packages> packagesList = packagesRepository.findAll();
        assertThat(packagesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = packagesRepository.findAll().size();
        // set the field null
        packages.setCreatedDate(null);

        // Create the Packages, which fails.
        PackagesDTO packagesDTO = packagesMapper.toDto(packages);

        restPackagesMockMvc.perform(post("/api/packages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(packagesDTO)))
            .andExpect(status().isBadRequest());

        List<Packages> packagesList = packagesRepository.findAll();
        assertThat(packagesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = packagesRepository.findAll().size();
        // set the field null
        packages.setCreatedBy(null);

        // Create the Packages, which fails.
        PackagesDTO packagesDTO = packagesMapper.toDto(packages);

        restPackagesMockMvc.perform(post("/api/packages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(packagesDTO)))
            .andExpect(status().isBadRequest());

        List<Packages> packagesList = packagesRepository.findAll();
        assertThat(packagesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPackages() throws Exception {
        // Initialize the database
        packagesRepository.saveAndFlush(packages);

        // Get all the packagesList
        restPackagesMockMvc.perform(get("/api/packages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(packages.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].expiry").value(hasItem(DEFAULT_EXPIRY)))
            .andExpect(jsonPath("$.[*].totalChapters").value(hasItem(DEFAULT_TOTAL_CHAPTERS)))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT)))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(DEFAULT_LAST_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastUpdatedBy").value(hasItem(DEFAULT_LAST_UPDATED_BY.toString())));
    }
    
    @Test
    @Transactional
    public void getPackages() throws Exception {
        // Initialize the database
        packagesRepository.saveAndFlush(packages);

        // Get the packages
        restPackagesMockMvc.perform(get("/api/packages/{id}", packages.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(packages.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.expiry").value(DEFAULT_EXPIRY))
            .andExpect(jsonPath("$.totalChapters").value(DEFAULT_TOTAL_CHAPTERS))
            .andExpect(jsonPath("$.discount").value(DEFAULT_DISCOUNT))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.lastUpdatedDate").value(DEFAULT_LAST_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.lastUpdatedBy").value(DEFAULT_LAST_UPDATED_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPackages() throws Exception {
        // Get the packages
        restPackagesMockMvc.perform(get("/api/packages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePackages() throws Exception {
        // Initialize the database
        packagesRepository.saveAndFlush(packages);

        int databaseSizeBeforeUpdate = packagesRepository.findAll().size();

        // Update the packages
        Packages updatedPackages = packagesRepository.findById(packages.getId()).get();
        // Disconnect from session so that the updates on updatedPackages are not directly saved in db
        em.detach(updatedPackages);
        updatedPackages
            .title(UPDATED_TITLE)
            .price(UPDATED_PRICE)
            .type(UPDATED_TYPE)
            .expiry(UPDATED_EXPIRY)
            .totalChapters(UPDATED_TOTAL_CHAPTERS)
            .discount(UPDATED_DISCOUNT)
            .activated(UPDATED_ACTIVATED)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .lastUpdatedBy(UPDATED_LAST_UPDATED_BY);
        PackagesDTO packagesDTO = packagesMapper.toDto(updatedPackages);

        restPackagesMockMvc.perform(put("/api/packages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(packagesDTO)))
            .andExpect(status().isOk());

        // Validate the Packages in the database
        List<Packages> packagesList = packagesRepository.findAll();
        assertThat(packagesList).hasSize(databaseSizeBeforeUpdate);
        Packages testPackages = packagesList.get(packagesList.size() - 1);
        assertThat(testPackages.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testPackages.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testPackages.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testPackages.getExpiry()).isEqualTo(UPDATED_EXPIRY);
        assertThat(testPackages.getTotalChapters()).isEqualTo(UPDATED_TOTAL_CHAPTERS);
        assertThat(testPackages.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
        assertThat(testPackages.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testPackages.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPackages.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPackages.getLastUpdatedDate()).isEqualTo(UPDATED_LAST_UPDATED_DATE);
        assertThat(testPackages.getLastUpdatedBy()).isEqualTo(UPDATED_LAST_UPDATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingPackages() throws Exception {
        int databaseSizeBeforeUpdate = packagesRepository.findAll().size();

        // Create the Packages
        PackagesDTO packagesDTO = packagesMapper.toDto(packages);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPackagesMockMvc.perform(put("/api/packages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(packagesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Packages in the database
        List<Packages> packagesList = packagesRepository.findAll();
        assertThat(packagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePackages() throws Exception {
        // Initialize the database
        packagesRepository.saveAndFlush(packages);

        int databaseSizeBeforeDelete = packagesRepository.findAll().size();

        // Delete the packages
        restPackagesMockMvc.perform(delete("/api/packages/{id}", packages.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Packages> packagesList = packagesRepository.findAll();
        assertThat(packagesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Packages.class);
        Packages packages1 = new Packages();
        packages1.setId(1L);
        Packages packages2 = new Packages();
        packages2.setId(packages1.getId());
        assertThat(packages1).isEqualTo(packages2);
        packages2.setId(2L);
        assertThat(packages1).isNotEqualTo(packages2);
        packages1.setId(null);
        assertThat(packages1).isNotEqualTo(packages2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PackagesDTO.class);
        PackagesDTO packagesDTO1 = new PackagesDTO();
        packagesDTO1.setId(1L);
        PackagesDTO packagesDTO2 = new PackagesDTO();
        assertThat(packagesDTO1).isNotEqualTo(packagesDTO2);
        packagesDTO2.setId(packagesDTO1.getId());
        assertThat(packagesDTO1).isEqualTo(packagesDTO2);
        packagesDTO2.setId(2L);
        assertThat(packagesDTO1).isNotEqualTo(packagesDTO2);
        packagesDTO1.setId(null);
        assertThat(packagesDTO1).isNotEqualTo(packagesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(packagesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(packagesMapper.fromId(null)).isNull();
    }
}
