package ci.nkagou.closedloop.dto.client;

import ci.nkagou.closedloop.model.Marchand;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ClientDtoOut {

    private Long clientId;
    private String clientName;
    private String clientContact;
    private Marchand marchand;
}
