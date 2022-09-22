package MaiOptiks.routing;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ClKundeController {

    private static final String TITLE = "CL-Kunde";

    @RequestMapping("/cl-kunde")
    public String home(Model model) {
        model.addAttribute("title", TITLE);
        return "Kunden/cl-kunde";
    }
}
