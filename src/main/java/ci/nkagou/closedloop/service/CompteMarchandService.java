package ci.nkagou.closedloop.service;

import ci.nkagou.closedloop.dto.marchand.MarchandDto;
import ci.nkagou.closedloop.model.CompteMarchand;
import ci.nkagou.closedloop.model.Marchand;

import java.util.List;

public interface CompteMarchandService {

    CompteMarchand create (CompteMarchand compteMarchand);
    List<CompteMarchand> all ();
    CompteMarchand getById (Long compteId);
    CompteMarchand update (CompteMarchand compteMarchand);
    void deleteById(Long compteId);


}
