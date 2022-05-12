package ci.nkagou.closedloop.repository;

import ci.nkagou.closedloop.model.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<AppRole, Long> {

    AppRole findByRoleId (Long id);

    AppRole findByRoleName(String roleName);

    List<AppRole> findByRoleNameIsNot(String rolename);
}
