package MaiOptiks.controller;

import MaiOptiks.domain.Land;
import MaiOptiks.model.StadtDTO;
import MaiOptiks.repos.LandRepository;
import MaiOptiks.service.StadtService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/stadts")
public class StadtController {

    private final StadtService stadtService;
    private final LandRepository landRepository;

    public StadtController(final StadtService stadtService, final LandRepository landRepository) {
        this.stadtService = stadtService;
        this.landRepository = landRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("landValues", landRepository.findAll().stream().collect(
                Collectors.toMap(Land::getLandid, Land::getLandid)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("stadts", stadtService.findAll());
        return "stadt/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("stadt") final StadtDTO stadtDTO) {
        return "stadt/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("stadt") @Valid final StadtDTO stadtDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "stadt/add";
        }
        stadtService.create(stadtDTO);
        redirectAttributes.addFlashAttribute("");
        return "redirect:/stadts";
    }

    @GetMapping("/edit/{plz}")
    public String edit(@PathVariable final Integer plz, final Model model) {
        model.addAttribute("stadt", stadtService.get(plz));
        return "stadt/edit";
    }

    @PostMapping("/edit/{plz}")
    public String edit(@PathVariable final Integer plz,
            @ModelAttribute("stadt") @Valid final StadtDTO stadtDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "stadt/edit";
        }
        stadtService.update(plz, stadtDTO);
        redirectAttributes.addFlashAttribute("");
        return "redirect:/stadts";
    }

    @PostMapping("/delete/{plz}")
    public String delete(@PathVariable final Integer plz,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = stadtService.getReferencedWarning(plz);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute("");
        } else {
            stadtService.delete(plz);
            redirectAttributes.addFlashAttribute("");
        }
        return "redirect:/stadts";
    }

}
