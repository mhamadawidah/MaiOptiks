package MaiOptiks.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import MaiOptiks.model.LandDTO;
import MaiOptiks.service.LandService;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(value = "/api/lands", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Land")
public class LandResource {

    private final LandService landService;

    public LandResource(final LandService landService) {
        this.landService = landService;
    }

    @GetMapping
    public ResponseEntity<List<LandDTO>> getAllLands() {
        return ResponseEntity.ok(landService.findAll());
    }

    @GetMapping("/{landId}")
    public ResponseEntity<LandDTO> getLand(@PathVariable final String landId) {
        return ResponseEntity.ok(landService.get(landId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Void> createLand(@RequestBody @Valid final LandDTO landDTO,
            final BindingResult bindingResult) throws MethodArgumentNotValidException {
        if (!bindingResult.hasFieldErrors("landId") && landDTO.getLandId() == null) {
            bindingResult.rejectValue("landId", "NotNull");
        }
        if (!bindingResult.hasFieldErrors("landId") && landService.landIdExists(landDTO.getLandId())) {
            bindingResult.rejectValue("landId", "Exists.land.landId");
        }
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(new MethodParameter(
                    this.getClass().getDeclaredMethods()[0], -1), bindingResult);
        }
        landService.create(landDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{landId}")
    public ResponseEntity<Void> updateLand(@PathVariable final String landId,
            @RequestBody @Valid final LandDTO landDTO) {
        landService.update(landId, landDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{landId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteLand(@PathVariable final String landId) {
        landService.delete(landId);
        return ResponseEntity.noContent().build();
    }

}
