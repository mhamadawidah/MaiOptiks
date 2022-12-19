package MaiOptiks.service;

import MaiOptiks.domain.Mitarbeiter;
import MaiOptiks.domain.Stadt;
import MaiOptiks.model.MitarbeiterDTO;
import MaiOptiks.repos.MitarbeiterRepository;
import MaiOptiks.repos.StadtRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class MitarbeiterService {

    private final MitarbeiterRepository mitarbeiterRepository;
    private final StadtRepository stadtRepository;

    public MitarbeiterService(final MitarbeiterRepository mitarbeiterRepository,
            final StadtRepository stadtRepository) {
        this.mitarbeiterRepository = mitarbeiterRepository;
        this.stadtRepository = stadtRepository;
    }

    public List<MitarbeiterDTO> findAll() {
        return mitarbeiterRepository.findAll(Sort.by("mitarbeiternr"))
                .stream()
                .map(mitarbeiter -> mapToDTO(mitarbeiter, new MitarbeiterDTO()))
                .collect(Collectors.toList());
    }

    public MitarbeiterDTO get(final Integer mitarbeiterNr) {
        return mitarbeiterRepository.findById(mitarbeiterNr)
                .map(mitarbeiter -> mapToDTO(mitarbeiter, new MitarbeiterDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Integer create(final MitarbeiterDTO mitarbeiterDTO) {
        final Mitarbeiter mitarbeiter = new Mitarbeiter();
        mapToEntity(mitarbeiterDTO, mitarbeiter);
        return mitarbeiterRepository.save(mitarbeiter).getMitarbeiternr();
    }

    public void update(final Integer mitarbeiterNr, final MitarbeiterDTO mitarbeiterDTO) {
        final Mitarbeiter mitarbeiter = mitarbeiterRepository.findById(mitarbeiterNr)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(mitarbeiterDTO, mitarbeiter);
        mitarbeiterRepository.save(mitarbeiter);
    }

    public List<MitarbeiterDTO> getByNameAndVorname(final String name, final String vorname) {
        return mitarbeiterRepository.findAllByNameAndVorname(name, vorname)
                .stream()
                .map(mitarbeiter -> MitarbeiterService.this.mapToDTO(mitarbeiter, new MitarbeiterDTO()))
                .collect(Collectors.toList());
    }

    public List<MitarbeiterDTO> getByName(final String name) {
        return mitarbeiterRepository.findAllByName(name)
                .stream()
                .map(mitarbeiter -> this.mapToDTO(mitarbeiter, new MitarbeiterDTO()))
                .collect(Collectors.toList());
    }

    public List<MitarbeiterDTO> getByVorname(final String vorname) {
        return mitarbeiterRepository.findAllByVorname(vorname)
                .stream()
                .map(mitarbeiterDTO -> MitarbeiterService.this.mapToDTO(mitarbeiterDTO, new MitarbeiterDTO()))
                .collect(Collectors.toList());
    }

    public void delete(final Integer mitarbeiterNr) {
        mitarbeiterRepository.deleteById(mitarbeiterNr);
    }

    private MitarbeiterDTO mapToDTO(final Mitarbeiter mitarbeiter,
            final MitarbeiterDTO mitarbeiterDTO) {
        mitarbeiterDTO.setMitarbeiterNr(mitarbeiter.getMitarbeiternr());
        mitarbeiterDTO.setName(mitarbeiter.getName());
        mitarbeiterDTO.setVorname(mitarbeiter.getVorname());
        mitarbeiterDTO.setStrasse(mitarbeiter.getStrasse());
        mitarbeiterDTO.setHausNr(mitarbeiter.getHausnr());
        mitarbeiterDTO.setTelefonNr(mitarbeiter.getTelefonnr());
        mitarbeiterDTO.setHandy(mitarbeiter.getHandy());
        mitarbeiterDTO.setEmail(mitarbeiter.getEmail());
        mitarbeiterDTO.setGeburtsdatum(mitarbeiter.getGeburtsdatum());
        mitarbeiterDTO.setPlz(mitarbeiter.getPlz() == null ? null : mitarbeiter.getPlz().getPlz());
        return mitarbeiterDTO;
    }

    private Mitarbeiter mapToEntity(final MitarbeiterDTO mitarbeiterDTO,
            final Mitarbeiter mitarbeiter) {
        mitarbeiter.setName(mitarbeiterDTO.getName());
        mitarbeiter.setVorname(mitarbeiterDTO.getVorname());
        mitarbeiter.setStrasse(mitarbeiterDTO.getStrasse());
        mitarbeiter.setHausnr(mitarbeiterDTO.getHausNr());
        mitarbeiter.setTelefonnr(mitarbeiterDTO.getTelefonNr());
        mitarbeiter.setHandy(mitarbeiterDTO.getHandy());
        mitarbeiter.setEmail(mitarbeiterDTO.getEmail());
        mitarbeiter.setGeburtsdatum(mitarbeiterDTO.getGeburtsdatum());
        final Stadt plz = mitarbeiterDTO.getPlz() == null ? null : stadtRepository.findById(mitarbeiterDTO.getPlz())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "plz not found"));
        mitarbeiter.setPlz(plz);
        return mitarbeiter;
    }

    @Transactional
    public String getReferencedWarning(final Integer mitarbeiterNr) {
        final Mitarbeiter mitarbeiter = mitarbeiterRepository.findById(mitarbeiterNr)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!mitarbeiter.getMitarbeiterNrRefraktionDurchgefuerts().isEmpty()) {
            return "";
        } else if (!mitarbeiter.getBeraterAuftrags().isEmpty()) {
            return "";
        } else if (!mitarbeiter.getWerkstattAuftrags().isEmpty()) {
            return "";
        }
        return null;
    }

}
