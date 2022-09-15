package MaiOptiks.service;

import MaiOptiks.domain.Abrechnungsart;
import MaiOptiks.model.AbrechnungsartDTO;
import MaiOptiks.repos.AbrechnungsartRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class AbrechnungsartService {

    private final AbrechnungsartRepository abrechnungsartRepository;

    public AbrechnungsartService(final AbrechnungsartRepository abrechnungsartRepository) {
        this.abrechnungsartRepository = abrechnungsartRepository;
    }

    public List<AbrechnungsartDTO> findAll() {
        return abrechnungsartRepository.findAll(Sort.by("id"))
                .stream()
                .map(abrechnungsart -> mapToDTO(abrechnungsart, new AbrechnungsartDTO()))
                .collect(Collectors.toList());
    }

    public AbrechnungsartDTO get(final Integer id) {
        return abrechnungsartRepository.findById(id)
                .map(abrechnungsart -> mapToDTO(abrechnungsart, new AbrechnungsartDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Integer create(final AbrechnungsartDTO abrechnungsartDTO) {
        final Abrechnungsart abrechnungsart = new Abrechnungsart();
        mapToEntity(abrechnungsartDTO, abrechnungsart);
        return abrechnungsartRepository.save(abrechnungsart).getId();
    }

    public void update(final Integer id, final AbrechnungsartDTO abrechnungsartDTO) {
        final Abrechnungsart abrechnungsart = abrechnungsartRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(abrechnungsartDTO, abrechnungsart);
        abrechnungsartRepository.save(abrechnungsart);
    }

    public void delete(final Integer id) {
        abrechnungsartRepository.deleteById(id);
    }

    private AbrechnungsartDTO mapToDTO(final Abrechnungsart abrechnungsart,
            final AbrechnungsartDTO abrechnungsartDTO) {
        abrechnungsartDTO.setId(abrechnungsart.getId());
        abrechnungsartDTO.setArt(abrechnungsart.getArt());
        return abrechnungsartDTO;
    }

    private Abrechnungsart mapToEntity(final AbrechnungsartDTO abrechnungsartDTO,
            final Abrechnungsart abrechnungsart) {
        abrechnungsart.setArt(abrechnungsartDTO.getArt());
        return abrechnungsart;
    }

    @Transactional
    public String getReferencedWarning(final Integer id) {
        final Abrechnungsart abrechnungsart = abrechnungsartRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!abrechnungsart.getAbrechnungsAuftrags().isEmpty()) {
            return "";
        }
        return null;
    }

}
