package com.orbital3d.server.tei.controller.pages;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Index page controller.
 * 
 * @author msiren
 *
 */
@Controller
public class IndexController
{
	/**
	 * @param model
	 * @return Index page template name
	 */
	@GetMapping("/tei")
	public String index(Model model)
	{
		return "pages/index";
	}
}
