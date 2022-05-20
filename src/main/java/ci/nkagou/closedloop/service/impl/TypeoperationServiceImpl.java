package ci.nkagou.closedloop.service.impl;

import ci.nkagou.closedloop.model.Typeoperation;
import ci.nkagou.closedloop.repository.TypeoperationRepository;
import ci.nkagou.closedloop.service.TypeoperationService;
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
public class TypeoperationServiceImpl implements TypeoperationService {

    private TypeoperationRepository typeoperationRepository;

    @Override
    public Typeoperation create(Typeoperation typeoperation) {
        return typeoperationRepository.save(typeoperation);
    }

    @Override
    public List<Typeoperation> all() {
        return typeoperationRepository.findAll();
    }

    @Override
    public Typeoperation getById(Long typeOperationId) {
        return typeoperationRepository.getById(typeOperationId);
    }

    @Override
    public Typeoperation update(Typeoperation typeoperation) {
        return typeoperationRepository.save(typeoperation);
    }

    @Override
    public void deleteById(Long typeOperationId) {
        typeoperationRepository.deleteById(typeOperationId);
    }
}
