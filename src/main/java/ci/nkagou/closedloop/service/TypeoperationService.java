package ci.nkagou.closedloop.service;

import ci.nkagou.closedloop.model.Typeoperation;

import java.util.List;

public interface TypeoperationService {

    Typeoperation create (Typeoperation typeoperation);
    List<Typeoperation> all ();
    Typeoperation getById (Long typeOperationId);
    Typeoperation update (Typeoperation typeoperation);
    void deleteById(Long typeOperationId);
}
