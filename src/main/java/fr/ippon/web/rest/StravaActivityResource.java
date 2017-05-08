package fr.ippon.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.ippon.domain.StravaActivity;

import fr.ippon.repository.StravaActivityRepository;
import fr.ippon.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing StravaActivity.
 */
@RestController
@RequestMapping("/api")
public class StravaActivityResource {

    private final Logger log = LoggerFactory.getLogger(StravaActivityResource.class);

    private static final String ENTITY_NAME = "stravaActivity";
        
    private final StravaActivityRepository stravaActivityRepository;

    public StravaActivityResource(StravaActivityRepository stravaActivityRepository) {
        this.stravaActivityRepository = stravaActivityRepository;
    }

    /**
     * POST  /strava-activities : Create a new stravaActivity.
     *
     * @param stravaActivity the stravaActivity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new stravaActivity, or with status 400 (Bad Request) if the stravaActivity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/strava-activities")
    @Timed
    public ResponseEntity<StravaActivity> createStravaActivity(@RequestBody StravaActivity stravaActivity) throws URISyntaxException {
        log.debug("REST request to save StravaActivity : {}", stravaActivity);
        if (stravaActivity.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new stravaActivity cannot already have an ID")).body(null);
        }
        StravaActivity result = stravaActivityRepository.save(stravaActivity);
        return ResponseEntity.created(new URI("/api/strava-activities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /strava-activities : Updates an existing stravaActivity.
     *
     * @param stravaActivity the stravaActivity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated stravaActivity,
     * or with status 400 (Bad Request) if the stravaActivity is not valid,
     * or with status 500 (Internal Server Error) if the stravaActivity couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/strava-activities")
    @Timed
    public ResponseEntity<StravaActivity> updateStravaActivity(@RequestBody StravaActivity stravaActivity) throws URISyntaxException {
        log.debug("REST request to update StravaActivity : {}", stravaActivity);
        if (stravaActivity.getId() == null) {
            return createStravaActivity(stravaActivity);
        }
        StravaActivity result = stravaActivityRepository.save(stravaActivity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, stravaActivity.getId().toString()))
            .body(result);
    }

    /**
     * GET  /strava-activities : get all the stravaActivities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of stravaActivities in body
     */
    @GetMapping("/strava-activities")
    @Timed
    public List<StravaActivity> getAllStravaActivities() {
        log.debug("REST request to get all StravaActivities");
        List<StravaActivity> stravaActivities = stravaActivityRepository.findAll();
        return stravaActivities;
    }

    /**
     * GET  /strava-activities/:id : get the "id" stravaActivity.
     *
     * @param id the id of the stravaActivity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the stravaActivity, or with status 404 (Not Found)
     */
    @GetMapping("/strava-activities/{id}")
    @Timed
    public ResponseEntity<StravaActivity> getStravaActivity(@PathVariable Long id) {
        log.debug("REST request to get StravaActivity : {}", id);
        StravaActivity stravaActivity = stravaActivityRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(stravaActivity));
    }

    /**
     * DELETE  /strava-activities/:id : delete the "id" stravaActivity.
     *
     * @param id the id of the stravaActivity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/strava-activities/{id}")
    @Timed
    public ResponseEntity<Void> deleteStravaActivity(@PathVariable Long id) {
        log.debug("REST request to delete StravaActivity : {}", id);
        stravaActivityRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
