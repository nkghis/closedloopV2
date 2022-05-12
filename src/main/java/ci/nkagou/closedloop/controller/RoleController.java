package ci.nkagou.closedloop.controller;

import ci.nkagou.closedloop.model.AppRole;
import ci.nkagou.closedloop.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller @AllArgsConstructor
public class RoleController {

        private RoleService roleService;

        public static String roleAdminName = "ROLE_ADMIN";
        public static String rolePropertyId = "roleId";

        @RequestMapping(value = "/acces/roles")
        public String indexRole(Model model, HttpServletRequest request){

                List<AppRole> roles = new ArrayList<AppRole>();

                if (request.isUserInRole(roleAdminName)) {
                        roles = roleService.allSortByRoleProperty(rolePropertyId);
                }

                else {
                        roles = roleService.findByRoleNameIsNot(roleAdminName);
                }
                model.addAttribute("listroles",roles);
                model.addAttribute("title", "Rôle - Liste");
                return "role/index";
        }

        @RequestMapping(value = "/admin/roles/new", method = RequestMethod.GET)
        public String newRole(Model model){

                model.addAttribute("monrole",new AppRole());
                model.addAttribute("title", "Rôle - Nouveau");
                return "role/new";

        }

        @RequestMapping(value = "/admin/roles/save", method = RequestMethod.POST)
        public String saveRole(@Valid AppRole role, Errors errors, Model model, RedirectAttributes redirectAttributes){

                if (errors.hasErrors()){
                        System.out.println("error YES");
                        model.addAttribute("monrole", new AppRole());
                        return "role/new";
                }
                roleService.create(role);
                redirectAttributes.addFlashAttribute("messagesucces","Opération éffectée avec succès");
                return "redirect:/acces/roles";
        }

        @RequestMapping(value = "/admin/roles/edit/{id}", method = RequestMethod.GET)
        public String editRole(@PathVariable Long id, Model model){

                AppRole r = roleService.getById(id);

                model.addAttribute("role", r);
                model.addAttribute("title", "Rôle - Edition");
                return "role/edit";
        }

        @RequestMapping(value = "/admin/roles/delete/{id}", method = RequestMethod.GET)
        public String deleteRole(@PathVariable Long id){

                roleService.deleteById(id);
                return "redirect:/acces/roles";
        }





}
