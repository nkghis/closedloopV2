package ci.nkagou.closedloop.service;

import ci.nkagou.closedloop.dto.operation.OperationDto;
import ci.nkagou.closedloop.dto.operation.OperationDtoOut;
import ci.nkagou.closedloop.dto.operation.RechargementAndTransfertDto;
import ci.nkagou.closedloop.model.*;

import java.util.List;

public interface OperationService {

    OperationDtoOut createOperation(OperationDto dto, AppUser user, Typeoperation typeoperation);
    OperationDtoOut operationToDto(Operation operation );
    List<OperationDtoOut> listOperationToDto(List<Operation> operations);
    Operation create (Operation operation);
    List<Operation> all ();
    Operation getById (Long operationId);
    Operation update (Operation operation);
    void deleteById(Long operationId);

/*    void makeApprovisionnement(ApprovisionnementDto dto);
    void makeTransfert(TransfertClientDto dto);
    void makeRechargement(RechargementDto dto);*/

    OperationDtoOut makeOperation(Typeoperation typeOperation, AppUser user, OperationDto dto);

    List<OperationDtoOut> listOperationToDtoByCompte(Long compteSourceId, Long compteCibleId);

    List<OperationDtoOut> listOperationToDtoCompteClientByMarchand(Marchand marchand);

    List<OperationDtoOut> listOperationToDtoCompteClient(Long compteId);

    List<OperationDtoOut> listOperationToDtoMarchandAndMarchandClient(Long compteId);

    List<OperationDtoOut> removeDuplicateOperationDtoOut(List<OperationDtoOut> out);

    List<OperationDtoOut> listRechargementToDtoClient(Long compteId);

    RechargementAndTransfertDto makeRechargementOrTransfert(Long sourceAccountId, Long targetAccountId, double amount, Typeoperation typeoperation, AppUser user);

    List<OperationDtoOut> listTransfertToDtoClient(AppUser user);
}
