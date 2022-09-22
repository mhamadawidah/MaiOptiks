package MaiOptiks.controller;

import MaiOptiks.model.RefraktionDTO;
import MaiOptiks.service.RefraktionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


@Controller
@RequestMapping("/refraktions")
public class RefraktionController {

    private final RefraktionService refraktionService;

    public RefraktionController(final RefraktionService refraktionService) {
        this.refraktionService = refraktionService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("refraktions", refraktionService.findAll());
        return "refraktion/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("refraktion") final RefraktionDTO refraktionDTO) {
        return "refraktion/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("refraktion") @Valid final RefraktionDTO refraktionDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "refraktion/add";
        }
        refraktionService.create(refraktionDTO);
        redirectAttributes.addFlashAttribute("");
        return "redirect:/refraktions";
    }

    @GetMapping("/edit/{refraktionId}")
    public String edit(@PathVariable final Integer refraktionId, final Model model) {
        model.addAttribute("refraktion", refraktionService.get(refraktionId));
        return "refraktion/edit";
    }

    @PostMapping("/edit/{refraktionId}")
    public String edit(@PathVariable final Integer refraktionId,
            @ModelAttribute("refraktion") @Valid final RefraktionDTO refraktionDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "refraktion/edit";
        }
        refraktionService.update(refraktionId, refraktionDTO);
        redirectAttributes.addFlashAttribute("");
        return "redirect:/refraktions";
    }

    @PostMapping("/delete/{refraktionId}")
    public String delete(@PathVariable final Integer refraktionId,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = refraktionService.getReferencedWarning(refraktionId);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute("");
        } else {
            refraktionService.delete(refraktionId);
            redirectAttributes.addFlashAttribute("");
        }
        return "redirect:/refraktions";
    }

}
