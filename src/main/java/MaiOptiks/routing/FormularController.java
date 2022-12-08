package MaiOptiks.routing;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FormularController {

    private static final String TITLE = "Formular-Editor";

    @RequestMapping("/formular")
    public String home(Model model) {
        model.addAttribute("title", TITLE);
        return "Organisation/Formular/formular";
    }
}