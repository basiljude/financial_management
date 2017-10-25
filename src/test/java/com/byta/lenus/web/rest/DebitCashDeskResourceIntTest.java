package com.byta.lenus.web.rest;

import com.byta.lenus.LenusApp;

import com.byta.lenus.domain.DebitCashDesk;
import com.byta.lenus.repository.DebitCashDeskRepository;
import com.byta.lenus.service.DebitCashDeskService;
import com.byta.lenus.service.dto.DebitCashDeskDTO;
import com.byta.lenus.service.mapper.DebitCashDeskMapper;
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
 * Test class for the DebitCashDeskResource REST controller.
 *
 * @see DebitCashDeskResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LenusApp.class)
public class DebitCashDeskResourceIntTest {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    @Autowired
    private DebitCashDeskRepository debitCashDeskRepository;

    @Autowired
    private DebitCashDeskMapper debitCashDeskMapper;

    @Autowired
    private DebitCashDeskService debitCashDeskService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDebitCashDeskMockMvc;

    private DebitCashDesk debitCashDesk;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DebitCashDeskResource debitCashDeskResource = new DebitCashDeskResource(debitCashDeskService);
        this.restDebitCashDeskMockMvc = MockMvcBuilders.standaloneSetup(debitCashDeskResource)
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
    public static DebitCashDesk createEntity(EntityManager em) {
        DebitCashDesk debitCashDesk = new DebitCashDesk()
            .type(DEFAULT_TYPE);
        return debitCashDesk;
    }

    @Before
    public void initTest() {
        debitCashDesk = createEntity(em);
    }

    @Test
    @Transactional
    public void createDebitCashDesk() throws Exception {
        int databaseSizeBeforeCreate = debitCashDeskRepository.findAll().size();

        // Create the DebitCashDesk
        DebitCashDeskDTO debitCashDeskDTO = debitCashDeskMapper.toDto(debitCashDesk);
        restDebitCashDeskMockMvc.perform(post("/api/debit-cash-desks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(debitCashDeskDTO)))
            .andExpect(status().isCreated());

        // Validate the DebitCashDesk in the database
        List<DebitCashDesk> debitCashDeskList = debitCashDeskRepository.findAll();
        assertThat(debitCashDeskList).hasSize(databaseSizeBeforeCreate + 1);
        DebitCashDesk testDebitCashDesk = debitCashDeskList.get(debitCashDeskList.size() - 1);
        assertThat(testDebitCashDesk.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createDebitCashDeskWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = debitCashDeskRepository.findAll().size();

        // Create the DebitCashDesk with an existing ID
        debitCashDesk.setId(1L);
        DebitCashDeskDTO debitCashDeskDTO = debitCashDeskMapper.toDto(debitCashDesk);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDebitCashDeskMockMvc.perform(post("/api/debit-cash-desks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(debitCashDeskDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DebitCashDesk in the database
        List<DebitCashDesk> debitCashDeskList = debitCashDeskRepository.findAll();
        assertThat(debitCashDeskList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDebitCashDesks() throws Exception {
        // Initialize the database
        debitCashDeskRepository.saveAndFlush(debitCashDesk);

        // Get all the debitCashDeskList
        restDebitCashDeskMockMvc.perform(get("/api/debit-cash-desks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(debitCashDesk.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getDebitCashDesk() throws Exception {
        // Initialize the database
        debitCashDeskRepository.saveAndFlush(debitCashDesk);

        // Get the debitCashDesk
        restDebitCashDeskMockMvc.perform(get("/api/debit-cash-desks/{id}", debitCashDesk.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(debitCashDesk.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDebitCashDesk() throws Exception {
        // Get the debitCashDesk
        restDebitCashDeskMockMvc.perform(get("/api/debit-cash-desks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDebitCashDesk() throws Exception {
        // Initialize the database
        debitCashDeskRepository.saveAndFlush(debitCashDesk);
        int databaseSizeBeforeUpdate = debitCashDeskRepository.findAll().size();

        // Update the debitCashDesk
        DebitCashDesk updatedDebitCashDesk = debitCashDeskRepository.findOne(debitCashDesk.getId());
        updatedDebitCashDesk
            .type(UPDATED_TYPE);
        DebitCashDeskDTO debitCashDeskDTO = debitCashDeskMapper.toDto(updatedDebitCashDesk);

        restDebitCashDeskMockMvc.perform(put("/api/debit-cash-desks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(debitCashDeskDTO)))
            .andExpect(status().isOk());

        // Validate the DebitCashDesk in the database
        List<DebitCashDesk> debitCashDeskList = debitCashDeskRepository.findAll();
        assertThat(debitCashDeskList).hasSize(databaseSizeBeforeUpdate);
        DebitCashDesk testDebitCashDesk = debitCashDeskList.get(debitCashDeskList.size() - 1);
        assertThat(testDebitCashDesk.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingDebitCashDesk() throws Exception {
        int databaseSizeBeforeUpdate = debitCashDeskRepository.findAll().size();

        // Create the DebitCashDesk
        DebitCashDeskDTO debitCashDeskDTO = debitCashDeskMapper.toDto(debitCashDesk);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDebitCashDeskMockMvc.perform(put("/api/debit-cash-desks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(debitCashDeskDTO)))
            .andExpect(status().isCreated());

        // Validate the DebitCashDesk in the database
        List<DebitCashDesk> debitCashDeskList = debitCashDeskRepository.findAll();
        assertThat(debitCashDeskList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDebitCashDesk() throws Exception {
        // Initialize the database
        debitCashDeskRepository.saveAndFlush(debitCashDesk);
        int databaseSizeBeforeDelete = debitCashDeskRepository.findAll().size();

        // Get the debitCashDesk
        restDebitCashDeskMockMvc.perform(delete("/api/debit-cash-desks/{id}", debitCashDesk.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DebitCashDesk> debitCashDeskList = debitCashDeskRepository.findAll();
        assertThat(debitCashDeskList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DebitCashDesk.class);
        DebitCashDesk debitCashDesk1 = new DebitCashDesk();
        debitCashDesk1.setId(1L);
        DebitCashDesk debitCashDesk2 = new DebitCashDesk();
        debitCashDesk2.setId(debitCashDesk1.getId());
        assertThat(debitCashDesk1).isEqualTo(debitCashDesk2);
        debitCashDesk2.setId(2L);
        assertThat(debitCashDesk1).isNotEqualTo(debitCashDesk2);
        debitCashDesk1.setId(null);
        assertThat(debitCashDesk1).isNotEqualTo(debitCashDesk2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DebitCashDeskDTO.class);
        DebitCashDeskDTO debitCashDeskDTO1 = new DebitCashDeskDTO();
        debitCashDeskDTO1.setId(1L);
        DebitCashDeskDTO debitCashDeskDTO2 = new DebitCashDeskDTO();
        assertThat(debitCashDeskDTO1).isNotEqualTo(debitCashDeskDTO2);
        debitCashDeskDTO2.setId(debitCashDeskDTO1.getId());
        assertThat(debitCashDeskDTO1).isEqualTo(debitCashDeskDTO2);
        debitCashDeskDTO2.setId(2L);
        assertThat(debitCashDeskDTO1).isNotEqualTo(debitCashDeskDTO2);
        debitCashDeskDTO1.setId(null);
        assertThat(debitCashDeskDTO1).isNotEqualTo(debitCashDeskDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(debitCashDeskMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(debitCashDeskMapper.fromId(null)).isNull();
    }
}
