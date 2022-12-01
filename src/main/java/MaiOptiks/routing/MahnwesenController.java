package MaiOptiks.routing;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MahnwesenController {

    private static final String TITLE = "Mahnwesen";

    @RequestMapping("/mahnwesen")
    public String home(Model model) {
        model.addAttribute("title", TITLE);
        return "Organisation/Mahnwesen/mahnwesen";
    }
}