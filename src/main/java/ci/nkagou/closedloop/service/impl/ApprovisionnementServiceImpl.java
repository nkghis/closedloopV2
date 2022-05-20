package ci.nkagou.closedloop.service.impl;

import ci.nkagou.closedloop.model.Approvisionnement;
import ci.nkagou.closedloop.repository.ApprovisionnementRepository;
import ci.nkagou.closedloop.service.ApprovisionnementService;
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
public class ApprovisionnementServiceImpl implements ApprovisionnementService {
    private ApprovisionnementRepository approvisionnementRepository;
    @Override
    public Approvisionnement create(Approvisionnement approvisionnement) {
        return approvisionnementRepository.save(approvisionnement);
    }

    @Override
    public List<Approvisionnement> all() {
        return approvisionnementRepository.findAll();
    }

    @Override
    public Approvisionnement getById(Long approvisionnementId) {
        return approvisionnementRepository.getById(approvisionnementId);
    }

    @Override
    public Approvisionnement update(Approvisionnement approvisionnement) {
        return approvisionnementRepository.save(approvisionnement);
    }

    @Override
    public void deleteById(Long approvisionnementId) {
        approvisionnementRepository.deleteById(approvisionnementId);
    }
}
