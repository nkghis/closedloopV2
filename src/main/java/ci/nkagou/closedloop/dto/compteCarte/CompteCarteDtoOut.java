package ci.nkagou.closedloop.dto.compteCarte;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CompteCarteDtoOut {

    private Long compteId;
    private Long numeroCompte;
    private String typeCompte;
    private String typeCarte;
    private double balance;
    private String orderNumber;
    private String serialNumber;
    private String client;
    private String marchand;

    private String statut;
    private String date;

}
