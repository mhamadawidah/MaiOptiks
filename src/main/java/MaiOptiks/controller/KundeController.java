package MaiOptiks.controller;

import MaiOptiks.domain.Krankenkasse;
import MaiOptiks.domain.Stadt;
import MaiOptiks.model.KundeDTO;
import MaiOptiks.repos.KrankenkasseRepository;
import MaiOptiks.repos.StadtRepository;
import MaiOptiks.service.KundeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/kundes")
public class KundeController {

    private final KundeService kundeService;
    private final StadtRepository stadtRepository;
    private final KrankenkasseRepository krankenkasseRepository;

    public KundeController(final KundeService kundeService, final StadtRepository stadtRepository,
            final KrankenkasseRepository krankenkasseRepository) {
        this.kundeService = kundeService;
        this.stadtRepository = stadtRepository;
        this.krankenkasseRepository = krankenkasseRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("plzValues", stadtRepository.findAll().stream().collect(
                Collectors.toMap(Stadt::getPlz, Stadt::getOrt)));
        model.addAttribute("krankenkassenNrValues", krankenkasseRepository.findAll().stream().collect(
                Collectors.toMap(Krankenkasse::getKrankenkassenNr, Krankenkasse::getKrankenkassenNr)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("kundes", kundeService.findAll());
        return "kunde/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("kunde") final KundeDTO kundeDTO) {
        return "kunde/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("kunde") @Valid final KundeDTO kundeDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "kunde/add";
        }
        kundeService.create(kundeDTO);
        redirectAttributes.addFlashAttribute("");
        return "redirect:/kundes";
    }

    @GetMapping("/edit/{kundenNr}")
    public String edit(@PathVariable final Integer kundenNr, final Model model) {
        model.addAttribute("kunde", kundeService.get(kundenNr));
        return "kunde/edit";
    }

    @PostMapping("/edit/{kundenNr}")
    public String edit(@PathVariable final Integer kundenNr,
            @ModelAttribute("kunde") @Valid final KundeDTO kundeDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "kunde/edit";
        }
        kundeService.update(kundenNr, kundeDTO);
        redirectAttributes.addFlashAttribute("");
        return "redirect:/kundes";
    }

    @PostMapping("/delete/{kundenNr}")
    public String delete(@PathVariable final Integer kundenNr,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = kundeService.getReferencedWarning(kundenNr);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute("");
        } else {
            kundeService.delete(kundenNr);
            redirectAttributes.addFlashAttribute("");
        }
        return "redirect:/kundes";
    }

}
