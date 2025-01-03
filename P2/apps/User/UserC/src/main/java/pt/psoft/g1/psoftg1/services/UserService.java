/*
 * Copyright (c) 2022-2024 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package pt.psoft.g1.psoftg1.services;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pt.psoft.g1.psoftg1.common.ApplicationType;
import pt.psoft.g1.psoftg1.common.Event;
import pt.psoft.g1.psoftg1.common.EventType;
import pt.psoft.g1.psoftg1.common.domain.CreateUserEvent;
import pt.psoft.g1.psoftg1.common.domain.DeleteUserEvent;
import pt.psoft.g1.psoftg1.common.domain.UpdateUserEvent;
import pt.psoft.g1.psoftg1.dto.UserDTO;
import pt.psoft.g1.psoftg1.model.Librarian;
import pt.psoft.g1.psoftg1.model.Reader;
import pt.psoft.g1.psoftg1.model.Role;
import pt.psoft.g1.psoftg1.model.User;
import pt.psoft.g1.psoftg1.repositories.EventRepositoryImpl;
import pt.psoft.g1.psoftg1.shared.IDGenerators.IDGenerator;

/**
 * Based on https://github.com/Yoh0xFF/java-spring-security-example
 *
 */
@Service
@Log4j2
public class UserService {

	private final IDGenerator idGenerator;

	private final EventRepositoryImpl eventRepository;
	private final EventServiceProducerImpl eventServiceProducer;

	public UserService(IDGenerator idGenerator, EventRepositoryImpl eventRepository, EventServiceProducerImpl eventServiceProducer) {
		this.idGenerator = idGenerator;
		this.eventRepository = eventRepository;
		this.eventServiceProducer = eventServiceProducer;
	}

	@Transactional
	public UserDTO create(User user) throws Exception {
		if(eventRepository.userExists(user.getID())) {
			throw new Exception("User with ID " + user.getID() + " already exists!");
		}

		if(user.getID() == null) {
			user = new User(user.getUsername(), user.getPassword(), user.getName());
		}

        if (user.getAuthorities().equals(Role.READER)) {
            user = Reader.newReader(user.getUsername(), user.getPassword(), user.getName());
        } else if (user.getAuthorities().equals(Role.LIBRARIAN)) {
            user = Librarian.newLibrarian(user.getUsername(), user.getPassword(), user.getName());
        } else {
            return null;
        }

		final CreateUserEvent eventMsg = new CreateUserEvent(user.getUsername(), user.getPassword(), user.getName(), user.getAuthorities());
		final Event event = new Event(EventType.USER_CREATE, eventMsg);
		eventServiceProducer.sendEvent(event, ApplicationType.USER);
		log.info("Create User: " + eventMsg);
		return user.toDTO();

	}

	@Transactional
	public UserDTO update(final String id, final User user) throws Exception {
		if(!eventRepository.userExists(id)) {
			throw new Exception("User with ID " + id + "not found!");
		}

		final UpdateUserEvent eventMsg = new UpdateUserEvent(user.getUsername(), user.getPassword(), user.getName(), user.getAuthorities());
		final Event event = new Event(EventType.USER_UPDATE, eventMsg);
		eventServiceProducer.sendEvent(event, ApplicationType.USER);
		log.info("Update User: " + eventMsg);
		return user.toDTO();
	}

	@Transactional
	public void delete(final String id) throws Exception {
		if(!eventRepository.userExists(id)) {
			throw new Exception("User with ID " + id + "not found!");
		}

		final DeleteUserEvent eventMsg = new DeleteUserEvent(id);
		final Event event = new Event(EventType.USER_DELETE, eventMsg);
		eventServiceProducer.sendEvent(event, ApplicationType.USER);
		log.info("Update User: " + eventMsg);
	}

}
