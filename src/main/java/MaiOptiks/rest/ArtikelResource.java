package MaiOptiks.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import MaiOptiks.model.ArtikelDTO;
import MaiOptiks.service.ArtikelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(value = "/api/artikels", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Artikel")
public class ArtikelResource {

    private final ArtikelService artikelService;

    public ArtikelResource(final ArtikelService artikelService) {
        this.artikelService = artikelService;
    }

    @GetMapping
    public ResponseEntity<List<ArtikelDTO>> getAllArtikels() {
        return ResponseEntity.ok(artikelService.findAll());
    }

    @GetMapping("/{artikelNr}")
    public ResponseEntity<ArtikelDTO> getArtikel(@PathVariable final Integer artikelNr) {
        return ResponseEntity.ok(artikelService.get(artikelNr));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createArtikel(@RequestBody @Valid final ArtikelDTO artikelDTO) {
        return new ResponseEntity<>(artikelService.create(artikelDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{artikelNr}")
    public ResponseEntity<Void> updateArtikel(@PathVariable final Integer artikelNr,
            @RequestBody @Valid final ArtikelDTO artikelDTO) {
        artikelService.update(artikelNr, artikelDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{artikelNr}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteArtikel(@PathVariable final Integer artikelNr) {
        artikelService.delete(artikelNr);
        return ResponseEntity.noContent().build();
    }

}
