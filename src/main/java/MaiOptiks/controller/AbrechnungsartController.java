package MaiOptiks.controller;

import MaiOptiks.model.AbrechnungsartDTO;
import MaiOptiks.service.AbrechnungsartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


@Controller
@RequestMapping("/abrechnungsarts")
public class AbrechnungsartController {

    private final AbrechnungsartService abrechnungsartService;

    public AbrechnungsartController(final AbrechnungsartService abrechnungsartService) {
        this.abrechnungsartService = abrechnungsartService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("abrechnungsarts", abrechnungsartService.findAll());
        return "abrechnungsart/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("abrechnungsart") final AbrechnungsartDTO abrechnungsartDTO) {
        return "abrechnungsart/add";
    }

    @PostMapping("/add")
    public String add(
            @ModelAttribute("abrechnungsart") @Valid final AbrechnungsartDTO abrechnungsartDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "abrechnungsart/add";
        }
        abrechnungsartService.create(abrechnungsartDTO);
        redirectAttributes.addFlashAttribute("");
        return "redirect:/abrechnungsarts";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Integer id, final Model model) {
        model.addAttribute("abrechnungsart", abrechnungsartService.get(id));
        return "abrechnungsart/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Integer id,
            @ModelAttribute("abrechnungsart") @Valid final AbrechnungsartDTO abrechnungsartDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "abrechnungsart/edit";
        }
        abrechnungsartService.update(id, abrechnungsartDTO);
        redirectAttributes.addFlashAttribute("");
        return "redirect:/abrechnungsarts";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Integer id,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = abrechnungsartService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute("");
        } else {
            abrechnungsartService.delete(id);
            redirectAttributes.addFlashAttribute("");
        }
        return "redirect:/abrechnungsarts";
    }

}
