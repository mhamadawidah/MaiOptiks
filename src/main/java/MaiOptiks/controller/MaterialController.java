package MaiOptiks.controller;

import MaiOptiks.model.MaterialDTO;
import MaiOptiks.service.MaterialService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


@Controller
@RequestMapping("/materials")
public class MaterialController {

    private final MaterialService materialService;

    public MaterialController(final MaterialService materialService) {
        this.materialService = materialService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("materials", materialService.findAll());
        return "material/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("material") final MaterialDTO materialDTO) {
        return "material/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("material") @Valid final MaterialDTO materialDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "material/add";
        }
        materialService.create(materialDTO);
        redirectAttributes.addFlashAttribute("");
        return "redirect:/materials";
    }

    @GetMapping("/edit/{materialId}")
    public String edit(@PathVariable final Integer materialId, final Model model) {
        model.addAttribute("material", materialService.get(materialId));
        return "material/edit";
    }

    @PostMapping("/edit/{materialId}")
    public String edit(@PathVariable final Integer materialId,
            @ModelAttribute("material") @Valid final MaterialDTO materialDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "material/edit";
        }
        materialService.update(materialId, materialDTO);
        redirectAttributes.addFlashAttribute("");
        return "redirect:/materials";
    }

    @PostMapping("/delete/{materialId}")
    public String delete(@PathVariable final Integer materialId,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = materialService.getReferencedWarning(materialId);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute("");
        } else {
            materialService.delete(materialId);
            redirectAttributes.addFlashAttribute("");
        }
        return "redirect:/materials";
    }

}
