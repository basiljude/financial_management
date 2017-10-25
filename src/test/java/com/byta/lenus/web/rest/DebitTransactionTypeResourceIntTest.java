package com.byta.lenus.web.rest;

import com.byta.lenus.LenusApp;

import com.byta.lenus.domain.DebitTransactionType;
import com.byta.lenus.repository.DebitTransactionTypeRepository;
import com.byta.lenus.service.DebitTransactionTypeService;
import com.byta.lenus.service.dto.DebitTransactionTypeDTO;
import com.byta.lenus.service.mapper.DebitTransactionTypeMapper;
import com.byta.lenus.web.rest.errors.ExceptionTranslator;

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

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DebitTransactionTypeResource REST controller.
 *
 * @see DebitTransactionTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LenusApp.class)
public class DebitTransactionTypeResourceIntTest {

    private static final String DEFAULT_TRANSACTIONS = "AAAAAAAAAA";
    private static final String UPDATED_TRANSACTIONS = "BBBBBBBBBB";

    @Autowired
    private DebitTransactionTypeRepository debitTransactionTypeRepository;

    @Autowired
    private DebitTransactionTypeMapper debitTransactionTypeMapper;

    @Autowired
    private DebitTransactionTypeService debitTransactionTypeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDebitTransactionTypeMockMvc;

    private DebitTransactionType debitTransactionType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DebitTransactionTypeResource debitTransactionTypeResource = new DebitTransactionTypeResource(debitTransactionTypeService);
        this.restDebitTransactionTypeMockMvc = MockMvcBuilders.standaloneSetup(debitTransactionTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DebitTransactionType createEntity(EntityManager em) {
        DebitTransactionType debitTransactionType = new DebitTransactionType()
            .transactions(DEFAULT_TRANSACTIONS);
        return debitTransactionType;
    }

    @Before
    public void initTest() {
        debitTransactionType = createEntity(em);
    }

    @Test
    @Transactional
    public void createDebitTransactionType() throws Exception {
        int databaseSizeBeforeCreate = debitTransactionTypeRepository.findAll().size();

        // Create the DebitTransactionType
        DebitTransactionTypeDTO debitTransactionTypeDTO = debitTransactionTypeMapper.toDto(debitTransactionType);
        restDebitTransactionTypeMockMvc.perform(post("/api/debit-transaction-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(debitTransactionTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the DebitTransactionType in the database
        List<DebitTransactionType> debitTransactionTypeList = debitTransactionTypeRepository.findAll();
        assertThat(debitTransactionTypeList).hasSize(databaseSizeBeforeCreate + 1);
        DebitTransactionType testDebitTransactionType = debitTransactionTypeList.get(debitTransactionTypeList.size() - 1);
        assertThat(testDebitTransactionType.getTransactions()).isEqualTo(DEFAULT_TRANSACTIONS);
    }

    @Test
    @Transactional
    public void createDebitTransactionTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = debitTransactionTypeRepository.findAll().size();

        // Create the DebitTransactionType with an existing ID
        debitTransactionType.setId(1L);
        DebitTransactionTypeDTO debitTransactionTypeDTO = debitTransactionTypeMapper.toDto(debitTransactionType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDebitTransactionTypeMockMvc.perform(post("/api/debit-transaction-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(debitTransactionTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DebitTransactionType in the database
        List<DebitTransactionType> debitTransactionTypeList = debitTransactionTypeRepository.findAll();
        assertThat(debitTransactionTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDebitTransactionTypes() throws Exception {
        // Initialize the database
        debitTransactionTypeRepository.saveAndFlush(debitTransactionType);

        // Get all the debitTransactionTypeList
        restDebitTransactionTypeMockMvc.perform(get("/api/debit-transaction-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(debitTransactionType.getId().intValue())))
            .andExpect(jsonPath("$.[*].transactions").value(hasItem(DEFAULT_TRANSACTIONS.toString())));
    }

    @Test
    @Transactional
    public void getDebitTransactionType() throws Exception {
        // Initialize the database
        debitTransactionTypeRepository.saveAndFlush(debitTransactionType);

        // Get the debitTransactionType
        restDebitTransactionTypeMockMvc.perform(get("/api/debit-transaction-types/{id}", debitTransactionType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(debitTransactionType.getId().intValue()))
            .andExpect(jsonPath("$.transactions").value(DEFAULT_TRANSACTIONS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDebitTransactionType() throws Exception {
        // Get the debitTransactionType
        restDebitTransactionTypeMockMvc.perform(get("/api/debit-transaction-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDebitTransactionType() throws Exception {
        // Initialize the database
        debitTransactionTypeRepository.saveAndFlush(debitTransactionType);
        int databaseSizeBeforeUpdate = debitTransactionTypeRepository.findAll().size();

        // Update the debitTransactionType
        DebitTransactionType updatedDebitTransactionType = debitTransactionTypeRepository.findOne(debitTransactionType.getId());
        updatedDebitTransactionType
            .transactions(UPDATED_TRANSACTIONS);
        DebitTransactionTypeDTO debitTransactionTypeDTO = debitTransactionTypeMapper.toDto(updatedDebitTransactionType);

        restDebitTransactionTypeMockMvc.perform(put("/api/debit-transaction-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(debitTransactionTypeDTO)))
            .andExpect(status().isOk());

        // Validate the DebitTransactionType in the database
        List<DebitTransactionType> debitTransactionTypeList = debitTransactionTypeRepository.findAll();
        assertThat(debitTransactionTypeList).hasSize(databaseSizeBeforeUpdate);
        DebitTransactionType testDebitTransactionType = debitTransactionTypeList.get(debitTransactionTypeList.size() - 1);
        assertThat(testDebitTransactionType.getTransactions()).isEqualTo(UPDATED_TRANSACTIONS);
    }

    @Test
    @Transactional
    public void updateNonExistingDebitTransactionType() throws Exception {
        int databaseSizeBeforeUpdate = debitTransactionTypeRepository.findAll().size();

        // Create the DebitTransactionType
        DebitTransactionTypeDTO debitTransactionTypeDTO = debitTransactionTypeMapper.toDto(debitTransactionType);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDebitTransactionTypeMockMvc.perform(put("/api/debit-transaction-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(debitTransactionTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the DebitTransactionType in the database
        List<DebitTransactionType> debitTransactionTypeList = debitTransactionTypeRepository.findAll();
        assertThat(debitTransactionTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDebitTransactionType() throws Exception {
        // Initialize the database
        debitTransactionTypeRepository.saveAndFlush(debitTransactionType);
        int databaseSizeBeforeDelete = debitTransactionTypeRepository.findAll().size();

        // Get the debitTransactionType
        restDebitTransactionTypeMockMvc.perform(delete("/api/debit-transaction-types/{id}", debitTransactionType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DebitTransactionType> debitTransactionTypeList = debitTransactionTypeRepository.findAll();
        assertThat(debitTransactionTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DebitTransactionType.class);
        DebitTransactionType debitTransactionType1 = new DebitTransactionType();
        debitTransactionType1.setId(1L);
        DebitTransactionType debitTransactionType2 = new DebitTransactionType();
        debitTransactionType2.setId(debitTransactionType1.getId());
        assertThat(debitTransactionType1).isEqualTo(debitTransactionType2);
        debitTransactionType2.setId(2L);
        assertThat(debitTransactionType1).isNotEqualTo(debitTransactionType2);
        debitTransactionType1.setId(null);
        assertThat(debitTransactionType1).isNotEqualTo(debitTransactionType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DebitTransactionTypeDTO.class);
        DebitTransactionTypeDTO debitTransactionTypeDTO1 = new DebitTransactionTypeDTO();
        debitTransactionTypeDTO1.setId(1L);
        DebitTransactionTypeDTO debitTransactionTypeDTO2 = new DebitTransactionTypeDTO();
        assertThat(debitTransactionTypeDTO1).isNotEqualTo(debitTransactionTypeDTO2);
        debitTransactionTypeDTO2.setId(debitTransactionTypeDTO1.getId());
        assertThat(debitTransactionTypeDTO1).isEqualTo(debitTransactionTypeDTO2);
        debitTransactionTypeDTO2.setId(2L);
        assertThat(debitTransactionTypeDTO1).isNotEqualTo(debitTransactionTypeDTO2);
        debitTransactionTypeDTO1.setId(null);
        assertThat(debitTransactionTypeDTO1).isNotEqualTo(debitTransactionTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(debitTransactionTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(debitTransactionTypeMapper.fromId(null)).isNull();
    }
}
