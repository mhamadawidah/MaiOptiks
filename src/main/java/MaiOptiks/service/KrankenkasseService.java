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
        return krankenkasseRepository.findAll(Sort.by("krankenkassenID"))
                .stream()
                .map(krankenkasse -> mapToDTO(krankenkasse, new KrankenkasseDTO()))
                .collect(Collectors.toList());
    }

    public KrankenkasseDTO get(final String krankenkassenID) {
        return krankenkasseRepository.findById(krankenkassenID)
                .map(krankenkasse -> mapToDTO(krankenkasse, new KrankenkasseDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public String create(final KrankenkasseDTO krankenkasseDTO) {
        final Krankenkasse krankenkasse = new Krankenkasse();
        mapToEntity(krankenkasseDTO, krankenkasse);
        krankenkasse.setKrankenkassenID(krankenkasseDTO.getKrankenkassenID());
        return krankenkasseRepository.save(krankenkasse).getKrankenkassenID();
    }

    public void update(final String krankenkassenID, final KrankenkasseDTO krankenkasseDTO) {
        final Krankenkasse krankenkasse = krankenkasseRepository.findById(krankenkassenID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(krankenkasseDTO, krankenkasse);
        krankenkasseRepository.save(krankenkasse);
    }

    public void delete(final String krankenkassenID) {
        krankenkasseRepository.deleteById(krankenkassenID);
    }

    private KrankenkasseDTO mapToDTO(final Krankenkasse krankenkasse,
            final KrankenkasseDTO krankenkasseDTO) {
        krankenkasseDTO.setKrankenkassenID(krankenkasse.getKrankenkassenID());
        krankenkasseDTO.setName(krankenkasse.getName());
        krankenkasseDTO.setStarsse(krankenkasse.getStrasse());
        krankenkasseDTO.setTelefonNr(krankenkasse.getTelefonNummer());
        krankenkasseDTO.setEmail(krankenkasse.getEmail());
        krankenkasseDTO.setPlz(krankenkasse.getPlz() == null ? null : krankenkasse.getPlz().getPlz());
        return krankenkasseDTO;
    }

    private Krankenkasse mapToEntity(final KrankenkasseDTO krankenkasseDTO,
            final Krankenkasse krankenkasse) {
        krankenkasse.setName(krankenkasseDTO.getName());
        krankenkasse.setStrasse(krankenkasseDTO.getStarsse());
        krankenkasse.setTelefonNummer(krankenkasseDTO.getTelefonNr());
        krankenkasse.setEmail(krankenkasseDTO.getEmail());
        final Stadt plz = krankenkasseDTO.getPlz() == null ? null : stadtRepository.findById(krankenkasseDTO.getPlz())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "plz not found"));
        krankenkasse.setPlz(plz);
        return krankenkasse;
    }

    public boolean krankenkassenIDExists(final String krankenkassenID) {
        return krankenkasseRepository.existsByKrankenkassenIDIgnoreCase(krankenkassenID);
    }

    @Transactional
    public String getReferencedWarning(final String krankenkassenID) {
        final Krankenkasse krankenkasse = krankenkasseRepository.findById(krankenkassenID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!krankenkasse.getKrankenkassenIDKunde().isEmpty()) {
            return "";
        }
        return null;
    }

}
