package com.byta.lenus.web.rest;

import com.byta.lenus.LenusApp;

import com.byta.lenus.domain.AddCredit;
import com.byta.lenus.repository.AddCreditRepository;
import com.byta.lenus.service.AddCreditService;
import com.byta.lenus.service.dto.AddCreditDTO;
import com.byta.lenus.service.mapper.AddCreditMapper;
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
 * Test class for the AddCreditResource REST controller.
 *
 * @see AddCreditResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LenusApp.class)
public class AddCreditResourceIntTest {

    private static final LocalDate DEFAULT_CREDIT_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREDIT_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_AMOUND = 1D;
    private static final Double UPDATED_AMOUND = 2D;

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    @Autowired
    private AddCreditRepository addCreditRepository;

    @Autowired
    private AddCreditMapper addCreditMapper;

    @Autowired
    private AddCreditService addCreditService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAddCreditMockMvc;

    private AddCredit addCredit;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AddCreditResource addCreditResource = new AddCreditResource(addCreditService);
        this.restAddCreditMockMvc = MockMvcBuilders.standaloneSetup(addCreditResource)
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
    public static AddCredit createEntity(EntityManager em) {
        AddCredit addCredit = new AddCredit()
            .creditTime(DEFAULT_CREDIT_TIME)
            .amound(DEFAULT_AMOUND)
            .comment(DEFAULT_COMMENT);
        return addCredit;
    }

    @Before
    public void initTest() {
        addCredit = createEntity(em);
    }

    @Test
    @Transactional
    public void createAddCredit() throws Exception {
        int databaseSizeBeforeCreate = addCreditRepository.findAll().size();

        // Create the AddCredit
        AddCreditDTO addCreditDTO = addCreditMapper.toDto(addCredit);
        restAddCreditMockMvc.perform(post("/api/add-credits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addCreditDTO)))
            .andExpect(status().isCreated());

        // Validate the AddCredit in the database
        List<AddCredit> addCreditList = addCreditRepository.findAll();
        assertThat(addCreditList).hasSize(databaseSizeBeforeCreate + 1);
        AddCredit testAddCredit = addCreditList.get(addCreditList.size() - 1);
        assertThat(testAddCredit.getCreditTime()).isEqualTo(DEFAULT_CREDIT_TIME);
        assertThat(testAddCredit.getAmound()).isEqualTo(DEFAULT_AMOUND);
        assertThat(testAddCredit.getComment()).isEqualTo(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    public void createAddCreditWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = addCreditRepository.findAll().size();

        // Create the AddCredit with an existing ID
        addCredit.setId(1L);
        AddCreditDTO addCreditDTO = addCreditMapper.toDto(addCredit);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAddCreditMockMvc.perform(post("/api/add-credits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addCreditDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AddCredit in the database
        List<AddCredit> addCreditList = addCreditRepository.findAll();
        assertThat(addCreditList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAddCredits() throws Exception {
        // Initialize the database
        addCreditRepository.saveAndFlush(addCredit);

        // Get all the addCreditList
        restAddCreditMockMvc.perform(get("/api/add-credits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(addCredit.getId().intValue())))
            .andExpect(jsonPath("$.[*].creditTime").value(hasItem(DEFAULT_CREDIT_TIME.toString())))
            .andExpect(jsonPath("$.[*].amound").value(hasItem(DEFAULT_AMOUND.doubleValue())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())));
    }

    @Test
    @Transactional
    public void getAddCredit() throws Exception {
        // Initialize the database
        addCreditRepository.saveAndFlush(addCredit);

        // Get the addCredit
        restAddCreditMockMvc.perform(get("/api/add-credits/{id}", addCredit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(addCredit.getId().intValue()))
            .andExpect(jsonPath("$.creditTime").value(DEFAULT_CREDIT_TIME.toString()))
            .andExpect(jsonPath("$.amound").value(DEFAULT_AMOUND.doubleValue()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAddCredit() throws Exception {
        // Get the addCredit
        restAddCreditMockMvc.perform(get("/api/add-credits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAddCredit() throws Exception {
        // Initialize the database
        addCreditRepository.saveAndFlush(addCredit);
        int databaseSizeBeforeUpdate = addCreditRepository.findAll().size();

        // Update the addCredit
        AddCredit updatedAddCredit = addCreditRepository.findOne(addCredit.getId());
        updatedAddCredit
            .creditTime(UPDATED_CREDIT_TIME)
            .amound(UPDATED_AMOUND)
            .comment(UPDATED_COMMENT);
        AddCreditDTO addCreditDTO = addCreditMapper.toDto(updatedAddCredit);

        restAddCreditMockMvc.perform(put("/api/add-credits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addCreditDTO)))
            .andExpect(status().isOk());

        // Validate the AddCredit in the database
        List<AddCredit> addCreditList = addCreditRepository.findAll();
        assertThat(addCreditList).hasSize(databaseSizeBeforeUpdate);
        AddCredit testAddCredit = addCreditList.get(addCreditList.size() - 1);
        assertThat(testAddCredit.getCreditTime()).isEqualTo(UPDATED_CREDIT_TIME);
        assertThat(testAddCredit.getAmound()).isEqualTo(UPDATED_AMOUND);
        assertThat(testAddCredit.getComment()).isEqualTo(UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void updateNonExistingAddCredit() throws Exception {
        int databaseSizeBeforeUpdate = addCreditRepository.findAll().size();

        // Create the AddCredit
        AddCreditDTO addCreditDTO = addCreditMapper.toDto(addCredit);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAddCreditMockMvc.perform(put("/api/add-credits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addCreditDTO)))
            .andExpect(status().isCreated());

        // Validate the AddCredit in the database
        List<AddCredit> addCreditList = addCreditRepository.findAll();
        assertThat(addCreditList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAddCredit() throws Exception {
        // Initialize the database
        addCreditRepository.saveAndFlush(addCredit);
        int databaseSizeBeforeDelete = addCreditRepository.findAll().size();

        // Get the addCredit
        restAddCreditMockMvc.perform(delete("/api/add-credits/{id}", addCredit.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AddCredit> addCreditList = addCreditRepository.findAll();
        assertThat(addCreditList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AddCredit.class);
        AddCredit addCredit1 = new AddCredit();
        addCredit1.setId(1L);
        AddCredit addCredit2 = new AddCredit();
        addCredit2.setId(addCredit1.getId());
        assertThat(addCredit1).isEqualTo(addCredit2);
        addCredit2.setId(2L);
        assertThat(addCredit1).isNotEqualTo(addCredit2);
        addCredit1.setId(null);
        assertThat(addCredit1).isNotEqualTo(addCredit2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AddCreditDTO.class);
        AddCreditDTO addCreditDTO1 = new AddCreditDTO();
        addCreditDTO1.setId(1L);
        AddCreditDTO addCreditDTO2 = new AddCreditDTO();
        assertThat(addCreditDTO1).isNotEqualTo(addCreditDTO2);
        addCreditDTO2.setId(addCreditDTO1.getId());
        assertThat(addCreditDTO1).isEqualTo(addCreditDTO2);
        addCreditDTO2.setId(2L);
        assertThat(addCreditDTO1).isNotEqualTo(addCreditDTO2);
        addCreditDTO1.setId(null);
        assertThat(addCreditDTO1).isNotEqualTo(addCreditDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(addCreditMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(addCreditMapper.fromId(null)).isNull();
    }
}
