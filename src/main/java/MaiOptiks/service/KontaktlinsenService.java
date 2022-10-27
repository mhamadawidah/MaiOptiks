package MaiOptiks.service;

import MaiOptiks.domain.*;
import MaiOptiks.model.KontaktlinsenDTO;
import MaiOptiks.repos.*;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class KontaktlinsenService {

    private final KontaktlinsenRepository kontaktlinsenRepository;
    private final ArtikelartRepository artikelartRepository;
    private final MaterialRepository materialRepository;
    private final FarbeRepository farbeRepository;
    private final LieferantRepository lieferantRepository;

    public KontaktlinsenService(final KontaktlinsenRepository kontaktlinsenRepository,
            final ArtikelartRepository artikelartRepository,
            final MaterialRepository materialRepository, final FarbeRepository farbeRepository,
            final LieferantRepository lieferantRepository) {
        this.kontaktlinsenRepository = kontaktlinsenRepository;
        this.artikelartRepository = artikelartRepository;
        this.materialRepository = materialRepository;
        this.farbeRepository = farbeRepository;
        this.lieferantRepository = lieferantRepository;
    }

    public List<KontaktlinsenDTO> findAll() {
        return kontaktlinsenRepository.findAll(Sort.by("artikelNr"))
                .stream()
                .map(kontaktlinsen -> mapToDTO(kontaktlinsen, new KontaktlinsenDTO()))
                .collect(Collectors.toList());
    }

    public KontaktlinsenDTO get(final Integer artikelNr) {
        return kontaktlinsenRepository.findById(artikelNr)
                .map(kontaktlinsen -> mapToDTO(kontaktlinsen, new KontaktlinsenDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Integer create(final KontaktlinsenDTO kontaktlinsenDTO) {
        final Kontaktlinsen kontaktlinsen = new Kontaktlinsen();
        mapToEntity(kontaktlinsenDTO, kontaktlinsen);
        return kontaktlinsenRepository.save(kontaktlinsen).getArtikelNr();
    }

    public void update(final Integer artikelNr, final KontaktlinsenDTO kontaktlinsenDTO) {
        final Kontaktlinsen kontaktlinsen = kontaktlinsenRepository.findById(artikelNr)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(kontaktlinsenDTO, kontaktlinsen);
        kontaktlinsenRepository.save(kontaktlinsen);
    }

    public void delete(final Integer artikelNr) {
        kontaktlinsenRepository.deleteById(artikelNr);
    }

    private KontaktlinsenDTO mapToDTO(final Kontaktlinsen kontaktlinsen,
            final KontaktlinsenDTO kontaktlinsenDTO) {
        kontaktlinsenDTO.setArtikelNr(kontaktlinsen.getArtikelNr());
        kontaktlinsenDTO.setEinkaufspreis(kontaktlinsen.getEinkaufspreis());
        kontaktlinsenDTO.setVerkaufspreis(kontaktlinsen.getVerkaufspreis());
        kontaktlinsenDTO.setArt(kontaktlinsen.getArt() == null ? null : kontaktlinsen.getArt().getArtId());
        kontaktlinsenDTO.setMaterial(kontaktlinsen.getMaterial() == null ? null : kontaktlinsen.getMaterial().getMaterialid());
        kontaktlinsenDTO.setFarbe(kontaktlinsen.getFarbe() == null ? null : kontaktlinsen.getFarbe().getFarbeId());
        kontaktlinsenDTO.setLieferant(kontaktlinsen.getLieferant() == null ? null : kontaktlinsen.getLieferant().getLieferantid());
        return kontaktlinsenDTO;
    }

    private Kontaktlinsen mapToEntity(final KontaktlinsenDTO kontaktlinsenDTO,
            final Kontaktlinsen kontaktlinsen) {
        kontaktlinsen.setEinkaufspreis(kontaktlinsenDTO.getEinkaufspreis());
        kontaktlinsen.setVerkaufspreis(kontaktlinsenDTO.getVerkaufspreis());
        final Artikelart art = kontaktlinsenDTO.getArt() == null ? null : artikelartRepository.findById(kontaktlinsenDTO.getArt())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "art not found"));
        kontaktlinsen.setArt(art);
        final Material material = kontaktlinsenDTO.getMaterial() == null ? null : materialRepository.findById(kontaktlinsenDTO.getMaterial())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "material not found"));
        kontaktlinsen.setMaterial(material);
        final Farbe farbe = kontaktlinsenDTO.getFarbe() == null ? null : farbeRepository.findById(kontaktlinsenDTO.getFarbe())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "farbe not found"));
        kontaktlinsen.setFarbe(farbe);
        final Lieferant lieferant = kontaktlinsenDTO.getLieferant() == null ? null : lieferantRepository.findById(kontaktlinsenDTO.getLieferant())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "lieferant not found"));
        kontaktlinsen.setLieferant(lieferant);
        return kontaktlinsen;
    }

}
