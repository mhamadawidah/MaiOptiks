package MaiOptiks.routing;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdressenverwaltungController {

    private static final String TITLE = "Adressenverwaltung";

    @RequestMapping("/adressenverwaltung")
    public String home(Model model) {
        model.addAttribute("title", TITLE);
        return "Organisation/Adressenverwaltung/adressenverwaltung";
    }
}