package ci.nkagou.closedloop.repository;

import ci.nkagou.closedloop.model.Client;
import ci.nkagou.closedloop.model.Marchand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Client findByClientName(String clientName);

    List<Client> findClientsByMarchand(Marchand marchand);
}
