package MaiOptiks.routing;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AufträgeController {

    private static final String TITLE = "Auftrag";

    @RequestMapping("/auftrag")
    public String home(Model model) {
        model.addAttribute("title", TITLE);
        return "Kunden/auftrag";
    }
}
