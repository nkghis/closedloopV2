package ci.nkagou.closedloop.dto.marchand;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MarchandDtoOut {

    private Long marchandId;
    private String marchandName;
    private String marchandContact;
}
