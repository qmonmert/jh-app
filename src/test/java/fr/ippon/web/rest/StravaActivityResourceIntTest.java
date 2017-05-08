package fr.ippon.web.rest;

import fr.ippon.JhipsterApp;

import fr.ippon.domain.StravaActivity;
import fr.ippon.repository.StravaActivityRepository;
import fr.ippon.web.rest.errors.ExceptionTranslator;

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
 * Test class for the StravaActivityResource REST controller.
 *
 * @see StravaActivityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterApp.class)
public class StravaActivityResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Float DEFAULT_DISTANCE = 1F;
    private static final Float UPDATED_DISTANCE = 2F;

    private static final Integer DEFAULT_KUDOS_COUNT = 1;
    private static final Integer UPDATED_KUDOS_COUNT = 2;

    @Autowired
    private StravaActivityRepository stravaActivityRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restStravaActivityMockMvc;

    private StravaActivity stravaActivity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        StravaActivityResource stravaActivityResource = new StravaActivityResource(stravaActivityRepository);
        this.restStravaActivityMockMvc = MockMvcBuilders.standaloneSetup(stravaActivityResource)
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
    public static StravaActivity createEntity(EntityManager em) {
        StravaActivity stravaActivity = new StravaActivity()
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .distance(DEFAULT_DISTANCE)
            .kudos_count(DEFAULT_KUDOS_COUNT);
        return stravaActivity;
    }

    @Before
    public void initTest() {
        stravaActivity = createEntity(em);
    }

    @Test
    @Transactional
    public void createStravaActivity() throws Exception {
        int databaseSizeBeforeCreate = stravaActivityRepository.findAll().size();

        // Create the StravaActivity
        restStravaActivityMockMvc.perform(post("/api/strava-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stravaActivity)))
            .andExpect(status().isCreated());

        // Validate the StravaActivity in the database
        List<StravaActivity> stravaActivityList = stravaActivityRepository.findAll();
        assertThat(stravaActivityList).hasSize(databaseSizeBeforeCreate + 1);
        StravaActivity testStravaActivity = stravaActivityList.get(stravaActivityList.size() - 1);
        assertThat(testStravaActivity.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStravaActivity.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testStravaActivity.getDistance()).isEqualTo(DEFAULT_DISTANCE);
        assertThat(testStravaActivity.getKudos_count()).isEqualTo(DEFAULT_KUDOS_COUNT);
    }

    @Test
    @Transactional
    public void createStravaActivityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stravaActivityRepository.findAll().size();

        // Create the StravaActivity with an existing ID
        stravaActivity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStravaActivityMockMvc.perform(post("/api/strava-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stravaActivity)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<StravaActivity> stravaActivityList = stravaActivityRepository.findAll();
        assertThat(stravaActivityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllStravaActivities() throws Exception {
        // Initialize the database
        stravaActivityRepository.saveAndFlush(stravaActivity);

        // Get all the stravaActivityList
        restStravaActivityMockMvc.perform(get("/api/strava-activities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stravaActivity.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].distance").value(hasItem(DEFAULT_DISTANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].kudos_count").value(hasItem(DEFAULT_KUDOS_COUNT)));
    }

    @Test
    @Transactional
    public void getStravaActivity() throws Exception {
        // Initialize the database
        stravaActivityRepository.saveAndFlush(stravaActivity);

        // Get the stravaActivity
        restStravaActivityMockMvc.perform(get("/api/strava-activities/{id}", stravaActivity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(stravaActivity.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.distance").value(DEFAULT_DISTANCE.doubleValue()))
            .andExpect(jsonPath("$.kudos_count").value(DEFAULT_KUDOS_COUNT));
    }

    @Test
    @Transactional
    public void getNonExistingStravaActivity() throws Exception {
        // Get the stravaActivity
        restStravaActivityMockMvc.perform(get("/api/strava-activities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStravaActivity() throws Exception {
        // Initialize the database
        stravaActivityRepository.saveAndFlush(stravaActivity);
        int databaseSizeBeforeUpdate = stravaActivityRepository.findAll().size();

        // Update the stravaActivity
        StravaActivity updatedStravaActivity = stravaActivityRepository.findOne(stravaActivity.getId());
        updatedStravaActivity
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .distance(UPDATED_DISTANCE)
            .kudos_count(UPDATED_KUDOS_COUNT);

        restStravaActivityMockMvc.perform(put("/api/strava-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStravaActivity)))
            .andExpect(status().isOk());

        // Validate the StravaActivity in the database
        List<StravaActivity> stravaActivityList = stravaActivityRepository.findAll();
        assertThat(stravaActivityList).hasSize(databaseSizeBeforeUpdate);
        StravaActivity testStravaActivity = stravaActivityList.get(stravaActivityList.size() - 1);
        assertThat(testStravaActivity.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStravaActivity.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testStravaActivity.getDistance()).isEqualTo(UPDATED_DISTANCE);
        assertThat(testStravaActivity.getKudos_count()).isEqualTo(UPDATED_KUDOS_COUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingStravaActivity() throws Exception {
        int databaseSizeBeforeUpdate = stravaActivityRepository.findAll().size();

        // Create the StravaActivity

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restStravaActivityMockMvc.perform(put("/api/strava-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stravaActivity)))
            .andExpect(status().isCreated());

        // Validate the StravaActivity in the database
        List<StravaActivity> stravaActivityList = stravaActivityRepository.findAll();
        assertThat(stravaActivityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteStravaActivity() throws Exception {
        // Initialize the database
        stravaActivityRepository.saveAndFlush(stravaActivity);
        int databaseSizeBeforeDelete = stravaActivityRepository.findAll().size();

        // Get the stravaActivity
        restStravaActivityMockMvc.perform(delete("/api/strava-activities/{id}", stravaActivity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<StravaActivity> stravaActivityList = stravaActivityRepository.findAll();
        assertThat(stravaActivityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StravaActivity.class);
    }
}
