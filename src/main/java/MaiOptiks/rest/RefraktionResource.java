package MaiOptiks.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import MaiOptiks.model.RefraktionDTO;
import MaiOptiks.service.RefraktionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(value = "/api/refraktions", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Refraktion")
public class RefraktionResource {

    private final RefraktionService refraktionService;

    public RefraktionResource(final RefraktionService refraktionService) {
        this.refraktionService = refraktionService;
    }

    @GetMapping
    public ResponseEntity<List<RefraktionDTO>> getAllRefraktions() {
        return ResponseEntity.ok(refraktionService.findAll());
    }

    @GetMapping("/{refraktionId}")
    public ResponseEntity<RefraktionDTO> getRefraktion(@PathVariable final Integer refraktionId) {
        return ResponseEntity.ok(refraktionService.get(refraktionId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createRefraktion(
            @RequestBody @Valid final RefraktionDTO refraktionDTO) {
        return new ResponseEntity<>(refraktionService.create(refraktionDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{refraktionId}")
    public ResponseEntity<Void> updateRefraktion(@PathVariable final Integer refraktionId,
            @RequestBody @Valid final RefraktionDTO refraktionDTO) {
        refraktionService.update(refraktionId, refraktionDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{refraktionId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteRefraktion(@PathVariable final Integer refraktionId) {
        refraktionService.delete(refraktionId);
        return ResponseEntity.noContent().build();
    }

}
