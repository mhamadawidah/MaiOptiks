package MaiOptiks.repos;

import MaiOptiks.domain.Auftrag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;


public interface AuftragRepository extends JpaRepository<Auftrag, Integer> {

    @Query(value = "SELECT MAX(auftragsnummer) FROM auftrag", nativeQuery = true)
    BigDecimal getMaxId();

    @Query(value = "SELECT * FROM auftrag WHERE kundennr = :kundenNr", nativeQuery = true)
    List<Auftrag> getAuftragByKundenNr(@Param("kundenNr") Integer kundenNr);

/*    @Query(value = "SELECT * FROM auftrag WHERE beraterid = :beraterid", nativeQuery = true)
    List<Auftrag> getAuftragByBeraterId(@Param("beraterid") Integer beraterid);

    @Query(value = "SELECT * FROM auftrag WHERE WerkstadtId = :WerkstadtId", nativeQuery = true)
    List<Auftrag> getAuftragByWerkstadtId(@Param("WerkstadtId") Integer WerkstadtId);

    @Query(value = "SELECT * FROM auftrag WHERE Abrechnungsart = :Abrechnungsart", nativeQuery = true)
    List<Auftrag> getAuftragByAbrechnungsart(@Param("Abrechnungsart") Integer Abrechnungsart);

 */
    @Query(value = "SELECT * FROM auftrag WHERE (kundennr = :kundennr OR :kundennr = -1) AND (berater = :beraterid OR :beraterid = -1) AND (werkstatt = :werkstadtid OR :werkstadtid = -1) AND (abrechnungsid = :abrechnungsart OR :abrechnungsart = -1)", nativeQuery = true)
    List<Auftrag> getAuftragByX(
            @Param("kundennr") Integer kundennr,
            @Param("beraterid") Integer beraterid,
            @Param("werkstadtid") Integer werkstadtid,
            @Param("abrechnungsart") Integer abrechnungsart
    );
}
