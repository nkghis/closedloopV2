package ci.nkagou.closedloop.service.impl;

import ci.nkagou.closedloop.dto.navbar.Navbar;
import ci.nkagou.closedloop.model.AppUser;
import ci.nkagou.closedloop.service.ApprovisionnementService;
import ci.nkagou.closedloop.service.NavbarService;
import ci.nkagou.closedloop.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class NavbarServiceImpl implements NavbarService {
    @Autowired
    private ApprovisionnementService approvisionnementService;

    @Autowired
    private UserService userService;

    @Override
    public Navbar displayNavbar(AppUser user) {


        String adminRoleName ="ROLE_ADMIN";
        Boolean isAdmin = userService.hasRole(user, adminRoleName);
        Navbar navbar = new Navbar();
        if (isAdmin == true){
            navbar.setBalance(0);
            navbar.setSizeApproForValidation(0);

        }else {

            double balance = user.getCompte().getBalance();
            int sizeApproForValidation = approvisionnementService.countDemandeforValidation(user/*, user.getCompte().getTypeCompte()*/);
            navbar.setBalance(balance);
            navbar.setSizeApproForValidation(sizeApproForValidation);
        }


        return navbar;
    }
}
