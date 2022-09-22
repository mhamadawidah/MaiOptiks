package MaiOptiks.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import MaiOptiks.model.GlaeserDTO;
import MaiOptiks.service.GlaeserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(value = "/api/glaesers", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Glaeser")
public class GlaeserResource {

    private final GlaeserService glaeserService;

    public GlaeserResource(final GlaeserService glaeserService) {
        this.glaeserService = glaeserService;
    }

    @GetMapping
    public ResponseEntity<List<GlaeserDTO>> getAllGlaesers() {
        return ResponseEntity.ok(glaeserService.findAll());
    }

    @GetMapping("/{artikelNr}")
    public ResponseEntity<GlaeserDTO> getGlaeser(@PathVariable final Integer artikelNr) {
        return ResponseEntity.ok(glaeserService.get(artikelNr));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createGlaeser(@RequestBody @Valid final GlaeserDTO glaeserDTO) {
        return new ResponseEntity<>(glaeserService.create(glaeserDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{artikelNr}")
    public ResponseEntity<Void> updateGlaeser(@PathVariable final Integer artikelNr,
            @RequestBody @Valid final GlaeserDTO glaeserDTO) {
        glaeserService.update(artikelNr, glaeserDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{artikelNr}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteGlaeser(@PathVariable final Integer artikelNr) {
        glaeserService.delete(artikelNr);
        return ResponseEntity.noContent().build();
    }

}
