package ci.nkagou.closedloop.repository;

import ci.nkagou.closedloop.model.CarteVehicule;

import javax.transaction.Transactional;

@Transactional
public interface CarteVehiculeRepository extends CarteBaseRepository<CarteVehicule> {
}
