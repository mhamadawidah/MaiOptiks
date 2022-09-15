package MaiOptiks.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import MaiOptiks.model.BrilleDTO;
import MaiOptiks.service.BrilleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(value = "/api/brilles", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Brille")
public class BrilleResource {

    private final BrilleService brilleService;

    public BrilleResource(final BrilleService brilleService) {
        this.brilleService = brilleService;
    }

    @GetMapping
    public ResponseEntity<List<BrilleDTO>> getAllBrilles() {
        return ResponseEntity.ok(brilleService.findAll());
    }

    @GetMapping("/{brillenId}")
    public ResponseEntity<BrilleDTO> getBrille(@PathVariable final Integer brillenId) {
        return ResponseEntity.ok(brilleService.get(brillenId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createBrille(@RequestBody @Valid final BrilleDTO brilleDTO) {
        return new ResponseEntity<>(brilleService.create(brilleDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{brillenId}")
    public ResponseEntity<Void> updateBrille(@PathVariable final Integer brillenId,
            @RequestBody @Valid final BrilleDTO brilleDTO) {
        brilleService.update(brillenId, brilleDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{brillenId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteBrille(@PathVariable final Integer brillenId) {
        brilleService.delete(brillenId);
        return ResponseEntity.noContent().build();
    }

}
