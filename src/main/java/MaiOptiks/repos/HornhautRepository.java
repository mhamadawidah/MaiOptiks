package MaiOptiks.repos;

import MaiOptiks.domain.Hornhaut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface HornhautRepository extends JpaRepository<Hornhaut, Integer> {

    @Query(value = "SELECT * FROM Hornhaut WHERE RefraktionID = :RefraktionID", nativeQuery = true)
    List<Hornhaut> findAllBzRefraktionId(Integer RefraktionID);
}
