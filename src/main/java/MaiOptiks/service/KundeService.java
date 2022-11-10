package MaiOptiks.service;

import MaiOptiks.domain.Krankenkasse;
import MaiOptiks.domain.Kunde;
import MaiOptiks.domain.Stadt;
import MaiOptiks.model.KundeDTO;
import MaiOptiks.repos.KrankenkasseRepository;
import MaiOptiks.repos.KundeRepository;
import MaiOptiks.repos.StadtRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public class KundeService {

    private final KundeRepository kundeRepository;
    private final StadtRepository stadtRepository;
    private final KrankenkasseRepository krankenkasseRepository;

    public KundeService(final KundeRepository kundeRepository,
                        final StadtRepository stadtRepository,
                        final KrankenkasseRepository krankenkasseRepository) {
        this.kundeRepository = kundeRepository;
        this.stadtRepository = stadtRepository;
        this.krankenkasseRepository = krankenkasseRepository;
    }

    public List<KundeDTO> findAll() {
        return kundeRepository.findAll(Sort.by("kundennr"))
                .stream()
                .map(new Function<Kunde, KundeDTO>() {
                    @Override
                    public KundeDTO apply(Kunde kunde) {
                        return KundeService.this.mapToDTO(kunde, new KundeDTO());
                    }
                })
                .collect(Collectors.toList());
    }

    public KundeDTO getById(final Integer kundenNr) {
        return kundeRepository.findById(kundenNr)
                .map(kunde -> mapToDTO(kunde, new KundeDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public List<KundeDTO> getByNameAndVorname(final String name, final String vorname) {
        return kundeRepository.findAllByNameAndVorname(name, vorname)
                .stream()
                .map(kunde -> KundeService.this.mapToDTO(kunde, new KundeDTO()))
                .collect(Collectors.toList());
    }

    public List<KundeDTO> getByName(final String name) {
        return kundeRepository.findAllByName(name)
                .stream()
                .map(kunde -> KundeService.this.mapToDTO(kunde, new KundeDTO()))
                .collect(Collectors.toList());
    }

    public List<KundeDTO> getByVorname(final String vorname) {
        return kundeRepository.findAllByVorname(vorname)
                .stream()
                .map(kunde -> KundeService.this.mapToDTO(kunde, new KundeDTO()))
                .collect(Collectors.toList());
    }

    public Integer create(final KundeDTO kundeDTO) {
        final Kunde kunde = new Kunde();
        mapToEntity(kundeDTO, kunde);
        return kundeRepository.save(kunde).getKundennr();
    }

    public void update(final Integer kundenNr, final KundeDTO kundeDTO) {
        final Kunde kunde = kundeRepository.findById(kundenNr)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(kundeDTO, kunde);
        kundeRepository.save(kunde);
    }

    public void delete(final Integer kundenNr) {
        kundeRepository.deleteById(kundenNr);
    }

    private KundeDTO mapToDTO(final Kunde kunde, final KundeDTO kundeDTO) {
        kundeDTO.setKundenNr(kunde.getKundennr());
        kundeDTO.setAnrede(kunde.getAnrede());
        kundeDTO.setName(kunde.getName());
        kundeDTO.setVorname(kunde.getVorname());
        kundeDTO.setStrasse(kunde.getStrasse());
        kundeDTO.setHausNr(kunde.getHausnr());
        kundeDTO.setGeburtsdatum(kunde.getGeburtsdatum());
        kundeDTO.setTelefonNr(kunde.getTelefonnr());
        kundeDTO.setHandy(kunde.getHandy());
        kundeDTO.setEmail(kunde.getEmail());
        kundeDTO.setVersicherungsNr(kunde.getVersicherungsnr());
        kundeDTO.setGueltigkeit(kunde.getGueltigkeit());
        kundeDTO.setBemerkung(kunde.getBemerkung());
        kundeDTO.setPlz(kunde.getPlz() == null ? null : kunde.getPlz().getPlz());
        kundeDTO.setKrankenkassenNr(kunde.getKrankenkassenNr() == null ? null : kunde.getKrankenkassenNr().getKrankenkassennr());
        return kundeDTO;
    }

    private Kunde mapToEntity(final KundeDTO kundeDTO, final Kunde kunde) {
        kunde.setAnrede(kundeDTO.getAnrede());
        kunde.setName(kundeDTO.getName());
        kunde.setVorname(kundeDTO.getVorname());
        kunde.setStrasse(kundeDTO.getStrasse());
        kunde.setHausnr(kundeDTO.getHausNr());
        kunde.setGeburtsdatum(kundeDTO.getGeburtsdatum());
        kunde.setTelefonnr(kundeDTO.getTelefonNr());
        kunde.setHandy(kundeDTO.getHandy());
        kunde.setEmail(kundeDTO.getEmail());
        kunde.setVersicherungsnr(kundeDTO.getVersicherungsNr());
        kunde.setGueltigkeit(kundeDTO.getGueltigkeit());
        kunde.setBemerkung(kundeDTO.getBemerkung());
        final Stadt plz = kundeDTO.getPlz() == null ? null : stadtRepository.findById(kundeDTO.getPlz())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "plz not found"));
        kunde.setPlz(plz);
        final Krankenkasse krankenkassenNr = kundeDTO.getKrankenkassenNr() == null ? null : krankenkasseRepository.findById(kundeDTO.getKrankenkassenNr())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "krankenkassenNr not found"));
        kunde.setKrankenkassenNr(krankenkassenNr);
        return kunde;
    }

    @Transactional
    public String getReferencedWarning(final Integer kundenNr) {
        final Kunde kunde = kundeRepository.findById(kundenNr)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!kunde.getKundenNrAuftrags().isEmpty()) {
            return "";
        }
        return null;
    }

}
