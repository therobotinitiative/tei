package com.orbital3d.server.tei.controller.pages;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdministratorViewController
{

	@GetMapping("/admin")
	// To be changed into role
	@RequiresPermissions("tei:administrator")
	public String administratorView()
	{
		return "pages/admin";
	}
}
