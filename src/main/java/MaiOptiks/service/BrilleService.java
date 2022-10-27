package MaiOptiks.service;

import MaiOptiks.domain.Artikel;
import MaiOptiks.domain.Brille;
import MaiOptiks.model.BrilleDTO;
import MaiOptiks.repos.ArtikelRepository;
import MaiOptiks.repos.BrilleRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class BrilleService {

    private final BrilleRepository brilleRepository;
    private final ArtikelRepository artikelRepository;

    public BrilleService(final BrilleRepository brilleRepository,
            final ArtikelRepository artikelRepository) {
        this.brilleRepository = brilleRepository;
        this.artikelRepository = artikelRepository;
    }

    public List<BrilleDTO> findAll() {
        return brilleRepository.findAll(Sort.by("brillenid"))
                .stream()
                .map(brille -> mapToDTO(brille, new BrilleDTO()))
                .collect(Collectors.toList());
    }

    public BrilleDTO get(final Integer brillenId) {
        return brilleRepository.findById(brillenId)
                .map(brille -> mapToDTO(brille, new BrilleDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Integer create(final BrilleDTO brilleDTO) {
        final Brille brille = new Brille();
        mapToEntity(brilleDTO, brille);
        return brilleRepository.save(brille).getBrillenid();
    }

    public void update(final Integer brillenId, final BrilleDTO brilleDTO) {
        final Brille brille = brilleRepository.findById(brillenId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(brilleDTO, brille);
        brilleRepository.save(brille);
    }

    public void delete(final Integer brillenId) {
        brilleRepository.deleteById(brillenId);
    }

    private BrilleDTO mapToDTO(final Brille brille, final BrilleDTO brilleDTO) {
        brilleDTO.setBrillenId(brille.getBrillenid());
        brilleDTO.setGlasArtikelIdlinks(brille.getGlasArtikelIdlinks() == null ? null : brille.getGlasArtikelIdlinks().getArtikelnr());
        brilleDTO.setGlasArtikelIdrechts(brille.getGlasArtikelIdrechts() == null ? null : brille.getGlasArtikelIdrechts().getArtikelnr());
        brilleDTO.setFassungsArtikel(brille.getFassungsArtikel() == null ? null : brille.getFassungsArtikel().getArtikelnr());
        return brilleDTO;
    }

    private Brille mapToEntity(final BrilleDTO brilleDTO, final Brille brille) {
        final Artikel glasArtikelIdlinks = brilleDTO.getGlasArtikelIdlinks() == null ? null : artikelRepository.findById(brilleDTO.getGlasArtikelIdlinks())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "glasArtikelIdlinks not found"));
        brille.setGlasArtikelIdlinks(glasArtikelIdlinks);
        final Artikel glasArtikelIdrechts = brilleDTO.getGlasArtikelIdrechts() == null ? null : artikelRepository.findById(brilleDTO.getGlasArtikelIdrechts())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "glasArtikelIdrechts not found"));
        brille.setGlasArtikelIdrechts(glasArtikelIdrechts);
        final Artikel fassungsArtikel = brilleDTO.getFassungsArtikel() == null ? null : artikelRepository.findById(brilleDTO.getFassungsArtikel())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "fassungsArtikel not found"));
        brille.setFassungsArtikel(fassungsArtikel);
        return brille;
    }

    @Transactional
    public String getReferencedWarning(final Integer brillenId) {
        final Brille brille = brilleRepository.findById(brillenId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!brille.getSehhilfeAuftragsartikels().isEmpty()) {
            return "";
        }
        return null;
    }

}
