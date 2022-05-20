package ci.nkagou.closedloop.service.impl;

import ci.nkagou.closedloop.model.AppRole;
import ci.nkagou.closedloop.model.AppUser;
import ci.nkagou.closedloop.model.UserRole;
import ci.nkagou.closedloop.repository.RoleRepository;
import ci.nkagou.closedloop.repository.UserRepository;
import ci.nkagou.closedloop.service.RoleService;
import ci.nkagou.closedloop.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleService roleService;


    @Override
    public AppUser create(AppUser user) {

        log.info("Enregistrer utilisateur");
        return userRepository.save(user);
    }

    @Override
    public List<AppUser> all() {
        return userRepository.findAll();
    }

    @Override
    public List<AppUser> allSortByRoleProperty(String roleProperty) {
        return userRepository.findAll(Sort.by(Sort.Direction.DESC,roleProperty));
    }

    @Override
    public List<AppUser> getUserListWithRoleInString(List<AppUser> Users) {
        //Init Collection
        Collection<AppRole> appRoles;
        for (AppUser u : Users){
            //Get roles for a user
            appRoles = u.getRoles();
            //ini array list
            ArrayList<String> arrayList = new ArrayList<>();

            for (AppRole a : appRoles){
                // Get User Role Name
                String s = a.getRoleName();

                //Add in list array
                arrayList.add(s);
            }

            // Convert Array to string without bracket
            String role = Arrays.toString(arrayList.toArray()).replace("[", "").replace("]", "");

            //Set String to mesroles;
            u.setMesroles(role);
        }

        return Users;
    }

    @Override
    public AppUser findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public Boolean existsByUserName(String userName) {
        return userRepository.existsByUserName(userName);
    }

    @Override
    public Boolean hasRole(AppUser user, String roleName) {
        List<AppRole> roles = roleService.getRolesByUser(user);
        List<String> stringRoles = new ArrayList<String>();
        for (AppRole r : roles){
            String rname = r.getRoleName();
            stringRoles.add(rname);
        }
        Boolean role = false;
        Boolean roleContain = stringRoles.contains(roleName);
        if (roleContain){
            role = true;
        }else {
            role = false;
        }

        return role;
    }


}
