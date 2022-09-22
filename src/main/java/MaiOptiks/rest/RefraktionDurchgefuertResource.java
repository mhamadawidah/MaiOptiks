package MaiOptiks.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import MaiOptiks.model.RefraktionDurchgefuertDTO;
import MaiOptiks.service.RefraktionDurchgefuertService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(value = "/api/refraktionDurchgefuerts", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "RefraktionDurchgefuert")
public class RefraktionDurchgefuertResource {

    private final RefraktionDurchgefuertService refraktionDurchgefuertService;

    public RefraktionDurchgefuertResource(
            final RefraktionDurchgefuertService refraktionDurchgefuertService) {
        this.refraktionDurchgefuertService = refraktionDurchgefuertService;
    }

    @GetMapping
    public ResponseEntity<List<RefraktionDurchgefuertDTO>> getAllRefraktionDurchgefuerts() {
        return ResponseEntity.ok(refraktionDurchgefuertService.findAll());
    }

    @GetMapping("/{refraktionsNr}")
    public ResponseEntity<RefraktionDurchgefuertDTO> getRefraktionDurchgefuert(
            @PathVariable final Integer refraktionsNr) {
        return ResponseEntity.ok(refraktionDurchgefuertService.get(refraktionsNr));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createRefraktionDurchgefuert(
            @RequestBody @Valid final RefraktionDurchgefuertDTO refraktionDurchgefuertDTO) {
        return new ResponseEntity<>(refraktionDurchgefuertService.create(refraktionDurchgefuertDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{refraktionsNr}")
    public ResponseEntity<Void> updateRefraktionDurchgefuert(
            @PathVariable final Integer refraktionsNr,
            @RequestBody @Valid final RefraktionDurchgefuertDTO refraktionDurchgefuertDTO) {
        refraktionDurchgefuertService.update(refraktionsNr, refraktionDurchgefuertDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{refraktionsNr}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteRefraktionDurchgefuert(
            @PathVariable final Integer refraktionsNr) {
        refraktionDurchgefuertService.delete(refraktionsNr);
        return ResponseEntity.noContent().build();
    }

}
