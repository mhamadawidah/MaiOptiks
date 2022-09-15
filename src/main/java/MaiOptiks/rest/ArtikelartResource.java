package MaiOptiks.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import MaiOptiks.model.ArtikelartDTO;
import MaiOptiks.service.ArtikelartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(value = "/api/artikelarts", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Artikelart")
public class ArtikelartResource {

    private final ArtikelartService artikelartService;

    public ArtikelartResource(final ArtikelartService artikelartService) {
        this.artikelartService = artikelartService;
    }

    @GetMapping
    public ResponseEntity<List<ArtikelartDTO>> getAllArtikelarts() {
        return ResponseEntity.ok(artikelartService.findAll());
    }

    @GetMapping("/{artId}")
    public ResponseEntity<ArtikelartDTO> getArtikelart(@PathVariable final Integer artId) {
        return ResponseEntity.ok(artikelartService.get(artId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createArtikelart(
            @RequestBody @Valid final ArtikelartDTO artikelartDTO) {
        return new ResponseEntity<>(artikelartService.create(artikelartDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{artId}")
    public ResponseEntity<Void> updateArtikelart(@PathVariable final Integer artId,
            @RequestBody @Valid final ArtikelartDTO artikelartDTO) {
        artikelartService.update(artId, artikelartDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{artId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteArtikelart(@PathVariable final Integer artId) {
        artikelartService.delete(artId);
        return ResponseEntity.noContent().build();
    }

}
