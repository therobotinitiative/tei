package com.orbital3d.server.tei.controller.pages;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.orbital3d.server.tei.security.permissiom.TEIPermissions;

/**
 * Template creation page controller.
 * 
 * @author msiren
 *
 */
@Controller
public class TemplateController
{
	/**
	 * @return Dashboard page template name
	 */
	@GetMapping("/templates/dashboard")
	@RequiresPermissions(TEIPermissions.TEI_VIEW_DASHBOARD)
	public String createDashboardPage()
	{
		return "pages/dashboard";
	}

	/**
	 * @param model
	 * @return View page template name
	 */
	@GetMapping("/templates/view")
	@RequiresPermissions(TEIPermissions.TEI_VIEW_VIEW)
	public String viewTemplate(Model model)
	{
		return "pages/view";
	}

	/**
	 * @param model
	 * @return Template page template name
	 */
	@GetMapping("/templates/template")
	@RequiresPermissions(TEIPermissions.TEI_VIEW_TEMPLATE)
	public String templateTemplate(Model model)
	{
		return "pages/template";
	}

	/**
	 * @param model
	 * @return Send page template name
	 */
	@GetMapping("/templates/send")
	@RequiresPermissions(TEIPermissions.TEI_VIEW_SEND)
	public String sendTemplate(Model model)
	{
		return "pages/send";
	}

	/**
	 * @param model
	 * @return Administrator page template name
	 */
	@GetMapping("/templates/admin")
	@RequiresPermissions(TEIPermissions.ADMINISTRATOR)
	public String admininistratorTemplate(Model model)
	{
		return "pages/admin";
	}
}
