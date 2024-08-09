package ci.nkagou.closedloop.service.impl;
import java.time.LocalDateTime;

import ci.nkagou.closedloop.dto.compteBanque.CompteBanqueDto;
import ci.nkagou.closedloop.dto.compteBanque.CompteBanqueDtoOut;
import ci.nkagou.closedloop.model.CompteBanque;
import ci.nkagou.closedloop.repository.CompteBanqueRepository;
import ci.nkagou.closedloop.service.CompteBanqueService;
import ci.nkagou.closedloop.utils.DateConvert;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class CompteBanqueServiceImpl implements CompteBanqueService {

    @Autowired
    private CompteBanqueRepository compteBanqueRepository;



    @Override
    public CompteBanqueDtoOut createCompteBanque(CompteBanqueDto dto) {
        CompteBanque compteBanque = new CompteBanque();
        compteBanque.setBanqueNom(dto.getBanqueNom());
        compteBanque.setNumeroCompte(dto.getNumeroCompte());
        compteBanque.setBalance(0.0D);
        compteBanque.setIsEnable(true);
        compteBanque.setInitiationDate(LocalDateTime.now());
        compteBanqueRepository.save(compteBanque);
        CompteBanqueDtoOut out = this.compteBanqueToDto(compteBanque);
        return out;
    }

    @Override
    public CompteBanqueDtoOut compteBanqueToDto(CompteBanque compteBanque) {

        CompteBanqueDtoOut out = new CompteBanqueDtoOut();
        out.setCompteId(compteBanque.getCompteId());
        out.setTypeCompte("COMPTE BANQUE");
        out.setNumeroCompte(compteBanque.getNumeroCompte());
        out.setBalance(compteBanque.getBalance());
        out.setBanqueNom(compteBanque.getBanqueNom());
        out.setStatut(compteBanque.getIsEnable() == true ? "ACTIVE" : "DESACTIVE");
        out.setDate(DateConvert.getStringDate(compteBanque.getInitiationDate()));


        return out;
    }

    @Override
    public CompteBanque create(CompteBanque compteBanque) {
        return compteBanqueRepository.save(compteBanque);
    }

    @Override
    public List<CompteBanque> all() {
        return compteBanqueRepository.findAll();
    }

    @Override
    public CompteBanque getById(Long compteId) {
        return compteBanqueRepository.getById(compteId);
    }

    @Override
    public CompteBanque update(CompteBanque compteBanque) {
        return compteBanqueRepository.save(compteBanque);
    }

    @Override
    public void deleteById(Long compteId) {
        compteBanqueRepository.deleteById(compteId);
    }
}
