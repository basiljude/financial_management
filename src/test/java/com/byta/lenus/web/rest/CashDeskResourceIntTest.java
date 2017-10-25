package com.byta.lenus.web.rest;

import com.byta.lenus.LenusApp;

import com.byta.lenus.domain.CashDesk;
import com.byta.lenus.repository.CashDeskRepository;
import com.byta.lenus.service.CashDeskService;
import com.byta.lenus.service.dto.CashDeskDTO;
import com.byta.lenus.service.mapper.CashDeskMapper;
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
 * Test class for the CashDeskResource REST controller.
 *
 * @see CashDeskResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LenusApp.class)
public class CashDeskResourceIntTest {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    @Autowired
    private CashDeskRepository cashDeskRepository;

    @Autowired
    private CashDeskMapper cashDeskMapper;

    @Autowired
    private CashDeskService cashDeskService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCashDeskMockMvc;

    private CashDesk cashDesk;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CashDeskResource cashDeskResource = new CashDeskResource(cashDeskService);
        this.restCashDeskMockMvc = MockMvcBuilders.standaloneSetup(cashDeskResource)
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
    public static CashDesk createEntity(EntityManager em) {
        CashDesk cashDesk = new CashDesk()
            .type(DEFAULT_TYPE);
        return cashDesk;
    }

    @Before
    public void initTest() {
        cashDesk = createEntity(em);
    }

    @Test
    @Transactional
    public void createCashDesk() throws Exception {
        int databaseSizeBeforeCreate = cashDeskRepository.findAll().size();

        // Create the CashDesk
        CashDeskDTO cashDeskDTO = cashDeskMapper.toDto(cashDesk);
        restCashDeskMockMvc.perform(post("/api/cash-desks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cashDeskDTO)))
            .andExpect(status().isCreated());

        // Validate the CashDesk in the database
        List<CashDesk> cashDeskList = cashDeskRepository.findAll();
        assertThat(cashDeskList).hasSize(databaseSizeBeforeCreate + 1);
        CashDesk testCashDesk = cashDeskList.get(cashDeskList.size() - 1);
        assertThat(testCashDesk.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createCashDeskWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cashDeskRepository.findAll().size();

        // Create the CashDesk with an existing ID
        cashDesk.setId(1L);
        CashDeskDTO cashDeskDTO = cashDeskMapper.toDto(cashDesk);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCashDeskMockMvc.perform(post("/api/cash-desks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cashDeskDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CashDesk in the database
        List<CashDesk> cashDeskList = cashDeskRepository.findAll();
        assertThat(cashDeskList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCashDesks() throws Exception {
        // Initialize the database
        cashDeskRepository.saveAndFlush(cashDesk);

        // Get all the cashDeskList
        restCashDeskMockMvc.perform(get("/api/cash-desks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cashDesk.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getCashDesk() throws Exception {
        // Initialize the database
        cashDeskRepository.saveAndFlush(cashDesk);

        // Get the cashDesk
        restCashDeskMockMvc.perform(get("/api/cash-desks/{id}", cashDesk.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cashDesk.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCashDesk() throws Exception {
        // Get the cashDesk
        restCashDeskMockMvc.perform(get("/api/cash-desks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCashDesk() throws Exception {
        // Initialize the database
        cashDeskRepository.saveAndFlush(cashDesk);
        int databaseSizeBeforeUpdate = cashDeskRepository.findAll().size();

        // Update the cashDesk
        CashDesk updatedCashDesk = cashDeskRepository.findOne(cashDesk.getId());
        updatedCashDesk
            .type(UPDATED_TYPE);
        CashDeskDTO cashDeskDTO = cashDeskMapper.toDto(updatedCashDesk);

        restCashDeskMockMvc.perform(put("/api/cash-desks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cashDeskDTO)))
            .andExpect(status().isOk());

        // Validate the CashDesk in the database
        List<CashDesk> cashDeskList = cashDeskRepository.findAll();
        assertThat(cashDeskList).hasSize(databaseSizeBeforeUpdate);
        CashDesk testCashDesk = cashDeskList.get(cashDeskList.size() - 1);
        assertThat(testCashDesk.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingCashDesk() throws Exception {
        int databaseSizeBeforeUpdate = cashDeskRepository.findAll().size();

        // Create the CashDesk
        CashDeskDTO cashDeskDTO = cashDeskMapper.toDto(cashDesk);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCashDeskMockMvc.perform(put("/api/cash-desks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cashDeskDTO)))
            .andExpect(status().isCreated());

        // Validate the CashDesk in the database
        List<CashDesk> cashDeskList = cashDeskRepository.findAll();
        assertThat(cashDeskList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCashDesk() throws Exception {
        // Initialize the database
        cashDeskRepository.saveAndFlush(cashDesk);
        int databaseSizeBeforeDelete = cashDeskRepository.findAll().size();

        // Get the cashDesk
        restCashDeskMockMvc.perform(delete("/api/cash-desks/{id}", cashDesk.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CashDesk> cashDeskList = cashDeskRepository.findAll();
        assertThat(cashDeskList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CashDesk.class);
        CashDesk cashDesk1 = new CashDesk();
        cashDesk1.setId(1L);
        CashDesk cashDesk2 = new CashDesk();
        cashDesk2.setId(cashDesk1.getId());
        assertThat(cashDesk1).isEqualTo(cashDesk2);
        cashDesk2.setId(2L);
        assertThat(cashDesk1).isNotEqualTo(cashDesk2);
        cashDesk1.setId(null);
        assertThat(cashDesk1).isNotEqualTo(cashDesk2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CashDeskDTO.class);
        CashDeskDTO cashDeskDTO1 = new CashDeskDTO();
        cashDeskDTO1.setId(1L);
        CashDeskDTO cashDeskDTO2 = new CashDeskDTO();
        assertThat(cashDeskDTO1).isNotEqualTo(cashDeskDTO2);
        cashDeskDTO2.setId(cashDeskDTO1.getId());
        assertThat(cashDeskDTO1).isEqualTo(cashDeskDTO2);
        cashDeskDTO2.setId(2L);
        assertThat(cashDeskDTO1).isNotEqualTo(cashDeskDTO2);
        cashDeskDTO1.setId(null);
        assertThat(cashDeskDTO1).isNotEqualTo(cashDeskDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(cashDeskMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(cashDeskMapper.fromId(null)).isNull();
    }
}
