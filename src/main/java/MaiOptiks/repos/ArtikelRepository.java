package MaiOptiks.repos;

import MaiOptiks.domain.Artikel;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ArtikelRepository extends JpaRepository<Artikel, Integer> {
}
