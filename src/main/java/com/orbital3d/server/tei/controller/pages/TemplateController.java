package com.orbital3d.server.tei.controller.pages;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
	public String createDashboardPage()
	{
		return "pages/dashboard";
	}

	/**
	 * @param model
	 * @return View page template name
	 */
	@GetMapping("/templates/view")
	public String viewTemplate(Model model)
	{
		return "pages/view";
	}

	/**
	 * @param model
	 * @return Template page template name
	 */
	@GetMapping("/templates/template")
	public String templateTemplate(Model model)
	{
		return "pages/template";
	}

	/**
	 * @param model
	 * @return Send page template name
	 */
	@GetMapping("/templates/send")
	public String sendTemplate(Model model)
	{
		return "pages/send";
	}
}
