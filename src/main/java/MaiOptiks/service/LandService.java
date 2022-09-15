package MaiOptiks.service;

import MaiOptiks.domain.Land;
import MaiOptiks.model.LandDTO;
import MaiOptiks.repos.LandRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class LandService {

    private final LandRepository landRepository;

    public LandService(final LandRepository landRepository) {
        this.landRepository = landRepository;
    }

    public List<LandDTO> findAll() {
        return landRepository.findAll(Sort.by("landId"))
                .stream()
                .map(land -> mapToDTO(land, new LandDTO()))
                .collect(Collectors.toList());
    }

    public LandDTO get(final String landId) {
        return landRepository.findById(landId)
                .map(land -> mapToDTO(land, new LandDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public String create(final LandDTO landDTO) {
        final Land land = new Land();
        mapToEntity(landDTO, land);
        land.setLandId(landDTO.getLandId());
        return landRepository.save(land).getLandId();
    }

    public void update(final String landId, final LandDTO landDTO) {
        final Land land = landRepository.findById(landId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(landDTO, land);
        landRepository.save(land);
    }

    public void delete(final String landId) {
        landRepository.deleteById(landId);
    }

    private LandDTO mapToDTO(final Land land, final LandDTO landDTO) {
        landDTO.setLandId(land.getLandId());
        landDTO.setName(land.getName());
        return landDTO;
    }

    private Land mapToEntity(final LandDTO landDTO, final Land land) {
        land.setName(landDTO.getName());
        return land;
    }

    public boolean landIdExists(final String landId) {
        return landRepository.existsByLandIdIgnoreCase(landId);
    }

    @Transactional
    public String getReferencedWarning(final String landId) {
        final Land land = landRepository.findById(landId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!land.getLandStadts().isEmpty()) {
            return "";
        }
        return null;
    }

}
