package MaiOptiks.service;

import MaiOptiks.domain.*;
import MaiOptiks.model.FassungenDTO;
import MaiOptiks.repos.*;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class FassungenService {

    private final FassungenRepository fassungenRepository;
    private final ArtikelartRepository artikelartRepository;
    private final MaterialRepository materialRepository;
    private final FarbeRepository farbeRepository;
    private final LieferantRepository lieferantRepository;

    public FassungenService(final FassungenRepository fassungenRepository,
            final ArtikelartRepository artikelartRepository,
            final MaterialRepository materialRepository, final FarbeRepository farbeRepository,
            final LieferantRepository lieferantRepository) {
        this.fassungenRepository = fassungenRepository;
        this.artikelartRepository = artikelartRepository;
        this.materialRepository = materialRepository;
        this.farbeRepository = farbeRepository;
        this.lieferantRepository = lieferantRepository;
    }

    public List<FassungenDTO> findAll() {
        return fassungenRepository.findAll(Sort.by("artikelNr"))
                .stream()
                .map(fassungen -> mapToDTO(fassungen, new FassungenDTO()))
                .collect(Collectors.toList());
    }

    public FassungenDTO get(final Integer artikelNr) {
        return fassungenRepository.findById(artikelNr)
                .map(fassungen -> mapToDTO(fassungen, new FassungenDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Integer create(final FassungenDTO fassungenDTO) {
        final Fassungen fassungen = new Fassungen();
        mapToEntity(fassungenDTO, fassungen);
        return fassungenRepository.save(fassungen).getArtikelNr();
    }

    public void update(final Integer artikelNr, final FassungenDTO fassungenDTO) {
        final Fassungen fassungen = fassungenRepository.findById(artikelNr)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(fassungenDTO, fassungen);
        fassungenRepository.save(fassungen);
    }

    public void delete(final Integer artikelNr) {
        fassungenRepository.deleteById(artikelNr);
    }

    private FassungenDTO mapToDTO(final Fassungen fassungen, final FassungenDTO fassungenDTO) {
        fassungenDTO.setArtikelNr(fassungen.getArtikelNr());
        fassungenDTO.setEinkaufspreis(fassungen.getEinkaufspreis());
        fassungenDTO.setVerkaufspreis(fassungen.getVerkaufspreis());
        fassungenDTO.setArt(fassungen.getArt() == null ? null : fassungen.getArt().getArtId());
        fassungenDTO.setMaterial(fassungen.getMaterial() == null ? null : fassungen.getMaterial().getMaterialId());
        fassungenDTO.setFarbe(fassungen.getFarbe() == null ? null : fassungen.getFarbe().getFarbeId());
        fassungenDTO.setLieferant(fassungen.getLieferant() == null ? null : fassungen.getLieferant().getLieferantId());
        return fassungenDTO;
    }

    private Fassungen mapToEntity(final FassungenDTO fassungenDTO, final Fassungen fassungen) {
        fassungen.setEinkaufspreis(fassungenDTO.getEinkaufspreis());
        fassungen.setVerkaufspreis(fassungenDTO.getVerkaufspreis());
        final Artikelart art = fassungenDTO.getArt() == null ? null : artikelartRepository.findById(fassungenDTO.getArt())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "art not found"));
        fassungen.setArt(art);
        final Material material = fassungenDTO.getMaterial() == null ? null : materialRepository.findById(fassungenDTO.getMaterial())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "material not found"));
        fassungen.setMaterial(material);
        final Farbe farbe = fassungenDTO.getFarbe() == null ? null : farbeRepository.findById(fassungenDTO.getFarbe())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "farbe not found"));
        fassungen.setFarbe(farbe);
        final Lieferant lieferant = fassungenDTO.getLieferant() == null ? null : lieferantRepository.findById(fassungenDTO.getLieferant())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "lieferant not found"));
        fassungen.setLieferant(lieferant);
        return fassungen;
    }

}
