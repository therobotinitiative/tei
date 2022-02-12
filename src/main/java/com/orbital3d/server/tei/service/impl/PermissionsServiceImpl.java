package com.orbital3d.server.tei.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orbital3d.server.tei.database.document.Permissions;
import com.orbital3d.server.tei.database.document.User;
import com.orbital3d.server.tei.database.repository.PermissionsRepository;
import com.orbital3d.server.tei.service.PermissionsService;

@Service
public class PermissionsServiceImpl implements PermissionsService
{
	@Autowired
	private PermissionsRepository permissionsRepository;

	@Override
	public PermissionsRepository getRepository()
	{
		return permissionsRepository;
	}

	@Override
	public Permissions findByUser(User user)
	{
		return permissionsRepository.findByUser(user);
	}

}
