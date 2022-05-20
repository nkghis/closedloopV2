package ci.nkagou.closedloop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "operations", uniqueConstraints = {
        @UniqueConstraint(columnNames = "reference")
})

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public  class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "operation_id", nullable = false)
    private Long operationId;

    @Column(name = "montant", nullable = false)
    private double amount;

    @Column(name = "initiation_date", nullable = false)
    private LocalDateTime initiationDate;

    @Column(name = "reference", nullable = false)
    private String reference;

    @Column(name = "compte_source_id", nullable = false)
    private Long sourceAccountId;

    @Column(name = "compte_cible_id", nullable = false)
    private Long targetAccountId;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "typeoperationId")
    private Typeoperation typeoperation;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "userId")
    private AppUser appUser;

}
