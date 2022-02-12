package com.orbital3d.server.tei.database.document;

import java.util.Collections;
import java.util.Set;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.orbital3d.server.tei.type.DomainObject;

@Document(collection = "permissions")
public class Permissions implements DomainObject
{
	@Id
	private ObjectId id;

	@DBRef
	private User user;

	private Set<String> permissions;

	public Permissions(ObjectId id, User user, Set<String> permissions)
	{
		this.id = id;
		this.user = user;
		this.permissions = Collections.unmodifiableSet(permissions);
	}

	public Permissions()
	{
		// Default
	}

	public ObjectId getId()
	{
		return id;
	}

	public void setId(ObjectId id)
	{
		this.id = id;
	}

	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
	}

	public Set<String> getPermissions()
	{
		return Collections.unmodifiableSet(permissions);
	}

	public void setPermissions(Set<String> permissions)
	{
		this.permissions = Collections.unmodifiableSet(permissions);
	}

}
