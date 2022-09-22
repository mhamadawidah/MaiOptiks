package MaiOptiks.controller;

import MaiOptiks.domain.Abrechnungsart;
import MaiOptiks.domain.Kunde;
import MaiOptiks.domain.Mitarbeiter;
import MaiOptiks.domain.RefraktionDurchgefuert;
import MaiOptiks.model.AuftragDTO;
import MaiOptiks.repos.AbrechnungsartRepository;
import MaiOptiks.repos.KundeRepository;
import MaiOptiks.repos.MitarbeiterRepository;
import MaiOptiks.repos.RefraktionDurchgefuertRepository;
import MaiOptiks.service.AuftragService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/auftrags")
public class AuftragController {

    private final AuftragService auftragService;
    private final KundeRepository kundeRepository;
    private final MitarbeiterRepository mitarbeiterRepository;
    private final RefraktionDurchgefuertRepository refraktionDurchgefuertRepository;
    private final AbrechnungsartRepository abrechnungsartRepository;

    public AuftragController(final AuftragService auftragService,
            final KundeRepository kundeRepository,
            final MitarbeiterRepository mitarbeiterRepository,
            final RefraktionDurchgefuertRepository refraktionDurchgefuertRepository,
            final AbrechnungsartRepository abrechnungsartRepository) {
        this.auftragService = auftragService;
        this.kundeRepository = kundeRepository;
        this.mitarbeiterRepository = mitarbeiterRepository;
        this.refraktionDurchgefuertRepository = refraktionDurchgefuertRepository;
        this.abrechnungsartRepository = abrechnungsartRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("kundenNrValues", kundeRepository.findAll().stream().collect(
                Collectors.toMap(Kunde::getKundenNr, Kunde::getAnrede)));
        model.addAttribute("beraterValues", mitarbeiterRepository.findAll().stream().collect(
                Collectors.toMap(Mitarbeiter::getMitarbeiterNr, Mitarbeiter::getName)));
        model.addAttribute("werkstattValues", mitarbeiterRepository.findAll().stream().collect(
                Collectors.toMap(Mitarbeiter::getMitarbeiterNr, Mitarbeiter::getName)));
        model.addAttribute("refraktionValues", refraktionDurchgefuertRepository.findAll().stream().collect(
                Collectors.toMap(RefraktionDurchgefuert::getRefraktionsNr, RefraktionDurchgefuert::getRefraktionsNr)));
        model.addAttribute("abrechnungsValues", abrechnungsartRepository.findAll().stream().collect(
                Collectors.toMap(Abrechnungsart::getId, Abrechnungsart::getArt)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("auftrags", auftragService.findAll());
        return "auftrag/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("auftrag") final AuftragDTO auftragDTO) {
        return "auftrag/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("auftrag") @Valid final AuftragDTO auftragDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "auftrag/add";
        }
        auftragService.create(auftragDTO);
        redirectAttributes.addFlashAttribute("");
        return "redirect:/auftrags";
    }

    @GetMapping("/edit/{auftragsnummer}")
    public String edit(@PathVariable final Integer auftragsnummer, final Model model) {
        model.addAttribute("auftrag", auftragService.get(auftragsnummer));
        return "auftrag/edit";
    }

    @PostMapping("/edit/{auftragsnummer}")
    public String edit(@PathVariable final Integer auftragsnummer,
            @ModelAttribute("auftrag") @Valid final AuftragDTO auftragDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "auftrag/edit";
        }
        auftragService.update(auftragsnummer, auftragDTO);
        redirectAttributes.addFlashAttribute("");
        return "redirect:/auftrags";
    }

    @PostMapping("/delete/{auftragsnummer}")
    public String delete(@PathVariable final Integer auftragsnummer,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = auftragService.getReferencedWarning(auftragsnummer);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute("");
        } else {
            auftragService.delete(auftragsnummer);
            redirectAttributes.addFlashAttribute("");
        }
        return "redirect:/auftrags";
    }

}
