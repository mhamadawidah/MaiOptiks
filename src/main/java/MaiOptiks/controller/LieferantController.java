package MaiOptiks.controller;

import MaiOptiks.domain.Stadt;
import MaiOptiks.model.LieferantDTO;
import MaiOptiks.repos.StadtRepository;
import MaiOptiks.service.LieferantService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/lieferants")
public class LieferantController {

    private final LieferantService lieferantService;
    private final StadtRepository stadtRepository;

    public LieferantController(final LieferantService lieferantService,
            final StadtRepository stadtRepository) {
        this.lieferantService = lieferantService;
        this.stadtRepository = stadtRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("plzValues", stadtRepository.findAll().stream().collect(
                Collectors.toMap(Stadt::getPlz, Stadt::getOrt)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("lieferants", lieferantService.findAll());
        return "lieferant/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("lieferant") final LieferantDTO lieferantDTO) {
        return "lieferant/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("lieferant") @Valid final LieferantDTO lieferantDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "lieferant/add";
        }
        lieferantService.create(lieferantDTO);
        redirectAttributes.addFlashAttribute("");
        return "redirect:/lieferants";
    }

    @GetMapping("/edit/{lieferantId}")
    public String edit(@PathVariable final Integer lieferantId, final Model model) {
        model.addAttribute("lieferant", lieferantService.get(lieferantId));
        return "lieferant/edit";
    }

    @PostMapping("/edit/{lieferantId}")
    public String edit(@PathVariable final Integer lieferantId,
            @ModelAttribute("lieferant") @Valid final LieferantDTO lieferantDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "lieferant/edit";
        }
        lieferantService.update(lieferantId, lieferantDTO);
        redirectAttributes.addFlashAttribute("");
        return "redirect:/lieferants";
    }

    @PostMapping("/delete/{lieferantId}")
    public String delete(@PathVariable final Integer lieferantId,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = lieferantService.getReferencedWarning(lieferantId);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute("");
        } else {
            lieferantService.delete(lieferantId);
            redirectAttributes.addFlashAttribute("");
        }
        return "redirect:/lieferants";
    }

}
