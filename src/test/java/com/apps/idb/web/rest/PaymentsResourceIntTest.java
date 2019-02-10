package com.apps.idb.web.rest;

import com.apps.idb.IdbApp;

import com.apps.idb.domain.Payments;
import com.apps.idb.repository.PaymentsRepository;
import com.apps.idb.service.PaymentsService;
import com.apps.idb.service.dto.PaymentsDTO;
import com.apps.idb.service.mapper.PaymentsMapper;
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

import com.apps.idb.domain.enumeration.PaymentStatus;
/**
 * Test class for the PaymentsResource REST controller.
 *
 * @see PaymentsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IdbApp.class)
public class PaymentsResourceIntTest {

    private static final String DEFAULT_VENDOR = "AAAAAAAAAA";
    private static final String UPDATED_VENDOR = "BBBBBBBBBB";

    private static final PaymentStatus DEFAULT_STATUS = PaymentStatus.Paid;
    private static final PaymentStatus UPDATED_STATUS = PaymentStatus.Failed;

    private static final Integer DEFAULT_PAYMENT_VALUE = 1;
    private static final Integer UPDATED_PAYMENT_VALUE = 2;

    private static final LocalDate DEFAULT_INITIATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_INITIATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_CONFIRM_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CONFIRM_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_ACTIVATED = false;
    private static final Boolean UPDATED_ACTIVATED = true;

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_LAST_UPDATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_LAST_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_UPDATED_BY = "BBBBBBBBBB";

    @Autowired
    private PaymentsRepository paymentsRepository;

    @Autowired
    private PaymentsMapper paymentsMapper;

    @Autowired
    private PaymentsService paymentsService;

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

    private MockMvc restPaymentsMockMvc;

    private Payments payments;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PaymentsResource paymentsResource = new PaymentsResource(paymentsService);
        this.restPaymentsMockMvc = MockMvcBuilders.standaloneSetup(paymentsResource)
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
    public static Payments createEntity(EntityManager em) {
        Payments payments = new Payments()
            .vendor(DEFAULT_VENDOR)
            .status(DEFAULT_STATUS)
            .paymentValue(DEFAULT_PAYMENT_VALUE)
            .initiatedDate(DEFAULT_INITIATED_DATE)
            .confirmDate(DEFAULT_CONFIRM_DATE)
            .activated(DEFAULT_ACTIVATED)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .lastUpdatedDate(DEFAULT_LAST_UPDATED_DATE)
            .lastUpdatedBy(DEFAULT_LAST_UPDATED_BY);
        return payments;
    }

    @Before
    public void initTest() {
        payments = createEntity(em);
    }

    @Test
    @Transactional
    public void createPayments() throws Exception {
        int databaseSizeBeforeCreate = paymentsRepository.findAll().size();

        // Create the Payments
        PaymentsDTO paymentsDTO = paymentsMapper.toDto(payments);
        restPaymentsMockMvc.perform(post("/api/payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentsDTO)))
            .andExpect(status().isCreated());

        // Validate the Payments in the database
        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeCreate + 1);
        Payments testPayments = paymentsList.get(paymentsList.size() - 1);
        assertThat(testPayments.getVendor()).isEqualTo(DEFAULT_VENDOR);
        assertThat(testPayments.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPayments.getPaymentValue()).isEqualTo(DEFAULT_PAYMENT_VALUE);
        assertThat(testPayments.getInitiatedDate()).isEqualTo(DEFAULT_INITIATED_DATE);
        assertThat(testPayments.getConfirmDate()).isEqualTo(DEFAULT_CONFIRM_DATE);
        assertThat(testPayments.isActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testPayments.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPayments.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPayments.getLastUpdatedDate()).isEqualTo(DEFAULT_LAST_UPDATED_DATE);
        assertThat(testPayments.getLastUpdatedBy()).isEqualTo(DEFAULT_LAST_UPDATED_BY);
    }

    @Test
    @Transactional
    public void createPaymentsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paymentsRepository.findAll().size();

        // Create the Payments with an existing ID
        payments.setId(1L);
        PaymentsDTO paymentsDTO = paymentsMapper.toDto(payments);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentsMockMvc.perform(post("/api/payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Payments in the database
        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkVendorIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentsRepository.findAll().size();
        // set the field null
        payments.setVendor(null);

        // Create the Payments, which fails.
        PaymentsDTO paymentsDTO = paymentsMapper.toDto(payments);

        restPaymentsMockMvc.perform(post("/api/payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentsDTO)))
            .andExpect(status().isBadRequest());

        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentsRepository.findAll().size();
        // set the field null
        payments.setStatus(null);

        // Create the Payments, which fails.
        PaymentsDTO paymentsDTO = paymentsMapper.toDto(payments);

        restPaymentsMockMvc.perform(post("/api/payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentsDTO)))
            .andExpect(status().isBadRequest());

        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPaymentValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentsRepository.findAll().size();
        // set the field null
        payments.setPaymentValue(null);

        // Create the Payments, which fails.
        PaymentsDTO paymentsDTO = paymentsMapper.toDto(payments);

        restPaymentsMockMvc.perform(post("/api/payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentsDTO)))
            .andExpect(status().isBadRequest());

        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActivatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentsRepository.findAll().size();
        // set the field null
        payments.setActivated(null);

        // Create the Payments, which fails.
        PaymentsDTO paymentsDTO = paymentsMapper.toDto(payments);

        restPaymentsMockMvc.perform(post("/api/payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentsDTO)))
            .andExpect(status().isBadRequest());

        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentsRepository.findAll().size();
        // set the field null
        payments.setCreatedDate(null);

        // Create the Payments, which fails.
        PaymentsDTO paymentsDTO = paymentsMapper.toDto(payments);

        restPaymentsMockMvc.perform(post("/api/payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentsDTO)))
            .andExpect(status().isBadRequest());

        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentsRepository.findAll().size();
        // set the field null
        payments.setCreatedBy(null);

        // Create the Payments, which fails.
        PaymentsDTO paymentsDTO = paymentsMapper.toDto(payments);

        restPaymentsMockMvc.perform(post("/api/payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentsDTO)))
            .andExpect(status().isBadRequest());

        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPayments() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        // Get all the paymentsList
        restPaymentsMockMvc.perform(get("/api/payments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(payments.getId().intValue())))
            .andExpect(jsonPath("$.[*].vendor").value(hasItem(DEFAULT_VENDOR.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].paymentValue").value(hasItem(DEFAULT_PAYMENT_VALUE)))
            .andExpect(jsonPath("$.[*].initiatedDate").value(hasItem(DEFAULT_INITIATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].confirmDate").value(hasItem(DEFAULT_CONFIRM_DATE.toString())))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(DEFAULT_LAST_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastUpdatedBy").value(hasItem(DEFAULT_LAST_UPDATED_BY.toString())));
    }
    
    @Test
    @Transactional
    public void getPayments() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        // Get the payments
        restPaymentsMockMvc.perform(get("/api/payments/{id}", payments.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(payments.getId().intValue()))
            .andExpect(jsonPath("$.vendor").value(DEFAULT_VENDOR.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.paymentValue").value(DEFAULT_PAYMENT_VALUE))
            .andExpect(jsonPath("$.initiatedDate").value(DEFAULT_INITIATED_DATE.toString()))
            .andExpect(jsonPath("$.confirmDate").value(DEFAULT_CONFIRM_DATE.toString()))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.lastUpdatedDate").value(DEFAULT_LAST_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.lastUpdatedBy").value(DEFAULT_LAST_UPDATED_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPayments() throws Exception {
        // Get the payments
        restPaymentsMockMvc.perform(get("/api/payments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePayments() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        int databaseSizeBeforeUpdate = paymentsRepository.findAll().size();

        // Update the payments
        Payments updatedPayments = paymentsRepository.findById(payments.getId()).get();
        // Disconnect from session so that the updates on updatedPayments are not directly saved in db
        em.detach(updatedPayments);
        updatedPayments
            .vendor(UPDATED_VENDOR)
            .status(UPDATED_STATUS)
            .paymentValue(UPDATED_PAYMENT_VALUE)
            .initiatedDate(UPDATED_INITIATED_DATE)
            .confirmDate(UPDATED_CONFIRM_DATE)
            .activated(UPDATED_ACTIVATED)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .lastUpdatedBy(UPDATED_LAST_UPDATED_BY);
        PaymentsDTO paymentsDTO = paymentsMapper.toDto(updatedPayments);

        restPaymentsMockMvc.perform(put("/api/payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentsDTO)))
            .andExpect(status().isOk());

        // Validate the Payments in the database
        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeUpdate);
        Payments testPayments = paymentsList.get(paymentsList.size() - 1);
        assertThat(testPayments.getVendor()).isEqualTo(UPDATED_VENDOR);
        assertThat(testPayments.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPayments.getPaymentValue()).isEqualTo(UPDATED_PAYMENT_VALUE);
        assertThat(testPayments.getInitiatedDate()).isEqualTo(UPDATED_INITIATED_DATE);
        assertThat(testPayments.getConfirmDate()).isEqualTo(UPDATED_CONFIRM_DATE);
        assertThat(testPayments.isActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testPayments.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPayments.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPayments.getLastUpdatedDate()).isEqualTo(UPDATED_LAST_UPDATED_DATE);
        assertThat(testPayments.getLastUpdatedBy()).isEqualTo(UPDATED_LAST_UPDATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingPayments() throws Exception {
        int databaseSizeBeforeUpdate = paymentsRepository.findAll().size();

        // Create the Payments
        PaymentsDTO paymentsDTO = paymentsMapper.toDto(payments);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentsMockMvc.perform(put("/api/payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Payments in the database
        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePayments() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        int databaseSizeBeforeDelete = paymentsRepository.findAll().size();

        // Delete the payments
        restPaymentsMockMvc.perform(delete("/api/payments/{id}", payments.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Payments.class);
        Payments payments1 = new Payments();
        payments1.setId(1L);
        Payments payments2 = new Payments();
        payments2.setId(payments1.getId());
        assertThat(payments1).isEqualTo(payments2);
        payments2.setId(2L);
        assertThat(payments1).isNotEqualTo(payments2);
        payments1.setId(null);
        assertThat(payments1).isNotEqualTo(payments2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentsDTO.class);
        PaymentsDTO paymentsDTO1 = new PaymentsDTO();
        paymentsDTO1.setId(1L);
        PaymentsDTO paymentsDTO2 = new PaymentsDTO();
        assertThat(paymentsDTO1).isNotEqualTo(paymentsDTO2);
        paymentsDTO2.setId(paymentsDTO1.getId());
        assertThat(paymentsDTO1).isEqualTo(paymentsDTO2);
        paymentsDTO2.setId(2L);
        assertThat(paymentsDTO1).isNotEqualTo(paymentsDTO2);
        paymentsDTO1.setId(null);
        assertThat(paymentsDTO1).isNotEqualTo(paymentsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(paymentsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(paymentsMapper.fromId(null)).isNull();
    }
}
