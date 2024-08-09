package ci.nkagou.closedloop.dto.carte;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CarteDtoOut {

    private Long carteId;
    private String typeCarte;
    private String serialNumber;
    private String orderNumber;
    private String statut;
    private String personneName;
    private String personneSurname;

    private String immatriculation;
    private String couleur;
    private String marque;

    private String date;
}
