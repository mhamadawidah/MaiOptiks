package MaiOptiks.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import MaiOptiks.model.AuftragsartikelDTO;
import MaiOptiks.service.AuftragsartikelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(value = "/api/auftragsartikels", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "AuftragsArtikel")
public class AuftragsartikelResource {

    private final AuftragsartikelService auftragsartikelService;

    public AuftragsartikelResource(final AuftragsartikelService auftragsartikelService) {
        this.auftragsartikelService = auftragsartikelService;
    }

    @GetMapping
    public ResponseEntity<List<AuftragsartikelDTO>> getAllAuftragsartikels() {
        return ResponseEntity.ok(auftragsartikelService.findAll());
    }

    @GetMapping("/{auftragsArtikelId}")
    public ResponseEntity<AuftragsartikelDTO> getAuftragsartikel(
            @PathVariable final Integer auftragsArtikelId) {
        return ResponseEntity.ok(auftragsartikelService.get(auftragsArtikelId));
    }

    @GetMapping("/get/{auftragsNr}")
    public ResponseEntity<List<AuftragsartikelDTO>> getByAuftragNr(@PathVariable Integer auftragsNr) {
        return ResponseEntity.ok(auftragsartikelService.findAllByAuftragsNr(auftragsNr));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createAuftragsartikel(
            @RequestBody @Valid final AuftragsartikelDTO auftragsartikelDTO) {
        return new ResponseEntity<>(auftragsartikelService.create(auftragsartikelDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{auftragsArtikelId}")
    public ResponseEntity<Void> updateAuftragsartikel(@PathVariable final Integer auftragsArtikelId,
            @RequestBody @Valid final AuftragsartikelDTO auftragsartikelDTO) {
        auftragsartikelService.update(auftragsArtikelId, auftragsartikelDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{auftragsArtikelId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteAuftragsartikel(
            @PathVariable final Integer auftragsArtikelId) {
        auftragsartikelService.delete(auftragsArtikelId);
        return ResponseEntity.noContent().build();
    }

}
