package ci.nkagou.closedloop.dto.compteCarte;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CompteCarteDto {

    private Long numeroCompte;
    private String serialNumber;
    private Long clientId;
    private String pinCode;
}
