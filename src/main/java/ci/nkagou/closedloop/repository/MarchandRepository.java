package ci.nkagou.closedloop.repository;


import ci.nkagou.closedloop.model.Marchand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarchandRepository extends JpaRepository<Marchand, Long> {

    Marchand findByMarchandName(String marchandName);
}
