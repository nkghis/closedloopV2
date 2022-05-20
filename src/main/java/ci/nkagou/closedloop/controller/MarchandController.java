package ci.nkagou.closedloop.controller;

import ci.nkagou.closedloop.dto.marchand.MarchandDto;
import ci.nkagou.closedloop.dto.marchand.MarchandDtoOut;
import ci.nkagou.closedloop.model.Marchand;
import ci.nkagou.closedloop.service.MarchandService;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class MarchandController {

    private MarchandService marchandService;

    @RequestMapping(value =  "/marchand/marchands", method = RequestMethod.GET)
    public String index(Model model, Principal principal) {

        List<MarchandDtoOut> marchandDtos = marchandService.listMarchandToDto();
        model.addAttribute("listMarchands", marchandDtos);
        return "marchand/index";
    }

    @RequestMapping(value = "/marchand/marchands/new", method = RequestMethod.GET)
    public String newMarchand(Model model){

        model.addAttribute("marchanddto",new MarchandDto());
        model.addAttribute("title", "Marchand - Nouveau");
        return "marchand/new";
    }


    @RequestMapping(value = "/marchand/marchands/save", method = RequestMethod.POST)
    public String saveMarchand(@Valid MarchandDto marchandDto, Errors errors, Model model, RedirectAttributes redirectAttributes){

        if (errors.hasErrors()){
            System.out.println("error YES");
            model.addAttribute("marchanddto", new MarchandDto());
            //model.addAttribute("errors", errors);
            return "marchand/new";
        }

        Marchand m = marchandService.findByMarchandName(marchandDto.getMarchandName());
        if (m == null){
            Marchand marchand = marchandService.dtoToMarchand(marchandDto);
            marchandService.create(marchand);
            redirectAttributes.addFlashAttribute("messagesucces","Opération de création éffectuée avec succès");
        }else{

            redirectAttributes.addFlashAttribute("messagedanger","Echec, Le nom du marchand [" +marchandDto.getMarchandName() + " ] existe déjà dans la base de données");
        }


        return "redirect:/marchand/marchands";
    }

    @RequestMapping(value = "/marchand/marchands/edit/{id}", method = RequestMethod.GET)
    public String editMarchand(@PathVariable Long id, Model model){
        Marchand m = marchandService.getById(id);
        model.addAttribute("marchand", m);
        model.addAttribute("title", "Marchand - Edition");
        return "marchand/edit";
    }

    @RequestMapping(value = "/marchand/marchands/delete/{id}", method = RequestMethod.GET)
    public String deleteMarchand(@PathVariable Long id, RedirectAttributes redirectAttributes){

        marchandService.deleteById(id);
        redirectAttributes.addFlashAttribute("messagesucces","Opération de suppression éffectuée avec succès");
        return "redirect:/marchand/marchands";
    }
}
