package ci.nkagou.closedloop.service.impl;

import ci.nkagou.closedloop.model.CartePersonne;
import ci.nkagou.closedloop.repository.CartePersonneRepository;
import ci.nkagou.closedloop.service.CartePersonneService;
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
public class CartePersonneServiceImpl implements CartePersonneService {

    private CartePersonneRepository cartePersonneRepository;
    @Override
    public CartePersonne create(CartePersonne cartePersonne) {
        return cartePersonneRepository.save(cartePersonne);
    }

    @Override
    public List<CartePersonne> all() {
        return cartePersonneRepository.findAll();
    }

    @Override
    public CartePersonne getById(Long carteId) {
        return cartePersonneRepository.getById(carteId);
    }

    @Override
    public CartePersonne update(CartePersonne cartePersonne) {
        return cartePersonneRepository.save(cartePersonne);
    }

    @Override
    public void deleteById(Long carteId) {
        cartePersonneRepository.deleteById(carteId);
    }
}
