package MaiOptiks.service;

import MaiOptiks.domain.Lieferant;
import MaiOptiks.domain.Stadt;
import MaiOptiks.model.LieferantDTO;
import MaiOptiks.repos.LieferantRepository;
import MaiOptiks.repos.StadtRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class LieferantService {

    private final LieferantRepository lieferantRepository;
    private final StadtRepository stadtRepository;

    public LieferantService(final LieferantRepository lieferantRepository,
            final StadtRepository stadtRepository) {
        this.lieferantRepository = lieferantRepository;
        this.stadtRepository = stadtRepository;
    }

    public List<LieferantDTO> findAll() {
        return lieferantRepository.findAll(Sort.by("lieferantid"))
                .stream()
                .map(lieferant -> mapToDTO(lieferant, new LieferantDTO()))
                .collect(Collectors.toList());
    }

    public LieferantDTO get(final Integer lieferantId) {
        return lieferantRepository.findById(lieferantId)
                .map(lieferant -> mapToDTO(lieferant, new LieferantDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Integer create(final LieferantDTO lieferantDTO) {
        final Lieferant lieferant = new Lieferant();
        mapToEntity(lieferantDTO, lieferant);
        return lieferantRepository.save(lieferant).getLieferantid();
    }

    public void update(final Integer lieferantId, final LieferantDTO lieferantDTO) {
        final Lieferant lieferant = lieferantRepository.findById(lieferantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(lieferantDTO, lieferant);
        lieferantRepository.save(lieferant);
    }

    public void delete(final Integer lieferantId) {
        lieferantRepository.deleteById(lieferantId);
    }

    private LieferantDTO mapToDTO(final Lieferant lieferant, final LieferantDTO lieferantDTO) {
        lieferantDTO.setLieferantId(lieferant.getLieferantid());
        lieferantDTO.setName(lieferant.getName());
        lieferantDTO.setStrasse(lieferant.getStrasse());
        lieferantDTO.setTelefonNr(lieferant.getTelefonnr());
        lieferantDTO.setPlz(lieferant.getPlz() == null ? null : lieferant.getPlz().getPlz());
        return lieferantDTO;
    }

    private Lieferant mapToEntity(final LieferantDTO lieferantDTO, final Lieferant lieferant) {
        lieferant.setName(lieferantDTO.getName());
        lieferant.setStrasse(lieferantDTO.getStrasse());
        lieferant.setTelefonnr(lieferantDTO.getTelefonNr());
        final Stadt plz = lieferantDTO.getPlz() == null ? null : stadtRepository.findById(lieferantDTO.getPlz())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "plz not found"));
        lieferant.setPlz(plz);
        return lieferant;
    }

    @Transactional
    public String getReferencedWarning(final Integer lieferantId) {
        final Lieferant lieferant = lieferantRepository.findById(lieferantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!lieferant.getLieferantArtikels().isEmpty()) {
            return "";
        } else if (!lieferant.getLieferantGlaesers().isEmpty()) {
            return "";
        } else if (!lieferant.getLieferantFassungens().isEmpty()) {
            return "";
        } else if (!lieferant.getLieferantKontaktlinsens().isEmpty()) {
            return "";
        }
        return null;
    }

}
