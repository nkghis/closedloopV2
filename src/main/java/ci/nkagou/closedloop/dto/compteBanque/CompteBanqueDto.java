package ci.nkagou.closedloop.dto.compteBanque;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CompteBanqueDto {

    private String banqueNom;
    private Long numeroCompte;
}
