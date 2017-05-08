package fr.ippon.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.ippon.domain.StravaActivity;

import fr.ippon.repository.StravaActivityRepository;
import fr.ippon.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing StravaActivity.
 */
@RestController
@RequestMapping("/api")
public class StravaActivityResource {

    private final static String STRAVA_URL = "https://www.strava.com/api/v3";
    private final static String ACCESS_TOKEN = "7352ab3baeb484779ced1f9a35c03bcd4340a403";

    private final Logger log = LoggerFactory.getLogger(StravaActivityResource.class);

    private final StravaActivityRepository stravaActivityRepository;

    public StravaActivityResource(StravaActivityRepository stravaActivityRepository) {
        this.stravaActivityRepository = stravaActivityRepository;
    }

    @GetMapping("/strava-activities")
    @Timed
    public List<StravaActivity> getAllStravaActivities() {
        log.debug("REST request to get all StravaActivities");
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<StravaActivity[]> response = restTemplate.getForEntity(
            STRAVA_URL + "/athlete/activities?access_token=" + ACCESS_TOKEN + "&page=1&per_page=5",
            StravaActivity[].class
        );
        log.info(response.getBody().toString());
        return Arrays.asList(response.getBody());
    }

    @GetMapping("/strava-activities/{id}")
    @Timed
    public ResponseEntity<StravaActivity> getStravaActivity(@PathVariable Long id) {
        log.debug("REST request to get StravaActivity : {}", id);
        RestTemplate restTemplate = new RestTemplate();
        StravaActivity stravaActivity = restTemplate.getForObject(
            STRAVA_URL + "/activities/" + id +"?access_token=" + ACCESS_TOKEN,
            StravaActivity.class
        );
        log.info(stravaActivity.toString());
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(stravaActivity));
    }

}
