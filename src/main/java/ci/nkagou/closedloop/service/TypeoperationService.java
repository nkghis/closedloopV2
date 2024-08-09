package ci.nkagou.closedloop.service;

import ci.nkagou.closedloop.model.Typeoperation;

import java.util.List;

public interface TypeoperationService {

    //String getTypeOperation()
    Typeoperation create (Typeoperation typeoperation);
    List<Typeoperation> all ();
    Typeoperation getById (Long typeOperationId);
    Typeoperation update (Typeoperation typeoperation);
    void deleteById(Long typeOperationId);

    Typeoperation findByTypeoperationNom(String nomTypeOperation);
}
