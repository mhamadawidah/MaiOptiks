package MaiOptiks.service;

import MaiOptiks.domain.Arzt;
import MaiOptiks.domain.Mitarbeiter;
import MaiOptiks.domain.RefraktionDurchgefuert;
import MaiOptiks.model.RefraktionDurchgefuertDTO;
import MaiOptiks.repos.ArztRepository;
import MaiOptiks.repos.MitarbeiterRepository;
import MaiOptiks.repos.RefraktionDurchgefuertRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class RefraktionDurchgefuertService {

    private final RefraktionDurchgefuertRepository refraktionDurchgefuertRepository;
    private final MitarbeiterRepository mitarbeiterRepository;
    private final ArztRepository arztRepository;

    public RefraktionDurchgefuertService(
            final RefraktionDurchgefuertRepository refraktionDurchgefuertRepository,
            final MitarbeiterRepository mitarbeiterRepository,
            final ArztRepository arztRepository) {
        this.refraktionDurchgefuertRepository = refraktionDurchgefuertRepository;
        this.mitarbeiterRepository = mitarbeiterRepository;
        this.arztRepository = arztRepository;
    }

    public List<RefraktionDurchgefuertDTO> findAll() {
        return refraktionDurchgefuertRepository.findAll(Sort.by("refraktionsnr"))
                .stream()
                .map(refraktionDurchgefuert -> mapToDTO(refraktionDurchgefuert, new RefraktionDurchgefuertDTO()))
                .collect(Collectors.toList());
    }

    public RefraktionDurchgefuertDTO get(final Integer refraktionsNr) {
        return refraktionDurchgefuertRepository.findById(refraktionsNr)
                .map(refraktionDurchgefuert -> mapToDTO(refraktionDurchgefuert, new RefraktionDurchgefuertDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Integer create(final RefraktionDurchgefuertDTO refraktionDurchgefuertDTO) {
        final RefraktionDurchgefuert refraktionDurchgefuert = new RefraktionDurchgefuert();
        mapToEntity(refraktionDurchgefuertDTO, refraktionDurchgefuert);
        return refraktionDurchgefuertRepository.save(refraktionDurchgefuert).getRefraktionsnr();
    }

    public void update(final Integer refraktionsNr,
            final RefraktionDurchgefuertDTO refraktionDurchgefuertDTO) {
        final RefraktionDurchgefuert refraktionDurchgefuert = refraktionDurchgefuertRepository.findById(refraktionsNr)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(refraktionDurchgefuertDTO, refraktionDurchgefuert);
        refraktionDurchgefuertRepository.save(refraktionDurchgefuert);
    }

    public void delete(final Integer refraktionsNr) {
        refraktionDurchgefuertRepository.deleteById(refraktionsNr);
    }

    private RefraktionDurchgefuertDTO mapToDTO(final RefraktionDurchgefuert refraktionDurchgefuert,
            final RefraktionDurchgefuertDTO refraktionDurchgefuertDTO) {
        refraktionDurchgefuertDTO.setRefraktionsNr(refraktionDurchgefuert.getRefraktionsnr());
        refraktionDurchgefuertDTO.setMitarbeiterNr(refraktionDurchgefuert.getMitarbeiternr() == null ? null : refraktionDurchgefuert.getMitarbeiternr().getMitarbeiternr());
        refraktionDurchgefuertDTO.setArztNr(refraktionDurchgefuert.getArztNr() == null ? null : refraktionDurchgefuert.getArztNr().getArztNr());
        return refraktionDurchgefuertDTO;
    }

    private RefraktionDurchgefuert mapToEntity(
            final RefraktionDurchgefuertDTO refraktionDurchgefuertDTO,
            final RefraktionDurchgefuert refraktionDurchgefuert) {
        final Mitarbeiter mitarbeiterNr = refraktionDurchgefuertDTO.getMitarbeiterNr() == null ? null : mitarbeiterRepository.findById(refraktionDurchgefuertDTO.getMitarbeiterNr())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "mitarbeiterNr not found"));
        refraktionDurchgefuert.setMitarbeiternr(mitarbeiterNr);
        final Arzt arztNr = refraktionDurchgefuertDTO.getArztNr() == null ? null : arztRepository.findById(refraktionDurchgefuertDTO.getArztNr())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "arztNr not found"));
        refraktionDurchgefuert.setArztNr(arztNr);
        return refraktionDurchgefuert;
    }

    @Transactional
    public String getReferencedWarning(final Integer refraktionsNr) {
        final RefraktionDurchgefuert refraktionDurchgefuert = refraktionDurchgefuertRepository.findById(refraktionsNr)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!refraktionDurchgefuert.getRefraktionAuftrags().isEmpty()) {
            return "";
        }
        return null;
    }

}
