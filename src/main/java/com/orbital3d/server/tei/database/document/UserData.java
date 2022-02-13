package com.orbital3d.server.tei.database.document;

import com.orbital3d.server.tei.type.DomainObject;

//@Document(collection = "user_data")
public class UserData implements DomainObject
{
	private String firstName;

	private String lastName;

	private String email;

	public UserData(String firstName, String lastName, String email)
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public UserData()
	{
		// Default
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	@Override
	public String toString()
	{
		return String.format("UserData[firstName='%s', lastName='%s', email='%s']", firstName, lastName, email);
	}

}
