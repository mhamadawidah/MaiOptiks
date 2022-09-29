package MaiOptiks.routing;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StatistikContoller {

    private static final String TITLE = "Statistik";

    @RequestMapping("/statistik")
    public String home(Model model) {
        model.addAttribute("title", TITLE);
        return "Statistik/statistik";
    }
}
