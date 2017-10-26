package com.byta.lenus.web.rest;

import com.byta.lenus.OpenclinicApp;

import com.byta.lenus.domain.Encounder;
import com.byta.lenus.repository.EncounderRepository;
import com.byta.lenus.service.EncounderService;
import com.byta.lenus.service.dto.EncounderDTO;
import com.byta.lenus.service.mapper.EncounderMapper;
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
 * Test class for the EncounderResource REST controller.
 *
 * @see EncounderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OpenclinicApp.class)
public class EncounderResourceIntTest {

    private static final LocalDate DEFAULT_OUT_SET = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_OUT_SET = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DEPARTMENT = "AAAAAAAAAA";
    private static final String UPDATED_DEPARTMENT = "BBBBBBBBBB";

    private static final String DEFAULT_VIST_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_VIST_TYPE = "BBBBBBBBBB";

    @Autowired
    private EncounderRepository encounderRepository;

    @Autowired
    private EncounderMapper encounderMapper;

    @Autowired
    private EncounderService encounderService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEncounderMockMvc;

    private Encounder encounder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EncounderResource encounderResource = new EncounderResource(encounderService);
        this.restEncounderMockMvc = MockMvcBuilders.standaloneSetup(encounderResource)
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
    public static Encounder createEntity(EntityManager em) {
        Encounder encounder = new Encounder()
            .outSet(DEFAULT_OUT_SET)
            .end(DEFAULT_END)
            .department(DEFAULT_DEPARTMENT)
            .vistType(DEFAULT_VIST_TYPE);
        return encounder;
    }

    @Before
    public void initTest() {
        encounder = createEntity(em);
    }

    @Test
    @Transactional
    public void createEncounder() throws Exception {
        int databaseSizeBeforeCreate = encounderRepository.findAll().size();

        // Create the Encounder
        EncounderDTO encounderDTO = encounderMapper.toDto(encounder);
        restEncounderMockMvc.perform(post("/api/encounders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(encounderDTO)))
            .andExpect(status().isCreated());

        // Validate the Encounder in the database
        List<Encounder> encounderList = encounderRepository.findAll();
        assertThat(encounderList).hasSize(databaseSizeBeforeCreate + 1);
        Encounder testEncounder = encounderList.get(encounderList.size() - 1);
        assertThat(testEncounder.getOutSet()).isEqualTo(DEFAULT_OUT_SET);
        assertThat(testEncounder.getEnd()).isEqualTo(DEFAULT_END);
        assertThat(testEncounder.getDepartment()).isEqualTo(DEFAULT_DEPARTMENT);
        assertThat(testEncounder.getVistType()).isEqualTo(DEFAULT_VIST_TYPE);
    }

    @Test
    @Transactional
    public void createEncounderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = encounderRepository.findAll().size();

        // Create the Encounder with an existing ID
        encounder.setId(1L);
        EncounderDTO encounderDTO = encounderMapper.toDto(encounder);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEncounderMockMvc.perform(post("/api/encounders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(encounderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Encounder in the database
        List<Encounder> encounderList = encounderRepository.findAll();
        assertThat(encounderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEncounders() throws Exception {
        // Initialize the database
        encounderRepository.saveAndFlush(encounder);

        // Get all the encounderList
        restEncounderMockMvc.perform(get("/api/encounders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(encounder.getId().intValue())))
            .andExpect(jsonPath("$.[*].outSet").value(hasItem(DEFAULT_OUT_SET.toString())))
            .andExpect(jsonPath("$.[*].end").value(hasItem(DEFAULT_END.toString())))
            .andExpect(jsonPath("$.[*].department").value(hasItem(DEFAULT_DEPARTMENT.toString())))
            .andExpect(jsonPath("$.[*].vistType").value(hasItem(DEFAULT_VIST_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getEncounder() throws Exception {
        // Initialize the database
        encounderRepository.saveAndFlush(encounder);

        // Get the encounder
        restEncounderMockMvc.perform(get("/api/encounders/{id}", encounder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(encounder.getId().intValue()))
            .andExpect(jsonPath("$.outSet").value(DEFAULT_OUT_SET.toString()))
            .andExpect(jsonPath("$.end").value(DEFAULT_END.toString()))
            .andExpect(jsonPath("$.department").value(DEFAULT_DEPARTMENT.toString()))
            .andExpect(jsonPath("$.vistType").value(DEFAULT_VIST_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEncounder() throws Exception {
        // Get the encounder
        restEncounderMockMvc.perform(get("/api/encounders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEncounder() throws Exception {
        // Initialize the database
        encounderRepository.saveAndFlush(encounder);
        int databaseSizeBeforeUpdate = encounderRepository.findAll().size();

        // Update the encounder
        Encounder updatedEncounder = encounderRepository.findOne(encounder.getId());
        updatedEncounder
            .outSet(UPDATED_OUT_SET)
            .end(UPDATED_END)
            .department(UPDATED_DEPARTMENT)
            .vistType(UPDATED_VIST_TYPE);
        EncounderDTO encounderDTO = encounderMapper.toDto(updatedEncounder);

        restEncounderMockMvc.perform(put("/api/encounders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(encounderDTO)))
            .andExpect(status().isOk());

        // Validate the Encounder in the database
        List<Encounder> encounderList = encounderRepository.findAll();
        assertThat(encounderList).hasSize(databaseSizeBeforeUpdate);
        Encounder testEncounder = encounderList.get(encounderList.size() - 1);
        assertThat(testEncounder.getOutSet()).isEqualTo(UPDATED_OUT_SET);
        assertThat(testEncounder.getEnd()).isEqualTo(UPDATED_END);
        assertThat(testEncounder.getDepartment()).isEqualTo(UPDATED_DEPARTMENT);
        assertThat(testEncounder.getVistType()).isEqualTo(UPDATED_VIST_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingEncounder() throws Exception {
        int databaseSizeBeforeUpdate = encounderRepository.findAll().size();

        // Create the Encounder
        EncounderDTO encounderDTO = encounderMapper.toDto(encounder);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEncounderMockMvc.perform(put("/api/encounders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(encounderDTO)))
            .andExpect(status().isCreated());

        // Validate the Encounder in the database
        List<Encounder> encounderList = encounderRepository.findAll();
        assertThat(encounderList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEncounder() throws Exception {
        // Initialize the database
        encounderRepository.saveAndFlush(encounder);
        int databaseSizeBeforeDelete = encounderRepository.findAll().size();

        // Get the encounder
        restEncounderMockMvc.perform(delete("/api/encounders/{id}", encounder.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Encounder> encounderList = encounderRepository.findAll();
        assertThat(encounderList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Encounder.class);
        Encounder encounder1 = new Encounder();
        encounder1.setId(1L);
        Encounder encounder2 = new Encounder();
        encounder2.setId(encounder1.getId());
        assertThat(encounder1).isEqualTo(encounder2);
        encounder2.setId(2L);
        assertThat(encounder1).isNotEqualTo(encounder2);
        encounder1.setId(null);
        assertThat(encounder1).isNotEqualTo(encounder2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EncounderDTO.class);
        EncounderDTO encounderDTO1 = new EncounderDTO();
        encounderDTO1.setId(1L);
        EncounderDTO encounderDTO2 = new EncounderDTO();
        assertThat(encounderDTO1).isNotEqualTo(encounderDTO2);
        encounderDTO2.setId(encounderDTO1.getId());
        assertThat(encounderDTO1).isEqualTo(encounderDTO2);
        encounderDTO2.setId(2L);
        assertThat(encounderDTO1).isNotEqualTo(encounderDTO2);
        encounderDTO1.setId(null);
        assertThat(encounderDTO1).isNotEqualTo(encounderDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(encounderMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(encounderMapper.fromId(null)).isNull();
    }
}
