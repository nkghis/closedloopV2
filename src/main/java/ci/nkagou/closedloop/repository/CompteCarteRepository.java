package ci.nkagou.closedloop.repository;

import ci.nkagou.closedloop.model.Client;
import ci.nkagou.closedloop.model.CompteCarte;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface CompteCarteRepository extends CompteBaseRepository<CompteCarte>{

    List<CompteCarte> findByClient(Client client);
    CompteCarte findByNumeroCompte(Long numeroCompte);
    CompteCarte findByCarte_SerialNumber(String serialNumber);

}
