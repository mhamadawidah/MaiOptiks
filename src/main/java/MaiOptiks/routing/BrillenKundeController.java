package MaiOptiks.routing;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BrillenKundeController {

    private static final String TITLE = "Brillen-Kunde";

    @RequestMapping("/brillen-kunde")
    public String home(Model model) {
        model.addAttribute("title", TITLE);
        return "kunden/brillen-kunde";
    }
}
