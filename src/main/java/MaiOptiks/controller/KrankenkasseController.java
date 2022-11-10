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
        if (!bindingResult.hasFieldErrors("krankenkassenID") && krankenkasseDTO.getKrankenkassenID() == null) {
            bindingResult.rejectValue("krankenkassenID", "NotNull");
        }
        if (!bindingResult.hasFieldErrors("krankenkassenID") && krankenkasseService.krankenkassenIDExists(krankenkasseDTO.getKrankenkassenID())) {
            bindingResult.rejectValue("krankenkassenID", "Exists.krankenkasse.krankenkassenID");
        }
        if (bindingResult.hasErrors()) {
            return "krankenkasse/add";
        }
        krankenkasseService.create(krankenkasseDTO);
        redirectAttributes.addFlashAttribute("");
        return "redirect:/krankenkasses";
    }

    @GetMapping("/edit/{krankenkassenID}")
    public String edit(@PathVariable final String krankenkassenID, final Model model) {
        model.addAttribute("krankenkasse", krankenkasseService.get(krankenkassenID));
        return "krankenkasse/edit";
    }

    @PostMapping("/edit/{krankenkassenID}")
    public String edit(@PathVariable final String krankenkassenID,
            @ModelAttribute("krankenkasse") @Valid final KrankenkasseDTO krankenkasseDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "krankenkasse/edit";
        }
        krankenkasseService.update(krankenkassenID, krankenkasseDTO);
        redirectAttributes.addFlashAttribute("");
        return "redirect:/krankenkasses";
    }

    @PostMapping("/delete/{krankenkassenID}")
    public String delete(@PathVariable final String krankenkassenID,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = krankenkasseService.getReferencedWarning(krankenkassenID);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute("");
        } else {
            krankenkasseService.delete(krankenkassenID);
            redirectAttributes.addFlashAttribute("");
        }
        return "redirect:/krankenkasses";
    }

}
