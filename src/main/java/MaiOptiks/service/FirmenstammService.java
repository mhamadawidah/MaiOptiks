package MaiOptiks.service;

import MaiOptiks.domain.Firmenstamm;
import MaiOptiks.domain.Stadt;
import MaiOptiks.model.FirmenstammDTO;
import MaiOptiks.repos.FirmenstammRepository;
import MaiOptiks.repos.StadtRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class FirmenstammService {

    private final FirmenstammRepository firmenstammRepository;
    private final StadtRepository stadtRepository;

    public FirmenstammService(final FirmenstammRepository firmenstammRepository,
            final StadtRepository stadtRepository) {
        this.firmenstammRepository = firmenstammRepository;
        this.stadtRepository = stadtRepository;
    }

    public List<FirmenstammDTO> findAll() {
        return firmenstammRepository.findAll(Sort.by("augenoptikerIknr"))
                .stream()
                .map(firmenstamm -> mapToDTO(firmenstamm, new FirmenstammDTO()))
                .collect(Collectors.toList());
    }

    public FirmenstammDTO get(final String augenoptikerIknr) {
        return firmenstammRepository.findById(augenoptikerIknr)
                .map(firmenstamm -> mapToDTO(firmenstamm, new FirmenstammDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public String create(final FirmenstammDTO firmenstammDTO) {
        final Firmenstamm firmenstamm = new Firmenstamm();
        mapToEntity(firmenstammDTO, firmenstamm);
        firmenstamm.setAugenoptikerIknr(firmenstammDTO.getAugenoptikerIknr());
        return firmenstammRepository.save(firmenstamm).getAugenoptikerIknr();
    }

    public void update(final String augenoptikerIknr, final FirmenstammDTO firmenstammDTO) {
        final Firmenstamm firmenstamm = firmenstammRepository.findById(augenoptikerIknr)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(firmenstammDTO, firmenstamm);
        firmenstammRepository.save(firmenstamm);
    }

    public void delete(final String augenoptikerIknr) {
        firmenstammRepository.deleteById(augenoptikerIknr);
    }

    private FirmenstammDTO mapToDTO(final Firmenstamm firmenstamm,
            final FirmenstammDTO firmenstammDTO) {
        firmenstammDTO.setAugenoptikerIknr(firmenstamm.getAugenoptikerIknr());
        firmenstammDTO.setSteuernummer(firmenstamm.getSteuernummer());
        firmenstammDTO.setGeschaeftsname(firmenstamm.getGeschaeftsname());
        firmenstammDTO.setBankverbindung(firmenstamm.getBankverbindung());
        firmenstammDTO.setStrasse(firmenstamm.getStrasse());
        firmenstammDTO.setHausNr(firmenstamm.getHausnr());
        firmenstammDTO.setTelefonNr(firmenstamm.getTelefonnr());
        firmenstammDTO.setInhabername(firmenstamm.getInhabername());
        firmenstammDTO.setInhabervorname(firmenstamm.getInhabervorname());
        firmenstammDTO.setPlz(firmenstamm.getPlz() == null ? null : firmenstamm.getPlz().getPlz());
        return firmenstammDTO;
    }

    private Firmenstamm mapToEntity(final FirmenstammDTO firmenstammDTO,
            final Firmenstamm firmenstamm) {
        firmenstamm.setSteuernummer(firmenstammDTO.getSteuernummer());
        firmenstamm.setGeschaeftsname(firmenstammDTO.getGeschaeftsname());
        firmenstamm.setBankverbindung(firmenstammDTO.getBankverbindung());
        firmenstamm.setStrasse(firmenstammDTO.getStrasse());
        firmenstamm.setHausnr(firmenstammDTO.getHausNr());
        firmenstamm.setTelefonnr(firmenstammDTO.getTelefonNr());
        firmenstamm.setInhabername(firmenstammDTO.getInhabername());
        firmenstamm.setInhabervorname(firmenstammDTO.getInhabervorname());
        final Stadt plz = firmenstammDTO.getPlz() == null ? null : stadtRepository.findById(firmenstammDTO.getPlz())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "plz not found"));
        firmenstamm.setPlz(plz);
        return firmenstamm;
    }

    public boolean augenoptikerIknrExists(final String augenoptikerIknr) {
        return firmenstammRepository.existsByAugenoptikerIknrIgnoreCase(augenoptikerIknr);
    }

}
