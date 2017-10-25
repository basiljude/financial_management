package com.byta.lenus.web.rest;

import com.byta.lenus.LenusApp;

import com.byta.lenus.domain.TransactionTypen;
import com.byta.lenus.repository.TransactionTypenRepository;
import com.byta.lenus.service.TransactionTypenService;
import com.byta.lenus.service.dto.TransactionTypenDTO;
import com.byta.lenus.service.mapper.TransactionTypenMapper;
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
 * Test class for the TransactionTypenResource REST controller.
 *
 * @see TransactionTypenResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LenusApp.class)
public class TransactionTypenResourceIntTest {

    private static final String DEFAULT_TRANSACTIONS = "AAAAAAAAAA";
    private static final String UPDATED_TRANSACTIONS = "BBBBBBBBBB";

    @Autowired
    private TransactionTypenRepository transactionTypenRepository;

    @Autowired
    private TransactionTypenMapper transactionTypenMapper;

    @Autowired
    private TransactionTypenService transactionTypenService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTransactionTypenMockMvc;

    private TransactionTypen transactionTypen;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TransactionTypenResource transactionTypenResource = new TransactionTypenResource(transactionTypenService);
        this.restTransactionTypenMockMvc = MockMvcBuilders.standaloneSetup(transactionTypenResource)
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
    public static TransactionTypen createEntity(EntityManager em) {
        TransactionTypen transactionTypen = new TransactionTypen()
            .transactions(DEFAULT_TRANSACTIONS);
        return transactionTypen;
    }

    @Before
    public void initTest() {
        transactionTypen = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransactionTypen() throws Exception {
        int databaseSizeBeforeCreate = transactionTypenRepository.findAll().size();

        // Create the TransactionTypen
        TransactionTypenDTO transactionTypenDTO = transactionTypenMapper.toDto(transactionTypen);
        restTransactionTypenMockMvc.perform(post("/api/transaction-typens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionTypenDTO)))
            .andExpect(status().isCreated());

        // Validate the TransactionTypen in the database
        List<TransactionTypen> transactionTypenList = transactionTypenRepository.findAll();
        assertThat(transactionTypenList).hasSize(databaseSizeBeforeCreate + 1);
        TransactionTypen testTransactionTypen = transactionTypenList.get(transactionTypenList.size() - 1);
        assertThat(testTransactionTypen.getTransactions()).isEqualTo(DEFAULT_TRANSACTIONS);
    }

    @Test
    @Transactional
    public void createTransactionTypenWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transactionTypenRepository.findAll().size();

        // Create the TransactionTypen with an existing ID
        transactionTypen.setId(1L);
        TransactionTypenDTO transactionTypenDTO = transactionTypenMapper.toDto(transactionTypen);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransactionTypenMockMvc.perform(post("/api/transaction-typens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionTypenDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TransactionTypen in the database
        List<TransactionTypen> transactionTypenList = transactionTypenRepository.findAll();
        assertThat(transactionTypenList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTransactionTypens() throws Exception {
        // Initialize the database
        transactionTypenRepository.saveAndFlush(transactionTypen);

        // Get all the transactionTypenList
        restTransactionTypenMockMvc.perform(get("/api/transaction-typens?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transactionTypen.getId().intValue())))
            .andExpect(jsonPath("$.[*].transactions").value(hasItem(DEFAULT_TRANSACTIONS.toString())));
    }

    @Test
    @Transactional
    public void getTransactionTypen() throws Exception {
        // Initialize the database
        transactionTypenRepository.saveAndFlush(transactionTypen);

        // Get the transactionTypen
        restTransactionTypenMockMvc.perform(get("/api/transaction-typens/{id}", transactionTypen.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(transactionTypen.getId().intValue()))
            .andExpect(jsonPath("$.transactions").value(DEFAULT_TRANSACTIONS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTransactionTypen() throws Exception {
        // Get the transactionTypen
        restTransactionTypenMockMvc.perform(get("/api/transaction-typens/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransactionTypen() throws Exception {
        // Initialize the database
        transactionTypenRepository.saveAndFlush(transactionTypen);
        int databaseSizeBeforeUpdate = transactionTypenRepository.findAll().size();

        // Update the transactionTypen
        TransactionTypen updatedTransactionTypen = transactionTypenRepository.findOne(transactionTypen.getId());
        updatedTransactionTypen
            .transactions(UPDATED_TRANSACTIONS);
        TransactionTypenDTO transactionTypenDTO = transactionTypenMapper.toDto(updatedTransactionTypen);

        restTransactionTypenMockMvc.perform(put("/api/transaction-typens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionTypenDTO)))
            .andExpect(status().isOk());

        // Validate the TransactionTypen in the database
        List<TransactionTypen> transactionTypenList = transactionTypenRepository.findAll();
        assertThat(transactionTypenList).hasSize(databaseSizeBeforeUpdate);
        TransactionTypen testTransactionTypen = transactionTypenList.get(transactionTypenList.size() - 1);
        assertThat(testTransactionTypen.getTransactions()).isEqualTo(UPDATED_TRANSACTIONS);
    }

    @Test
    @Transactional
    public void updateNonExistingTransactionTypen() throws Exception {
        int databaseSizeBeforeUpdate = transactionTypenRepository.findAll().size();

        // Create the TransactionTypen
        TransactionTypenDTO transactionTypenDTO = transactionTypenMapper.toDto(transactionTypen);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTransactionTypenMockMvc.perform(put("/api/transaction-typens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionTypenDTO)))
            .andExpect(status().isCreated());

        // Validate the TransactionTypen in the database
        List<TransactionTypen> transactionTypenList = transactionTypenRepository.findAll();
        assertThat(transactionTypenList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTransactionTypen() throws Exception {
        // Initialize the database
        transactionTypenRepository.saveAndFlush(transactionTypen);
        int databaseSizeBeforeDelete = transactionTypenRepository.findAll().size();

        // Get the transactionTypen
        restTransactionTypenMockMvc.perform(delete("/api/transaction-typens/{id}", transactionTypen.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TransactionTypen> transactionTypenList = transactionTypenRepository.findAll();
        assertThat(transactionTypenList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransactionTypen.class);
        TransactionTypen transactionTypen1 = new TransactionTypen();
        transactionTypen1.setId(1L);
        TransactionTypen transactionTypen2 = new TransactionTypen();
        transactionTypen2.setId(transactionTypen1.getId());
        assertThat(transactionTypen1).isEqualTo(transactionTypen2);
        transactionTypen2.setId(2L);
        assertThat(transactionTypen1).isNotEqualTo(transactionTypen2);
        transactionTypen1.setId(null);
        assertThat(transactionTypen1).isNotEqualTo(transactionTypen2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransactionTypenDTO.class);
        TransactionTypenDTO transactionTypenDTO1 = new TransactionTypenDTO();
        transactionTypenDTO1.setId(1L);
        TransactionTypenDTO transactionTypenDTO2 = new TransactionTypenDTO();
        assertThat(transactionTypenDTO1).isNotEqualTo(transactionTypenDTO2);
        transactionTypenDTO2.setId(transactionTypenDTO1.getId());
        assertThat(transactionTypenDTO1).isEqualTo(transactionTypenDTO2);
        transactionTypenDTO2.setId(2L);
        assertThat(transactionTypenDTO1).isNotEqualTo(transactionTypenDTO2);
        transactionTypenDTO1.setId(null);
        assertThat(transactionTypenDTO1).isNotEqualTo(transactionTypenDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(transactionTypenMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(transactionTypenMapper.fromId(null)).isNull();
    }
}
