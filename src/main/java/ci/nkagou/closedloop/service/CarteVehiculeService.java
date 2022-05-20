package ci.nkagou.closedloop.service;

import ci.nkagou.closedloop.model.CarteVehicule;

import java.util.List;

public interface CarteVehiculeService {

    CarteVehicule create (CarteVehicule carteVehicule);
    List<CarteVehicule> all ();
    CarteVehicule getById (Long carteId);
    CarteVehicule update (CarteVehicule carteVehicule);
    void deleteById(Long carteId);
}
