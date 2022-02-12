package com.orbital3d.server.tei.controller.admin;

import java.util.Set;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.orbital3d.server.tei.database.document.Permissions;
import com.orbital3d.server.tei.database.document.User;
import com.orbital3d.server.tei.security.permissiom.SystemPermissions;
import com.orbital3d.server.tei.security.permissiom.TEIPermissions;
import com.orbital3d.server.tei.service.PermissionsService;
import com.orbital3d.server.tei.service.UserService;

@RestController
public class PermissionController
{
	private static final Logger LOG = LoggerFactory.getLogger(PermissionController.class);

	@Autowired
	private SystemPermissions systemPermissions;

	@Autowired
	private UserService userService;

	@Autowired
	private PermissionsService permissionsService;

	@GetMapping("/admin/perm/all")
	@RequiresPermissions(TEIPermissions.ADMINISTRATOR)
	public Set<String> getAllPermissions() throws IllegalArgumentException, IllegalAccessException
	{
		return systemPermissions.allPermissions();
	}

	@PutMapping(path = "/admin/perm/{username}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@RequiresPermissions(TEIPermissions.ADMINISTRATOR)
	public void updateUserPermissions(@PathVariable(name = "username", required = true) String userName, @RequestBody Set<String> permissions)
	{
		LOG.trace("Updating permissions for {}", userName);

		User user = userService.findUser(userName);
		Permissions userPermissions = permissionsService.findByUser(user);
		if (userPermissions == null)
		{
			userPermissions = new Permissions();
			userPermissions.setUser(user);
		}
		userPermissions.setPermissions(permissions);
		permissionsService.save(userPermissions);
	}
}
