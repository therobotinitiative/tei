package com.orbital3d.server.tei.controller.admin;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orbital3d.server.tei.database.document.User;
import com.orbital3d.server.tei.service.PasswordService;
import com.orbital3d.server.tei.service.UserService;

/**
 * REST (or RPC to be more exact) controller for user related administrative
 * operations.
 * 
 * @author msiren
 *
 */
@RestController
public class UserController
{
	@Autowired
	private UserService us;

	@Autowired
	private PasswordService ps;

	/**
	 * Get user or all users. User can be retrieved with user name, but if the user
	 * name is omitted all users will be returned.<br>
	 * <h3>Required permission:</h3><br>
	 * <ul>
	 * <li>tei:afministrator</li>
	 * </ul>
	 * 
	 * @param userName User name to getM optional
	 * @return {@link User} as {@link List} n JSON format
	 */
	@GetMapping(path =
	{ "/admin/user", "/admin/user/{username}" }, produces =
	{ MediaType.APPLICATION_JSON_VALUE })
	@RequiresPermissions("tei:administrator")
	public List<User> getUser(@PathVariable(name = "username", required = false) String userName)
	{
		List<User> user = null;
		if (StringUtils.isNotEmpty(userName))
		{
			user = new ArrayList<>();
			user.add(us.findUser(userName));
		}
		else
		{
			user = us.findAll();
		}
		return user;
	}

	/**
	 * Create new user. <b>Password is not set.</b><br>
	 * <h3>Required permission:</h3><br>
	 * <ul>
	 * <li>tei:administrator:createuser</li>
	 * </ul>
	 * 
	 * @param userName User name to create
	 * @return Newly created {@link User} as JSON
	 */
	@PostMapping(path = "/admin/users/{username}", produces =
	{ MediaType.APPLICATION_JSON_VALUE })
	// @RequiresPermissions("tei:administrator:createuser")
	public User creeateUser(@PathVariable("username") String userName)
	{
		User u = new User();
		u.setUserName(userName);
		return us.save(u);
	}

	/**
	 * Change user password. This is administrative operation so the new password is
	 * just updated.<br>
	 * <h3>Required permission:</h3><br>
	 * <ul>
	 * <li>tei:administrator:updateuserpassword</li>
	 * </ul>
	 * 
	 * @param userName User name
	 * @param password New password
	 */
	@PutMapping("/admin/users/{username}/{password}")
	// @RequiresPermissions("tei:administrator:updateuserpassword")
	public void updateUPassword(@PathVariable("username") String userName, @PathVariable("password") String password)
	{
		User u = us.findUser(userName);
		// Hash the password and all that jazz
		byte[] salt = ps.generateSalt();
		byte[] hpwd = ps.hashPassword(password, salt);
		u.setPassword(hpwd);
		u.setSalt(salt);
		us.save(u);
	}

}
