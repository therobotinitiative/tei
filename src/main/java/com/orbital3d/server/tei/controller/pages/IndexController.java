package com.orbital3d.server.tei.controller.pages;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.orbital3d.server.tei.i18n.LocalisationKeys;

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
		Map<String, LocalisationKeys> menu = new LinkedHashMap<>();

		menu.put("#!/dashboard", LocalisationKeys.DASHBOARD);
		menu.put("#!/view", LocalisationKeys.VIEW);
		menu.put("#!/send", LocalisationKeys.SEND);
		menu.put("#!/template", LocalisationKeys.TEMPLATE);
		// TODO : Check if the user is administrator then show "admin" menu item
		menu.put("/logout", LocalisationKeys.LOGOUT);

		model.addAttribute("menuItems", menu);
		return "pages/index";
	}
}
