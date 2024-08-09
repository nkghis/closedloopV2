package ci.nkagou.closedloop.service.impl;

import ci.nkagou.closedloop.dto.compte.CompteIsEnableDto;
import ci.nkagou.closedloop.model.*;
import ci.nkagou.closedloop.repository.*;
import ci.nkagou.closedloop.service.CartePersonneService;
import ci.nkagou.closedloop.service.CarteVehiculeService;
import ci.nkagou.closedloop.service.CompteService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class CompteServiceImpl implements CompteService {

    @Autowired
    private CompteRepository compteRepository;

    @Autowired
    private CompteBanqueRepository compteBanqueRepository;

    @Autowired
    private CompteMarchandRepository compteMarchandRepository;

    @Autowired
    private CompteClientRepository compteClientRepository;

    @Autowired
    private CompteCarteRepository compteCarteRepository;

    @Autowired
    private CartePersonneService cartePersonneService;

    @Autowired
    private CarteVehiculeService  carteVehiculeService;

    @Override
    public Compte getById(Long compteId){

        return compteRepository.getById(compteId);
    }

    @Override
    public String getCompteType(String typeCompte) {

        String type = null;
        switch (typeCompte) {
            case "cba":
                type = "COMPTE BANQUE";
                break;
            case "cma":
                type = "COMPTE MARCHAND";
                break;
            case "ccl":
                type = "COMPTE CLIENT";
                break;
            case "cca":
                type = "COMPTE CARTE";
                break;
        }

        return type;
    }

    @Override
    public String getTitulaireCompte(Compte compte) {

        String out = null;

        String typeCompte = compte.getTypeCompte();

        switch (typeCompte){
            case "cba":
                CompteBanque compteBanque = compteBanqueRepository.getById(compte.getCompteId());
                out = compteBanque.getBanqueNom();
                break;
            case "cma":
                CompteMarchand compteMarchand = compteMarchandRepository.getById(compte.getCompteId());
                out = compteMarchand.getMarchand().getMarchandName();
                break;
            case "ccl":
                CompteClient compteClient = compteClientRepository.getById(compte.getCompteId());
                out = compteClient.getClient().getClientName();
                break;
            case "cca":
                CompteCarte compteCarte = compteCarteRepository.getById(compte.getCompteId());
                String nameTitulaireCarte = "";
                if (compteCarte.getCarte().getTypeCarte().equals("pec"))
                {
                    CartePersonne cartePersonne = cartePersonneService.getById(compteCarte.getCarte().getCarteId());
                    nameTitulaireCarte = cartePersonne.getPersonneSurname()+" "+ cartePersonne.getPersonneName();

                }else {
                    CarteVehicule carteVehicule = carteVehiculeService.getById(compteCarte.getCarte().getCarteId());
                    nameTitulaireCarte = carteVehicule.getImmatriculation() +" "+ carteVehicule.getMarque();
                }
                out = nameTitulaireCarte;
                break;
        }
        return out;
    }

    @Override
    public void updateBalance(double balance, Compte compte) {
        compte.setBalance(balance);
        compteRepository.save(compte);

    }

    @Override
    public double getBalanceSourceCompte(double sourceBalance, double amount) {
        return sourceBalance - amount;
    }

    @Override
    public double getBalanceTargetCompte(double targetBalance, double amount) {
        return targetBalance + amount;
    }

    @Override
    public void updateComptesAfterOperation(Compte compteSource, Compte compteCible, double amount) {

        double newSourceBalance = this.getBalanceSourceCompte(compteSource.getBalance(), amount);
        double newCibleBalance = this.getBalanceTargetCompte(compteCible.getBalance(), amount);
        updateBalance(newSourceBalance, compteSource);
        updateBalance(newCibleBalance, compteCible);
    }

    @Override
    public Boolean checkIsEnable(Long numeroCompte) {

        Compte compte = compteRepository.findByNumeroCompte(numeroCompte);

        if (compte.getIsEnable() == true){
            return true;
        }else {
            return false;
        }

    }

    @Override
    public Compte findByNumeroCompte(Long numeroCompte) {
        return compteRepository.findByNumeroCompte(numeroCompte);
    }


    @Override
    public Boolean checkIsExist(Long numeroCompte){

        Compte compte = this.findByNumeroCompte(numeroCompte);
        if(compte == null){
            return false;
        }else {
            return true;
        }

    }

    //Retourne Class CompteIsEnableDto
    //Class CompteIsEnableDto contient l'etat de chaque compte et compte (cible ou source)
    @Override
    public CompteIsEnableDto checkIsEnableSourceAndCible(Long compteSourceId, Long compteCibleId){

        Compte compteSource = compteRepository.getById(compteSourceId);
        Compte compteCible = compteRepository.getById(compteCibleId);

        CompteIsEnableDto dto = new CompteIsEnableDto();

        if (compteSource.getIsEnable() == false){

            dto.setIsEnable(false);
            dto.setNumeroCompte(compteSource.getNumeroCompte());
            return dto;
        }if (compteCible.getIsEnable() == false){

            dto.setIsEnable(false);
            dto.setNumeroCompte(compteCible.getNumeroCompte());
            return dto;
        }else {

            dto.setIsEnable(true);
            dto.setNumeroCompte(null);
            return dto;
        }
    }


    @Override
    public Boolean isAmountAvailable(double amount, Long compteSourceId) {

        Compte compteSource = compteRepository.getById(compteSourceId);
        double balance = compteSource.getBalance() - amount;
        if (balance >= 0){
            return true;
        }else {
            return false;
        }
        //log.info("method to check wether the amount is available");
        //return (compteSource.getBalance() - amount) < 0;
    }

    @Override
    public void changeStatut(Compte compte){
        if (compte.getIsEnable() == true){

            compte.setIsEnable(false);
        }else {

            compte.setIsEnable(true);
        }
        compteRepository.save(compte);
    }
}
