package MaiOptiks.routing;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuftragsdatenverwaltungController {

    private static final String TITLE = "Auftragsdatenverwaltung";

    @RequestMapping("/auftragsdatenverwaltung")
    public String home(Model model, Integer selection, Integer selection2 /*Integer is nullable. int is not*/) {
        model.addAttribute("selection", selection == null ? 1 : selection);
        model.addAttribute("selection2", selection2 == null ? 1 : selection2);
        model.addAttribute("title", TITLE);
        return "Auftragsdatenverwaltung/index";
    }
}