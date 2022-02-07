package com.orbital3d.server.tei.controller.pages;

import java.util.LinkedHashMap;
import java.util.Map;

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
		Map<String, String> menu = new LinkedHashMap<>();

		menu.put("#!/dashboard", "Dashboard");
		menu.put("#!/view", "View");
		menu.put("#!/send", "Send");
		menu.put("#!/template", "Template");
		// TODO : Check if the user is administrator then show "admin" menu item
		menu.put("/logout", "Logout");

		model.addAttribute("menuItems", menu);
		return "pages/index";
	}
}
