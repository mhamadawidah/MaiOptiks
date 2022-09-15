package MaiOptiks.repos;

import MaiOptiks.domain.Kunde;
import org.springframework.data.jpa.repository.JpaRepository;


public interface KundeRepository extends JpaRepository<Kunde, Integer> {
}
