package MaiOptiks.routing;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MitarbeiterCreationController {

    private static final String TITLE = "Mitarbeiter";

    @RequestMapping("/mitarbeiter")
    public String index(Model model) {
        model.addAttribute("title", TITLE);
        return "mitarbeiter/index";
    }
}
