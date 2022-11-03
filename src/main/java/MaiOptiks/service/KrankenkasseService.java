package MaiOptiks.service;

import MaiOptiks.domain.Krankenkasse;
import MaiOptiks.domain.Stadt;
import MaiOptiks.model.KrankenkasseDTO;
import MaiOptiks.repos.KrankenkasseRepository;
import MaiOptiks.repos.StadtRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class KrankenkasseService {

    private final KrankenkasseRepository krankenkasseRepository;
    private final StadtRepository stadtRepository;

    public KrankenkasseService(final KrankenkasseRepository krankenkasseRepository,
            final StadtRepository stadtRepository) {
        this.krankenkasseRepository = krankenkasseRepository;
        this.stadtRepository = stadtRepository;
    }

    public List<KrankenkasseDTO> findAll() {
        return krankenkasseRepository.findAll(Sort.by("krankenkassennr"))
                .stream()
                .map(krankenkasse -> mapToDTO(krankenkasse, new KrankenkasseDTO()))
                .collect(Collectors.toList());
    }

    public KrankenkasseDTO get(final String krankenkassenNr) {
        return krankenkasseRepository.findById(krankenkassenNr)
                .map(krankenkasse -> mapToDTO(krankenkasse, new KrankenkasseDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public String create(final KrankenkasseDTO krankenkasseDTO) {
        final Krankenkasse krankenkasse = new Krankenkasse();
        mapToEntity(krankenkasseDTO, krankenkasse);
        krankenkasse.setKrankenkassennr(krankenkasseDTO.getKrankenkassennr());
        return krankenkasseRepository.save(krankenkasse).getKrankenkassennr();
    }

    public void update(final String krankenkassenNr, final KrankenkasseDTO krankenkasseDTO) {
        final Krankenkasse krankenkasse = krankenkasseRepository.findById(krankenkassenNr)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(krankenkasseDTO, krankenkasse);
        krankenkasseRepository.save(krankenkasse);
    }

    public void delete(final String krankenkassenNr) {
        krankenkasseRepository.deleteById(krankenkassenNr);
    }

    private KrankenkasseDTO mapToDTO(final Krankenkasse krankenkasse,
            final KrankenkasseDTO krankenkasseDTO) {
        krankenkasseDTO.setKrankenkassennr(krankenkasse.getKrankenkassennr());
        krankenkasseDTO.setName(krankenkasse.getName());
        krankenkasseDTO.setStrasse(krankenkasse.getStrasse());
        krankenkasseDTO.setTelefonnr(krankenkasse.getTelefonnr());
        krankenkasseDTO.setEmail(krankenkasse.getEmail());
        krankenkasseDTO.setPlz(krankenkasse.getPlz() == null ? null : krankenkasse.getPlz().getPlz());
        return krankenkasseDTO;
    }

    private Krankenkasse mapToEntity(final KrankenkasseDTO krankenkasseDTO,
            final Krankenkasse krankenkasse) {
        krankenkasse.setName(krankenkasseDTO.getName());
        krankenkasse.setStrasse(krankenkasseDTO.getStrasse());
        krankenkasse.setTelefonnr(krankenkasseDTO.getTelefonnr());
        krankenkasse.setEmail(krankenkasseDTO.getEmail());
        final Stadt plz = krankenkasseDTO.getPlz() == null ? null : stadtRepository.findById(krankenkasseDTO.getPlz())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "plz not found"));
        krankenkasse.setPlz(plz);
        return krankenkasse;
    }

    public boolean krankenkassenNrExists(final String krankenkassenNr) {
        return krankenkasseRepository.existsByKrankenkassennrIgnoreCase(krankenkassenNr);
    }

    @Transactional
    public String getReferencedWarning(final String krankenkassenNr) {
        final Krankenkasse krankenkasse = krankenkasseRepository.findById(krankenkassenNr)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!krankenkasse.getKrankenkassenNrKundes().isEmpty()) {
            return "";
        }
        return null;
    }

}
