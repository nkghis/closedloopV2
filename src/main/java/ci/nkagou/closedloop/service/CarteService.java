package ci.nkagou.closedloop.service;

import ci.nkagou.closedloop.dto.carte.CarteDto;
import ci.nkagou.closedloop.dto.carte.CarteDtoOut;
import ci.nkagou.closedloop.dto.carte.CartePersonneDto;
import ci.nkagou.closedloop.dto.carte.CarteVehiculeDto;
import ci.nkagou.closedloop.model.Carte;
import ci.nkagou.closedloop.model.CartePersonne;
import ci.nkagou.closedloop.model.CarteVehicule;

import java.util.List;

public interface CarteService {

    List<Carte> all ();

    CarteDtoOut carteToDto (Carte carte);

    List<CarteDtoOut> listCartesToDto();

    List<CarteDtoOut> listCartesToDtoByListCarte(List<Carte> cartes);

    Carte getById (Long id);

    CarteDtoOut createCartePersonne(CartePersonneDto dto);
    CarteDtoOut createVehiculePersonne(CarteVehiculeDto dto);

    CarteDtoOut cartePersonneToDto(CartePersonne personne);

    List<CarteDtoOut> listCartePersonneToDto(List<CartePersonne> cartePersonnes);

    List<CarteDtoOut> listCarteVehiculeToDto(List<CarteVehicule> carteVehicules);

    CarteDtoOut carteVehiculeToDto(CarteVehicule vehicule);

    String checkCarteBySerialNumber(String serialNumber);

    Boolean getCarteStatut(Carte carte);

    List<Carte> getListStatusDisable();

    void updateCarteAfterCompteCreated(Carte carte);
    Carte findBySerialNumber(String serialNumber);

    List<CarteDto> listAllCartesToDto();

    List<CarteDto> listCarteDtoByListCarteDtoOut(List<CarteDtoOut> dtos);
}
