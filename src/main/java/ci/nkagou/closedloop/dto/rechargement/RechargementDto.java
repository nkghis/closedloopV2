package ci.nkagou.closedloop.dto.rechargement;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RechargementDto {

    private double amount;
    private String numeroSerie;
}
