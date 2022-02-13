package com.orbital3d.server.tei.controller.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orbital3d.server.tei.database.document.Permissions;
import com.orbital3d.server.tei.database.document.User;
import com.orbital3d.server.tei.database.repository.PermissionsRepository;
import com.orbital3d.server.tei.security.permissiom.TEIPermissions;
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
	private UserService userService;

	@Autowired
	private PasswordService passwordService;

	@Autowired
	private PermissionsRepository permissionRepository;

	/**
	 * Get user or all users. User can be retrieved with user name, but if the user
	 * name is omitted all users will be returned.<br>
	 * <h3>Required permission:</h3><br>
	 * <ul>
	 * <li>{@link TEIPermissions#ADMINISTRATOR}</li>
	 * </ul>
	 * 
	 * @param userName User name to getM optional
	 * @return {@link User} as {@link List} n JSON format
	 */
	@GetMapping(path =
	{ "/admin/user", "/admin/user/{username}" }, produces =
	{ MediaType.APPLICATION_JSON_VALUE })
	@RequiresPermissions(TEIPermissions.ADMINISTRATOR)
	public List<User> getUser(@PathVariable(name = "username", required = false) String userName)
	{
		List<User> user = null;
		if (StringUtils.isNotEmpty(userName))
		{
			user = new ArrayList<>();
			user.add(userService.findUser(userName));
		}
		else
		{
			user = userService.findAll();
		}
		return user;
	}

	/**
	 * Create new user. <b>Password is not set.</b><br>
	 * <h3>Required permission:</h3><br>
	 * <ul>
	 * <li>{@link TEIPermissions#ADMIN_CREATE_USER}</li>
	 * </ul>
	 * 
	 * @param userName User name to create
	 * @return Newly created {@link User} as JSON
	 * @throws IllegalStateException If user with the user name already exists
	 */
	@PostMapping(path = "/admin/users/{username}", produces =
	{ MediaType.APPLICATION_JSON_VALUE })
	// @RequiresPermissions(SystemPermissions.ADMIN_CREATE_USER)
	public User creeateUser(@PathVariable("username") String userName)
	{
		if (userService.exists(userName))
		{
			throw new IllegalStateException("User already exists");
		}
		User user = new User();
		user.setUserName(userName);
		return userService.save(user);

	}

	/**
	 * Change user password. This is administrative operation so the new password is
	 * just updated.<br>
	 * <h3>Required permission:</h3><br>
	 * <ul>
	 * <li>{@link TEIPermissions#ADMIN_UPDATE_USE_PASSWORD}</li>
	 * </ul>
	 * 
	 * @param userName User name
	 * @param password New password
	 */
	@PutMapping("/admin/users/{username}/{password}")
	// @RequiresPermissions(SystemPermissions.ADMIN_UPDATE_USE_PASSWORD)
	public void updateUPassword(@PathVariable("username") String userName, @PathVariable("password") String password)
	{
		User user = userService.findUser(userName);
		// Hash the password and all that jazz
		byte[] salt = passwordService.generateSalt();
		byte[] hashedPassword = passwordService.hashPassword(password, salt);
		user.setPassword(hashedPassword);
		user.setSalt(salt);
		userService.save(user);
	}

	/**
	 * Retrieves user permissions.
	 * 
	 * @param userName User name
	 * @return {@link Set} of user permissions
	 */
	@GetMapping("/admin/user/permissions/{username}")
//	@RequiresPermissions(TEIPermissions.ADMIN_USER_PERMISSIONS)
	public Set<String> getUserPermissions(@PathVariable("username") String userName)
	{
		Permissions permissions = permissionRepository.findByUser(userService.findUser(userName));
		if (permissions != null)
		{
			return permissions.getPermissions();
		}
		return null;
	}

	/**
	 * Delete user.<br>
	 * <br>
	 * <h3>Required oermissions:</h3><br>
	 * <ul>
	 * <li>{@link TEIPermissions#ADMIN_USER_DELETE}</li>
	 * </ul>
	 * 
	 * @param userName User name to delete
	 */
	@DeleteMapping("/admin/user/{username}")
	public void delete(@PathVariable("username") String userName)
	{
		User user = userService.findUser(userName);
		if (user != null)
		{
			// Clean up all the user related information
			Permissions permissions = permissionRepository.findByUser(user);
			if (permissions != null)
			{
				permissionRepository.delete(permissions);
			}
			userService.delete(user);
		}
	}

}
