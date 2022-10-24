package MaiOptiks.service;

import MaiOptiks.domain.Hornhaut;
import MaiOptiks.domain.Refraktion;
import MaiOptiks.model.HornhautDTO;
import MaiOptiks.repos.HornhautRepository;
import MaiOptiks.repos.RefraktionRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class HornhautService {

    private final HornhautRepository hornhautRepository;
    private final RefraktionRepository refraktionRepository;

    public HornhautService(final HornhautRepository hornhautRepository,
            final RefraktionRepository refraktionRepository) {
        this.hornhautRepository = hornhautRepository;
        this.refraktionRepository = refraktionRepository;
    }

    public List<HornhautDTO> findAll() {
        return hornhautRepository.findAll(Sort.by("hornhautId"))
                .stream()
                .map(hornhaut -> mapToDTO(hornhaut, new HornhautDTO()))
                .collect(Collectors.toList());
    }

    public HornhautDTO get(final Integer hornhautId) {
        return hornhautRepository.findById(hornhautId)
                .map(hornhaut -> mapToDTO(hornhaut, new HornhautDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Integer create(final HornhautDTO hornhautDTO) {
        final Hornhaut hornhaut = new Hornhaut();
        mapToEntity(hornhautDTO, hornhaut);
        return hornhautRepository.save(hornhaut).getHornhautId();
    }

    public void update(final Integer hornhautId, final HornhautDTO hornhautDTO) {
        final Hornhaut hornhaut = hornhautRepository.findById(hornhautId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(hornhautDTO, hornhaut);
        hornhautRepository.save(hornhaut);
    }

    public void delete(final Integer hornhautId) {
        hornhautRepository.deleteById(hornhautId);
    }

    private HornhautDTO mapToDTO(final Hornhaut hornhaut, final HornhautDTO hornhautDTO) {
        hornhautDTO.setHornhautId(hornhaut.getHornhautId());
        hornhautDTO.setHsir(hornhaut.getHsir());
        hornhautDTO.setA0R(hornhaut.getA0R());
        hornhautDTO.setH2Shr(hornhaut.getH2Shr());
        hornhautDTO.setA90R(hornhaut.getA90R());
        hornhautDTO.setMassalR(hornhaut.getMassalR());
        hornhautDTO.setTempR(hornhaut.getTempR());
        hornhautDTO.setSupR(hornhaut.getSupR());
        hornhautDTO.setIntR(hornhaut.getIntR());
        hornhautDTO.setHsil(hornhaut.getHsil());
        hornhautDTO.setA0L(hornhaut.getA0L());
        hornhautDTO.setHshl(hornhaut.getHshl());
        hornhautDTO.setA90L(hornhaut.getA90L());
        hornhautDTO.setMassalL(hornhaut.getMassalL());
        hornhautDTO.setTempL(hornhaut.getTempL());
        hornhautDTO.setSupL(hornhaut.getSupL());
        hornhautDTO.setIntL(hornhaut.getIntL());
        hornhautDTO.setRefraktion(hornhaut.getRefraktion() == null ? null : hornhaut.getRefraktion().getRefraktionid());
        return hornhautDTO;
    }

    private Hornhaut mapToEntity(final HornhautDTO hornhautDTO, final Hornhaut hornhaut) {
        hornhaut.setHsir(hornhautDTO.getHsir());
        hornhaut.setA0R(hornhautDTO.getA0R());
        hornhaut.setH2Shr(hornhautDTO.getH2Shr());
        hornhaut.setA90R(hornhautDTO.getA90R());
        hornhaut.setMassalR(hornhautDTO.getMassalR());
        hornhaut.setTempR(hornhautDTO.getTempR());
        hornhaut.setSupR(hornhautDTO.getSupR());
        hornhaut.setIntR(hornhautDTO.getIntR());
        hornhaut.setHsil(hornhautDTO.getHsil());
        hornhaut.setA0L(hornhautDTO.getA0L());
        hornhaut.setHshl(hornhautDTO.getHshl());
        hornhaut.setA90L(hornhautDTO.getA90L());
        hornhaut.setMassalL(hornhautDTO.getMassalL());
        hornhaut.setTempL(hornhautDTO.getTempL());
        hornhaut.setSupL(hornhautDTO.getSupL());
        hornhaut.setIntL(hornhautDTO.getIntL());
        final Refraktion refraktion = hornhautDTO.getRefraktion() == null ? null : refraktionRepository.findById(hornhautDTO.getRefraktion())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "refraktion not found"));
        hornhaut.setRefraktion(refraktion);
        return hornhaut;
    }

}
