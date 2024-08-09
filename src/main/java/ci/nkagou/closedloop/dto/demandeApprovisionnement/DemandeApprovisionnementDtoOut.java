package ci.nkagou.closedloop.dto.demandeApprovisionnement;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DemandeApprovisionnementDtoOut {

    private Long approId;
    private double amount;
    private String statut;
    private String note;
    private  String path;
    private String date;
    private Long numeroCompte;
    private String typeCompte;
    private String titulaire;


}


