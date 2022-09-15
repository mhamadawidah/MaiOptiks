package MaiOptiks.routing;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AbrechnungController {

    private static final String TITLE = "Abrechnung";

    @RequestMapping("/abrechnung")
    public String home(Model model) {
        model.addAttribute("title", TITLE);
        return "Abrechnung/abrechnung";
    }
}