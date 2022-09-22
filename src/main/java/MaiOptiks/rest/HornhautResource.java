package MaiOptiks.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import MaiOptiks.model.HornhautDTO;
import MaiOptiks.service.HornhautService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(value = "/api/hornhauts", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Hornhaut")
public class HornhautResource {

    private final HornhautService hornhautService;

    public HornhautResource(final HornhautService hornhautService) {
        this.hornhautService = hornhautService;
    }

    @GetMapping
    public ResponseEntity<List<HornhautDTO>> getAllHornhauts() {
        return ResponseEntity.ok(hornhautService.findAll());
    }

    @GetMapping("/{hornhautId}")
    public ResponseEntity<HornhautDTO> getHornhaut(@PathVariable final Integer hornhautId) {
        return ResponseEntity.ok(hornhautService.get(hornhautId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createHornhaut(
            @RequestBody @Valid final HornhautDTO hornhautDTO) {
        return new ResponseEntity<>(hornhautService.create(hornhautDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{hornhautId}")
    public ResponseEntity<Void> updateHornhaut(@PathVariable final Integer hornhautId,
            @RequestBody @Valid final HornhautDTO hornhautDTO) {
        hornhautService.update(hornhautId, hornhautDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{hornhautId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteHornhaut(@PathVariable final Integer hornhautId) {
        hornhautService.delete(hornhautId);
        return ResponseEntity.noContent().build();
    }

}
