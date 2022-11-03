package MaiOptiks.routing;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MailingController {

    private static final String TITLE = "Mailing";

    @RequestMapping("/mailing")
    public String home(Model model) {
        model.addAttribute("title", TITLE);
        return "Organisation/Mailing/mailing";
    }
}
