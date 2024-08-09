package ci.nkagou.closedloop.service;

import ci.nkagou.closedloop.dto.compte.CompteIsEnableDto;
import ci.nkagou.closedloop.model.Compte;

public interface CompteService {

    Compte getById(Long compteId);

    String getCompteType(String typeCompte);

    String getTitulaireCompte(Compte compte);

    void updateBalance(double balance, Compte compte);

    double getBalanceSourceCompte(double sourceBalance, double amount);

    double getBalanceTargetCompte(double TargetBalance, double amount);

    void updateComptesAfterOperation(Compte compteSource, Compte compteCible, double amount);

    Boolean checkIsEnable(Long compteId);

    Compte findByNumeroCompte(Long numeroCompte);

    Boolean checkIsExist(Long numeroCompte);

    CompteIsEnableDto checkIsEnableSourceAndCible(Long compteSourceId, Long compteCibleId);

    Boolean isAmountAvailable(double amount, Long compteSourceId);

    void changeStatut(Compte compte);

    /*  Boolean checkCompteEnable(Compte compte);*/
}
