package MaiOptiks.routing;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OrganisationController {

    private static final String TITLE = "Organisation";

    @RequestMapping("/organisation")
    public String home(Model model) {
        model.addAttribute("title", TITLE);
        return "Organisation/organisation";
    }
}