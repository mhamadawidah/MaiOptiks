package MaiOptiks.controller;

import MaiOptiks.domain.Farbe;
import MaiOptiks.domain.Lieferant;
import MaiOptiks.domain.Material;
import MaiOptiks.model.ArtikelDTO;
import MaiOptiks.repos.FarbeRepository;
import MaiOptiks.repos.LieferantRepository;
import MaiOptiks.repos.MaterialRepository;
import MaiOptiks.service.ArtikelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/artikels")
public class ArtikelController {

    private final ArtikelService artikelService;
    private final MaterialRepository materialRepository;
    private final FarbeRepository farbeRepository;
    private final LieferantRepository lieferantRepository;

    public ArtikelController(final ArtikelService artikelService,
            final MaterialRepository materialRepository, final FarbeRepository farbeRepository,
            final LieferantRepository lieferantRepository) {
        this.artikelService = artikelService;
        this.materialRepository = materialRepository;
        this.farbeRepository = farbeRepository;
        this.lieferantRepository = lieferantRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("materialValues", materialRepository.findAll().stream().collect(
                Collectors.toMap(Material::getMaterialId, Material::getBezeichung)));
        model.addAttribute("farbeValues", farbeRepository.findAll().stream().collect(
                Collectors.toMap(Farbe::getFarbeId, Farbe::getBezeichnung)));
        model.addAttribute("lieferantValues", lieferantRepository.findAll().stream().collect(
                Collectors.toMap(Lieferant::getLieferantId, Lieferant::getName)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("artikels", artikelService.findAll());
        return "artikel/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("artikel") final ArtikelDTO artikelDTO) {
        return "artikel/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("artikel") @Valid final ArtikelDTO artikelDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "artikel/add";
        }
        artikelService.create(artikelDTO);
        redirectAttributes.addFlashAttribute("");
        return "redirect:/artikels";
    }

    @GetMapping("/edit/{artikelNr}")
    public String edit(@PathVariable final Integer artikelNr, final Model model) {
        model.addAttribute("artikel", artikelService.get(artikelNr));
        return "artikel/edit";
    }

    @PostMapping("/edit/{artikelNr}")
    public String edit(@PathVariable final Integer artikelNr,
            @ModelAttribute("artikel") @Valid final ArtikelDTO artikelDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "artikel/edit";
        }
        artikelService.update(artikelNr, artikelDTO);
        redirectAttributes.addFlashAttribute("");
        return "redirect:/artikels";
    }

    @PostMapping("/delete/{artikelNr}")
    public String delete(@PathVariable final Integer artikelNr,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = artikelService.getReferencedWarning(artikelNr);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute("");
        } else {
            artikelService.delete(artikelNr);
            redirectAttributes.addFlashAttribute("");
        }
        return "redirect:/artikels";
    }

}
