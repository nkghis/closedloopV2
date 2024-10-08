package ci.nkagou.closedloop.repository;


import ci.nkagou.closedloop.model.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CompteBaseRepository <T extends Compte> extends JpaRepository<T, Long> {

    public T findByTypeCompte (String typeCompte);
    //public T findByNumeroCompte()
}
