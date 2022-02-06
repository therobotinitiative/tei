package com.orbital3d.server.tei.database.document;

import java.math.BigInteger;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import com.orbital3d.server.tei.type.DomainObject;

@Document(collection = "user")
public class User implements DomainObject
{
	@MongoId
	private BigInteger id;

	private String userName;
	private byte[] password;
	private byte[] salt;

	public User()
	{
		// Default
	}

	public User(BigInteger id, String userName, byte[] password, byte[] salt)
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

	public byte[] getPassword()
	{
		return password;
	}

	public void setPassword(byte[] password)
	{
		this.password = password;
	}

	public byte[] getSalt()
	{
		return salt;
	}

	public void setSalt(byte[] salt)
	{
		this.salt = salt;
	}

	@Override
	public String toString()
	{
		return String.format("User[id=%s, userName='%s']", id, userName);
	}
}
