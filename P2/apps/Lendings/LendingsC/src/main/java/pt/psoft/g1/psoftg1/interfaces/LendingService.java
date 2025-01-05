package pt.psoft.g1.psoftg1.interfaces;


import pt.psoft.g1.psoftg1.model.Lending;
import pt.psoft.g1.psoftg1.model.LendingDTO;

import java.util.List;
import java.util.Optional;


public interface LendingService {

    LendingDTO createLending(Lending lending) throws Exception;
    LendingDTO updateLending(String id, Lending lending) throws Exception;
    void deleteLending(String id) throws Exception;


}
