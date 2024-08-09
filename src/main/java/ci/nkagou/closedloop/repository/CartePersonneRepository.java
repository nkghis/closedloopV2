package ci.nkagou.closedloop.repository;

import ci.nkagou.closedloop.model.CartePersonne;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface CartePersonneRepository extends CarteBaseRepository<CartePersonne> {

    //List<CartePersonne> findBy
}
