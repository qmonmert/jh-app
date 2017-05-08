package fr.ippon.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A StravaActivity.
 */
@Entity
@Table(name = "strava_activity")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class StravaActivity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "jhi_type")
    private String type;

    @Column(name = "distance")
    private Float distance;

    @Column(name = "kudos_count")
    private Integer kudos_count;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public StravaActivity name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public StravaActivity type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Float getDistance() {
        return distance;
    }

    public StravaActivity distance(Float distance) {
        this.distance = distance;
        return this;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }

    public Integer getKudos_count() {
        return kudos_count;
    }

    public StravaActivity kudos_count(Integer kudos_count) {
        this.kudos_count = kudos_count;
        return this;
    }

    public void setKudos_count(Integer kudos_count) {
        this.kudos_count = kudos_count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StravaActivity stravaActivity = (StravaActivity) o;
        if (stravaActivity.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, stravaActivity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "StravaActivity{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", type='" + type + "'" +
            ", distance='" + distance + "'" +
            ", kudos_count='" + kudos_count + "'" +
            '}';
    }
}
