package ci.nkagou.closedloop.service.impl;

import ci.nkagou.closedloop.model.CompteCarte;
import ci.nkagou.closedloop.repository.CompteCarteRepository;
import ci.nkagou.closedloop.service.CompteCarteService;
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
public class CompteCarteServiceImpl implements CompteCarteService {

    private CompteCarteRepository compteCarteRepository;
    @Override
    public CompteCarte create(CompteCarte compteCarte) {
        return compteCarteRepository.save(compteCarte);
    }

    @Override
    public List<CompteCarte> all() {
        return compteCarteRepository.findAll();
    }

    @Override
    public CompteCarte getById(Long compteId) {
        return compteCarteRepository.getById(compteId);
    }

    @Override
    public CompteCarte update(CompteCarte compteCarte) {
        return compteCarteRepository.save(compteCarte);
    }

    @Override
    public void deleteById(Long compteId) {
        compteCarteRepository.deleteById(compteId);
    }
}
