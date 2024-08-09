package ci.nkagou.closedloop.controller;

import ci.nkagou.closedloop.dto.compte.CompteIsEnableDto;
import ci.nkagou.closedloop.dto.demandeApprovisionnement.DemandeApprovisionnementDto;
import ci.nkagou.closedloop.dto.demandeApprovisionnement.DemandeApprovisionnementDtoOut;
import ci.nkagou.closedloop.dto.navbar.Navbar;
import ci.nkagou.closedloop.dto.operation.OperationDto;
import ci.nkagou.closedloop.dto.operation.OperationDtoOut;
import ci.nkagou.closedloop.model.AppUser;
import ci.nkagou.closedloop.model.Approvisionnement;
import ci.nkagou.closedloop.model.Compte;
import ci.nkagou.closedloop.model.Typeoperation;
import ci.nkagou.closedloop.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ApprovisionnementController {

    @Autowired
    private ApprovisionnementService approvisionnementService;

    @Autowired
    private UserService userService;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private TypeoperationService typeoperationService;

    @Autowired
    private OperationService operationService;

    @Autowired
    private CompteService compteService;

    @Autowired
    private NavbarService navbarService;


    public static String nomTypeOperation = "APPROVISIONNEMENT";



    @RequestMapping(value =  "/rechargement/appro", method = RequestMethod.GET)
    public String indexDemandeAppro(Model model, Principal principal) {

        //get user connected
        AppUser user = userService.findByUserName(principal.getName());

        //Display Balance & validation
        Navbar navbar = navbarService.displayNavbar(user);

        List<DemandeApprovisionnementDtoOut> listDemandeAppro = approvisionnementService.listDemandesByUser(user);
        model.addAttribute("title", "Approvisionnement - Liste des demandes de validations");
        model.addAttribute("listappros",listDemandeAppro);
        model.addAttribute("navbar", navbar);


        return "approvisionnement/indexDemande";

    }

    @RequestMapping(value =  "/approvisionnement/appro", method = RequestMethod.GET)
    public String indexValidationAppro(Model model, Principal principal) {

        //get user connected
        AppUser user = userService.findByUserName(principal.getName());

        //Display Balance & validation
        Navbar navbar = navbarService.displayNavbar(user);


        String typeCompte = user.getCompte().getTypeCompte();
        List<DemandeApprovisionnementDtoOut> listDemandeAppro = approvisionnementService.listDemandesForValidation(user/*, typeCompte*/);

        model.addAttribute("listappros",listDemandeAppro);
        model.addAttribute("title", "Approvisionnement - Liste des approvision en attente de validations");
        model.addAttribute("navbar", navbar);
        return "approvisionnement/indexValidation";
    }

    @RequestMapping(value = "/rechargement/appro/new", method = RequestMethod.GET)
    public String newDemandeAppro(Model model, Principal principal){
        //get user connected
        AppUser user = userService.findByUserName(principal.getName());

        //Display Balance & validation
        Navbar navbar = navbarService.displayNavbar(user);

        model.addAttribute("demandeapprodto",new DemandeApprovisionnementDto());
        model.addAttribute("title", "Approvisionnement - Nouvelle demande");
        model.addAttribute("navbar", navbar);
        return "approvisionnement/newDemande";
    }

    @RequestMapping(value = "/rechargement/appro/save", method = RequestMethod.POST)
    public String saveClient(@Valid DemandeApprovisionnementDto dto, Errors errors, Model model, RedirectAttributes redirectAttributes, Principal principal, @RequestParam("document") MultipartFile file){
        if (errors.hasErrors()){
            System.out.println("error YES");
            model.addAttribute("demandeapprodto", new DemandeApprovisionnementDto());
            //model.addAttribute("errors", errors);
            return "approvisionnement/newDemande";
        }

        String fileName = fileStorageService.storeFile(file);

        AppUser user = userService.findByUserName(principal.getName());
        //LocalDateTime date = LocalDateTime.now();

        //String filename =
        Compte compte = user.getCompte();
        dto.setCompte(compte);
        dto.setUser(user);
        dto.setFilename(fileName);
        DemandeApprovisionnementDtoOut out = approvisionnementService.createAppovisionnement(dto);
        redirectAttributes.addFlashAttribute("messagesucces","Opération de création éffectuée avec succès");


        return "redirect:/rechargement/appro/";
    }


    @RequestMapping(value="/approvisionnement/appro/valider/{id}", method = RequestMethod.GET)
    public String validateAppro(@PathVariable Long id, Model model, Principal principal, HttpServletRequest request, RedirectAttributes redirectAttributes){



        Approvisionnement approvisionnement = approvisionnementService.getById(id);
        AppUser user = userService.findByUserName(principal.getName());

        Compte compteCible = approvisionnement.getCompte();
        //Compte compteSource = user.getCompte();

        Long sourceAccountId = user.getCompte().getCompteId();
        Long targetAccountId = approvisionnement.getCompte().getCompteId();


        CompteIsEnableDto isEnableDto = compteService.checkIsEnableSourceAndCible(sourceAccountId, targetAccountId);

        //Check account enable

        if (isEnableDto.getIsEnable() == true){

            double amount = approvisionnement.getAmount();
            Boolean sourceBalanceAvailable = compteService.isAmountAvailable(amount, sourceAccountId);
            if (sourceBalanceAvailable == true){

                //Approuve approvisionnement

                OperationDto dto = new OperationDto();
                dto.setAmount(amount);
                dto.setSourceAccountId(sourceAccountId);
                dto.setTargetAccountId(targetAccountId);
                dto.setUser(user);
                dto.setInitiationDate(LocalDateTime.now());

                Typeoperation typeoperation = typeoperationService.findByTypeoperationNom(nomTypeOperation);

                OperationDtoOut out = operationService.makeOperation( typeoperation,user, dto);

                approvisionnementService.approuverApprovisionnement(approvisionnement);
                String message = "La demande d'approvisionnement vers le compte :  " + compteCible.getNumeroCompte() +" d'un montant de : "+ amount +" a été validée avec succès";
                redirectAttributes.addFlashAttribute("messagesucces",message);
            }else {

                //erreur solde insuffisant
                String message = "Désolé, le solde du compte : "+ user.getCompte().getNumeroCompte() +" est insuffisant pour effectuer cette opération.";
                redirectAttributes.addFlashAttribute("messagedanger",message);

            }
        }else {
            //erreur compte desactivée
            String message = "Désolé cette operation ne peut aboutir car le compte ayant le numero : " +isEnableDto.getNumeroCompte()+" est desactivé";
            redirectAttributes.addFlashAttribute("messagedanger",message);

        }

        return "redirect:/approvisionnement/appro";
    }

    @RequestMapping(value="/approvisionnement/appro/refuser/{id}" , method = RequestMethod.GET)
    public String refuserAppro(@PathVariable Long id, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes){

        Approvisionnement approvisionnement = approvisionnementService.getById(id);
         approvisionnementService.refuserApprovisionnement(approvisionnement);

        String message = "La demande d'approvisionnement a été refusée";
        redirectAttributes.addFlashAttribute("messagedanger",message);

        return "redirect:/approvisionnement/appro";
    }

}
