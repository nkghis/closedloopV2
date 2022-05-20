package ci.nkagou.closedloop.service;

import ci.nkagou.closedloop.model.Approvisionnement;

import java.util.List;

public interface ApprovisionnementService {

    Approvisionnement create (Approvisionnement approvisionnement);
    List<Approvisionnement> all ();
    Approvisionnement getById (Long approvisionnementId);
    Approvisionnement update (Approvisionnement approvisionnement);
    void deleteById(Long approvisionnementId);
}
