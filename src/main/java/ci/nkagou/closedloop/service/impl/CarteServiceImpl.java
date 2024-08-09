package ci.nkagou.closedloop.service.impl;

import ci.nkagou.closedloop.dto.carte.CarteDto;
import ci.nkagou.closedloop.dto.carte.CarteDtoOut;
import ci.nkagou.closedloop.dto.carte.CartePersonneDto;
import ci.nkagou.closedloop.dto.carte.CarteVehiculeDto;
import ci.nkagou.closedloop.model.Carte;
import ci.nkagou.closedloop.model.CartePersonne;
import ci.nkagou.closedloop.model.CarteVehicule;
import ci.nkagou.closedloop.model.Client;
import ci.nkagou.closedloop.repository.CarteBaseRepository;
import ci.nkagou.closedloop.repository.CartePersonneRepository;
import ci.nkagou.closedloop.repository.CarteRepository;
import ci.nkagou.closedloop.repository.CarteVehiculeRepository;
import ci.nkagou.closedloop.service.CarteService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class CarteServiceImpl implements CarteService {

    @Autowired
    private CarteRepository carteRepository;

    @Autowired
    private CartePersonneRepository cartePersonneRepository;

    @Autowired
    private CarteVehiculeRepository carteVehiculeRepository;


    //private CarteBaseRepository carteBaseRepository;

    @Override
    public List<Carte> all() {
        return carteRepository.findAll();
    }

    @Override
    public CarteDtoOut carteToDto(Carte carte) {


        CarteDtoOut dto = new CarteDtoOut();

        if (carte.getTypeCarte().equals("pec")){

            CartePersonne cartePersonne = cartePersonneRepository.getById(carte.getCarteId());
            dto.setPersonneName(cartePersonne.getPersonneName());
            dto.setPersonneSurname(cartePersonne.getPersonneSurname());
            dto.setImmatriculation("N/A");
            dto.setCouleur("N/A");
            dto.setMarque("N/A");
            dto.setTypeCarte("PERSONNE");
        }

        if (carte.getTypeCarte().equals("vec"))
        {
            CarteVehicule carteVehicule = carteVehiculeRepository.getById(carte.getCarteId());
            dto.setPersonneName("N/A");
            dto.setPersonneSurname("N/A");
            dto.setImmatriculation(carteVehicule.getImmatriculation());
            dto.setCouleur(carteVehicule.getCouleur());
            dto.setMarque(carteVehicule.getMarque());
            dto.setTypeCarte("VEHICULE");


        }

        dto.setCarteId(carte.getCarteId());
        //dto.setTypeCarte(carte.getTypeCarte().equals("pec") ? "PERSONNE" : "VEHICULE");
        dto.setSerialNumber(carte.getSerialNumber());
        dto.setOrderNumber(carte.getOrderNumber());
        dto.setStatut(carte.isStatut()== true ? "AFFECTEE" : "NON AFFECTEE");

        // Custom format if needed
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        dto.setDate(carte.getInitiationDate().format(formatter));


        return dto;
    }

    @Override
    public List<CarteDtoOut> listCartesToDto() {

        List<CarteDtoOut> carteDtoOuts = new ArrayList<>();
        List<Carte> cartes = carteRepository.findAll();

        for (Carte carte : cartes){

            CarteDtoOut dto = new CarteDtoOut();
            dto = this.carteToDto(carte);

            carteDtoOuts.add(dto);
        }
        return carteDtoOuts;
    }


    @Override
    public List<CarteDtoOut> listCartesToDtoByListCarte(List<Carte> cartes) {

        List<CarteDtoOut> carteDtoOuts = new ArrayList<>();


        for (Carte carte : cartes){

            CarteDtoOut dto = new CarteDtoOut();
            dto = this.carteToDto(carte);

            carteDtoOuts.add(dto);
        }
        return carteDtoOuts;
    }



    @Override
    public Carte getById(Long id) {
        return carteRepository.getById(id);
    }

    @Override
    public CarteDtoOut createCartePersonne(CartePersonneDto dto) {

        CartePersonne personne = new CartePersonne();
        personne.setOrderNumber(dto.getOrderNumber());
        personne.setSerialNumber(dto.getSerialNumber());
        personne.setPersonneName(dto.getPersonneName());
        personne.setPersonneSurname(dto.getPersonneSurname());
        personne.setStatut(false);
        personne.setInitiationDate(LocalDateTime.now());
        cartePersonneRepository.save(personne);
        CarteDtoOut out = this.cartePersonneToDto(personne);
        return out;
    }

    @Override
    public CarteDtoOut createVehiculePersonne(CarteVehiculeDto dto) {

        CarteVehicule vehicule = new CarteVehicule();
        vehicule.setOrderNumber(dto.getOrderNumber());
        vehicule.setSerialNumber(dto.getSerialNumber());
        vehicule.setImmatriculation(dto.getImmatriculation());
        vehicule.setMarque(dto.getMarque());
        vehicule.setCouleur(dto.getCouleur());
        vehicule.setStatut(false);
        vehicule.setInitiationDate(LocalDateTime.now());
        carteVehiculeRepository.save(vehicule);
        CarteDtoOut out = this.carteVehiculeToDto(vehicule);
        return out;
    }

    @Override
    public CarteDtoOut cartePersonneToDto(CartePersonne personne) {

        CarteDtoOut dto = new CarteDtoOut();

        dto.setCarteId(personne.getCarteId());
        dto.setOrderNumber(personne.getOrderNumber());
        dto.setSerialNumber(personne.getSerialNumber());
        dto.setPersonneName(personne.getPersonneName());
        dto.setPersonneSurname(personne.getPersonneSurname());
        dto.setImmatriculation("N/A");
        dto.setCouleur("N/A");
        dto.setMarque("N/A");
        dto.setTypeCarte("PERSONNE");
        dto.setStatut(personne.isStatut()== true ? "AFFECTEE" : "NON AFFECTEE");

        // Custom format if needed
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        dto.setDate(personne.getInitiationDate().format(formatter));

        return dto;
    }

    @Override
    public List<CarteDtoOut> listCartePersonneToDto(List<CartePersonne> cartePersonnes){

        List<CarteDtoOut> out = new ArrayList<>();
        for (CartePersonne cartePersonne : cartePersonnes){

            CarteDtoOut dto = this.cartePersonneToDto(cartePersonne);
            out.add(dto);
        }
        return out;
    }



    @Override
    public CarteDtoOut carteVehiculeToDto(CarteVehicule vehicule) {

        CarteDtoOut dto = new CarteDtoOut();

        dto.setCarteId(vehicule.getCarteId());
        dto.setOrderNumber(vehicule.getOrderNumber());
        dto.setSerialNumber(vehicule.getSerialNumber());
        dto.setImmatriculation(vehicule.getImmatriculation());
        dto.setCouleur(vehicule.getCouleur());
        dto.setMarque(vehicule.getMarque());
        dto.setPersonneName("N/A");
        dto.setPersonneSurname("N/A");
        dto.setTypeCarte("VEHICULE");
        dto.setStatut(vehicule.isStatut()== true ? "AFFECTEE" : "NON AFFECTEE");

        // Custom format if needed
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        dto.setDate(vehicule.getInitiationDate().format(formatter));

        return dto;


    }

    @Override
    public List<CarteDtoOut> listCarteVehiculeToDto(List<CarteVehicule>  carteVehicules){

        List<CarteDtoOut> out = new ArrayList<>();
        for (CarteVehicule carteVehicule : carteVehicules){

            CarteDtoOut dto = this.carteVehiculeToDto(carteVehicule);
            out.add(dto);
        }
        return out;
    }

    @Override
    public String checkCarteBySerialNumber(String serialNumber) {

        Carte carte = carteRepository.findBySerialNumber(serialNumber);

        String out = null;

        if (carte != null){

            Boolean isEnable = this.getCarteStatut(carte);

            if (isEnable == true){

                out =  "enable";
            }else {
                out =  "disable";
            }


        } else {

            out = "notfound";

        }

        return out;
        //return null;
    }

    @Override
    public Boolean getCarteStatut(Carte carte){

        if (carte.isStatut() == true){
            return true;
        }else {

            return false;
        }
    }

    @Override
    public List<Carte> getListStatusDisable() {
        return carteRepository.findByStatut(false);
    }

    @Override
    public void updateCarteAfterCompteCreated(Carte carte) {

        carte.setStatut(true);
        carteRepository.save(carte);
    }

    @Override
    public Carte findBySerialNumber(String serialNumber) {
        return carteRepository.findBySerialNumber(serialNumber);
    }

    //Method return DTO carte for use in form new rechargement simple to get all carte(Personne, vehicuel) serial number wiht reference
    @Override
    public List<CarteDto> listAllCartesToDto(){

        List<CarteDtoOut> cartePersonneDtoOut = this.listCartePersonneToDto(cartePersonneRepository.findAll());
        List<CarteDtoOut> carteVehiculeDtoOut = this.listCarteVehiculeToDto(carteVehiculeRepository.findAll());
        List<CarteDtoOut> dtos = new ArrayList<>();
        dtos.addAll(cartePersonneDtoOut);
        dtos.addAll(carteVehiculeDtoOut);

        List<CarteDto> out = this.listCarteDtoByListCarteDtoOut(dtos);

       /* for (CarteDtoOut dto : dtos){

            CarteDto c = new CarteDto();
            c.setNumeroSerie(dto.getSerialNumber());

            //Check if carte egal a carte personne
            if (dto.getTypeCarte().equals("PERSONNE")){

                c.setRef1(dto.getPersonneSurname());
                c.setRef2(dto.getPersonneName());
            }else{
                c.setRef1(dto.getImmatriculation());
                c.setRef2(dto.getMarque());
            }

            out.add(c);

        }*/

        return out;
    }

    @Override
    public List<CarteDto> listCarteDtoByListCarteDtoOut(List<CarteDtoOut> dtos){


        List<CarteDto> out = new ArrayList<>();

        for (CarteDtoOut dto : dtos){

            CarteDto c = new CarteDto();
            c.setNumeroSerie(dto.getSerialNumber());

            //Check if carte egal a carte personne
            if (dto.getTypeCarte().equals("PERSONNE")){

                c.setRef1(dto.getPersonneSurname());
                c.setRef2(dto.getPersonneName());
            }else{
                c.setRef1(dto.getImmatriculation());
                c.setRef2(dto.getMarque());
            }

            out.add(c);

        }

        return out;
    }







}
