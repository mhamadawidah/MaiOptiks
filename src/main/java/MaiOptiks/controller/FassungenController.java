package MaiOptiks.controller;

import MaiOptiks.domain.Artikelart;
import MaiOptiks.domain.Farbe;
import MaiOptiks.domain.Lieferant;
import MaiOptiks.domain.Material;
import MaiOptiks.model.FassungenDTO;
import MaiOptiks.repos.ArtikelartRepository;
import MaiOptiks.repos.FarbeRepository;
import MaiOptiks.repos.LieferantRepository;
import MaiOptiks.repos.MaterialRepository;
import MaiOptiks.service.FassungenService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/fassungens")
public class FassungenController {

    private final FassungenService fassungenService;
    private final ArtikelartRepository artikelartRepository;
    private final MaterialRepository materialRepository;
    private final FarbeRepository farbeRepository;
    private final LieferantRepository lieferantRepository;

    public FassungenController(final FassungenService fassungenService,
            final ArtikelartRepository artikelartRepository,
            final MaterialRepository materialRepository, final FarbeRepository farbeRepository,
            final LieferantRepository lieferantRepository) {
        this.fassungenService = fassungenService;
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
                Collectors.toMap(Material::getMaterialid, Material::getBezeichung)));
        model.addAttribute("farbeValues", farbeRepository.findAll().stream().collect(
                Collectors.toMap(Farbe::getFarbeId, Farbe::getBezeichnung)));
        model.addAttribute("lieferantValues", lieferantRepository.findAll().stream().collect(
                Collectors.toMap(Lieferant::getLieferantid, Lieferant::getName)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("fassungens", fassungenService.findAll());
        return "fassungen/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("fassungen") final FassungenDTO fassungenDTO) {
        return "fassungen/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("fassungen") @Valid final FassungenDTO fassungenDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "fassungen/add";
        }
        fassungenService.create(fassungenDTO);
        redirectAttributes.addFlashAttribute("");
        return "redirect:/fassungens";
    }

    @GetMapping("/edit/{artikelNr}")
    public String edit(@PathVariable final Integer artikelNr, final Model model) {
        model.addAttribute("fassungen", fassungenService.get(artikelNr));
        return "fassungen/edit";
    }

    @PostMapping("/edit/{artikelNr}")
    public String edit(@PathVariable final Integer artikelNr,
            @ModelAttribute("fassungen") @Valid final FassungenDTO fassungenDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "fassungen/edit";
        }
        fassungenService.update(artikelNr, fassungenDTO);
        redirectAttributes.addFlashAttribute("");
        return "redirect:/fassungens";
    }

    @PostMapping("/delete/{artikelNr}")
    public String delete(@PathVariable final Integer artikelNr,
            final RedirectAttributes redirectAttributes) {
        fassungenService.delete(artikelNr);
        redirectAttributes.addFlashAttribute("");
        return "redirect:/fassungens";
    }

}
