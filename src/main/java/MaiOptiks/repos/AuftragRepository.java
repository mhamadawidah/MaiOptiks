package MaiOptiks.repos;

import MaiOptiks.domain.Auftrag;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AuftragRepository extends JpaRepository<Auftrag, Integer> {
}
