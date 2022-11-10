package MaiOptiks.repos;

import MaiOptiks.domain.Kunde;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface KundeRepository extends JpaRepository<Kunde, Integer> {

    @Query(value = "SELECT * FROM kunde k WHERE lower(k.name) LIKE concat('%', lower(:nameParam), '%') AND lower(k.vorname) LIKE concat('%', lower(:vornameParam), '%')", nativeQuery = true)
    List<Kunde> findAllByNameAndVorname(@Param("nameParam") String nameParam, @Param("vornameParam") String vornameParam);

    @Query(value = "SELECT * FROM kunde k WHERE lower(k.name) LIKE concat('%', lower(:nameParam), '%')", nativeQuery = true)
    List<Kunde> findAllByName(@Param("nameParam") String nameParam);

    @Query(value = "SELECT * FROM kunde k WHERE lower(k.vorname) LIKE concat('%', lower(:vornameParam), '%')", nativeQuery = true)
    List<Kunde> findAllByVorname(@Param("vornameParam") String vornameParam);
}
