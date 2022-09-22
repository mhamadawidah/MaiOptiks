package MaiOptiks.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import MaiOptiks.model.StadtDTO;
import MaiOptiks.service.StadtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(value = "/api/stadts", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Stadt")
public class StadtResource {

    private final StadtService stadtService;

    public StadtResource(final StadtService stadtService) {
        this.stadtService = stadtService;
    }

    @GetMapping
    public ResponseEntity<List<StadtDTO>> getAllStadts() {
        return ResponseEntity.ok(stadtService.findAll());
    }

    @GetMapping("/{plz}")
    public ResponseEntity<StadtDTO> getStadt(@PathVariable final Integer plz) {
        return ResponseEntity.ok(stadtService.get(plz));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createStadt(@RequestBody @Valid final StadtDTO stadtDTO) {
        return new ResponseEntity<>(stadtService.create(stadtDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{plz}")
    public ResponseEntity<Void> updateStadt(@PathVariable final Integer plz,
            @RequestBody @Valid final StadtDTO stadtDTO) {
        stadtService.update(plz, stadtDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{plz}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteStadt(@PathVariable final Integer plz) {
        stadtService.delete(plz);
        return ResponseEntity.noContent().build();
    }

}
