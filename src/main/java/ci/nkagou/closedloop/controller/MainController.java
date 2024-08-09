package ci.nkagou.closedloop.controller;

import ci.nkagou.closedloop.dto.navbar.Navbar;
import ci.nkagou.closedloop.model.AppUser;
import ci.nkagou.closedloop.model.Typeoperation;
import ci.nkagou.closedloop.service.ApprovisionnementService;
import ci.nkagou.closedloop.service.NavbarService;
import ci.nkagou.closedloop.service.TypeoperationService;
import ci.nkagou.closedloop.service.UserService;
import ci.nkagou.closedloop.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private NavbarService navbarService;
    @Autowired
    private TypeoperationService typeoperationService;

    @RequestMapping(value = { "/", "/login" }, method = RequestMethod.GET)
    public String welcomePage(Model model, Principal principal) {

        //Check if user is connected
        if (null == principal){
            model.addAttribute("title", "Authentification");
            return "main/loginPage";
        }


        return "redirect:/dashboard";

    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminPage(Model model, Principal principal) {

        User loginedUser = (User) ((Authentication) principal).getPrincipal();
        String userInfo = WebUtils.toString(loginedUser);
        model.addAttribute("userInfo", userInfo);
        return "main/adminPage";
    }

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String userInfo(Model model, Principal principal) {
        //Check if user is connected
        if (null == principal){
            model.addAttribute("title", "Authentification");
            return "main/loginPage";
        }

        String userName = principal.getName();
        System.out.println("User Name: " + userName);
        User loginedUser = (User) ((Authentication) principal).getPrincipal();
        AppUser user = userService.findByUserName(principal.getName());

/*        double balance = user.getCompte().getBalance();
        int sizeApproForValidation = approvisionnementService.countDemandeforValidation(user, user.getCompte().getTypeCompte());

        Navbar navbar = new Navbar();
        navbar.setBalance(balance);
        navbar.setSizeApproForValidation(sizeApproForValidation);*/

        Navbar navbar = navbarService.displayNavbar(user);


        List<AppUser> users = userService.all();
        List<Typeoperation> typeoperations = typeoperationService.all();


        String userInfo = WebUtils.toString(loginedUser);
        model.addAttribute("userInfo", userInfo);
        model.addAttribute("users", users);
        model.addAttribute("typeoperations", typeoperations);
//        model.addAttribute("balance", balance);
//        model.addAttribute("sizeApproForValidation", sizeApproForValidation);
        model.addAttribute("navbar", navbar);
        model.addAttribute("title", "Tableau de bord");


        return "main/dashboard";
    }


    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String accessDenied(Model model, Principal principal) {

        if (principal != null) {
            User loginedUser = (User) ((Authentication) principal).getPrincipal();
            String userInfo = WebUtils.toString(loginedUser);
            model.addAttribute("userInfo", userInfo);
            String message = "Hi " + principal.getName() //
                    + "<br> Vous n'avez pas la permission d'accéder à cette page!";
            model.addAttribute("message", message);

        }

        return "main/403Page";
    }

    @RequestMapping(value = "/admin/test", method = RequestMethod.GET)
    public String loginPage(Model model) {
        model.addAttribute("title", "admin page");
        return "main/adminPage";
    }

}
