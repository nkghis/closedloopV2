package ci.nkagou.closedloop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "cartes", uniqueConstraints = {
        @UniqueConstraint(columnNames = "serial_number"),
        @UniqueConstraint(columnNames = "order_number") })

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "typecarte", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class Carte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "carte_id", nullable = false)
    private Long carteId;

    @Column(name = "serial_number", nullable = false)
    private String serialNumber;

    @Column(name = "order_number", nullable = false)
    private String orderNumber;

    @Column(name = "statut", nullable = false)
    private boolean statut;

    @Column(name = "initiation_date", nullable = true)
    private LocalDateTime initiationDate;

    //Add for field in this section to get type carte
    @Column(name="typecarte", insertable = false, updatable = false)
    protected String typeCarte;






    public Carte(String serialNumber, String orderNumber, boolean statut) {
        this.serialNumber = serialNumber;
        this.orderNumber = orderNumber;
        this.statut = statut;
    }

    public Carte(String serialNumber, String orderNumber, boolean statut, LocalDateTime initiationDate) {
        this.serialNumber = serialNumber;
        this.orderNumber = orderNumber;
        this.statut = statut;
        this.initiationDate = initiationDate;
    }
}
