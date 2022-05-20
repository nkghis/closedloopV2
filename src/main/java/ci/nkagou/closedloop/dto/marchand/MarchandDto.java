package ci.nkagou.closedloop.dto.marchand;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MarchandDto {

    @NotNull
    private String marchandName;

    private String marchandContact;
}
