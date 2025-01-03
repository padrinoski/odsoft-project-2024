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
package pt.psoft.g1.psoftg1.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ResponseStatusException;
import pt.psoft.g1.psoftg1.dto.UserDTO;
import pt.psoft.g1.psoftg1.model.Role;
import pt.psoft.g1.psoftg1.model.User;
import pt.psoft.g1.psoftg1.services.UserService;

/**
 * Based on https://github.com/Yoh0xFF/java-spring-security-example
 *
 */
@RestController
@RequestMapping(path = "api/admin/users")
@RolesAllowed(Role.ADMIN)
@RequiredArgsConstructor
public class UserController {


	private final UserService userService;

	@PostMapping
	public ResponseEntity<UserDTO> create(@RequestBody User request) {
		try {
			final UserDTO user = userService.create(request);
			return new ResponseEntity<>(user, HttpStatus.CREATED);
		}
		catch( Exception e ) {
			throw new ResponseStatusException(HttpStatus.CONFLICT,e.getMessage());
		}
	}

	@PutMapping("{id}")
	public ResponseEntity<UserDTO> update(@PathVariable final String id, @RequestBody @Valid final User request) {
		try {
			final UserDTO user = userService.update(id, request);
			return new ResponseEntity<>(user, HttpStatus.CREATED);

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
		}
	}

	@DeleteMapping("{id}")
	public ResponseEntity<UserDTO> delete(@PathVariable final String id) {
		try {
			userService.delete(id);
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
		}
	}
}