package ci.nkagou.closedloop.service;

import ci.nkagou.closedloop.dto.marchand.MarchandDto;
import ci.nkagou.closedloop.dto.marchand.MarchandDtoOut;
import ci.nkagou.closedloop.model.AppRole;
import ci.nkagou.closedloop.model.AppUser;
import ci.nkagou.closedloop.model.Marchand;

import java.util.List;

public interface MarchandService {

    Marchand create (Marchand marchand);
    List<Marchand> all ();
    Marchand getById (Long marchandId);
    Marchand update (Marchand marchand);
    void deleteById(Long marchandId);

    MarchandDtoOut marchandToDto(Marchand marchand);
    Marchand dtoToMarchand(MarchandDto marchandDto);
    List<MarchandDtoOut> listMarchandToDto();
    Marchand findByMarchandName(String marchandName);
    Marchand getMarchandByUser(AppUser user);
}
