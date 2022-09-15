package MaiOptiks.routing;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    private static final String TITLE = "MaiOptics";

    @RequestMapping("/")
    public String home(Model model) {
        model.addAttribute("title", TITLE);
        return "home";
    }
}