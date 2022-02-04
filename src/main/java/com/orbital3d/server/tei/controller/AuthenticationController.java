package com.orbital3d.server.tei.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.orbital3d.server.tei.database.entity.User;
import com.orbital3d.server.tei.service.UserService;

/**
 * Authentication controller.
 * 
 * @author msiren
 *
 */
@Controller
public class AuthenticationController
{
	private static final Logger LOG = LoggerFactory.getLogger(AuthenticationController.class);

	@Autowired
	private UserService userService;

	/**
	 * GET request for displaying login page.
	 * 
	 * @param unauth Used to indicate if to show that attempt was unsuccessful.
	 *               Subject to changw
	 * @param model  {@link Model} to carry data to login page
	 * @return Login page template name
	 */
	@GetMapping("/login")
	public String loginPage(@RequestParam(name = "unauthorized", required = false) boolean unauth, Model model)
	{
		String token = "0xdeadbabe";
		model.addAttribute("token", token);
		if (unauth)
		{
			model.addAttribute("unauth", "Unauthorized");
		}
		return "login";
	}

	/**
	 * POST request for login. Collects the data from the request to perform
	 * authentication.
	 * 
	 * @param userName User name
	 * @param password Password
	 * @param model
	 * @return Redirect to application main page if the login was successful
	 */
	@PostMapping("/login")
	public String login(@RequestParam(name = "user") String userName, @RequestParam(name = "password") String password, Model model)
	{
		User user = userService.finfUser(userName);
		LOG.info("{} user logged in", user.getUserName());
		return "redirect:/tei";
	}

	/**
	 * Logout end point. Logs the user out and invalidates any data associated with
	 * it.
	 * 
	 * @return Redirection to log out successful page
	 */
	@GetMapping("/logout")
	public String logout()
	{
		LOG.info("User logged out");
		return "redirect:login";
	}
}
