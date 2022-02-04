package com.orbital3d.server.tei.database.repository;

import java.math.BigInteger;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.orbital3d.server.tei.database.entity.User;

/**
 * User repository actions extending the basic CRUD actions.
 * 
 * @author msiren
 *
 */
public interface UserRepository extends MongoRepository<User, BigInteger>
{
	/**
	 * Finds user by its user name.
	 * 
	 * @param userName User name used for finding user.
	 * @return {@link User} object if found.
	 */
	User findByUserName(String userName);
}
