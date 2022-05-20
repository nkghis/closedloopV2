package ci.nkagou.closedloop.service;

import ci.nkagou.closedloop.dto.client.ClientDto;
import ci.nkagou.closedloop.dto.client.ClientDtoOut;
import ci.nkagou.closedloop.model.Client;
import ci.nkagou.closedloop.model.Marchand;

import java.util.List;

public interface ClientService {

    Client create (Client client);
    List<Client> all ();
    Client getById (Long clientId);
    Client update (Client client);
    void deleteById(Long clientId);

    ClientDtoOut clientToDto(Client client);
    Client dtoToClient(ClientDto clientDto);
    List<ClientDtoOut> listClientToDto();
    List<ClientDtoOut> listClientsToDto(List<Client> clients);
    Client findByClientName(String clientName);

    List<Client> listClientsByMarchand(Marchand marchand);

}
