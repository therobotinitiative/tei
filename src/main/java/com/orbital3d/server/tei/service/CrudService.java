package com.orbital3d.server.tei.service;

import java.math.BigInteger;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.orbital3d.server.tei.type.DomainObject;

/**
 * Basic CRUD service interface with default implementations. Using generic to
 * define the used types. It is left for the implementation to provide the
 * repository instance which will be used to execute the actions.
 * 
 * Operations are {@link Transactional} see {@link Propagation#REQUIRED} for
 * more details.
 * 
 * @author msiren
 *
 * @param <T>
 * @param <R>
 */
public interface CrudService<T extends DomainObject, R extends CrudRepository<T, BigInteger>>
{
	/**
	 * Default implementation for saving domain object.
	 * 
	 * @param data Domain object to save
	 * @return New instance of domain object, this should be used after saving
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	default T save(T data)
	{
		return getRepository().save(data);
	}

	/**
	 * Default implementation for updating domain object.
	 * 
	 * @param data Domain object to update
	 * @return Updated instance of domain object
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	default T update(T data)
	{
		return getRepository().save(data);
	}

	/**
	 * Default implementation for deleting domain object.
	 * 
	 * @param data Domain object to delete
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	default void delete(T data)
	{
		getRepository().delete(data);
	}

	/**
	 * @return {@link CrudRepository} instance which is used to perform the actions
	 */
	R getRepository();
}
