package MaiOptiks.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import MaiOptiks.model.FassungenDTO;
import MaiOptiks.service.FassungenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(value = "/api/fassungens", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Fassungen")
public class FassungenResource {

    private final FassungenService fassungenService;

    public FassungenResource(final FassungenService fassungenService) {
        this.fassungenService = fassungenService;
    }

    @GetMapping
    public ResponseEntity<List<FassungenDTO>> getAllFassungens() {
        return ResponseEntity.ok(fassungenService.findAll());
    }

    @GetMapping("/{artikelNr}")
    public ResponseEntity<FassungenDTO> getFassungen(@PathVariable final Integer artikelNr) {
        return ResponseEntity.ok(fassungenService.get(artikelNr));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createFassungen(
            @RequestBody @Valid final FassungenDTO fassungenDTO) {
        return new ResponseEntity<>(fassungenService.create(fassungenDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{artikelNr}")
    public ResponseEntity<Void> updateFassungen(@PathVariable final Integer artikelNr,
            @RequestBody @Valid final FassungenDTO fassungenDTO) {
        fassungenService.update(artikelNr, fassungenDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{artikelNr}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteFassungen(@PathVariable final Integer artikelNr) {
        fassungenService.delete(artikelNr);
        return ResponseEntity.noContent().build();
    }

}
