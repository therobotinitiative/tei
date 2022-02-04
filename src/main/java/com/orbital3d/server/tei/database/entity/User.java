package com.orbital3d.server.tei.database.entity;

import java.math.BigInteger;

import org.springframework.data.annotation.Id;

import com.orbital3d.server.tei.type.DomainObject;

public class User implements DomainObject
{
	@Id
	private BigInteger id;

	private String userName;
	private char[] password;
	private char[] salt;

	public User()
	{
		// Default
	}

	public User(BigInteger id, String userName, char[] password, char[] salt)
	{
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.salt = salt;
	}

	public BigInteger getId()
	{
		return id;
	}

	public void setId(BigInteger id)
	{
		this.id = id;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public char[] getPassword()
	{
		return password;
	}

	public void setPassword(char[] password)
	{
		this.password = password;
	}

	public char[] getSalt()
	{
		return salt;
	}

	public void setSalt(char[] salt)
	{
		this.salt = salt;
	}

	@Override
	public String toString()
	{
		return String.format("Customer[id=%s, userName='%s']", id, userName);
	}
}
