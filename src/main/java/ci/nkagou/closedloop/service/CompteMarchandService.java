package ci.nkagou.closedloop.service;

import ci.nkagou.closedloop.dto.compteMarchand.CompteMarchandDto;
import ci.nkagou.closedloop.dto.compteMarchand.CompteMarchandDtoOut;
import ci.nkagou.closedloop.dto.marchand.MarchandDto;
import ci.nkagou.closedloop.model.CompteMarchand;
import ci.nkagou.closedloop.model.Marchand;

import java.util.List;

public interface CompteMarchandService {

    CompteMarchandDtoOut createCompteMarchand (CompteMarchandDto compteMarchandDto);
    CompteMarchandDtoOut compteMarchandToDto(CompteMarchand compteMarchand);
    CompteMarchand create (CompteMarchand compteMarchand);
    List<CompteMarchandDtoOut> listCompteMarchandsToDto();
    List<CompteMarchand> all ();
    CompteMarchand getById (Long compteId);
    CompteMarchand update (CompteMarchand compteMarchand);
    void deleteById(Long compteId);


}
