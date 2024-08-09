package ci.nkagou.closedloop.controller;

import ci.nkagou.closedloop.dto.compteCarte.CompteCarteDto;
import ci.nkagou.closedloop.dto.compteCarte.CompteCarteDtoOut;
import ci.nkagou.closedloop.dto.compteClient.CompteClientDto;
import ci.nkagou.closedloop.dto.compteClient.CompteClientDtoOut;
import ci.nkagou.closedloop.dto.compteMarchand.CompteMarchandDto;
import ci.nkagou.closedloop.dto.compteMarchand.CompteMarchandDtoOut;
import ci.nkagou.closedloop.dto.navbar.Navbar;
import ci.nkagou.closedloop.model.*;
import ci.nkagou.closedloop.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
public class CompteController {

    @Autowired
    private UserService userService;

    @Autowired
    private CompteService compteService;

    @Autowired
    private CompteClientService compteClientService;

    @Autowired
    private CompteCarteService compteCarteService;

    @Autowired
    private CompteMarchandService compteMarchandService;

    @Autowired
    private CarteService carteService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private MarchandService marchandService;

    @Autowired
    private NavbarService navbarService;


    @RequestMapping(value =  "/compte/comptes", method = RequestMethod.GET)
    public String index(Model model, Principal principal) {

        AppUser user = userService.findByUserName(principal.getName());

        //Display Balance & validation
        Navbar navbar = navbarService.displayNavbar(user);

        String typeCompteConnected = user.getCompte().getTypeCompte();

        String returnString = null;

        if (typeCompteConnected.equals("ccl")){

            //Client
            CompteClient compteClient = compteClientService.getById(user.getCompte().getCompteId());
            Client client = compteClient.getClient();
            List<CompteCarteDtoOut> compteCarteDtoOuts =  compteCarteService.allComptesCarteByClient(client);
            model.addAttribute("listCompte", compteCarteDtoOuts);
            model.addAttribute("compte", "carte");

        }else if(typeCompteConnected.equals("cma")){

            CompteMarchand compteMarchand = compteMarchandService.getById(user.getCompte().getCompteId());
            Marchand marchand   = compteMarchand.getMarchand();

            List<CompteClientDtoOut> compteClientDtoOuts = compteClientService.allComptesClientByMarchand(marchand);
            model.addAttribute("listCompte", compteClientDtoOuts);
            model.addAttribute("compte", "client");


        }


        else{

            List<CompteMarchandDtoOut> compteMarchandDtoOuts = compteMarchandService.listCompteMarchandsToDto();
            model.addAttribute("listCompte", compteMarchandDtoOuts);
            model.addAttribute("compte", "marchand");
        }
        model.addAttribute("navbar", navbar);

        return "compte/index";
    }

    @RequestMapping(value = "/compte/comptes/cartes", method = RequestMethod.GET)
    public String newCompteCarte(Model model, Principal principal){

        AppUser user = userService.findByUserName(principal.getName());
        model.addAttribute("comptecartedto",new CompteCarteDto());
        List<Carte> cartes = carteService.getListStatusDisable();
        //Display Balance & validation
        Navbar navbar = navbarService.displayNavbar(user);
        model.addAttribute("title", "Compte Carte - Nouveau");
        model.addAttribute("cartes", cartes);
        model.addAttribute("navbar", navbar);
        return "compte/comptecarte";
    }

    @RequestMapping(value = "/compte/comptes/clients", method = RequestMethod.GET)
    public String newCompteClient(Model model, Principal principal){
        AppUser user = userService.findByUserName(principal.getName());
        Navbar navbar = navbarService.displayNavbar(user);
        Marchand marchand = marchandService.getMarchandByUser(user);
        List<Client> clients = clientService.listClientsByMarchand(marchand);
        model.addAttribute("comptecliendto", new CompteClientDto());
        model.addAttribute("title", "Compte Client - Nouveau");
        model.addAttribute("clients", clients);
        model.addAttribute("navbar", navbar);
        return "compte/compteclient";
    }

    @RequestMapping(value = "/compte/comptes/marchands", method = RequestMethod.GET)
    public String newCompteMarchand(Model model, Principal principal){

        AppUser user = userService.findByUserName(principal.getName());
        Navbar navbar = navbarService.displayNavbar(user);
        List<Marchand> marchands = marchandService.all();
        model.addAttribute("comptemarchanddto",new CompteMarchandDto());
        model.addAttribute("title", "Compte Carte - Nouveau");
        model.addAttribute("marchands", marchands);
        model.addAttribute("navbar", navbar);
        return "compte/comptemarchand";
    }



    @RequestMapping(value = "/compte/comptes/cartes/save", method = RequestMethod.POST)
    public String saveCompteCarte(@Valid CompteCarteDto compteCarteDto, Errors errors, Model model, RedirectAttributes redirectAttributes, Principal principal){
        if (errors.hasErrors()){
            System.out.println("error YES");
            model.addAttribute("dto", new CompteCarteDto());
            //model.addAttribute("errors", errors);
            return "compte/comptecarte";
        }


        Compte compte = compteService.findByNumeroCompte(compteCarteDto.getNumeroCompte());

        //CompteCarte compteCarte = compteCarteService.getByNumeroCompte(compteCarteDto.getNumeroCompte());



        //Check if compte exist
        if (compte == null){

            //Boolean isEnable = compteService.checkIsEnable(compteCarteDto.getNumeroCompte());

            //Check if compte enable
           /* if (isEnable == true){*/

                //Get user connected
                AppUser user = userService.findByUserName(principal.getName());

                //Get Client
                Client client = compteCarteService.getClientByCompte(user.getCompte());

                //Set Client
                compteCarteDto.setClientId(client.getClientId());

                //Create Carte Compte
                CompteCarteDtoOut out = compteCarteService.createCompteCarte(compteCarteDto);

                //get carte by serial number
                Carte carte = carteService.findBySerialNumber(compteCarteDto.getSerialNumber());

                //Update Carte after create Compte
                carteService.updateCarteAfterCompteCreated(carte);

                redirectAttributes.addFlashAttribute("messagesucces","Opération de création éffectuée avec succès");

            }/*else {

                redirectAttributes.addFlashAttribute("messagedanger","Echec, Le compte carte N° [" + compteCarteDto.getNumeroCompte() + " ] est desactivé");
            }*/


        /*}*/ else {
            redirectAttributes.addFlashAttribute("messagedanger","Echec, Le compte carte N° [" + compteCarteDto.getNumeroCompte() + " ] existe déjà dans la base de données");
        }


/*
        if (compteCarte != null){

            Boolean isEnable = compteService.checkIsEnable(compteCarteDto.getNumeroCompte());

            if (isEnable == true){

                String carteStatut = carteService.checkCarteBySerialNumber(compteCarteDto.getSerialNumber());

                switch (carteStatut){

                    case "enable" :
                        AppUser user = userService.findByUserName(principal.getName());

                        Client client = compteCarteService.getClientByCompte(user.getCompte());

                        compteCarteDto.setClientId(client.getClientId());

                        CompteCarteDtoOut out = compteCarteService.createCompteCarte(compteCarteDto);

                        redirectAttributes.addFlashAttribute("messagesucces","Opération de création éffectuée avec succès");
                        break;

                    case "disable" :
                        redirectAttributes.addFlashAttribute("messagedanger","Echec, La carte ayant le numero de serie  [" + compteCarteDto.getSerialNumber() + " ] est deja associé à un compte");
                        break;

                    case "notfound" :
                        redirectAttributes.addFlashAttribute("messagedanger","Echec, La carte ayant le numero de serie  [" + compteCarteDto.getSerialNumber() + " ] est introuvable");
                        break;
                }

            }else {

                redirectAttributes.addFlashAttribute("messagedanger","Echec, Le compte carte N° [" + compteCarteDto.getNumeroCompte() + " ] est desactivé");
            }


        } else {
            redirectAttributes.addFlashAttribute("messagedanger","Echec, Le compte carte N° [" + compteCarteDto.getNumeroCompte() + " ] existe déjà dans la base de données");
        }
*/


        return "redirect:/compte/comptes/";
    }

    @RequestMapping(value = "/compte/comptes/clients/save", method = RequestMethod.POST)
    public String saveCompteClient(@Valid CompteClientDto compteClientDto, Errors errors, Model model, RedirectAttributes redirectAttributes, Principal principal, HttpServletRequest request ){
        if (errors.hasErrors()){
            System.out.println("error YES");
            model.addAttribute("dto", new CompteClientDto());
            //model.addAttribute("errors", errors);
            return "compte/compteclient";
        }
        String id = request.getParameter("client");
        Long clientId = Long.parseLong(id);
        compteClientDto.setClientId(clientId);
        Compte compte = compteService.findByNumeroCompte(compteClientDto.getNumeroCompte());

        //Check if compte exist
        if (compte == null){

            //AppUser user = userService.findByUserName(principal.getName());

            CompteClientDtoOut out = compteClientService.createCompteClient(compteClientDto);
            redirectAttributes.addFlashAttribute("messagesucces","Opération de création éffectuée avec succès");
        }else {
            redirectAttributes.addFlashAttribute("messagedanger","Echec, Le compte carte N° [" + compteClientDto.getNumeroCompte() + " ] existe déjà dans la base de données");
        }
        return "redirect:/compte/comptes/";
    }


    @RequestMapping(value = "/compte/comptes/marchands/save", method = RequestMethod.POST)
    public String saveCompteMarchand(@Valid CompteMarchandDto compteMarchandDto, Errors errors, Model model, RedirectAttributes redirectAttributes, Principal principal, HttpServletRequest request){
        if (errors.hasErrors()){
            System.out.println("error YES");
            model.addAttribute("dto", new CompteMarchandDto());
            //model.addAttribute("errors", errors);
            return "compte/comptemarchand";
        }

        Compte compte = compteService.findByNumeroCompte(compteMarchandDto.getNumeroCompte());
        String id = request.getParameter("marchand");
        Long marchandId = Long.parseLong(id);
        compteMarchandDto.setMarchandId(marchandId);

        //Check if compte exist
        if (compte == null){

            CompteMarchandDtoOut out = compteMarchandService.createCompteMarchand(compteMarchandDto);

            redirectAttributes.addFlashAttribute("messagesucces","Opération de création éffectuée avec succès");

        }else {
            redirectAttributes.addFlashAttribute("messagedanger","Echec, Le compte carte N° [" + compteMarchandDto.getNumeroCompte() + " ] existe déjà dans la base de données");
        }

        return "redirect:/compte/comptes/";
    }


    @RequestMapping(value = "/compte/comptes/statut/{id}", method = RequestMethod.GET)
    public String changeStatutAccount(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes){

        Compte compte = compteService.getById(id);
        compteService.changeStatut(compte);

        redirectAttributes.addFlashAttribute("messagesucces","le statut du compte a été changé avec succès");

        return "redirect:/compte/comptes/";
    }
}
