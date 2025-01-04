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
package pt.psoft.g1.psoftg1.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.userdetails.UserDetails;


import pt.psoft.g1.psoftg1.dto.UserDTO;
import pt.psoft.g1.psoftg1.interfaces.GetID;
import lombok.Getter;
import lombok.Setter;

/**
 * Based on https://github.com/Yoh0xFF/java-spring-security-example
 *
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Document("users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class User implements GetID<String> {

	// database primary key
	@Id
	@UuidGenerator
	@Getter
	@Column(name="USER_ID")
	private String id;

	@Setter
	@Getter
	private boolean enabled = true;

	@Setter
    @Column(unique = true, /*updatable = false,*/ nullable = false)
	@Email
	@Getter
	@NotNull
	@NotBlank
	private String username;

	@Column(nullable = false)
	@Getter
	@NotNull
	@NotBlank
	private String password;

	@Getter
//	@Setter
	@Embedded
	private String name;

	@Getter
	private Role authority;

	protected User() {
		// for ORM only
	}

	/**
	 *
	 * @param username
	 * @param password
	 */
	public User(final String username, final String password, final String name) {
		this.username = username;
		this.name = name;
		setPassword(password);
	}

	/**
	 * factory method. since mapstruct does not handle protected/private setters
	 * neither more than one public constructor, we use these factory methods for
	 * helper creation scenarios
	 *
	 * @param username
	 * @param password
	 * @param name
	 * @return
	 */
	public static User newUser(final String username, final String password, final String name) {
		final var u = new User(username, password, name);
		return u;
	}

	/**
	 * factory method. since mapstruct does not handle protected/private setters
	 * neither more than one public constructor, we use these factory methods for
	 * helper creation scenarios
	 *
	 * @param username
	 * @param password
	 * @param name
	 * @param role
	 * @return
	 */
	public static User newUser(final String username, final String password, final String name, final String role) {
		final var u = new User(username, password, name);
		u.addAuthority(new Role(role));
		return u;
	}

	public void setPassword(final String password) {
		Password passwordCheck = new Password(password);

		this.password = passwordCheck.toString();
	}

    public void addAuthority(final Role r) {
		this.authority = r;
	}



	@Override
	public String getID() {
		return this.id;
	}

	public UserDTO toDTO() {
		return new UserDTO(this.id, this.username, this.password, this.name, this.authority);
	}
}
