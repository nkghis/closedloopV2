package ci.nkagou.closedloop.controller;

import ci.nkagou.closedloop.dto.carte.CartePersonneDto;
import ci.nkagou.closedloop.dto.carte.CarteVehiculeDto;
import ci.nkagou.closedloop.dto.carte.CarteDtoOut;
import ci.nkagou.closedloop.dto.navbar.Navbar;
import ci.nkagou.closedloop.model.AppUser;
import ci.nkagou.closedloop.model.Carte;
import ci.nkagou.closedloop.service.CarteService;
import ci.nkagou.closedloop.service.NavbarService;
import ci.nkagou.closedloop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
public class CarteController {

    @Autowired
    private CarteService carteService;

    @Autowired
    private NavbarService navbarService;

    @Autowired
    private UserService userService;

    @RequestMapping(value =  "/carte/cartes", method = RequestMethod.GET)
    public String index(Model model, Principal principal) {

        //get user connected
        AppUser user = userService.findByUserName(principal.getName());
        //Display Balance & validation
        Navbar navbar = navbarService.displayNavbar(user);

        List<CarteDtoOut> cartes = carteService.listCartesToDto();

        model.addAttribute("listCartes", cartes);
        model.addAttribute("navbar", navbar);
        return "carte/index";
    }

    @RequestMapping(value = "/carte/cartes/newPersonne", method = RequestMethod.GET)
    public String newCartePersonne(Model model, Principal principal){

        //get user connected
        AppUser user = userService.findByUserName(principal.getName());
        //Display Balance & validation
        Navbar navbar = navbarService.displayNavbar(user);

        //List<Marchand> marchands = marchandService.all();
        model.addAttribute("cartepersonnedto",new CartePersonneDto());
        /*  model.addAttribute("marchands",marchands);*/
        model.addAttribute("title", "Carte Personne - Nouveau");

        model.addAttribute("navbar", navbar);

        return "carte/newCartePersonne";
    }

    @RequestMapping(value = "/carte/cartes/newVehicule", method = RequestMethod.GET)
    public String newCarteVehicule(Model model, Principal principal){

        //get user connected
        AppUser user = userService.findByUserName(principal.getName());
        //Display Balance & validation
        Navbar navbar = navbarService.displayNavbar(user);

        //List<Marchand> marchands = marchandService.all();
        model.addAttribute("cartevehiculedto",new CarteVehiculeDto());
        /*  model.addAttribute("marchands",marchands);*/
        model.addAttribute("title", "Carte Vehicule - Nouveau");
        model.addAttribute("navbar", navbar);
        return "carte/newCarteVehicule";
    }

    @RequestMapping(value = "/carte/cartes/edit/{id}", method = RequestMethod.GET)
    public String editClient(@PathVariable Long id, Model model, Principal principal){
        Carte c = carteService.getById(id);

        //get user connected
        AppUser user = userService.findByUserName(principal.getName());
        //Display Balance & validation
        Navbar navbar = navbarService.displayNavbar(user);

        //model.addAttribute("carte", c);
        //model.addAttribute("title", "Carte - Edition");
        model.addAttribute("navbar", navbar);
        return "carte/edit";
    }

    @RequestMapping(value = "/carte/cartes/personne/save", method = RequestMethod.POST)
    public String saveCartePersonne(@Valid CartePersonneDto cartePersonneDto, Errors errors, Model model, RedirectAttributes redirectAttributes, Principal principal) {
        if (errors.hasErrors()) {
            System.out.println("error YES");
            model.addAttribute("cartepersonnedto", new CartePersonneDto());
            //model.addAttribute("errors", errors);
            return "carte/newCartePersonne";
        }

        CarteDtoOut dto = carteService.createCartePersonne(cartePersonneDto);

        redirectAttributes.addFlashAttribute("messagesucces","Opération de création éffectuée avec succès");

        return "redirect:/carte/cartes/";

    }

    @RequestMapping(value = "/carte/cartes/vehicule/save", method = RequestMethod.POST)
    public String saveCarteVehicule(@Valid CarteVehiculeDto carteVehiculeDto, Errors errors, Model model, RedirectAttributes redirectAttributes, Principal principal) {
        if (errors.hasErrors()) {
            System.out.println("error YES");
            model.addAttribute("cartevehiculedto", new CarteVehiculeDto());
            //model.addAttribute("errors", errors);
            return "carte/newCarteVehicule";
        }

        CarteDtoOut dto = carteService.createVehiculePersonne(carteVehiculeDto);
        redirectAttributes.addFlashAttribute("messagesucces","Opération de création éffectuée avec succès");
        return "redirect:/carte/cartes/";

    }

}
