package com.orbital3d.server.tei.database.document;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import com.orbital3d.server.tei.type.DomainObject;

@Document(collection = "user")
public class User implements DomainObject
{
	@MongoId
	private ObjectId id;

	private String userName;
	private byte[] password;
	private byte[] salt;
	private UserData userData;

	public User()
	{
		// Default
	}

	public User(ObjectId id, String userName, byte[] password, byte[] salt, UserData userData)
	{
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.salt = salt;
		this.userData = userData;
	}

	public ObjectId getId()
	{
		return id;
	}

	public void setId(ObjectId id)
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

	public UserData getUserData()
	{
		return userData;
	}

	public void setUserData(UserData userData)
	{
		this.userData = userData;
	}

	@Override
	public String toString()
	{
		return String.format("User[id=%s, userName='%s']", id, userName);
	}
}
