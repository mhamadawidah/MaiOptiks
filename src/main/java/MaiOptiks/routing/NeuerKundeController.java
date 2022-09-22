package MaiOptiks.routing;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class NeuerKundeController {

    private static final String TITLE = "Neuer Kunde";

    @RequestMapping("/neuer-kunde")
    public String home(Model model) {
        model.addAttribute("title", TITLE);
        return "Kunden/neuer-kunde";
    }
}