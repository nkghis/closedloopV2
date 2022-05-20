package ci.nkagou.closedloop.service.impl;

import ci.nkagou.closedloop.model.CompteClient;
import ci.nkagou.closedloop.repository.CompteClientRepository;
import ci.nkagou.closedloop.service.CompteClientService;
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
public class CompteClientServiceImpl implements CompteClientService {
    private CompteClientRepository compteClientRepository;
    @Override
    public CompteClient create(CompteClient compteClient) {
        return compteClientRepository.save(compteClient);
    }

    @Override
    public List<CompteClient> all() {
        return compteClientRepository.findAll();
    }

    @Override
    public CompteClient getById(Long compteId) {
        return compteClientRepository.getById(compteId);
    }

    @Override
    public CompteClient update(CompteClient compteClient) {
        return compteClientRepository.save(compteClient);
    }

    @Override
    public void deleteById(Long compteId) {
        compteClientRepository.deleteById(compteId);
    }
}
