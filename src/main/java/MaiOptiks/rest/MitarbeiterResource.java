package MaiOptiks.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import MaiOptiks.model.MitarbeiterDTO;
import MaiOptiks.service.MitarbeiterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(value = "/api/mitarbeiters", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Mitarbeiter")
public class MitarbeiterResource {

    private final MitarbeiterService mitarbeiterService;

    public MitarbeiterResource(final MitarbeiterService mitarbeiterService) {
        this.mitarbeiterService = mitarbeiterService;
    }

    /**
     * Deprecated. Use {@link MitarbeiterResource#getAll(String, String)} instead.
     */
    @Deprecated
    @GetMapping
    public ResponseEntity<List<MitarbeiterDTO>> getAllMitarbeiters() {
        return ResponseEntity.ok(mitarbeiterService.findAll());
    }

    @GetMapping("/{mitarbeiterNr}")
    public ResponseEntity<MitarbeiterDTO> getMitarbeiter(
            @PathVariable final Integer mitarbeiterNr) {
        return ResponseEntity.ok(mitarbeiterService.get(mitarbeiterNr));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<MitarbeiterDTO>> getAll(@RequestParam(value="name") String name, @RequestParam(value="vorname") String vorname) {
        if (name.trim().equalsIgnoreCase("*") && vorname.trim().equalsIgnoreCase("*"))
        {
            return ResponseEntity.ok(mitarbeiterService.findAll());
        }
        if (vorname.trim().equalsIgnoreCase("*"))
        {
            return ResponseEntity.ok(mitarbeiterService.getByName(name));
        }
        if (name.trim().equalsIgnoreCase("*"))
        {
            return ResponseEntity.ok(mitarbeiterService.getByVorname(vorname));
        }
        return ResponseEntity.ok(mitarbeiterService.getByNameAndVorname(name, vorname));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createMitarbeiter(
            @RequestBody @Valid final MitarbeiterDTO mitarbeiterDTO) {
        return new ResponseEntity<>(mitarbeiterService.create(mitarbeiterDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{mitarbeiterNr}")
    public ResponseEntity<Void> updateMitarbeiter(@PathVariable final Integer mitarbeiterNr,
            @RequestBody @Valid final MitarbeiterDTO mitarbeiterDTO) {
        mitarbeiterService.update(mitarbeiterNr, mitarbeiterDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{mitarbeiterNr}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteMitarbeiter(@PathVariable final Integer mitarbeiterNr) {
        mitarbeiterService.delete(mitarbeiterNr);
        return ResponseEntity.noContent().build();
    }

}
