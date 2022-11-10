package MaiOptiks.repos;

import MaiOptiks.domain.Auftrag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;


public interface AuftragRepository extends JpaRepository<Auftrag, Integer> {

    @Query(value = "select nextval('primary_sequence')", nativeQuery = true)
    public BigDecimal getNextId();
}
