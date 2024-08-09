package ci.nkagou.closedloop.controller;

import ci.nkagou.closedloop.dto.carte.CarteDto;
import ci.nkagou.closedloop.dto.navbar.Navbar;
import ci.nkagou.closedloop.dto.operation.OperationDtoOut;
import ci.nkagou.closedloop.dto.operation.RechargementAndTransfertDto;
import ci.nkagou.closedloop.dto.rechargement.RechargementDto;
import ci.nkagou.closedloop.dto.transfert.TransfertClientDto;
import ci.nkagou.closedloop.model.AppUser;
import ci.nkagou.closedloop.model.CompteCarte;
import ci.nkagou.closedloop.model.CompteClient;
import ci.nkagou.closedloop.model.Typeoperation;
import ci.nkagou.closedloop.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
public class TransfertController {

    @Autowired
    private UserService userService;

    @Autowired
    private NavbarService navbarService;

    @Autowired
    private OperationService operationService;

    @Autowired
    private CompteCarteService compteCarteService;

    @Autowired
    private CompteClientService compteClientService;

    @Autowired
    private TypeoperationService typeoperationService;

    public static String typeOperationTransfert = "TRANSFERT";

    @RequestMapping(value =  "/transfert/transferts", method = RequestMethod.GET)
    public String index(Model model, Principal principal) {

        //get user connected
        AppUser user = userService.findByUserName(principal.getName());

        //Display Balance & validation
        Navbar navbar = navbarService.displayNavbar(user);

        //List des transfert par utilisateur connécte
        //List<OperationDtoOut> listTransfert = operationService.listTransfertToDtoClient(user.getCompte().getCompteId());
        List<OperationDtoOut> listTransfert = operationService.listTransfertToDtoClient(user);

        model.addAttribute("listTransfert", listTransfert);
        model.addAttribute("title", "Transactions Transfert - Liste");

        model.addAttribute("navbar", navbar);
        return "transfert/index";
    }

    @RequestMapping(value = "/transfert/transferts/new", method = RequestMethod.GET)
    public String newTransfert(Model model, Principal principal){

        AppUser user = userService.findByUserName(principal.getName());

        //Display Balance & validation
        Navbar navbar = navbarService.displayNavbar(user);

        CompteClient compteClient = compteClientService.getById(user.getCompte().getCompteId());
        List<CarteDto> cartes = compteCarteService.listCarteSerialNumberByClient(compteClient.getClient());


        model.addAttribute("transfertclientdto",new TransfertClientDto());
        /*  model.addAttribute("marchands",marchands);*/
        model.addAttribute("title", "Transfert - Nouveau");
       model.addAttribute("cartes", cartes);
        model.addAttribute("navbar", navbar);
        return "transfert/new";
    }

    @RequestMapping(value="/transfert/transferts/save" , method = RequestMethod.POST)
    public String saveTransfert(@Valid TransfertClientDto transfertClientDto, Principal principal , Model model, HttpServletRequest request, RedirectAttributes redirectAttributes, Errors errors) {

        if (errors.hasErrors()) {
            System.out.println("error YES");
            model.addAttribute("transfertclientdto", new TransfertClientDto());
            //model.addAttribute("errors", errors);
            return "redirect:/transfert/transferts/new";
        }
        //Get user
        AppUser user = userService.findByUserName(principal.getName());

        CompteCarte compteSoure = compteCarteService.getCompteCarteBySerialNumber(transfertClientDto.getNumeroSerieCarteSource());
        CompteCarte compteCible = compteCarteService.getCompteCarteBySerialNumber(transfertClientDto.getNumeroSerieCartecible());
        Typeoperation typeoperation = typeoperationService.findByTypeoperationNom(typeOperationTransfert);
        double amount = transfertClientDto.getAmount();
        RechargementAndTransfertDto dto = operationService.makeRechargementOrTransfert(compteSoure.getCompteId(), compteCible.getCompteId(),amount, typeoperation, user );

        //Check if rechargement success
        if (dto.getTypeMessage().equals("succes")){
            redirectAttributes.addFlashAttribute("messagesucces",dto.getMessage());
        }else{
            redirectAttributes.addFlashAttribute("messagedanger",dto.getMessage());
        }

        return "redirect:/transfert/transferts";
    }


}
