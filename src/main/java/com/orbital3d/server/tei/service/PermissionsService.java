package com.orbital3d.server.tei.service;

import com.orbital3d.server.tei.database.document.Permissions;
import com.orbital3d.server.tei.database.document.User;
import com.orbital3d.server.tei.database.repository.PermissionsRepository;

public interface PermissionsService extends CrudService<Permissions, PermissionsRepository>
{
	Permissions findByUser(User user);
}
