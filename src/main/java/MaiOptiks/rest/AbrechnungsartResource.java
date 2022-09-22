package MaiOptiks.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import MaiOptiks.model.AbrechnungsartDTO;
import MaiOptiks.service.AbrechnungsartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(value = "/api/abrechnungsarts", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Abrechnungsart")
public class AbrechnungsartResource {

    private final AbrechnungsartService abrechnungsartService;

    public AbrechnungsartResource(final AbrechnungsartService abrechnungsartService) {
        this.abrechnungsartService = abrechnungsartService;
    }

    @GetMapping
    @Operation(summary = "")
    public ResponseEntity<List<AbrechnungsartDTO>> getAllAbrechnungsarts() {
        return ResponseEntity.ok(abrechnungsartService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AbrechnungsartDTO> getAbrechnungsart(@PathVariable final Integer id) {
        return ResponseEntity.ok(abrechnungsartService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createAbrechnungsart(
            @RequestBody @Valid final AbrechnungsartDTO abrechnungsartDTO) {
        return new ResponseEntity<>(abrechnungsartService.create(abrechnungsartDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAbrechnungsart(@PathVariable final Integer id,
            @RequestBody @Valid final AbrechnungsartDTO abrechnungsartDTO) {
        abrechnungsartService.update(id, abrechnungsartDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteAbrechnungsart(@PathVariable final Integer id) {
        abrechnungsartService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
