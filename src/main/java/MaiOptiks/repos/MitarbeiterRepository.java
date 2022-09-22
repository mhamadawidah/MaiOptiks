package MaiOptiks.repos;

import MaiOptiks.domain.Mitarbeiter;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MitarbeiterRepository extends JpaRepository<Mitarbeiter, Integer> {
}
