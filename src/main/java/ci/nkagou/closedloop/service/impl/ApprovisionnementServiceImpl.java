package ci.nkagou.closedloop.service.impl;

import ci.nkagou.closedloop.dto.demandeApprovisionnement.DemandeApprovisionnementDto;
import ci.nkagou.closedloop.dto.demandeApprovisionnement.DemandeApprovisionnementDtoOut;
import ci.nkagou.closedloop.model.AppUser;
import ci.nkagou.closedloop.model.Approvisionnement;
import ci.nkagou.closedloop.model.CompteClient;
import ci.nkagou.closedloop.model.Marchand;
import ci.nkagou.closedloop.repository.ApprovisionnementRepository;
import ci.nkagou.closedloop.service.ApprovisionnementService;
import ci.nkagou.closedloop.service.CompteClientService;
import ci.nkagou.closedloop.service.CompteService;
import ci.nkagou.closedloop.service.MarchandService;
import ci.nkagou.closedloop.utils.DateConvert;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
//@AllArgsConstructor
public class ApprovisionnementServiceImpl implements ApprovisionnementService {

    private final ApprovisionnementRepository approvisionnementRepository;

    private final CompteService compteService;

    private final MarchandService marchandService;

    private final CompteClientService compteClientService;

    public static int statutEnAttente = 0;
    public static int statutApprouver = 1;
    public static int statutRefuser = 2;

    public ApprovisionnementServiceImpl(ApprovisionnementRepository approvisionnementRepository, CompteService compteService, MarchandService marchandService, CompteClientService compteClientService) {
        this.approvisionnementRepository = approvisionnementRepository;
        this.compteService = compteService;
        this.marchandService = marchandService;
        this.compteClientService = compteClientService;
    }

    @Override
    public Approvisionnement create(Approvisionnement approvisionnement) {
        return approvisionnementRepository.save(approvisionnement);
    }

    @Override
    public DemandeApprovisionnementDtoOut createAppovisionnement(DemandeApprovisionnementDto dto) {


        Approvisionnement appro = new Approvisionnement();
        appro.setAmount(dto.getAmount());
        appro.setStatut(0);
        appro.setNote("");
        appro.setPath(dto.getFilename());
        appro.setInitiationDate(LocalDateTime.now());
        appro.setCompte(dto.getCompte());
        appro.setAppUser(dto.getUser());
        approvisionnementRepository.save(appro);
        DemandeApprovisionnementDtoOut out = this.approvisionnementToDto(appro);
        return out;
    }

    @Override
    public DemandeApprovisionnementDtoOut approvisionnementToDto(Approvisionnement approvisionnement) {

        //Compte compte = compteRepository.getById(demandeApprovisionnement.)
        DemandeApprovisionnementDtoOut out = new DemandeApprovisionnementDtoOut();
        out.setApproId(approvisionnement.getApproId());
        out.setAmount(approvisionnement.getAmount());
        out.setStatut(this.getStatutName(approvisionnement.getStatut()));
        out.setNote(approvisionnement.getNote());
        out.setPath(approvisionnement.getPath());
        out.setDate(DateConvert.getStringDate(approvisionnement.getInitiationDate()));
        out.setNumeroCompte(approvisionnement.getCompte().getNumeroCompte());
        out.setTypeCompte(compteService.getCompteType(approvisionnement.getCompte().getTypeCompte()));
        out.setTitulaire(compteService.getTitulaireCompte(approvisionnement.getCompte()));
        return out;
    }

    @Override
    public String getStatutName(int statut) {

        String stat = null;

        switch (statut){
            case 0 :
                stat = "EN ATTENTE";
                break;
            case 1:
                stat = "APPROUVEE";
                break;
            case 2 :
                stat = "REFUSEE";
                break;
        }
        return stat;
    }


    @Override
    public List<Approvisionnement> all() {
        return approvisionnementRepository.findAll();
    }


    @Override
    public Approvisionnement getById(Long approvisionnementId) {
        return approvisionnementRepository.getById(approvisionnementId);
    }

    @Override
    public Approvisionnement update(Approvisionnement approvisionnement) {
        return approvisionnementRepository.save(approvisionnement);
    }

    @Override
    public void deleteById(Long approvisionnementId) {
        approvisionnementRepository.deleteById(approvisionnementId);
    }

    @Override
    public List<DemandeApprovisionnementDtoOut> listDemandesByUser(AppUser user) {

        List<Approvisionnement> approvisionnements = approvisionnementRepository.findByAppUser(user);

        List<DemandeApprovisionnementDtoOut> out = new ArrayList<>();

        for (Approvisionnement appro : approvisionnements){

            DemandeApprovisionnementDtoOut dto = this.approvisionnementToDto(appro);

            out.add(dto);
        }
        return out;
    }


    //Retourne la liste des demandes d'appro DTO out pour validation du marchand
   /* @Override
    public List<DemandeApprovisionnementDtoOut> listDemandesForValidationMarchand(AppUser user) {

        //Type de compte client
        String typeCompte = "ccl";

        //Recuperer le marchand connecté
        Marchand marchand = marchandService.getMarchandByUser(user);

        //init
        List<DemandeApprovisionnementDtoOut> out = new ArrayList<>();

        //retourne list des approvisionnements ayant pour Statut " EN ATTENTE" et le type de compte "Compte Client'
        List<Approvisionnement> approvisionnements = approvisionnementRepository.findByStatutAndCompte_TypeCompte(statutEnAttente, typeCompte);


        for (Approvisionnement approvisionnement : approvisionnements){

            //Recuperer le compte client
            CompteClient compteClient = compteClientService.getById(approvisionnement.getCompte().getCompteId());

            //Check si le marchande du compte client recuperer est egal au marchand du compte connecté
            if (compteClient.getClient().getMarchand().getMarchandName().equals(marchand.getMarchandName())){

                //Convertir le demande d'approvisionnement en Dto OUt
                DemandeApprovisionnementDtoOut dto = this.approvisionnementToDto(approvisionnement);

                out.add(dto);

            }
        }

        return out;
    }

    @Override
    public List<DemandeApprovisionnementDtoOut> listDemandesForValidationBanque(AppUser user) {

        String typeCompte = "cma";

        List<Approvisionnement> approvisionnements = approvisionnementRepository.findByStatutAndCompte_TypeCompte(statutEnAttente, typeCompte);

        List<DemandeApprovisionnementDtoOut> out = new ArrayList<>();

        for (Approvisionnement approvisionnement : approvisionnements){
            DemandeApprovisionnementDtoOut dto = this.approvisionnementToDto(approvisionnement);
            out.add(dto);
        }
        return out;
    }
*/
    @Override
    public List<DemandeApprovisionnementDtoOut> listDemandesForValidation(AppUser user/*, String typeCompte*/) {

        List<DemandeApprovisionnementDtoOut> out = new ArrayList<>();

        if (user.getCompte().getTypeCompte().equals("cma")){

            //Recuperer le marchand connecté
            Marchand marchand = marchandService.getMarchandByUser(user);

            //retourne list des approvisionnements ayant pour Statut " EN ATTENTE" et le type de compte "Compte Client'
            List<Approvisionnement> approvisionnements = approvisionnementRepository.findByStatutAndCompte_TypeCompte(statutEnAttente, "ccl");


            for (Approvisionnement approvisionnement : approvisionnements){

                //Recuperer le compte client
                CompteClient compteClient = compteClientService.getById(approvisionnement.getCompte().getCompteId());

                //Check si le marchande du compte client recuperer est egal au marchand du compte connecté
                if (compteClient.getClient().getMarchand().getMarchandName().equals(marchand.getMarchandName())){

                    //Convertir le demande d'approvisionnement en Dto OUt
                    DemandeApprovisionnementDtoOut dto = this.approvisionnementToDto(approvisionnement);

                    out.add(dto);

                }
            }
        }else {

            List<Approvisionnement> approvisionnements = approvisionnementRepository.findByStatutAndCompte_TypeCompte(statutEnAttente, "cma");
            for (Approvisionnement approvisionnement : approvisionnements){
                DemandeApprovisionnementDtoOut dto = this.approvisionnementToDto(approvisionnement);
                out.add(dto);
            }
        }

        return out;
    }


    @Override
    public int countDemandeforValidation(AppUser user/*, String typeCompte*/){

        int nombre = this.listDemandesForValidation(user/*, typeCompte*/).size();
        return nombre;
    }

    @Override
    public void refuserApprovisionnement(Approvisionnement appro){

/*        appro.setStatut(3);

        Approvisionnement approvisionnement = this.create(appro);

        DemandeApprovisionnementDtoOut out = this.approvisionnementToDto(approvisionnement);*/

        this.updateStatut(statutRefuser, appro);

        //return out;
    }

    @Override
    public void updateStatut(int statut, Approvisionnement approvisionnement){

        approvisionnement.setStatut(statut);
        this.create(approvisionnement);

    }

    @Override
    public void approuverApprovisionnement(Approvisionnement appro){

        this.updateStatut(statutApprouver, appro);

    }



}
