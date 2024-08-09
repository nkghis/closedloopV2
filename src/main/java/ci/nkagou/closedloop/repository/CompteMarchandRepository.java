package ci.nkagou.closedloop.repository;

import ci.nkagou.closedloop.model.CompteMarchand;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface CompteMarchandRepository extends CompteBaseRepository<CompteMarchand> {


}
