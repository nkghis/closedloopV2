package ci.nkagou.closedloop.service.impl;

import ci.nkagou.closedloop.model.CarteVehicule;
import ci.nkagou.closedloop.repository.CarteVehiculeRepository;
import ci.nkagou.closedloop.service.CarteVehiculeService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class CarteVehiculeServiceImpl implements CarteVehiculeService {

    private CarteVehiculeRepository carteVehiculeRepository;

    @Override
    public CarteVehicule create(CarteVehicule carteVehicule) {
        return carteVehiculeRepository.save(carteVehicule);
    }

    @Override
    public List<CarteVehicule> all() {
        return carteVehiculeRepository.findAll();
    }

    @Override
    public CarteVehicule getById(Long carteId) {
        return carteVehiculeRepository.getById(carteId);
    }

    @Override
    public CarteVehicule update(CarteVehicule carteVehicule) {
        return carteVehiculeRepository.save(carteVehicule);
    }

    @Override
    public void deleteById(Long carteId) {

        carteVehiculeRepository.deleteById(carteId);
    }
}
