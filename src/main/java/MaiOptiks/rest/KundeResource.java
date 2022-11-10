package MaiOptiks.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import MaiOptiks.model.KundeDTO;
import MaiOptiks.service.KundeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(value = "/api/kundes", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Kunde")
public class KundeResource {

    private final KundeService kundeService;

    public KundeResource(final KundeService kundeService) {
        this.kundeService = kundeService;
    }

    @GetMapping
    public ResponseEntity<List<KundeDTO>> getAllKundes() {
        return ResponseEntity.ok(kundeService.findAll());
    }

    @GetMapping("/{kundenNr}")
    public ResponseEntity<KundeDTO> getKunde(@PathVariable final Integer kundenNr) {
        return ResponseEntity.ok(kundeService.get(kundenNr));
    }

    @GetMapping("/{name}/{vorname}")
    public ResponseEntity<List<KundeDTO>> getKundeList(@PathVariable final String name, @PathVariable final String vorname) {
        return ResponseEntity.ok(kundeService.get(name, vorname));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createKunde(@RequestBody @Valid final KundeDTO kundeDTO) {
        return new ResponseEntity<>(kundeService.create(kundeDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{kundenNr}")
    public ResponseEntity<Void> updateKunde(@PathVariable final Integer kundenNr,
            @RequestBody @Valid final KundeDTO kundeDTO) {
        kundeService.update(kundenNr, kundeDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{kundenNr}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteKunde(@PathVariable final Integer kundenNr) {
        kundeService.delete(kundenNr);
        return ResponseEntity.noContent().build();
    }

}
