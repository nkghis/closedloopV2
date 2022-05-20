package ci.nkagou.closedloop.service.impl;

import ci.nkagou.closedloop.dto.client.ClientDto;
import ci.nkagou.closedloop.dto.client.ClientDtoOut;
import ci.nkagou.closedloop.model.Client;
import ci.nkagou.closedloop.model.Marchand;
import ci.nkagou.closedloop.repository.ClientRepository;
import ci.nkagou.closedloop.service.ClientService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;
    @Override
    public Client create(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public List<Client> all() {
        return clientRepository.findAll();
    }

    @Override
    public Client getById(Long clientId) {
        return clientRepository.getById(clientId);
    }

    @Override
    public Client update(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public void deleteById(Long clientId) {
        clientRepository.deleteById(clientId);
    }

    @Override
    public ClientDtoOut clientToDto(Client client) {

        ClientDtoOut clientDtoOut = new ClientDtoOut();
        clientDtoOut.setClientId(client.getClientId());
        clientDtoOut.setClientName(client.getClientName());
        clientDtoOut.setClientContact(client.getClientContact());
        clientDtoOut.setMarchand(client.getMarchand());
        return clientDtoOut;
    }

    @Override
    public Client dtoToClient(ClientDto clientDto) {
        Client client = new Client();
        client.setClientName(clientDto.getClientName());
        client.setClientContact(clientDto.getClientContact());
        client.setMarchand(clientDto.getMarchand());
        return client;
    }

    @Override
    public List<ClientDtoOut> listClientToDto() {

        List<Client> clients = clientRepository.findAll();

        List<ClientDtoOut> clientDtoOuts = new ArrayList<>();

        for(Client client: clients){

            ClientDtoOut clientDtoOut = this.clientToDto(client);
            clientDtoOuts.add(clientDtoOut);
        }

        return clientDtoOuts;
    }

    @Override
    public List<ClientDtoOut> listClientsToDto(List<Client> clients) {

        List<ClientDtoOut> clientDtoOuts = new ArrayList<>();

        for(Client client: clients){

            ClientDtoOut clientDtoOut = this.clientToDto(client);
            clientDtoOuts.add(clientDtoOut);
        }

        return clientDtoOuts;
    }

    @Override
    public Client findByClientName(String clientName) {
        return clientRepository.findByClientName(clientName);
    }

    @Override
    public List<Client> listClientsByMarchand(Marchand marchand) {
        return clientRepository.findClientsByMarchand(marchand);
    }
}
