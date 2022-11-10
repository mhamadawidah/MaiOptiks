package MaiOptiks.service;

import MaiOptiks.domain.*;
import MaiOptiks.model.GlaeserDTO;
import MaiOptiks.repos.*;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class GlaeserService {

    private final GlaeserRepository glaeserRepository;
    private final ArtikelartRepository artikelartRepository;
    private final RefraktionRepository refraktionRepository;
    private final MaterialRepository materialRepository;
    private final FarbeRepository farbeRepository;
    private final LieferantRepository lieferantRepository;

    public GlaeserService(final GlaeserRepository glaeserRepository,
            final ArtikelartRepository artikelartRepository,
            final RefraktionRepository refraktionRepository,
            final MaterialRepository materialRepository, final FarbeRepository farbeRepository,
            final LieferantRepository lieferantRepository) {
        this.glaeserRepository = glaeserRepository;
        this.artikelartRepository = artikelartRepository;
        this.refraktionRepository = refraktionRepository;
        this.materialRepository = materialRepository;
        this.farbeRepository = farbeRepository;
        this.lieferantRepository = lieferantRepository;
    }

    public List<GlaeserDTO> findAll() {
        return glaeserRepository.findAll(Sort.by("artikelnr"))
                .stream()
                .map(glaeser -> mapToDTO(glaeser, new GlaeserDTO()))
                .collect(Collectors.toList());
    }

    public GlaeserDTO get(final Integer artikelNr) {
        return glaeserRepository.findById(artikelNr)
                .map(glaeser -> mapToDTO(glaeser, new GlaeserDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Integer create(final GlaeserDTO glaeserDTO) {
        final Glaeser glaeser = new Glaeser();
        mapToEntity(glaeserDTO, glaeser);
        return glaeserRepository.save(glaeser).getArtikelnr();
    }

    public void update(final Integer artikelNr, final GlaeserDTO glaeserDTO) {
        final Glaeser glaeser = glaeserRepository.findById(artikelNr)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(glaeserDTO, glaeser);
        glaeserRepository.save(glaeser);
    }

    public void delete(final Integer artikelNr) {
        glaeserRepository.deleteById(artikelNr);
    }

    private GlaeserDTO mapToDTO(final Glaeser glaeser, final GlaeserDTO glaeserDTO) {
        glaeserDTO.setArtikelNr(glaeser.getArtikelnr());
        glaeserDTO.setEinkaufspreis(glaeser.getEinkaufspreis());
        glaeserDTO.setVerkaufspreis(glaeser.getVerkaufspreis());
        glaeserDTO.setArt(glaeser.getArt() == null ? null : glaeser.getArt().getArtid());
        glaeserDTO.setWerte(glaeser.getWerte() == null ? null : glaeser.getWerte().getRefraktionid());
        glaeserDTO.setMaterial(glaeser.getMaterial() == null ? null : glaeser.getMaterial().getMaterialid());
        glaeserDTO.setFarbe(glaeser.getFarbe() == null ? null : glaeser.getFarbe().getFarbeid());
        glaeserDTO.setLieferant(glaeser.getLieferant() == null ? null : glaeser.getLieferant().getLieferantid());
        return glaeserDTO;
    }

    private Glaeser mapToEntity(final GlaeserDTO glaeserDTO, final Glaeser glaeser) {
        glaeser.setEinkaufspreis(glaeserDTO.getEinkaufspreis());
        glaeser.setVerkaufspreis(glaeserDTO.getVerkaufspreis());
        final Artikelart art = glaeserDTO.getArt() == null ? null : artikelartRepository.findById(glaeserDTO.getArt())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "art not found"));
        glaeser.setArt(art);
        final Refraktion werte = glaeserDTO.getWerte() == null ? null : refraktionRepository.findById(glaeserDTO.getWerte())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "werte not found"));
        glaeser.setWerte(werte);
        final Material material = glaeserDTO.getMaterial() == null ? null : materialRepository.findById(glaeserDTO.getMaterial())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "material not found"));
        glaeser.setMaterial(material);
        final Farbe farbe = glaeserDTO.getFarbe() == null ? null : farbeRepository.findById(glaeserDTO.getFarbe())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "farbe not found"));
        glaeser.setFarbe(farbe);
        final Lieferant lieferant = glaeserDTO.getLieferant() == null ? null : lieferantRepository.findById(glaeserDTO.getLieferant())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "lieferant not found"));
        glaeser.setLieferant(lieferant);
        return glaeser;
    }

}
