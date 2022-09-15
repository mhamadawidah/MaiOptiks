package MaiOptiks.service;

import MaiOptiks.domain.Artikelart;
import MaiOptiks.model.ArtikelartDTO;
import MaiOptiks.repos.ArtikelartRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ArtikelartService {

    private final ArtikelartRepository artikelartRepository;

    public ArtikelartService(final ArtikelartRepository artikelartRepository) {
        this.artikelartRepository = artikelartRepository;
    }

    public List<ArtikelartDTO> findAll() {
        return artikelartRepository.findAll(Sort.by("artId"))
                .stream()
                .map(artikelart -> mapToDTO(artikelart, new ArtikelartDTO()))
                .collect(Collectors.toList());
    }

    public ArtikelartDTO get(final Integer artId) {
        return artikelartRepository.findById(artId)
                .map(artikelart -> mapToDTO(artikelart, new ArtikelartDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Integer create(final ArtikelartDTO artikelartDTO) {
        final Artikelart artikelart = new Artikelart();
        mapToEntity(artikelartDTO, artikelart);
        return artikelartRepository.save(artikelart).getArtId();
    }

    public void update(final Integer artId, final ArtikelartDTO artikelartDTO) {
        final Artikelart artikelart = artikelartRepository.findById(artId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(artikelartDTO, artikelart);
        artikelartRepository.save(artikelart);
    }

    public void delete(final Integer artId) {
        artikelartRepository.deleteById(artId);
    }

    private ArtikelartDTO mapToDTO(final Artikelart artikelart, final ArtikelartDTO artikelartDTO) {
        artikelartDTO.setArtId(artikelart.getArtId());
        artikelartDTO.setBezeichnung(artikelart.getBezeichnung());
        return artikelartDTO;
    }

    private Artikelart mapToEntity(final ArtikelartDTO artikelartDTO, final Artikelart artikelart) {
        artikelart.setBezeichnung(artikelartDTO.getBezeichnung());
        return artikelart;
    }

    @Transactional
    public String getReferencedWarning(final Integer artId) {
        final Artikelart artikelart = artikelartRepository.findById(artId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!artikelart.getArtGlaesers().isEmpty()) {
            return "";
        } else if (!artikelart.getArtFassungens().isEmpty()) {
            return "";
        } else if (!artikelart.getArtKontaktlinsens().isEmpty()) {
            return "";
        }
        return null;
    }

}
