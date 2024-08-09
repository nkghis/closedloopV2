package ci.nkagou.closedloop.controller;

import ci.nkagou.closedloop.dto.carte.CarteDto;
import ci.nkagou.closedloop.dto.compte.CompteIsEnableDto;
import ci.nkagou.closedloop.dto.demandeApprovisionnement.DemandeApprovisionnementDto;
import ci.nkagou.closedloop.dto.navbar.Navbar;
import ci.nkagou.closedloop.dto.operation.OperationDto;
import ci.nkagou.closedloop.dto.operation.OperationDtoOut;
import ci.nkagou.closedloop.dto.operation.RechargementAndTransfertDto;
import ci.nkagou.closedloop.dto.rechargement.RechargementDto;
import ci.nkagou.closedloop.model.*;
import ci.nkagou.closedloop.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class RechargementController {

    @Autowired
    private UserService userService;

    @Autowired
    private NavbarService navbarService;

    @Autowired
    private OperationService operationService;

    @Autowired
    private CarteService carteService;

    @Autowired
    private CompteCarteService compteCarteService;



    @Autowired
    private TypeoperationService typeoperationService;

    @Autowired
    private CompteClientService compteClientService;

    public static String typeOperationRechargement = "RECHARGEMENT";

    @RequestMapping(value =  "/rechargement/rechargements", method = RequestMethod.GET)
    public String index(Model model, Principal principal) {

        //get user connected
        AppUser user = userService.findByUserName(principal.getName());

        List<OperationDtoOut> listRechargement = operationService.listRechargementToDtoClient(user.getCompte().getCompteId());

        //Display Balance & validation
        Navbar navbar = navbarService.displayNavbar(user);

        model.addAttribute("navbar", navbar);
        model.addAttribute("listRechargement", listRechargement);
        model.addAttribute("title", "Transactions Rechargement - Liste");
        return "rechargement/index";
    }

    @RequestMapping(value = "/rechargement/rechargements/simple/new", method = RequestMethod.GET)
    public String newRechargementSimple(Model model, Principal principal){

        AppUser user = userService.findByUserName(principal.getName());

        //Display Balance & validation
        Navbar navbar = navbarService.displayNavbar(user);


        //List<CarteDto> cartes = carteService.listAllCartesToDto();

        //Liste des cartes ayant un compte
        List<CarteDto> cartes = compteCarteService.listCarteSerialHaveAccount();
        model.addAttribute("rechargementdto",new RechargementDto());
        /*  model.addAttribute("marchands",marchands);*/
        model.addAttribute("title", "Rechargement Simple - Nouveau");
        model.addAttribute("cartes", cartes);
        model.addAttribute("navbar", navbar);
        return "rechargement/simple";
    }

    @RequestMapping(value = "/rechargement/rechargements/multiple/new", method = RequestMethod.GET)
    public String newRechargementMultiple(Model model, Principal principal){

        AppUser user = userService.findByUserName(principal.getName());

        CompteClient compteClient = compteClientService.getById(user.getCompte().getCompteId());

        //Display Balance & validation
        Navbar navbar = navbarService.displayNavbar(user);

        List<CarteDto> listCarte = compteCarteService.listCarteSerialNumberByClient(compteClient.getClient());


        //model.addAttribute("rechargementdto",new RechargementDto());
        /*  model.addAttribute("marchands",marchands);*/
        model.addAttribute("title", "Rechargement Multiple - Nouveau");
        model.addAttribute("navbar", navbar);
        model.addAttribute("listCarte", listCarte);
        return "rechargement/multiple";
    }

    @RequestMapping(value="/rechargement/rechargements/simple/save" , method = RequestMethod.POST)
    public String saveRechargementSimple(@Valid RechargementDto rechargementDto, Principal principal ,Model model, HttpServletRequest request, RedirectAttributes redirectAttributes, Errors errors) {

        if (errors.hasErrors()){
            System.out.println("error YES");
            model.addAttribute("rechargementdto", new RechargementDto());
            //model.addAttribute("errors", errors);
            return "redirect:/rechargement/rechargements/simple/new";
        }
        //Get user
        AppUser user = userService.findByUserName(principal.getName());

        //get compte cible
        CompteCarte compteCible = compteCarteService.getCompteCarteBySerialNumber(rechargementDto.getNumeroSerie());


        //Set source and cible ID
        Long sourceAccountId = user.getCompte().getCompteId();
        Long targetAccountId = compteCible.getCompteId();

        //Get TypeOperation
        Typeoperation typeoperation = typeoperationService.findByTypeoperationNom(typeOperationRechargement);

        //make rechargement
        RechargementAndTransfertDto dto = operationService.makeRechargementOrTransfert(sourceAccountId, targetAccountId, rechargementDto.getAmount(), typeoperation, user);

        //Check if rechargement success
        if (dto.getTypeMessage().equals("succes")){
            redirectAttributes.addFlashAttribute("messagesucces",dto.getMessage());
        }else{
            redirectAttributes.addFlashAttribute("messagedanger",dto.getMessage());
        }

        return "redirect:/rechargement/rechargements";
    }

    @RequestMapping(value="/rechargement/rechargements/multiple/save" , method = RequestMethod.POST)
    public String saveRechargementMultiple(Principal principal,Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {

        //Get checkbox value
        String[] cartes = request.getParameterValues("numeros");

        //Get user
        AppUser user = userService.findByUserName(principal.getName());


        double amount = Double.parseDouble(request.getParameter("amount"));

        //Get TypeOperation
        Typeoperation typeoperation = typeoperationService.findByTypeoperationNom(typeOperationRechargement);

        Long sourceAccountId = user.getCompte().getCompteId();

        List<RechargementAndTransfertDto> dtos = new ArrayList<>();

        for (String s : cartes){

            CompteCarte compteCarte = compteCarteService.getCompteCarteBySerialNumber(s);
            Long targetAccountId = compteCarte.getCompteId();

            //make rechargement
            RechargementAndTransfertDto dto = operationService.makeRechargementOrTransfert(sourceAccountId, targetAccountId,  amount, typeoperation, user);
            dtos.add(dto);

        }

        List<String> succes = new ArrayList<>();
        List<String> error = new ArrayList<>();
        //String[] error =null;


        //Display succes or error message
        for (RechargementAndTransfertDto rechargementAndTransfertDto : dtos){

            //Check if rechargement success


            if (rechargementAndTransfertDto.getTypeMessage().equals("succes")){

                String messageSucces = rechargementAndTransfertDto.getMessage();
                succes.add(messageSucces);
               // redirectAttributes.addFlashAttribute("messagesucces",rechargementAndTransfertDto.getMessage());
            }else{
                String messageError = rechargementAndTransfertDto.getMessage();
                error.add(messageError);
                //redirectAttributes.addFlashAttribute("messagedanger",rechargementAndTransfertDto.getMessage());
            }
        }


        //Display notifaction message
        if (succes.isEmpty()== false){

            String s = String.join(", ", succes);
            redirectAttributes.addFlashAttribute("messagesucces",s);
        }
        if (error.isEmpty() == false){
            String e = String.join(", ", error);
            redirectAttributes.addFlashAttribute("messagedanger",e);
        }



        return "redirect:/rechargement/rechargements";
    }
}
