package ci.nkagou.closedloop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
public class CarteController {

    @RequestMapping(value =  "/carte/cartes", method = RequestMethod.GET)
    public String index(Model model, Principal principal) {

        return "carte/index";
    }
}
