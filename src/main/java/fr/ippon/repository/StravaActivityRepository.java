package fr.ippon.repository;

import fr.ippon.domain.StravaActivity;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the StravaActivity entity.
 */
@SuppressWarnings("unused")
public interface StravaActivityRepository extends JpaRepository<StravaActivity,Long> {

}
