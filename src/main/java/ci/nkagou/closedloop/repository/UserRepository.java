package ci.nkagou.closedloop.repository;

import ci.nkagou.closedloop.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AppUser, Long> {

    AppUser findByUserName(String userName);
    Boolean existsByUserName(String userName);
}
