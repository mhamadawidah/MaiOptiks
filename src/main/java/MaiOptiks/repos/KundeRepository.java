package MaiOptiks.repos;

import MaiOptiks.domain.Kunde;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface KundeRepository extends JpaRepository<Kunde, Integer> {
    List<Kunde> findAllByNameAndVorname(String name, String vorname);

}
