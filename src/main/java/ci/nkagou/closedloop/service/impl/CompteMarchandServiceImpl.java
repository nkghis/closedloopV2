package ci.nkagou.closedloop.service.impl;
import ci.nkagou.closedloop.model.Marchand;
import java.time.LocalDateTime;

import ci.nkagou.closedloop.dto.compteMarchand.CompteMarchandDto;
import ci.nkagou.closedloop.dto.compteMarchand.CompteMarchandDtoOut;
import ci.nkagou.closedloop.model.CompteMarchand;
import ci.nkagou.closedloop.repository.CompteMarchandRepository;
import ci.nkagou.closedloop.repository.MarchandRepository;
import ci.nkagou.closedloop.service.CompteMarchandService;
import ci.nkagou.closedloop.utils.DateConvert;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class CompteMarchandServiceImpl implements CompteMarchandService {

    @Autowired
    private CompteMarchandRepository compteMarchandRepository;

    @Autowired
    private MarchandRepository marchandRepository;


    @Override
    public CompteMarchandDtoOut createCompteMarchand(CompteMarchandDto dto) {

        CompteMarchandDtoOut out = new CompteMarchandDtoOut();
        Marchand marchand = marchandRepository.getById(dto.getMarchandId());

        CompteMarchand compteMarchand = new CompteMarchand();
        compteMarchand.setDecouvert(0.0D);
        compteMarchand.setNumeroCompte(dto.getNumeroCompte());
        compteMarchand.setMarchand(marchand);
        compteMarchand.setBalance(0.0D);
        compteMarchand.setIsEnable(true);
        compteMarchand.setInitiationDate(LocalDateTime.now());
        compteMarchandRepository.save(compteMarchand);
        return out;
    }

    @Override
    public CompteMarchandDtoOut compteMarchandToDto(CompteMarchand compteMarchand) {

        CompteMarchandDtoOut out = new CompteMarchandDtoOut();
        out.setCompteId(compteMarchand.getCompteId());
        out.setTypeCompte("COMPTE MARCHAND");
        out.setNumeroCompte(compteMarchand.getNumeroCompte());
        out.setDecouvert(compteMarchand.getDecouvert());
        out.setBalance(compteMarchand.getBalance());
        out.setMarchand(compteMarchand.getMarchand().getMarchandName());
        out.setStatut(compteMarchand.getIsEnable() == true ? "ACTIVE" : "DESACTIVE");
        out.setDate(DateConvert.getStringDate(compteMarchand.getInitiationDate()));
        return out;
    }

    @Override
    public CompteMarchand create(CompteMarchand compteMarchand) {
        return compteMarchandRepository.save(compteMarchand);
    }

    @Override
    public List<CompteMarchandDtoOut> listCompteMarchandsToDto() {

        List<CompteMarchand> compteMarchands = compteMarchandRepository.findAll();
        List<CompteMarchandDtoOut> out = new ArrayList<>();

        for (CompteMarchand compteMarchand : compteMarchands){

            CompteMarchandDtoOut dtoOut = this.compteMarchandToDto(compteMarchand);
            out.add(dtoOut);

        }
        return out;
    }

    @Override
    public List<CompteMarchand> all() {
        return compteMarchandRepository.findAll();
    }

    @Override
    public CompteMarchand getById(Long compteId) {
        return compteMarchandRepository.getById(compteId);
    }

    @Override
    public CompteMarchand update(CompteMarchand compteMarchand) {
        return compteMarchandRepository.save(compteMarchand);
    }

    @Override
    public void deleteById(Long compteId) {
        compteMarchandRepository.deleteById(compteId);
    }
}
