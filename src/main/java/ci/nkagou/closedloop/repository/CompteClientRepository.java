package ci.nkagou.closedloop.repository;

import ci.nkagou.closedloop.model.Client;
import ci.nkagou.closedloop.model.CompteClient;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface CompteClientRepository extends CompteBaseRepository<CompteClient> {

    CompteClient findByClient(Client client);

}
