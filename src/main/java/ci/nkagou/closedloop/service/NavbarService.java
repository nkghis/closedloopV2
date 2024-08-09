package ci.nkagou.closedloop.service;

import ci.nkagou.closedloop.dto.navbar.Navbar;
import ci.nkagou.closedloop.model.AppUser;

public interface NavbarService {

    Navbar displayNavbar(AppUser user);
}
