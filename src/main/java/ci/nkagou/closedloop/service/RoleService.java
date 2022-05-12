package ci.nkagou.closedloop.service;

import ci.nkagou.closedloop.model.AppRole;
import ci.nkagou.closedloop.model.AppUser;

import java.util.List;

public interface RoleService {

    AppRole create (AppRole role);
    AppRole findByRoleName (String roleName);
    List<AppRole> all();
    List<AppRole> allSortByRoleProperty(String roleProperty);
    List<AppRole> findByRoleNameIsNot(String roleName);
    AppRole getById(Long id);
    Boolean deleteById (Long id);

    List<AppRole> getRolesByUser(AppUser user);
    List<String> getRoleNames (List<AppRole> roles);
}
