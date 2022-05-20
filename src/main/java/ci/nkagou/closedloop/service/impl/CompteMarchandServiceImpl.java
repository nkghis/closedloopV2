package ci.nkagou.closedloop.service.impl;

import ci.nkagou.closedloop.model.CompteMarchand;
import ci.nkagou.closedloop.repository.CompteMarchandRepository;
import ci.nkagou.closedloop.service.CompteMarchandService;
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
public class CompteMarchandServiceImpl implements CompteMarchandService {

    private CompteMarchandRepository compteMarchandRepository;

    @Override
    public CompteMarchand create(CompteMarchand compteMarchand) {
        return compteMarchandRepository.save(compteMarchand);
    }

    @Override
    public List<CompteMarchand> all() {
        return compteMarchandRepository.findAll();
    }

    @Override
    public CompteMarchand getById(Long compteId) {
        return compteMarchandRepository.getById(compteId);
    }

    @Override
    public CompteMarchand update(CompteMarchand compteMarchand) {
        return compteMarchandRepository.save(compteMarchand);
    }

    @Override
    public void deleteById(Long compteId) {
        compteMarchandRepository.deleteById(compteId);
    }
}
