package ci.nkagou.closedloop.repository;

import ci.nkagou.closedloop.model.CompteBanque;

import javax.transaction.Transactional;

@Transactional
public interface CompteBanqueRepository extends CompteBaseRepository<CompteBanque> {

}
