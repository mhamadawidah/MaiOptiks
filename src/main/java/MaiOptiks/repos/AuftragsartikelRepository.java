package MaiOptiks.repos;

import MaiOptiks.domain.Auftragsartikel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface AuftragsartikelRepository extends JpaRepository<Auftragsartikel, Integer> {

    @Query(value = "SELECT * FROM Auftragsartikel WHERE auftragsNr = :auftragsNr", nativeQuery = true)
    List<Auftragsartikel> findAllByAuftragsNr(Integer auftragsNr);
}
