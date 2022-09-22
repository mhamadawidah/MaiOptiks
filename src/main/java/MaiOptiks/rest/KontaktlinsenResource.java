package MaiOptiks.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import MaiOptiks.model.KontaktlinsenDTO;
import MaiOptiks.service.KontaktlinsenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(value = "/api/kontaktlinsens", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Kontaktlinsen")
public class KontaktlinsenResource {

    private final KontaktlinsenService kontaktlinsenService;

    public KontaktlinsenResource(final KontaktlinsenService kontaktlinsenService) {
        this.kontaktlinsenService = kontaktlinsenService;
    }

    @GetMapping
    public ResponseEntity<List<KontaktlinsenDTO>> getAllKontaktlinsens() {
        return ResponseEntity.ok(kontaktlinsenService.findAll());
    }

    @GetMapping("/{artikelNr}")
    public ResponseEntity<KontaktlinsenDTO> getKontaktlinsen(
            @PathVariable final Integer artikelNr) {
        return ResponseEntity.ok(kontaktlinsenService.get(artikelNr));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createKontaktlinsen(
            @RequestBody @Valid final KontaktlinsenDTO kontaktlinsenDTO) {
        return new ResponseEntity<>(kontaktlinsenService.create(kontaktlinsenDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{artikelNr}")
    public ResponseEntity<Void> updateKontaktlinsen(@PathVariable final Integer artikelNr,
            @RequestBody @Valid final KontaktlinsenDTO kontaktlinsenDTO) {
        kontaktlinsenService.update(artikelNr, kontaktlinsenDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{artikelNr}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteKontaktlinsen(@PathVariable final Integer artikelNr) {
        kontaktlinsenService.delete(artikelNr);
        return ResponseEntity.noContent().build();
    }

}
