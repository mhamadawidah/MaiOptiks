package MaiOptiks.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import MaiOptiks.model.KrankenkasseDTO;
import MaiOptiks.service.KrankenkasseService;
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
@RequestMapping(value = "/api/krankenkasses", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Krankenkasse")
public class KrankenkasseResource {

    private final KrankenkasseService krankenkasseService;

    public KrankenkasseResource(final KrankenkasseService krankenkasseService) {
        this.krankenkasseService = krankenkasseService;
    }

    @GetMapping
    public ResponseEntity<List<KrankenkasseDTO>> getAllKrankenkasses() {
        return ResponseEntity.ok(krankenkasseService.findAll());
    }

    @GetMapping("/{krankenkassenID}")
    public ResponseEntity<KrankenkasseDTO> getKrankenkasse(
            @PathVariable final String krankenkassenID) {
        return ResponseEntity.ok(krankenkasseService.get(krankenkassenID));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Void> createKrankenkasse(
            @RequestBody @Valid final KrankenkasseDTO krankenkasseDTO,
            final BindingResult bindingResult) throws MethodArgumentNotValidException {
        if (!bindingResult.hasFieldErrors("krankenkassenID") && krankenkasseDTO.getKrankenkassenID() == null) {
            bindingResult.rejectValue("krankenkassenID", "NotNull");
        }
        if (!bindingResult.hasFieldErrors("krankenkassenID") && krankenkasseService.krankenkassenIDExists(krankenkasseDTO.getKrankenkassenID())) {
            bindingResult.rejectValue("krankenkassenID", "Exists.krankenkasse.krankenkassenID");
        }
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(new MethodParameter(
                    this.getClass().getDeclaredMethods()[0], -1), bindingResult);
        }
        krankenkasseService.create(krankenkasseDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{krankenkassenID}")
    public ResponseEntity<Void> updateKrankenkasse(@PathVariable final String krankenkassenID,
            @RequestBody @Valid final KrankenkasseDTO krankenkasseDTO) {
        krankenkasseService.update(krankenkassenID, krankenkasseDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{krankenkassenID}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteKrankenkasse(@PathVariable final String krankenkassenID) {
        krankenkasseService.delete(krankenkassenID);
        return ResponseEntity.noContent().build();
    }

}
