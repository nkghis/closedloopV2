package ci.nkagou.closedloop.service;

import ci.nkagou.closedloop.model.CompteCarte;

import java.util.List;

public interface CompteCarteService {

    CompteCarte create (CompteCarte compteCarte);
    List<CompteCarte> all ();
    CompteCarte getById (Long compteId);
    CompteCarte update (CompteCarte compteCarte);
    void deleteById(Long compteId);
}
