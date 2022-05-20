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
@DiscriminatorValue("ccl")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
/*@Table( uniqueConstraints = {
        @UniqueConstraint(columnNames = "numero_compte")
})*/
public class CompteClient extends Compte {

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "clientId")
    private Client client;


    @Column(name = "numero_compte", nullable = true)
    private Long numeroCompte;

    public CompteClient(@NotNull(message = "Le [solde] ne peut pas être null") double balance, @NotNull Boolean isEnable, LocalDateTime initiationDate, Client client, Long numeroCompte) {
        super(balance, isEnable, initiationDate);
        this.client = client;
        this.numeroCompte = numeroCompte;
    }

}
