package MaiOptiks.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import MaiOptiks.model.LieferantDTO;
import MaiOptiks.service.LieferantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(value = "/api/lieferants", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Lieferant")
public class LieferantResource {

    private final LieferantService lieferantService;

    public LieferantResource(final LieferantService lieferantService) {
        this.lieferantService = lieferantService;
    }

    @GetMapping
    public ResponseEntity<List<LieferantDTO>> getAllLieferants() {
        return ResponseEntity.ok(lieferantService.findAll());
    }

    @GetMapping("/{lieferantId}")
    public ResponseEntity<LieferantDTO> getLieferant(@PathVariable final Integer lieferantId) {
        return ResponseEntity.ok(lieferantService.get(lieferantId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createLieferant(
            @RequestBody @Valid final LieferantDTO lieferantDTO) {
        return new ResponseEntity<>(lieferantService.create(lieferantDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{lieferantId}")
    public ResponseEntity<Void> updateLieferant(@PathVariable final Integer lieferantId,
            @RequestBody @Valid final LieferantDTO lieferantDTO) {
        lieferantService.update(lieferantId, lieferantDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{lieferantId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteLieferant(@PathVariable final Integer lieferantId) {
        lieferantService.delete(lieferantId);
        return ResponseEntity.noContent().build();
    }

}
