package MaiOptiks.controller;

import MaiOptiks.domain.Stadt;
import MaiOptiks.model.ArztDTO;
import MaiOptiks.repos.StadtRepository;
import MaiOptiks.service.ArztService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/arzts")
public class ArztController {

    private final ArztService arztService;
    private final StadtRepository stadtRepository;

    public ArztController(final ArztService arztService, final StadtRepository stadtRepository) {
        this.arztService = arztService;
        this.stadtRepository = stadtRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("plzValues", stadtRepository.findAll().stream().collect(
                Collectors.toMap(Stadt::getPlz, Stadt::getOrt)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("arzts", arztService.findAll());
        return "arzt/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("arzt") final ArztDTO arztDTO) {
        return "arzt/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("arzt") @Valid final ArztDTO arztDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "arzt/add";
        }
        arztService.create(arztDTO);
        redirectAttributes.addFlashAttribute("");
        return "redirect:/arzts";
    }

    @GetMapping("/edit/{arztNr}")
    public String edit(@PathVariable final Integer arztNr, final Model model) {
        model.addAttribute("arzt", arztService.get(arztNr));
        return "arzt/edit";
    }

    @PostMapping("/edit/{arztNr}")
    public String edit(@PathVariable final Integer arztNr,
            @ModelAttribute("arzt") @Valid final ArztDTO arztDTO, final BindingResult bindingResult,
            final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "arzt/edit";
        }
        arztService.update(arztNr, arztDTO);
        redirectAttributes.addFlashAttribute("");
        return "redirect:/arzts";
    }

    @PostMapping("/delete/{arztNr}")
    public String delete(@PathVariable final Integer arztNr,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = arztService.getReferencedWarning(arztNr);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute("");
        } else {
            arztService.delete(arztNr);
            redirectAttributes.addFlashAttribute("");
        }
        return "redirect:/arzts";
    }

}
