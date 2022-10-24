package MaiOptiks.service;

import MaiOptiks.domain.Refraktion;
import MaiOptiks.model.RefraktionDTO;
import MaiOptiks.repos.RefraktionRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class RefraktionService {

    private final RefraktionRepository refraktionRepository;

    public RefraktionService(final RefraktionRepository refraktionRepository) {
        this.refraktionRepository = refraktionRepository;
    }

    public List<RefraktionDTO> findAll() {
        return refraktionRepository.findAll(Sort.by("refraktionid"))
                .stream()
                .map(refraktion -> mapToDTO(refraktion, new RefraktionDTO()))
                .collect(Collectors.toList());
    }

    public RefraktionDTO get(final Integer refraktionId) {
        return refraktionRepository.findById(refraktionId)
                .map(refraktion -> mapToDTO(refraktion, new RefraktionDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Integer create(final RefraktionDTO refraktionDTO) {
        final Refraktion refraktion = new Refraktion();
        mapToEntity(refraktionDTO, refraktion);
        return refraktionRepository.save(refraktion).getRefraktionid();
    }

    public void update(final Integer refraktionId, final RefraktionDTO refraktionDTO) {
        final Refraktion refraktion = refraktionRepository.findById(refraktionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(refraktionDTO, refraktion);
        refraktionRepository.save(refraktion);
    }

    public void delete(final Integer refraktionId) {
        refraktionRepository.deleteById(refraktionId);
    }

    private RefraktionDTO mapToDTO(final Refraktion refraktion, final RefraktionDTO refraktionDTO) {
        refraktionDTO.setRefraktionId(refraktion.getRefraktionid());
        refraktionDTO.setSph(refraktion.getSph());
        refraktionDTO.setCyl(refraktion.getCyl());
        refraktionDTO.setAch(refraktion.getAch());
        refraktionDTO.setAdds(refraktion.getAdds());
        refraktionDTO.setPris(refraktion.getPris());
        refraktionDTO.setBas(refraktion.getBas());
        refraktionDTO.setVisus(refraktion.getVisus());
        return refraktionDTO;
    }

    private Refraktion mapToEntity(final RefraktionDTO refraktionDTO, final Refraktion refraktion) {
        refraktion.setSph(refraktionDTO.getSph());
        refraktion.setCyl(refraktionDTO.getCyl());
        refraktion.setAch(refraktionDTO.getAch());
        refraktion.setAdds(refraktionDTO.getAdds());
        refraktion.setPris(refraktionDTO.getPris());
        refraktion.setBas(refraktionDTO.getBas());
        refraktion.setVisus(refraktionDTO.getVisus());
        return refraktion;
    }

    @Transactional
    public String getReferencedWarning(final Integer refraktionId) {
        final Refraktion refraktion = refraktionRepository.findById(refraktionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!refraktion.getRefraktionHornhauts().isEmpty()) {
            return "";
        } else if (!refraktion.getWerteGlaesers().isEmpty()) {
            return "";
        }
        return null;
    }

}
