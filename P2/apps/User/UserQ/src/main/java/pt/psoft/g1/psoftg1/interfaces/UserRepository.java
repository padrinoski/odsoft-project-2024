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
package pt.psoft.g1.psoftg1.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.NoRepositoryBean;
import pt.psoft.g1.psoftg1.dto.SearchUsersQuery;
import pt.psoft.g1.psoftg1.exceptions.NotFoundException;
import pt.psoft.g1.psoftg1.model.user.User;
import pt.psoft.g1.psoftg1.shared.services.Page;

/**
 *
 */
@NoRepositoryBean
public interface UserRepository{



	Optional<User> findById(String objectId);

	default User getById(final String id) {
		final Optional<User> maybeUser = findById(id);
		// throws 404 Not Found if the user does not exist or is not enabled
		return maybeUser.filter(User::isEnabled).orElseThrow(() -> new NotFoundException(User.class, id));
	}

	Optional<User> findByUsername(String username);

	List<User> searchUsers(Page page, SearchUsersQuery query);

	List<User> findByName(String name);
	List<User> findByNameContains(String name);

}
