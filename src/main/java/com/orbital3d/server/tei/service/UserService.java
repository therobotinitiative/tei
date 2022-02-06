package com.orbital3d.server.tei.service;

import com.orbital3d.server.tei.database.document.User;
import com.orbital3d.server.tei.database.repository.UserRepository;

/**
 * User service interface. Extending {@link CrudService} actions.
 * 
 * @author msiren
 *
 */
public interface UserService extends CrudService<User, UserRepository>
{
	/**
	 * Find user by user name.
	 * 
	 * @param userName User name
	 * @return {@link User} object
	 */
	User findUser(String userName);
}
