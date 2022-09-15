package MaiOptiks.controller;

import MaiOptiks.model.MailDTO;
import MaiOptiks.service.MailService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


@Controller
@RequestMapping("/mails")
public class MailController {

    private final MailService mailService;

    public MailController(final MailService mailService) {
        this.mailService = mailService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("mails", mailService.findAll());
        return "mail/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("mail") final MailDTO mailDTO) {
        return "mail/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("mail") @Valid final MailDTO mailDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (!bindingResult.hasFieldErrors("email") && mailDTO.getEmail() == null) {
            bindingResult.rejectValue("email", "NotNull");
        }
        if (!bindingResult.hasFieldErrors("email") && mailService.emailExists(mailDTO.getEmail())) {
            bindingResult.rejectValue("email", "Exists.mail.email");
        }
        if (bindingResult.hasErrors()) {
            return "mail/add";
        }
        mailService.create(mailDTO);
        redirectAttributes.addFlashAttribute("");
        return "redirect:/mails";
    }

    @GetMapping("/edit/{email}")
    public String edit(@PathVariable final String email, final Model model) {
        model.addAttribute("mail", mailService.get(email));
        return "mail/edit";
    }

    @PostMapping("/edit/{email}")
    public String edit(@PathVariable final String email,
            @ModelAttribute("mail") @Valid final MailDTO mailDTO, final BindingResult bindingResult,
            final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "mail/edit";
        }
        mailService.update(email, mailDTO);
        redirectAttributes.addFlashAttribute("");
        return "redirect:/mails";
    }

    @PostMapping("/delete/{email}")
    public String delete(@PathVariable final String email,
            final RedirectAttributes redirectAttributes) {
        mailService.delete(email);
        redirectAttributes.addFlashAttribute("");
        return "redirect:/mails";
    }

}
