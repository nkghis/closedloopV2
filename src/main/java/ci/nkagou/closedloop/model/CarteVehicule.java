package ci.nkagou.closedloop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("vec")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarteVehicule extends Carte {

    @Column(name = "immatriculation", nullable = true)
    private String immatriculation;

    @Column(name = "marque", nullable = true)
    private String marque;

    @Column(name = "couleur", nullable = true)
    private String couleur;


    public CarteVehicule(String serialNumber, String orderNumber, boolean statut, LocalDateTime initiationDate) {
        super(serialNumber, orderNumber, statut, initiationDate);
    }

    public CarteVehicule(String serialNumber, String orderNumber, boolean statut, LocalDateTime initiationDate, String immatriculation, String marque, String couleur) {
        super(serialNumber, orderNumber, statut, initiationDate);
        this.immatriculation = immatriculation;
        this.marque = marque;
        this.couleur = couleur;
    }
}
