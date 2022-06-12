package com.orbital3d.server.tei.security.permissiom;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

/**
 * TEI specific permissions.
 * 
 * @author msiren
 *
 */
@Component
public final class TEIPermissions implements SystemPermissions
{
	public static final class Dashboard
	{
		public static final String VIEW = "tei:dashboard:view";
	}

	public static final class View
	{
		public static final String VIEW = "tei:view:view";
	}

	public static final class Send
	{
		public static final String VIEW = "tei:send:view";
	}

	public static final class Templates
	{
		public static final String VIEW = "tei:template:view";
		public static final String STORE = "tei:template:store";
		public static final String RESTORE = "tei:template:restore";
		public static final String IDS = "tei:template:ids";
		public static final String STORE_TAGS = "tei:template:store-tags";
		public static final String TAGS = "tei:template:tags";
	}

	public static final class Administrator
	{
		public static final String VIEW = "tei:administrator:view";
		public static final String CREATE_USER = "tei:administrator:create-user";
		public static final String GET_USER = "tei:administrator:get-user";
		public static final String UPDATE_USER_PASSWORD = "tei:administrator:update-user-password";
		public static final String USER_PERMISSIONS = "tei:administrator:user-permissions";
		public static final String DELETE_USER = "tei:administrator:delete-user";
		public static final String UPDATE_USER_DATA = "tei:administrator:update-user-data";
		public static final String UPDATE_USER_PERMISSIONS = "tei:administrator:update-user-permissions";
		public static final String PERMISSIONS = "tei:administrator:permissions";
	}

	@Override
	public Set<String> allPermissions() throws IllegalArgumentException, IllegalAccessException
	{
		Set<String> permissions = new HashSet<>();
		for (Class<?> c : this.getClass().getClasses())
		{
			for (Field field : c.getFields())
			{
				// Only get the static final fields
				if ((field.getModifiers() & Modifier.STATIC) != 0 && (field.getModifiers() & Modifier.FINAL) != 0)
				{
					Object object = field.get(this);
					if (object.getClass().isAssignableFrom(String.class))
					{
						permissions.add((String) object);
					}
				}
			}
		}
		return permissions;
	}
}
