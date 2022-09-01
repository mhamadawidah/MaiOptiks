package MaiOptiks.rest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private static final String TITLE = "MyOptiks";

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", TITLE);
        return "home";
    }
}