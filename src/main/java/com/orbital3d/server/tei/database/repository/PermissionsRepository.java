package com.orbital3d.server.tei.database.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.orbital3d.server.tei.database.document.Permissions;
import com.orbital3d.server.tei.database.document.User;

public interface PermissionsRepository extends MongoRepository<Permissions, ObjectId>
{
	Permissions findByUser(User user);
}
