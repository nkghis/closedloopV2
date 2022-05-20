package ci.nkagou.closedloop.service.impl;

import ci.nkagou.closedloop.model.CompteBanque;
import ci.nkagou.closedloop.repository.CompteBanqueRepository;
import ci.nkagou.closedloop.service.CompteBanqueService;
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
public class CompteBanqueServiceImpl implements CompteBanqueService {

    private CompteBanqueRepository compteBanqueRepository;

    @Override
    public CompteBanque create(CompteBanque compteBanque) {
        return compteBanqueRepository.save(compteBanque);
    }

    @Override
    public List<CompteBanque> all() {
        return compteBanqueRepository.findAll();
    }

    @Override
    public CompteBanque getById(Long compteId) {
        return compteBanqueRepository.getById(compteId);
    }

    @Override
    public CompteBanque update(CompteBanque compteBanque) {
        return compteBanqueRepository.save(compteBanque);
    }

    @Override
    public void deleteById(Long compteId) {
        compteBanqueRepository.deleteById(compteId);
    }
}
