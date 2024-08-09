package ci.nkagou.closedloop.repository;

import ci.nkagou.closedloop.model.Carte;
import ci.nkagou.closedloop.model.CartePersonne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
//@NoRepositoryBean
public interface CarteRepository  extends CarteBaseRepository<Carte> {

    //CartePersonne findByTypeCarte(String )

    Carte findBySerialNumber(String serialNumber);

    List<Carte> findByStatut(Boolean statut);


}
