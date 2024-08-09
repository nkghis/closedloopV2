package ci.nkagou.closedloop.repository;

import ci.nkagou.closedloop.model.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.transaction.Transactional;

@Transactional
public interface CompteRepository extends CompteBaseRepository<Compte> {

    Compte findByNumeroCompte(Long numeroCompte);


}
