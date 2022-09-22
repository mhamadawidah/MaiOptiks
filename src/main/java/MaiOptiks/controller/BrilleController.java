package MaiOptiks.controller;

import MaiOptiks.domain.Artikel;
import MaiOptiks.model.BrilleDTO;
import MaiOptiks.repos.ArtikelRepository;
import MaiOptiks.service.BrilleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/brilles")
public class BrilleController {

    private final BrilleService brilleService;
    private final ArtikelRepository artikelRepository;

    public BrilleController(final BrilleService brilleService,
            final ArtikelRepository artikelRepository) {
        this.brilleService = brilleService;
        this.artikelRepository = artikelRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("glasArtikelIdlinksValues", artikelRepository.findAll().stream().collect(
                Collectors.toMap(Artikel::getArtikelNr, Artikel::getBezeichnung)));
        model.addAttribute("glasArtikelIdrechtsValues", artikelRepository.findAll().stream().collect(
                Collectors.toMap(Artikel::getArtikelNr, Artikel::getBezeichnung)));
        model.addAttribute("fassungsArtikelValues", artikelRepository.findAll().stream().collect(
                Collectors.toMap(Artikel::getArtikelNr, Artikel::getBezeichnung)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("brilles", brilleService.findAll());
        return "brille/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("brille") final BrilleDTO brilleDTO) {
        return "brille/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("brille") @Valid final BrilleDTO brilleDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "brille/add";
        }
        brilleService.create(brilleDTO);
        redirectAttributes.addFlashAttribute("");
        return "redirect:/brilles";
    }

    @GetMapping("/edit/{brillenId}")
    public String edit(@PathVariable final Integer brillenId, final Model model) {
        model.addAttribute("brille", brilleService.get(brillenId));
        return "brille/edit";
    }

    @PostMapping("/edit/{brillenId}")
    public String edit(@PathVariable final Integer brillenId,
            @ModelAttribute("brille") @Valid final BrilleDTO brilleDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "brille/edit";
        }
        brilleService.update(brillenId, brilleDTO);
        redirectAttributes.addFlashAttribute("");
        return "redirect:/brilles";
    }

    @PostMapping("/delete/{brillenId}")
    public String delete(@PathVariable final Integer brillenId,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = brilleService.getReferencedWarning(brillenId);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute("");
        } else {
            brilleService.delete(brillenId);
            redirectAttributes.addFlashAttribute("");
        }
        return "redirect:/brilles";
    }

}
