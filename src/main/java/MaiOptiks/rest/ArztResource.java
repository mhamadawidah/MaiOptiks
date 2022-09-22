package MaiOptiks.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import MaiOptiks.model.ArztDTO;
import MaiOptiks.service.ArztService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(value = "/api/arzts", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Arzt")
public class ArztResource {

    private final ArztService arztService;

    public ArztResource(final ArztService arztService) {
        this.arztService = arztService;
    }

    @GetMapping
    public ResponseEntity<List<ArztDTO>> getAllArzts() {
        return ResponseEntity.ok(arztService.findAll());
    }

    @GetMapping("/{arztNr}")
    public ResponseEntity<ArztDTO> getArzt(@PathVariable final Integer arztNr) {
        return ResponseEntity.ok(arztService.get(arztNr));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createArzt(@RequestBody @Valid final ArztDTO arztDTO) {
        return new ResponseEntity<>(arztService.create(arztDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{arztNr}")
    public ResponseEntity<Void> updateArzt(@PathVariable final Integer arztNr,
            @RequestBody @Valid final ArztDTO arztDTO) {
        arztService.update(arztNr, arztDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{arztNr}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteArzt(@PathVariable final Integer arztNr) {
        arztService.delete(arztNr);
        return ResponseEntity.noContent().build();
    }

}
