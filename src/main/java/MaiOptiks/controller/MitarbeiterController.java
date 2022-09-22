package MaiOptiks.controller;

import MaiOptiks.domain.Stadt;
import MaiOptiks.model.MitarbeiterDTO;
import MaiOptiks.repos.StadtRepository;
import MaiOptiks.service.MitarbeiterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/mitarbeiters")
public class MitarbeiterController {

    private final MitarbeiterService mitarbeiterService;
    private final StadtRepository stadtRepository;

    public MitarbeiterController(final MitarbeiterService mitarbeiterService,
            final StadtRepository stadtRepository) {
        this.mitarbeiterService = mitarbeiterService;
        this.stadtRepository = stadtRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("plzValues", stadtRepository.findAll().stream().collect(
                Collectors.toMap(Stadt::getPlz, Stadt::getOrt)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("mitarbeiters", mitarbeiterService.findAll());
        return "mitarbeiter/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("mitarbeiter") final MitarbeiterDTO mitarbeiterDTO) {
        return "mitarbeiter/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("mitarbeiter") @Valid final MitarbeiterDTO mitarbeiterDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "mitarbeiter/add";
        }
        mitarbeiterService.create(mitarbeiterDTO);
        redirectAttributes.addFlashAttribute("");
        return "redirect:/mitarbeiters";
    }

    @GetMapping("/edit/{mitarbeiterNr}")
    public String edit(@PathVariable final Integer mitarbeiterNr, final Model model) {
        model.addAttribute("mitarbeiter", mitarbeiterService.get(mitarbeiterNr));
        return "mitarbeiter/edit";
    }

    @PostMapping("/edit/{mitarbeiterNr}")
    public String edit(@PathVariable final Integer mitarbeiterNr,
            @ModelAttribute("mitarbeiter") @Valid final MitarbeiterDTO mitarbeiterDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "mitarbeiter/edit";
        }
        mitarbeiterService.update(mitarbeiterNr, mitarbeiterDTO);
        redirectAttributes.addFlashAttribute("");
        return "redirect:/mitarbeiters";
    }

    @PostMapping("/delete/{mitarbeiterNr}")
    public String delete(@PathVariable final Integer mitarbeiterNr,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = mitarbeiterService.getReferencedWarning(mitarbeiterNr);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute("");
        } else {
            mitarbeiterService.delete(mitarbeiterNr);
            redirectAttributes.addFlashAttribute("");
        }
        return "redirect:/mitarbeiters";
    }

}
