package MaiOptiks.service;

import MaiOptiks.domain.*;
import MaiOptiks.model.AuftragDTO;
import MaiOptiks.repos.*;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class AuftragService {

    private final AuftragRepository auftragRepository;
    private final KundeRepository kundeRepository;
    private final MitarbeiterRepository mitarbeiterRepository;
    private final RefraktionDurchgefuertRepository refraktionDurchgefuertRepository;
    private final AbrechnungsartRepository abrechnungsartRepository;

    public AuftragService(final AuftragRepository auftragRepository,
            final KundeRepository kundeRepository,
            final MitarbeiterRepository mitarbeiterRepository,
            final RefraktionDurchgefuertRepository refraktionDurchgefuertRepository,
            final AbrechnungsartRepository abrechnungsartRepository) {
        this.auftragRepository = auftragRepository;
        this.kundeRepository = kundeRepository;
        this.mitarbeiterRepository = mitarbeiterRepository;
        this.refraktionDurchgefuertRepository = refraktionDurchgefuertRepository;
        this.abrechnungsartRepository = abrechnungsartRepository;
    }

    public List<AuftragDTO> findAll() {
        return auftragRepository.findAll(Sort.by("auftragsnummer"))
                .stream()
                .map(auftrag -> mapToDTO(auftrag, new AuftragDTO()))
                .collect(Collectors.toList());
    }

    public AuftragDTO get(final Integer auftragsnummer) {
        return auftragRepository.findById(auftragsnummer)
                .map(auftrag -> mapToDTO(auftrag, new AuftragDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Integer create(final AuftragDTO auftragDTO) {
        final Auftrag auftrag = new Auftrag();
        mapToEntity(auftragDTO, auftrag);
        return auftragRepository.save(auftrag).getAuftragsnummer();
    }

    public void update(final Integer auftragsnummer, final AuftragDTO auftragDTO) {
        final Auftrag auftrag = auftragRepository.findById(auftragsnummer)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(auftragDTO, auftrag);
        auftragRepository.save(auftrag);
    }

    public void delete(final Integer auftragsnummer) {
        auftragRepository.deleteById(auftragsnummer);
    }

    private AuftragDTO mapToDTO(final Auftrag auftrag, final AuftragDTO auftragDTO) {
        auftragDTO.setAuftragsnummer(auftrag.getAuftragsnummer());
        auftragDTO.setRezepturvorhanden(auftrag.getRezepturvorhanden());
        auftragDTO.setWomit(auftrag.getWomit());
        auftragDTO.setWann(auftrag.getWann());
        auftragDTO.setFertig(auftrag.getFertig());
        auftragDTO.setAbgeholt(auftrag.getAbgeholt());
        auftragDTO.setBezahlt(auftrag.getBezahlt());
        auftragDTO.setAuftragsbestaetigung(auftrag.getAuftragsbestaetigung());
        auftragDTO.setRechnung(auftrag.getRechnung());
        auftragDTO.setErsteMahnung(auftrag.getErstemahnung());
        auftragDTO.setZweiteMahnung(auftrag.getZweitemahnung());
        auftragDTO.setDritteMahnung(auftrag.getDrittemahnung());
        auftragDTO.setDatum(auftrag.getDatum());
        auftragDTO.setKundenNr(auftrag.getKundenNr() == null ? null : auftrag.getKundenNr().getKundenNr());
        auftragDTO.setBerater(auftrag.getBerater() == null ? null : auftrag.getBerater().getMitarbeiterNr());
        auftragDTO.setWerkstatt(auftrag.getWerkstatt() == null ? null : auftrag.getWerkstatt().getMitarbeiterNr());
        auftragDTO.setRefraktion(auftrag.getRefraktion() == null ? null : auftrag.getRefraktion().getRefraktionsNr());
        auftragDTO.setAbrechnungs(auftrag.getAbrechnungs() == null ? null : auftrag.getAbrechnungs().getId());
        return auftragDTO;
    }

    private Auftrag mapToEntity(final AuftragDTO auftragDTO, final Auftrag auftrag) {
        auftrag.setRezepturvorhanden(auftragDTO.getRezepturvorhanden());
        auftrag.setWomit(auftragDTO.getWomit());
        auftrag.setWann(auftragDTO.getWann());
        auftrag.setFertig(auftragDTO.getFertig());
        auftrag.setAbgeholt(auftragDTO.getAbgeholt());
        auftrag.setBezahlt(auftragDTO.getBezahlt());
        auftrag.setAuftragsbestaetigung(auftragDTO.getAuftragsbestaetigung());
        auftrag.setRechnung(auftragDTO.getRechnung());
        auftrag.setErstemahnung(auftragDTO.getErsteMahnung());
        auftrag.setZweitemahnung(auftragDTO.getZweiteMahnung());
        auftrag.setDrittemahnung(auftragDTO.getDritteMahnung());
        auftrag.setDatum(auftragDTO.getDatum());
        final Kunde kundenNr = auftragDTO.getKundenNr() == null ? null : kundeRepository.findById(auftragDTO.getKundenNr())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "kundenNr not found"));
        auftrag.setKundenNr(kundenNr);
        final Mitarbeiter berater = auftragDTO.getBerater() == null ? null : mitarbeiterRepository.findById(auftragDTO.getBerater())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "berater not found"));
        auftrag.setBerater(berater);
        final Mitarbeiter werkstatt = auftragDTO.getWerkstatt() == null ? null : mitarbeiterRepository.findById(auftragDTO.getWerkstatt())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "werkstatt not found"));
        auftrag.setWerkstatt(werkstatt);
        final RefraktionDurchgefuert refraktion = auftragDTO.getRefraktion() == null ? null : refraktionDurchgefuertRepository.findById(auftragDTO.getRefraktion())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "refraktion not found"));
        auftrag.setRefraktion(refraktion);
        final Abrechnungsart abrechnungs = auftragDTO.getAbrechnungs() == null ? null : abrechnungsartRepository.findById(auftragDTO.getAbrechnungs())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "abrechnungs not found"));
        auftrag.setAbrechnungs(abrechnungs);
        return auftrag;
    }

    @Transactional
    public String getReferencedWarning(final Integer auftragsnummer) {
        final Auftrag auftrag = auftragRepository.findById(auftragsnummer)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!auftrag.getAuftragsNrAuftragsartikels().isEmpty()) {
            return "";
        }
        return null;
    }

}
