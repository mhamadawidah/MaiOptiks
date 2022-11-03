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

    @GetMapping("/{krankenkassenNr}")
    public ResponseEntity<KrankenkasseDTO> getKrankenkasse(
            @PathVariable final String krankenkassenNr) {
        return ResponseEntity.ok(krankenkasseService.get(krankenkassenNr));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Void> createKrankenkasse(
            @RequestBody @Valid final KrankenkasseDTO krankenkasseDTO,
            final BindingResult bindingResult) throws MethodArgumentNotValidException {
        if (!bindingResult.hasFieldErrors("krankenkassennr") && krankenkasseDTO.getKrankenkassennr() == null) {
            bindingResult.rejectValue("krankenkassennr", "NotNull");
        }
        if (!bindingResult.hasFieldErrors("krankenkassennr") && krankenkasseService.krankenkassenNrExists(krankenkasseDTO.getKrankenkassennr())) {
            bindingResult.rejectValue("krankenkassennr", "Exists.krankenkasse.krankenkassennr");
        }
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(new MethodParameter(
                    this.getClass().getDeclaredMethods()[0], -1), bindingResult);
        }
        krankenkasseService.create(krankenkasseDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{krankenkassenNr}")
    public ResponseEntity<Void> updateKrankenkasse(@PathVariable final String krankenkassenNr,
            @RequestBody @Valid final KrankenkasseDTO krankenkasseDTO) {
        krankenkasseService.update(krankenkassenNr, krankenkasseDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{krankenkassenNr}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteKrankenkasse(@PathVariable final String krankenkassenNr) {
        krankenkasseService.delete(krankenkassenNr);
        return ResponseEntity.noContent().build();
    }

}
