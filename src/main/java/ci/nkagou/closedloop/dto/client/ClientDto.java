package ci.nkagou.closedloop.dto.client;

import ci.nkagou.closedloop.model.Marchand;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class ClientDto {

    @NotNull
    private String clientName;
    private String clientContact;
    private Marchand marchand;
}
