package MaiOptiks.service;

import MaiOptiks.domain.Artikel;
import MaiOptiks.domain.Farbe;
import MaiOptiks.domain.Lieferant;
import MaiOptiks.domain.Material;
import MaiOptiks.model.ArtikelDTO;
import MaiOptiks.repos.ArtikelRepository;
import MaiOptiks.repos.FarbeRepository;
import MaiOptiks.repos.LieferantRepository;
import MaiOptiks.repos.MaterialRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ArtikelService {

    private final ArtikelRepository artikelRepository;
    private final MaterialRepository materialRepository;
    private final FarbeRepository farbeRepository;
    private final LieferantRepository lieferantRepository;

    public ArtikelService(final ArtikelRepository artikelRepository,
            final MaterialRepository materialRepository, final FarbeRepository farbeRepository,
            final LieferantRepository lieferantRepository) {
        this.artikelRepository = artikelRepository;
        this.materialRepository = materialRepository;
        this.farbeRepository = farbeRepository;
        this.lieferantRepository = lieferantRepository;
    }

    public List<ArtikelDTO> findAll() {
        return artikelRepository.findAll(Sort.by("artikelNr"))
                .stream()
                .map(artikel -> mapToDTO(artikel, new ArtikelDTO()))
                .collect(Collectors.toList());
    }

    public ArtikelDTO get(final Integer artikelNr) {
        return artikelRepository.findById(artikelNr)
                .map(artikel -> mapToDTO(artikel, new ArtikelDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Integer create(final ArtikelDTO artikelDTO) {
        final Artikel artikel = new Artikel();
        mapToEntity(artikelDTO, artikel);
        return artikelRepository.save(artikel).getArtikelNr();
    }

    public void update(final Integer artikelNr, final ArtikelDTO artikelDTO) {
        final Artikel artikel = artikelRepository.findById(artikelNr)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(artikelDTO, artikel);
        artikelRepository.save(artikel);
    }

    public void delete(final Integer artikelNr) {
        artikelRepository.deleteById(artikelNr);
    }

    private ArtikelDTO mapToDTO(final Artikel artikel, final ArtikelDTO artikelDTO) {
        artikelDTO.setArtikelNr(artikel.getArtikelNr());
        artikelDTO.setBezeichnung(artikel.getBezeichnung());
        artikelDTO.setArtikelart(artikel.getArtikelart());
        artikelDTO.setEinkaufspreis(artikel.getEinkaufspreis());
        artikelDTO.setVerkaufspreis(artikel.getVerkaufspreis());
        artikelDTO.setMaterial(artikel.getMaterial() == null ? null : artikel.getMaterial().getMaterialId());
        artikelDTO.setFarbe(artikel.getFarbe() == null ? null : artikel.getFarbe().getFarbeId());
        artikelDTO.setLieferant(artikel.getLieferant() == null ? null : artikel.getLieferant().getLieferantId());
        return artikelDTO;
    }

    private Artikel mapToEntity(final ArtikelDTO artikelDTO, final Artikel artikel) {
        artikel.setBezeichnung(artikelDTO.getBezeichnung());
        artikel.setArtikelart(artikelDTO.getArtikelart());
        artikel.setEinkaufspreis(artikelDTO.getEinkaufspreis());
        artikel.setVerkaufspreis(artikelDTO.getVerkaufspreis());
        final Material material = artikelDTO.getMaterial() == null ? null : materialRepository.findById(artikelDTO.getMaterial())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "material not found"));
        artikel.setMaterial(material);
        final Farbe farbe = artikelDTO.getFarbe() == null ? null : farbeRepository.findById(artikelDTO.getFarbe())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "farbe not found"));
        artikel.setFarbe(farbe);
        final Lieferant lieferant = artikelDTO.getLieferant() == null ? null : lieferantRepository.findById(artikelDTO.getLieferant())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "lieferant not found"));
        artikel.setLieferant(lieferant);
        return artikel;
    }

    @Transactional
    public String getReferencedWarning(final Integer artikelNr) {
        final Artikel artikel = artikelRepository.findById(artikelNr)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!artikel.getGlasArtikelIdlinksBrilles().isEmpty()) {
            return "";
        } else if (!artikel.getGlasArtikelIdrechtsBrilles().isEmpty()) {
            return "";
        } else if (!artikel.getFassungsArtikelBrilles().isEmpty()) {
            return "";
        }
        return null;
    }

}
