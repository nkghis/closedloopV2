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
@DiscriminatorValue("pec")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartePersonne extends Carte {

    @Column(name = "personne_name", nullable = true)
    private String personneName;

    @Column(name = "personne_surname", nullable = true)
    private String personneSurname;


    public CartePersonne(String serialNumber, String orderNumber, boolean statut, LocalDateTime initiationDate) {
        super(serialNumber, orderNumber, statut, initiationDate);
    }

    public CartePersonne(String serialNumber, String orderNumber, boolean statut, LocalDateTime initiationDate, String personneName, String personneSurname) {
        super(serialNumber, orderNumber, statut, initiationDate);
        this.personneName = personneName;
        this.personneSurname = personneSurname;
    }
}
