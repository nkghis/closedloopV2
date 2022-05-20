package ci.nkagou.closedloop.service;

import ci.nkagou.closedloop.model.CartePersonne;

import java.util.List;

public interface CartePersonneService {

    CartePersonne create (CartePersonne cartePersonne);
    List<CartePersonne> all ();
    CartePersonne getById (Long carteId);
    CartePersonne update (CartePersonne cartePersonne);
    void deleteById(Long carteId);
}
