package ci.nkagou.closedloop.service.impl;
import java.time.LocalDateTime;

import ci.nkagou.closedloop.dto.compte.CompteIsEnableDto;
import ci.nkagou.closedloop.dto.operation.RechargementAndTransfertDto;
import ci.nkagou.closedloop.model.*;

import ci.nkagou.closedloop.dto.operation.OperationDto;
import ci.nkagou.closedloop.dto.operation.OperationDtoOut;
import ci.nkagou.closedloop.repository.CompteRepository;
import ci.nkagou.closedloop.repository.OperationRepository;
import ci.nkagou.closedloop.repository.TypeoperationRepository;
import ci.nkagou.closedloop.service.*;
import ci.nkagou.closedloop.utils.DateConvert;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class OperationServiceImpl implements OperationService {
    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private CompteRepository compteRepository;

    @Autowired
    private TypeoperationRepository typeoperationRepository;

    @Autowired
    private CompteService compteService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private CompteClientService compteClientService;

    @Autowired
    private CompteMarchandService compteMarchandService;

    @Autowired
    private TypeoperationService typeoperationService;

    public static String typeOperationRechargement = "RECHARGEMENT";
    public static String typeOperationTransfert = "TRANSFERT";

    @Override
    public OperationDtoOut createOperation(OperationDto dto, AppUser user, Typeoperation typeoperation) {

        Operation operation = new Operation();

        //Typeoperation typeoperation = typeoperationRepository.getById(dto.getTypeOperation());

        String reference = UUID.randomUUID().toString();
        operation.setAmount(dto.getAmount());
        operation.setInitiationDate(LocalDateTime.now());
        operation.setReference(reference);
        operation.setSourceAccountId(dto.getSourceAccountId());
        operation.setTargetAccountId(dto.getTargetAccountId());
        operation.setTypeoperation(typeoperation);
        operation.setAppUser(user);
        operationRepository.save(operation);
        OperationDtoOut out = this.operationToDto(operation);
        return out;
    }

    @Override
    public OperationDtoOut operationToDto(Operation operation) {


        Compte  compteSource = compteRepository.getById(operation.getSourceAccountId());
        Compte  compteCible = compteRepository.getById(operation.getTargetAccountId());

        OperationDtoOut out = new OperationDtoOut();
        out.setOperationId(operation.getOperationId());
        out.setReference(operation.getReference());
        out.setAmount(operation.getAmount());
        out.setCompteSource(compteSource.getNumeroCompte());
        out.setNameCompteSource(compteService.getTitulaireCompte(compteSource));
        out.setCompteCible(compteCible.getNumeroCompte());
        out.setNameCompteCible(compteService.getTitulaireCompte(compteCible));
        out.setTypeOperation(operation.getTypeoperation().getTypeoperationNom());
        out.setDateOperation(DateConvert.getStringDate(operation.getInitiationDate()));
        return out;
    }

    @Override
    public List<OperationDtoOut> listOperationToDto(List<Operation> operations) {

        List<OperationDtoOut> out = new ArrayList<>();

        for (Operation operation :operations){

            OperationDtoOut dtoOut = this.operationToDto(operation);

            out.add(dtoOut);
        }

        return out;
    }

    @Override
    public Operation create(Operation operation) {
        return operationRepository.save(operation);
    }

    @Override
    public List<Operation> all() {
        return operationRepository.findAll();
    }

    @Override
    public Operation getById(Long operationId) {
        return operationRepository.getById(operationId);
    }

    @Override
    public Operation update(Operation operation) {
        return operationRepository.save(operation);
    }

    @Override
    public void deleteById(Long operationId) {
        operationRepository.deleteById(operationId);
    }

    @Override
    public OperationDtoOut makeOperation(/*Compte compteSource, Compte compteCible,*/ Typeoperation typeOperation, AppUser user, OperationDto dto) {
        OperationDtoOut out = this.createOperation(dto, user, typeOperation);
        double amount = dto.getAmount();
        Compte compteSource = compteRepository.getById(dto.getSourceAccountId());
        Compte compteCible = compteRepository.getById(dto.getTargetAccountId());
        compteService.updateComptesAfterOperation(compteSource, compteCible, amount);
        return out;
    }


    //List des operations en fonction d'un id de compte
    @Override
    public List<OperationDtoOut> listOperationToDtoByCompte(Long compteSourceId, Long compteCibleId){

        List<Operation> operations = operationRepository.findBySourceAccountIdOrTargetAccountId(compteSourceId, compteCibleId);
        List<OperationDtoOut> out = this.listOperationToDto(operations);
        return out;
    }




    //List des operations des clients appartenant a un marchand.
    @Override
    public List<OperationDtoOut> listOperationToDtoCompteClientByMarchand(Marchand marchand){
        List<Client> clients = clientService.listClientsByMarchand(marchand);

        List<CompteClient> compteClients = compteClientService.listCompteClientByListClient(clients);

        List<OperationDtoOut> out = new ArrayList<>();

        for (CompteClient compteClient : compteClients){
            //List operation compte client
            List<OperationDtoOut>  dtos = this.listOperationToDtoCompteClient(compteClient.getCompteId());
            for (OperationDtoOut dto : dtos ){
                out.add(dto);
            }
        }
        return out;
    }

    @Override
    public List<OperationDtoOut> listOperationToDtoCompteClient(Long compteId){

       String nameAppro = "APPROVISIONNEMENT";

        //List<Operation> operations = operationRepository.findBySourceAccountIdOrTargetAccountIdAndTypeoperationNot(compteId, compteId, typeoperation.getTypeoperationId());
        List<Operation> operations = operationRepository.findBySourceAccountIdOrTargetAccountId(compteId, compteId);

       /* List<Operation> operations1 = new ArrayList<>();

        for (Operation operation : operations){

            if (!operation.getTypeoperation().getTypeoperationNom().equals(nameAppro)){

                operations1.add(operation);
            }

        }
        List<OperationDtoOut> out = this.listOperationToDto(operations1);*/
        List<OperationDtoOut> out = this.listOperationToDto(operations);

        //remove duplicate
        out = this.removeDuplicateOperationDtoOut(out);


        return out;
    }



    @Override
    public List<OperationDtoOut> listOperationToDtoMarchandAndMarchandClient(Long compteId){

        //Compte Marchand
        CompteMarchand compteMarchand = compteMarchandService.getById(compteId);
        //Marchand
        Marchand marchand = compteMarchand.getMarchand();

        //List des operation du marchand connecté
        List<OperationDtoOut> operationMarchand = this.listOperationToDtoByCompte(compteId, compteId);

        ////List des operation des clients du marchand connecté
        List<OperationDtoOut> operationClientByMarchand = this.listOperationToDtoCompteClientByMarchand(marchand);

        //Init list
        List<OperationDtoOut> out = new ArrayList<>();

        //Fusion des deux listes operation marchand et operation des client du marchand  en une liste
        out.addAll(operationMarchand);
        out.addAll(operationClientByMarchand);

        //remove duplicate value

        out = this.removeDuplicateOperationDtoOut(out);
        /*Set<OperationDtoOut>sOut = new HashSet<OperationDtoOut>();
        sOut.addAll(out);
        out = new ArrayList<OperationDtoOut>();
        out.addAll(sOut);*/

        return out;
    }



    @Override
    public List<OperationDtoOut> listRechargementToDtoClient(Long compteId){

        Typeoperation typeoperation = typeoperationService.findByTypeoperationNom(typeOperationRechargement);
        List<Operation> operations = operationRepository.findBySourceAccountIdOrTargetAccountIdAndTypeoperation(compteId, compteId, typeoperation);
        List<OperationDtoOut> out = this.listOperationToDto(operations);
        out = this.removeDuplicateOperationDtoOut(out);
        return out;

    }

    @Override
    public List<OperationDtoOut> removeDuplicateOperationDtoOut(List<OperationDtoOut> out){

        Set<OperationDtoOut>sOut = new HashSet<OperationDtoOut>();
        sOut.addAll(out);
        out = new ArrayList<OperationDtoOut>();
        out.addAll(sOut);
        return out;
    }


    //Method for make transfert or rechargemrnt return Dto
    @Override
    public RechargementAndTransfertDto makeRechargementOrTransfert(Long sourceAccountId, Long targetAccountId, double amount, Typeoperation  typeoperation, AppUser user){
        //Init
        RechargementAndTransfertDto out = new RechargementAndTransfertDto();

        //Check account enable
        CompteIsEnableDto isEnableDto = compteService.checkIsEnableSourceAndCible(sourceAccountId, targetAccountId);

        if (isEnableDto.getIsEnable() == true){

            //Check Account cible balance available for operation
            Boolean sourceBalanceAvailable = compteService.isAmountAvailable(amount, sourceAccountId);
            if (sourceBalanceAvailable == true){

                //Make operation
                OperationDto dto = new OperationDto();
                dto.setAmount(amount);
                dto.setSourceAccountId(sourceAccountId);
                dto.setTargetAccountId(targetAccountId);
                dto.setUser(user);
                dto.setInitiationDate(LocalDateTime.now());


                //Make operation
                OperationDtoOut operationDtoOut = this.makeOperation( typeoperation,user, dto);

                String type = "";
                //Check if operation is transfert or recharment for custom message
                if (typeoperation.getTypeoperationNom().equals(typeOperationRechargement)){

                    //rechargement
                    type = "Rechargement";


                }else {

                    //transfert
                    type = "Transfert";

                }

                //Custom message success
                String message = "Le " + type +" vers le compte :  " + operationDtoOut.getCompteCible() +" d'un montant de : "+ amount +" a été validée avec succès";
                out.setMessage(message);
                out.setTypeMessage("succes");

            }else {

                //erreur solde insuffisant
                String message = "Désolé, le solde du compte : "+ user.getCompte().getNumeroCompte() +" est insuffisant pour effectuer cette opération.";
                out.setMessage(message);
                out.setTypeMessage("erreur");

            }
        }else {
            //erreur compte desactivée
            String message = "Désolé cette operation ne peut aboutir car le compte ayant le numero : " + isEnableDto.getNumeroCompte()+" est desactivé";
            out.setMessage(message);
            out.setTypeMessage("erreur");

        }

        return out;

    }

   /* @Override
    public List<Operation>  listOperationByTypeoperation(Typeoperation typeoperation){

        List<Operation> operations = operationRepository.findByTypeoperation(typeoperation);
        //List<OperationDtoOut> out = this.listOperationToDto(operations);
        return operations;
    }*/

    @Override
    public List<OperationDtoOut> listTransfertToDtoClient(AppUser user){

        Typeoperation typeoperation = typeoperationService.findByTypeoperationNom(typeOperationTransfert);

        //List<Operation> operations = operationRepository.findBySourceAccountIdOrTargetAccountIdAndTypeoperation(compteId, compteId, typeoperation);
       // List<Operation> operations = operationRepository.findByTypeoperationAndSourceAccountIdOrTargetAccountId( typeoperation, compteId, compteId);

        List<Operation> operations = operationRepository.findByAppUserAndTypeoperation(user, typeoperation);

        List<OperationDtoOut> out = this.listOperationToDto(operations);
        out = this.removeDuplicateOperationDtoOut(out);
        return out;

    }
}
;