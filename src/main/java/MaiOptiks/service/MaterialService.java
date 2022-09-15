package MaiOptiks.service;

import MaiOptiks.domain.Material;
import MaiOptiks.model.MaterialDTO;
import MaiOptiks.repos.MaterialRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class MaterialService {

    private final MaterialRepository materialRepository;

    public MaterialService(final MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    public List<MaterialDTO> findAll() {
        return materialRepository.findAll(Sort.by("materialId"))
                .stream()
                .map(material -> mapToDTO(material, new MaterialDTO()))
                .collect(Collectors.toList());
    }

    public MaterialDTO get(final Integer materialId) {
        return materialRepository.findById(materialId)
                .map(material -> mapToDTO(material, new MaterialDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Integer create(final MaterialDTO materialDTO) {
        final Material material = new Material();
        mapToEntity(materialDTO, material);
        return materialRepository.save(material).getMaterialId();
    }

    public void update(final Integer materialId, final MaterialDTO materialDTO) {
        final Material material = materialRepository.findById(materialId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(materialDTO, material);
        materialRepository.save(material);
    }

    public void delete(final Integer materialId) {
        materialRepository.deleteById(materialId);
    }

    private MaterialDTO mapToDTO(final Material material, final MaterialDTO materialDTO) {
        materialDTO.setMaterialId(material.getMaterialId());
        materialDTO.setBezeichung(material.getBezeichung());
        materialDTO.setInfo(material.getInfo());
        return materialDTO;
    }

    private Material mapToEntity(final MaterialDTO materialDTO, final Material material) {
        material.setBezeichung(materialDTO.getBezeichung());
        material.setInfo(materialDTO.getInfo());
        return material;
    }

    @Transactional
    public String getReferencedWarning(final Integer materialId) {
        final Material material = materialRepository.findById(materialId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!material.getMaterialArtikels().isEmpty()) {
            return "";
        } else if (!material.getMaterialGlaesers().isEmpty()) {
            return "";
        } else if (!material.getMaterialFassungens().isEmpty()) {
            return "";
        } else if (!material.getMaterialKontaktlinsens().isEmpty()) {
            return "";
        }
        return null;
    }

}
