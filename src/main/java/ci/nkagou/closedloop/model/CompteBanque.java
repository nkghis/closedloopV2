package ci.nkagou.closedloop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("cba")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

/*@Table( uniqueConstraints = {
        @UniqueConstraint(columnNames = "numero_compte")
})*/
public class CompteBanque extends Compte {

    @Column(name = "banque_nom", nullable = true)
    private String banqueNom;

/*    @Column(name = "numero_compte", nullable = true)
    private Long numeroCompte;*/


    public CompteBanque(@NotNull(message = "Le [solde] ne peut pas être null") double balance, @NotNull Boolean isEnable, LocalDateTime initiationDate, String banqueNom, @NotNull Long numeroCompte) {
        super(balance, isEnable, initiationDate, numeroCompte);
        this.banqueNom = banqueNom;
//        this.numeroCompte = numeroCompte;
    }
}
