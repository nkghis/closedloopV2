package ci.nkagou.closedloop.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "comptes"/*, //
        uniqueConstraints = { //
                @UniqueConstraint(name = "APP_OPERATION_UK", columnNames = "operationReference") }*/)

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "typecompte")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class Compte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "compte_id", nullable = false)
    private Long compteId;

    @NotNull(message = "Le [COMPTE] ne peut pas être null")
    @Column(name = "numero_compte", nullable = false)
    private Long numeroCompte;

    @NotNull(message = "Le [solde] ne peut pas être null")
    @Column(name = "balance", nullable = false)
    private double balance;

    @NotNull
    @Column(name = "statut", nullable = false)
    private Boolean isEnable;

    @Column(name = "initiation_date", nullable = false)
    private LocalDateTime initiationDate;

    //Add for field in this section to get type carte
    @Column(name="typecompte", insertable = false, updatable = false)
    protected String typeCompte;


    public Compte(@NotNull(message = "Le [solde] ne peut pas être null") double balance, @NotNull Boolean isEnable, LocalDateTime initiationDate, Long numeroCompte) {
        this.balance = balance;
        this.isEnable = isEnable;
        this.initiationDate = initiationDate;
        this.numeroCompte = numeroCompte;
    }


}
