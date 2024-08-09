package ci.nkagou.closedloop.dto.compte;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class CompteIsEnableDto {

    private Boolean isEnable;
    private Long numeroCompte;
}
