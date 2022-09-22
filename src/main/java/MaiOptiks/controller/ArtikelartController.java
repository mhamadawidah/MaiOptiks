package MaiOptiks.controller;

import MaiOptiks.model.ArtikelartDTO;
import MaiOptiks.service.ArtikelartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


@Controller
@RequestMapping("/artikelarts")
public class ArtikelartController {

    private final ArtikelartService artikelartService;

    public ArtikelartController(final ArtikelartService artikelartService) {
        this.artikelartService = artikelartService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("artikelarts", artikelartService.findAll());
        return "artikelart/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("artikelart") final ArtikelartDTO artikelartDTO) {
        return "artikelart/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("artikelart") @Valid final ArtikelartDTO artikelartDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "artikelart/add";
        }
        artikelartService.create(artikelartDTO);
        redirectAttributes.addFlashAttribute("");
        return "redirect:/artikelarts";
    }

    @GetMapping("/edit/{artId}")
    public String edit(@PathVariable final Integer artId, final Model model) {
        model.addAttribute("artikelart", artikelartService.get(artId));
        return "artikelart/edit";
    }

    @PostMapping("/edit/{artId}")
    public String edit(@PathVariable final Integer artId,
            @ModelAttribute("artikelart") @Valid final ArtikelartDTO artikelartDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "artikelart/edit";
        }
        artikelartService.update(artId, artikelartDTO);
        redirectAttributes.addFlashAttribute("");
        return "redirect:/artikelarts";
    }

    @PostMapping("/delete/{artId}")
    public String delete(@PathVariable final Integer artId,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = artikelartService.getReferencedWarning(artId);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute("");
        } else {
            artikelartService.delete(artId);
            redirectAttributes.addFlashAttribute("");
        }
        return "redirect:/artikelarts";
    }

}
