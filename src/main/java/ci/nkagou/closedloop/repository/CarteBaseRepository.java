package ci.nkagou.closedloop.repository;

import ci.nkagou.closedloop.model.Carte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CarteBaseRepository<T extends Carte> extends JpaRepository<T, Long> {

    public T findByTypeCarte(String typeCarte);
    public T findBySerialNumber(String serialNumber);
}
