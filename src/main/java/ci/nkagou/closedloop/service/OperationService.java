package ci.nkagou.closedloop.service;

import ci.nkagou.closedloop.model.Operation;

import java.util.List;

public interface OperationService {

    Operation create (Operation operation);
    List<Operation> all ();
    Operation getById (Long operationId);
    Operation update (Operation operation);
    void deleteById(Long operationId);
}
