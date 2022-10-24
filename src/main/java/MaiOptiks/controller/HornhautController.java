package MaiOptiks.controller;

import MaiOptiks.domain.Refraktion;
import MaiOptiks.model.HornhautDTO;
import MaiOptiks.repos.RefraktionRepository;
import MaiOptiks.service.HornhautService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/hornhauts")
public class HornhautController {

    private final HornhautService hornhautService;
    private final RefraktionRepository refraktionRepository;

    public HornhautController(final HornhautService hornhautService,
            final RefraktionRepository refraktionRepository) {
        this.hornhautService = hornhautService;
        this.refraktionRepository = refraktionRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("refraktionValues", refraktionRepository.findAll().stream().collect(
                Collectors.toMap(Refraktion::getRefraktionid, Refraktion::getRefraktionid)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("hornhauts", hornhautService.findAll());
        return "hornhaut/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("hornhaut") final HornhautDTO hornhautDTO) {
        return "hornhaut/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("hornhaut") @Valid final HornhautDTO hornhautDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "hornhaut/add";
        }
        hornhautService.create(hornhautDTO);
        redirectAttributes.addFlashAttribute("");
        return "redirect:/hornhauts";
    }

    @GetMapping("/edit/{hornhautId}")
    public String edit(@PathVariable final Integer hornhautId, final Model model) {
        model.addAttribute("hornhaut", hornhautService.get(hornhautId));
        return "hornhaut/edit";
    }

    @PostMapping("/edit/{hornhautId}")
    public String edit(@PathVariable final Integer hornhautId,
            @ModelAttribute("hornhaut") @Valid final HornhautDTO hornhautDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "hornhaut/edit";
        }
        hornhautService.update(hornhautId, hornhautDTO);
        redirectAttributes.addFlashAttribute("");
        return "redirect:/hornhauts";
    }

    @PostMapping("/delete/{hornhautId}")
    public String delete(@PathVariable final Integer hornhautId,
            final RedirectAttributes redirectAttributes) {
        hornhautService.delete(hornhautId);
        redirectAttributes.addFlashAttribute("");
        return "redirect:/hornhauts";
    }

}
