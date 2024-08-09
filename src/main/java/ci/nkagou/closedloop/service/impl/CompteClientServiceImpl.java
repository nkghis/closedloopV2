package ci.nkagou.closedloop.service.impl;

import ci.nkagou.closedloop.dto.compteClient.CompteClientDto;
import ci.nkagou.closedloop.dto.compteClient.CompteClientDtoOut;
import ci.nkagou.closedloop.model.Client;
import ci.nkagou.closedloop.model.Compte;
import ci.nkagou.closedloop.model.CompteClient;
import ci.nkagou.closedloop.model.Marchand;
import ci.nkagou.closedloop.repository.ClientRepository;
import ci.nkagou.closedloop.repository.CompteClientRepository;
import ci.nkagou.closedloop.repository.CompteRepository;
import ci.nkagou.closedloop.service.CompteClientService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class CompteClientServiceImpl implements CompteClientService {

    @Autowired
    private CompteClientRepository compteClientRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public CompteClient create(CompteClient compteClient) {
        return compteClientRepository.save(compteClient);
    }

    @Override
    public CompteClientDtoOut createCompteClient(CompteClientDto dto) {

        Client client = clientRepository.getById(dto.getClientId());

        CompteClient compteClient = new CompteClient();
        compteClient.setClient(client);
        compteClient.setNumeroCompte(dto.getNumeroCompte());
        compteClient.setCompteId(dto.getNumeroCompte());
        compteClient.setBalance(0);
        compteClient.setIsEnable(true);
        compteClient.setInitiationDate(LocalDateTime.now());
        compteClientRepository.save(compteClient);
        CompteClientDtoOut out = this.compteClientToDto(compteClient);

        return out;
    }

    @Override
    public CompteClientDtoOut compteClientToDto(CompteClient compteClient) {

        CompteClientDtoOut out = new CompteClientDtoOut();
        out.setCompteId(compteClient.getCompteId());
        out.setTypeCompte("COMPTE CLIENT");
        out.setNumeroCompte(compteClient.getNumeroCompte());
        out.setBalance(compteClient.getBalance());
        out.setClient(compteClient.getClient().getClientName());
        out.setMarchand(compteClient.getClient().getMarchand().getMarchandName());
        out.setStatut(compteClient.getIsEnable() == true ? "ACTIVE" : "DESACTIVE");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        out.setDate(compteClient.getInitiationDate().format(formatter));
        return out;
    }

    @Override
    public List<CompteClientDtoOut> allComptesClientByMarchand(Marchand marchand) {

        List<CompteClientDtoOut> out = new ArrayList<>();
        List<CompteClient> compteClients = compteClientRepository.findAll();

        for (CompteClient compteClient : compteClients){

            if (compteClient.getClient().getMarchand().getMarchandId() == marchand.getMarchandId()){
                CompteClientDtoOut dto = this.compteClientToDto(compteClient);
                out.add(dto);
            }
        }

        return out;
    }

    @Override
    public List<CompteClient> all() {
        return compteClientRepository.findAll();
    }

    @Override
    public CompteClient getById(Long compteId) {
        return compteClientRepository.getById(compteId);
    }

    @Override
    public CompteClient update(CompteClient compteClient) {
        return compteClientRepository.save(compteClient);
    }

    @Override
    public void deleteById(Long compteId) {
        compteClientRepository.deleteById(compteId);
    }

    @Override
    public CompteClient findByClient(Client client){

        return compteClientRepository.findByClient(client);
    }

    @Override
    public List<CompteClient> listCompteClientByListClient(List<Client> clients){

        List<CompteClient> compteClients = new ArrayList<>();

        for (Client client : clients){

            CompteClient compteClient = this.findByClient(client);

            compteClients.add(compteClient);
        }

        return compteClients;
    }
}
