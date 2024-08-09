package ci.nkagou.closedloop.dto.carte;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CarteVehiculeDto {

    private String serialNumber;
    private String orderNumber;
    private String immatriculation;
    private String couleur;
    private String marque;
}
