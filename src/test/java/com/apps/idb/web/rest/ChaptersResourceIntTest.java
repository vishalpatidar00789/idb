package com.apps.idb.web.rest;

import com.apps.idb.IdbApp;

import com.apps.idb.domain.Chapters;
import com.apps.idb.repository.ChaptersRepository;
import com.apps.idb.service.ChaptersService;
import com.apps.idb.service.dto.ChaptersDTO;
import com.apps.idb.service.mapper.ChaptersMapper;
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

import com.apps.idb.domain.enumeration.ChapterStatus;
/**
 * Test class for the ChaptersResource REST controller.
 *
 * @see ChaptersResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IdbApp.class)
public class ChaptersResourceIntTest {

    private static final ChapterStatus DEFAULT_STATUS = ChapterStatus.Open;
    private static final ChapterStatus UPDATED_STATUS = ChapterStatus.Accepted;

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
    private ChaptersRepository chaptersRepository;

    @Autowired
    private ChaptersMapper chaptersMapper;

    @Autowired
    private ChaptersService chaptersService;

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

    private MockMvc restChaptersMockMvc;

    private Chapters chapters;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ChaptersResource chaptersResource = new ChaptersResource(chaptersService);
        this.restChaptersMockMvc = MockMvcBuilders.standaloneSetup(chaptersResource)
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
    public static Chapters createEntity(EntityManager em) {
        Chapters chapters = new Chapters()
            .status(DEFAULT_STATUS)
            .activated(DEFAULT_ACTIVATED)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .lastUpdatedDate(DEFAULT_LAST_UPDATED_DATE)
            .lastUpdatedBy(DEFAULT_LAST_UPDATED_BY);
        return chapters;
    }

    @Before
    public void initTest() {
        chapters = createEntity(em);
    }

    @Test
    @Transactional
    public void createChapters() throws Exception {
        int databaseSizeBeforeCreate = chaptersRepository.findAll().size();

        // Create the Chapters
        ChaptersDTO chaptersDTO = chaptersMapper.toDto(chapters);
        restChaptersMockMvc.perform(post("/api/chapters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chaptersDTO)))
            .andExpect(status().isCreated());

        // Validate the Chapters in the database
        List<Chapters> chaptersList = chaptersRepository.findAll();
        assertThat(chaptersList).hasSize(databaseSizeBeforeCreate + 1);
        Chapters testChapters = chaptersList.get(chaptersList.size() - 1);
        assertThat(testChapters.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testChapters.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testChapters.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testChapters.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testChapters.getLastUpdatedDate()).isEqualTo(DEFAULT_LAST_UPDATED_DATE);
        assertThat(testChapters.getLastUpdatedBy()).isEqualTo(DEFAULT_LAST_UPDATED_BY);
    }

    @Test
    @Transactional
    public void createChaptersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = chaptersRepository.findAll().size();

        // Create the Chapters with an existing ID
        chapters.setId(1L);
        ChaptersDTO chaptersDTO = chaptersMapper.toDto(chapters);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChaptersMockMvc.perform(post("/api/chapters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chaptersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Chapters in the database
        List<Chapters> chaptersList = chaptersRepository.findAll();
        assertThat(chaptersList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkActivatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = chaptersRepository.findAll().size();
        // set the field null
        chapters.setActivated(null);

        // Create the Chapters, which fails.
        ChaptersDTO chaptersDTO = chaptersMapper.toDto(chapters);

        restChaptersMockMvc.perform(post("/api/chapters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chaptersDTO)))
            .andExpect(status().isBadRequest());

        List<Chapters> chaptersList = chaptersRepository.findAll();
        assertThat(chaptersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = chaptersRepository.findAll().size();
        // set the field null
        chapters.setCreatedDate(null);

        // Create the Chapters, which fails.
        ChaptersDTO chaptersDTO = chaptersMapper.toDto(chapters);

        restChaptersMockMvc.perform(post("/api/chapters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chaptersDTO)))
            .andExpect(status().isBadRequest());

        List<Chapters> chaptersList = chaptersRepository.findAll();
        assertThat(chaptersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = chaptersRepository.findAll().size();
        // set the field null
        chapters.setCreatedBy(null);

        // Create the Chapters, which fails.
        ChaptersDTO chaptersDTO = chaptersMapper.toDto(chapters);

        restChaptersMockMvc.perform(post("/api/chapters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chaptersDTO)))
            .andExpect(status().isBadRequest());

        List<Chapters> chaptersList = chaptersRepository.findAll();
        assertThat(chaptersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllChapters() throws Exception {
        // Initialize the database
        chaptersRepository.saveAndFlush(chapters);

        // Get all the chaptersList
        restChaptersMockMvc.perform(get("/api/chapters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chapters.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(DEFAULT_LAST_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastUpdatedBy").value(hasItem(DEFAULT_LAST_UPDATED_BY.toString())));
    }
    
    @Test
    @Transactional
    public void getChapters() throws Exception {
        // Initialize the database
        chaptersRepository.saveAndFlush(chapters);

        // Get the chapters
        restChaptersMockMvc.perform(get("/api/chapters/{id}", chapters.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(chapters.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.lastUpdatedDate").value(DEFAULT_LAST_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.lastUpdatedBy").value(DEFAULT_LAST_UPDATED_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingChapters() throws Exception {
        // Get the chapters
        restChaptersMockMvc.perform(get("/api/chapters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChapters() throws Exception {
        // Initialize the database
        chaptersRepository.saveAndFlush(chapters);

        int databaseSizeBeforeUpdate = chaptersRepository.findAll().size();

        // Update the chapters
        Chapters updatedChapters = chaptersRepository.findById(chapters.getId()).get();
        // Disconnect from session so that the updates on updatedChapters are not directly saved in db
        em.detach(updatedChapters);
        updatedChapters
            .status(UPDATED_STATUS)
            .activated(UPDATED_ACTIVATED)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .lastUpdatedBy(UPDATED_LAST_UPDATED_BY);
        ChaptersDTO chaptersDTO = chaptersMapper.toDto(updatedChapters);

        restChaptersMockMvc.perform(put("/api/chapters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chaptersDTO)))
            .andExpect(status().isOk());

        // Validate the Chapters in the database
        List<Chapters> chaptersList = chaptersRepository.findAll();
        assertThat(chaptersList).hasSize(databaseSizeBeforeUpdate);
        Chapters testChapters = chaptersList.get(chaptersList.size() - 1);
        assertThat(testChapters.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testChapters.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testChapters.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testChapters.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testChapters.getLastUpdatedDate()).isEqualTo(UPDATED_LAST_UPDATED_DATE);
        assertThat(testChapters.getLastUpdatedBy()).isEqualTo(UPDATED_LAST_UPDATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingChapters() throws Exception {
        int databaseSizeBeforeUpdate = chaptersRepository.findAll().size();

        // Create the Chapters
        ChaptersDTO chaptersDTO = chaptersMapper.toDto(chapters);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChaptersMockMvc.perform(put("/api/chapters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chaptersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Chapters in the database
        List<Chapters> chaptersList = chaptersRepository.findAll();
        assertThat(chaptersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteChapters() throws Exception {
        // Initialize the database
        chaptersRepository.saveAndFlush(chapters);

        int databaseSizeBeforeDelete = chaptersRepository.findAll().size();

        // Delete the chapters
        restChaptersMockMvc.perform(delete("/api/chapters/{id}", chapters.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Chapters> chaptersList = chaptersRepository.findAll();
        assertThat(chaptersList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Chapters.class);
        Chapters chapters1 = new Chapters();
        chapters1.setId(1L);
        Chapters chapters2 = new Chapters();
        chapters2.setId(chapters1.getId());
        assertThat(chapters1).isEqualTo(chapters2);
        chapters2.setId(2L);
        assertThat(chapters1).isNotEqualTo(chapters2);
        chapters1.setId(null);
        assertThat(chapters1).isNotEqualTo(chapters2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChaptersDTO.class);
        ChaptersDTO chaptersDTO1 = new ChaptersDTO();
        chaptersDTO1.setId(1L);
        ChaptersDTO chaptersDTO2 = new ChaptersDTO();
        assertThat(chaptersDTO1).isNotEqualTo(chaptersDTO2);
        chaptersDTO2.setId(chaptersDTO1.getId());
        assertThat(chaptersDTO1).isEqualTo(chaptersDTO2);
        chaptersDTO2.setId(2L);
        assertThat(chaptersDTO1).isNotEqualTo(chaptersDTO2);
        chaptersDTO1.setId(null);
        assertThat(chaptersDTO1).isNotEqualTo(chaptersDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(chaptersMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(chaptersMapper.fromId(null)).isNull();
    }
}
