package ci.nkagou.closedloop.service;


import ci.nkagou.closedloop.model.AppUser;

import java.util.List;

public interface UserService  {

    AppUser create (AppUser user);
    List<AppUser> all();
    List<AppUser> allSortByRoleProperty(String roleProperty);
    List<AppUser> getUserListWithRoleInString(List<AppUser> Users);
    AppUser findByUserName(String userName);
    Boolean existsByUserName(String userName);
}
