package ci.nkagou.closedloop.dto.approvisionnement;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ApprovisionnementDto {

    private double amount;
    private String note;
    private  String path;
    private LocalDateTime initiationDate;

}
