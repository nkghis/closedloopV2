package ci.nkagou.closedloop.controller;

import ci.nkagou.closedloop.model.AppRole;
import ci.nkagou.closedloop.model.AppUser;
import ci.nkagou.closedloop.model.UserRole;
import ci.nkagou.closedloop.service.RoleService;
import ci.nkagou.closedloop.service.UserRoleService;
import ci.nkagou.closedloop.service.UserService;
import ci.nkagou.closedloop.utils.EncrytedPasswordUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;

@Controller
@AllArgsConstructor/*
@Transactional*/
public class UserController {

    private UserService userService;
    private RoleService roleService;
    private UserRoleService userRoleService;

    @RequestMapping(value = "/acces/users")
    public String indexUser(Model model, HttpServletRequest request){

        List<AppUser> users = new ArrayList<AppUser>();

        if (request.isUserInRole("ROLE_ADMIN")) {
            users = userService.allSortByRoleProperty("userId");
            users = userService.getUserListWithRoleInString(users);

        }else {

            String r = "ROLE_ADMIN";
            AppRole role = roleService.findByRoleName(r);
            List<UserRole> listUsers = userRoleService.findByAppRoleIsNot(role);
            List<AppUser> allusers = new ArrayList<AppUser>();

            for (UserRole userrole:listUsers) {

                AppUser myuser = userrole.getAppUser();
                allusers.add(myuser);
            }

            users = allusers;
            users = userService.getUserListWithRoleInString(users);
        }

        model.addAttribute("listusers",users);
        model.addAttribute("title", "Utilisateur - Liste");
        return "user/index";
    }

    @RequestMapping(value = "/acces/users/new", method = RequestMethod.GET)
    public String newUser(Model model, HttpServletRequest request){

        List<AppRole> roles = new ArrayList<AppRole>();

        String r = "ROLE_ADMIN";

        if (request.isUserInRole(r)) {
            roles = roleService.all();
        }
        else {

            roles = roleService.findByRoleNameIsNot(r);
        }

        model.addAttribute("monuser",new AppUser());
        model.addAttribute("title", "Utilisateur - Nouveau");
        model.addAttribute("roles", roles);
        return "user/new";

    }

    @RequestMapping(value = "/acces/users/save", method = RequestMethod.POST)
    public String saveUser(@Valid AppUser user, Errors errors, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request){

        if (errors.hasErrors()){
            System.out.println("error YES");
            model.addAttribute("monuser", new AppUser());
            //model.addAttribute("errors", errors);
            return "user/new";
        }
        //Encrypt Password
        String password = EncrytedPasswordUtils.encrytePassword(user.getEncrytedPassword());
        user.setEncrytedPassword(password);;
        user.setEnabled(true);

        // Save User
        AppUser u = userService.create(user);

        //Get role form Select multiple value
        String[] selected = request.getParameterValues("role");

        //Add role
        for (String s : selected){

            Long id = Long.parseLong(s);
            AppRole role = roleService.getById(id);
            UserRole userRole = new UserRole();
            userRole.setAppUser(u);
            userRole.setAppRole(role);

            userRoleService.create(userRole);


        }
        redirectAttributes.addFlashAttribute("messagesucces","Opération éffectée avec succès");
        return "redirect:/acces/users";
    }


}
