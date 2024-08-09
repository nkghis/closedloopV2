package ci.nkagou.closedloop.dto.operation;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//Ajouter pour gerer les doublons de l objet
@EqualsAndHashCode
public class OperationDtoOut {

    private Long operationId;

    private String reference;

    private double amount;

    private Long compteSource;

    private String nameCompteSource;

    private Long compteCible;

    private String nameCompteCible;

    private String typeOperation;

    private String dateOperation;


}
