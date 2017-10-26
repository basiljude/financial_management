package com.byta.lenus.web.rest;

import com.byta.lenus.OpenclinicApp;

import com.byta.lenus.domain.PatientPaiment;
import com.byta.lenus.repository.PatientPaimentRepository;
import com.byta.lenus.service.PatientPaimentService;
import com.byta.lenus.service.dto.PatientPaimentDTO;
import com.byta.lenus.service.mapper.PatientPaimentMapper;
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
 * Test class for the PatientPaimentResource REST controller.
 *
 * @see PatientPaimentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OpenclinicApp.class)
public class PatientPaimentResourceIntTest {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_AMOUND = 1D;
    private static final Double UPDATED_AMOUND = 2D;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private PatientPaimentRepository patientPaimentRepository;

    @Autowired
    private PatientPaimentMapper patientPaimentMapper;

    @Autowired
    private PatientPaimentService patientPaimentService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPatientPaimentMockMvc;

    private PatientPaiment patientPaiment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PatientPaimentResource patientPaimentResource = new PatientPaimentResource(patientPaimentService);
        this.restPatientPaimentMockMvc = MockMvcBuilders.standaloneSetup(patientPaimentResource)
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
    public static PatientPaiment createEntity(EntityManager em) {
        PatientPaiment patientPaiment = new PatientPaiment()
            .date(DEFAULT_DATE)
            .amound(DEFAULT_AMOUND)
            .description(DEFAULT_DESCRIPTION);
        return patientPaiment;
    }

    @Before
    public void initTest() {
        patientPaiment = createEntity(em);
    }

    @Test
    @Transactional
    public void createPatientPaiment() throws Exception {
        int databaseSizeBeforeCreate = patientPaimentRepository.findAll().size();

        // Create the PatientPaiment
        PatientPaimentDTO patientPaimentDTO = patientPaimentMapper.toDto(patientPaiment);
        restPatientPaimentMockMvc.perform(post("/api/patient-paiments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patientPaimentDTO)))
            .andExpect(status().isCreated());

        // Validate the PatientPaiment in the database
        List<PatientPaiment> patientPaimentList = patientPaimentRepository.findAll();
        assertThat(patientPaimentList).hasSize(databaseSizeBeforeCreate + 1);
        PatientPaiment testPatientPaiment = patientPaimentList.get(patientPaimentList.size() - 1);
        assertThat(testPatientPaiment.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testPatientPaiment.getAmound()).isEqualTo(DEFAULT_AMOUND);
        assertThat(testPatientPaiment.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createPatientPaimentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = patientPaimentRepository.findAll().size();

        // Create the PatientPaiment with an existing ID
        patientPaiment.setId(1L);
        PatientPaimentDTO patientPaimentDTO = patientPaimentMapper.toDto(patientPaiment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPatientPaimentMockMvc.perform(post("/api/patient-paiments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patientPaimentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PatientPaiment in the database
        List<PatientPaiment> patientPaimentList = patientPaimentRepository.findAll();
        assertThat(patientPaimentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPatientPaiments() throws Exception {
        // Initialize the database
        patientPaimentRepository.saveAndFlush(patientPaiment);

        // Get all the patientPaimentList
        restPatientPaimentMockMvc.perform(get("/api/patient-paiments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(patientPaiment.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].amound").value(hasItem(DEFAULT_AMOUND.doubleValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getPatientPaiment() throws Exception {
        // Initialize the database
        patientPaimentRepository.saveAndFlush(patientPaiment);

        // Get the patientPaiment
        restPatientPaimentMockMvc.perform(get("/api/patient-paiments/{id}", patientPaiment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(patientPaiment.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.amound").value(DEFAULT_AMOUND.doubleValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPatientPaiment() throws Exception {
        // Get the patientPaiment
        restPatientPaimentMockMvc.perform(get("/api/patient-paiments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePatientPaiment() throws Exception {
        // Initialize the database
        patientPaimentRepository.saveAndFlush(patientPaiment);
        int databaseSizeBeforeUpdate = patientPaimentRepository.findAll().size();

        // Update the patientPaiment
        PatientPaiment updatedPatientPaiment = patientPaimentRepository.findOne(patientPaiment.getId());
        updatedPatientPaiment
            .date(UPDATED_DATE)
            .amound(UPDATED_AMOUND)
            .description(UPDATED_DESCRIPTION);
        PatientPaimentDTO patientPaimentDTO = patientPaimentMapper.toDto(updatedPatientPaiment);

        restPatientPaimentMockMvc.perform(put("/api/patient-paiments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patientPaimentDTO)))
            .andExpect(status().isOk());

        // Validate the PatientPaiment in the database
        List<PatientPaiment> patientPaimentList = patientPaimentRepository.findAll();
        assertThat(patientPaimentList).hasSize(databaseSizeBeforeUpdate);
        PatientPaiment testPatientPaiment = patientPaimentList.get(patientPaimentList.size() - 1);
        assertThat(testPatientPaiment.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testPatientPaiment.getAmound()).isEqualTo(UPDATED_AMOUND);
        assertThat(testPatientPaiment.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingPatientPaiment() throws Exception {
        int databaseSizeBeforeUpdate = patientPaimentRepository.findAll().size();

        // Create the PatientPaiment
        PatientPaimentDTO patientPaimentDTO = patientPaimentMapper.toDto(patientPaiment);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPatientPaimentMockMvc.perform(put("/api/patient-paiments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patientPaimentDTO)))
            .andExpect(status().isCreated());

        // Validate the PatientPaiment in the database
        List<PatientPaiment> patientPaimentList = patientPaimentRepository.findAll();
        assertThat(patientPaimentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePatientPaiment() throws Exception {
        // Initialize the database
        patientPaimentRepository.saveAndFlush(patientPaiment);
        int databaseSizeBeforeDelete = patientPaimentRepository.findAll().size();

        // Get the patientPaiment
        restPatientPaimentMockMvc.perform(delete("/api/patient-paiments/{id}", patientPaiment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PatientPaiment> patientPaimentList = patientPaimentRepository.findAll();
        assertThat(patientPaimentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PatientPaiment.class);
        PatientPaiment patientPaiment1 = new PatientPaiment();
        patientPaiment1.setId(1L);
        PatientPaiment patientPaiment2 = new PatientPaiment();
        patientPaiment2.setId(patientPaiment1.getId());
        assertThat(patientPaiment1).isEqualTo(patientPaiment2);
        patientPaiment2.setId(2L);
        assertThat(patientPaiment1).isNotEqualTo(patientPaiment2);
        patientPaiment1.setId(null);
        assertThat(patientPaiment1).isNotEqualTo(patientPaiment2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PatientPaimentDTO.class);
        PatientPaimentDTO patientPaimentDTO1 = new PatientPaimentDTO();
        patientPaimentDTO1.setId(1L);
        PatientPaimentDTO patientPaimentDTO2 = new PatientPaimentDTO();
        assertThat(patientPaimentDTO1).isNotEqualTo(patientPaimentDTO2);
        patientPaimentDTO2.setId(patientPaimentDTO1.getId());
        assertThat(patientPaimentDTO1).isEqualTo(patientPaimentDTO2);
        patientPaimentDTO2.setId(2L);
        assertThat(patientPaimentDTO1).isNotEqualTo(patientPaimentDTO2);
        patientPaimentDTO1.setId(null);
        assertThat(patientPaimentDTO1).isNotEqualTo(patientPaimentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(patientPaimentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(patientPaimentMapper.fromId(null)).isNull();
    }
}
