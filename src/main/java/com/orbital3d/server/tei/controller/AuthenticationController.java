package com.orbital3d.server.tei.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.orbital3d.server.tei.database.document.User;
import com.orbital3d.server.tei.service.PasswordService;
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

	@Autowired
	private PasswordService passwordService;

	/**
	 * GET request for displaying login page.
	 * 
	 * @param unauth Used to indicate if to show that attempt was unsuccessful.
	 *               Subject to change
	 * @param model  {@link Model} to carry data to login page
	 * @return Login page template name
	 */
	@GetMapping("/login")
	public String loginPage(@RequestParam(name = "unauthorized", required = false) boolean unauth, HttpServletRequest request, Model model)
	{
		String token = new String(passwordService.generateSalt());
		// Store token to session, TODO: remember expiration
		SecurityUtils.getSubject().getSession().setAttribute("token", token);

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
	 * @throws AuthenticationException If authentication fails
	 */
	@PostMapping("/login")
	public String login(@RequestParam(name = "user") String userName, @RequestParam(name = "password") String password, @RequestParam(name = "token", required = true) String token,
			HttpServletRequest request, HttpServletResponse response, Model model)
	{
		User user = userService.findUser(userName);
		String sessionToken = (String) SecurityUtils.getSubject().getSession().getAttribute("token");
		if (user != null && token.equals(sessionToken))
		{
			SecurityUtils.getSubject().login(new UsernamePasswordToken(userName, password.toCharArray()));
			LOG.info("{} user logged in with token {}", user.getUserName(), token);
			return "redirect:/tei#!/dashboard";
		}
		throw new AuthenticationException();
	}

	/**
	 * Logout end point. Logs the user out and invalidates any data associated with
	 * it.
	 * 
	 * @return Redirection to log out successful page
	 */
	@GetMapping("/logout")
	public String logout(HttpServletRequest request)
	{
		LOG.info("User logged out");
		SecurityUtils.getSubject().logout();
		return "redirect:login";
	}
}
