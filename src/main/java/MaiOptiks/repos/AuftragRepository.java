package MaiOptiks.repos;

import MaiOptiks.domain.Auftrag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;


public interface AuftragRepository extends JpaRepository<Auftrag, Integer> {

    @Query(value = "SELECT MAX(auftragsnummer) FROM auftrag", nativeQuery = true)
    BigDecimal getMaxId();
}
