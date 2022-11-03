package MaiOptiks.controller;

import MaiOptiks.domain.Stadt;
import MaiOptiks.model.KrankenkasseDTO;
import MaiOptiks.repos.StadtRepository;
import MaiOptiks.service.KrankenkasseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/krankenkasses")
public class KrankenkasseController {

    private final KrankenkasseService krankenkasseService;
    private final StadtRepository stadtRepository;

    public KrankenkasseController(final KrankenkasseService krankenkasseService,
            final StadtRepository stadtRepository) {
        this.krankenkasseService = krankenkasseService;
        this.stadtRepository = stadtRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("plzValues", stadtRepository.findAll().stream().collect(
                Collectors.toMap(Stadt::getPlz, Stadt::getOrt)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("krankenkasses", krankenkasseService.findAll());
        return "krankenkasse/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("krankenkasse") final KrankenkasseDTO krankenkasseDTO) {
        return "krankenkasse/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("krankenkasse") @Valid final KrankenkasseDTO krankenkasseDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (!bindingResult.hasFieldErrors("krankenkassennr") && krankenkasseDTO.getKrankenkassennr() == null) {
            bindingResult.rejectValue("krankenkassennr", "NotNull");
        }
        if (!bindingResult.hasFieldErrors("krankenkassennr") && krankenkasseService.krankenkassenNrExists(krankenkasseDTO.getKrankenkassennr())) {
            bindingResult.rejectValue("krankenkassennr", "Exists.krankenkasse.krankenkassennr");
        }
        if (bindingResult.hasErrors()) {
            return "krankenkasse/add";
        }
        krankenkasseService.create(krankenkasseDTO);
        redirectAttributes.addFlashAttribute("");
        return "redirect:/krankenkasses";
    }

    @GetMapping("/edit/{krankenkassenNr}")
    public String edit(@PathVariable final String krankenkassenNr, final Model model) {
        model.addAttribute("krankenkasse", krankenkasseService.get(krankenkassenNr));
        return "krankenkasse/edit";
    }

    @PostMapping("/edit/{krankenkassenNr}")
    public String edit(@PathVariable final String krankenkassenNr,
            @ModelAttribute("krankenkasse") @Valid final KrankenkasseDTO krankenkasseDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "krankenkasse/edit";
        }
        krankenkasseService.update(krankenkassenNr, krankenkasseDTO);
        redirectAttributes.addFlashAttribute("");
        return "redirect:/krankenkasses";
    }

    @PostMapping("/delete/{krankenkassenNr}")
    public String delete(@PathVariable final String krankenkassenNr,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = krankenkasseService.getReferencedWarning(krankenkassenNr);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute("");
        } else {
            krankenkasseService.delete(krankenkassenNr);
            redirectAttributes.addFlashAttribute("");
        }
        return "redirect:/krankenkasses";
    }

}
