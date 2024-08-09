package ci.nkagou.closedloop.dto.carte;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartePersonneDto {

    private String serialNumber;
    private String orderNumber;

    private String personneName;
    private String personneSurname;
}
