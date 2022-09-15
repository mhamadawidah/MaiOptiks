package MaiOptiks.service;

import MaiOptiks.domain.Land;
import MaiOptiks.domain.Stadt;
import MaiOptiks.model.StadtDTO;
import MaiOptiks.repos.LandRepository;
import MaiOptiks.repos.StadtRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class StadtService {

    private final StadtRepository stadtRepository;
    private final LandRepository landRepository;

    public StadtService(final StadtRepository stadtRepository,
            final LandRepository landRepository) {
        this.stadtRepository = stadtRepository;
        this.landRepository = landRepository;
    }

    public List<StadtDTO> findAll() {
        return stadtRepository.findAll(Sort.by("plz"))
                .stream()
                .map(stadt -> mapToDTO(stadt, new StadtDTO()))
                .collect(Collectors.toList());
    }

    public StadtDTO get(final Integer plz) {
        return stadtRepository.findById(plz)
                .map(stadt -> mapToDTO(stadt, new StadtDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Integer create(final StadtDTO stadtDTO) {
        final Stadt stadt = new Stadt();
        mapToEntity(stadtDTO, stadt);
        return stadtRepository.save(stadt).getPlz();
    }

    public void update(final Integer plz, final StadtDTO stadtDTO) {
        final Stadt stadt = stadtRepository.findById(plz)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(stadtDTO, stadt);
        stadtRepository.save(stadt);
    }

    public void delete(final Integer plz) {
        stadtRepository.deleteById(plz);
    }

    private StadtDTO mapToDTO(final Stadt stadt, final StadtDTO stadtDTO) {
        stadtDTO.setPlz(stadt.getPlz());
        stadtDTO.setOrt(stadt.getOrt());
        stadtDTO.setLand(stadt.getLand() == null ? null : stadt.getLand().getLandId());
        return stadtDTO;
    }

    private Stadt mapToEntity(final StadtDTO stadtDTO, final Stadt stadt) {
        stadt.setOrt(stadtDTO.getOrt());
        final Land land = stadtDTO.getLand() == null ? null : landRepository.findById(stadtDTO.getLand())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "land not found"));
        stadt.setLand(land);
        return stadt;
    }

    @Transactional
    public String getReferencedWarning(final Integer plz) {
        final Stadt stadt = stadtRepository.findById(plz)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!stadt.getPlzKrankenkasses().isEmpty()) {
            return "";
        } else if (!stadt.getPlzKundes().isEmpty()) {
            return "";
        } else if (!stadt.getPlzFirmenstamms().isEmpty()) {
            return "";
        } else if (!stadt.getPlzMitarbeiters().isEmpty()) {
            return "";
        } else if (!stadt.getPlzArzts().isEmpty()) {
            return "";
        } else if (!stadt.getPlzLieferants().isEmpty()) {
            return "";
        }
        return null;
    }

}
