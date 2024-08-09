package ci.nkagou.closedloop.repository;

import ci.nkagou.closedloop.model.AppUser;
import ci.nkagou.closedloop.model.Operation;
import ci.nkagou.closedloop.model.Typeoperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface OperationRepository extends JpaRepository<Operation, Long> {

    List<Operation> findBySourceAccountIdOrTargetAccountId(Long compteSourceId, Long compteCibleId);
    List<Operation> findBySourceAccountIdOrTargetAccountIdAndTypeoperation(Long compteSourceId, Long compteCibleId, Typeoperation typeOperation);
    List<Operation> findByTypeoperation(Typeoperation typeoperation);
    List<Operation> findByTypeoperationAndSourceAccountIdOrTargetAccountId(Typeoperation typeoperation,Long compteSourceId, Long compteCibleId );

    List<Operation> findByAppUserAndTypeoperation(AppUser user, Typeoperation typeoperation);
    //Operation findBy
}
