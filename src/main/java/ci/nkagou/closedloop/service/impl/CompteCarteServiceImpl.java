package ci.nkagou.closedloop.service.impl;

import ch.qos.logback.classic.pattern.DateConverter;
import ci.nkagou.closedloop.dto.carte.CarteDto;
import ci.nkagou.closedloop.dto.carte.CarteDtoOut;
import ci.nkagou.closedloop.dto.compteCarte.CompteCarteDto;
import ci.nkagou.closedloop.dto.compteCarte.CompteCarteDtoOut;
import ci.nkagou.closedloop.model.*;
import ci.nkagou.closedloop.repository.CarteRepository;
import ci.nkagou.closedloop.repository.ClientRepository;
import ci.nkagou.closedloop.repository.CompteCarteRepository;
import ci.nkagou.closedloop.repository.CompteClientRepository;
import ci.nkagou.closedloop.service.CarteService;
import ci.nkagou.closedloop.service.CompteCarteService;
import ci.nkagou.closedloop.service.CompteService;
import ci.nkagou.closedloop.service.PinService;
import ci.nkagou.closedloop.utils.DateConvert;
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
public class CompteCarteServiceImpl implements CompteCarteService {

    @Autowired
    private CompteCarteRepository compteCarteRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CarteRepository carteRepository;
    @Autowired
    private PinService pinService;
    @Autowired
    private CompteClientRepository compteClientRepository;

    @Autowired
    private CarteService carteService;



    @Override
    public CompteCarteDtoOut createCompteCarte(CompteCarteDto dto) {

        CompteCarte compteCarte = new CompteCarte();
        Client client = clientRepository.getById(dto.getClientId());
        Carte carte = carteRepository.findBySerialNumber(dto.getSerialNumber());
        String hashPinCode = pinService.hashPinCode(dto.getPinCode());
        double balance = 0;

        compteCarte.setNumeroCompte(dto.getNumeroCompte());
        compteCarte.setClient(client);
        compteCarte.setCarte(carte);
        compteCarte.setPinCode(hashPinCode);
        compteCarte.setBalance(balance);
        compteCarte.setIsEnable(true);
        compteCarte.setInitiationDate(LocalDateTime.now());
        compteCarteRepository.save(compteCarte);

        CompteCarteDtoOut out = this.compteCarteToDto(compteCarte);

        return out;
    }

    @Override
    public CompteCarteDtoOut compteCarteToDto(CompteCarte compteCarte) {

        CompteCarteDtoOut dto = new CompteCarteDtoOut();

        dto.setNumeroCompte(compteCarte.getNumeroCompte());
        dto.setCompteId(compteCarte.getCompteId());
        dto.setTypeCompte("COMPTE CARTE");
        dto.setBalance(compteCarte.getBalance());
        dto.setTypeCarte(compteCarte.getCarte().getTypeCarte().equals("pec") ? "PERSONNE" : "VEHICULE");
        dto.setOrderNumber(compteCarte.getCarte().getOrderNumber());
        dto.setSerialNumber(compteCarte.getCarte().getSerialNumber());
        dto.setClient(compteCarte.getClient().getClientName());
        dto.setMarchand(compteCarte.getClient().getMarchand().getMarchandName());
        dto.setStatut(compteCarte.getIsEnable() == true ? "ACTIVE" : "DESACTIVE");

        // Custom format if needed
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        //dto.setDate(compteCarte.getInitiationDate().format(formatter));
        dto.setDate(DateConvert.getStringDate(compteCarte.getInitiationDate()));

        return dto;
    }

    @Override
    public List<CompteCarte> all() {
        return compteCarteRepository.findAll();
    }

    @Override
    public List<CompteCarteDtoOut> allComptesCarteByClient(Client client) {

        List<CompteCarte> compteCartes = compteCarteRepository.findByClient(client);

        List<CompteCarteDtoOut> out = new ArrayList<>();

        for (CompteCarte compteCarte : compteCartes){
            CompteCarteDtoOut dto = this.compteCarteToDto(compteCarte);
            out.add(dto);

        }
        return out;
    }

    @Override
    public CompteCarte getById(Long compteId) {
        return compteCarteRepository.getById(compteId);
    }

    @Override
    public CompteCarte update(CompteCarte compteCarte) {
        return compteCarteRepository.save(compteCarte);
    }

    @Override
    public CompteCarte getByNumeroCompte(Long numeroCompte) {
        return compteCarteRepository.findByNumeroCompte(numeroCompte);
    }

    @Override
    public void deleteById(Long compteId) {
        compteCarteRepository.deleteById(compteId);
    }

    @Override
    public Client getClientByCompte(Compte compte) {
        CompteClient compteClient = compteClientRepository.getById(compte.getCompteId());
        Client client = compteClient.getClient();
        return client;
    }

    @Override
    public CompteCarte getCompteCarteBySerialNumber(String serialNumber){

        return compteCarteRepository.findByCarte_SerialNumber(serialNumber);
    }

    //List des cartes ayant un compte appartenant a un client.
    @Override
    public List<CarteDto> listCarteSerialNumberByClient(Client client){

        List<CompteCarte> compteCartes = compteCarteRepository.findByClient(client);

        List<CarteDto> out = this.convertListCompteCarteToListCarteDto(compteCartes);


        return out;
    }

    //List des cartes ayant un compte.
    @Override
    public List<CarteDto> listCarteSerialHaveAccount(){

        List<CompteCarte> compteCartes = compteCarteRepository.findAll();

        List<CarteDto> out = this.convertListCompteCarteToListCarteDto(compteCartes);


        return out;
    }


    //convert Compte Carte en carte Dto.
    @Override
    public  List<CarteDto> convertListCompteCarteToListCarteDto(List<CompteCarte> compteCartes){

        List<Carte> cartes = new ArrayList<>();
        for (CompteCarte compteCarte : compteCartes){

            Carte carte = compteCarte.getCarte();

            cartes.add(carte);
        }
        List<CarteDtoOut> dtos = carteService.listCartesToDtoByListCarte(cartes);

        List<CarteDto> out = carteService.listCarteDtoByListCarteDtoOut(dtos);

        return out;


    }
}
