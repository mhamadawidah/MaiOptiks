package MaiOptiks.service;

import MaiOptiks.domain.Farbe;
import MaiOptiks.model.FarbeDTO;
import MaiOptiks.repos.FarbeRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class FarbeService {

    private final FarbeRepository farbeRepository;

    public FarbeService(final FarbeRepository farbeRepository) {
        this.farbeRepository = farbeRepository;
    }

    public List<FarbeDTO> findAll() {
        return farbeRepository.findAll(Sort.by("farbeId"))
                .stream()
                .map(farbe -> mapToDTO(farbe, new FarbeDTO()))
                .collect(Collectors.toList());
    }

    public FarbeDTO get(final Integer farbeId) {
        return farbeRepository.findById(farbeId)
                .map(farbe -> mapToDTO(farbe, new FarbeDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Integer create(final FarbeDTO farbeDTO) {
        final Farbe farbe = new Farbe();
        mapToEntity(farbeDTO, farbe);
        return farbeRepository.save(farbe).getFarbeId();
    }

    public void update(final Integer farbeId, final FarbeDTO farbeDTO) {
        final Farbe farbe = farbeRepository.findById(farbeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(farbeDTO, farbe);
        farbeRepository.save(farbe);
    }

    public void delete(final Integer farbeId) {
        farbeRepository.deleteById(farbeId);
    }

    private FarbeDTO mapToDTO(final Farbe farbe, final FarbeDTO farbeDTO) {
        farbeDTO.setFarbeId(farbe.getFarbeId());
        farbeDTO.setBezeichnung(farbe.getBezeichnung());
        farbeDTO.setInfo(farbe.getInfo());
        return farbeDTO;
    }

    private Farbe mapToEntity(final FarbeDTO farbeDTO, final Farbe farbe) {
        farbe.setBezeichnung(farbeDTO.getBezeichnung());
        farbe.setInfo(farbeDTO.getInfo());
        return farbe;
    }

    @Transactional
    public String getReferencedWarning(final Integer farbeId) {
        final Farbe farbe = farbeRepository.findById(farbeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!farbe.getFarbeArtikels().isEmpty()) {
            return "";
        } else if (!farbe.getFarbeGlaesers().isEmpty()) {
            return "";
        } else if (!farbe.getFarbeFassungens().isEmpty()) {
            return "";
        } else if (!farbe.getFarbeKontaktlinsens().isEmpty()) {
            return "";
        }
        return null;
    }

}
