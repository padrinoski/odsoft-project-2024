package pt.psoft.g1.psoftg1.services;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import pt.psoft.g1.psoftg1.common.domain.CreateUserEvent;
import pt.psoft.g1.psoftg1.common.domain.DeleteUserEvent;
import pt.psoft.g1.psoftg1.common.domain.UpdateUserEvent;
import pt.psoft.g1.psoftg1.dto.UserDTO;
import pt.psoft.g1.psoftg1.interfaces.UserRepository;
import pt.psoft.g1.psoftg1.interfaces.UserService;
import pt.psoft.g1.psoftg1.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public Optional<UserDTO> findByUsername(String username) {
		Optional<User> user = userRepository.findByUsername(username);
		return user.map(u -> new UserDTO(u.getUsername(), u.getPassword(), u.getName(), u.getAuthority()));
	}

	@Override
	public Iterable<UserDTO> findByName(String name) {
		Iterable<User> users = userRepository.findByName(name);
		List<UserDTO> userDTOs = new ArrayList<>();
		for (User user : users) {
			userDTOs.add(new UserDTO(user.getUsername(), user.getPassword(), user.getName(), user.getAuthority()));
		}
		return userDTOs;
	}

	@Override
	public Iterable<UserDTO> findAll() {
		Iterable<User> users =  userRepository.findAll();
		List<UserDTO> userDTOs = new ArrayList<>();
		for (User user : users) {
			userDTOs.add(new UserDTO(user.getUsername(), user.getPassword(), user.getName(), user.getAuthority()));
		}
		return userDTOs;
	}

	@Override
	public Optional<UserDTO> findById(String id) {
		Optional<User> user = userRepository.findById(id);
		return user.map(u -> new UserDTO(u.getUsername(), u.getPassword(), u.getName(), u.getAuthority()));
	}

	@Override
	public void createUser(CreateUserEvent event) {
	User user = new User(event.getUsername(), event.getName(), event.getPassword());
	if(event.getAuthority() != null) {
		user.setAuthority(event.getAuthority());
	}
	userRepository.save(user);
	log.debug("Persisted user: " + user);
	}

	@Override
	public void deleteById(DeleteUserEvent event) {
		Optional<User> optionalUser = userRepository.findById(event.getUserId());
		if (optionalUser.isPresent()) {
			userRepository.delete(optionalUser.get());
		} else {
			log.error("User with id " + event.getUserId() + " not found");
		}
	}

	@Override
	public void updateUser(UpdateUserEvent event) {
		Optional<User> optionalUser = userRepository.findById(event.getUserId());
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			user.setName(event.getName());
			user.setUsername(event.getUsername());
			user.setPassword(event.getPassword());
			userRepository.save(user);
		} else {
			log.error("User with id " + event.getUserId() + " not found");
		}
	}
}
