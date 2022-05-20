package ci.nkagou.closedloop.service.impl;

import ci.nkagou.closedloop.dto.marchand.MarchandDto;
import ci.nkagou.closedloop.dto.marchand.MarchandDtoOut;
import ci.nkagou.closedloop.model.AppUser;
import ci.nkagou.closedloop.model.CompteMarchand;
import ci.nkagou.closedloop.model.Marchand;
import ci.nkagou.closedloop.repository.CompteMarchandRepository;
import ci.nkagou.closedloop.repository.MarchandRepository;
import ci.nkagou.closedloop.service.MarchandService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class MarchandServiceImpl implements MarchandService {

    private MarchandRepository marchandRepository;
    private CompteMarchandRepository compteMarchandRepository;

    @Override
    public Marchand create(Marchand marchand) {
        return marchandRepository.save(marchand);
    }

    @Override
    public List<Marchand> all() {
        return marchandRepository.findAll();
    }

    @Override
    public Marchand getById(Long marchandId) {
        return marchandRepository.getById(marchandId);
    }

    @Override
    public Marchand update(Marchand marchand) {
        return marchandRepository.save(marchand);
    }

    @Override
    public void deleteById(Long marchandId) {
        marchandRepository.deleteById(marchandId);
    }

    @Override
    public MarchandDtoOut marchandToDto(Marchand marchand) {

        MarchandDtoOut marchandDtoOut = new MarchandDtoOut();
        marchandDtoOut.setMarchandId(marchand.getMarchandId());
        marchandDtoOut.setMarchandName(marchand.getMarchandName());
        marchandDtoOut.setMarchandContact(marchand.getMarchandContact());
        return marchandDtoOut;
    }

    @Override
    public Marchand dtoToMarchand(MarchandDto marchandDto) {

        Marchand marchand = new Marchand();
        marchand.setMarchandName(marchandDto.getMarchandName().toUpperCase());
        marchand.setMarchandContact(marchandDto.getMarchandContact());
        return marchand;
    }

    @Override
    public List<MarchandDtoOut> listMarchandToDto() {

        List<Marchand> marchands = marchandRepository.findAll();

        List<MarchandDtoOut> marchandDtoOuts = new ArrayList<>();

        for (Marchand marchand : marchands)
        {
            MarchandDtoOut marchandDtoOut = this.marchandToDto(marchand);
            marchandDtoOuts.add(marchandDtoOut);
        }
        return marchandDtoOuts;
    }

    @Override
    public Marchand findByMarchandName(String marchandName) {
        return marchandRepository.findByMarchandName(marchandName);
    }

    @Override
    public Marchand getMarchandByUser(AppUser user) {
        CompteMarchand compteMarchand = compteMarchandRepository.getById(user.getCompte().getCompteId());
        Marchand marchand = compteMarchand.getMarchand();
        return marchand;
    }
}
