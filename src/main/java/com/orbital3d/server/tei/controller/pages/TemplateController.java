package com.orbital3d.server.tei.controller.pages;

import org.springframework.stereotype.Controller;
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
	 * @return Template page template name
	 */
	@GetMapping("/template")
	public String createTemplatePage()
	{
		return "pages/templates";
	}
}
