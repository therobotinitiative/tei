package com.orbital3d.server.tei.controller.admin;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.orbital3d.server.tei.database.document.User;
import com.orbital3d.server.tei.database.document.UserData;
import com.orbital3d.server.tei.security.permissiom.TEIPermissions;
import com.orbital3d.server.tei.service.UserService;

@RestController
public class UserDataController
{
	/**
	 * Data Transfer Object for receiving user data.
	 * 
	 * @author msiren
	 *
	 */
	// Methods used with reflection
	@SuppressWarnings("unused")
	private static final class UserDataControllerDTO
	{
		private String firstName;
		private String lastName;
		private String email;

		private UserDataControllerDTO()
		{
			// Default
		}

		String getFirstName()
		{
			return firstName;
		}

		private void setFirstName(String firstName)
		{
			this.firstName = firstName;
		}

		String getLastName()
		{
			return lastName;
		}

		private void setLastName(String lastName)
		{
			this.lastName = lastName;
		}

		String getEmail()
		{
			return email;
		}

		private void setEmail(String email)
		{
			this.email = email;
		}

	}

	@Autowired
	private UserService userService;

	/**
	 * Updating user data information.<br>
	 * <br>
	 * <h2>Required permissions:</h2><br>
	 * <ul>
	 * <li>{@link TEIPermissions#ADMIN_USERDATA_UPDATE}</li>
	 * </ul>
	 * 
	 * @param userName    User name
	 * @param newUserData User data to update into
	 */
	@PutMapping("/admin/userdata/{username}")
	@RequiresPermissions(TEIPermissions.Administrator.UPDATE_USER_DATA)
	public void updateUserData(@PathVariable("username") String userName, @RequestBody UserDataControllerDTO newUserData)
	{
		User uaer = userService.findUser(userName);
		if (uaer != null)
		{
			UserData userData = uaer.getUserData();
			if (userData == null)
			{
				userData = new UserData();
			}
			userData.setFirstName(newUserData.getFirstName());
			userData.setLastName(newUserData.getLastName());
			userData.setEmail(newUserData.getEmail());
			uaer.setUserData(userData);
			userService.save(uaer);
		}
	}
}
