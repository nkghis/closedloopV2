package ci.nkagou.closedloop.service;

import ci.nkagou.closedloop.dto.compteBanque.CompteBanqueDto;
import ci.nkagou.closedloop.dto.compteBanque.CompteBanqueDtoOut;
import ci.nkagou.closedloop.model.CompteBanque;

import java.util.List;

public interface CompteBanqueService {

    CompteBanqueDtoOut createCompteBanque(CompteBanqueDto dto);
    CompteBanqueDtoOut compteBanqueToDto(CompteBanque compteBanque);
    CompteBanque create (CompteBanque compteBanque);
    List<CompteBanque> all ();
    CompteBanque getById (Long compteId);
    CompteBanque update (CompteBanque compteBanque);
    void deleteById(Long compteId);
}
