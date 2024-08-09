package ci.nkagou.closedloop.dto.transfert;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TransfertClientDto {

    private double amount;
    private String numeroSerieCarteSource;
    private String numeroSerieCartecible;
}
