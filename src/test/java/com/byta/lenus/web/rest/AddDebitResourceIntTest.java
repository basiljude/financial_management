package com.byta.lenus.web.rest;

import com.byta.lenus.OpenclinicApp;

import com.byta.lenus.domain.AddDebit;
import com.byta.lenus.repository.AddDebitRepository;
import com.byta.lenus.service.AddDebitService;
import com.byta.lenus.service.dto.AddDebitDTO;
import com.byta.lenus.service.mapper.AddDebitMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AddDebitResource REST controller.
 *
 * @see AddDebitResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OpenclinicApp.class)
public class AddDebitResourceIntTest {

    private static final LocalDate DEFAULT_CREDIT_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREDIT_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_AMOUND = 1D;
    private static final Double UPDATED_AMOUND = 2D;

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    @Autowired
    private AddDebitRepository addDebitRepository;

    @Autowired
    private AddDebitMapper addDebitMapper;

    @Autowired
    private AddDebitService addDebitService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAddDebitMockMvc;

    private AddDebit addDebit;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AddDebitResource addDebitResource = new AddDebitResource(addDebitService);
        this.restAddDebitMockMvc = MockMvcBuilders.standaloneSetup(addDebitResource)
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
    public static AddDebit createEntity(EntityManager em) {
        AddDebit addDebit = new AddDebit()
            .creditTime(DEFAULT_CREDIT_TIME)
            .amound(DEFAULT_AMOUND)
            .comment(DEFAULT_COMMENT);
        return addDebit;
    }

    @Before
    public void initTest() {
        addDebit = createEntity(em);
    }

    @Test
    @Transactional
    public void createAddDebit() throws Exception {
        int databaseSizeBeforeCreate = addDebitRepository.findAll().size();

        // Create the AddDebit
        AddDebitDTO addDebitDTO = addDebitMapper.toDto(addDebit);
        restAddDebitMockMvc.perform(post("/api/add-debits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addDebitDTO)))
            .andExpect(status().isCreated());

        // Validate the AddDebit in the database
        List<AddDebit> addDebitList = addDebitRepository.findAll();
        assertThat(addDebitList).hasSize(databaseSizeBeforeCreate + 1);
        AddDebit testAddDebit = addDebitList.get(addDebitList.size() - 1);
        assertThat(testAddDebit.getCreditTime()).isEqualTo(DEFAULT_CREDIT_TIME);
        assertThat(testAddDebit.getAmound()).isEqualTo(DEFAULT_AMOUND);
        assertThat(testAddDebit.getComment()).isEqualTo(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    public void createAddDebitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = addDebitRepository.findAll().size();

        // Create the AddDebit with an existing ID
        addDebit.setId(1L);
        AddDebitDTO addDebitDTO = addDebitMapper.toDto(addDebit);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAddDebitMockMvc.perform(post("/api/add-debits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addDebitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AddDebit in the database
        List<AddDebit> addDebitList = addDebitRepository.findAll();
        assertThat(addDebitList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAddDebits() throws Exception {
        // Initialize the database
        addDebitRepository.saveAndFlush(addDebit);

        // Get all the addDebitList
        restAddDebitMockMvc.perform(get("/api/add-debits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(addDebit.getId().intValue())))
            .andExpect(jsonPath("$.[*].creditTime").value(hasItem(DEFAULT_CREDIT_TIME.toString())))
            .andExpect(jsonPath("$.[*].amound").value(hasItem(DEFAULT_AMOUND.doubleValue())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())));
    }

    @Test
    @Transactional
    public void getAddDebit() throws Exception {
        // Initialize the database
        addDebitRepository.saveAndFlush(addDebit);

        // Get the addDebit
        restAddDebitMockMvc.perform(get("/api/add-debits/{id}", addDebit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(addDebit.getId().intValue()))
            .andExpect(jsonPath("$.creditTime").value(DEFAULT_CREDIT_TIME.toString()))
            .andExpect(jsonPath("$.amound").value(DEFAULT_AMOUND.doubleValue()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAddDebit() throws Exception {
        // Get the addDebit
        restAddDebitMockMvc.perform(get("/api/add-debits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAddDebit() throws Exception {
        // Initialize the database
        addDebitRepository.saveAndFlush(addDebit);
        int databaseSizeBeforeUpdate = addDebitRepository.findAll().size();

        // Update the addDebit
        AddDebit updatedAddDebit = addDebitRepository.findOne(addDebit.getId());
        updatedAddDebit
            .creditTime(UPDATED_CREDIT_TIME)
            .amound(UPDATED_AMOUND)
            .comment(UPDATED_COMMENT);
        AddDebitDTO addDebitDTO = addDebitMapper.toDto(updatedAddDebit);

        restAddDebitMockMvc.perform(put("/api/add-debits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addDebitDTO)))
            .andExpect(status().isOk());

        // Validate the AddDebit in the database
        List<AddDebit> addDebitList = addDebitRepository.findAll();
        assertThat(addDebitList).hasSize(databaseSizeBeforeUpdate);
        AddDebit testAddDebit = addDebitList.get(addDebitList.size() - 1);
        assertThat(testAddDebit.getCreditTime()).isEqualTo(UPDATED_CREDIT_TIME);
        assertThat(testAddDebit.getAmound()).isEqualTo(UPDATED_AMOUND);
        assertThat(testAddDebit.getComment()).isEqualTo(UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void updateNonExistingAddDebit() throws Exception {
        int databaseSizeBeforeUpdate = addDebitRepository.findAll().size();

        // Create the AddDebit
        AddDebitDTO addDebitDTO = addDebitMapper.toDto(addDebit);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAddDebitMockMvc.perform(put("/api/add-debits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addDebitDTO)))
            .andExpect(status().isCreated());

        // Validate the AddDebit in the database
        List<AddDebit> addDebitList = addDebitRepository.findAll();
        assertThat(addDebitList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAddDebit() throws Exception {
        // Initialize the database
        addDebitRepository.saveAndFlush(addDebit);
        int databaseSizeBeforeDelete = addDebitRepository.findAll().size();

        // Get the addDebit
        restAddDebitMockMvc.perform(delete("/api/add-debits/{id}", addDebit.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AddDebit> addDebitList = addDebitRepository.findAll();
        assertThat(addDebitList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AddDebit.class);
        AddDebit addDebit1 = new AddDebit();
        addDebit1.setId(1L);
        AddDebit addDebit2 = new AddDebit();
        addDebit2.setId(addDebit1.getId());
        assertThat(addDebit1).isEqualTo(addDebit2);
        addDebit2.setId(2L);
        assertThat(addDebit1).isNotEqualTo(addDebit2);
        addDebit1.setId(null);
        assertThat(addDebit1).isNotEqualTo(addDebit2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AddDebitDTO.class);
        AddDebitDTO addDebitDTO1 = new AddDebitDTO();
        addDebitDTO1.setId(1L);
        AddDebitDTO addDebitDTO2 = new AddDebitDTO();
        assertThat(addDebitDTO1).isNotEqualTo(addDebitDTO2);
        addDebitDTO2.setId(addDebitDTO1.getId());
        assertThat(addDebitDTO1).isEqualTo(addDebitDTO2);
        addDebitDTO2.setId(2L);
        assertThat(addDebitDTO1).isNotEqualTo(addDebitDTO2);
        addDebitDTO1.setId(null);
        assertThat(addDebitDTO1).isNotEqualTo(addDebitDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(addDebitMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(addDebitMapper.fromId(null)).isNull();
    }
}
