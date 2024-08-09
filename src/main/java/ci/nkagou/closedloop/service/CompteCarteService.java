package ci.nkagou.closedloop.service;

import ci.nkagou.closedloop.dto.carte.CarteDto;
import ci.nkagou.closedloop.dto.compteCarte.CompteCarteDto;
import ci.nkagou.closedloop.dto.compteCarte.CompteCarteDtoOut;
import ci.nkagou.closedloop.model.Client;
import ci.nkagou.closedloop.model.Compte;
import ci.nkagou.closedloop.model.CompteCarte;

import java.util.List;

public interface CompteCarteService {

    CompteCarteDtoOut createCompteCarte (CompteCarteDto dto);
    CompteCarteDtoOut compteCarteToDto(CompteCarte compteCarte);

    List<CompteCarte> all ();

    List<CompteCarteDtoOut> allComptesCarteByClient(Client client);
    CompteCarte getById (Long compteId);
    CompteCarte update (CompteCarte compteCarte);
    CompteCarte getByNumeroCompte(Long numeroCompte);
    void deleteById(Long compteId);

    Client getClientByCompte(Compte compte);

    CompteCarte getCompteCarteBySerialNumber(String serialNumber);

    List<CarteDto> listCarteSerialNumberByClient(Client client);

    List<CarteDto> listCarteSerialHaveAccount();

    List<CarteDto> convertListCompteCarteToListCarteDto(List<CompteCarte> compteCartes);
}
