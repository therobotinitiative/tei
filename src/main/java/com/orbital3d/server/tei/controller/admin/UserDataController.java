package com.orbital3d.server.tei.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.orbital3d.server.tei.database.document.User;
import com.orbital3d.server.tei.database.document.UserData;
import com.orbital3d.server.tei.service.UserService;

@RestController
public class UserDataController
{
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
	private UserService us;

	@PutMapping("/admin/userdata/{username}")
	public void updateUserData(@PathVariable("username") String userName, @RequestBody UserDataControllerDTO newUserData)
	{
		User u = us.findUser(userName);
		if (u != null)
		{
			UserData ud = u.getUserData();
			if (ud == null)
			{
				ud = new UserData();
			}
			ud.setFirstName(newUserData.getFirstName());
			ud.setLastName(newUserData.getLastName());
			ud.setEmail(newUserData.getEmail());
			u.setUserData(ud);
			us.save(u);
		}
	}
}
