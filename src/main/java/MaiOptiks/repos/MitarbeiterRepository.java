package MaiOptiks.repos;

import MaiOptiks.domain.Mitarbeiter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface MitarbeiterRepository extends JpaRepository<Mitarbeiter, Integer> {
    @Query(value = "SELECT * FROM mitarbeiter m WHERE lower(m.name) LIKE concat('%', lower(:nameParam), '%') AND lower(m.vorname) LIKE concat('%', lower(:vornameParam), '%')", nativeQuery = true)
    List<Mitarbeiter> findAllByNameAndVorname(@Param("nameParam") String nameParam, @Param("vornameParam") String vornameParam);

    @Query(value = "SELECT * FROM mitarbeiter m WHERE lower(m.name) LIKE concat('%', lower(:nameParam), '%')", nativeQuery = true)
    List<Mitarbeiter> findAllByName(@Param("nameParam") String nameParam);

    @Query(value = "SELECT * FROM mitarbeiter m WHERE lower(m.vorname) LIKE concat('%', lower(:vornameParam), '%')", nativeQuery = true)
    List<Mitarbeiter> findAllByVorname(@Param("vornameParam") String vornameParam);
}
