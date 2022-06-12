package com.orbital3d.server.tei.controller.admin;

import java.util.Set;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orbital3d.server.tei.security.permissiom.SystemPermissions;
import com.orbital3d.server.tei.security.permissiom.TEIPermissions;

@RestController
public class PermissionController
{
	@Autowired
	private SystemPermissions systemPermissions;

	@GetMapping("/admin/perm/all")
	@RequiresPermissions(TEIPermissions.Administrator.PERMISSIONS)
	public Set<String> getAllPermissions() throws IllegalArgumentException, IllegalAccessException
	{
		return systemPermissions.allPermissions();
	}

}
