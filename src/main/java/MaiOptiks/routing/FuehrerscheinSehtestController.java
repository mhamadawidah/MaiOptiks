package MaiOptiks.routing;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FuehrerscheinSehtestController {

    private static final String TITLE = "Führerschein Sehtest";

    @RequestMapping("/führerscheinsehtest")
    public String home(Model model) {
        model.addAttribute("title", TITLE);
        return "Sehtest/fuehrerschein-sehtest";
    }
}
