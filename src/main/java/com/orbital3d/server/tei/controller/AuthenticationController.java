package com.orbital3d.server.tei.controller;

import java.util.Arrays;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.orbital3d.server.tei.database.document.User;
import com.orbital3d.server.tei.error.AuthenticationFailedExcetion;
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
		request.getSession(true).setAttribute("token", token);

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
		if (user != null && token.equals(request.getSession(false).getAttribute("token")))
		{
			String authToken = verifyPassword(password, user);
			if (authToken != null)
			{
				// Save authentication token as cookie
				Cookie cookie = new Cookie("auth", authToken);
				cookie.setMaxAge(60 * 60); // One hour
				response.addCookie(cookie);
				// Save the authentication token into session data
				request.getSession(false).setAttribute("auth", authToken);

				LOG.info("{} user logged in with token {}", user.getUserName(), token);
				return "redirect:/tei";
			}
		}
		throw new AuthenticationFailedExcetion();
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
		request.getSession().invalidate();
		LOG.info("User logged out");
		return "redirect:login";
	}

	private String verifyPassword(String password, User user)
	{
		// Hash the given password
		byte[] pwd = passwordService.hashPassword(password, user.getSalt());
		// Compare with one is data storage
		if (Arrays.compare(pwd, user.getPassword()) == 0)
		{
			return new String(passwordService.generateSalt());
		}
		// TODO : throw exception
		return null;
	}
}
