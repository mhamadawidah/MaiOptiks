package MaiOptiks.controller;

import MaiOptiks.domain.*;
import MaiOptiks.model.GlaeserDTO;
import MaiOptiks.repos.*;
import MaiOptiks.service.GlaeserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/glaesers")
public class GlaeserController {

    private final GlaeserService glaeserService;
    private final ArtikelartRepository artikelartRepository;
    private final RefraktionRepository refraktionRepository;
    private final MaterialRepository materialRepository;
    private final FarbeRepository farbeRepository;
    private final LieferantRepository lieferantRepository;

    public GlaeserController(final GlaeserService glaeserService,
            final ArtikelartRepository artikelartRepository,
            final RefraktionRepository refraktionRepository,
            final MaterialRepository materialRepository, final FarbeRepository farbeRepository,
            final LieferantRepository lieferantRepository) {
        this.glaeserService = glaeserService;
        this.artikelartRepository = artikelartRepository;
        this.refraktionRepository = refraktionRepository;
        this.materialRepository = materialRepository;
        this.farbeRepository = farbeRepository;
        this.lieferantRepository = lieferantRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("artValues", artikelartRepository.findAll().stream().collect(
                Collectors.toMap(Artikelart::getArtId, Artikelart::getBezeichnung)));
        model.addAttribute("werteValues", refraktionRepository.findAll().stream().collect(
                Collectors.toMap(Refraktion::getRefraktionid, Refraktion::getRefraktionid)));
        model.addAttribute("materialValues", materialRepository.findAll().stream().collect(
                Collectors.toMap(Material::getMaterialId, Material::getBezeichung)));
        model.addAttribute("farbeValues", farbeRepository.findAll().stream().collect(
                Collectors.toMap(Farbe::getFarbeId, Farbe::getBezeichnung)));
        model.addAttribute("lieferantValues", lieferantRepository.findAll().stream().collect(
                Collectors.toMap(Lieferant::getLieferantId, Lieferant::getName)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("glaesers", glaeserService.findAll());
        return "glaeser/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("glaeser") final GlaeserDTO glaeserDTO) {
        return "glaeser/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("glaeser") @Valid final GlaeserDTO glaeserDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "glaeser/add";
        }
        glaeserService.create(glaeserDTO);
        redirectAttributes.addFlashAttribute("");
        return "redirect:/glaesers";
    }

    @GetMapping("/edit/{artikelNr}")
    public String edit(@PathVariable final Integer artikelNr, final Model model) {
        model.addAttribute("glaeser", glaeserService.get(artikelNr));
        return "glaeser/edit";
    }

    @PostMapping("/edit/{artikelNr}")
    public String edit(@PathVariable final Integer artikelNr,
            @ModelAttribute("glaeser") @Valid final GlaeserDTO glaeserDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "glaeser/edit";
        }
        glaeserService.update(artikelNr, glaeserDTO);
        redirectAttributes.addFlashAttribute("");
        return "redirect:/glaesers";
    }

    @PostMapping("/delete/{artikelNr}")
    public String delete(@PathVariable final Integer artikelNr,
            final RedirectAttributes redirectAttributes) {
        glaeserService.delete(artikelNr);
        redirectAttributes.addFlashAttribute("");
        return "redirect:/glaesers";
    }

}
