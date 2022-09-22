package MaiOptiks.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import MaiOptiks.model.FirmenstammDTO;
import MaiOptiks.service.FirmenstammService;
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
@RequestMapping(value = "/api/firmenstamms", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Firmenstamm")
public class FirmenstammResource {

    private final FirmenstammService firmenstammService;

    public FirmenstammResource(final FirmenstammService firmenstammService) {
        this.firmenstammService = firmenstammService;
    }

    @GetMapping
    public ResponseEntity<List<FirmenstammDTO>> getAllFirmenstamms() {
        return ResponseEntity.ok(firmenstammService.findAll());
    }

    @GetMapping("/{augenoptikerIknr}")
    public ResponseEntity<FirmenstammDTO> getFirmenstamm(
            @PathVariable final String augenoptikerIknr) {
        return ResponseEntity.ok(firmenstammService.get(augenoptikerIknr));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Void> createFirmenstamm(
            @RequestBody @Valid final FirmenstammDTO firmenstammDTO,
            final BindingResult bindingResult) throws MethodArgumentNotValidException {
        if (!bindingResult.hasFieldErrors("augenoptikerIknr") && firmenstammDTO.getAugenoptikerIknr() == null) {
            bindingResult.rejectValue("augenoptikerIknr", "NotNull");
        }
        if (!bindingResult.hasFieldErrors("augenoptikerIknr") && firmenstammService.augenoptikerIknrExists(firmenstammDTO.getAugenoptikerIknr())) {
            bindingResult.rejectValue("augenoptikerIknr", "Exists.firmenstamm.augenoptikerIknr");
        }
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(new MethodParameter(
                    this.getClass().getDeclaredMethods()[0], -1), bindingResult);
        }
        firmenstammService.create(firmenstammDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{augenoptikerIknr}")
    public ResponseEntity<Void> updateFirmenstamm(@PathVariable final String augenoptikerIknr,
            @RequestBody @Valid final FirmenstammDTO firmenstammDTO) {
        firmenstammService.update(augenoptikerIknr, firmenstammDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{augenoptikerIknr}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteFirmenstamm(@PathVariable final String augenoptikerIknr) {
        firmenstammService.delete(augenoptikerIknr);
        return ResponseEntity.noContent().build();
    }

}
