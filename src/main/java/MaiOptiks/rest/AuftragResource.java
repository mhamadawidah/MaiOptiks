package MaiOptiks.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import MaiOptiks.model.AuftragDTO;
import MaiOptiks.service.AuftragService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;


@RestController
@RequestMapping(value = "/api/auftrags", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Auftrag")
public class AuftragResource {

    private final AuftragService auftragService;

    public AuftragResource(final AuftragService auftragService) {
        this.auftragService = auftragService;
    }

    @GetMapping
    public ResponseEntity<List<AuftragDTO>> getAllAuftrags() {
        return ResponseEntity.ok(auftragService.findAll());
    }

    @GetMapping("/{auftragsnummer}")
    public ResponseEntity<AuftragDTO> getAuftrag(@PathVariable final Integer auftragsnummer) {
        return ResponseEntity.ok(auftragService.get(auftragsnummer));
    }

    @GetMapping("/countAll")
    public ResponseEntity<Integer> countAll() {
        return ResponseEntity.ok(auftragService.findAll().size());
    }

    @GetMapping("/getNextId")
    public ResponseEntity<BigDecimal> getNextId() {
        return ResponseEntity.ok(auftragService.getNextId());
    }

    @GetMapping("/getByKundenNr/{kundenNr}")
    public ResponseEntity<List<AuftragDTO>> getByKundenNr(@PathVariable final Integer kundenNr) {

        return ResponseEntity.ok(auftragService.getAuftragByKundenNr(kundenNr));
    }

    @GetMapping("/getByX")
    public ResponseEntity<List<AuftragDTO>> getByKundenX(
            @RequestParam(value = "kundenNr") Integer kundenNr,
            @RequestParam(value = "beraterId") Integer beraterId,
            @RequestParam(value = "werkstadtId") Integer werkstadtId,
            @RequestParam(value = "abrechnungsart") Integer abrechnungsart
    ) {

        return ResponseEntity.ok(auftragService.getAuftragByX(kundenNr, beraterId, werkstadtId, abrechnungsart));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createAuftrag(@RequestBody @Valid final AuftragDTO auftragDTO) {
        return new ResponseEntity<>(auftragService.create(auftragDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{auftragsnummer}")
    public ResponseEntity<Void> updateAuftrag(@PathVariable final Integer auftragsnummer,
            @RequestBody @Valid final AuftragDTO auftragDTO) {
        auftragService.update(auftragsnummer, auftragDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{auftragsnummer}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteAuftrag(@PathVariable final Integer auftragsnummer) {
        auftragService.delete(auftragsnummer);
        return ResponseEntity.noContent().build();
    }

}
