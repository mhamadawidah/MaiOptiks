package MaiOptiks.repos;

import MaiOptiks.domain.Land;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LandRepository extends JpaRepository<Land, String> {

    boolean existsByLandIdIgnoreCase(String landId);

}
