package MaiOptiks.repos;

import MaiOptiks.domain.Firmenstamm;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FirmenstammRepository extends JpaRepository<Firmenstamm, String> {

    boolean existsByAugenoptikeriknrIgnoreCase(String augenoptikerIknr);

}
