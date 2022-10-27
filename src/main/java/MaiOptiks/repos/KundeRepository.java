package MaiOptiks.repos;

import MaiOptiks.domain.Kunde;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KundeRepository extends JpaRepository<Kunde, Integer> {
    Optional<Kunde> findByNameAndVorname(String name, String vorname);
}
