package MaiOptiks.repos;

import MaiOptiks.domain.Krankenkasse;
import org.springframework.data.jpa.repository.JpaRepository;


public interface KrankenkasseRepository extends JpaRepository<Krankenkasse, String> {

    boolean existsByKrankenkassennrIgnoreCase(String krankenkassennr);

}
