package ci.nkagou.closedloop.service;

import ci.nkagou.closedloop.model.AppRole;
import ci.nkagou.closedloop.model.AppUser;
import ci.nkagou.closedloop.model.UserRole;

import java.util.List;

public interface UserRoleService {

    List<UserRole> all ();
    UserRole create (UserRole userRole);
    List<UserRole> findByAppRoleIsNot(AppRole role);
    List<UserRole> findByAppUser(AppUser user);

}
