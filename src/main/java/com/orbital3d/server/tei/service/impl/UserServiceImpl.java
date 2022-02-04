package com.orbital3d.server.tei.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orbital3d.server.tei.database.entity.User;
import com.orbital3d.server.tei.database.repository.UserRepository;
import com.orbital3d.server.tei.service.UserService;

/**
 * Implementation of {@link UserService}.
 * 
 * @author msiren
 *
 */
@Service
public class UserServiceImpl implements UserService
{
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserRepository getRepository()
	{
		return userRepository;
	}

	@Override
	public User finfUser(String userName)
	{
		return getRepository().findByUserName(userName);
	}

}
