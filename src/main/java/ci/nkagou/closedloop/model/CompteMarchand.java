package ci.nkagou.closedloop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("cma")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
/*@Table( uniqueConstraints = {
        @UniqueConstraint(columnNames = "numero_compte")
})*/
public class CompteMarchand extends Compte {

    @Column(name = "decouvert", nullable = true)
    private double decouvert;

/*    @Column(name = "numero_compte", nullable = true)
    private Long numeroCompte;*/



    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "marchandId")
    private Marchand marchand;

    public CompteMarchand(@NotNull Long numeroCompte, @NotNull(message = "Le [solde] ne peut pas être null") double balance, @NotNull Boolean isEnable, LocalDateTime initiationDate, double decouvert,  Marchand marchand) {
        super(balance, isEnable, initiationDate, numeroCompte);
        this.decouvert = decouvert;
        /*this.numeroCompte = numeroCompte;*/
        this.marchand = marchand;
    }
}
