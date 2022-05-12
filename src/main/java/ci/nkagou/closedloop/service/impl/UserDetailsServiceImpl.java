package ci.nkagou.closedloop.service.impl;

import ci.nkagou.closedloop.dao.AppRoleDAO;
import ci.nkagou.closedloop.dao.AppUserDAO;
import ci.nkagou.closedloop.model.AppUser;
import ci.nkagou.closedloop.service.RoleService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AppUserDAO appUserDAO;

    @Autowired
    private AppRoleDAO appRoleDAO;

    private RoleService roleService;


    public UserDetailsServiceImpl(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    @Transactional

    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        AppUser appUser = this.appUserDAO.findUserAccount(userName);

        if (appUser == null) {
            System.out.println("Utilisateur non trouvé! " + userName);
            throw new UsernameNotFoundException("Utilisateur " + userName + " n'a pas été trouvé dans la base de données");
        }

        System.out.println("Utilisateur trouvé: " + appUser);

        // [ROLE_USER, ROLE_ADMIN,..]



        List<String> roleNames = this.appRoleDAO.getRoleNames(appUser.getUserId());

        //List<AppRole> roles = appUser.getRoles();
        /*List<AppRole> roles = roleService.getRolesByUser(appUser);
        List<String> roleNames = roleService.getRoleNames(roles);*/

        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
        if (roleNames != null) {
            for (String role : roleNames) {
                // ROLE_USER, ROLE_ADMIN,..
                GrantedAuthority authority = new SimpleGrantedAuthority(role);
                grantList.add(authority);
            }
        }

        UserDetails userDetails = (UserDetails) new User(appUser.getUserName(), //
                appUser.getEncrytedPassword(), grantList);

        LocalDateTime currentLocalDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String formattedDateTime = currentLocalDateTime.format(dateTimeFormatter);
        System.out.println("l'utilisateur [" + userName + " ] s'est conneté avec succes à : " +formattedDateTime);

        return userDetails;
    }
}
