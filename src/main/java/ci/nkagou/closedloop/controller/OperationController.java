package ci.nkagou.closedloop.controller;

import ci.nkagou.closedloop.dto.navbar.Navbar;
import ci.nkagou.closedloop.dto.operation.OperationDtoOut;
import ci.nkagou.closedloop.model.AppUser;
import ci.nkagou.closedloop.model.Operation;
import ci.nkagou.closedloop.service.NavbarService;
import ci.nkagou.closedloop.service.OperationService;
import ci.nkagou.closedloop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class OperationController {

    @Autowired
    private UserService userService;

    @Autowired
    private NavbarService navbarService;

    @Autowired
    private OperationService operationService;

    @RequestMapping(value =  "/transaction/transactions", method = RequestMethod.GET)
    public String index(Model model, Principal principal) {

        //get user connected
        AppUser user = userService.findByUserName(principal.getName());

        String typeCompte = user.getCompte().getTypeCompte();
        List<OperationDtoOut> out = new ArrayList<>();

        Long compteId = null;

        if (typeCompte.equals("cma")){

            compteId = user.getCompte().getCompteId();
            //Liste des operations du marchand connecté et des clients du marchand.
            out = operationService.listOperationToDtoMarchandAndMarchandClient(compteId);

        }else if (typeCompte.equals("ccl")){
            compteId = user.getCompte().getCompteId();

            //Liste des operations du client connecté+
           // out = operationService.listOperationToDtoByCompte(compteId, compteId);

            out = operationService.listOperationToDtoCompteClient(compteId);
        }else {
            List<Operation> operations = operationService.all();
            out = operationService.listOperationToDto(operations);

        }

        //Display Balance & validation
        Navbar navbar = navbarService.displayNavbar(user);

        model.addAttribute("navbar", navbar);
        model.addAttribute("listoperation", out);
        model.addAttribute("title", "Transactions - Liste");
        return "operation/index";
    }
}
