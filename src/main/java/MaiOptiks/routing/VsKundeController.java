package MaiOptiks.routing;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class VsKundeController {

    private static final String TITLE = "VS-Kunde";

    @RequestMapping("/vs-kunde")
    public String home(Model model) {
        model.addAttribute("title", TITLE);
        return "kunden/vs-kunde";
    }
}
