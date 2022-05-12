package ci.nkagou.closedloop.repository;

import ci.nkagou.closedloop.model.AppRole;
import ci.nkagou.closedloop.model.AppUser;
import ci.nkagou.closedloop.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    List<UserRole> findByAppUser(AppUser user);
    List<UserRole> findByAppRoleIsNot(AppRole role);
}
