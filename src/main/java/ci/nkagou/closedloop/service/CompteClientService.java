package ci.nkagou.closedloop.service;

import ci.nkagou.closedloop.dto.compteClient.CompteClientDto;
import ci.nkagou.closedloop.dto.compteClient.CompteClientDtoOut;
import ci.nkagou.closedloop.model.Client;
import ci.nkagou.closedloop.model.CompteClient;
import ci.nkagou.closedloop.model.Marchand;

import java.util.List;

public interface CompteClientService {

    CompteClient create (CompteClient compteClient);
    CompteClientDtoOut createCompteClient(CompteClientDto dto);
    CompteClientDtoOut compteClientToDto( CompteClient  compteClient);

    List<CompteClientDtoOut> allComptesClientByMarchand(Marchand marchand);
    List<CompteClient> all ();
    CompteClient getById (Long compteId);
    CompteClient update (CompteClient compteClient);
    void deleteById(Long compteId);

    CompteClient findByClient(Client client);

    List<CompteClient> listCompteClientByListClient(List<Client> clients);
}
