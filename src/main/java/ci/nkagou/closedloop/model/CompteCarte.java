package ci.nkagou.closedloop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("cca")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompteCarte extends Compte {

    @NotBlank
    @JsonIgnore
    @Column(name = "code_pin", nullable = true)
    private String pinCode;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "clientId")
    private Client client;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "carteId")
    private Carte carte;

    public CompteCarte(@NotNull(message = "Le [solde] ne peut pas être null") double balance, @NotNull Boolean isEnable, LocalDateTime initiationDate, @NotBlank String pinCode, Client client, Carte carte) {
        super(balance, isEnable, initiationDate);
        this.pinCode = pinCode;
        this.client = client;
        this.carte = carte;
    }

    public CompteCarte(@NotNull(message = "Le [solde] ne peut pas être null") double balance, @NotNull Boolean isEnable, LocalDateTime initiationDate, @NotBlank String pinCode) {
        super(balance, isEnable, initiationDate);
        this.pinCode = pinCode;
    }

    public CompteCarte(@NotNull(message = "Le [solde] ne peut pas être null") double balance, @NotNull Boolean isEnable, LocalDateTime initiationDate, @NotBlank String pinCode, Client client) {
        super(balance, isEnable, initiationDate);
        this.pinCode = pinCode;
        this.client = client;
    }
}
