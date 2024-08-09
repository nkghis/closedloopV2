package ci.nkagou.closedloop;

import ci.nkagou.closedloop.model.*;
import ci.nkagou.closedloop.repository.*;
import ci.nkagou.closedloop.utils.BCrypt;
import ci.nkagou.closedloop.utils.EncrytedPasswordUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDateTime;

@SpringBootApplication
public class ClosedloopjavaApplication {

	public static void main(String[] args) {


		ApplicationContext ctx = SpringApplication.run(ClosedloopjavaApplication.class, args);

		/*UserRepository userRepository = ctx.getBean(UserRepository.class);
		MarchandRepository marchandRepository = ctx.getBean(MarchandRepository.class);
		Marchand marchand	= marchandRepository.getById(1L);
		AppUser user = userRepository.getById(1L);
		String l = "test";*/
		String p = "123";
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
		AppRole roleclient = roleRepository.save(new AppRole("ROLE_CLIENT"));
		AppRole rolemarchand = roleRepository.save(new AppRole("ROLE_MARCHAND"));
		AppRole rolebanque = roleRepository.save(new AppRole("ROLE_BANQUE"));
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
		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&FIN DE LA TRANSACTION&&&&&&&&&&&&&&&&&&&");

		System.out.println("===============DEBUT TYPE OPERATION=======================");

		TypeoperationRepository typeoperationRepository = ctx.getBean(TypeoperationRepository.class);
		Typeoperation typeoperation1 = typeoperationRepository.save( new Typeoperation("PAIEMENT"));
		Typeoperation typeoperation2 = typeoperationRepository.save( new Typeoperation("RECHARGEMENT"));
		Typeoperation typeoperation3 = typeoperationRepository.save( new Typeoperation("TRANSFERT"));
		Typeoperation typeoperation4 = typeoperationRepository.save( new Typeoperation("APPROVISIONNEMENT"));

		System.out.println("===============AJOUT TYPE OPERATION=======================");

		System.out.println("===============DEBUT MARCHAND=======================");

		MarchandRepository marchandRepository = ctx.getBean(MarchandRepository.class);
		Marchand marchandShellVallons = marchandRepository.save( new Marchand("SHELL LES VALLONS", "+225 0101010101"));
		Marchand marchandTotalKoumassiZone = marchandRepository.save( new Marchand("TOTAL KOUMASSI", "+225 0707070707"));
		System.out.println("===============AJOUT MARCHAND=======================");

		System.out.println("===============DEBUT CLIENT=======================");
		ClientRepository clientRepository = ctx.getBean((ClientRepository.class));

		Client clientICS = clientRepository.save(new Client("IVOIRE CARTES SYSTEMES", "+225 0202020202", marchandShellVallons));
		Client clientSOCOCE = clientRepository.save(new Client("SOCOCE GROS", "+225 0303030303", marchandShellVallons));
		Client clientBANAMUR = clientRepository.save(new Client("BANAMUR", "+225 0404040404", marchandTotalKoumassiZone));
		Client clientSOTICI = clientRepository.save(new Client("SOTICI", "+225 0404040404", marchandTotalKoumassiZone));
		System.out.println("===============AJOUT CLIENT=======================");

		System.out.println("===============DEBUT COMPTE CLIENT=======================");

		CompteClientRepository compteClientRepository = ctx.getBean((CompteClientRepository.class));
		LocalDateTime dateTime = LocalDateTime.now();
		Long numeroCompteICS = Long.parseLong("100001");
		Long numeroCompteSOCOCE = Long.parseLong("100002");
		Long numeroCompteBANAMUR = Long.parseLong("100003");
		Long numeroCompteSOTICI = Long.parseLong("100004");
		CompteClient cclICS = compteClientRepository.save(new CompteClient(50000, true, dateTime, clientICS, numeroCompteICS));
		CompteClient cclSOCOCE = compteClientRepository.save(new CompteClient(50000, true, dateTime, clientSOCOCE, numeroCompteSOCOCE));
		CompteClient cclBANAMUR = compteClientRepository.save(new CompteClient(50000, true, dateTime, clientBANAMUR, numeroCompteBANAMUR));
		CompteClient cclSOTICI = compteClientRepository.save(new CompteClient(50000, true, dateTime, clientSOTICI, numeroCompteSOTICI));

		System.out.println("===============AJOUT COMPTE CLIENT=======================");

		System.out.println("===============DEBUT COMPTE MARCHAND=======================");

		CompteMarchandRepository compteMarchandRepository = ctx.getBean((CompteMarchandRepository.class));

		Long numeroCompteSHELLVALLONS = Long.parseLong("200001");
		Long numeroCompteTOTALKOUMASSIZONE = Long.parseLong("200002");
		CompteMarchand cmaShellVallons = compteMarchandRepository.save(new CompteMarchand(numeroCompteSHELLVALLONS,200000, true, dateTime, 50000,  marchandShellVallons ));
		CompteMarchand cmaTotalShellKoumassiZone = compteMarchandRepository.save(new CompteMarchand(numeroCompteTOTALKOUMASSIZONE,200000, true, dateTime, 50000,  marchandTotalKoumassiZone ));
		System.out.println("===============AJOUT COMPTE MARCHAND=======================");

		System.out.println("===============DEBUT COMPTE BANQUE=======================");

		CompteBanqueRepository compteBanqueRepository = ctx.getBean((CompteBanqueRepository.class));

		Long numeroCompteBGFI = Long.parseLong("300001");
		CompteBanque cbaBGFI = compteBanqueRepository.save(new CompteBanque(1000000, true, dateTime, "BGFI BANQUE", numeroCompteBGFI));

		System.out.println("===============AJOUT COMPTE BANQUE=======================");




		System.out.println("===============DEBUT CARTE PERSONNE=======================");

		CartePersonneRepository cartePersonneRepository = ctx.getBean(CartePersonneRepository.class);
		CartePersonne cartePersonneICS1 = cartePersonneRepository.save( new CartePersonne("A6C978C1", "000001", true, dateTime, "YAO", "Franck"));
		CartePersonne cartePersonneICS2 = cartePersonneRepository.save( new CartePersonne("B6C978C2", "000002", true, dateTime, "KONE", "YASMINE"));
		CartePersonne cartePersonneICS3 = cartePersonneRepository.save( new CartePersonne("C6C978C3", "000003", true, dateTime, "YO", "KOFFI PAUL"));
		CartePersonne cartePersonneICS4 = cartePersonneRepository.save( new CartePersonne("D6C978C4", "000004", true, dateTime, "KRA", "MARIE-HERMINE"));
		CartePersonne cartePersonneSOCOCE1 = cartePersonneRepository.save( new CartePersonne("E6C978C5", "000005", true, dateTime, "KI", "ALAIN"));
		CartePersonne cartePersonneSOCOCE2 = cartePersonneRepository.save( new CartePersonne("F6C978C6", "000006", true, dateTime, "GUELA", "MARIETTE"));
		System.out.println("===============AJOUT CARTE PERSONNE=======================");

		System.out.println("===============DEBUT CARTE VEHICULE=======================");
		CarteVehiculeRepository carteVehiculeRepository = ctx.getBean(CarteVehiculeRepository.class);
		CarteVehicule carteVehiculeICS = carteVehiculeRepository.save(new CarteVehicule("G6C978C7", "000007", true, dateTime, "5959JG01", "MERCEDES" ,"Blanc"));
		CarteVehicule carteVehiculeSOCOCE1 = carteVehiculeRepository.save(new CarteVehicule("H6C978C8", "000008", true, dateTime, "2021HG01", "FORD" ,"Noir"));
		CarteVehicule carteVehiculeSOCOCE2 = carteVehiculeRepository.save(new CarteVehicule("I6C978C9", "000009", true, dateTime, "4563KA01", "BMW" ,"Rouge"));
		System.out.println("===============AJOUT CARTE VEHICULE=======================");

		System.out.println("===============DEBUT COMPTE CARTE=======================");

		CompteCarteRepository compteCarteRepository = ctx.getBean(CompteCarteRepository.class);

		String pinCompte1 = "1111";
		String pinCompte2 = "2222";
		String pinCompte3 = "3333";
		String pinCompte4 = "4444";
		String pinCompte5 = "5555";
		String pinCompte6 = "6666";
		String pinCompte7 = "7777";
		String pinCompte8 = "8888";
		String pinCompte9 = "9999";



		//PinService pinService = ctx.getBean(PinService.class);

		System.out.println("****HACHAGE CODE PIN****");
		String pinHashCompte1 = BCrypt.hashpw(pinCompte1, BCrypt.gensalt(12));
		String pinHashCompte2 = BCrypt.hashpw(pinCompte2, BCrypt.gensalt(12));
		String pinHashCompte3 = BCrypt.hashpw(pinCompte3, BCrypt.gensalt(12));
		String pinHashCompte4 = BCrypt.hashpw(pinCompte4, BCrypt.gensalt(12));
		String pinHashCompte5 = BCrypt.hashpw(pinCompte5, BCrypt.gensalt(12));
		String pinHashCompte6 = BCrypt.hashpw(pinCompte6, BCrypt.gensalt(12));
		String pinHashCompte7= BCrypt.hashpw(pinCompte7, BCrypt.gensalt(12));
		String pinHashCompte8 = BCrypt.hashpw(pinCompte8, BCrypt.gensalt(12));
		String pinHashCompte9 = BCrypt.hashpw(pinCompte9, BCrypt.gensalt(12));
		System.out.println("****FIN HACHAGE CODE PIN****");



		Long numeroCompteCarteICS1 = Long.parseLong("400001");
		Long numeroCompteCarteICS2 = Long.parseLong("400002");
		Long numeroCompteCarteICS3 = Long.parseLong("400003");
		Long numeroCompteCarteICS4 = Long.parseLong("400004");
		Long numeroCompteCarteICS5 = Long.parseLong("400005");


		Long numeroCompteCarteSOCOCE1 = Long.parseLong("400006");
		Long numeroCompteCarteSOCOCE2 = Long.parseLong("400007");
		Long numeroCompteCarteSOCOCE3 = Long.parseLong("400008");
		Long numeroCompteCarteSOCOCE4 = Long.parseLong("400009");

		CompteCarte ccaICS1 = compteCarteRepository.save(new CompteCarte(numeroCompteCarteICS1, 3000, true, dateTime, pinHashCompte1, clientICS,cartePersonneICS1 ));
		CompteCarte ccaICS2 = compteCarteRepository.save(new CompteCarte(numeroCompteCarteICS2, 3000, true, dateTime, pinHashCompte2, clientICS,cartePersonneICS2 ));
		CompteCarte ccaICS3 = compteCarteRepository.save(new CompteCarte(numeroCompteCarteICS3, 3000, true, dateTime, pinHashCompte3, clientICS,cartePersonneICS3 ));
		CompteCarte ccaICS4 = compteCarteRepository.save(new CompteCarte(numeroCompteCarteICS4, 3000, true, dateTime, pinHashCompte4, clientICS,cartePersonneICS4 ));
		CompteCarte ccaICS5 = compteCarteRepository.save(new CompteCarte(numeroCompteCarteICS5,3000, true, dateTime, pinHashCompte5, clientICS,carteVehiculeICS ));
		CompteCarte ccaSOCOCE1 = compteCarteRepository.save(new CompteCarte(numeroCompteCarteSOCOCE1, 3000, true, dateTime, pinHashCompte6, clientSOCOCE,cartePersonneSOCOCE1 ));
		CompteCarte ccaSOCOCE2 = compteCarteRepository.save(new CompteCarte(numeroCompteCarteSOCOCE2, 3000, true, dateTime, pinHashCompte7, clientSOCOCE,cartePersonneSOCOCE2 ));
		CompteCarte ccaSOCOCE3 = compteCarteRepository.save(new CompteCarte(numeroCompteCarteSOCOCE3, 3000, true, dateTime, pinHashCompte8, clientSOCOCE,carteVehiculeSOCOCE1 ));
		CompteCarte ccaSOCOCE4 = compteCarteRepository.save(new CompteCarte(numeroCompteCarteSOCOCE4, 3000, true, dateTime, pinHashCompte9, clientSOCOCE,carteVehiculeSOCOCE2 ));

		System.out.println("===============AJOUT COMPTE CARTE=======================");

		System.out.println("===============DEBUT USER - COMPTE -ROLES =======================");

			System.out.println("*** ADD USER ***");

			AppUser userICS = userRepository.save(new AppUser("ics", password, true, cclICS));
			AppUser userSOCOCE = userRepository.save(new AppUser("sococe", password, true, cclSOCOCE));
			AppUser userSotici = userRepository.save(new AppUser("sotici", password, true, cclSOTICI));
			AppUser userShellVallons = userRepository.save(new AppUser("shell", password, true, cmaShellVallons));
			AppUser userTotalKoumassiZone= userRepository.save(new AppUser("total", password, true, cmaTotalShellKoumassiZone));
			AppUser userBGFI= userRepository.save(new AppUser("bgfi", password, true, cbaBGFI));

			System.out.println("*** GET ROLES ***");
			AppRole roleClient = roleRepository.findByRoleName("ROLE_CLIENT");
			AppRole roleMarchand = roleRepository.findByRoleName("ROLE_MARCHAND");
			AppRole roleBanque = roleRepository.findByRoleName("ROLE_BANQUE");

		System.out.println("*** ADD USER_ROLES ***");
		userRoleRepository.save(new UserRole(userICS,roleClient));
		userRoleRepository.save(new UserRole(userSOCOCE,roleClient));
		userRoleRepository.save(new UserRole(userShellVallons,roleMarchand));
		userRoleRepository.save(new UserRole(userTotalKoumassiZone,roleMarchand));
		userRoleRepository.save(new UserRole(userBGFI,roleBanque));

		System.out.println("===============DEBUT USER - COMPTE - ROLES=======================");
	}

}
