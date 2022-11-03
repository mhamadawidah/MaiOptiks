package MaiOptiks.controller;

import MaiOptiks.model.LandDTO;
import MaiOptiks.service.LandService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


@Controller
@RequestMapping("/lands")
public class LandController {

    private final LandService landService;

    public LandController(final LandService landService) {
        this.landService = landService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("lands", landService.findAll());
        return "land/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("land") final LandDTO landDTO) {
        return "land/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("land") @Valid final LandDTO landDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (!bindingResult.hasFieldErrors("landid") && landDTO.getLandId() == null) {
            bindingResult.rejectValue("landid", "NotNull");
        }
        if (!bindingResult.hasFieldErrors("landid") && landService.landIdExists(landDTO.getLandId())) {
            bindingResult.rejectValue("landid", "Exists.land.landid");
        }
        if (bindingResult.hasErrors()) {
            return "land/add";
        }
        landService.create(landDTO);
        redirectAttributes.addFlashAttribute("");
        return "redirect:/lands";
    }

    @GetMapping("/edit/{landId}")
    public String edit(@PathVariable final String landId, final Model model) {
        model.addAttribute("land", landService.get(landId));
        return "land/edit";
    }

    @PostMapping("/edit/{landId}")
    public String edit(@PathVariable final String landId,
            @ModelAttribute("land") @Valid final LandDTO landDTO, final BindingResult bindingResult,
            final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "land/edit";
        }
        landService.update(landId, landDTO);
        redirectAttributes.addFlashAttribute("");
        return "redirect:/lands";
    }

    @PostMapping("/delete/{landId}")
    public String delete(@PathVariable final String landId,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = landService.getReferencedWarning(landId);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute("");
        } else {
            landService.delete(landId);
            redirectAttributes.addFlashAttribute("");
        }
        return "redirect:/lands";
    }

}
