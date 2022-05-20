package ci.nkagou.closedloop.controller;

import ci.nkagou.closedloop.dto.client.ClientDto;
import ci.nkagou.closedloop.dto.client.ClientDtoOut;
import ci.nkagou.closedloop.model.AppUser;
import ci.nkagou.closedloop.model.Client;
import ci.nkagou.closedloop.model.Marchand;
import ci.nkagou.closedloop.service.ClientService;
import ci.nkagou.closedloop.service.MarchandService;
import ci.nkagou.closedloop.service.UserService;
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
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class ClientController {

    private ClientService clientService;
    private MarchandService marchandService;
    private UserService userService;

    @RequestMapping(value =  "/client/clients", method = RequestMethod.GET)
        public String index(Model model, Principal principal, HttpServletRequest request) {
        //get user connected
        AppUser user = userService.findByUserName(principal.getName());

        List<ClientDtoOut> clients = new ArrayList<>();

        if (request.isUserInRole("ROLE_ADMIN") || request.isUserInRole("ROLE_BANQUE") ){
            clients = clientService.listClientToDto();
        }else {
            // get marchand connected
            Marchand marchand = marchandService.getMarchandByUser(user);
            List<Client>  cs = clientService.listClientsByMarchand(marchand);
            clients = clientService.listClientsToDto(cs);
        }

        model.addAttribute("listClients", clients);
            return "client/index";
    }

    @RequestMapping(value = "/client/clients/new", method = RequestMethod.GET)
    public String newClient(Model model){

        List<Marchand> marchands = marchandService.all();
        model.addAttribute("clientdto",new ClientDto());
      /*  model.addAttribute("marchands",marchands);*/
        model.addAttribute("title", "Client - Nouveau");
        return "client/new";
    }

    @RequestMapping(value = "/client/clients/save", method = RequestMethod.POST)
    public String saveClient(@Valid ClientDto clientDto, Errors errors, Model model, RedirectAttributes redirectAttributes, Principal principal){
        if (errors.hasErrors()){
            System.out.println("error YES");
            model.addAttribute("clientdto", new ClientDto());
            //model.addAttribute("errors", errors);
            return "client/new";
        }

        Client c = clientService.findByClientName(clientDto.getClientName());
        if (c == null){
            AppUser user = userService.findByUserName(principal.getName());
            Marchand marchand =marchandService.getMarchandByUser(user);
            clientDto.setMarchand(marchand);
            Client client = clientService.dtoToClient(clientDto);
            clientService.create(client);
            redirectAttributes.addFlashAttribute("messagesucces","Opération de création éffectuée avec succès");
        }else{

            redirectAttributes.addFlashAttribute("messagedanger","Echec, Le nom du marchand [" + clientDto.getClientName() + " ] existe déjà dans la base de données");
        }
        return "redirect:/client/clients/";
    }

    @RequestMapping(value = "/client/clients/edit/{id}", method = RequestMethod.GET)
    public String editClient(@PathVariable Long id, Model model){
        Client c = clientService.getById(id);
        model.addAttribute("client", c);
        model.addAttribute("title", "Client - Edition");
        return "client/edit";
    }

    @RequestMapping(value = "/client/clients/delete/{id}", method = RequestMethod.GET)
    public String deleteMarchand(@PathVariable Long id, RedirectAttributes redirectAttributes){
        clientService.deleteById(id);
        redirectAttributes.addFlashAttribute("messagesucces","Opération de suppression éffectuée avec succès");
        return "redirect:/client/clients";
    }

}
