package ci.nkagou.closedloop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "approvisionnements")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Approvisionnement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "approvisionnement_id", nullable = false)
    private Long approId;

    @Column(name = "montant", nullable = true)
    private double amount;

    @Column(name = "statut", nullable = true)
    private int statut;

    @Column(name = "note", nullable = false)
    private String note;


    @Column(name = "path", nullable = true)
    private  String path;

    @Column(name = "initiation_date", nullable = false)
    private LocalDateTime initiationDate;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "compteId")
    private Compte compte;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "User_Id")
    private AppUser appUser;
}
