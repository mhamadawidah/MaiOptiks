package MaiOptiks.controller;

import MaiOptiks.domain.Arzt;
import MaiOptiks.domain.Mitarbeiter;
import MaiOptiks.model.RefraktionDurchgefuertDTO;
import MaiOptiks.repos.ArztRepository;
import MaiOptiks.repos.MitarbeiterRepository;
import MaiOptiks.service.RefraktionDurchgefuertService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/refraktionDurchgefuerts")
public class RefraktionDurchgefuertController {

    private final RefraktionDurchgefuertService refraktionDurchgefuertService;
    private final MitarbeiterRepository mitarbeiterRepository;
    private final ArztRepository arztRepository;

    public RefraktionDurchgefuertController(
            final RefraktionDurchgefuertService refraktionDurchgefuertService,
            final MitarbeiterRepository mitarbeiterRepository,
            final ArztRepository arztRepository) {
        this.refraktionDurchgefuertService = refraktionDurchgefuertService;
        this.mitarbeiterRepository = mitarbeiterRepository;
        this.arztRepository = arztRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("mitarbeiterNrValues", mitarbeiterRepository.findAll().stream().collect(
                Collectors.toMap(Mitarbeiter::getMitarbeiterNr, Mitarbeiter::getName)));
        model.addAttribute("arztNrValues", arztRepository.findAll().stream().collect(
                Collectors.toMap(Arzt::getArztNr, Arzt::getName)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("refraktionDurchgefuerts", refraktionDurchgefuertService.findAll());
        return "refraktionDurchgefuert/list";
    }

    @GetMapping("/add")
    public String add(
            @ModelAttribute("refraktionDurchgefuert") final RefraktionDurchgefuertDTO refraktionDurchgefuertDTO) {
        return "refraktionDurchgefuert/add";
    }

    @PostMapping("/add")
    public String add(
            @ModelAttribute("refraktionDurchgefuert") @Valid final RefraktionDurchgefuertDTO refraktionDurchgefuertDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "refraktionDurchgefuert/add";
        }
        refraktionDurchgefuertService.create(refraktionDurchgefuertDTO);
        redirectAttributes.addFlashAttribute("");
        return "redirect:/refraktionDurchgefuerts";
    }

    @GetMapping("/edit/{refraktionsNr}")
    public String edit(@PathVariable final Integer refraktionsNr, final Model model) {
        model.addAttribute("refraktionDurchgefuert", refraktionDurchgefuertService.get(refraktionsNr));
        return "refraktionDurchgefuert/edit";
    }

    @PostMapping("/edit/{refraktionsNr}")
    public String edit(@PathVariable final Integer refraktionsNr,
            @ModelAttribute("refraktionDurchgefuert") @Valid final RefraktionDurchgefuertDTO refraktionDurchgefuertDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "refraktionDurchgefuert/edit";
        }
        refraktionDurchgefuertService.update(refraktionsNr, refraktionDurchgefuertDTO);
        redirectAttributes.addFlashAttribute("");
        return "redirect:/refraktionDurchgefuerts";
    }

    @PostMapping("/delete/{refraktionsNr}")
    public String delete(@PathVariable final Integer refraktionsNr,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = refraktionDurchgefuertService.getReferencedWarning(refraktionsNr);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute("");
        } else {
            refraktionDurchgefuertService.delete(refraktionsNr);
            redirectAttributes.addFlashAttribute("");
        }
        return "redirect:/refraktionDurchgefuerts";
    }

}
