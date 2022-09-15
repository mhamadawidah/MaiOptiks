package MaiOptiks.controller;

import MaiOptiks.model.FarbeDTO;
import MaiOptiks.service.FarbeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


@Controller
@RequestMapping("/farbes")
public class FarbeController {

    private final FarbeService farbeService;

    public FarbeController(final FarbeService farbeService) {
        this.farbeService = farbeService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("farbes", farbeService.findAll());
        return "farbe/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("farbe") final FarbeDTO farbeDTO) {
        return "farbe/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("farbe") @Valid final FarbeDTO farbeDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "farbe/add";
        }
        farbeService.create(farbeDTO);
        redirectAttributes.addFlashAttribute("");
        return "redirect:/farbes";
    }

    @GetMapping("/edit/{farbeId}")
    public String edit(@PathVariable final Integer farbeId, final Model model) {
        model.addAttribute("farbe", farbeService.get(farbeId));
        return "farbe/edit";
    }

    @PostMapping("/edit/{farbeId}")
    public String edit(@PathVariable final Integer farbeId,
            @ModelAttribute("farbe") @Valid final FarbeDTO farbeDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "farbe/edit";
        }
        farbeService.update(farbeId, farbeDTO);
        redirectAttributes.addFlashAttribute("");
        return "redirect:/farbes";
    }

    @PostMapping("/delete/{farbeId}")
    public String delete(@PathVariable final Integer farbeId,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = farbeService.getReferencedWarning(farbeId);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute("");
        } else {
            farbeService.delete(farbeId);
            redirectAttributes.addFlashAttribute("");
        }
        return "redirect:/farbes";
    }

}
