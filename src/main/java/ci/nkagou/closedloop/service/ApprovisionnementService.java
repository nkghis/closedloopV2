package ci.nkagou.closedloop.service;

import ci.nkagou.closedloop.dto.demandeApprovisionnement.DemandeApprovisionnementDto;
import ci.nkagou.closedloop.dto.demandeApprovisionnement.DemandeApprovisionnementDtoOut;
import ci.nkagou.closedloop.model.AppUser;
import ci.nkagou.closedloop.model.Approvisionnement;

import java.util.List;

public interface ApprovisionnementService {

    Approvisionnement create (Approvisionnement approvisionnement);
    DemandeApprovisionnementDtoOut createAppovisionnement(DemandeApprovisionnementDto dto);
    DemandeApprovisionnementDtoOut approvisionnementToDto(Approvisionnement approvisionnement);
    String getStatutName(int statut);
    List<Approvisionnement> all ();
    Approvisionnement getById (Long approvisionnementId);
    Approvisionnement update (Approvisionnement approvisionnement);
    void deleteById(Long approvisionnementId);
    List<DemandeApprovisionnementDtoOut> listDemandesByUser(AppUser user);

   /* List<DemandeApprovisionnementDtoOut> listDemandesForValidationMarchand(AppUser user);
    List<DemandeApprovisionnementDtoOut> listDemandesForValidationBanque(AppUser user);
*/
    List<DemandeApprovisionnementDtoOut> listDemandesForValidation(AppUser user/*, String typeCompte*/);

    int countDemandeforValidation(AppUser user/*, String typeCompte*/);

    void refuserApprovisionnement(Approvisionnement appro);

    void updateStatut(int statut, Approvisionnement approvisionnement);

    void approuverApprovisionnement(Approvisionnement appro);
}
