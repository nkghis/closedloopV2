package ci.nkagou.closedloop.dto.compteClient;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CompteClientDtoOut {

    private Long compteId;
    private String typeCompte;
    private Long numeroCompte;
    private double balance;
    private String client;
    private String marchand;
    private String statut;
    private String date;

}
