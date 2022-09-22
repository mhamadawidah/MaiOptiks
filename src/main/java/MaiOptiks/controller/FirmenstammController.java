package MaiOptiks.controller;

import MaiOptiks.domain.Stadt;
import MaiOptiks.model.FirmenstammDTO;
import MaiOptiks.repos.StadtRepository;
import MaiOptiks.service.FirmenstammService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/firmenstamms")
public class FirmenstammController {

    private final FirmenstammService firmenstammService;
    private final StadtRepository stadtRepository;

    public FirmenstammController(final FirmenstammService firmenstammService,
            final StadtRepository stadtRepository) {
        this.firmenstammService = firmenstammService;
        this.stadtRepository = stadtRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("plzValues", stadtRepository.findAll().stream().collect(
                Collectors.toMap(Stadt::getPlz, Stadt::getOrt)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("firmenstamms", firmenstammService.findAll());
        return "firmenstamm/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("firmenstamm") final FirmenstammDTO firmenstammDTO) {
        return "firmenstamm/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("firmenstamm") @Valid final FirmenstammDTO firmenstammDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (!bindingResult.hasFieldErrors("augenoptikerIknr") && firmenstammDTO.getAugenoptikerIknr() == null) {
            bindingResult.rejectValue("augenoptikerIknr", "NotNull");
        }
        if (!bindingResult.hasFieldErrors("augenoptikerIknr") && firmenstammService.augenoptikerIknrExists(firmenstammDTO.getAugenoptikerIknr())) {
            bindingResult.rejectValue("augenoptikerIknr", "Exists.firmenstamm.augenoptikerIknr");
        }
        if (bindingResult.hasErrors()) {
            return "firmenstamm/add";
        }
        firmenstammService.create(firmenstammDTO);
        redirectAttributes.addFlashAttribute("");
        return "redirect:/firmenstamms";
    }

    @GetMapping("/edit/{augenoptikerIknr}")
    public String edit(@PathVariable final String augenoptikerIknr, final Model model) {
        model.addAttribute("firmenstamm", firmenstammService.get(augenoptikerIknr));
        return "firmenstamm/edit";
    }

    @PostMapping("/edit/{augenoptikerIknr}")
    public String edit(@PathVariable final String augenoptikerIknr,
            @ModelAttribute("firmenstamm") @Valid final FirmenstammDTO firmenstammDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "firmenstamm/edit";
        }
        firmenstammService.update(augenoptikerIknr, firmenstammDTO);
        redirectAttributes.addFlashAttribute("");
        return "redirect:/firmenstamms";
    }

    @PostMapping("/delete/{augenoptikerIknr}")
    public String delete(@PathVariable final String augenoptikerIknr,
            final RedirectAttributes redirectAttributes) {
        firmenstammService.delete(augenoptikerIknr);
        redirectAttributes.addFlashAttribute("");
        return "redirect:/firmenstamms";
    }

}
