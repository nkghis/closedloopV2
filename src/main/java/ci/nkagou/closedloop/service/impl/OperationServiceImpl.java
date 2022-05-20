package ci.nkagou.closedloop.service.impl;

import ci.nkagou.closedloop.model.Operation;
import ci.nkagou.closedloop.repository.OperationRepository;
import ci.nkagou.closedloop.service.OperationService;
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
public class OperationServiceImpl implements OperationService {
    private OperationRepository operationRepository;
    @Override
    public Operation create(Operation operation) {
        return operationRepository.save(operation);
    }

    @Override
    public List<Operation> all() {
        return operationRepository.findAll();
    }

    @Override
    public Operation getById(Long operationId) {
        return operationRepository.getById(operationId);
    }

    @Override
    public Operation update(Operation operation) {
        return operationRepository.save(operation);
    }

    @Override
    public void deleteById(Long operationId) {
        operationRepository.deleteById(operationId);
    }
}
