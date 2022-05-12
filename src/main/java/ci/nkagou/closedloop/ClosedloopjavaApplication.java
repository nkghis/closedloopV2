package ci.nkagou.closedloop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ClosedloopjavaApplication {

	public static void main(String[] args) {

		ApplicationContext ctx = SpringApplication.run(ClosedloopjavaApplication.class, args);

		/*String p = "123";
		String password = EncrytedPasswordUtils.encrytePassword(p);

		System.out.println("===============DEBUT TRANSACTION=======================");
		UserRepository userRepository = ctx.getBean(UserRepository.class);
		AppUser admin = userRepository.save(new AppUser("admin",password,true));
		AppUser user = userRepository.save(new AppUser("user",password,true));
		AppUser access = userRepository.save(new AppUser("access",password,true));
		System.out.println("===============AJOUT CLIENT=======================");
		userRepository.findAll().forEach(u->System.out.println(u.getUserName()));
		System.out.println("Utilisateurs ajoutés avec succes");
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println(
				"===============AJOUT ROLE=======================");
		RoleRepository roleRepository = ctx.getBean(RoleRepository.class);
		AppRole roleadmin = roleRepository.save(new AppRole("ROLE_ADMIN"));
		AppRole roleaccess = roleRepository.save(new AppRole("ROLE_ACCES"));
		AppRole roleuser = roleRepository.save(new AppRole("ROLE_USER"));
		roleRepository.findAll().forEach(u->System.out.println(u.getRoleName()));
		System.out.println("Roles ajoutés avec succes");
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");

		System.out.println("===============AFFECTATION ROLE PAR USER=======================");
		UserRoleRepository userRoleRepository = ctx.getBean(UserRoleRepository.class);
		userRoleRepository.save(new UserRole(admin,roleadmin));
		userRoleRepository.save(new UserRole(admin,roleuser));
		userRoleRepository.save(new UserRole(admin,roleaccess));
		userRoleRepository.save(new UserRole(access,roleaccess));
		userRoleRepository.save(new UserRole(user,roleuser));
		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&FIN DE LA TRANSACTION&&&&&&&&&&&&&&&&&&&");*/
	}

}
