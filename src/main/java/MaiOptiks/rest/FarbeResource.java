package MaiOptiks.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import MaiOptiks.model.FarbeDTO;
import MaiOptiks.service.FarbeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(value = "/api/farbes", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Farbe")
public class FarbeResource {

    private final FarbeService farbeService;

    public FarbeResource(final FarbeService farbeService) {
        this.farbeService = farbeService;
    }

    @GetMapping
    public ResponseEntity<List<FarbeDTO>> getAllFarbes() {
        return ResponseEntity.ok(farbeService.findAll());
    }

    @GetMapping("/{farbeId}")
    public ResponseEntity<FarbeDTO> getFarbe(@PathVariable final Integer farbeId) {
        return ResponseEntity.ok(farbeService.get(farbeId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createFarbe(@RequestBody @Valid final FarbeDTO farbeDTO) {
        return new ResponseEntity<>(farbeService.create(farbeDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{farbeId}")
    public ResponseEntity<Void> updateFarbe(@PathVariable final Integer farbeId,
            @RequestBody @Valid final FarbeDTO farbeDTO) {
        farbeService.update(farbeId, farbeDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{farbeId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteFarbe(@PathVariable final Integer farbeId) {
        farbeService.delete(farbeId);
        return ResponseEntity.noContent().build();
    }

}
