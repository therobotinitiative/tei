package com.orbital3d.server.tei.controller.pages;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * View page controller.
 * 
 * @author msiren
 *
 */
@Controller
public class ViewStatistic
{
	/**
	 * @param model
	 * @return View page template name
	 */
	@GetMapping("/view")
	public String viewPage(Model model)
	{
		return "pages/view";
	}
}
