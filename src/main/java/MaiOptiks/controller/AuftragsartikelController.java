package MaiOptiks.controller;

import MaiOptiks.domain.Auftrag;
import MaiOptiks.domain.Brille;
import MaiOptiks.model.AuftragsartikelDTO;
import MaiOptiks.repos.AuftragRepository;
import MaiOptiks.repos.BrilleRepository;
import MaiOptiks.service.AuftragsartikelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/auftragsartikels")
public class AuftragsartikelController {

    private final AuftragsartikelService auftragsartikelService;
    private final AuftragRepository auftragRepository;
    private final BrilleRepository brilleRepository;

    public AuftragsartikelController(final AuftragsartikelService auftragsartikelService,
            final AuftragRepository auftragRepository, final BrilleRepository brilleRepository) {
        this.auftragsartikelService = auftragsartikelService;
        this.auftragRepository = auftragRepository;
        this.brilleRepository = brilleRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("auftragsNrValues", auftragRepository.findAll().stream().collect(
                Collectors.toMap(Auftrag::getAuftragsnummer, Auftrag::getWomit)));
        model.addAttribute("sehhilfeValues", brilleRepository.findAll().stream().collect(
                Collectors.toMap(Brille::getBrillenId, Brille::getBrillenId)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("auftragsartikels", auftragsartikelService.findAll());
        return "auftragsartikel/list";
    }

    @GetMapping("/add")
    public String add(
            @ModelAttribute("auftragsartikel") final AuftragsartikelDTO auftragsartikelDTO) {
        return "auftragsartikel/add";
    }

    @PostMapping("/add")
    public String add(
            @ModelAttribute("auftragsartikel") @Valid final AuftragsartikelDTO auftragsartikelDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "auftragsartikel/add";
        }
        auftragsartikelService.create(auftragsartikelDTO);
        redirectAttributes.addFlashAttribute("");
        return "redirect:/auftragsartikels";
    }

    @GetMapping("/edit/{auftragsArtikelId}")
    public String edit(@PathVariable final Integer auftragsArtikelId, final Model model) {
        model.addAttribute("auftragsartikel", auftragsartikelService.get(auftragsArtikelId));
        return "auftragsartikel/edit";
    }

    @PostMapping("/edit/{auftragsArtikelId}")
    public String edit(@PathVariable final Integer auftragsArtikelId,
            @ModelAttribute("auftragsartikel") @Valid final AuftragsartikelDTO auftragsartikelDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "auftragsartikel/edit";
        }
        auftragsartikelService.update(auftragsArtikelId, auftragsartikelDTO);
        redirectAttributes.addFlashAttribute("");
        return "redirect:/auftragsartikels";
    }

    @PostMapping("/delete/{auftragsArtikelId}")
    public String delete(@PathVariable final Integer auftragsArtikelId,
            final RedirectAttributes redirectAttributes) {
        auftragsartikelService.delete(auftragsArtikelId);
        redirectAttributes.addFlashAttribute("");
        return "redirect:/auftragsartikels";
    }

}
