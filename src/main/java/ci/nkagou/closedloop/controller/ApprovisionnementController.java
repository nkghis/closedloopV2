package ci.nkagou.closedloop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
public class ApprovisionnementController {

    @RequestMapping(value =  "/rechargement/appro", method = RequestMethod.GET)
    public String indexDemandeAppro(Model model, Principal principal) {

        return "approvisionnement/indexDemande";
    }

    @RequestMapping(value =  "/approvisionnement/appro", method = RequestMethod.GET)
    public String indexValidationAppro(Model model, Principal principal) {

        return "approvisionnement/indexValidation";
    }


}
