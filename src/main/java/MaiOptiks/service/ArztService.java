package MaiOptiks.service;

import MaiOptiks.domain.Arzt;
import MaiOptiks.domain.Stadt;
import MaiOptiks.model.ArztDTO;
import MaiOptiks.repos.ArztRepository;
import MaiOptiks.repos.StadtRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ArztService {

    private final ArztRepository arztRepository;
    private final StadtRepository stadtRepository;

    public ArztService(final ArztRepository arztRepository, final StadtRepository stadtRepository) {
        this.arztRepository = arztRepository;
        this.stadtRepository = stadtRepository;
    }

    public List<ArztDTO> findAll() {
        return arztRepository.findAll(Sort.by("arztnr"))
                .stream()
                .map(arzt -> mapToDTO(arzt, new ArztDTO()))
                .collect(Collectors.toList());
    }

    public ArztDTO get(final Integer arztNr) {
        return arztRepository.findById(arztNr)
                .map(arzt -> mapToDTO(arzt, new ArztDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Integer create(final ArztDTO arztDTO) {
        final Arzt arzt = new Arzt();
        mapToEntity(arztDTO, arzt);
        return arztRepository.save(arzt).getArztnr();
    }

    public void update(final Integer arztNr, final ArztDTO arztDTO) {
        final Arzt arzt = arztRepository.findById(arztNr)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(arztDTO, arzt);
        arztRepository.save(arzt);
    }

    public void delete(final Integer arztNr) {
        arztRepository.deleteById(arztNr);
    }

    private ArztDTO mapToDTO(final Arzt arzt, final ArztDTO arztDTO) {
        arztDTO.setArztNr(arzt.getArztnr());
        arztDTO.setName(arzt.getName());
        arztDTO.setVorname(arzt.getVorname());
        arztDTO.setStrasse(arzt.getStrasse());
        arztDTO.setHausNr(arzt.getHausnr());
        arztDTO.setTelefonNr(arzt.getTelefonnr());
        arztDTO.setHandy(arzt.getHandy());
        arztDTO.setEmail(arzt.getEmail());
        arztDTO.setPlz(arzt.getPlz() == null ? null : arzt.getPlz().getPlz());
        return arztDTO;
    }

    private Arzt mapToEntity(final ArztDTO arztDTO, final Arzt arzt) {
        arzt.setName(arztDTO.getName());
        arzt.setVorname(arztDTO.getVorname());
        arzt.setStrasse(arztDTO.getStrasse());
        arzt.setHausnr(arztDTO.getHausNr());
        arzt.setTelefonnr(arztDTO.getTelefonNr());
        arzt.setHandy(arztDTO.getHandy());
        arzt.setEmail(arztDTO.getEmail());
        final Stadt plz = arztDTO.getPlz() == null ? null : stadtRepository.findById(arztDTO.getPlz())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "plz not found"));
        arzt.setPlz(plz);
        return arzt;
    }

    @Transactional
    public String getReferencedWarning(final Integer arztNr) {
        final Arzt arzt = arztRepository.findById(arztNr)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!arzt.getArztNrRefraktionDurchgefuerts().isEmpty()) {
            return "";
        }
        return null;
    }

}
