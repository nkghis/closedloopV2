package ci.nkagou.closedloop.dto.demandeApprovisionnement;


import ci.nkagou.closedloop.model.AppUser;
import ci.nkagou.closedloop.model.Compte;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DemandeApprovisionnementDto {

    private double amount;
    private String note;
    private String filename;
    private Compte compte;
    private AppUser user;
}
