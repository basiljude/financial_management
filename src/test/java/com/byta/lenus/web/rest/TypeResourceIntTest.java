package com.byta.lenus.web.rest;

import com.byta.lenus.OpenclinicApp;

import com.byta.lenus.domain.Type;
import com.byta.lenus.repository.TypeRepository;
import com.byta.lenus.service.TypeService;
import com.byta.lenus.service.dto.TypeDTO;
import com.byta.lenus.service.mapper.TypeMapper;
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
 * Test class for the TypeResource REST controller.
 *
 * @see TypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OpenclinicApp.class)
public class TypeResourceIntTest {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    @Autowired
    private TypeRepository typeRepository;

    @Autowired
    private TypeMapper typeMapper;

    @Autowired
    private TypeService typeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTypeMockMvc;

    private Type type;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeResource typeResource = new TypeResource(typeService);
        this.restTypeMockMvc = MockMvcBuilders.standaloneSetup(typeResource)
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
    public static Type createEntity(EntityManager em) {
        Type type = new Type()
            .type(DEFAULT_TYPE);
        return type;
    }

    @Before
    public void initTest() {
        type = createEntity(em);
    }

    @Test
    @Transactional
    public void createType() throws Exception {
        int databaseSizeBeforeCreate = typeRepository.findAll().size();

        // Create the Type
        TypeDTO typeDTO = typeMapper.toDto(type);
        restTypeMockMvc.perform(post("/api/types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeDTO)))
            .andExpect(status().isCreated());

        // Validate the Type in the database
        List<Type> typeList = typeRepository.findAll();
        assertThat(typeList).hasSize(databaseSizeBeforeCreate + 1);
        Type testType = typeList.get(typeList.size() - 1);
        assertThat(testType.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeRepository.findAll().size();

        // Create the Type with an existing ID
        type.setId(1L);
        TypeDTO typeDTO = typeMapper.toDto(type);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeMockMvc.perform(post("/api/types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Type in the database
        List<Type> typeList = typeRepository.findAll();
        assertThat(typeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTypes() throws Exception {
        // Initialize the database
        typeRepository.saveAndFlush(type);

        // Get all the typeList
        restTypeMockMvc.perform(get("/api/types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(type.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getType() throws Exception {
        // Initialize the database
        typeRepository.saveAndFlush(type);

        // Get the type
        restTypeMockMvc.perform(get("/api/types/{id}", type.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(type.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingType() throws Exception {
        // Get the type
        restTypeMockMvc.perform(get("/api/types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateType() throws Exception {
        // Initialize the database
        typeRepository.saveAndFlush(type);
        int databaseSizeBeforeUpdate = typeRepository.findAll().size();

        // Update the type
        Type updatedType = typeRepository.findOne(type.getId());
        updatedType
            .type(UPDATED_TYPE);
        TypeDTO typeDTO = typeMapper.toDto(updatedType);

        restTypeMockMvc.perform(put("/api/types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeDTO)))
            .andExpect(status().isOk());

        // Validate the Type in the database
        List<Type> typeList = typeRepository.findAll();
        assertThat(typeList).hasSize(databaseSizeBeforeUpdate);
        Type testType = typeList.get(typeList.size() - 1);
        assertThat(testType.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingType() throws Exception {
        int databaseSizeBeforeUpdate = typeRepository.findAll().size();

        // Create the Type
        TypeDTO typeDTO = typeMapper.toDto(type);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTypeMockMvc.perform(put("/api/types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeDTO)))
            .andExpect(status().isCreated());

        // Validate the Type in the database
        List<Type> typeList = typeRepository.findAll();
        assertThat(typeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteType() throws Exception {
        // Initialize the database
        typeRepository.saveAndFlush(type);
        int databaseSizeBeforeDelete = typeRepository.findAll().size();

        // Get the type
        restTypeMockMvc.perform(delete("/api/types/{id}", type.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Type> typeList = typeRepository.findAll();
        assertThat(typeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Type.class);
        Type type1 = new Type();
        type1.setId(1L);
        Type type2 = new Type();
        type2.setId(type1.getId());
        assertThat(type1).isEqualTo(type2);
        type2.setId(2L);
        assertThat(type1).isNotEqualTo(type2);
        type1.setId(null);
        assertThat(type1).isNotEqualTo(type2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeDTO.class);
        TypeDTO typeDTO1 = new TypeDTO();
        typeDTO1.setId(1L);
        TypeDTO typeDTO2 = new TypeDTO();
        assertThat(typeDTO1).isNotEqualTo(typeDTO2);
        typeDTO2.setId(typeDTO1.getId());
        assertThat(typeDTO1).isEqualTo(typeDTO2);
        typeDTO2.setId(2L);
        assertThat(typeDTO1).isNotEqualTo(typeDTO2);
        typeDTO1.setId(null);
        assertThat(typeDTO1).isNotEqualTo(typeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(typeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(typeMapper.fromId(null)).isNull();
    }
}
