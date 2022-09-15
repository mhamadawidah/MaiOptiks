package MaiOptiks.controller;

import MaiOptiks.domain.Artikelart;
import MaiOptiks.domain.Farbe;
import MaiOptiks.domain.Lieferant;
import MaiOptiks.domain.Material;
import MaiOptiks.model.KontaktlinsenDTO;
import MaiOptiks.repos.ArtikelartRepository;
import MaiOptiks.repos.FarbeRepository;
import MaiOptiks.repos.LieferantRepository;
import MaiOptiks.repos.MaterialRepository;
import MaiOptiks.service.KontaktlinsenService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/kontaktlinsens")
public class KontaktlinsenController {

    private final KontaktlinsenService kontaktlinsenService;
    private final ArtikelartRepository artikelartRepository;
    private final MaterialRepository materialRepository;
    private final FarbeRepository farbeRepository;
    private final LieferantRepository lieferantRepository;

    public KontaktlinsenController(final KontaktlinsenService kontaktlinsenService,
            final ArtikelartRepository artikelartRepository,
            final MaterialRepository materialRepository, final FarbeRepository farbeRepository,
            final LieferantRepository lieferantRepository) {
        this.kontaktlinsenService = kontaktlinsenService;
        this.artikelartRepository = artikelartRepository;
        this.materialRepository = materialRepository;
        this.farbeRepository = farbeRepository;
        this.lieferantRepository = lieferantRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("artValues", artikelartRepository.findAll().stream().collect(
                Collectors.toMap(Artikelart::getArtId, Artikelart::getBezeichnung)));
        model.addAttribute("materialValues", materialRepository.findAll().stream().collect(
                Collectors.toMap(Material::getMaterialId, Material::getBezeichung)));
        model.addAttribute("farbeValues", farbeRepository.findAll().stream().collect(
                Collectors.toMap(Farbe::getFarbeId, Farbe::getBezeichnung)));
        model.addAttribute("lieferantValues", lieferantRepository.findAll().stream().collect(
                Collectors.toMap(Lieferant::getLieferantId, Lieferant::getName)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("kontaktlinsens", kontaktlinsenService.findAll());
        return "kontaktlinsen/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("kontaktlinsen") final KontaktlinsenDTO kontaktlinsenDTO) {
        return "kontaktlinsen/add";
    }

    @PostMapping("/add")
    public String add(
            @ModelAttribute("kontaktlinsen") @Valid final KontaktlinsenDTO kontaktlinsenDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "kontaktlinsen/add";
        }
        kontaktlinsenService.create(kontaktlinsenDTO);
        redirectAttributes.addFlashAttribute("");
        return "redirect:/kontaktlinsens";
    }

    @GetMapping("/edit/{artikelNr}")
    public String edit(@PathVariable final Integer artikelNr, final Model model) {
        model.addAttribute("kontaktlinsen", kontaktlinsenService.get(artikelNr));
        return "kontaktlinsen/edit";
    }

    @PostMapping("/edit/{artikelNr}")
    public String edit(@PathVariable final Integer artikelNr,
            @ModelAttribute("kontaktlinsen") @Valid final KontaktlinsenDTO kontaktlinsenDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "kontaktlinsen/edit";
        }
        kontaktlinsenService.update(artikelNr, kontaktlinsenDTO);
        redirectAttributes.addFlashAttribute("");
        return "redirect:/kontaktlinsens";
    }

    @PostMapping("/delete/{artikelNr}")
    public String delete(@PathVariable final Integer artikelNr,
            final RedirectAttributes redirectAttributes) {
        kontaktlinsenService.delete(artikelNr);
        redirectAttributes.addFlashAttribute("");
        return "redirect:/kontaktlinsens";
    }

}
