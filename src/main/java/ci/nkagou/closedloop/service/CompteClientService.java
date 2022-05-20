package ci.nkagou.closedloop.service;

import ci.nkagou.closedloop.model.CompteClient;

import java.util.List;

public interface CompteClientService {

    CompteClient create (CompteClient compteClient);
    List<CompteClient> all ();
    CompteClient getById (Long compteId);
    CompteClient update (CompteClient compteClient);
    void deleteById(Long compteId);
}
