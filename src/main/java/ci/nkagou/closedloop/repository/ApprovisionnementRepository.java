package ci.nkagou.closedloop.repository;

import ci.nkagou.closedloop.model.AppUser;
import ci.nkagou.closedloop.model.Approvisionnement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApprovisionnementRepository extends JpaRepository<Approvisionnement, Long > {

    List<Approvisionnement> findByAppUser(AppUser user);
    List<Approvisionnement> findByStatut(int statut);
    List<Approvisionnement> findByAppUserAndStatut(AppUser user, int statut);

    List<Approvisionnement> findByStatutAndCompte_TypeCompte(int statut, String typeCompte);
}
