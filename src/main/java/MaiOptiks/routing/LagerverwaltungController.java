package MaiOptiks.routing;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LagerverwaltungController {

    private static final String TITLE = "Lagerverwaltung";

    @RequestMapping("/lagerverwaltung")
    public String home(Model model) {
        model.addAttribute("title", TITLE);
        return "lagerverwaltung/lagerverwaltung";
    }
}