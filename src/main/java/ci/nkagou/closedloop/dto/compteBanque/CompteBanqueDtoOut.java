package ci.nkagou.closedloop.dto.compteBanque;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CompteBanqueDtoOut {

    private Long compteId;
    private String typeCompte;
    private Long numeroCompte;
    private double balance;
    private String banqueNom;
    private String statut;
    private String date;
}
