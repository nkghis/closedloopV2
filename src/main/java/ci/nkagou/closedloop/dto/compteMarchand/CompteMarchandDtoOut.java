package ci.nkagou.closedloop.dto.compteMarchand;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CompteMarchandDtoOut {

    private Long compteId;
    private String typeCompte;
    private Long numeroCompte;
    private double decouvert;
    private double balance;
    private String marchand;
    private String statut;
    private String date;
}


