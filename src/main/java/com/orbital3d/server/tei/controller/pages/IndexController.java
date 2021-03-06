package com.orbital3d.server.tei.controller.pages;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.orbital3d.server.tei.i18n.LocalisationKeys;
import com.orbital3d.server.tei.security.permissiom.TEIPermissions;

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
	 * Inner class for populating menu items.
	 * 
	 * @author msiren
	 *
	 */
	static class MenuContainer
	{
		private String url;
		private LocalisationKeys localisationKey;
		private String permission;

		private MenuContainer(String url, LocalisationKeys localisationKey, String permission)
		{
			this.url = url;
			this.localisationKey = localisationKey;
			this.permission = permission;
		}

		public String getUrl()
		{
			return url;
		}

		public LocalisationKeys getLocalisationKey()
		{
			return localisationKey;
		}

		public String getPermission()
		{
			return permission;
		}

		/**
		 * Static factory method.
		 * 
		 * @param url             Path for the menu item
		 * @param localisationKey Localisation key for the menu item
		 * @param permission      Required permission
		 * @return New instance
		 */
		public static MenuContainer of(String url, LocalisationKeys localisationKey, String permission)
		{
			return new MenuContainer(url, localisationKey, permission);
		}

	}

	/**
	 * @param model
	 * @return Index page template name
	 */
	@GetMapping("/tei")
	public String index(Model model)
	{
		Map<String, LocalisationKeys> menu = new LinkedHashMap<>();
		Set<MenuContainer> menuItems = new LinkedHashSet<>();

		menuItems.add(MenuContainer.of("#!/dashboard", LocalisationKeys.DASHBOARD, TEIPermissions.Dashboard.VIEW));
		menuItems.add(MenuContainer.of("#!/view", LocalisationKeys.VIEW, TEIPermissions.View.VIEW));
		menuItems.add(MenuContainer.of("#!/send", LocalisationKeys.SEND, TEIPermissions.Send.VIEW));
		menuItems.add(MenuContainer.of("#!/template", LocalisationKeys.TEMPLATE, TEIPermissions.Templates.VIEW));
		menuItems.add(MenuContainer.of("#!/admin", LocalisationKeys.ADMINISTRATOR, TEIPermissions.Administrator.VIEW));
		menuItems.add(MenuContainer.of("/logout", LocalisationKeys.LOGOUT, ""));

		Subject subject = SecurityUtils.getSubject();
		for (MenuContainer menuContainer : menuItems)
		{
			if (StringUtils.isEmpty(menuContainer.getPermission()) || subject.isPermitted(menuContainer.getPermission()))
			{
				menu.put(menuContainer.getUrl(), menuContainer.getLocalisationKey());
			}
		}

		model.addAttribute("menuItems", menu);
		model.addAttribute("user", "Fetch user name");
		return "pages/index";
	}
}
