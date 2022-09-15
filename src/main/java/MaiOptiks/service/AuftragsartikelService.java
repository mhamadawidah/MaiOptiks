package MaiOptiks.service;

import MaiOptiks.domain.Auftrag;
import MaiOptiks.domain.Auftragsartikel;
import MaiOptiks.domain.Brille;
import MaiOptiks.model.AuftragsartikelDTO;
import MaiOptiks.repos.AuftragRepository;
import MaiOptiks.repos.AuftragsartikelRepository;
import MaiOptiks.repos.BrilleRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class AuftragsartikelService {

    private final AuftragsartikelRepository auftragsartikelRepository;
    private final AuftragRepository auftragRepository;
    private final BrilleRepository brilleRepository;

    public AuftragsartikelService(final AuftragsartikelRepository auftragsartikelRepository,
            final AuftragRepository auftragRepository, final BrilleRepository brilleRepository) {
        this.auftragsartikelRepository = auftragsartikelRepository;
        this.auftragRepository = auftragRepository;
        this.brilleRepository = brilleRepository;
    }

    public List<AuftragsartikelDTO> findAll() {
        return auftragsartikelRepository.findAll(Sort.by("auftragsArtikelId"))
                .stream()
                .map(auftragsartikel -> mapToDTO(auftragsartikel, new AuftragsartikelDTO()))
                .collect(Collectors.toList());
    }

    public AuftragsartikelDTO get(final Integer auftragsArtikelId) {
        return auftragsartikelRepository.findById(auftragsArtikelId)
                .map(auftragsartikel -> mapToDTO(auftragsartikel, new AuftragsartikelDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Integer create(final AuftragsartikelDTO auftragsartikelDTO) {
        final Auftragsartikel auftragsartikel = new Auftragsartikel();
        mapToEntity(auftragsartikelDTO, auftragsartikel);
        return auftragsartikelRepository.save(auftragsartikel).getAuftragsArtikelId();
    }

    public void update(final Integer auftragsArtikelId,
            final AuftragsartikelDTO auftragsartikelDTO) {
        final Auftragsartikel auftragsartikel = auftragsartikelRepository.findById(auftragsArtikelId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(auftragsartikelDTO, auftragsartikel);
        auftragsartikelRepository.save(auftragsartikel);
    }

    public void delete(final Integer auftragsArtikelId) {
        auftragsartikelRepository.deleteById(auftragsArtikelId);
    }

    private AuftragsartikelDTO mapToDTO(final Auftragsartikel auftragsartikel,
            final AuftragsartikelDTO auftragsartikelDTO) {
        auftragsartikelDTO.setAuftragsArtikelId(auftragsartikel.getAuftragsArtikelId());
        auftragsartikelDTO.setSehhilfenArt(auftragsartikel.getSehhilfenArt());
        auftragsartikelDTO.setAuftragsNr(auftragsartikel.getAuftragsNr() == null ? null : auftragsartikel.getAuftragsNr().getAuftragsnummer());
        auftragsartikelDTO.setSehhilfe(auftragsartikel.getSehhilfe() == null ? null : auftragsartikel.getSehhilfe().getBrillenId());
        return auftragsartikelDTO;
    }

    private Auftragsartikel mapToEntity(final AuftragsartikelDTO auftragsartikelDTO,
            final Auftragsartikel auftragsartikel) {
        auftragsartikel.setSehhilfenArt(auftragsartikelDTO.getSehhilfenArt());
        final Auftrag auftragsNr = auftragsartikelDTO.getAuftragsNr() == null ? null : auftragRepository.findById(auftragsartikelDTO.getAuftragsNr())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "auftragsNr not found"));
        auftragsartikel.setAuftragsNr(auftragsNr);
        final Brille sehhilfe = auftragsartikelDTO.getSehhilfe() == null ? null : brilleRepository.findById(auftragsartikelDTO.getSehhilfe())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "sehhilfe not found"));
        auftragsartikel.setSehhilfe(sehhilfe);
        return auftragsartikel;
    }

}
