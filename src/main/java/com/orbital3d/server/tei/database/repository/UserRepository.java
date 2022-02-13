package com.orbital3d.server.tei.database.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.orbital3d.server.tei.database.document.User;

/**
 * User repository actions extending the basic CRUD actions.
 * 
 * @author msiren
 *
 */
public interface UserRepository extends MongoRepository<User, ObjectId>
{
	/**
	 * Finds user by its user name.
	 * 
	 * @param userName User name used for finding user.
	 * @return {@link User} object if found.
	 */
	User findByUserName(String userName);

	/**
	 * @param UserName User name to check
	 * @return true id the user name already exists
	 */
	boolean existsByUserName(String UserName);
}
