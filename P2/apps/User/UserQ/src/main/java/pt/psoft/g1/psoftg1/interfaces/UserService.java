package pt.psoft.g1.psoftg1.interfaces;

import pt.psoft.g1.psoftg1.common.domain.CreateUserEvent;
import pt.psoft.g1.psoftg1.common.domain.DeleteUserEvent;
import pt.psoft.g1.psoftg1.common.domain.UpdateUserEvent;
import pt.psoft.g1.psoftg1.dto.UserDTO;
import pt.psoft.g1.psoftg1.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<UserDTO> findByUsername(String username);
    Iterable<UserDTO> findByName(String name);
    Iterable<UserDTO> findAll();
    Optional<UserDTO> findById(String id);
    void createUser(CreateUserEvent event);
    void deleteById(DeleteUserEvent event);
    void updateUser(UpdateUserEvent event);
}
