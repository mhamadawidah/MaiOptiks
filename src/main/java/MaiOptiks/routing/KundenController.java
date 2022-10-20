package MaiOptiks.routing;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class KundenController {

    private static final String TITLE = "Kunde";

    @RequestMapping("/kunde")
    public String home(Model model) {
        model.addAttribute("title", TITLE);
        return "Kunden/kunde";
    }
}
