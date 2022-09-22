package MaiOptiks.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import MaiOptiks.model.MaterialDTO;
import MaiOptiks.service.MaterialService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(value = "/api/materials", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Material")
public class MaterialResource {

    private final MaterialService materialService;

    public MaterialResource(final MaterialService materialService) {
        this.materialService = materialService;
    }

    @GetMapping
    public ResponseEntity<List<MaterialDTO>> getAllMaterials() {
        return ResponseEntity.ok(materialService.findAll());
    }

    @GetMapping("/{materialId}")
    public ResponseEntity<MaterialDTO> getMaterial(@PathVariable final Integer materialId) {
        return ResponseEntity.ok(materialService.get(materialId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createMaterial(
            @RequestBody @Valid final MaterialDTO materialDTO) {
        return new ResponseEntity<>(materialService.create(materialDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{materialId}")
    public ResponseEntity<Void> updateMaterial(@PathVariable final Integer materialId,
            @RequestBody @Valid final MaterialDTO materialDTO) {
        materialService.update(materialId, materialDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{materialId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteMaterial(@PathVariable final Integer materialId) {
        materialService.delete(materialId);
        return ResponseEntity.noContent().build();
    }

}
