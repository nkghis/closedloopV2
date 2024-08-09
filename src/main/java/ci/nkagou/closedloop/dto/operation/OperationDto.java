package ci.nkagou.closedloop.dto.operation;

import ci.nkagou.closedloop.model.AppUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OperationDto {

    private double amount;

    //private String reference;

    private Long sourceAccountId;

    private Long targetAccountId;

   // private Long typeOperation;

    private AppUser user;

    private LocalDateTime initiationDate;
}
